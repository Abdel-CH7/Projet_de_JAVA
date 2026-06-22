import MG2D.Fenetre;
import MG2D.Couleur;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texte;
import java.awt.Font;

public class JeuPokemon {
    public static void main(String[] args) {
        int tailleCase = 80;
        int nbCases = Plateau.TAILLE;
        int margeHaut = 50;

        Fenetre fenetre = new Fenetre("Échecs Pokémon", nbCases * tailleCase, (nbCases * tailleCase) + margeHaut);

        Font policeTour = new Font("Arial", Font.BOLD, 22);

        Texte uiTour = new Texte(Couleur.BLEU, "TOUR : JOUEUR 1", policeTour, new Point((nbCases * tailleCase) / 2, (nbCases * tailleCase) + 25));
        fenetre.ajouter(uiTour);

        for (int i = 0; i < nbCases; i++) {
            for (int j = 0; j < nbCases; j++) {
                Couleur couleurCase = ((i + j) % 2 == 0) ? Couleur.BLANC : Couleur.GRIS;
                Rectangle casePlateau = new Rectangle(couleurCase, new Point(i * tailleCase, j * tailleCase), tailleCase, tailleCase, true);
                fenetre.ajouter(casePlateau);
            }
        }
    }
}