package compilationProject;

import java.util.Scanner;

public class AutomateApp {

    public static void main(String[] args) {
        automate();
    }

    public static void automate() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        // Initialiser l'automate une seule fois en dehors de la boucle
        System.out.println("Entrez les unites terminales:");
        String c = scanner.nextLine();
        AEF a = new AEF(c);

        while (continuer) {
            boolean ajouterEtats = true;
            boolean ajouterTransitions = true;

            while (ajouterEtats) {
                System.out.println("Voulez-vous ajouter un etat? oui/non");
                String i = scanner.nextLine();
                if ("oui".equalsIgnoreCase(i)) {
                    System.out.println("Saisir l'état:");
                    String state = scanner.nextLine();
                    System.out.println("C'est un état final? oui/non");
                    String isFinal = scanner.nextLine();
                    System.out.println("C'est un état initial? oui/non");
                    String isInitial = scanner.nextLine();
                    a.ajoutEtat(state, "oui".equalsIgnoreCase(isFinal),"oui".equalsIgnoreCase(isInitial));


                } else {
                    ajouterEtats = false;
                }
            }

            while (ajouterTransitions) {
                System.out.println("Voulez-vous ajouter une transition? oui/non");
                String i = scanner.nextLine();
                if ("oui".equalsIgnoreCase(i)) {
                    System.out.println("etat source:");
                    String srcState = scanner.nextLine();
                    System.out.println("Destination:");
                    String dstState = scanner.nextLine();
                    System.out.println("etiquette:");
                    char etiquette = scanner.next().charAt(0);
                    scanner.nextLine(); // Consume newline
                    a.addTransition(srcState, etiquette, dstState);
                } else {
                    ajouterTransitions = false;
                }
            }

            System.out.println("------------------- Representation procédurale directe --------------------");

            a.affichageProcDirecte();

            System.out.println("Entrez la chaîne à analyser:");
            String inputString = scanner.nextLine();

            // Effectuer l'analyse lexicale avec la chaîne fournie par l'utilisateur
            a.analyserLexical(inputString);

            System.out.println("Voulez-vous effectuer une nouvelle analyse sur le même automate? oui/non");
            continuer = "oui".equalsIgnoreCase(scanner.nextLine());
        }

        scanner.close();
    }
}







