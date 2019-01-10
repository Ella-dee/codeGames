package com.elodie.jeux.jeu_Mastermind;

import com.elodie.jeux.Exceptions.ExceptionNaN;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;

/**
 * <b>Mastermind</b>
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
 *<p>Peut être joué selon 3 modes :
 *<ul>
 *     <li>Mode challenger où vous devez trouver la combinaison secrète de l'ordinateur</li>
 *     <li>Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète</li>
 *     <li>Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné</li>
 *</ul>
 *<p>Il doit être possible de lancer l'application dans un mode "développeur".
 * Dans ce mode la solution est affichée dès le début. Cela permet de tester le bon comportement
 * de l'application en cas de bonne ou de mauvaise réponse de l'utilisateur.
 * Ceci est à réaliser avec les mécanismes suivants :
 *<ul>
 *     <li>Passage d'un paramètre au lancement de l'application</li>
 *     <li>Propriété spécifique dans le fichier de configuration</li>
 *</ul>
 * <p>Un fichier de configuration (config.properties) permettra de paramétrer l'application, notamment :
 *<ul>
 *     <li>le nombre de cases de la combinaison secrète</li>
 *     <li>le nombre d'essais possibles</li>
 *     <li>le nombre de chiffres utilisables</li>
 *</ul>
 * <p>Un fichier de configuration (log4j.xml) permet de paramétrer les logs de l'application.
 * @author elojito
 * @version 1.0
 */

public class MastermindGame {
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>4 chiffres générés par la fonction Math.random compris entre 0 et 9</li>
     * <li>un tableau formant une combinaison secrète avec ces 4 chiffres</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère "4 bien placés" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * @see MastermindGame#start()
     * <p>Si l'utilisateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     */
    public static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    final static int first = (int)(Math.random() * 10);
    final static int second = (int)(Math.random() * 10);
    final static int third = (int)(Math.random() * 10);
    final static int fourth = (int)(Math.random() * 10);
    protected static final int[] secretCode = {first, second, third, fourth};
    static String userInput = "";
    static ArrayList reponse = new ArrayList();
    static String reponseToString = "";
    final String winwin = "4 bien placés";

    public MastermindGame(){

        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );
        //On lance le jeu
        do {
            start();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison: "+userInput );
    }
    /**
     * Méthode récupère une chaine de caractères de chiffres prise en entrée pour l'afficher en sortie.
     * On récupère une liste composée des caractères de cette chaine, puis on la parcoure.
     * On compare ainsi chaque entrée de la liste avec lse entrées d'un tableau de chiffres.
     * Selon si les entrées de la liste sont présentes dans la combinaison mais mal placées ou bien trouvées
     * aux entrées du tableau, on affichera en sortie "x bien placé" ou "x présent".
     * Ces résultats seront stocker dans une liste, puis transformés en chaine de caractères pour l'affichage sortie.
     * L'attribut "countFound" s'incrémente si un chiffre est bien placé.
     * L'attribut "countSomewhere" s'incrémente si un chiffre est trouvé mais mal placé.
     * @param liste une liste composée de chaque chiffre de la chaine de caractères input
     * @param secret un tableau composé de chiffres définis
     * @param input entrée utilisateur composée de chiffres
     * @param output liste composée des chaines de caractères définissant les chiffres de "liste" comme étant
     *               présents ou bien placés par rapport à "secret" à l'aide des attributs entiers
     *               countFound et countSomewhere.
     * @return une chaine de caractères composées des entrées de la liste "output"
     */

    public static String tryOutCheck(ArrayList liste, int[] secret, String input, ArrayList output){
        int countFound = 0;
        int countSomewhere = 0;
        System.out.print( "Votre proposition: " + input + " -> Réponse: " );
        int i=0;
        for (Object o:liste) {
            int oInt = Integer.parseInt(o.toString());
            if (oInt == secret[i]) {
                countFound += 1;
            }
            else if((oInt != secret[i])&& Arrays.asList(secret).contains(o.toString())){
                countSomewhere += 1;
            }
            i++;
        }
        if(countFound == 1){
            output.add(countFound+" bien placé");
        }
        else if(countFound >1){
            output.add( countFound+" bien placés" );
        }
        if(countSomewhere == 1){
            output.add( countSomewhere+" présent" );
        }
        else if(countSomewhere >1){
            output.add( countSomewhere+" présents" );
        }
        String outputToString = myTrimStringWithSpaces(output.toString());
        System.out.println(outputToString);
        return outputToString;
    }

    /**
     * Méthode comprend la mécanique du jeu.
     * <p>On demande à l'utilisateur d'entrer une combinaison</p>
     * <p>On vérifie qu'il s'agit bien de chiffres et que le nombre de chiffres correspond à celui du code secret</p>
     * @see ExceptionNaN#ExceptionNaN()
     * <p>On compare à la combinaison secrète puis affiche si les chiffres sont bien placés ou au moins présents.</p>
     * @see MastermindGame#tryOutCheck(ArrayList, int[], String, ArrayList)
     */
    public static void start(){
        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList inputToArray = new ArrayList();
        do{
            try{
                catched = false;
                System.out.println( "\nquelle est votre proposition?" );
                userInput = sc.nextLine();
                inputToArray = createArrayListeFromInput( userInput );
                if(!checkOccurencesFromListInArray(inputToArray, nbr)){
                    throw new ExceptionNaN();
                }
            } catch (ExceptionNaN e) {
                catched = true;
            }
            finally {
                if ( inputToArray.size() > secretCode.length || inputToArray.size() < secretCode.length) {
                    System.out.print( "Vous devez saisir une combinaison à " + secretCode.length + " chiffres." );
                    catched = false;
                }
            }
        }while(inputToArray.size()>secretCode.length||inputToArray.size()<secretCode.length||catched);

        //vérification réponse/code
        reponseToString = tryOutCheck( inputToArray, secretCode, userInput, reponse);
        reponse.clear();
    }
}

