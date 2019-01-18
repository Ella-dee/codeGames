package com.elodie.jeux;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LogInfos {
    static final Logger logger = LogManager.getLogger( LogInfos.class.getName());

    public boolean doIt() {
        logger.entry();
        logger.debug("msg de debogage");
        logger.info("msg d'information");
        logger.warn("msg d'avertissement");
        logger.error("msg d'erreur");
        logger.fatal("msg d'erreur fatale");
        return logger.exit(false);
    }
}
