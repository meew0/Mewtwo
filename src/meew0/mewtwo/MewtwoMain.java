package meew0.mewtwo;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationBuilder;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
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

        config = new PropertiesConfiguration("mewtwo.cfg");

        mewtwoLogger.info("Loaded config");

        nick = config.getString("nick", "TheMewtwo");
        server = config.getString("server", "chat.freenode.net");
        login = config.getString("login", "Mewtwo");
        password = config.getString("password", "fur3x6f3r78hvg645");



		org.pircbotx.Configuration.Builder<PircBotX> configuration = new org.pircbotx.Configuration.Builder<PircBotX>()
		        .setName(nick)
		        .setLogin(login)
		        .setRealName("Mewtwo")
		        .setAutoNickChange(true)
		        .setCapEnabled(true)
		        .addListener(new MewtwoListener())
		        .setServerHostname(server);

        if(!password.equals("")) configuration.setNickservPassword(password);
		
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
