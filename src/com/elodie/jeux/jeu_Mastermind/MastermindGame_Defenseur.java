package com.elodie.jeux.jeu_Mastermind;

import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import java.util.Arrays;

import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;
import static java.lang.Character.getNumericValue;

/**
 *  <b>Mastermind // Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète</b>
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

public class MastermindGame_Defenseur {
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "x bien placés ou présents" à venir</li>
     * <li>une chaine de caractère "4 bien placés" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'utilisateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String AIinput = "";
    static String reponseToString = "";
    final String winwin = "4 bien placés";

    public MastermindGame_Defenseur(){
        int[] secretCode = inputSecretCode();
        ArrayList inputToArray = new ArrayList();
        int first = (int) (Math.random() * 10);
        int second = (int) (Math.random() * 10);
        int third = (int) (Math.random() * 10);
        int fourth = (int) (Math.random() * 10);

        //affichage du code secret pour mode développeur
        showSecretCode( secretCode );
        //On lance le jeu
        do {
            System.out.println( "Proposition de l'ordinateur." );
            //Si des essais ont déjà été faits par l'AI:
            if(!reponseToString.isEmpty()){
                // on vide la liste de l'essai AI
                inputToArray.clear();
                for (int i = 0; i < AIinput.length(); i++) {
                    //On garde le chiffre donné s'il est bon et à la bonne place
                    if (AIinput.charAt(i) == secretCode[i]) {
                        String ok = ""+ getNumericValue(AIinput.charAt(i));
                        inputToArray.add( ok );
                        System.out.println( ok );
                    }
                    //TODO écarter les propositions déjà faites
                    //S'il est bon mais pas à la bonne place
                    else if((AIinput.charAt(i) != secretCode[i]) && (Arrays.asList( secretCode ).contains( AIinput.charAt(i)))){
                        //TODO à implémenter (random en attendant)
                        inputToArray.add((int) (Math.random() * 10));
                    }
                    //Si non trouvé
                    else{
                        inputToArray.add((int) (Math.random() * 10));
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
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //vérification réponse/code
            reponseToString = tryOutCheckMastermindGame(inputToArray, secretCode, AIinput);
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nL'ordinateur  trouvé votre combinaison!" );
    }
}
