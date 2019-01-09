package com.elodie.jeux.jeu_Recherche;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * <b>Recherche +/-</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=).
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<section>
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -> Réponse : -=--</i></p>
 *<p><i>(Proposition : 2214 -> Réponse : -=+=</i></p>
 *</section>
 *<p>Peut être joué selon 3 modes :
 *<ul>
 *     <li>Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur</li>
 *     <li>Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète</li>
 *     <li>Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné</li>
 *</ul>
 *<p>Il doit être possible de lancer l'application dans un mode "développeur".
 * Dans ce mode la solution est affichée dès le début. Cela permet de tester le bon comportement
 * de l'application en cas de bonne ou de mauvaise réponse de l'utilisateur.
 * Ceci est à réaliser avec les mécanismes suivants :
 *<ul>
 *     <li>Passage d'un paramètre au lancement de l'application</li>
 *     <li>Propriété spécifique dans le fichier de configuration</li>
 *</ul>
 * <p>Un fichier de configuration (config.properties) permettra de paramétrer l'application, notamment :
 *<ul>
 *     <li>le nombre de cases de la combinaison secrète</li>
 *     <li>le nombre d'essais possibles</li>
 *</ul>
 * <p>Un fichier de configuration (log4j.xml) permet de paramétrer les logs de l'application.
 * @author elojito
 * @version 1.0
 */

public class SearchGame {
    public   int[] nbr = {0,1,2,3,4,5,6,7,8,9};

    int first = (int)(Math.random() * 10);
    int second = (int)(Math.random() * 10);
    int third = (int)(Math.random() * 10);
    int fourth = (int)(Math.random() * 10);
    private int[] secretCode = {first, second, third, fourth};
    int userInput = 0;
    ArrayList reponse = new ArrayList();
    String reponseToString="";

    public SearchGame(){
        //affichage du code secret
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );

        //interraction user:
        do {
            System.out.println( "\nquelle est votre proposition?" );
            Scanner sc = new Scanner( System.in );
            userInput = sc.nextInt();
            int[] inputToArray = createArrayFromInput( userInput );

            //vérification réponse/code
            String equal = "=";
            String minus = "-";
            String plus = "+";
            System.out.print( "Votre proposition: " + userInput + " -> Réponse: " );
            for (int i = 0; i < inputToArray.length; i++) {
                if (inputToArray[i] == secretCode[i]) {
                    System.out.print( "=" );
                    reponse.add( equal );

                } else if (inputToArray[i] < secretCode[i]) {
                    System.out.print( "+" );
                    reponse.add( plus );
                } else if (inputToArray[i] > secretCode[i]) {
                    System.out.print( "-" );
                    reponse.add( minus );
                }
            }
            reponseToString = myTrimString(reponse.toString());
        }while(!(reponseToString.equals("====")));
        System.out.println( "\nBravo vous avez trouvé la combinaison: "+userInput );
    }

    /**
     * Méthode prend la réponse de l'utilisateur et transforme chaque chiffre en élément de tableau
     * @param number
     * @return un tableau rempli de chaque chiffre composant la réponse utilisateur
     */
    private int[] createArrayFromInput(int number) {
        String str = Integer.toString( number );
        char[] charr = str.toCharArray();
        int[] arr = new int[charr.length];
        for (int i = 0; i< charr.length; i++) {
            arr[i] = Character.getNumericValue(charr[i]);
        }
        return arr;
    }
    /**
     * <b>Méthode de formatage du texte</b>
     * <p>Permet de transformer un ArrayList en chaîne de caractères,
     * et d'enlever la mise en forme "liste"</p>
     * @param str chaîne de caractères donnée
     * @return la chaine de caractères donnée reformatée sans espace
     */
    private static String myTrimString(String str){
        StringBuilder sb = new StringBuilder();
        sb.append(str.replaceAll("[\\[\\],]", "").replace( " ", "" ));
        str.trim();
        return sb.substring( 0, sb.length());
    }

}
