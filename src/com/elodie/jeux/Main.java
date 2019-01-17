package com.elodie.jeux;

import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import com.elodie.jeux.jeu_Mastermind.MastermindGame_Challenger;
import com.elodie.jeux.jeu_Mastermind.MastermindGame_Defenseur;
import com.elodie.jeux.jeu_Mastermind.MastermindGame_Duel;
import com.elodie.jeux.jeu_Recherche.SearchGame_Challenger;
import com.elodie.jeux.jeu_Recherche.SearchGame_Defenseur;
import com.elodie.jeux.jeu_Recherche.SearchGame_Duel;

import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;

/**
 * <b>Développement en mode console d'une application proposant des jeux de logique. </b>
 * <p>Chaque jeu pourra être joué selon 3 modes :
 *<ul>
 *  <li>Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur</li>
 *  <li>Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète</li>
 *  <li>Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison
 *  secrète de l'autre a gagné</li>
 *</ul>
 * <p> Au démarrage, l'utilisateur doit choisir le jeu auquel il veut jouer parmi les choix proposés.
 *L'utilisateur sélectionne le jeu et le mode de son choix. L'application lance le jeu sélectionné.
 *L'utilisateur joue. S'il perd, l'application affiche la solution.
 * <ul>À la fin de la partie, l'utilisateur peut choisir :
 *<li>de rejouer au même jeu</li>
 * @see Methodes_MecaniqueJeu#stopOuEncore()
 * <li>de lancer un autre jeu (retour à l'écran de choix des jeux du début)</li>
 * @see Methodes_MecaniqueJeu#backToMenu()
 * <li>de quitter l'application</li>
 *</ul>
 * <p>Il doit être possible de lancer l'application dans un mode "développeur".
 * Dans ce mode la solution est affichée dès le début. Cela permet de tester le bon comportement
 * de l'application en cas de bonne ou de mauvaise réponse de l'utilisateur.
 * <ul>Ceci est à réaliser avec les mécanismes suivants :
 * <li>Passage d'un paramètre au lancement de l'application</li>
 * <li>Propriété spécifique dans le fichier de configuration</li>
 *</ul>
 *<p>Un fichier de configuration (config.properties) permettra de paramétrer l'application, notamment :
 *<ul>Pour chaque jeu :
 *<li>le nombre de cases de la combinaison secrète</li>
 *<li>le nombre d'essais possibles</li>
 *</ul>
 *<ul>Pour le Mastermind :
 *<li>le nombre couleur/chiffre utilisables (de 4 à 10)</li>
 *</ul>
 *<p>Un fichier de configuration (log4j.xml) permettra de paramétrer les logs de l'application. La gestion des logs se fera avec Apache Log4j.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println( "Bienvenu-e-s!" );
        System.out.println( "-------------------------------------" );

        int game = 0;
        int mode = 0;
        String chooseAgain ="";

        do {
            game = menuJeu();
            //TODO gérer les modes avec le package Modes
            String playAgain = "";
            switch (game) {
                //Case 1 = Jeu Recherche
                //Case 2 = Jeu Mastermind
                case 1:
                    mode = chooseModeForGame();
                    //Mode 1 = Challenger
                    //Mode 2 = Defenseur
                    //Mode 3 = Duel
                    if (mode == 1) {
                        do {
                            SearchGame_Challenger searchgame = new SearchGame_Challenger();
                            playAgain = stopOuEncore();
                        } while (playAgain.equals( "O" ));
                    } else if (mode == 2) {
                        do {
                            SearchGame_Defenseur searchgame = new SearchGame_Defenseur();
                            playAgain = stopOuEncore();
                        } while (playAgain.equals( "O" ));
                    }else if (mode == 3) {
                        do {
                            SearchGame_Duel searchgame = new SearchGame_Duel();
                            playAgain = stopOuEncore();
                        } while (playAgain.equals( "O" ));
                    }
                    break;
                //Jeu Mastermind
                case 2:
                    mode = chooseModeForGame();
                    if (mode == 1) {
                        do {
                            MastermindGame_Challenger mastermind = new MastermindGame_Challenger();
                            playAgain = stopOuEncore();
                        } while (playAgain.equals( "O" ));
                    } else if (mode == 2) {
                        do {
                            MastermindGame_Defenseur mastermindGame = new MastermindGame_Defenseur();
                            playAgain = stopOuEncore();
                        } while (playAgain.equals( "O" ));
                    }
                        else if (mode == 3) {
                        do {
                            MastermindGame_Duel mastermindGame = new MastermindGame_Duel();
                            playAgain = stopOuEncore();
                        } while (playAgain.equals( "O" ));
                    }
                    break;
                default:
                    System.out.println( "Nous n'avons pas compris votre choix." );
            }
            chooseAgain = backToMenu();
        } while (chooseAgain.equals( "O" ));
        System.out.println( "A bientôt!!!" );
    }
}
