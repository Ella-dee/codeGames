package com.elodie.jeux.search;

import com.elodie.jeux.utilities.utilsGameMecanics;
import java.util.ArrayList;
import static com.elodie.jeux.utilities.utils.*;
import static com.elodie.jeux.utilities.utilsGameMecanics.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>Recherche +/- // Mode Défenseur: l'ordinateur de trouver votre combinaison secrète</b>
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

public class searchDefenseur {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir</li>
     * <li>une chaine de caractère représentant "====" soit l'affichage de sortie pour une combinaison gagnante. </li>
     * <li>un compteur d'essais</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String AIinput = "";
    static String verifReponseComp = "";
    final String winwin = "====";

    /**
     * <b>Méthode comprend la mécanique du jeu pour le Mode Défenseur (AI vs utilisateur)</b>
     * <p>On demande à l'utilisateur la longueur du code avec laquelle il souhaite jouer.</p>
     * @see utilsGameMecanics#chooseCodeLenght()
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * @see utilsGameMecanics#inputSecretCode()
     * <p>On demande à l'utilisateur le nombre d'essais maximal à ne pas dépasser.</p>
     * @see utilsGameMecanics#maxTries()
     * <p>On lance le jeu</p>
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see utilsGameMecanics#tryOutCheckSearchGame(ArrayList, int[], String)
     * <p>Si l'ordinateur trouve alors apparait "====", la partie s'arrête.
     * <p>Si l'ordinateur ne trouve pas en moins de 6 essais, la partie s'arrête.</p>
     * @see utilsGameMecanics#showSecretCode(int[])
     */
    public searchDefenseur(){
        int counter = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Recherche +/- Mode Défenseur." );
        System.out.println( "-----------------------------------------" );
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCode = inputSecretCode();
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();
        ArrayList AIinputToArray = new ArrayList(  );
        //On lance le jeu
        do{
            //Si des essais ont déjà été faits par l'AI:
            if(counter!=0){
                AIinputToArray.clear(); // on vide la liste de l'essai AI
                for (int i = 0; i < verifReponseComp.length(); i++) {
                    //On garde le chiffre donné s'il est bon
                    if (verifReponseComp.charAt(i) == '=') {
                        String ok = ""+ getNumericValue(AIinput.charAt(i));
                        AIinputToArray.add( ok );
                    }
                    //TODO écarter les propositions déjà faites
                    //S'il est supérieur à celui du code secret, on lance un random avec en entier max ce chiffre essai
                    else if(verifReponseComp.charAt(i) == '-'){
                        int minus = getNumericValue(AIinput.charAt(i));
                        minus = randomInRange( -1, minus);
                        AIinputToArray.add( minus );
                    }
                    //S'il est inférieur à celui du code secret, on lance un random avec en entier min ce chiffre essai
                    else if(verifReponseComp.charAt(i) == '+'){
                        int plus = getNumericValue(AIinput.charAt(i));
                        plus = randomInRange( plus-1, nbr.length-1);
                        AIinputToArray.add( plus );
                    }
                }
                AIinput = myTrimString(AIinputToArray.toString());
            }

            //Si c'est le premier essai, on lance des randoms
            else{
                for(int i = 0; i<secretCode.length;i++) {
                    AIinputToArray.add( (int) (Math.random() * 10));
                }
                AIinput = myTrimString( AIinputToArray.toString() );
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //vérification réponse/code
            verifReponseComp = tryOutCheckSearchGame(AIinputToArray, secretCode, AIinput);
            counter++;
        }while(!verifReponseComp.equals( winwin ) && counter < max);
        if(verifReponseComp.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison!" );
        }
        else {
            System.out.println( "\nL'ordinateur n'a pas trouvé votre combinaison!" );
        }
    }
}

