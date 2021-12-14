package com.SimpleBot.command.commands;

import com.SimpleBot.Config;
import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class CellCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx)  {
        final Member member = ctx.getMember();
        final Guild guild = ctx.getGuild();
        final String CategoryName = ctx.getArgs().get(0);
        final String roleName = ctx.getArgs().get(1);

        List<Role> MemberRoles = member.getRoles();
        Role Check = guild.getRolesByName(Config.get("CREATOR_ROLE"),false).get(0);
        boolean isCreator = MemberRoles.contains(Check);

        if(isCreator){
            guild.createCategory(CategoryName).queue();
            try{
                Thread.sleep(5000);
            }catch (Exception e){}

            List<Category> categoryList = guild.getCategories();
            Category category = guild.getCategoriesByName(CategoryName, false).get(0);

            guild.createTextChannel("флуд", category).queue();
            guild.createTextChannel("инфо", category).queue();
            guild.createVoiceChannel("Говорильная", category).queue();
            guild.createVoiceChannel("Игровальня", category).queue();

            guild.createRole().setName(roleName).queue();
            ctx.getChannel().sendMessage("done").queue();


        } else
            ctx.getChannel().sendMessage("Право на создание отсутствует.").queue();

    }

    @Override
    public String getName() {
        return "cell";
    }

    @Override
    public String getHelp() {
        return "Команда создающая стандартную ячейку. Доступна только роли Architect\n" +
                "Синтаксис: ~cell [название подгруппы] [название роли для участников]";
    }
}

/*
        String adminID = ctx.getArgs().get(1);
        adminID = adminID.substring(4,adminID.length()-2);
        final Member Moderator = guild.getMemberById(adminID);
 */

/*          guild.createTextChannel("флуд", category).queue();
            guild.createTextChannel("инфо", category).queue();
            guild.createVoiceChannel("Говорильная", category).queue();
            guild.createVoiceChannel("Игровальня", category).queue();

            guild.createRole().setName(roleName).queue();
            ctx.getChannel().sendMessage("done").queue();

 */