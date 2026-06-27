Compilation

Depuis la racine du projet, exécuter la commande suivante :
javac -encoding utf8 -d bin -sourcepath ".;src" src/*.java


Lancement du jeu graphique MG2D

Après la compilation, lancer le jeu avec :
java -cp "bin;." JeuPokemon


Lancement du test console

La classe Main.java permet de tester le système de combat et le chargement des Pokémon depuis le fichier CSV.
java -cp "bin;." Main


Remarques importantes

Le dossier ressources doit rester à la racine du projet, car il contient :
le fichier pokedex_gen1.csv ;
les images des Pokémon dans ressources/images.