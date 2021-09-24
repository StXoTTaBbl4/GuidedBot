package com.SimpleBot.command.commands;

import com.SimpleBot.CommandManager;
import com.SimpleBot.Config;
import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()){
            StringBuilder builder = new StringBuilder();

            builder.append("List of commands\n");

            manager.getCommands().stream().map(ICommand::getName).forEach((it) -> builder.append(Config.get("PREFIX")).append(it).append('\n'));

            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if(command == null){
            channel.sendMessageFormat("Команда + %s не найдена",search).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();


    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Руководство: ради бога, используйте Octave, а не этот pizdetz\n" +
                "Показывает доступные боту команды\n" +
                "для того чтобы узнать про команду: ~help [command]\n" +
                "хочешь музыку - используй сначала ~join";
    }
}
