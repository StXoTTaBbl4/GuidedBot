package com.SimpleBot.command.commands.music;

import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import com.SimpleBot.lavaPlayer.GuildMusicManager;
import com.SimpleBot.lavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class RepeatCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();


        if(!selfVoiceState.inVoiceChannel()) {
            channel.sendMessage("Бот уже подключён к голосовому каналу").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("Вы должны находится в голосом канале\n" +
                    "для использования этой команды").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("Вы должны находится в одном и том же голосовом канале\n" +
                    "для использования этой команды").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final boolean newRepeating = !musicManager.scheduler.repeating;
        musicManager.scheduler.repeating = newRepeating;

        channel.sendMessageFormat("The player has been set to **%s**", newRepeating ? "repeating" : "not repeating").queue();
    }

    @Override
    public String getName() {
        return "repeat";
    }

    @Override
    public String getHelp() {
        return "Ставит трэк на повтор";
    }
}
