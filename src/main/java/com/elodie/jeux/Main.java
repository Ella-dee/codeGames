package com.elodie.jeux;

import com.elodie.jeux.utilities.*;
import com.elodie.jeux.mastermind.*;
import com.elodie.jeux.search.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.PrintWriter;
import java.io.StringWriter;
import static com.elodie.jeux.utilities.UtilsGameMecanics.*;
import static com.elodie.jeux.utilities.UtilsPropreties.*;

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
 * À la fin de la partie, l'utilisateur peut choisir :
 * <ul>
 *<li>de rejouer au même jeu</li>
 * <li>de lancer un autre jeu (retour à l'écran de choix des jeux du début)</li>
 * <li>de quitter l'application</li>
 *</ul>
 * <p>Il est possible de lancer l'application dans un mode "développeur".
 * Dans ce mode la solution est affichée dès le début. Cela permet de tester le bon comportement
 * de l'application en cas de bonne ou de mauvaise réponse de l'utilisateur.
 * On peut passer en mode développeur de deux manières :
 * <ul>
 * <li>Passage d'un paramètre "dev" au lancement de l'application</li>
 * <li>Passer la propriété "mode.dev" à 1 dans le fichier de configuration</li>
 *</ul>
 *<p>Le fichier de configuration (config.properties) permet de paramétrer l'application, notamment :
 * Pour chaque jeu :
 *<ul>
 *<li>le nombre de cases de la combinaison secrète</li>
 *<li>le nombre d'essais possibles</li>
 *</ul>
 *<p>Le fichier log4j2.xml permet de paramétrer les logs de l'application.
 * La gestion des logs se fait avec Apache Log4j, et les logs sortent dans le fichier logs.log</p>.
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static String mainParam;

    /**
     * On vérifie si l'argument mode développeur est lancé en commande.
     * On vérifie si le fichier config lance le mode développeur.
     * On insère une Exception qui affichera dans le fichier logs les exceptions non catchées.
     * On demande à l'utilisateur de choisir un jeu.
     * On demande à l'utilisateur de choisir un mode de jeu.
     * On lance le jeu avec son mode correspondant.
     * La partie terminée, on demande à l'utilisateur s'il veut refaire une partie.
     * Si oui on relance le jeu avec son mode correspondant.
     * Si non, on demande à l'utilisateur s'il veut jouer à un autre jeu.
     * Si oui, on lui demande le jeu et le mode auquel il souhaite jouer.
     * Si non, on ferme l'application.
     * @param args argument éventuellement passé au lancement indiquant si on passe ou non en mode développeur
     * @throws Exception exception ajoute les exceptions non attrapées dans le fichier des logs
     * @see UtilsGameMecanics#stopOuEncore()
     * @see UtilsGameMecanics#showModeDevOn()
     * @see UtilsGameMecanics#backToMenu()
     * @see UtilsGameMecanics#menuJeu()
     * @see UtilsGameMecanics#chooseModeForGame()
     */
    public static void main(String[] args)throws Exception {
        if(args.length!=0) {
            mainParam = Utils.myTrimString(args[0]);
        }
        showModeDevOn();

           Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                String stacktrace = sw.toString();
                logger.fatal("uncaught exception: "+ stacktrace );
                System.out.println("une erreur inattendue a fermé le programme" );
            }
        });
        logger.info("Entering application.");

        System.out.println( "\nBienvenu-e-s!" );
        System.out.println( "-------------------------------------" );

        int game = 0;
        int mode = 0;
        String chooseAgain ="";

        do {
            game = menuJeu();
                logger.info(getConfigProprety( "game."+game ));

            String playAgain = "";
            switch (game) {
                //Case 1 = Jeu Recherche
                //Case 2 = Jeu mastermind
                case 1:
                    mode = chooseModeForGame();
                       logger.info( getConfigProprety( "mode."+mode ));
                    //Mode 1 = Challenger
                    //Mode 2 = Defenseur
                    //Mode 3 = Duel
                    if (mode == 1) {
                        do {
                            SearchChallenger searchgame = new SearchChallenger();
                            playAgain = stopOuEncore();
                            logger.info( "réponse à stopouencore(): "+playAgain );
                        } while (playAgain.equals( "O" ));
                    } else if (mode == 2) {
                        do {
                            SearchDefenseur searchgame = new SearchDefenseur();
                            playAgain = stopOuEncore();
                            logger.info( "réponse à stopouencore(): "+playAgain );
                        } while (playAgain.equals( "O" ));
                    }else if (mode == 3) {
                        do {
                            SearchDuel searchgame = new SearchDuel();
                            playAgain = stopOuEncore();
                            logger.info( "réponse à stopouencore(): "+playAgain );
                        } while (playAgain.equals( "O" ));
                    }
                    break;
                //Jeu mastermind
                case 2:
                    mode = chooseModeForGame();
                    logger.info(getConfigProprety( "mode."+mode ) );
                    if (mode == 1) {
                        do {
                            MastermindChallenger mastermind = new MastermindChallenger();
                            playAgain = stopOuEncore();
                            logger.info( "réponse à stopouencore(): "+playAgain );
                        } while (playAgain.equals( "O" ));
                    } else if (mode == 2) {
                        do {
                            MastermindDefenseur mastermindGame = new MastermindDefenseur();
                            playAgain = stopOuEncore();
                            logger.info( "réponse à stopouencore(): "+playAgain );
                        } while (playAgain.equals( "O" ));
                    }
                    else if (mode == 3) {
                        do {
                            MastermindDuel mastermindGame = new MastermindDuel();
                            playAgain = stopOuEncore();
                            logger.info( "réponse à stopouencore(): "+playAgain );
                        } while (playAgain.equals( "O" ));
                    }
                    break;
                default:
                    System.out.println( "Nous n'avons pas compris votre choix." );
                    logger.info( "choix de mode incorrect: "+mode );
            }
            chooseAgain = backToMenu();
            logger.info( "réponse à backToMenu(): "+chooseAgain );
        } while (chooseAgain.equals( "O" ));
        System.out.println( "A bientôt!!!" );
        logger.info("Exiting application.");
    }
}
