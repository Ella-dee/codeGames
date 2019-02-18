package com.elodie.jeux.search;

import com.elodie.jeux.Main;
import com.elodie.jeux.utilities.UtilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.utilities.Utils.*;
import static com.elodie.jeux.utilities.UtilsGameMecanics.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>Recherche +/- Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison gagne.</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur) en premier.
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=). Puis les rôles s'inversent.
 *<p>Le joueur et l'ordinateur doivent deviner la combinaison secrète en un nombre limité d'essais.
 *<p>(Combinaison secrète : 1234)</p>
 *<p>(Proposition : 4278 -&#155; Réponse : -=--)</p>
 *<p>(Proposition : 2214 -&#155; Réponse : -=+=)</p>
 * @author elojito
 * @version 1.0
 */

public class SearchDuel {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir pour l'utilisateur</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir pour l'AI</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un booléen pour les exceptions</li>
     * <li>un compteur d'essais pouyr l'utilisateur</li>
     * <li>un compteur d'essais pour l'ordinateur</li>
     * <li>un appel pour le logger/li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String compInput = "";
    static String verifReponseUser = "";
    static String verifReponseAI = "";
    static final String winwin = "====";
    boolean catched = false;

    private static final Logger logger = LogManager.getLogger( Main.class);

    /**
     * Méthode comprend la mécanique du jeu pour le Mode Duel (ustilisateur et AI à chaque tour).
     * <p>On créée une combinaison secrète.</p>
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * <p>On vérifie si on est en mode développeur ou non, si c'est le cas on affiche le code secret à trouver.</p>
     * <p>On charge le nombre d'essais maximal à ne pas dépasser.</p>
     * <p>Tour de l'utilisateur:</p>
     * <p>Puis c'est au tour de l'ordinateur de jouer:
     * <p>On demande à l'AI d'entrer une combinaison de chiffres, on compare à la combinaison
     * secrète puis affiche les indices "+", "-", ou "="</p>
     * <p>Si l'utilisateur ou l'ordinateur trouve la bonne combinaison alors apparait "====", la partie s'arrête.
     * @see UtilsGameMecanics#stopOuEncore()
     * @see UtilsGameMecanics#computedSecretCode()
     * @see UtilsGameMecanics#inputSecretCode()
     * @see UtilsGameMecanics#modeDevOrNot()
     * @see UtilsGameMecanics#maxTries()
     * @see UtilsGameMecanics#playerTurnSearchGame(String, ArrayList, int[])
     * @see UtilsGameMecanics#tryOutCheckSearchGame(ArrayList, int[], String)
     */

    public SearchDuel() {

        int counterUser = 0;
        int counterAI = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Recherche +/- Mode DUEL." );
        System.out.println( "-----------------------------------------" );
        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        //génération du code secret
        int[] secretCodeForUser = computedSecretCode();
        int[] secretCodeForAI = inputSecretCode();
        //affichage du code secret pour mode développeur
        if(showModeDevOn()==true) {
            System.out.println(showSecretCode(secretCodeForUser));
        }
        int max = maxTries();

        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList userInputToArray = new ArrayList();
        ArrayList tried = new ArrayList();
        do{
            //Tour de l'utilisateur
            verifReponseUser = playerTurnSearchGame(userInput, userInputToArray, secretCodeForUser );
            counterUser++;
            if(!(verifReponseUser.equals( winwin ))) {

                //Tour de l'ordinateur
                ArrayList compInputToArray = new ArrayList();
                System.out.println( "\nProposition de l'ordinateur:" );
                //Si des essais ont déjà été faits par l'AI:
                if (counterAI!=0) {
                    do {
                        compInputToArray.clear(); // on vide la liste de l'essai AI
                        for (int i = 0; i < verifReponseAI.length(); i++) {
                            //On garde le chiffre donné s'il est bon
                            if (verifReponseAI.charAt(i) == '=') {
                                String ok = "" + getNumericValue(compInput.charAt(i));
                                compInputToArray.add(ok);
                            }
                            //S'il est supérieur à celui du code secret, on lance un random avec en entier max ce chiffre essai
                            else if (verifReponseAI.charAt(i) == '-') {
                                int minus = getNumericValue(compInput.charAt(i));
                                minus = randomInRange(-1, minus);
                                compInputToArray.add(minus);
                            }
                            //S'il est inférieur à celui du code secret, on lance un random avec en entier min ce chiffre essai
                            else if (verifReponseAI.charAt(i) == '+') {
                                int plus = getNumericValue(compInput.charAt(i));
                                plus = randomInRange(plus - 1, nbr.length - 1);
                                compInputToArray.add(plus);
                            }
                        }
                        compInput = myTrimString(compInputToArray.toString());
                    }while (tried.contains(compInput)) ;
                    tried.add(compInput);
                }
                //Si c'est le premier essai, on lance des randoms
                else {
                    for(int i = 0; i<secretCodeForAI.length;i++) {
                        compInputToArray.add( (int) (Math.random() * 10));
                    }
                    compInput = myTrimString( compInputToArray.toString() );
                    tried.add(compInput);
                }

                try {
                    Thread.sleep( 2000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //vérification réponse/code
                verifReponseAI = tryOutCheckSearchGame( compInputToArray, secretCodeForAI, compInput );
                counterAI++;
            }
        }while(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin )&& counterAI <max && counterUser <max);
        if(verifReponseUser.equals( winwin )) {
            System.out.println( "\nBravo vous avez trouvé la combinaison !" );
            logger.info( "combinaison trouvée." );
        }
        else if(verifReponseAI.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison !" );
            logger.info( "combinaison trouvée." );
            System.out.println( "La combinaison de l'ordinateur était: "+ secretCodeForUser );
        }
        else if(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin )){
            System.out.println( "\nAucune combinaison trouvée. Le code de l'ordinateur était : " + showSecretCode( secretCodeForUser ));
            logger.info( "combinaison non trouvée." );
        }
    }
}
