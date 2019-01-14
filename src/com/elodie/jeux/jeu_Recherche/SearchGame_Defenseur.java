package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;
import static com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>Recherche +/- // Mode Défenseur: l'ordinateur de trouver votre combinaison secrète</b>
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

public class SearchGame_Defenseur {
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>tableau de 4 chiffres comportant la combinaison secrète</li>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une liste vide représentant les indices "+-=+" à venir</li>
     * <li>une chaine de caractère vide représentant la liste ci dessus</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * @see SearchGame_Defenseur#startDefenseurSearchGame()
     * <p>Si l'utilisateur trouve alors apparait "====", le jeu s'arrête</p>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    public static final int[] secretCode = Methodes_MecaniqueJeu.inputSecretCode();
    static String AIinput = "";
    static ArrayList output = new ArrayList();
    static String reponseToString = "";
    final String winwin = "====";

    public SearchGame_Defenseur(){
        //affichage du code secret pour mode développeur
        showSecretCode( secretCode );
        //On lance le jeu
        do {
            startDefenseurSearchGame();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nL'ordinateur  trouvé votre combinaison!" );
    }

    /**
     * Méthode comprend la mécanique du jeu pour le Mode Defenseur (AI VS utilisateur).
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckSearchGame(ArrayList, int[], String)
     * <p>Contient les attributs suivants:
     * <ul>
     *     <li>liste contenant l'essai de l'AI</li>
     *     <li>4 entiers random pour l'essai de l'AI</li>
     * </ul>
     */
    public static void startDefenseurSearchGame(){
        ArrayList inputToArray = new ArrayList();
        int first = (int) (Math.random() * 10);
        int second = (int) (Math.random() * 10);
        int third = (int) (Math.random() * 10);
        int fourth = (int) (Math.random() * 10);

        System.out.println( "Proposition de l'ordinateur." );
        //Si des essais ont déjà été faits par l'AI:
        if(!reponseToString.isEmpty()){
            inputToArray.clear(); // on vide la liste de l'essai AI
            for (int i = 0; i < reponseToString.length(); i++) {
                //On garde le chiffre donné s'il est bon
                if (reponseToString.charAt(i) == '=') {
                    String ok = ""+ getNumericValue(AIinput.charAt(i));
                    inputToArray.add( ok );
                }
                //TODO écarter les propositions déjà faites
                //S'il est supérieur à celui du code secret, on lance un random avec en entier max ce chiffre essai
                else if(reponseToString.charAt(i) == '-'){
                    int minus = getNumericValue(AIinput.charAt(i));
                    minus = randomInRange( -1, minus);
                    inputToArray.add( minus );
                }
                //S'il est inférieur à celui du code secret, on lance un random avec en entier min ce chiffre essai
                else if(reponseToString.charAt(i) == '+'){
                    int plus = getNumericValue(AIinput.charAt(i));
                    plus = randomInRange( plus-1, nbr.length-1);
                    inputToArray.add( plus );
                }
            }
            AIinput = myTrimString(inputToArray.toString());
        }

        //Si c'est le premier essai, on lance 4 randoms
        else{
            inputToArray.add( first );
            inputToArray.add( second );
            inputToArray.add( third );
            inputToArray.add( fourth );
            AIinput = myTrimString( inputToArray.toString() );
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //vérification réponse/code
        reponseToString = tryOutCheckSearchGame(inputToArray, secretCode, AIinput);
    }
}
