package com.elodie.jeux.jeu_Mastermind;

import com.elodie.jeux.Exceptions.ExceptionNaN;
import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>Mastermind //Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison gagne.</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour
 * chaque proposition le nombre de chiffres de la proposition qui apparaissent
 * à la bonne place et à la mauvaise place dans la combinaison secrète.
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais. Puis les rôles s'inversent.
 *<p>Le joueur et l'ordinateur doivent deviner la combinaison secrète en un nombre limité d'essais.
 *<section>
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -> Réponse : 1 présent, 1 bien placé</i></p>
 *<p><i>(Proposition : 2214 -> Réponse : 2 bien placés</i></p>
 *</section>
 * @author elojito
 * @version 1.0
 */

public class MastermindGame_Duel {
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
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String AIinput = "";
    static String verifReponseUser = "";
    static String verifReponseAI = "";
    final String winwin = "4 bien placés";
    static int counterUser = 0;
    static int counterAI = 0;
    /**
     * Méthode comprend la mécanique du jeu.
     * <p>On créée une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#computedSecretCode(int)
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche si les chiffres sont bien placés ou au moins présents.</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Puis c'est au tour de l'ordinateur de jouer:
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#inputSecretCode(int)
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckMastermindGame(ArrayList, int[], String)
     * <p>Si l'utilisateur ou l'ordinateur trouve la bonne combinaison alors apparait "4 bien placés", la partie s'arrête.
     * @see Methodes_MecaniqueJeu#stopOuEncore()
     **/
    public MastermindGame_Duel(){

        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Mastermind Mode DUEL." );
        System.out.println( "-----------------------------------------" );

        Scanner sc = new Scanner( System.in );
        int cases = chooseCodeLenght();
        int[] secretCodeForUser = computedSecretCode(cases);
        int[] secretCodeForAI = inputSecretCode(cases);
        boolean catched;
        ArrayList userInputToArray = new ArrayList();
        //affichage du code secret pour mode développeur
        System.out.println("code que l'utilisateur doit trouver: "+showSecretCode( secretCodeForUser ));
        System.out.println("code que l'ordinateur doit trouver: "+showSecretCode( secretCodeForAI ));

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
                }
                finally {
                    if ( userInputToArray.size() > secretCodeForUser.length || userInputToArray.size() < secretCodeForUser.length) {
                        System.out.print( "Vous devez saisir une combinaison à " + secretCodeForUser.length + " chiffres." );
                        catched = true;
                    }
                }
            }while (catched);
            //vérification réponse/code
            verifReponseUser = tryOutCheckMastermindGame(userInputToArray, secretCodeForUser, userInput);
            counterUser++;
            if(!verifReponseUser.equals( winwin )) {
                //Tour de l'ordinateur
                ArrayList AIinputToArray = new ArrayList();
                System.out.println( "\nProposition de l'ordinateur:" );
                //Si des essais ont déjà été faits par l'AI:
                if (!verifReponseAI.isEmpty()) {
                    // on vide la liste de l'essai AI
                    AIinputToArray.clear();
                    for (int i = 0; i < AIinput.length(); i++) {
                        //On garde le chiffre donné s'il est bon et à la bonne place
                        if (AIinput.charAt( i ) == secretCodeForAI[i]) {
                            String ok = "" + getNumericValue( AIinput.charAt( i ) );
                            AIinputToArray.add( ok );
                            System.out.println( ok );
                        }
                        //TODO écarter les propositions déjà faites
                        //S'il est bon mais pas à la bonne place
                        else if ((AIinput.charAt( i ) != secretCodeForAI[i]) && (Arrays.asList( secretCodeForAI ).contains( AIinput.charAt( i ) ))) {
                            //TODO à implémenter (random en attendant)
                            AIinputToArray.add( (int) (Math.random() * 10) );
                        }
                        //Si non trouvé
                        else {
                            AIinputToArray.add( (int) (Math.random() * 10) );
                        }
                    }
                    AIinput = myTrimString( AIinputToArray.toString() );
                }
                //Si c'est le premier essai, on lance des randoms
                else {
                    for(int i = 0; i<secretCodeForAI.length;i++) {
                        AIinputToArray.add( (int) (Math.random() * 10));
                    }
                    AIinput = myTrimString( AIinputToArray.toString() );
                }
                try {
                    Thread.sleep( 2000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //vérification réponse/code
                verifReponseAI = tryOutCheckMastermindGame( AIinputToArray, secretCodeForAI, AIinput );
                counterAI++;
            }
        }while(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin ) && counterAI <6 && counterUser <6);
        if(verifReponseUser.equals( winwin )) {
            System.out.println( "\nBravo vous avez trouvé la combinaison: !" );
        }
        else if(verifReponseAI.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison: !" );
            System.out.println( "La combinaison de l'ordinateur était: "+ secretCodeForUser );
        }
        else if(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin )){
            System.out.println( "\nAucune combinaison trouvée. Le code de l'ordinateur était : " + showSecretCode( secretCodeForUser ));
        }
    }
}

