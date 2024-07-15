package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public record Channel(String name, IRCBot bot) implements IChannel {

    public String getUnprefixedName() {
        if (name.startsWith("#")) {
            return name.substring(1);
        }

        return name;
    }

    public String[] getUserNicks() {
        return bot.getUserListForChannel(name).getNicks();
    }

    @Override
    public void sendMessage(String message) {
        bot.writePrivmsg(name, message);
    }

    @Override
    public boolean isPrivate() {
        return false;
    }
}
