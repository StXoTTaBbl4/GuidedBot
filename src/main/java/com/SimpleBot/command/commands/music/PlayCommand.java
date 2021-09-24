package com.SimpleBot.command.commands.music;

import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import com.SimpleBot.lavaPlayer.PlayerManager;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;


import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
import java.net.URL;

public class PlayCommand implements ICommand {

    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();

        final Member self = ctx.getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(ctx.getArgs().isEmpty()){
            //channel.sendMessage("Шутник, а запрос ты дома не забыл?").queue();
            //return;
            PlayerManager.getInstance().loadAndPlay(channel,"https://www.youtube.com/watch?v=5qap5aO4i9A&ab_channel=LofiGirl");
        }


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

        String link = String.join(" ", ctx.getArgs());

        if(!isUrl(link)) {
            link = "ytsearch:" + link;
            
        }

            PlayerManager.getInstance().loadAndPlay(channel,link);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "а ты угадай чё она длеает";
    }

    private boolean isUrl(String url){
        try{
            new URL(url);
            return true;
        }catch (MalformedURLException e){
            return false;
        }
    }
}
