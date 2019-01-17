package com.elodie.jeux.jeu_Mastermind;

import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import java.util.Arrays;

import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;
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
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés, x présents" à venir</li>
     * <li>un compteur d'essais</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String AIinput = "";
    static String verifReponse = "";
    final String winwin = "4 bien placés";
    static int counter = 0;
    /**
     * <b>Méthode comprend la mécanique du jeu pour le Mode Défenseur (AI vs utilisateur)</b>
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#inputSecretCode()
     * <p>On lance le jeu</p>
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'ordinateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     */
    public MastermindGame_Defenseur(){
        int[] secretCode = inputSecretCode();
        ArrayList inputToArray = new ArrayList();
        int first = (int) (Math.random() * 10);
        int second = (int) (Math.random() * 10);
        int third = (int) (Math.random() * 10);
        int fourth = (int) (Math.random() * 10);

        //affichage du code secret pour mode développeur
        showSecretCode( secretCode );
        //On lance le jeu
        do {
            System.out.println( "Proposition de l'ordinateur." );
            //Si des essais ont déjà été faits par l'AI:
            if(!verifReponse.isEmpty()){
                // on vide la liste de l'essai AI
                inputToArray.clear();
                for (int i = 0; i < AIinput.length(); i++) {
                    //On garde le chiffre donné s'il est bon et à la bonne place
                    if (AIinput.charAt(i) == secretCode[i]) {
                        String ok = ""+ getNumericValue(AIinput.charAt(i));
                        inputToArray.add( ok );
                        System.out.println( ok );
                    }
                    //TODO écarter les propositions déjà faites
                    //S'il est bon mais pas à la bonne place
                    else if((AIinput.charAt(i) != secretCode[i]) && (Arrays.asList( secretCode ).contains( AIinput.charAt(i)))){
                        //TODO à implémenter (random en attendant)
                        inputToArray.add((int) (Math.random() * 10));
                    }
                    //Si non trouvé
                    else{
                        inputToArray.add((int) (Math.random() * 10));
                    }
                }
                AIinput = myTrimString(inputToArray.toString());
            }
            //Si c'est le premier essai, on lance 4 randoms
            else{
                inputToArray.add( first );
                inputToArray.add( second );
                inputToArray.add( third );
                inputToArray.add( fourth );
                AIinput = myTrimString( inputToArray.toString() );
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //vérification réponse/code
            verifReponse = tryOutCheckMastermindGame(inputToArray, secretCode, AIinput);
            counter++;
        }while(!verifReponse.equals(winwin) && counter < 6);
        if(verifReponse.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison!" );
        }
        else {
            System.out.println( "\nL'ordinateur n'a pas trouvé votre combinaison!" );
        }
    }
}
