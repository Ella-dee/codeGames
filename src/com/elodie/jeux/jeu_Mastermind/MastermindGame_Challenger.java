package com.elodie.jeux.jeu_Mastermind;

import com.elodie.jeux.Exceptions.ExceptionNaN;
import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;

/**
 * <b>Mastermind // Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur</b>
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

public class MastermindGame_Challenger {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés, x présents" à venir</li>
     * <li>une chaine de caractère "4 bien placés" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un compteur d'essais</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String verifReponse = "";
    final String winwin = "4 bien placés";
    static int counter = 0;

    public MastermindGame_Challenger(){
    /**
     * Méthode comprend la mécanique du jeu.
     * <p>On créée une combinaison secrète.</p>
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche si les chiffres sont bien placés ou au moins présents.</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'utilisateur trouve alors apparait "4 bien placés", la partie s'arrête.</p>
     * <p>Si l'utilisateur ne trouve pas en moins de 6 essais, la partie s'arrête et on affiche la solution.</p>
     * @see Methodes_MecaniqueJeu#showSecretCode(int[])
     */
        int[] secretCode = computedSecretCode();
        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList inputToArray = new ArrayList();
        //affichage du code secret pour mode développeur
        System.out.println(showSecretCode( secretCode ));
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
                }
                finally {
                    if ( inputToArray.size() > secretCode.length || inputToArray.size() < secretCode.length) {
                        System.out.print( "Vous devez saisir une combinaison à " + secretCode.length + " chiffres." );
                        catched = true;
                    }
                }
            }while (catched);
            //vérification réponse/code
            verifReponse = tryOutCheckMastermindGame(inputToArray, secretCode, userInput);
            counter++;
        }while(!(verifReponse.equals( winwin )) && counter < 6);
        if(verifReponse.equals( winwin )){
            System.out.println( "\nBravo vous avez trouvé la combinaison: !" );
        }
        else{
            System.out.println( "Vous n'avez pas trouvé la combinaison. La réponse était : " + showSecretCode( secretCode ));
        }
    }
}

