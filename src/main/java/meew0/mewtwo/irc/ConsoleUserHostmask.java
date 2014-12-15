package meew0.mewtwo.irc;

import org.pircbotx.PircBotX;
import org.pircbotx.UserHostmask;

/**
 * Created by miras on 15.12.14.
 */

public class ConsoleUserHostmask extends UserHostmask {
    public ConsoleUserHostmask(PircBotX bot) {
        super(bot, "CONSOLE.local");
    }
}