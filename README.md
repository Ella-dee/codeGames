# CodeGames
### Développement en mode console d'une application proposant des jeux de logique.

<p>Chaque jeu possède 3 modes :
<ul>
<li>Mode challenger: le joueur doit trouver la combinaison secrète de l'ordinateur</li>
<li>Mode défenseur: l'ordinateur doit trouver la combinaison secrète du joueur</li>
<li>Mode duel où l'ordinateur et le joueur jouent tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné</li>
</ul>

### Compilation

<p>Installer le JDK d'Oracle version minimum 8. https://www.oracle.com/technetwork/java/javase/downloads/index.html
<ol>
 <li>Récupérer le code source dans src/main
 <ul>
  <li>lancer le build Main dans l'IDE</li>
   <li>Installer Maven version minimum 2.</li>
   <li>Lancer le run.
 </ul></li>
  <li>Récupérer le dossier target pour lancement en ligne de commande
  <ul>
   <li>Installer Maven version minimum 2.</li>
   <li>Se positionner dans le dossier du jar en ligne de commande</li>
   <li>lancer java -jar jeux-1.0-SNAPSHOT.jar</li>
  </ul></li>
 </ol>

### Utilisation
#### Jeux disponibles:Recherche +/-, Mastermind (code à chiffres)

<p>Il est possible de lancer l'application dans un mode "développeur" pour afficher la solution dès le début. 
<ul>
<li>dans le fichier config.propreties, passer le paramètre "mode.dev" en "on".</li>
<li>en passant un argument en la ligne de commande, récupéré à l'aide de String args[]: </br>
<i><b>java -jar nom-du-jeu.jar 1</b></i></li>
</ul>
<p> Au démarrage, l'utilisateur choisi le jeu auquel il veut jouer parmi les choix proposés.
Il sélectionne le mode de son choix. L'application lance le jeu sélectionné.
<p>L'utilisateur joue. S'il perd, l'application affiche la solution.
À la fin de la partie, l'utilisateur peut choisir :
<ul>
<li>de rejouer au même jeu</li>
<li>de lancer un autre jeu (retour à l'écran de choix des jeux du début)</li>
<li>de quitter l'application</li>
</ul>

<p>Il est possible de changer le nombre d'essais maximal pour deniver un code en changeant la valeur de la propriété "max.tries" dans le fichiers config.propreties.
<p>Il est possible de changer le nombre de chiffres maximal qui compose un code en changeant la valeur de la propriété "max.cases" dans le fichiers config.propreties.
<p>Un fichier de logs.log à la racine du projet, ou à la racine du dossier où se trouve le jar, permet de suivre les logs de l'application.
La gestion des logs se fait avec Apache Log4j2</p>
