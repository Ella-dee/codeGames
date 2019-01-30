# CodeGames
### Développement en mode console d'une application proposant des jeux de logique.

<p>Chaque jeu possède 3 modes :
<ul>
<li>Mode challenger: le joueur doit trouver la combinaison secrète de l'ordinateur</li>
<li>Mode défenseur: l'ordinateur doit trouver la combinaison secrète du joueur</li>
<li>Mode duel où l'ordinateur et le joueur jouent tour à tour, le premier à trouver la combinaison secrète de l'autre a gagné</li>
</ul>

### Compilation

<p>Installer le JDK Oracle version minimum 8. https://www.oracle.com/technetwork/java/javase/downloads/index.html
 
<p>il est possible de passer en mode développeur en passant un argument dans lal igne de commande, qui sera récupéré dans la méthode Main à l'aide de String argd[]: java -jar nom-du-jeu.jar on
 

### Utilisation
#### Jeux disponibles:Recherche +/-, Mastermind (code à chiffres)

<p> Au démarrage, l'utilisateur choisi le jeu auquel il veut jouer parmi les choix proposés.
Il sélectionne le mode de son choix. L'application lance le jeu sélectionné.
<p>On demande au joueur le nombre de cases de la combinaison secrète qu'il souhaite deviner
ou faire deviner, ainsi que le nombre d'essais maximal.
<p>L'utilisateur joue. S'il perd, l'application affiche la solution.
À la fin de la partie, l'utilisateur peut choisir :
<ul>
<li>de rejouer au même jeu</li>
<li>de lancer un autre jeu (retour à l'écran de choix des jeux du début)</li>
<li>de quitter l'application</li>
</ul>
<p>Il est possible de lancer l'application dans un mode "développeur" pour afficher la solution dès le début. 
Pour cela dans le fichier config.propreties, passer le paramètre "mode.dev" en "on".

<p>Un fichier de logs.log à la racine du projet permet de suivre les logs de l'application.
La gestion des logs se fait avec Apache Log4j2</p>
