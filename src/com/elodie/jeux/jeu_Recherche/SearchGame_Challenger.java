package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.Exceptions.ExceptionNaN;
import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;

/**
 * <b>Recherche +/- Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur</b>
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
 * @author elojito
 * @version 1.0
 */

public class SearchGame_Challenger {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide représentant la liste ci dessus</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un booléen pour les exceptions</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String reponseToString = "";
    static final String winwin = "====";
    boolean catched = false;


    /**
     * Méthode comprend la mécanique du jeu pour le Mode Challenger (utilisateur VS AI).
     * <p>On créée une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckSearchGame(ArrayList, int[], String)
     * <p>Si l'utilisateur trouve alors apparait "====", la partie s'arrête,
     * on demande à l'utilisateur s'il veut rejouer.
     * @see Methodes_MecaniqueJeu#stopOuEncore()
     */

    public SearchGame_Challenger() {
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
                reponseToString = tryOutCheckSearchGame(inputToArray, secretCode, userInput);
            }while(!(reponseToString.equals( winwin )));
            System.out.println( "\nBravo vous avez trouvé la combinaison: !" );
        }
    }
