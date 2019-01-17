package com.elodie.jeux.Methodes;

import com.elodie.jeux.Exceptions.ExceptionNaL;
import com.elodie.jeux.Exceptions.ExceptionNaN;
import java.util.ArrayList;
import java.util.Scanner;

import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static java.lang.Character.getNumericValue;

public class Methodes_MecaniqueJeu {

    //Méthodes communes
    /**
     * Méthode génère un code aléatoire de 4 chiffres compris entre 0 et 9
     * @return un code secret sous forme de tableau d'entiers
     */
    public static int[] computedSecretCode(){
         int first = (int)(Math.random() * 10);
         int second = (int)(Math.random() * 10);
         int third = (int)(Math.random() * 10);
         int fourth = (int)(Math.random() * 10);
         int[] code = {first, second, third, fourth};
         return code;
    }

    /**
     * Méthode génère un code de 4 chiffres compris entre 0 et 9 donné par l'utilisateur
     * @return un code secret sous forme de tableau d'entiers
     */
    public static int[] inputSecretCode(){
        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList inputToArray = new ArrayList();
        String input = "";
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        final int[] code = {0,0,0,0};
        do{
            try{
                catched = false;
                System.out.println( "Saisissez une combinaison secrète à quatre chiffres:" );
                input = sc.nextLine();
                inputToArray = createArrayListeFromInput( input );
                if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                    throw new ExceptionNaN();
                }
            } catch (ExceptionNaN e) {
                catched = true;
            }
            finally {
                if ( inputToArray.size() > 4) {
                    System.out.print( "Vous devez saisir une combinaison à 4 chiffres." );
                    catched = true;
                }
            }
        }while(catched);

        int i = 0;
        for (Object o:inputToArray){
            int oInt = Integer.parseInt(o.toString());
            code[i] = oInt;
            i++;
        }

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
    // * @param output liste composée des opérateurs de comparaison entre "liste" et "secret"
     * @return une chaine de caractères composées des entrées de la liste "output"
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
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckSearchGame(ArrayList, int[], String)
     * @param userInput chaine de caractères représentant les entrées utilisateur
     * @param userInputListe liste contenant la chaine userInput
     * @param secret tableau d'entiers représentant le code secret
     * @return une chaine de caractère représetnant les indices "-,+,=" de la réponse utilisateur.
     */
    public static String playerTurnSearchGame(String userInput, ArrayList userInputListe, int[] secret){
    Scanner sc = new Scanner( System.in );
    boolean catched;
    final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    String indicesReponse = "";
    do{
        try{
            catched = false;
            System.out.println( "\nQuelle est votre proposition?" );
            userInput = sc.nextLine();
            userInputListe = createArrayListeFromInput( userInput );
            if(!checkOccurencesFromListInArray(userInputListe, nbr)){
                throw new ExceptionNaN();
            }
        } catch (ExceptionNaN e) {
            catched = true;
        }
        finally {
            if ( userInputListe.size() > secret.length || userInputListe.size() < secret.length) {
                System.out.print( "Vous devez saisir une combinaison à " + secret.length + " chiffres." );
                catched = true;
            }
        }
    }while (catched);
    //vérification réponse/code
    indicesReponse = tryOutCheckSearchGame(userInputListe, secret, userInput);
    return indicesReponse;
}
//TODO SearchGame: mutualiser le tour de jeu de l'ordinateur pour ne pas répéter deux fois dans duel et défenseur

    //Méthode pour le jeu Mastermind
    /**
     * Méthode pour le jeu Mastermind:
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
    //   * @param output liste composée des chaines de caractères définissant les chiffres de "liste" comme étant
     *               présents ou bien placés par rapport à "secret" à l'aide des attributs entiers
     *               found et somewhere.
     * @return une chaine de caractères composées des entrées de la liste "output"
     */

    public static String tryOutCheckMastermindGame(ArrayList liste, int[] secret, String input){
        ArrayList output = new ArrayList();
        int found = 0;
        int somewhere = 0;
        System.out.print( "Proposition: " + input + " -> Réponse: " );
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
        String outputToString = trimStringKeepSpacesAndComas(output.toString());
        output.clear();
        System.out.println( outputToString );
        return outputToString;
    }

    //Méthodes pour le menu principal
    /**
     * Méthode qui demande à l'utilisateur s'il souhaite refaire une partie.
     * @return chaine de caractère représentant O ou N
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
            } finally {
                if (!startAgain.equals("O") && !startAgain.equals("N")) {
                    catched = true;
                    System.out.println( "Vous n'avez pas saisi O ou N." );
                    System.out.println( "Vous avez saisi: "+startAgain );
                }
            }
        }while (catched);
        return startAgain;
    }

    /**
     * Méthode demande à l'utilisateur de choisir un jeu.
     * On vérifie que son choix est viable, puis on enregistre son choix.
     * @return un entier représentant le jeu choisi
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
            }
            finally {
                if(chosenModeParsed < 1|| chosenModeParsed > 3) {
                    System.out.println( "Vous devez saisir un choix parmis ceux disponibles." );
                    catched = true;
                }
            }
        }while(catched);
        chosenModeParsed = Integer.parseInt( chosenMode);
        return chosenModeParsed;
    }
    /**
     * Méthode qui demande à l'utilisateur s'il souhaite refaire une partie du même jeu.
     * @return chaine de caractère représentant O ou N
     */
    public static String stopOuEncore(){
        boolean catched;
        Scanner sc = new Scanner( System.in );
        String playAgain = "";
        do{
            try {
                catched = false;
                System.out.println( "Souhaitez-vous rejouez? O/N" );
                playAgain = myTrimString(sc.nextLine()).toUpperCase();
                if(!playAgain.matches( "[a-zA-Z]" )){
                    throw new ExceptionNaL();
                }
            } catch (ExceptionNaL e) {
                catched = true;
            } finally {
                if (!playAgain.equals("O") && !playAgain.equals("N")) {
                    catched = true;
                    System.out.println( "Vous n'avez pas saisi O ou N." );
                    System.out.println( "Vous avez saisi: "+playAgain );
                }
            }
        }while (catched);
        return playAgain;
    }
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
            } finally {
                if (chosenGameParsed < 1 || chosenGameParsed > 2) {
                    System.out.println( "Vous devez saisir un choix parmis ceux disponibles." );
                    catched = true;
                }
            }
        } while (catched);
        chosenGameParsed = Integer.parseInt( chosenGame );
        return chosenGameParsed;
    }
    //Pour le mode Développeur
    /**
     * Méthode affiche le code secret à trouver.
     * @param code le tableau d'entiers représentant le code secret
     * @return une chaine de caractères donnant le code secret
     */
    public static String showSecretCode(int[] code){
        //affichage du code secret pour mode développeur
        StringBuilder sb = new StringBuilder();
        sb.append("(Code Secret: " );
        for(int i =0;i<code.length;i++){
            sb.append( code[i] );
        }
        sb.append( ")" );
        String secretCode = sb.toString();
        return secretCode;
    }
}
