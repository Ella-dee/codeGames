package com.elodie.jeux.search;

import com.elodie.jeux.Main;
import com.elodie.jeux.utilities.UtilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import static com.elodie.jeux.utilities.Utils.*;
import static com.elodie.jeux.utilities.UtilsGameMecanics.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>Recherche +/- - Mode Défenseur</b>
 * <p>Le but : l'ordinateur doit découvrir la combinaison à x chiffres de l'adversaire
 * (le défenseur, soit ici l'utilisateur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=).
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<p>(Combinaison secrète : 1234))</p>
 *<p>(Proposition : 4278 -&#155; Réponse : -=--)</p>
 *<p>(Proposition : 2214 -&#155; Réponse : -=+=)</p>
 * @author elojito
 * @version 1.0
 */

public class SearchDefenseur {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir</li>
     * <li>une chaine de caractère représentant "====" soit l'affichage de sortie pour une combinaison gagnante. </li>
     * <li>un compteur d'essais</li>
     *  <li>un appel pour le logger/li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String compInput = "";
    static String verifReponseComp = "";
    final String winwin = "====";
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
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see UtilsGameMecanics#tryOutCheckSearchGame(ArrayList, int[], String)
     * <p>Si l'ordinateur trouve alors apparait "====", la partie s'arrête.
     * <p>Si l'ordinateur ne trouve pas en moins de 6 essais, la partie s'arrête.</p>
     * @see UtilsGameMecanics#showSecretCode(int[])
     */
    public SearchDefenseur(){
        int counter = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Recherche +/- Mode Défenseur." );
        System.out.println( "-----------------------------------------" );
        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCode = inputSecretCode();
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();
        ArrayList compInputToArray = new ArrayList(  );
        //On lance le jeu
        do{
            //Si des essais ont déjà été faits par l'AI:
            if(counter!=0){
                compInputToArray.clear(); // on vide la liste de l'essai AI
                for (int i = 0; i < verifReponseComp.length(); i++) {
                    //On garde le chiffre donné s'il est bon
                    if (verifReponseComp.charAt(i) == '=') {
                        String ok = ""+ getNumericValue( compInput.charAt(i));
                        compInputToArray.add( ok );
                    }
                    //TODO écarter les propositions déjà faites
                    //S'il est supérieur à celui du code secret, on lance un random avec en entier max ce chiffre essai
                    else if(verifReponseComp.charAt(i) == '-'){
                        int minus = getNumericValue( compInput.charAt(i));
                        minus = randomInRange( -1, minus);
                        compInputToArray.add( minus );
                    }
                    //S'il est inférieur à celui du code secret, on lance un random avec en entier min ce chiffre essai
                    else if(verifReponseComp.charAt(i) == '+'){
                        int plus = getNumericValue( compInput.charAt(i));
                        plus = randomInRange( plus-1, nbr.length-1);
                        compInputToArray.add( plus );
                    }
                }
                compInput = myTrimString(compInputToArray.toString());
            }

            //Si c'est le premier essai, on lance des randoms
            else{
                for(int i = 0; i<secretCode.length;i++) {
                    compInputToArray.add( (int) (Math.random() * 10));
                }
                compInput = myTrimString( compInputToArray.toString() );
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //vérification réponse/code
            verifReponseComp = tryOutCheckSearchGame(compInputToArray, secretCode, compInput );
            counter++;
        }while(!verifReponseComp.equals( winwin ) && counter < max);
        if(verifReponseComp.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison!" );
            logger.info( "combinaison trouvée." );
        }
        else {
            System.out.println( "\nL'ordinateur n'a pas trouvé votre combinaison!" );
            logger.info( "combinaison non trouvée." );
        }
    }
}

