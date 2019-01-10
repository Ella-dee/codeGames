package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.Exceptions.ExceptionNaN;
import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.GeneralMethodes.Methodes.*;

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
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>4 chiffres générés par la fonction Math.random compris entre 0 et 9</li>
     * <li>un tableau formant une combinaison secrète avec ces 4 chiffres</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * @see SearchGame#start()
     * <p>Si l'utilisateur trouve alors apparait "====", le jeu s'arrête</p>
     */
    public static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    final static int first = (int)(Math.random() * 10);
    final static int second = (int)(Math.random() * 10);
    final static int third = (int)(Math.random() * 10);
    final static int fourth = (int)(Math.random() * 10);
    protected static final int[] secretCode = {first, second, third, fourth};
    static String userInput = "";
    static ArrayList reponse = new ArrayList();
    static String reponseToString = "";
    final String winwin = "====";

    public SearchGame(){

        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );
        //On lance le jeu
        do {
            start();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison: "+userInput );
    }

    /**
     * Méthode récupère une chaine de caractères de chiffres prise en entrée pour l'afficher en sortie.
     * On récupère une liste composée des caractères de cette chaine, puis on la parcoure.
     * On compare ainsi chaque entrée de la liste avec lse entrées d'un tableau de chiffres.
     * Selon si les entrées de la liste sont supérieures, inférieures ou égales aux entrées du tableau,
     * on affichera en sortie "-", "+", ou "=".
     * Ces résultats seront stocker dans une liste, puis transformés en chaine de caractères pour l'affichage sortie.
     * @param liste une liste composée de chaque chiffre de la chaine de caractères input
     * @param secret un tableau composé de chiffres définis
     * @param input entrée utilisateur composée de chiffres
     * @param output liste composée des opérateurs de comparaison entre "liste" et "secret"
     * @return une chaine de caractères composées des entrées de la liste "output"
     */
    public static String tryOutCheck(ArrayList liste, int[] secret, String input, ArrayList output){
        String equal = "=";
        String minus = "-";
        String plus = "+";
        System.out.print( "Votre proposition: " + input + " -> Réponse: " );
        int i=0;
        for (Object o:liste) {
            if (Integer.parseInt( o.toString())== secretCode[i]) {
                System.out.print( "=" );
                output.add( equal );
            } else if (Integer.parseInt( o.toString())< secretCode[i]) {
                System.out.print( "+" );
                output.add( plus );
            } else if (Integer.parseInt( o.toString())> secretCode[i]) {
                System.out.print( "-" );
                output.add( minus );
            }
            i++;
        }
        String outputToString = myTrimString(output.toString());
        return outputToString;
    }

    /**
     * Méthode comprend la mécanique du jeu.
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see SearchGame#tryOutCheck(ArrayList, int[], String, ArrayList)
     */
    public static void start(){
        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList inputToArray = new ArrayList();
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
        reponseToString = tryOutCheck( inputToArray, secretCode, userInput, reponse);
        reponse.clear();
    }
}
