package compilationProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe principale représentant un automate fini
public class AEF {

    // Listes et structures de données pour représenter les états et les transitions de l'automate
    private List<String> etats;
    private Map<String, List<Pair>> transitions;
    private List<String> initials; // Liste des états initiaux
    private List<String> finals; // Liste des états finaux
    private String UTer; // Ensemble des étiquettes terminales

    // Constructeur de la classe AEF
    public AEF(String UTer) {
        // Initialisation des structures de données
        this.etats = new ArrayList<>();
        this.transitions = new HashMap<>();
        this.initials = new ArrayList<>();
        this.finals = new ArrayList<>();
        this.UTer = "";

        // Ajoute chaque étiquette terminale à l'ensemble UTer, en éliminant les doublons
        for (char s : UTer.toCharArray()) {
            if (this.UTer.indexOf(s) == -1) {
                this.UTer += s;
            }
        }
    }

    // Méthode pour ajouter un état à l'automate
    public void ajoutEtat(String etat, boolean finalEtat, boolean initEtat) {
        if (etats.contains(etat)) {
            System.out.println("error: l'etat " + etat + " existe déjà.");
            return;
        }
        transitions.put(etat, new ArrayList<Pair>());
        etats.add(etat);
        if (finalEtat) {
            finals.add(etat);
        }
        if (initEtat) {
            initials.add(etat);
        }
    }

    // Méthode pour valider une étiquette
    public boolean validateEtiquette(char etiquette) {
        return UTer.indexOf(etiquette) != -1;
    }

    // Méthode pour obtenir l'état de destination d'une transition
    public String getDestinationEtat(String srcEtat, char etiquette) {
        if (!etats.contains(srcEtat)) {
            System.out.println("error: the state " + srcEtat + " is not an existing state.");
            return null;
        }

        for (Pair pair : transitions.get(srcEtat)) {
            if (pair.getEtiquette() == etiquette) {
                return pair.getDestinationEtat();
            }
        }
        return null;
    }

    // Méthode pour ajouter une transition à l'automate
    public void addTransition(String srcEtat, char etiquette, String destEtat) {
        if (!validateEtiquette(etiquette)) {
            System.out.println("error: le symbole " + etiquette + " ne fait pas partie de T.");
            return;
        }

        if (!etats.contains(srcEtat) || !etats.contains(destEtat)) {
            System.out.println("error: l'etat source ou l'etat destination n'existe pas.");
            return;
        }

        if (getDestinationEtat(srcEtat, etiquette) != null) {
            System.out.println("error: la transition(" + srcEtat + "," + etiquette + ",...) existe déjà.");
            return;
        }

        // Ajoute la transition à la liste des transitions pour l'état source
        transitions.get(srcEtat).add(new Pair(etiquette, destEtat));
    }

    // Méthode pour afficher une représentation procédurale directe de l'automate
    public void affichageProcDirecte() {
        for (String etat : etats) {
            System.out.println("void etat " + etat + "() {");
            System.out.println("    char c;");
            System.out.println("    c = getchar();");
            System.out.println("    switch(c) {");

            for (Pair pair : transitions.get(etat)) {
                System.out.println("        case '" + pair.getEtiquette() + "': etat " + pair.getDestinationEtat() + "(); break;");
            }

            System.out.println("        default: Erreur();");
            System.out.println("    }");
            System.out.println("}\n");
        }
    }

    // Méthode pour effectuer l'analyse lexicale depuis les états initiaux
    public void analyserLexical(String input) {
        for (String initialState : initials) {
            String currentEtat = initialState;

            for (char c : input.toCharArray()) {
                if (!validateEtiquette(c)) {
                    System.out.println("Erreur: Le symbole '" + c + "' n'est pas un symbole terminal valide.");
                    return;
                }

                String destinationEtat = getDestinationEtat(currentEtat, c);

                if (destinationEtat != null) {
                    currentEtat = destinationEtat;
                } else {
                    System.out.println("Erreur: Transition invalide pour le symbole '" + c + "' depuis l'état '" + currentEtat + "'.");
                    return;
                }
            }

            if (finals.contains(currentEtat)) {
                System.out.println("Analyse lexicale réussie.");
                return; // Sortir de la méthode dès qu'une analyse réussie est trouvée
            }
        }

        System.out.println("Erreur: Aucune analyse lexicale réussie depuis les états initiaux.");
    }
}

// Classe représentant une paire de symbole et d'état de destination dans une transition
class Pair {
    private char etiquette;
    private String destinationEtat;

    // Constructeur de la classe Pair
    public Pair(char etiquette, String destinationEtat) {
        this.etiquette = etiquette;
        this.destinationEtat = destinationEtat;
    }

    // Méthode pour obtenir l'étiquette de la transition
    public char getEtiquette() {
        return etiquette;
    }

    // Méthode pour obtenir l'état de destination de la transition
    public String getDestinationEtat() {
        return destinationEtat;
    }
}





