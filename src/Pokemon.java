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
}