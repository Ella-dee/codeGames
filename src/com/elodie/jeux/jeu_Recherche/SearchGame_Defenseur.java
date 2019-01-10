package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu;

import java.util.ArrayList;

import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;
import static java.lang.Character.getNumericValue;

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

public class SearchGame_Defenseur {
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>tableau de 4 chiffres comportant la combinaison secrète</li>
     * @see Methodes_MecaniqueJeu#MakeSecretCode()
     * <li>un tableau formant une combinaison secrète avec ces 4 chiffres</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * @see SearchGame_Defenseur#startDefenseurSearchGame()
     * <p>Si l'utilisateur trouve alors apparait "====", le jeu s'arrête</p>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    public static final int[] secretCode = Methodes_MecaniqueJeu.MakeSecretCode();
    static String AIinput = "";
    static ArrayList output = new ArrayList();
    static ArrayList reponse = new ArrayList();
    static String reponseToString = "";
    final String winwin = "====";

    public SearchGame_Defenseur(){
        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );
        //On lance le jeu
        do {
            startDefenseurSearchGame();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison: !" );
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

    /**
     * Méthode comprend la mécanique du jeu pour le Mode Defenseur (AI VS utilisateur).
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * //TODO prendre en compte les opérateurs de comparaison pour AI?
     * @see SearchGame_Defenseur#tryOutCheck(ArrayList, int[], String, ArrayList)
     */
    public static String startDefenseurSearchGame(){
        ArrayList inputToArray = new ArrayList();
        int first = (int) (Math.random() * 10);
        int second = (int) (Math.random() * 10);
        int third = (int) (Math.random() * 10);
        int fourth = (int) (Math.random() * 10);

        System.out.println( "\nPropostition de l'ordinateur." );

        if(!reponseToString.isEmpty()){
            System.out.println("on rentre dans le else car des opérateurs indices ont été saisis:");
            System.out.println("AIinput "+AIinput);
            inputToArray.clear();
            for (int i = 0; i < reponseToString.length(); i++) {
                if (reponseToString.charAt(i) == '=') {
                    System.out.print("\non rentre dans le if =");
                    String ok = ""+ getNumericValue(AIinput.charAt(i));
                    System.out.print(" ok "+ok);
                    inputToArray.add( ok );
                }
                else if(reponseToString.charAt(i) == '-'){
                    System.out.print("\non rentre dans le if -");
                    int minus = getNumericValue(AIinput.charAt(i));
                    minus = randomInRange( 0, getNumericValue(AIinput.charAt(i)));
                    System.out.print(" minus "+minus);
                    inputToArray.add( minus );
                }
                else if(reponseToString.charAt(i) == '+'){
                    System.out.print("\non rentre dans le if +");
                    int plus = getNumericValue(AIinput.charAt(i));
                    plus = randomInRange( getNumericValue(AIinput.charAt(i)), nbr.length-1);
                    System.out.print(" plus "+plus);
                    inputToArray.add( plus );
                }
            }
            AIinput = myTrimString(inputToArray.toString());
            System.out.println(" AIinput "+AIinput );
        }

        else{
            inputToArray.add( first );
            inputToArray.add( second );
            inputToArray.add( third );
            inputToArray.add( fourth );
            AIinput = myTrimString( inputToArray.toString() );
            System.out.println( "first AIinput " + AIinput );
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //vérification réponse/code
        reponseToString = tryOutCheck(inputToArray, secretCode, AIinput, output);
        output.clear();
        return reponseToString;
    }
}
