package com.elodie.jeux.utilities;

import com.elodie.jeux.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.Iterator;
import java.util.Properties;

public class utilsPropreties {

    final static  String configPath = "./src/main/resources/config.propreties";

    public utilsPropreties() {
        super();
    }

    /**
     * Méthode récupère la valeur d'une propriété
     * @param str chaine de caractère représentant le nom de la propriété
     * @return chaine de caractères représentant la valeur de la propriété
     */
    public static String getConfigProprety(String str){
        Logger logger = LogManager.getLogger( Main.class);
        utilsPropreties demo = new utilsPropreties();
        String prop = "";
        try {
            String propertiesFileLocation = configPath;
            Properties loadedProps = demo.loadProperties( propertiesFileLocation );
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
     * Méthode récupère la valeur d'une propriété
     * @param key chaine de caractère représentant le nom de la propriété
     */
    public static void replaceProprety(String key, String newKeyValue){
        Logger logger = LogManager.getLogger( Main.class);
        utilsPropreties demo = new utilsPropreties();
        try {
            String propertiesFileLocation = configPath;
            Properties loadedProps = demo.loadProperties( propertiesFileLocation );
            String oldKey = getConfigProprety( key );
            loadedProps.replace( key, newKeyValue );
            // On stocke le fichier sur le disque
            demo.saveProperties( loadedProps, propertiesFileLocation, "Sauvegarde config.propreties" );
            logger.info( key+" "+oldKey+", in config "+getConfigProprety(key));
        }catch (FileNotFoundException e){
            System.out.println( "le fichier spécifié est introuvable" );
            logger.error( "le fichier spécifié est introuvable" );
        }catch (IOException e){
            System.out.println( "erreur lors de la lecture du fichier" );
            logger.error( "erreur lors de la lecture du fichier" );
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode stocke le fichier utilsPropreties à l'emplacement spécifié
     * @param props Le fichier à stocker
     * @param fileLocation L'emplacement où le fichier doit être stocké
     * @param comments Commentaires à insérer en tête du fichier
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     * @throws IOException si une erreur est survenue lors de l'écriture du fichier
     * */
    public void saveProperties(Properties props, String fileLocation, String comments) throws FileNotFoundException, IOException {
        OutputStream out = new FileOutputStream(fileLocation);
        props.store(out, comments);
        out.flush();
        out.close();
    }
    /**
     * Cette méthode lit un fichier utilsPropreties à l'emplacement spécifié *
     * @param propertiesFileLocation L'emplacemnt du fichier
     * @return Le fichier utilsPropreties chargé *
     * @throws FileNotFoundException si le fichier n'a pas été trouvé
     * @throws IOException si une erreur est survenue durant la lecture
     * */
    public java.util.Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesFileLocation));
        return props;
    }
    /**
     *Cette méthode affiche cahque paire [clé,valuer] d'un fichier utilsPropreties
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
