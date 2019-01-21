package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import java.util.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;

/**
 * <b>Recherche +/- Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=).
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -&#155; Réponse : -=--</i></p>
 *<p><i>(Proposition : 2214 -&#155; Réponse : -=+=</i></p>
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



    /**
     * Méthode comprend la mécanique du jeu pour le Mode Challenger (utilisateur VS AI).
     * <p>On demande à l'utilisateur la longueur du code avec laquelle il souhaite jouer.</p>
     * @see Methodes_MecaniqueJeu#chooseCodeLenght()
     * <p>On créée une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <p>On vérifie si on est en mode développeur ou non, si c'est le cas on affiche le code secret à trouver.</p>
     * @see Methodes_MecaniqueJeu#modeDevOrNot(int[])
     * <p>On demande à l'utilisateur le nombre d'essais maximal à ne pas dépasser.</p>
     * @see Methodes_MecaniqueJeu#maxTries()
     * <p>Tour de jeu de l'utilisateur.</p>
     * @see Methodes_MecaniqueJeu#playerTurnSearchGame(String, ArrayList, int[])
     * <p>Si l'utilisateur trouve alors apparait "====", la partie s'arrête.</p>
     * <p>Si l'utilisateur ne trouve pas en moins de 6 essais, la partie s'arrête et on affiche la solution.</p>
     * @see Methodes_MecaniqueJeu#showSecretCode(int[])
     */

    public SearchGame_Challenger() {
        int counter = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Recherche +/- Mode Challenger." );
        System.out.println( "-----------------------------------------" );
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCode = computedSecretCode();
        //affichage du code secret pour mode développeur
        modeDevOrNot( secretCode );
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();

        ArrayList inputToArray = new ArrayList();

        do{
            verifReponse = playerTurnSearchGame( userInput, inputToArray, secretCode );
            counter++;
        }while(!(verifReponse.equals( winwin )) && counter < max);
        if(verifReponse.equals( winwin )){
            System.out.println( "\nBravo vous avez trouvé la combinaison: !" );
        }
        else{
            System.out.println( "\nVous n'avez pas trouvé la combinaison. La réponse était : " + showSecretCode( secretCode ));
        }
    }
}
