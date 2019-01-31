package com.elodie.jeux.utilities;

import com.elodie.jeux.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;

import static org.apache.logging.log4j.core.util.Loader.getClassLoader;

public class UtilsPropreties {

    public UtilsPropreties() {
        super();
    }

    /**
     * Méthode récupère la valeur d'une propriété
     * @param str chaine de caractère représentant le nom de la propriété
     * @return chaine de caractères représentant la valeur de la propriété
     */
    public static String getConfigProprety(String str){
        Logger logger = LogManager.getLogger( Main.class);
        UtilsPropreties demo = new UtilsPropreties();
        String prop = "";
        try {
            Properties loadedProps = demo.loadProperties("config.propreties");
            prop = loadedProps.getProperty(str);
        }catch (FileNotFoundException e){
            System.out.println( "le fichier spécifié est introuvable" );
            logger.error( "le fichier spécifié est introuvable" );
        }catch (IOException e){
            System.out.println( "erreur lors de la lecture du fichier" );
            logger.error( "erreur lors de la lecture du fichier" );
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * Cette méthode lit et charge un fichier UtilsPropreties à l'emplacement spécifié
     * @param propertiesFileLocation L'emplacement du fichier
     * @return Le fichier UtilsPropreties chargé
     * @throws FileNotFoundException si le fichier n'a pas été trouvé
     * @throws IOException si une erreur est survenue durant la lecture
     * */
    public Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        InputStream in = getClassLoader().getResourceAsStream(propertiesFileLocation);
        props.load(in);
        return props;
    }
    /**
     *Cette méthode affiche cahque paire [clé,valuer] d'un fichier UtilsPropreties
     *  @param props Le fichier à afficher
     */
    public static void displayProperties(Properties props) {
        Iterator it = props.keySet().iterator();
        while (it.hasNext()) {
            String propertyName = (String) it.next();
            String propertyValue = props.getProperty(propertyName);
            System.out.println(propertyName + "=" + propertyValue); }
    }
}
