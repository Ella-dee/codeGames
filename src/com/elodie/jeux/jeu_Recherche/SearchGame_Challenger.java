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
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un booléen pour les exceptions</li>
     * <li>un compteur d'essais</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String verifReponse = "";
    static final String winwin = "====";
    static int counter = 0;


    /**
     * Méthode comprend la mécanique du jeu pour le Mode Challenger (utilisateur VS AI).
     * <p>On créée une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <p>Tour de jeu de l'utilisateur.</p>
     * @see Methodes_MecaniqueJeu#playerTurnSearchGame(String, ArrayList, int[])
     * <p>Si l'utilisateur trouve alors apparait "====", la partie s'arrête.</p>
     * <p>Si l'utilisateur ne trouve pas en moins de 6 essais, la partie s'arrête et on affiche la solution.</p>
     * @see Methodes_MecaniqueJeu#showSecretCode(int[])
     */

    public SearchGame_Challenger() {
            int[] secretCode = computedSecretCode();
            ArrayList inputToArray = new ArrayList();

            //affichage du code secret pour mode développeur
            System.out.println(showSecretCode( secretCode ));
            do{
                verifReponse = playerTurnSearchGame( userInput, inputToArray, secretCode );
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
