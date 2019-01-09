package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.Exceptions.ExceptionNaN;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
    public static  String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    int first = (int)(Math.random() * 10);
    int second = (int)(Math.random() * 10);
    int third = (int)(Math.random() * 10);
    int fourth = (int)(Math.random() * 10);
    private int[] secretCode = {first, second, third, fourth};
    String userInput = "";
    ArrayList reponse = new ArrayList();
    String reponseToString = "";
    String winwin = "====";


    public SearchGame(){
        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );

        //le jeu:
        Scanner sc = new Scanner( System.in );
        boolean catched = false;
        ArrayList inputToArray = new ArrayList();
        do {

            do{
                try{
                    catched = false;
                    System.out.println( "\nquelle est votre proposition?" );
                    userInput = sc.nextLine();
                    inputToArray = createArrayFromInput( userInput );
                    if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                        throw new ExceptionNaN();
                    }
                } catch (ExceptionNaN e) {
                    catched = true;
                }
                finally {
                    if ( inputToArray.size() > secretCode.length || inputToArray.size() < secretCode.length) {
                        System.out.print( "Vous devez saisir une combinaison à " + secretCode.length + " chiffres." );
                        catched = false;
                    }
                }
            }while(inputToArray.size()>secretCode.length||inputToArray.size()<secretCode.length||catched);

            //vérification réponse/code
            String equal = "=";
            String minus = "-";
            String plus = "+";
            System.out.print( "Votre proposition: " + userInput + " -> Réponse: " );
            int i=0;
            for (Object o:inputToArray) {
                    if (Integer.parseInt( o.toString())== secretCode[i]) {
                        System.out.print( "=" );
                        reponse.add( equal );
                    } else if (Integer.parseInt( o.toString())< secretCode[i]) {
                        System.out.print( "+" );
                        reponse.add( plus );
                    } else if (Integer.parseInt( o.toString())> secretCode[i]) {
                        System.out.print( "-" );
                        reponse.add( minus );
                    }
                    i++;
                }
            reponseToString = myTrimString(reponse.toString());
            reponse.clear();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison: "+userInput );
    }

    /**
     * Méthode prend la réponse de l'utilisateur et transforme chaque chiffre en élément de liste
     * @param str
     * @return une liste remplie de chaque chiffre composant la réponse utilisateur
     */
    public ArrayList createArrayFromInput(String str) {
        char[] charr = str.toCharArray();
        ArrayList arr = new ArrayList(charr.length);
        for (int i = 0; i< charr.length; i++) {
            arr.add(Character.toString(charr[i]));
        }
        return arr;
    }

    /**
     * Méthode prend une liste, et compare ses entrées avec un tableau.
     * si une entrée de la liste est comprise dans le tableau on ajoute 1 à un compteur.
     * Si le total du compteur n'est pas égal à la longueur du tableau,
     * alors une ou plusieurs entrées de la liste ne sont pas comprises dans le tableau.
     * @param arrLi
     * @param str
     * @return un booléen "appears" qui renvoie "false" si une ou plusieurs entrées de la liste ne sont pas dans le tableau
     */
    private boolean checkOccurencesFromListInArray(ArrayList arrLi, String[] str){
        boolean appears = true;
        int count = 0;
        for(Object o:arrLi){
            for(int j=0;j<str.length;j++){
                if(o.toString().equals(str[j])){
                    count += 1;
                }
            }
            if(count == arrLi.size()){
                appears = true;
            }
            else appears = false;
        }
        return appears;
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
