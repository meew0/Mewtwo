package meew0.mewtwo;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SuppressWarnings("WeakerAccess")
public class MewtwoMain {
    public static Logger mewtwoLogger;

    /**
     * Main method, shouldn't be called
     * @param args The command line arguments, unused
     * @throws ConfigurationException in case something happens while loading configs
     */
	public static void main(String[] args) throws ConfigurationException, IOException {
        // Make logger
        mewtwoLogger = LoggerFactory.getLogger("Mewtwo");
	}
}
