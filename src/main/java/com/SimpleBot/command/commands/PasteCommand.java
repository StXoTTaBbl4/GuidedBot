package com.SimpleBot.command.commands;

import com.SimpleBot.command.CommandContext;
import com.SimpleBot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;
import org.menudocs.paste.PasteHost;

import java.util.List;

public class PasteCommand implements ICommand {
    private final PasteClient client = new PasteClientBuilder()
                .setUserAgent("Example paste client")
                .setDefaultExpiry("5m")//поменять на 10 минут
                .setPasteHost(PasteHost.MENUDOCS) // Optional
                .build();
    @Override
    public void handle(CommandContext ctx) {
        final List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();

        if(args.size() < 2){
            channel.sendMessage("Недостаточный размер").queue();
            return;
        }

        ctx.getEvent().getMessage().delete().queue();

        final String language = args.get(0);
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(language) + language.length();
        final String body = contentRaw.substring(index).trim();

        client.createPaste(language,body).async(
                (id) -> client.getPaste(id).async((paste) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Paste " + paste.getId(),paste.getPasteUrl())
                    .setAuthor(ctx.getAuthor()+"")
                    .setDescription("```")
                    .appendDescription(paste.getLanguage().getId())
                    .appendDescription("\n")
                    .appendDescription(paste.getBody())
                    .appendDescription("```");
            channel.sendMessageFormat("%s",builder.build()).queue();
        })
        );

    }

    @Override
    public String getName() {
        return "paste";
    }

    @Override
    public String getHelp() {
        return "Создает рамку с форматированым текстом\n" +
                "Использование: ~paste [language] [text]\n" +
                "чтобы узнать языки...дите нафиг, сами в \n" +
                "https://paste.menudocs.org/ копайтесь";
    }
}
