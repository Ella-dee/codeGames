package com.elodie.jeux.Modes;

import java.util.ArrayList;
import static com.elodie.jeux.GeneralMethodes.Methodes.createArrayFromInput;
import static com.elodie.jeux.jeu_Recherche.SearchGame.tryOutCheck;

public class Defenseur extends Mode {
    public Defenseur(String name){
        super(name);
        this.name = "Mode Defenseur";
    }
    /**
     * Méthode comprend la mécanique du jeu pour le Mode Defenseur (AI VS utilisateur).
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices "+", "-", ou "="</p>
     * //TODO prendre en compte les opérateurs de comparaison pour AI?
     * @see com.elodie.jeux.jeu_Recherche.SearchGame#tryOutCheck(ArrayList, int[], String, ArrayList)
     */
    public static String startDefenseurSearchGame(String AIinput, String[] tableauNbr, int[] tableauSecret, String listeToString, ArrayList liste){
        ArrayList inputToArray = new ArrayList();
        do{
            System.out.println( "\nPropostition de l'ordinateur." );
            int first = (int)(Math.random() * 10);
            int second = (int)(Math.random() * 10);
            int third = (int)(Math.random() * 10);
            int fourth = (int)(Math.random() * 10);
            AIinput = String.valueOf( first )+String.valueOf( second )+String.valueOf( third )+String.valueOf( fourth );
            inputToArray = createArrayFromInput( AIinput );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(inputToArray.size()>tableauSecret.length||inputToArray.size()<tableauSecret.length);

        //vérification réponse/code
        listeToString = tryOutCheck(inputToArray, tableauSecret, AIinput, liste);
        liste.clear();
        return listeToString;
    }
}
