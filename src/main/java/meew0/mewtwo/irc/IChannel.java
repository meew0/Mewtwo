package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public interface IChannel {
    void sendMessage(String message);

    String[] getUserNicks();

    String name();
}
