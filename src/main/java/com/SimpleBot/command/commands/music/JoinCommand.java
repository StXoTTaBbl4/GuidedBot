package com.SimpleBot.command.commands.music;

import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

@SuppressWarnings("ContentConditions")
public class JoinCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(selfVoiceState.inVoiceChannel()){
            channel.sendMessage("Бот уже подключён к голосовому каналу").queue();
            return;
        }

        final Member member = ctx.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("Вы должны находится в голосовом канале\n" +
                    "для использования этой команды").queue();
            return;
        }

        final AudioManager audioManager = ctx.getGuild().getAudioManager();
        final VoiceChannel memberChannel = memberVoiceState.getChannel();


        if(self.hasPermission(memberChannel,Permission.VOICE_CONNECT)) {
            audioManager.openAudioConnection(memberChannel);
            channel.sendMessageFormat("Подключён к `\uD83D\uDD0A %s`", memberChannel.getName()).queue();
        }else
            channel.sendMessage("Недостаточно прав для подключения к этому каналу.").queue();

    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Командует боту зайти в голосовой канал";
    }
}
