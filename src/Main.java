public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   TEST DU SYSTEME DE COMBAT ET DU PARSING CSV   ");
        System.out.println("==================================================");

        System.out.println("[INFO] Chargement des données depuis ressources/pokedex_gen1.csv...");
        Pokemon bulbi = new Pokemon(1, "Bulbizarre");
        Pokemon salameche = new Pokemon(4, "Salamèche");

        System.out.println("\n[STATISTIQUES INITIALES]");
        System.out.println(bulbi.toString());
        System.out.println(salameche.toString());
        System.out.println("--------------------------------------------------");

        int tour = 1;
        while (bulbi.estVivant() && salameche.estVivant()) { 
            System.out.println("\n=== TOUR " + tour + " ===");
            
            bulbi.attaque(salameche);
            
            tour++;

        }

        System.out.println("==================================================");
        System.out.println("                FIN DU COMBAT                     ");
        System.out.println("==================================================");
        if (bulbi.estKO()) {
            System.out.println("Le vainqueur est : " + salameche.getNom() + " !");
        } else {
            System.out.println("Le vainqueur est : " + bulbi.getNom() + " !");
        }
        System.out.println("==================================================");
    }
}