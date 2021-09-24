package com.SimpleBot;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class BotMain {

    private BotMain() throws LoginException {
        JDABuilder.createDefault(Config.get("TOKEN"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_EMOJIS
                )
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Listener())
                .setActivity(Activity.playing("инкубаторо для бактериев\n" +
                        "~help"))
                .build();
    }

    public static void main(String[] args) throws LoginException {
        new BotMain();
    }
}
