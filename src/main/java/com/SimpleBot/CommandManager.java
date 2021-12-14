package com.SimpleBot;

import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import com.SimpleBot.command.commands.CellCommand;
import com.SimpleBot.command.commands.HelpCommand;
import com.SimpleBot.command.commands.PasteCommand;
import com.SimpleBot.command.commands.PingCommand;
import com.SimpleBot.command.commands.music.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new PasteCommand());
        addCommand(new CellCommand());

        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new SkipCommand());
        addCommand(new QueueCommand());
        addCommand(new LeaveCommand());
        addCommand(new RepeatCommand());

    }

    private void addCommand(ICommand cmd){
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));
        if(nameFound){
            throw new IllegalArgumentException("A command with this name is already present");
        }
        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search){
        String searchLower = search.toLowerCase();

        for (ICommand cmd: this.commands) {
            if(cmd.getName().equals(searchLower) || cmd.getAliases().equals(searchLower)){
                return cmd;
            }
        }
        return null;
    }

    public void handle(GuildMessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("PREFIX")),"")
                .split("\\s+");

        String invoke =  split[0].toLowerCase();
        ICommand cmd  = this.getCommand(invoke);

        if(cmd != null){
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx  = new CommandContext(event,args);

            cmd.handle(ctx);
        }else{
            event.getChannel().sendMessage("Команда не найдена!").queue();
        }

    }

}
