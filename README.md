# Chercheur Chemin

## A propos de l'application

Cette application a ete concue pour aider les voyageurs et voyageuses a trouver le plus court chemin pour parcourir toutes les villes d'une liste de villes a visiter.
Notre application est specialement concue pour les personnes travaillant dans les sciences, puisqu'elle integre une base de donnees contenant differentes personnes occupant divers roles (Etudiant, MCF, Chercheur) et specialisees dans plusieurs disciplines.
Nous proposons a l'utilisateur de trier la liste des personnes qu'il souhaite visiter selon differents criteres.


## Comment compiler et executer le projet

Tout d'abord, clonez le projet depuis GitHub et extrayez le fichier ZIP.

Ensuite, afin de pouvoir lancer notre application, en utilisant votre base de donnees PostgreSQL, suivez les etapes suivantes :

1) Lancer les requetes contenues dans le fichier `creationBaseDeDonneesVilles.sql`, qui se trouve dans le dossier du projet (`ChercheurChemin`). 
Ces requetes permettent de creer deux relations initiales : de departements et de villes, ainsi que les requetes pour remplir ces relations.

2) Ensuite, allez dans le terminal et placez-vous dans le dossier `ChercheurChemin` (qui se trouve egalement dans le dossier principal du projet - `ChercheurChemin`).

### Compilation

3) Tout d'abord, vous devez vider le dossier contenant les fichiers executables precedemment crees, en utilisant la commande suivante : 
```bash
rm -r bin
```

4) Ensuite, pour compiler le projet, lancez la commande suivante : 
```bash
javac -d bin -cp "lib/postgresql-42.7.4.jar" src/affichage/*.java src/affichage/affichageAlgo/*.java src/affichage/affichageEcosysteme/*.java src/affichage/affichageAlgo/choixPersonne/*.java src/affichage/affichageAlgo/choixVille/*.java src/baseDeDonnees/*.java src/filtrage/*.java src/genetique/*.java src/main/*.java src/objetsAuxiliaires/*.java src/peuple/*.java src/representationSolution/*.java src/territoire/*.java src/verificationEntree/*.java
```

### Execution

5) Pour executer le projet, veuillez lancer la commande suivante : 
```bash
java -cp "bin:lib/*" main.Main
```

6) Suivez les indications affichees pour connecter le programme a votre base de donnees.
Si vous ne connaissez pas l'URL de votre base de donnees, elle est sous la forme :
jdbc:postgresql://localhost:VOTRE_PORT/NOM_DE_LA_BASE_DE_DONNEE.

### Execution via Eclipse

Vous pouvez egalement ouvrir le projet dans Eclipse et le lancer directement depuis l'IDE.


## Particularites de notre programme

Notre programme peut etre utilise a travers le terminal ou via une interface graphique.
Au lancement, l'utilisateur a le choix entre taper 'terminal' ou 'ui' (pour user interface). Selon son choix, les interactions se feront :
- Soit via le terminal.
- Soit via une fenetre graphique qui s'ouvrira automatiquement.


## Fonctionnalites de notre programme

### Partie Tri 

Notre programme propose plusieurs methodes de tri permettant d'organiser la liste des personnes selon differents criteres.
Les criteres de tri sont les suivant : en fonction de la ville, region, -population et superficie de la ville ou region aussi-, departement, ville chef-lieu, nom prenom, ID, age, disciplines, fonction -mcf,chercheur,etudiants-, nombre d'etudiant -pour les chercheur-, numero de bureau -pour les titulaires-, etudiants -pour les titulaires-, encadrants -pour les etudiants-, sujet de these et annee de these -pour les etudiants-.
Nous avons essayer de vous donner le plus de criteres de selection possible.

### Attention ! 
Dans la partie selection de l'interface graphique, lorsque vous devez choisir des noms de ville (ou departement, ID, nom, prenom...) ou des personnes, suivez les etapes suivantes :  
1. Cliquez deux fois sur une proposition pour la selectionner.  
2. Cliquez sur "Ajouter" pour l'inclure dans la liste.  
3. Une fois tous les elements souhaites sont ajoutes, cliquez sur "Valider" pour confirmer votre choix.


### Modification de la base de donnees

Notre programme propose une base de donnees par defaut contenant differentes personnes. Mais vous pouvez la modifier !
L'utilisateur peut ajouter, supprimer et modifier des personnes autant qu'il le souhaite.
Certaines regles de coherence sont appliquees pour garantir l'integrite des donnees. Si une action demandee par l'utilisateur n'est pas permise, le programme l'indiquera clairement.

### Generation du meilleur chemin

Notre programme propose plusieurs options pour representer le meilleur chemin genere par l'algorithme :

Affichage direct :
Dans la fenetre graphique (si l'interface graphique est utilisee).
Dans le terminal (si l'option terminal est choisie).

Exportation dans un fichier texte :
L'utilisateur peut choisir l'emplacement ou enregistrer le fichier text contenant le chemin genere.

Exportation dans un fichier html :
L'utilisateur peut choisir l'emplacement ou enregistrer le fichier html contenant le chemin genere.