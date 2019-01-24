package com.elodie.jeux.utilities;

import java.util.ArrayList;

/**
 * <b>Contient les méthodes généralistes nécessaires au divers jeux</b>
 * <p>Ne contient aucun mécanisme de jeu.</p>
 * Les méthodes:
 * <ul>
 *     <li>formatage de texte</li>
 *     <li>comparaison des entrées d'une liste avec les entrées d'un tableau</li>
 *     <li>transformation d'une chaine de caractères en tableau rempli par chaque caractère de cette chaine</li>
 * </ul>
 */
public class utils {
    /**
     * <b>Méthode de formatage du texte</b>
     * <p>Permet de transformer un ArrayList en chaîne de caractères,
     * et d'enlever la mise en forme "liste", ainsi que les espaces</p>
     * @param str chaîne de caractères donnée
     * @return la chaine de caractères donnée reformatée sans espace
     */
    public static String myTrimString(String str){
        StringBuilder sb = new StringBuilder();
        sb.append(str.replaceAll("[\\[\\],]", "").replace( " ", "" ));
        str.trim();
        return sb.substring( 0, sb.length());
    }
    /**
     * <b>Méthode de formatage du texte</b>
     * <p>Permet de transformer un ArrayList en chaîne de caractères,
     * et d'enlever la mise en forme "liste", mais garde les espaces et la virgule de séparation.</p>
     * @param str chaîne de caractères donnée
     * @return la chaine de caractères donnée reformatée
     */
    public static String trimStringKeepFormat(String str){
        StringBuilder sb = new StringBuilder();
        sb.append(str.replaceAll("[\\[\\]]", ""));
        str.trim();
        return sb.substring( 0, sb.length());
    }
    /**
     * Méthode prend une liste, et compare ses entrées avec un tableau.
     * si une entrée de la liste est comprise dans le tableau on ajoute 1 à un compteur.
     * Si le total du compteur n'est pas égal à la longueur du tableau,
     * alors une ou plusieurs entrées de la liste ne sont pas comprises dans le tableau.
     * @param liste une liste remplie de données
     * @param str un tableau de chaine de caractère données
     * @return un booléen "appears" qui renvoie "false" si une ou plusieurs entrées de la liste ne sont pas dans le tableau
     */
    public static boolean checkOccurencesFromListInArray(ArrayList liste, String[] str){
        boolean appears = true;
        int count = 0;
        for(Object o:liste){
            for(int j=0;j<str.length;j++){
                if(o.toString().equals(str[j])){
                    count += 1;
                }
            }
            if(count == liste.size()){
                appears = true;
            }
            else appears = false;
        }
        return appears;
    }

    /**
     * Méthode prend la réponse de l'utilisateur et transforme chaque chiffre en élément de liste
     * @param str une chaîne de caractère donnée
     * @return une liste remplie de chaque chiffre composant la réponse utilisateur
     */
    public static ArrayList createArrayListeFromInput(String str) {
        char[] charr = str.toCharArray();
        ArrayList arr = new ArrayList(charr.length);
        for (int i = 0; i< charr.length; i++) {
            arr.add(Character.toString(charr[i]));
        }
        return arr;
    }

    public static int randomInRange(int arg1, int arg2) {
        int i = (int)Math.ceil(Math.random() * (arg1 - arg2) + arg2);
        return i;
    }

}
