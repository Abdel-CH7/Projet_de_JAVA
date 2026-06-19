import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pokemon {
    private int numPokedex;
    private String nom;
    private int hp;
    private int hpMax; 
    private int attaque;
    private int defense;
    private int vitesse;
    private int type1;
    private int type2;

    public Pokemon(String nom, int hp, int attaque, int defense, int vitesse, int type1, int type2) {
        this.numPokedex = 0;
        this.nom = nom;
        this.hpMax = hp;
        this.hp = hp; 
        this.attaque = attaque;
        this.defense = defense;
        this.vitesse = vitesse;
        this.type1 = type1;
        this.type2 = type2;
    }

    public String getNom() { return nom; }
    public int getNumPokedex() { return numPokedex; }
    public int getHp() { return hp; }
    public int getHpMax() { return hpMax; }
    public int getPv() { return hp; }
    public int getPvMax() { return hpMax; }
    public int getAttaque() { return attaque; }
    public int getDefense() { return defense; }
    public int getVitesse() { return vitesse; }
    public int getType1() { return type1; }
    public int getType2() { return type2; }

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
                        
                        this.hpMax = Integer.parseInt(colonnes[4]); 
                        this.hp = this.hpMax;
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
}