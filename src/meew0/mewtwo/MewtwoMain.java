package meew0.mewtwo;

import org.apache.commons.configuration.*;
import org.pircbotx.PircBotX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class MewtwoMain {
	public static String nick, server, login, password;

    public static Configuration config;

    public static Logger mewtwoLogger;

	public static void main(String[] args) throws ConfigurationException {
        mewtwoLogger = LoggerFactory.getLogger("Mewtwo");

        config = new HierarchicalINIConfiguration("mewtwo.cfg");

        mewtwoLogger.info("Loaded config");

        nick = config.getString("nick", "Mewtwo");
        server = config.getString("server",
                "irc.esper.net");
                //"chat.freenode.net");
                //"127.0.0.1");
        login = config.getString("login", "Mewtwo");
        password = config.getString("password", "");



		org.pircbotx.Configuration.Builder<PircBotX> configuration = new org.pircbotx.Configuration.Builder<PircBotX>()
		        .setName(nick)
		        .setLogin(login)
		        .setRealName("Mewtwo")
		        .setAutoNickChange(true)
		        .setCapEnabled(true)
		        .addListener(new MewtwoListener(config.getString("prefix", "%")))
		        .setServerHostname(server);

        //if(!password.equals("")) configuration.setNickservPassword(password);
		
		PircBotX mewtwo = new PircBotX(configuration.buildConfiguration());

        mewtwoLogger.info("Bot created, starting up...");
		
		try {
			mewtwo.startBot();
            mewtwoLogger.info("Bot started, listening now");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
