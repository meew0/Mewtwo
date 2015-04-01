package meew0.mewtwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MewtwoMain {
    public static Logger mewtwoLogger;

    /**
     * Main method, shouldn't be called
     * @param args The command line arguments, unused
     */
	public static void main(String[] args) {
        // Make logger
        mewtwoLogger = LoggerFactory.getLogger("Mewtwo");
	}
}
