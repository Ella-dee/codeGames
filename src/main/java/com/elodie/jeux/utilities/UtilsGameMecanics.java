package com.elodie.jeux.utilities;

import com.elodie.jeux.exceptions.ExceptionNaL;
import com.elodie.jeux.exceptions.ExceptionNaN;
import com.elodie.jeux.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;
import static com.elodie.jeux.utilities.Utils.*;
import static com.elodie.jeux.utilities.UtilsPropreties.getConfigProprety;
import static java.lang.Character.getNumericValue;

public class UtilsGameMecanics {

    private static final Logger logger = LogManager.getLogger( Main.class);
    //Méthodes communes
    /**
     * Méthode génère un code aléatoire du nombre de chiffres définis par l'utilisateur et compris entre 0 et 9
     * @return un code secret sous forme de tableau d'entiers
     */
    public static int[] computedSecretCode(){
        int cases = 0;
        String max_cases = getConfigProprety("max.cases");
        cases = Integer.parseInt( max_cases );
        int[]code = new int[cases];
        for(int i = 0; i<cases;i++){
            code[i] = (int)(Math.random() * 10);
        }
        logger.info( "code secret de "+cases+" cases: "+showSecretCode(code));
        return code;
    }
    /**
     * Méthode renvoie un nombre limite d'essais.
     * Récupère le paramètre dans config.propreties
     * @return un entier représentant le nombre max d'essais.
     * @see UtilsPropreties#getConfigProprety(String)
     */
    public static int maxTries(){
        int max = 0;
        String max_tries = getConfigProprety("max.tries");
        max = Integer.parseInt( max_tries );
        return max;
    }

    /**
     * Méthode génère un code de x chiffres compris entre 0 et 9 donné par l'utilisateur.
     * On demande à l'utilisateur de saisir un code secret.
     * On vérifie qu'il s'agit d'un nombre et qu'il est composé du nombre de chiffres attendus.
     * @return un code secret sous forme de tableau d'entiers
     * @see ExceptionNaN
     * @see UtilsPropreties#getConfigProprety(String)
     */
    public static int[] inputSecretCode(){
        int cases = 0;
        String max_cases = getConfigProprety("max.cases");
        cases = Integer.parseInt( max_cases );
        int[] code = new int[cases];
        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList inputToArray = new ArrayList();
        String input = "";
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        do{
            try{
                catched = false;
                System.out.println( "Saisissez une combinaison secrète à "+cases+" chiffres:" );
                input = sc.nextLine();
                inputToArray = createArrayListeFromInput( input );
                if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                    throw new ExceptionNaN();
                }
            } catch (ExceptionNaN e) {
                catched = true;
                logger.error( "NotANumber catched = "+cases );
            }
            finally {
                if ( inputToArray.size() > cases || inputToArray.size()<cases) {
                    System.out.print( "Vous devez saisir une combinaison "+cases+" chiffres." );
                    catched = true;
                    logger.error( "Bad size catched = "+inputToArray.size()+", expected size = "+cases );

                }
            }
        }while(catched);

