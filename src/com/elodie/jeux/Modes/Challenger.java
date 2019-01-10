package com.elodie.jeux.Modes;

import java.util.ArrayList;
import java.util.Scanner;

import com.elodie.jeux.Exceptions.ExceptionNaN;
import static com.elodie.jeux.GeneralMethodes.Methodes.checkOccurencesFromListInArray;
import static com.elodie.jeux.GeneralMethodes.Methodes.createArrayFromInput;
import static com.elodie.jeux.jeu_Recherche.SearchGame.tryOutCheck;

public class Challenger extends Mode {
    public Challenger(String name){
        super(name);
        this.name = "Mode Challenger";
    }
    /**
     * Méthode comprend la mécanique du jeu pour le Mode Challenger (utilisateur VS AI).
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * @see com.elodie.jeux.jeu_Recherche.SearchGame#tryOutCheck(ArrayList, int[], String, ArrayList)
     */
    public static String startChallengerSearchGame(String userStr, String[] tableauNbr, int[] tableauSecret, String listeToString, ArrayList liste){
            Scanner sc = new Scanner( System.in );
            boolean catched;
            ArrayList inputToArray = new ArrayList();
            do{
                try{
                    catched = false;
                    System.out.println( "\nquelle est votre proposition?" );
                    userStr = sc.nextLine();
                    inputToArray = createArrayFromInput( userStr );
                    if(!checkOccurencesFromListInArray(inputToArray, tableauNbr)){
                        throw new ExceptionNaN();
                    }
                } catch (ExceptionNaN e) {
                    catched = true;
                }
                finally {
                    if ( inputToArray.size() > tableauSecret.length || inputToArray.size() < tableauSecret.length) {
                        System.out.print( "Vous devez saisir une combinaison à " + tableauSecret.length + " chiffres." );
                        catched = false;
                    }
                }
            }while(inputToArray.size()>tableauSecret.length||inputToArray.size()<tableauSecret.length||catched);

            //vérification réponse/code
            listeToString = tryOutCheck(inputToArray, tableauSecret, userStr, liste);
            liste.clear();
            return listeToString;
    }
}
