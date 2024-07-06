package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public record User(String nick, String fullHostmask, String hostmask, IRCBot bot) implements IChannel {
    public void sendMessage(String message) {
        bot.writePrivmsg(nick, message);
    }

    @Override
    public String[] getUserNicks() {
        return new String[]{nick};
    }

    @Override
    public String name() {
        return nick;
    }
}
