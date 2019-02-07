package com.elodie.jeux.mastermind;

import com.elodie.jeux.Main;
import com.elodie.jeux.utilities.UtilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

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
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * <p>On charge le nombre d'essais maximal à ne pas dépasser.</p>
     * <p>On lance le jeu</p>
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On vérifie qu'un chiffre n'a pas été déjà proposé à un emplacement donné</p>
     * <p>On vérifie qu'une combinais on n'ai pas été déjà proposée</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * <p>Si l'ordinateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     * @see UtilsGameMecanics#tryOutCheckMastermindGame(ArrayList, int[], String)
     * @see UtilsGameMecanics#maxTries()
     * @see UtilsGameMecanics#inputSecretCode()
     */
    public MastermindDefenseur(){
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans mastermind Mode Défenseur." );
        System.out.println( "-----------------------------------------" );
        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        int counter = 0;
        //génération du code secret
        int[] secretCode = inputSecretCode();
        int max = maxTries();
        ArrayList combinaisonEssayees = new ArrayList();
        ArrayList inputToArray = new ArrayList();
        ArrayList defNot = new ArrayList();
        ArrayList maybeNot = new ArrayList();
        ArrayList maybe = new ArrayList();
        ArrayList chiffreEssaye = new ArrayList();
        int maybeNbr = 0;
        int indexOfMaybeToTry = 0;
        int otherNbr = 0;
        //On lance le jeu
        do {
            //Si des essais ont déjà été faits par l'AI:
            if(counter!=0) {
                // do  while (combinaisonEssayees.contains(compInput));
                do {
                    //Si aucun chiffre n'est trouvé on les ajoutes à liste defnot pour ne pas les reproposer
                    if (Objects.equals(verifReponse, "aucun chiffre trouvé")) {
                        noNumbersFoundAtAll(secretCode, compInput, defNot);
                    }

                    for (int i = 0; i < secretCode.length; i++) {
                        int chiffre = getNumericValue((compInput.charAt(i)));
                        int chiffreSoluce = secretCode[i];

                        //On garde le chiffre donné s'il est bon et à la bonne place
                        if (chiffre == chiffreSoluce) {
                            numberFoundRightPlace(chiffre, inputToArray, i);
                        }
                        //Si le chiffre est présent, on l'ajoute à la liste maybe et à la liste de combi chiffreEssayé
                        else if (appearsinArray(chiffre, secretCode) && chiffre != chiffreSoluce) {
                            numberFoundElsewhere(maybe, chiffre, chiffreEssaye, i, inputToArray, defNot );
                        }
                        //Sinon on essaye un chiffre de la liste maybe si elle n'est pas vide et si le chiffre n'a pas déjà été proposé
                        else {
                            numberNotFound(chiffreEssaye, i, chiffre, maybe, inputToArray, defNot, maybeNot);
                        }
                    }
                    compInput = myTrimString(inputToArray.toString());
                } while (combinaisonEssayees.contains(compInput));
                combinaisonEssayees.add(compInput);
            }

            //Si c'est le premier essai, on lance des randoms
            else {
                for (int i = 0; i < secretCode.length; i++) {
                    inputToArray.add( (int) (Math.random() * 10) );
                    compInput = myTrimString( inputToArray.toString() );
                }
                combinaisonEssayees.add(compInput);
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
