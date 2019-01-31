package com.elodie.jeux.mastermind;

import com.elodie.jeux.Main;
import com.elodie.jeux.exceptions.ExceptionNaN;
import com.elodie.jeux.utilities.UtilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.elodie.jeux.utilities.Utils.*;
import static com.elodie.jeux.utilities.UtilsGameMecanics.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>mastermind - Mode duel</b>
 * <p>Le but : l'ordinateur et l'utilisateur jouent tour à tour, le premier à trouver la combinaison gagne.
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour
 * chaque proposition le nombre de chiffres de la proposition qui apparaissent
 * à la bonne place et à la mauvaise place dans la combinaison secrète.
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais. Puis les rôles s'inversent.
 *<p>Le joueur et l'ordinateur doivent deviner la combinaison secrète en un nombre limité d'essais.
 *<p>(Combinaison secrète : 1234))</p>
 *<p>(Proposition : 4278 -&#155; Réponse : 1 présent, 1 bien placé)</p>
 *<p>(Proposition : 2214 -&#155; Réponse : 2 bien placés)</p>
 * @author elojito
 * @version 1.0
 */

public class MastermindDuel {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés, x présents" à venir pour l'utilisateur</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés, x présents" à venir pour l'AI</li>
     * <li>une chaine de caractère "4 bien placés" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un compteur d'essais pouyr l'utilisateur</li>
     * <li>un compteur d'essais pour l'ordinateur</li>
     * <li>un appel pour le logger</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String compInput = "";
    static String verifReponseUser = "";
    static String verifReponseAI = "";
    final String winwin = "4 bien placés";
    private static final Logger logger = LogManager.getLogger( Main.class);
    /**
     * Méthode comprend la mécanique du jeu.
     * <p>On créée une combinaison secrète.</p>
     * @see UtilsGameMecanics#computedSecretCode()
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * @see UtilsGameMecanics#inputSecretCode()
     * <p>On vérifie si on est en mode développeur ou non, si c'est le cas on affiche le code secret à trouver.</p>
     * @see UtilsGameMecanics#modeDevOrNot()
     * <p>On charge le nombre d'essais maximal à ne pas dépasser.</p>
     * @see UtilsGameMecanics#maxTries()
     * <p>Tour de l'utilisateur.</p>
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche si les chiffres sont bien placés ou au moins présents.</p>
     * @see UtilsGameMecanics#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Puis c'est au tour de l'ordinateur de jouer:
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see UtilsGameMecanics#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'utilisateur ou l'ordinateur trouve la bonne combinaison alors apparait "4 bien placés", la partie s'arrête.
     * @see UtilsGameMecanics#stopOuEncore()
     **/
    public MastermindDuel(){
        int counterUser = 0;
        int counterAI = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans mastermind Mode DUEL." );
        System.out.println( "-----------------------------------------" );
        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        //génération du code secret
        int[] secretCodeForUser = computedSecretCode();
        int[] secretCodeForAI = inputSecretCode();
        //affichage du code secret pour mode développeur
        if(modeDevOrNot()==true || Main.mainParam.equals("dev")) {
            System.out.println( showSecretCode(secretCodeForUser ));
        }
        int max = maxTries();
        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList userInputToArray = new ArrayList();

        //Tour de l'utilisateur
        do{
            do{
                try{
                    catched = false;
                    System.out.println( "\nQuelle est votre proposition?" );
                    userInput = sc.nextLine();
                    userInputToArray = createArrayListeFromInput( userInput );
                    if(!checkOccurencesFromListInArray(userInputToArray, nbr)){
                        throw new ExceptionNaN();
                    }
                } catch (ExceptionNaN e) {
                    catched = true;
                    logger.error( "NotANumber catched = "+ userInput);
                }
                finally {
                    if ( userInputToArray.size() > secretCodeForUser.length || userInputToArray.size() < secretCodeForUser.length) {
                        System.out.print( "Vous devez saisir une combinaison à " + secretCodeForUser.length + " chiffres." );
                        catched = true;
                        logger.error("Bad size catched = "+userInput.length()+", expected: "+secretCodeForUser.length  );

                    }
                }
            }while (catched);
            //vérification réponse/code
            verifReponseUser = tryOutCheckMastermindGame(userInputToArray, secretCodeForUser, userInput);
            counterUser++;
            if(!verifReponseUser.equals( winwin )) {
                //Tour de l'ordinateur
                ArrayList compInputToArray = new ArrayList();
                System.out.println( "\nProposition de l'ordinateur:" );
                //Si des essais ont déjà été faits par l'AI:
                if (counterAI!=0) {
                    // on vide la liste de l'essai AI
                    compInputToArray.clear();
                    for (int i = 0; i < compInput.length(); i++) {
                        //On garde le chiffre donné s'il est bon et à la bonne place
                        if (compInput.charAt( i ) == secretCodeForAI[i]) {
                            String goodAnswer = "" + getNumericValue( compInput.charAt( i ) );
                            compInputToArray.add( goodAnswer );
                            System.out.println( goodAnswer );
                        }
                        //TODO écarter les propositions déjà faites
                        //S'il est bon mais pas à la bonne place
                        else if ((compInput.charAt( i ) != secretCodeForAI[i]) && (Arrays.asList( secretCodeForAI ).contains( compInput.charAt( i ) ))) {
                            //TODO à implémenter (random en attendant)
                            compInputToArray.add( (int) (Math.random() * 10) );
                        }
                        //Si non trouvé
                        else {
                            compInputToArray.add( (int) (Math.random() * 10) );
                        }
                    }
                    compInput = myTrimString( compInputToArray.toString() );
                }
                //Si c'est le premier essai, on lance des randoms
                else {
                    for(int i = 0; i<secretCodeForAI.length;i++) {
                        compInputToArray.add( (int) (Math.random() * 10));
                    }
                    compInput = myTrimString( compInputToArray.toString() );
                }
                try {
                    Thread.sleep( 2000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //vérification réponse/code
                verifReponseAI = tryOutCheckMastermindGame( compInputToArray, secretCodeForAI, compInput );
                counterAI++;
            }
        }while(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin ) && counterAI <max && counterUser <max);
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

