package com.elodie.jeux.jeu_Mastermind;

import com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;
import static com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu.*;
import static java.lang.Character.getNumericValue;

/**
 *  <b>Mastermind // Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour
 * chaque proposition le nombre de chiffres de la proposition qui apparaissent
 * à la bonne place et à la mauvaise place dans la combinaison secrète.
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<section>
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -> Réponse : 1 présent, 1 bien placé</i></p>
 *<p><i>(Proposition : 2214 -> Réponse : 2 bien placés</i></p>
 *</section>
 * @author elojito
 * @version 1.0
 */

public class MastermindGame_Defenseur {
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>tableau de 4 chiffres comportant la combinaison secrète</li>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <li>un tableau formant une combinaison secrète avec ces 4 chiffres</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * @see MastermindGame_Defenseur#startDefenseurMastermindGame()
     * <p>Si l'utilisateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    public static final int[] secretCode = inputSecretCode();
    static String AIinput = "";
    static ArrayList output = new ArrayList();
    static ArrayList reponse = new ArrayList();
    static String reponseToString = "";
    final String winwin = "4 bien placés";

    public MastermindGame_Defenseur(){
        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );
        //On lance le jeu
        do {
            startDefenseurMastermindGame();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison!" );
    }

    /**
     * Méthode comprend la mécanique du jeu pour le Mode Defenseur (AI VS utilisateur).
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckMastermindGame(ArrayList, int[], String, ArrayList)
     */
    public static String startDefenseurMastermindGame(){
        ArrayList inputToArray = new ArrayList();
        int first = (int) (Math.random() * 10);
        int second = (int) (Math.random() * 10);
        int third = (int) (Math.random() * 10);
        int fourth = (int) (Math.random() * 10);

        System.out.println( "\nProposition de l'ordinateur." );
        if(!reponseToString.isEmpty()){
            inputToArray.clear();
            for (int i = 0; i < reponseToString.length(); i++) {
                if (AIinput.charAt(i) == secretCode[i]) {
                    String ok = ""+ getNumericValue(AIinput.charAt(i));
                    inputToArray.add( ok );
                }
                else if(AIinput.charAt(i) != secretCode[i]){
                    int minus = getNumericValue(AIinput.charAt(i));
                    minus = randomInRange( -1, getNumericValue( AIinput.charAt(i)));
                    inputToArray.add( minus );
                }
            }
            AIinput = myTrimString(inputToArray.toString());
        }

        else{
            inputToArray.add( first );
            inputToArray.add( second );
            inputToArray.add( third );
            inputToArray.add( fourth );
            AIinput = myTrimString( inputToArray.toString() );
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //vérification réponse/code
        reponseToString = tryOutCheckMastermindGame(inputToArray, secretCode, AIinput, output);
        output.clear();
        return reponseToString;
    }
}