package com.SimpleBot.command.commands;

import com.SimpleBot.Config;
import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final Member member = ctx.getMember();
        final Guild guild = member.getGuild();
        final TextChannel channel = ctx.getChannel();

        final String arg = ctx.getArgs().toString();
        final List<Role> kickRole = guild.getRolesByName(Config.get("KICK_ROLE"),false);
        if(member.getRoles().containsAll(kickRole)){
            guild.kick(arg.substring(4,arg.length()-2)).queue();
            channel.sendMessage("Кекикнут").queue();
        }else{
            channel.sendMessage("Недостаточно сосал админу ;-)").queue();
        }


    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "Кикает выбранного участника. Только для администарции.";
    }
}
