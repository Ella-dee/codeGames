package com.elodie.jeux.mastermind;

import com.elodie.jeux.Main;
import com.elodie.jeux.exceptions.ExceptionNaN;
import com.elodie.jeux.utilities.UtilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.utilities.Utils.*;
import static com.elodie.jeux.utilities.UtilsGameMecanics.*;

/**
 * <b>Mastermind - Mode challenger</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur soit ici l'ordinateur).
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

public class MastermindChallenger {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés, x présents" à venir</li>
     * <li>une chaine de caractère "4 bien placés" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un compteur d'essais</li>
     * <li>un appel pour le logger/li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String verifReponse = "";
    final String winwin = "4 bien placés";
    private static final Logger logger = LogManager.getLogger( Main.class);
    /**
     * Méthode comprend la mécanique du jeu.
     * <p>On demande à l'utilisateur la longueur du code avec laquelle il souhaite jouer.</p>
     * @see UtilsGameMecanics#chooseCodeLenght()
     * <p>On créée une combinaison secrète.</p>
     * @see UtilsGameMecanics#computedSecretCode()
     * <p>On vérifie si on est en mode développeur ou non, si c'est le cas on affiche le code secret à trouver.</p>
     * @see UtilsGameMecanics#modeDevOrNot()
     * <p>On demande à l'utilisateur le nombre d'essais maximal à ne pas dépasser.</p>
     * @see UtilsGameMecanics#maxTries()
     * <p>Tour de jeu de l'utilisateur.</p>
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche si les chiffres sont bien placés ou au moins présents.</p>
     * @see UtilsGameMecanics#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'utilisateur trouve alors apparait "4 bien placés", la partie s'arrête.</p>
     * <p>Si l'utilisateur ne trouve pas en moins de 6 essais, la partie s'arrête et on affiche la solution.</p>
     * @see UtilsGameMecanics#showSecretCode(int[])
     **/
    public MastermindChallenger(){
        int counter = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans mastermind Mode Challenger." );
        System.out.println( "-----------------------------------------" );
        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCode = computedSecretCode();
        //affichage du code secret pour mode développeur
        if(modeDevOrNot()==true) {
            System.out.println( showSecretCode( secretCode ) );
        }
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();

        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList inputToArray = new ArrayList();
        do{
            do{
                try{
                    catched = false;
                    System.out.println( "\nQuelle est votre proposition?" );
                    userInput = sc.nextLine();
                    inputToArray = createArrayListeFromInput( userInput );
                    if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                        throw new ExceptionNaN();
                    }
                } catch (ExceptionNaN e) {
                    catched = true;
                    logger.error( "NotANumber catched = "+ userInput);
                }
                finally {
                    if ( inputToArray.size() > secretCode.length || inputToArray.size() < secretCode.length) {
                        System.out.print( "Vous devez saisir une combinaison à " + secretCode.length + " chiffres." );
                        catched = true;
                        logger.error("Bad size catched = "+userInput.length()+", expected: "+secretCode.length  );
                    }
                }
            }while (catched);
            //vérification réponse/code
            verifReponse = tryOutCheckMastermindGame(inputToArray, secretCode, userInput);
            counter++;
        }while(!(verifReponse.equals( winwin )) && counter < 6);
        if(verifReponse.equals( winwin )){
            System.out.println( "\nBravo vous avez trouvé la combinaison !" );
            logger.info( "combinaison trouvée." );
        }
        else{
            System.out.println( "\nVous n'avez pas trouvé la combinaison. La réponse était : " + showSecretCode( secretCode ));
            logger.info( "combinaison non trouvée." );
        }
    }
}

