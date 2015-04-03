package meew0.mewtwo.irc;

/**
 * Created by meew0 on 02.04.15.
 */
public interface IChannel {
    public void sendMessage(String message);
    public String[] getUserNicks();
}
