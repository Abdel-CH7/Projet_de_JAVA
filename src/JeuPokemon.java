import MG2D.Fenetre;
import MG2D.Couleur;
import MG2D.geometrie.Rectangle;
import MG2D.geometrie.Point;
import MG2D.geometrie.Texture;
import MG2D.geometrie.Texte;
import MG2D.Souris;
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

        Souris souris = fenetre.getSouris();
        int tourJoueur = 1;
        boolean pokemonSelectionne = false;
        int selectX = -1;
        int selectY = -1;
        Rectangle carreSelection = null;

        while (true) {

            if (souris.getClicGauche()) {
                Point clic = souris.getPosition();

                int caseX = (int) (clic.getX() / tailleCase);
                int caseY = (int) (clic.getY() / tailleCase);

                if (caseX >= 0 && caseX < nbCases && caseY >= 0 && caseY < nbCases) {
                    int proprietaire = plateauLogique.getProprietaire(caseX, caseY);
                    Pokemon pClic = plateauLogique.getPokemon(caseX, caseY);

                    if (!pokemonSelectionne) {
                        if (proprietaire == tourJoueur && pClic != null && pClic.estVivant()) {
                            selectX = caseX;
                            selectY = caseY;
                            pokemonSelectionne = true;

                            if (carreSelection != null) {
                                fenetre.supprimer(carreSelection);
                            }
                            carreSelection = new Rectangle(Couleur.ROUGE, new Point(selectX * tailleCase, selectY * tailleCase), tailleCase, tailleCase, false);
                            fenetre.ajouter(carreSelection);
                        }
                    } else {
                        if (carreSelection != null) {
                            fenetre.supprimer(carreSelection);
                            carreSelection = null;
                        }

                        if (caseX == selectX && caseY == selectY) {
                            pokemonSelectionne = false;
                        } else {
                            int distX = Math.abs(caseX - selectX);
                            int distY = Math.abs(caseY - selectY);

                            if (distX <= 1 && distY <= 1) {
                                Pokemon attaquant = plateauLogique.getPokemon(selectX, selectY);

                                if (proprietaire == 0) { 
                                    plateauLogique.deplacer(selectX, selectY, caseX, caseY);

                                    String img = plateauChemins[selectX][selectY];
                                    plateauChemins[caseX][caseY] = img;
                                    plateauChemins[selectX][selectY] = null;

                                    fenetre.supprimer(plateauVisuel[selectX][selectY]);
                                    Texture nouvelleTex = new Texture(img, new Point(caseX * tailleCase, caseY * tailleCase), tailleCase, tailleCase);
                                    plateauVisuel[caseX][caseY] = nouvelleTex;
                                    plateauVisuel[selectX][selectY] = null;
                                    fenetre.ajouter(nouvelleTex);

                                    fenetre.supprimer(plateauPV[selectX][selectY]);
                                    Texte nvPV = new Texte(Couleur.NOIR, attaquant.getPv() + "/" + attaquant.getPvMax(), policePV, new Point(caseX * tailleCase + (tailleCase / 2), caseY * tailleCase + 12));
                                    plateauPV[caseX][caseY] = nvPV;
                                    plateauPV[selectX][selectY] = null;
                                    fenetre.ajouter(nvPV);

                                    tourJoueur = (tourJoueur == 1) ? 2 : 1;
                                    uiTour.setTexte("TOUR : JOUEUR " + tourJoueur);
                                    uiTour.setCouleur((tourJoueur == 1) ? Couleur.BLEU : Couleur.ROUGE);
                                    pokemonSelectionne = false;

                                } else if (proprietaire != tourJoueur) { 
                                    Pokemon defenseur = plateauLogique.getPokemon(caseX, caseY);
                                    attaquant.attaque(defenseur);

                                    if (!defenseur.estVivant()) {
                                        fenetre.supprimer(plateauVisuel[caseX][caseY]);
                                        fenetre.supprimer(plateauPV[caseX][caseY]);
                                        plateauVisuel[caseX][caseY] = null;
                                        plateauPV[caseX][caseY] = null;

                                        plateauLogique.deplacer(selectX, selectY, caseX, caseY);
                                        
                                        String img = plateauChemins[selectX][selectY];
                                        plateauChemins[caseX][caseY] = img;
                                        plateauChemins[selectX][selectY] = null;

                                        fenetre.supprimer(plateauVisuel[selectX][selectY]);
                                        Texture nouvelleTex = new Texture(img, new Point(caseX * tailleCase, caseY * tailleCase), tailleCase, tailleCase);
                                        plateauVisuel[caseX][caseY] = nouvelleTex;
                                        plateauVisuel[selectX][selectY] = null;
                                        fenetre.ajouter(nouvelleTex);

                                        fenetre.supprimer(plateauPV[selectX][selectY]);
                                        Texte nvPVAtt = new Texte(Couleur.NOIR, attaquant.getPv() + "/" + attaquant.getPvMax(), policePV, new Point(caseX * tailleCase + (tailleCase / 2), caseY * tailleCase + 12));
                                        plateauPV[caseX][caseY] = nvPVAtt;
                                        plateauPV[selectX][selectY] = null;
                                        fenetre.ajouter(nvPVAtt);

                                        if (defenseur.getNom().equalsIgnoreCase("Mewtwo")) {
                                            Texte victoire = new Texte(Couleur.VERT, "VICTOIRE J" + tourJoueur + " !", policeVictoire, new Point((nbCases * tailleCase) / 2, (nbCases * tailleCase) / 2));
                                            fenetre.ajouter(victoire);
                                            fenetre.rafraichir();
                                            try { Thread.sleep(7000); } catch (InterruptedException e) {}
                                            System.exit(0);
                                        }
                                    } else {
                                        fenetre.supprimer(plateauPV[caseX][caseY]);
                                        Texte nvPVDef = new Texte(Couleur.NOIR, defenseur.getPv() + "/" + defenseur.getPvMax(), policePV, new Point(caseX * tailleCase + (tailleCase / 2), caseY * tailleCase + 12));
                                        plateauPV[caseX][caseY] = nvPVDef;
                                        fenetre.ajouter(nvPVDef);
                                    }

                                    if (!attaquant.estVivant()) {
                                        if (plateauVisuel[selectX][selectY] != null) {
                                            fenetre.supprimer(plateauVisuel[selectX][selectY]);
                                            fenetre.supprimer(plateauPV[selectX][selectY]);
                                            plateauVisuel[selectX][selectY] = null;
                                            plateauPV[selectX][selectY] = null;
                                            plateauChemins[selectX][selectY] = null;
                                        }
                                        plateauLogique.placerPokemon(null, 0, selectX, selectY);
                                    } else if (defenseur.estVivant()) {
                                        fenetre.supprimer(plateauPV[selectX][selectY]);
                                        Texte nvPVAtt = new Texte(Couleur.NOIR, attaquant.getPv() + "/" + attaquant.getPvMax(), policePV, new Point(selectX * tailleCase + (tailleCase / 2), selectY * tailleCase + 12));
                                        plateauPV[selectX][selectY] = nvPVAtt;
                                        fenetre.ajouter(nvPVAtt);
                                    }

                                    tourJoueur = (tourJoueur == 1) ? 2 : 1;
                                    uiTour.setTexte("TOUR : JOUEUR " + tourJoueur);
                                    uiTour.setCouleur((tourJoueur == 1) ? Couleur.BLEU : Couleur.ROUGE);
                                    pokemonSelectionne = false;

                                } else {
                                    carreSelection = new Rectangle(Couleur.ROUGE, new Point(selectX * tailleCase, selectY * tailleCase), tailleCase, tailleCase, false);
                                    fenetre.ajouter(carreSelection);
                                }
                            } else {
                                carreSelection = new Rectangle(Couleur.ROUGE, new Point(selectX * tailleCase, selectY * tailleCase), tailleCase, tailleCase, false);
                                fenetre.ajouter(carreSelection);
                            }
                        }                       
                    }
                    fenetre.rafraichir();
                }
            }
        }
    }
}