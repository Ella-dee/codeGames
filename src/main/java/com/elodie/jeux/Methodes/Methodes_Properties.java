package com.elodie.jeux.Methodes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Properties;

public class Methodes_Properties {
    public Methodes_Properties() {
        super();
    }
    /**
     * Cette méthode stocke le fichier Methodes_Properties à l'emplacement spécifié
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
     * Cette méthode lit un fichier Methodes_Properties à l'emplacement spécifié *
     * @param propertiesFileLocation L'emplacemnt du fichier
     * @return Le fichier Methodes_Properties chargé *
     * @throws FileNotFoundException si le fichier n'a pas été trouvé
     * @throws IOException si une erreur est survenue durant la lecture
     * */
    public java.util.Properties loadProperties(String propertiesFileLocation) throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(propertiesFileLocation));
        return props;
    }
    /**
     *Cette méthode affiche cahque paire [clé,valuer] d'un fichier Methodes_Properties
     *  @param props Le fichier à afficher
     */
    public static void displayProperties(Properties props) {
        Iterator it = props.keySet().iterator();
        while (it.hasNext()) {
            String propertyName = (String) it.next();
            String propertyValue = props.getProperty(propertyName);
            System.out.println(propertyName + "=" + propertyValue); }
    }
   /* /**
     *  Cette méthode représente la démo d'utilisation de Propreties.
     *  On y crée un fichier Properties que l'on remplitavec des paires [clé,valeur]
     *  puis on le stocke sur le disque. Ensuite on le load depuis le disque et, enfin,
     *  son contenu est affiché.
     *  @param args non utilsé
     */
  /*  public static void main(String[] args) {
        Methodes_Properties demo = new Methodes_Properties();
        //Emplacement où sera stocké le fichier
        String propertiesFileLocation = "C:\\Users\\elodie\\IdeaProjects\\jeux\\src\\main\\resources\\config.propreties";
        // On instancie un nouvel objet
        Properties myProps = new Properties();
        // On y insère des paires [clé,valeur]
        myProps.setProperty("mode.dev","on");
        try {
            // On stocke le fichier sur le disque
            demo.saveProperties(myProps, propertiesFileLocation, "This is a demo on Methodes_Properties");
            // On crée un nouvel objet Methodes_Properties en lisant le fichier sur le disque
            Properties loadedProps = demo.loadProperties(propertiesFileLocation);
            //On affiche le contenu du fichier
            demo.displayProperties(loadedProps);
        } catch (FileNotFoundException e) { e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }
    }*/
}
