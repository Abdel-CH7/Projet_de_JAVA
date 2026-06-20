public class Plateau {
    public static final int TAILLE = 9; 
    
    private Pokemon[][] grille;
    private int[][] proprietaire; 

    public Plateau() {
        grille = new Pokemon[TAILLE][TAILLE];
        proprietaire = new int[TAILLE][TAILLE];
    }

    public void placerPokemon(Pokemon p, int prop, int x, int y) {
        if (x >= 0 && x < TAILLE && y >= 0 && y < TAILLE) {
            grille[x][y] = p;
            proprietaire[x][y] = prop;
        }
    }

    public Pokemon getPokemon(int x, int y) {
        return grille[x][y];
    }

    public int getProprietaire(int x, int y) {
        return proprietaire[x][y];
    }

    public void deplacer(int xOrig, int yOrig, int xDest, int yDest) {
        grille[xDest][yDest] = grille[xOrig][yOrig];
        grille[xOrig][yOrig] = null;
        
        proprietaire[xDest][yDest] = proprietaire[xOrig][yOrig];
        proprietaire[xOrig][yOrig] = 0;
    }

    public boolean attaquer(int xAtt, int yAtt, int xDef, int yDef) {
        Pokemon attaquant = grille[xAtt][yAtt];
        Pokemon defenseur = grille[xDef][yDef];
        
        attaquant.attaque(defenseur);
        
        if (defenseur.estKO()) {
            grille[xDef][yDef] = null;
            proprietaire[xDef][yDef] = 0;
            return true; 
        }
        return false; 
    }
}