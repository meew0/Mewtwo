package meew0.mewtwo;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.io.File;

public class MewtwoMain {
	public static final String MEWTWO_NICK = "TheMewtwo";
	public static final String MEWTWO_SERVER =
			"127.0.0.1";
	public static final String MEWTWO_LOCAL = "Mewtwo";

	public static void main(String[] args) {
		Configuration.Builder<PircBotX> configuration = new Configuration.Builder<PircBotX>()
		        .setName(MEWTWO_NICK)
		        .setLogin(MEWTWO_LOCAL)
		        .setRealName("Mewtwo")
		        .setAutoNickChange(true)
		        .setCapEnabled(true)
		        .addListener(new MewtwoListener())
		        .setServerHostname(MEWTWO_SERVER);
		
		PircBotX mewtwo = new PircBotX(configuration.buildConfiguration());
		
		try {
			mewtwo.startBot();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
