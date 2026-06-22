import MG2D.Fenetre;
import MG2D.Couleur;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;
import MG2D.geometrie.Texte;
import java.awt.Font;

public class JeuPokemon {
    public static void main(String[] args) {
        int tailleCase = 80;
        int nbCases = Plateau.TAILLE;
        int margeHaut = 50;

        Fenetre fenetre = new Fenetre("Échecs Pokémon", nbCases * tailleCase, (nbCases * tailleCase) + margeHaut);

        Plateau plateauLogique = new Plateau();

        Texture[][] plateauVisuel = new Texture[nbCases][nbCases];
        String[][] plateauChemins = new String[nbCases][nbCases];
        Texte[][] plateauPV = new Texte[nbCases][nbCases];

        Font policeTour = new Font("Arial", Font.BOLD, 22);
        Font policePV = new Font("Arial", Font.BOLD, 12);


        Texte uiTour = new Texte(Couleur.BLEU, "TOUR : JOUEUR 1", policeTour, new Point((nbCases * tailleCase) / 2, (nbCases * tailleCase) + 25));
        fenetre.ajouter(uiTour);

        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {
                Couleur couleurCase = ((i + j) % 2 == 0) ? Couleur.BLANC : Couleur.GRIS;
                Rectangle casePlateau = new Rectangle(couleurCase, new Point(i * tailleCase, j * tailleCase), tailleCase, tailleCase, true);
                fenetre.ajouter(casePlateau);
            }
        }

        String csvPath = "ressources/pokedex_gen1.csv";
        
        int[] pokesJ1 = {1, 3, 4, 6, 7, 9, 25, 26, 39, 40, 52, 54, 58, 59, 63, 65, 66, 68, 74, 76, 92, 94, 133, 134, 135, 136};
        int[] pokesJ2 = {2, 5, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 27, 28, 29, 30, 31, 32, 33, 34};
        
        int indexJ1 = 0;
        int indexJ2 = 0;

        //Joueur 1 (en bas)
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < nbCases; i++) {
                Pokemon p = null;
                int idActuel = 0;

                if (j == 0 && i == 4) {
                    idActuel = 150;
                    p = new Pokemon(150, "Mewtwo", csvPath);
                } else if (indexJ1 < pokesJ1.length) {
                    idActuel = pokesJ1[indexJ1++];
                    p = new Pokemon(idActuel, Type.getEspece(idActuel), csvPath);
                }

                if (p != null) {
                    plateauLogique.placerPokemon(p, 1, i, j);
                    
                    plateauChemins[i][j] = "images/" + idActuel + ".png";
                    Texture tex = new Texture("images/" + idActuel + ".png", new Point(i * tailleCase, j * tailleCase), tailleCase, tailleCase);
                    plateauVisuel[i][j] = tex;
                    fenetre.ajouter(tex);

                    Texte textePV = new Texte(Couleur.NOIR, p.getPv() + "/" + p.getPvMax(), policePV, new Point(i * tailleCase + (tailleCase / 2), j * tailleCase + 12));
                    plateauPV[i][j] = textePV;
                    fenetre.ajouter(textePV);
                }
            }
        }
        
        // Joueur 2 (en haut)
        for (int j = 6; j < 9; j++) {
            for (int i = 0; i < nbCases; i++) {
                Pokemon p = null;
                int idActuel = 0;

                if (j == 8 && i == 4) {
                    idActuel = 150;
                    p = new Pokemon(150, "Mewtwo", csvPath);
                } else if (indexJ2 < pokesJ2.length) {
                    idActuel = pokesJ2[indexJ2++];
                    p = new Pokemon(idActuel, Type.getEspece(idActuel), csvPath);
                }

                if (p != null) {
                    plateauLogique.placerPokemon(p, 2, i, j);
                    
                    plateauChemins[i][j] = "images/" + idActuel + ".png";
                    Texture tex = new Texture("images/" + idActuel + ".png", new Point(i * tailleCase, j * tailleCase), tailleCase, tailleCase);
                    plateauVisuel[i][j] = tex;
                    fenetre.ajouter(tex);

                    Texte textePV = new Texte(Couleur.NOIR, p.getPv() + "/" + p.getPvMax(), policePV, new Point(i * tailleCase + (tailleCase / 2), j * tailleCase + 12));
                    plateauPV[i][j] = textePV;
                    fenetre.ajouter(textePV);
                }
            }
        }
        
        fenetre.rafraichir();
    }
}