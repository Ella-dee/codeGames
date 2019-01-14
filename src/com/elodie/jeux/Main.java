package com.elodie.jeux;

import com.elodie.jeux.Exceptions.ExceptionNaL;
import com.elodie.jeux.Exceptions.ExceptionNaN;
import com.elodie.jeux.jeu_Mastermind.MastermindGame_Challenger;
import com.elodie.jeux.jeu_Mastermind.MastermindGame_Defenseur;
import com.elodie.jeux.jeu_Recherche.SearchGame_Challenger;
import com.elodie.jeux.jeu_Recherche.SearchGame_Defenseur;
import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;
import static com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu.chooseModeForGame;
import static com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu.stopOuEncore;

public class Main {

    public static void main(String[] args) {

        String chosenGame = "";
        int chosenGameParsed = 0;
        int chosenModeParsed = 0;
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        ArrayList inputToArray = new ArrayList();

        boolean catched;

        System.out.println( "Bienvenu-e-s!" );
        System.out.println( "-------------------------------------" );
        Scanner sc = new Scanner( System.in );
        do{
            try{
                catched = false;
                System.out.println( "Veuillez choisir votre jeu: 1 - Recherche +/-, 2 - MasterMind" );
                chosenGame = sc.nextLine();
                inputToArray = createArrayListeFromInput( chosenGame );
                if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                    throw new ExceptionNaN();
                }
                else {
                    chosenGameParsed = Integer.parseInt( myTrimString( chosenGame ) );
                }
            }catch (ExceptionNaN e){
                catched = true;
            }
            finally {
                if(chosenGameParsed < 1 || chosenGameParsed > 2) {
                    System.out.println( "Vous devez saisir un choix parmis ceux disponibles." );
                    catched = true;
                }
            }
        }while(catched);

        chosenGameParsed = Integer.parseInt( chosenGame );
        //TODO gérer les modes avec le package Modes
        switch (chosenGameParsed){
            case 1:
                chosenModeParsed = chooseModeForGame();
                if(chosenModeParsed == 1){
                    String playAgain = "";
                    do {
                        SearchGame_Challenger searchgame = new SearchGame_Challenger();
                        playAgain=stopOuEncore();
                    }while (playAgain.equals("O"));
                }
                else if(chosenModeParsed == 2){
                    SearchGame_Defenseur searchgame = new SearchGame_Defenseur();
                }
                break;
            case 2:
                chosenModeParsed = chooseModeForGame();
                if(chosenModeParsed == 1){
                    String playAgain = "";
                    do {
                        MastermindGame_Challenger mastermind = new MastermindGame_Challenger();
                        playAgain=stopOuEncore();
                    }while (playAgain.equals("O"));
                }
                else if(chosenModeParsed == 2){
                    MastermindGame_Defenseur mastermindGame = new MastermindGame_Defenseur();
                }
                break;
            default:System.out.println("Nous n'avons pas compris votre choix.");
        }
    }
}