        int i = 0;
        for (Object o:inputToArray){
            int oInt = Integer.parseInt(o.toString());
            code[i] = oInt;
            i++;
        }
        logger.info( "code secret avec "+cases+" cases: "+showSecretCode(code));
        return code;
    }

    //Méthodes pour le jeu Recherche
    /**
     * Méthode pour le jeu Recherche:
     * affiche le résultat de l'essai pour trouver la combinaison.
     * On récupère une liste composée des essais, qu'on compare avec lse entrées d'un tableau de chiffres.
     * Selon si les entrées de la liste sont supérieures, inférieures ou égales aux entrées du tableau,
     * on affichera en sortie "-", "+", ou "=".
     * Ces résultats seront stocker dans une liste, puis transformés en chaine de caractères pour l'affichage sortie.
     * @param liste une liste composée de chaque chiffre de la chaine de caractères input
     * @param secret un tableau composé de chiffres définis
     * @param input entrée utilisateur composée de chiffres
     * @return une chaine de caractères composées des indices "+=-".
     */
    public static String tryOutCheckSearchGame(ArrayList liste, int[] secret, String input){
        ArrayList output = new ArrayList();
        String equal = "=";
        String minus = "-";
        String plus = "+";
        System.out.print( "\nProposition: " + input + " -> Réponse: " );
        int i=0;
        for (Object o:liste) {
            if (Integer.parseInt( o.toString())== secret[i]) {
                System.out.print( "=" );
                output.add( equal );
            } else if (Integer.parseInt( o.toString())< secret[i]) {
                System.out.print( "+" );
                output.add( plus );
            } else if (Integer.parseInt( o.toString())> secret[i]) {
                System.out.print( "-" );
                output.add( minus );
            }
            i++;
        }
        String outputToString = myTrimString(output.toString());
        output.clear();
        return outputToString;
    }

    /**
     * Méthode représente le tour de jeu de l'utilisateur.
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @param userInput chaine de caractères représentant les entrées utilisateur
     * @param userInputListe liste contenant la chaine userInput
     * @param secret tableau d'entiers représentant le code secret
     * @return une chaine de caractère représetnant les indices "-,+,=" de la réponse utilisateur.
     * @see ExceptionNaN#ExceptionNaN()
     * @see UtilsGameMecanics#tryOutCheckSearchGame(ArrayList, int[], String)
     */
    public static String playerTurnSearchGame(String userInput, ArrayList userInputListe, int[] secret){
        Scanner sc = new Scanner( System.in );
        boolean catched;
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        String indicesReponse = "";

        do{
            try{
                catched = false;

                    System.out.println("\nQuelle est votre proposition?");
                    userInput = sc.nextLine();
                userInputListe = createArrayListeFromInput( userInput );
                if(!checkOccurencesFromListInArray(userInputListe, nbr)){
                    throw new ExceptionNaN();
                }
            } catch (ExceptionNaN e) {
                catched = true;
                logger.error( "NotANumber catched = "+userInput );
            }
            finally {
                if ( userInputListe.size() > secret.length || userInputListe.size() < secret.length) {
                    System.out.print( "Vous devez saisir une combinaison à " + secret.length + " chiffres." );
                    catched = true;
                    logger.error( "Bad size catched = "+userInput.length()+", expected size = "+secret.length );
                }
            }
        }while (catched);
        //vérification réponse/code
        indicesReponse = tryOutCheckSearchGame(userInputListe, secret, userInput);
        return indicesReponse;
    }

