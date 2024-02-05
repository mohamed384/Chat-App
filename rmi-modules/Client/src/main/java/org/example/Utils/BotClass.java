package org.example.Utils;

import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.*;

public class BotClass {

    public static String getBotResult(String textline) throws Exception {

        ChatterBotFactory factory = new ChatterBotFactory();

        ChatterBot bot1 = factory.create(ChatterBotType.CLEVERBOT);
        ChatterBotSession bot1session = bot1.createSession();

        ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        ChatterBotSession bot2session = bot2.createSession();

        String s = textline;
        s = bot2session.think(s);

        return s;

    }
}
