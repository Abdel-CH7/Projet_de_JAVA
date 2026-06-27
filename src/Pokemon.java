import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pokemon {

    private static final String CHEMIN_CSV = "ressources/pokedex_gen1.csv";

    private int numPokedex;
    private String nom;
    private int pv;         
    private int pvMax;
    private int attaque;    
    private int defense;    
    private int vitesse;
    private int type1;
    private int type2;

    public Pokemon() {
        this(25, "Pikachu");
    }
    
    public Pokemon(int numPokedex, String nom, int pv, int attaque, int defense, int vitesse, int type1, int type2) {
        this.numPokedex = numPokedex;
        this.nom = nom;
        this.pvMax = pv;
        this.pv = pv; 
        this.attaque = attaque;
        this.defense = defense;
        this.vitesse = vitesse;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getNom() { return nom; }
    public int getNumPokedex() { return numPokedex; }
    public int getPv() { return pv; }
    public int getPvMax() { return pvMax; }
    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getVitesse() { return vitesse; }
    public int getType1() { return type1; }
    public int getType2() { return type2; }

    public Pokemon(int numPokedex, String surnom) {
        this(numPokedex, surnom, CHEMIN_CSV);
    }


    public Pokemon(int numPokedex, String surnom, String cheminCSV) {
        this.numPokedex = numPokedex;
        String ligne = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(cheminCSV))) {
            while ((ligne = br.readLine()) != null) {
                String[] colonnes = ligne.split(";");
                try {
                    int currentNum = Integer.parseInt(colonnes[0]);
                    if (currentNum == numPokedex) {
                        if (surnom != null && !surnom.trim().isEmpty()) {
                            this.nom = surnom;
                        } else {
                            this.nom = Type.getEspece(numPokedex);
                        }
                        
                        this.type1 = Type.getIndiceType(colonnes[2]);
                        if (colonnes.length > 3 && !colonnes[3].trim().isEmpty()) {
                            this.type2 = Type.getIndiceType(colonnes[3]);
                        } else {
                            this.type2 = Type.SANS;
                        }
                        
                        this.pvMax = Integer.parseInt(colonnes[4]); 
                        this.pv = this.pvMax;                       
                        this.attaque = Integer.parseInt(colonnes[5]);
                        this.defense = Integer.parseInt(colonnes[6]);
                        this.vitesse = Integer.parseInt(colonnes[7]);
                        break; 
                    }
                } catch (NumberFormatException e) {
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier CSV: " + e.getMessage());
        }
    }

    public boolean estKO() { return this.pv <= 0; }
    public boolean estVivant() { return !estKO(); }
    public void soigner() { this.pv = this.pvMax; }

    public String toString() { 
        return this.nom + " (PV: " + this.pv + "/" + this.pvMax + ")"; 
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pokemon autre = (Pokemon) obj;
        return this.nom.equals(autre.nom) && this.pvMax == autre.pvMax;
    }

    public void attaque(Pokemon adversaire) {
        Pokemon premier = this;
        Pokemon second = adversaire;
        
        if (adversaire.getVitesse() > this.getVitesse()) {
            premier = adversaire;
            second = this;
        }
        System.out.println("--- " + premier.getNom() + " est le plus rapide et attaque en premier ! ---");
        
        frapper(premier, second);

        if (!second.estKO()) {
            System.out.println("--- " + second.getNom() + " contre-attaque ! ---");
            frapper(second, premier);
        } else {
            System.out.println(second.getNom() + " est K.O. ! Il ne peut pas contre-attaquer.");
        }
    }

    private void frapper(Pokemon attaquant, Pokemon defenseur) {
        double efficacite1 = Type.getEfficacite(attaquant.getType1(), defenseur.getType1());
        double efficacite2 = 1.0;
        
        if (defenseur.getType2() != Type.SANS) {
            efficacite2 = Type.getEfficacite(attaquant.getType1(), defenseur.getType2());
        }
        
        double multiplicateurTotal = efficacite1 * efficacite2;
        
        int degats = attaquant.getAttaque() - defenseur.getDefense();
        
        if (degats < 1) {
            degats = 1;
        }

        degats = (int) (degats * multiplicateurTotal);

        if (degats < 1) {
            degats = 1;
        }

        if (multiplicateurTotal > 1) {
            System.out.println("C'est très efficace !");
        } else if (multiplicateurTotal < 1) {
            System.out.println("Ce n'est pas très efficace...");
        }
        
        System.out.println(attaquant.getNom() + " inflige " + degats + " dégâts à " + defenseur.getNom() + ".");

        defenseur.pv = defenseur.pv - degats;
        if (defenseur.pv < 0) {
            defenseur.pv = 0; 
        }        
        System.out.println("Il reste " + defenseur.getPv() + " PV à " + defenseur.getNom() + ".\n");
    }
}