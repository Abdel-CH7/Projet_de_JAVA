public class Plateau {
    public static final int TAILLE = 9; 
    
    private Pokemon[][] grille;
    private int[][] proprietaire; 

    public Plateau() {
        grille = new Pokemon[TAILLE][TAILLE];
        proprietaire = new int[TAILLE][TAILLE];
    }

    private boolean positionValide(int x, int y) {
        return x >= 0 && x < TAILLE && y >= 0 && y < TAILLE;
    }

    public void placerPokemon(Pokemon p, int prop, int x, int y) {
        if (positionValide(x, y)) {
            grille[x][y] = p;
            proprietaire[x][y] = prop;
        }
    }

    public Pokemon getPokemon(int x, int y) {
        if (!positionValide(x, y)) {
            return null;
        }

        return grille[x][y];
    }

    public int getProprietaire(int x, int y) {
        if (!positionValide(x, y)) {
            return 0;
        }

        return proprietaire[x][y];
    }

    public void deplacer(int xOrig, int yOrig, int xDest, int yDest) {
        if (!positionValide(xOrig, yOrig) || !positionValide(xDest, yDest)) {
            return;
        }

        if (grille[xOrig][yOrig] == null) {
            return;
        }

        if (grille[xDest][yDest] != null) {
            return;
        }

        grille[xDest][yDest] = grille[xOrig][yOrig];
        grille[xOrig][yOrig] = null;
        
        proprietaire[xDest][yDest] = proprietaire[xOrig][yOrig];
        proprietaire[xOrig][yOrig] = 0;
    }

    public boolean attaquer(int xAtt, int yAtt, int xDef, int yDef) {
        if (!positionValide(xAtt, yAtt) || !positionValide(xDef, yDef)) {
            return false;
        }

        Pokemon attaquant = grille[xAtt][yAtt];
        Pokemon defenseur = grille[xDef][yDef];

        if (attaquant == null || defenseur == null) {
            return false;
        }

        attaquant.attaque(defenseur);
        
        if (defenseur.estKO()) {
            grille[xDef][yDef] = null;
            proprietaire[xDef][yDef] = 0;
            return true; 
        }
        return false; 
    }
}