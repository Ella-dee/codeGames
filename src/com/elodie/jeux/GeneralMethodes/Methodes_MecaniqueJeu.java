package com.elodie.jeux.GeneralMethodes;

import com.elodie.jeux.Exceptions.ExceptionNaL;
import com.elodie.jeux.Exceptions.ExceptionNaN;
import java.util.ArrayList;
import java.util.Scanner;

import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;

public class Methodes_MecaniqueJeu {
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
                System.out.println( "\nSaisissez une combinaison secrète à quatre chiffres:" );
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

    /**
     * Méthode affiche le résultat de l'essai pour trouver la combinaison.
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
        System.out.print( "Proposition: " + input + " -> Réponse: " );
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
     * Méthode affiche le résultat de l'essai pour trouver la combinaison.
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
                System.out.println( "Veuillez choisir votre mode de jeu: 1 - Challenger, 2 - Défenseur" );
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
                if(chosenModeParsed < 1|| chosenModeParsed > 2) {
                    System.out.println( "Vous devez saisir un choix parmis ceux disponibles." );
                    catched = true;
                }
            }
        }while(catched);
        chosenModeParsed = Integer.parseInt( chosenMode);
        return chosenModeParsed;
    }
}
