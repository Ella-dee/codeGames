package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.Exceptions.ExceptionNaN;

import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.GeneralMethodes.Methodes.*;

/**
 * <b>Recherche +/-</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=).
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<section>
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -> Réponse : -=--</i></p>
 *<p><i>(Proposition : 2214 -> Réponse : -=+=</i></p>
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
 *</ul>
 * <p>Un fichier de configuration (log4j.xml) permet de paramétrer les logs de l'application.
 * @author elojito
 * @version 1.0
 */

public class SearchGame {
    public static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    final int first = (int)(Math.random() * 10);
    final int second = (int)(Math.random() * 10);
    final int third = (int)(Math.random() * 10);
    final int fourth = (int)(Math.random() * 10);
    private final int[] secretCode = {first, second, third, fourth};
    String userInput = "";
    final ArrayList reponse = new ArrayList();
    String reponseToString = "";
    final String winwin = "====";
//TODO ajout d'une limite d'essais
    public SearchGame(){
        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );

        //le jeu:
        Scanner sc = new Scanner( System.in );
        boolean catched = false;
        ArrayList inputToArray = new ArrayList();
        do {

            do{
                try{
                    catched = false;
                    System.out.println( "\nquelle est votre proposition?" );
                    userInput = sc.nextLine();
                    inputToArray = createArrayFromInput( userInput );
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
            String equal = "=";
            String minus = "-";
            String plus = "+";
            System.out.print( "Votre proposition: " + userInput + " -> Réponse: " );
            int i=0;
            for (Object o:inputToArray) {
                    if (Integer.parseInt( o.toString())== secretCode[i]) {
                        System.out.print( "=" );
                        reponse.add( equal );
                    } else if (Integer.parseInt( o.toString())< secretCode[i]) {
                        System.out.print( "+" );
                        reponse.add( plus );
                    } else if (Integer.parseInt( o.toString())> secretCode[i]) {
                        System.out.print( "-" );
                        reponse.add( minus );
                    }
                    i++;
                }
            reponseToString = myTrimString(reponse.toString());
            reponse.clear();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison: "+userInput );
    }
}