//TODO SearchGame: mutualiser le tour de jeu de l'ordinateur pour ne pas répéter deux fois dans duel et défenseur

    //Méthode pour le jeu mastermind
    /**
     * Méthode pour le jeu mastermind:
     * affiche le résultat de l'essai pour trouver la combinaison.
     * On récupère une liste composée des essais, qu'on compare avec lse entrées d'un tableau de chiffres.
     * Selon si les entrées de la liste sont trouvées et à la bonne place, ou présentes dans la combinaison
     * mais mal placées, on affichera en sortie "x bien placé" ou "x présent".
     * Ces résultats seront stocker dans une liste, puis transformés en chaine de caractères pour l'affichage sortie.
     * L'attribut "found" s'incrémente si un chiffre est bien placé.
     * L'attribut "somewhere" s'incrémente si un chiffre est trouvé mais mal placé.
     * @param liste une liste composée de chaque chiffre de la chaine de caractères input
     * @param secret un tableau composé de chiffres définis
     * @param input entrée utilisateur composée de chiffres
     * @return une chaine de caractères composées des entrées de la liste "output"
     */

    public static String tryOutCheckMastermindGame(ArrayList liste, int[] secret, String input){
        ArrayList output = new ArrayList();
        int found = 0;
        int somewhere = 0;
        System.out.print( "\nProposition: " + input + " -> Réponse: " );
        int i=0;
        //On parcoure la liste des entrées utilisateurs en parsant chaque entrée en chiffre entier
        for (Object o:liste) {
            int oInt = Integer.parseInt(o.toString());
            //Si le chiffre de la liste entrées se trouve dans le code et est au bon endroit
            if (oInt == secret[i]) {
                found += 1;
            }
            //Autrement: Si le chiffre de la liste entrées se trouve au moins dans le code
            else {
                for (int j = 0; j < secret.length; j++) {
                    if (secret[j] == oInt) {
                        somewhere+=1;
                    }
                }
            }
            i++;
        }
        //Une fois la liste entièrement parcourue, on regarde les valeurs de found et somewhere
        //On ajoute les chaines de caractères correspondantes à la liste output pour l'affichage sortie
        if(found == 1){
            output.add(found+" bien placé");
        }
        // Pour la gestion du pluriel
        else if(found >1){
            output.add( found+" bien placés" );
        }
        if(somewhere == 1){
            output.add( somewhere+" présent" );
        }
        // Pour la gestion du pluriel
        else if(somewhere >1){
            output.add( somewhere+" présents" );
        }
        else if(somewhere==0 && found == 0){
            output.add( "aucun chiffre trouvé" );
        }
        String outputToString = trimStringKeepFormat(output.toString());
        output.clear();
        System.out.println( outputToString );
        return outputToString;
    }

    /**
     * Méthode enregistre tous les chiffres quand aucun ne compose le code secret dans une liste
     * @param secretCode tableau représentant le code secret
     * @param compInput chaine de caractères représentant l'entrée AI
     * @param defNot liste enregistrée contenant les chiffres qui ne composent pas le code
     */
    public static void noNumbersFoundAtAll(int[] secretCode, String compInput, ArrayList defNot){
    for (int i = 0; i < secretCode.length; i++) {
        int chiffre = getNumericValue((compInput.charAt(i)));
        int chiffreSoluce = secretCode[i];
        if (!defNot.contains(chiffre)) {
            defNot.add(chiffre);
        }
    }
}

    /**
     * Méthode enregistre le chiffre si à la bonne place et le garde à celle ci
     * @param chiffre entier représentant le chiffre proposé
     * @param inputToArray liste représentant le code de sortie de chiffres proposés
     * @param i entier représentant la case du chiffre que l'on teste
     */
    public static void numberFoundRightPlace(int chiffre, ArrayList inputToArray, int i){
    String goodAnswer = "" + chiffre;
    inputToArray.set(i, goodAnswer);
}

    /**
     * Méthode concerne un chiffre présent dans le code mais proposé à une autre place que la sienne
     * Si le chiffre n'est pas contenu dans la liste maybe on l'y rajoute.
     * Si la combinaison case-chiffre n'est pas contenu dans la liste chiffreEssaye on l'y rajoute.
     * On choisi un chiffre contenu dans la liste maybe aléatoirement.
     * Si le chiffre choisi n'a pas déjà été proposé à cette case, on l'enregistre pour la proposition.
     * Sinon on lance la fonction randomizeNumber().
     * @param maybe liste contenant les chiffres enregistrés comme présents dans le code
     * @param chiffre  entier représentant le chiffre proposé
     * @param chiffreEssaye liste contenant les combinaison case-chiffre déjà essayées
     * @param i entier représentant la case du chiffre que l'on teste
     * @param inputToArray liste représentant le code de sortie de chiffres proposés
     * @param defNot liste enregistrée contenant les chiffres qui ne composent pas le code
     * @see UtilsGameMecanics#randomizeNumber(ArrayList, ArrayList, int, ArrayList)
     */
    public static void numberFoundElsewhere(ArrayList maybe, int chiffre, ArrayList chiffreEssaye, int i, ArrayList inputToArray, ArrayList defNot ){
        int otherNbr = 0;
        if(!maybe.contains(chiffre)) {
            maybe.add(chiffre);
        }
        if(!chiffreEssaye.contains(i+""+chiffre)) {
            chiffreEssaye.add(i+""+chiffre);
        }

        //On génère un chiffre de la liste maybe
        ListIterator<String> it = chiffreEssaye.listIterator() ;
        int index = (int) (Math.random() * maybe.size());
        int maybeNbr = (Integer) maybe.get(index);
        //on l'affecte à la case s'il n'a pas déjà été proposé
        if(maybeNbr != chiffre && !chiffreEssaye.contains(i+""+maybeNbr)){
            inputToArray.set(i, maybeNbr);
        }
        //Si le chiffre maybe a déjà été proposé dans la case présente on lance un random non proposé pour cette case
        else {
            while (it.hasNext()) {
                String element = it.next();
                if (element.equals(i + "" + maybeNbr)) {
                    randomizeNumber(defNot, chiffreEssaye,i, inputToArray);
                }
            }
        }
    }

    /**
     *Méthode concerne un chiffre non présent dans le code.
     * On ajoute la combinaison case-chiffre dans la liste maybeNot.
     * On compte les occurences de cette combinaison dans la liste.
     * Si elle apparait plus d'une fois, on place le chiffre dans la liste defNot car il ne fait pas parti du code.
     * Si la combinaison case-chiffre n'est pas contenu dans la liste chiffreEssaye on l'y rajoute.
     * On choisi un chiffre contenu dans la liste maybe aléatoirement.
     * Si le chiffre choisi n'a pas déjà été proposé à cette case, on l'enregistre pour la proposition.
     * Sinon on lance la fonction randomizeNumber().
     * @param maybe liste contenant les chiffres enregistrés comme présents dans le code
     * @param maybeNot liste contenant les chiffres non présents dans le code ayant été utilisés
     * @param chiffre  entier représentant le chiffre proposé
     * @param chiffreEssaye liste contenant les combinaison case-chiffre déjà essayées
     * @param i entier représentant la case du chiffre que l'on teste
     * @param inputToArray liste représentant le code de sortie de chiffres proposés
     * @param defNot liste enregistrée contenant les chiffres qui ne composent pas le code
     * @see UtilsGameMecanics#randomizeNumber(ArrayList, ArrayList, int, ArrayList)
     */
    public static void numberNotFound(ArrayList chiffreEssaye, int i, int chiffre, ArrayList maybe, ArrayList inputToArray, ArrayList defNot, ArrayList maybeNot) {
        int otherNbr = 0;
        //On ajoute le chiffre dans la liste maybeNot.
        String chiffreToString = String.valueOf(chiffre);
        maybeNot.add(chiffreToString);
        int count = 0;
        ListIterator<String> it = maybeNot.listIterator() ;
        //On compte les occurences de cette combinaison dans la liste.
        while (it.hasNext()) {
            String element = it.next();
            if (element.equals(chiffreToString)) {
                count++;
            }
        }
        //Si elle apparait plus d'une fois, on place le chiffre dans la liste defNot car il ne fait pas parti du code.
        if(count>=2){
            defNot.add(chiffre);
        }

        //On ajoute la combinaison case-chiffre dans la liste chiffreEssaye s'il n'y est pas déjà.
        if (!chiffreEssaye.contains(i + "" + chiffre)) {
            chiffreEssaye.add(i + "" + chiffre);
        }
        //Si maybe n'est pas vide, on prend aléatoirement un chiffre de cette liste
        if (maybe.size() > 0) {
            int index = (int) (Math.random() * maybe.size());
            int maybeNbr = (Integer) maybe.get(index);
            if (maybeNbr != chiffre && !chiffreEssaye.contains(i + "" + otherNbr)) {
                inputToArray.set(i, maybeNbr);
            }
            //Si le chiffre maybe a déjà été proposé dans la case présente on lance un random non proposé pour cette case
            else {
                randomizeNumber(defNot, chiffreEssaye, i, inputToArray);
            }
        }
        //Si maybe est vide, on choisi un chiffre aléatoirement en vérifiant qu'il n'ait pas déjà été proposés.
        else {
            randomizeNumber(defNot, chiffreEssaye, i, inputToArray);
        }
    }

    /**
     *Méthode choisi un chiffre aléatoirement en vérifiant qu'il n'ait pas déjà été proposés.
     * @param chiffreEssaye liste contenant les combinaison case-chiffre déjà essayées
     * @param i entier représentant la case du chiffre que l'on teste
     * @param inputToArray liste représentant le code de sortie de chiffres proposés
     * @param defNot liste enregistrée contenant les chiffres qui ne composent pas le code
     * @return entier représentant le chiffre à enregistrer pour la proposition
     */
    public static int randomizeNumber(ArrayList defNot, ArrayList chiffreEssaye, int i, ArrayList inputToArray){
        int otherNbr = 0;
        do {
            otherNbr = (int) (Math.random() * 10);
        } while (defNot.contains(otherNbr)&& !chiffreEssaye.contains(i+""+otherNbr));
        inputToArray.set(i, otherNbr);
        return otherNbr;
    }
    //Méthodes pour le menu principal
    /**
     * Méthode qui demande à l'utilisateur s'il souhaite refaire une partie d'un autre jeu.
     * On demande à l'utilisateur s'il souhaite jouer à un autre jeu.
     * On vérifie que sa réponse est bien une lettre et qu'elle correspond à 'N' ou 'O'.
     * @return chaine de caractère représentant O ou N
     * @see ExceptionNaL
     */
    public static String backToMenu(){
        boolean catched;
        Scanner sc = new Scanner( System.in );
        String startAgain = "";
        do{
            try {
                catched = false;
                System.out.println( "Souhaitez-vous jouer à un autre jeu? O/N" );
                startAgain = myTrimString(sc.nextLine()).toUpperCase();
                if(!startAgain.matches( "[a-zA-Z]" )){
                    throw new ExceptionNaL();
                }
            } catch (ExceptionNaL e) {
                catched = true;
                logger.error( "NotALetter catched = "+startAgain );
            } finally {
                if (!startAgain.equals("O") && !startAgain.equals("N")) {
                    catched = true;
                    System.out.println( "Vous n'avez pas saisi O ou N mais: "+startAgain+"." );
                    logger.error( "Not O or N catched = "+startAgain );
                }
            }
        }while (catched);
        return startAgain;
    }

    /**
     * Méthode demande à l'utilisateur de choisir un jeu.
     * On vérifie que son choix est viable, puis on enregistre son choix.
     * @return un entier représentant le jeu choisi
     * @see ExceptionNaN
     */
    public static int chooseModeForGame(){
        boolean catched = false;
        Scanner sc = new Scanner( System.in );
        String chosenMode = "";
        int chosenModeParsed = 0;
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        ArrayList inputToArray = new ArrayList();

        do{
            try {
                catched = false;
                System.out.println( "Veuillez choisir votre mode de jeu: 1 - Challenger, 2 - Défenseur, 3 - Duel" );
                chosenMode = sc.nextLine();
                inputToArray = createArrayListeFromInput( chosenMode );
                if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                    throw new ExceptionNaN();
                }
                else{
                    chosenModeParsed = Integer.parseInt( myTrimString(chosenMode) );
                }
            }catch (ExceptionNaN e){
                catched = true;
                logger.error( "NotANumber catched = "+chosenMode );
            }
            finally {
                if(chosenModeParsed < 1|| chosenModeParsed > 3) {
                    System.out.println( "Vous devez saisir un choix parmis ceux disponibles." );
                    catched = true;
                    logger.error( "Not 1 or 2 catched = "+chosenModeParsed );
                }
            }
        }while(catched);
        return chosenModeParsed;
    }
    /**
     * Méthode qui demande à l'utilisateur s'il souhaite refaire une partie du même jeu.
     * On demande à l'utilisateur s'il souhaite refaire une partie du même jeu.
     * On vérifie que sa réponse est bien une lettre et qu'elle correspond à 'N' ou 'O'.
     * @return chaine de caractère représentant O ou N
     * @see ExceptionNaL
     */
    public static String stopOuEncore(){
        boolean catched;
        Scanner sc = new Scanner( System.in );
        String playAgain = "";
        do{
            try {
                catched = false;
                System.out.println( "Souhaitez-vous refaire une partie? O/N" );
                playAgain = myTrimString(sc.nextLine()).toUpperCase();
                if(!playAgain.matches( "[a-zA-Z]" )){
                    throw new ExceptionNaL();
                }
            } catch (ExceptionNaL e) {
                catched = true;
                logger.error( "NotALetter catched = "+playAgain );
            } finally {
                if (!playAgain.equals("O") && !playAgain.equals("N")) {
                    catched = true;
                    System.out.println( "Vous n'avez pas saisi O ou N mais: "+playAgain+"." );
                    logger.error( "not O or N catched = "+playAgain );
                }
            }
        }while (catched);
        return playAgain;
    }
    /**
     * Méthode permet de sélectionner un jeu.
     * On demande à l'utilisateur à quel jeu il souhaite jouer.
     * On vérifie que sa réponse est viable et on enregistre son choix.
     * @return un entier représentant le jeu choisi
     * @see ExceptionNaN
     */
    public static int menuJeu() {
        boolean catched = false;
        String chosenGame = "";
        Scanner sc = new Scanner( System.in );
        ArrayList inputToArray = new ArrayList();
        int chosenGameParsed = 0;
        final String[] nbr = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        do {
            try {
                catched = false;
                System.out.println( "Veuillez choisir votre jeu: 1 - Recherche +/-, 2 - MasterMind" );
                chosenGame = sc.nextLine();
                inputToArray = createArrayListeFromInput( chosenGame );
                if (!checkOccurencesFromListInArray( inputToArray, nbr )) {
                    throw new ExceptionNaN();
                } else {
                    chosenGameParsed = Integer.parseInt( myTrimString( chosenGame ) );
                }
            } catch (ExceptionNaN e) {
                catched = true;
                logger.error( "NotANumber catched = "+chosenGame );
            } finally {
                if (chosenGameParsed < 1 || chosenGameParsed > 2) {
                    System.out.println( "Vous devez saisir un choix parmis ceux disponibles." );
                    catched = true;
                    logger.error( "Not 1 or 2 catched = "+chosenGameParsed );
                }
            }
        } while (catched);
        chosenGameParsed = Integer.parseInt( chosenGame );
        return chosenGameParsed;
    }

    //Pour le mode Développeur
    /**
     * Méthode affiche le code secret à trouver, si le mode développeur est enclenché ou à la fin si l'utilisateur n'a pas trouvé le code.
     * @param code le tableau d'entiers représentant le code secret
     * @return une chaine de caractères représentant le code secret
     * @see UtilsGameMecanics#modeDevOrNot()
     */
    public static String showSecretCode(int[] code){
        StringBuilder sb = new StringBuilder();
        sb.append( "(Code Secret: " );
        for (int i = 0; i < code.length; i++) {
            sb.append( code[i] );
        }
        sb.append( ")" );
        String secretCode = sb.toString();
        return secretCode;
    }

    /**
     * Méthode vérifie si le jeu est lancé en mode développeur afin d'afficher.
     * @return un booléen true si le mode développeur est égal à 1
     */
    public static boolean modeDevOrNot(){
        boolean devMode = false;
        int mode_dev_value = Integer.parseInt(UtilsPropreties.getConfigProprety( "mode.dev"));
        if (mode_dev_value == 1  || Main.mainParam.equals("dev")) {
            devMode = true;
        }
        return devMode;
    }

    /**
     * Méthode sert à afficher que le jeu est lancé en mode développeur si c'est le cas.
     * @return booléen true si le mode développeur est activé
     */
    public static boolean showModeDevOn(){
        boolean devOn = false;
        try {
            if (modeDevOrNot() == true) {
                System.out.println("-------------------");
                System.out.println("Mode développeur ON");
                System.out.println("-------------------");
                devOn = true;
            }
        }catch (NullPointerException e){
            devOn = false;
        }
        return devOn;
    }
}
