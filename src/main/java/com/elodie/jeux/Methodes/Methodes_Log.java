package com.elodie.jeux.Methodes;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Methodes_Log {
    static final Logger logger = LogManager.getLogger();

    public void logIt() {
        logger.debug("msg de debogage");
        logger.info("msg d'information");
        logger.warn("msg d'avertissement");
        logger.error("msg d'erreur");
        logger.fatal("msg d'erreur fatale");
    }
}
