package com.elodie.jeux;

import com.elodie.jeux.jeu_Mastermind.MastermindGame_Challenger;
import com.elodie.jeux.jeu_Mastermind.MastermindGame_Defenseur;
import com.elodie.jeux.jeu_Recherche.SearchGame_Challenger;
import com.elodie.jeux.jeu_Recherche.SearchGame_Defenseur;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;

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
                //Jeu Recherche
                case 1:
                    mode = chooseModeForGame();
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
                    break;
                default:
                    System.out.println( "Nous n'avons pas compris votre choix." );
            }
            chooseAgain = backToMenu();
        } while (chooseAgain.equals( "O" ));
        System.out.println( "A bientôt!!!" );
    }
}
