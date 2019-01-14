package com.elodie.jeux.GeneralMethodes;

import com.elodie.jeux.Exceptions.ExceptionNaN;

import java.util.ArrayList;
import java.util.Scanner;

import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;

public class Methodes_MecaniqueJeu {

    /**
     * Méthode génère un code aléatoire de 4 chiffres compris entre 0 et 9
     * @return un code secret sous forme de tableau d'entiers
     */
    public static int[] computedSecretCode(){
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        final int first = (int)(Math.random() * 10);
        final int second = (int)(Math.random() * 10);
        final int third = (int)(Math.random() * 10);
        final int fourth = (int)(Math.random() * 10);
        final int[] code = {first, second, third, fourth};
        return code;
    }

    /**
     * Méthode génère un code de 4 chiffres compris entre 0 et 9 donné paar l'utilisateur
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
     * @param output liste composée des opérateurs de comparaison entre "liste" et "secret"
     * @return une chaine de caractères composées des entrées de la liste "output"
     */
    public static String tryOutCheckSearchGame(ArrayList liste, int[] secret, String input, ArrayList output){
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
        return outputToString;
    }
}
