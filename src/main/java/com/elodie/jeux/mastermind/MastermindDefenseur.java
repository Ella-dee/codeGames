package com.elodie.jeux.mastermind;

import com.elodie.jeux.Main;
import com.elodie.jeux.utilities.UtilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import static com.elodie.jeux.utilities.Utils.*;
import static com.elodie.jeux.utilities.UtilsGameMecanics.*;
import static java.lang.Character.getNumericValue;

/**
 *  <b>mastermind - Mode défenseur</b>
 * <p>Le but : l'ordinateur doit découvrir la combinaison à x chiffres de l'adversaire
 * (le défenseur, soit ici l'utilisateur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour
 * chaque proposition le nombre de chiffres de la proposition qui apparaissent
 * à la bonne place et à la mauvaise place dans la combinaison secrète.
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<p>(Combinaison secrète : 1234)</p>
 *<p>(Proposition : 4278 -&#155; Réponse : 1 présent, 1 bien placé)</p>
 *<p>(Proposition : 2214 -&#155; Réponse : 2 bien placés)</p>
 * @author elojito
 * @version 1.0
 */

public class MastermindDefenseur {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés, x présents" à venir</li>
     * <li>un compteur d'essais</li>
     * <li>un appel pour le logger/li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String compInput = "";
    static String verifReponse = "";
    final String winwin = "4 bien placés";
    private static final Logger logger = LogManager.getLogger( Main.class);

    /**
     * <b>Méthode comprend la mécanique du jeu pour le Mode Défenseur (AI vs utilisateur)</b>
     * <p>On demande à l'utilisateur la longueur du code avec laquelle il souhaite jouer.</p>
     * @see UtilsGameMecanics#chooseCodeLenght()
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * @see UtilsGameMecanics#inputSecretCode()
     * <p>On demande à l'utilisateur le nombre d'essais maximal à ne pas dépasser.</p>
     * @see UtilsGameMecanics#maxTries()
     * <p>On lance le jeu</p>
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see UtilsGameMecanics#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'ordinateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     */
    public MastermindDefenseur(){
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans mastermind Mode Défenseur." );
        System.out.println( "-----------------------------------------" );
        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        int counter = 0;
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCode = inputSecretCode();
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();

        ArrayList inputToArray = new ArrayList();
        //On lance le jeu
        do {
            System.out.println( "Proposition de l'ordinateur." );
            //Si des essais ont déjà été faits par l'AI:
            if(counter!=0){
                // on vide la liste de l'essai AI
                inputToArray.clear();
                for (int i = 0; i < compInput.length(); i++) {
                    //On garde le chiffre donné s'il est bon et à la bonne place
                    if (compInput.charAt(i) == secretCode[i]) {
                        String goodAnswer = ""+ getNumericValue( compInput.charAt(i));
                        inputToArray.add( goodAnswer );
                        System.out.println( goodAnswer );
                    }
                    //TODO écarter les propositions déjà faites
                    //S'il est bon mais pas à la bonne place
                    else if((compInput.charAt(i) != secretCode[i]) && (Arrays.asList( secretCode ).contains( compInput.charAt(i)))){
                        //TODO si chiffre présent mais mal placé: à implémenter (random en attendant)
                        inputToArray.add((int) (Math.random() * 10));
                    }
                    //Si non trouvé
                    else{
                        inputToArray.add((int) (Math.random() * 10));
                    }
                }
                compInput = myTrimString(inputToArray.toString());
            }
            //Si c'est le premier essai, on lance des randoms
            else {
                for (int i = 0; i < secretCode.length; i++) {
                    inputToArray.add( (int) (Math.random() * 10) );
                    compInput = myTrimString( inputToArray.toString() );
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //vérification réponse/code
            verifReponse = tryOutCheckMastermindGame(inputToArray, secretCode, compInput );
            counter++;
        }while(!verifReponse.equals(winwin) && counter < max);
        if(verifReponse.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison!" );
            logger.info( "combinaison trouvée." );
        }
        else {
            System.out.println( "\nL'ordinateur n'a pas trouvé votre combinaison!" );
            logger.info( "combinaison non trouvée." );
        }
    }
}
