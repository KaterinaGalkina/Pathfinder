package verificationEntree;

import filtrage.Trie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import peuple.*;
import territoire.*;

/**
 * Cette classe contient des méthodes de validation utilisées dans le processus de tri. 
 * Elles permettent de vérifier les critères de tri fournis par l'utilisateur pour les départements, les codes postaux, 
 * les villes, les régions, les disciplines, les fonctions, etc.
 * @version 1.0
 */
public class VerificationTrieTerminal {

	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private VerificationTrieTerminal() {
		
	}
	
    /**
     * Vérifie si un intervalle est valide (si min est inférieur ou égal à max).
     * @param min La valeur minimale de l'intervalle.
     * @param max La valeur maximale de l'intervalle.
     * @return true si l'intervalle est valide, sinon false.
     */
    public static Boolean verifIntervalle(double min, double max) {
        if (min > max){
            System.out.println("Erreur : min > max, veuillez recommancer.");
            return(false);
        }
        return(true);
    }
  
    /**
     * Vérifie si les départements fournis par l'utilisateur sont valides.
     * @param villes Liste des villes disponibles pour extraire les départements.
     * @param departement Liste dans laquelle seront ajoutés les départements valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins un département valide est sélectionné, sinon false.
     */
    public static Boolean VerifDepartement(ArrayList<Ville> villes, ArrayList<String> departement, Scanner scanner){
        System.out.println("Vous avez choisi de trier par départements. Veuillez fournir les noms des départements à considérer, séparés par des virgules");
        String textedep="";
        ArrayList<String> dep= new ArrayList<>();
        for (Ville v : villes){
            dep.add(v.getDepartement().toLowerCase());
            textedep+=v.getDepartement().toLowerCase()+", ";
        }
        System.out.println("voici les départements possibles : "+textedep);
        String[] codedepartements= scanner.nextLine().toLowerCase().replaceAll("\\s*,\\s*", ",").trim().split(",");
        Iterator<String> iterator = Arrays.asList(codedepartements).iterator();
        while (iterator.hasNext()) {
            String d = iterator.next().trim();
            if (dep.contains(d)) {
                departement.add(d.toLowerCase());
            }
            else {
                System.out.println("Erreur : le département \"" + d + "\" n'existe pas.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        while (true) {
                            System.out.println("Veuillez fournir un département valide parmi les personnes sélectionnées :");
                            String nouveaudep = scanner.nextLine().trim().toLowerCase();
                            if (dep.contains(nouveaudep)) {
                                departement.add(nouveaudep);
                                break;
                            } else {
                                System.out.println("Le département \"" + nouveaudep + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //si supprimer rien faire
                    case "recommencer" -> { return false; }
                }
            }
        }
        return !departement.isEmpty();
    }
    /**
     * Vérifie si les régions fournies par l'utilisateur sont valides.
     * @param regionsSelected Liste dans laquelle seront ajoutées les régions valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins une région valide est sélectionnée, sinon false.
     */
    public static Boolean VerifNomRegion(ArrayList<Region> regionsSelected, Scanner scanner){
        System.out.println("Vous avez choisi de trier par région. Veuillez fournir les noms des région à considérer, séparés par des virgules");
        String texte="";
        for(Region r : Region.values()){
            texte+=r.name()+", ";
        }
        System.out.println("voici les région possibles : "+texte);
        String[] nomRegions=scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
        Iterator<String> iterator = Arrays.asList(nomRegions).iterator(); // Utilisation d'un iterator pour modifier la liste en cours    
        while (iterator.hasNext()) {
            String nom = iterator.next().trim();
            Boolean valide = false;
            // Vérifier si la region est valide
            for (Region region : Region.values()) {
                if (region.name().equalsIgnoreCase(nom)) {
                    regionsSelected.add(region); // Ajouter la region valide
                    valide = true;
                    break;
                }
            }
            if (!valide) {
                System.out.println("Erreur : la région \"" + nom + "\" n'existe pas ou a été mal saisie.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        while (true) {
                            System.out.println("Veuillez fournir une région valide :");
                            String nouvelleRegion = scanner.nextLine().trim();
                            for (Region reg : Region.values()) {
                                if (reg.name().equalsIgnoreCase(nouvelleRegion)) {
                                    regionsSelected.add(reg);
                                    valide = true;
                                    break;
                                }
                            }
                            if (valide) {
                                break; // Quitter la boucle après avoir trouvé une region valide
                            } else {
                                System.out.println("La région \"" + nouvelleRegion + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //cas supprimer on ne fait rien
                    case "recommencer" -> {
                        return false;
                    }
                }
            }
        }
        return !regionsSelected.isEmpty();
    }
    /**
     * Vérifie si les villes fournies par l'utilisateur sont valides.
     * @param villes Liste des villes disponibles pour extraire les noms.
     * @param NomsVilles Liste dans laquelle seront ajoutés les noms de villes valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins une ville valide est sélectionnée, sinon false.
     */
    public static Boolean VerifNomVille(ArrayList<Ville> villes, ArrayList<String> NomsVilles, Scanner scanner){
        System.out.println("Vous avez choisi de trier par nom. Veuillez fournir les noms de villes à considérer, séparés par des virgules (Attention s'il y a des villes avec le même nom il faura faire un tri par département)");
        String texte="";
        ArrayList<String> nomvilles= new ArrayList<>();
        for (Ville v : villes){
            nomvilles.add(v.getNom().toLowerCase());
            texte+=v.getNom().toLowerCase()+", ";
        }
        System.out.println("voici les villes possibles : "+texte);
        String[] nomVille=scanner.nextLine().toLowerCase().replaceAll("\\s*,\\s*", ",").trim().split(",");

        Iterator<String> iterator = Arrays.asList(nomVille).iterator();
        while (iterator.hasNext()) {
            String ville = iterator.next().trim();
            if (nomvilles.contains(ville)) {
                NomsVilles.add(ville.toLowerCase());
            }
            else {
                System.out.println("Erreur : la ville \"" + ville + "\" n'existe pas.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        while (true) {
                            System.out.println("Veuillez fournir une ville valide parmi les personnes sélectionnées :");
                            String nouvelleville = scanner.nextLine().trim().toLowerCase();
                            if (nomvilles.contains(nouvelleville)) {
                                NomsVilles.add(nouvelleville);
                                break;
                            } else {
                                System.out.println("La ville \"" + nouvelleville + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //si supprimer rien faire
                    case "recommencer" -> { return false; }
                }
            }
        }
        return !NomsVilles.isEmpty();
    }
    /**
     * Vérifie si les villes chef-lieu fournies par l'utilisateur sont valides.
     * @param villes Liste des villes disponibles pour extraire les noms.
     * @param regions Liste des régions disponibles pour extraire les villes chef-lieu.
     * @param NomsVilles Liste dans laquelle seront ajoutées les villes chef-lieu valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins une ville chef-lieu valide est sélectionnée, sinon false.
     */
    public static Boolean VerifVilleChefLieu(ArrayList<Ville> villes, ArrayList<Region> regions, ArrayList<String> NomsVilles, Scanner scanner){
        System.out.println("Vous avez choisi de trier par ville chef-lieu. Veuillez fournir les noms des ville chef-lieu à considérer, séparés par des virgules");
        String texte="";
        ArrayList<String> VilleCheflieu= new ArrayList<>();
        for (Region reg : regions){
            VilleCheflieu.add(reg.getChefLieu().getNom().toLowerCase());
            texte+=reg.getChefLieu().getNom().toLowerCase()+", ";
        }
        System.out.println("voici les villes chef-lieu possibles : "+texte);
        String[] nomCheflieu=scanner.nextLine().toLowerCase().replaceAll("\\s*,\\s*", ",").trim().split(",");
        Iterator<String> iterator = Arrays.asList(nomCheflieu).iterator();
        while (iterator.hasNext()) {
            String ville = iterator.next().trim();
            if (VilleCheflieu.contains(ville)) {
                NomsVilles.add(ville.toLowerCase());
            }
            else {
                System.out.println("Erreur : la ville \"" + ville + "\" n'existe pas ou n'est pas une ville chef lieu.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        while (true) {
                            System.out.println("Veuillez fournir une ville chef lieu valide parmi les personnes sélectionnées :");
                            String nouvelleville = scanner.nextLine().trim().toLowerCase();
                            if (VilleCheflieu.contains(nouvelleville)) {
                                NomsVilles.add(nouvelleville);
                                break;
                            } else {
                                System.out.println("La ville \"" + nouvelleville + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //si supprimer rien faire
                    case "recommencer" -> { return false; }
                }
            }
        }
        return !NomsVilles.isEmpty();
    }

    /**
     * Vérifie si les disciplines fournies par l'utilisateur sont valides.
     * @param disciplines Liste dans laquelle seront ajoutees les disciplines valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins une discipline valide est sélectionnée, sinon false.
     */
    public static Boolean VerifDiscipline(ArrayList<Discipline> disciplines, Scanner scanner) {
        System.out.println("Veuillez fournir les disciplines : mathematiques, informatique, gestion, droit, sciences sociales (séparées par des virgules) :");
        String[] nomsDisciplines = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");

        Iterator<String> iterator = Arrays.asList(nomsDisciplines).iterator(); // Utilisation d'un iterator pour modifier la liste en cours    
        while (iterator.hasNext()) {
            String nom = iterator.next().trim();
            Boolean valide = false;
            // Verifier si la discipline est valide
        	Discipline disc = Discipline.getDiscipline(nom);
            if (disc != null) {
                disciplines.add(disc); // Ajouter la discipline valide
                valide = true;
            }
            
            if (!valide) {
                // System.out.println("Erreur : la discipline \"" + nom + "\" n'existe pas ou a été mal saisie.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        // Demander une nouvelle discipline valide
                        while (true) {
                            System.out.println("Veuillez fournir une discipline valide (mathematiques, informatique, gestion, droit, sciences sociales) :");
                            String nouvelleDiscipline = scanner.nextLine().trim();
                            disc = Discipline.getDiscipline(nom);
                            if (disc != null) {
                                disciplines.add(disc); // Ajouter la discipline valide
                                valide = true;
                                break;
                            } else {
                                System.out.println("La discipline \"" + nouvelleDiscipline + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //cas supprimer on ne fait rien
                    case "recommencer" -> {
                        return false;
                    }
                }
            }
        }
        return !disciplines.isEmpty();
    }
    /**
     * Vérifie si les fonctions fournies par l'utilisateur sont valides.
     * @param Fonctions Liste dans laquelle seront ajoutées les fonctions valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins une fonction valide est sélectionnée, sinon false.
     */
    public static Boolean VerifFonction(ArrayList<String> Fonctions, Scanner scanner) {
        System.out.println("Veuillez fournir les fonctions (chercheur, etudiant, titulaire, mcf) séparées par des virgules :");
        String[] fonctionSelected = scanner.nextLine().toLowerCase().replaceAll("\\s*,\\s*", ",").trim().split(",");
        // Vérifier si aucune fonction n'a été saisie
        if (fonctionSelected.length == 0 || (fonctionSelected.length == 1 && fonctionSelected[0].isEmpty())) {
            System.out.println("Erreur : aucune fonction n'a été tapée.");
            return false;
        }
        ArrayList<String> fonctionsValides = new ArrayList<>(Arrays.asList("chercheur", "etudiant", "titulaire", "mcf")); //fonctions valides
        Boolean total = true;
        Iterator<String> iterator = Arrays.asList(fonctionSelected).iterator();
        while (iterator.hasNext()) {
            String fonction = iterator.next().trim();
            Boolean valide = fonctionsValides.contains(fonction.toLowerCase());
            if (valide) {
                Fonctions.add(fonction); // Ajouter la fonction valide
            }
            else {
                System.out.println("Erreur : la fonction \"" + fonction + "\" n'existe pas ou a été mal saisie.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        // Demander une nouvelle fonction valide
                        while (true) {
                            System.out.println("Veuillez fournir une fonction valide (chercheur, etudiant, titulaire, mcf) :");
                            String nouvelleFonction = scanner.nextLine().trim().toLowerCase();
                            if (fonctionsValides.contains(nouvelleFonction)) {
                                Fonctions.add(nouvelleFonction); // Ajouter la nouvelle fonction valide
                                break;
                            }
                            else {
                                System.out.println("La fonction \"" + nouvelleFonction + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //si supprimer rien faire
                    case "recommencer" -> {
                        return false;
                    }
                }
            }
        }
        return total && !Fonctions.isEmpty();
    }
    /**
     * Vérifie si les fonctions fournies par l'utilisateur sont valides.
     * @param personnes Liste des personnes disponibles.
     * @param NomPrenom Liste des nom prenom des personnes selectionnees.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins une fonction valide est sélectionnée, sinon false.
     */
    public static Boolean VerifNomPrenom(ArrayList<Personne> personnes, ArrayList<String> NomPrenom, Scanner scanner) {
        System.out.println("Veuillez fournir les noms et prénoms (chaque couple séparé par des virgules) :");
        String texte="";
        ArrayList<String> nomPersonnes = new ArrayList<>();
        for (Personne p : personnes) {
            nomPersonnes.add(p.getNomPrenom().toLowerCase());
            texte+=p.getNomPrenom().toLowerCase()+", ";
        }
        System.out.println("voici les personnes possibles : "+texte);
        String[] NomsPrenoms = scanner.nextLine().trim().toLowerCase().replaceAll("\\s*,\\s*", ",").split(",");
        
        Iterator<String> iterator = Arrays.asList(NomsPrenoms).iterator();
        while (iterator.hasNext()) {
            String np = iterator.next().trim();
            if (nomPersonnes.contains(np)) {
                NomPrenom.add(np);
            }
            else {
                System.out.println("Erreur : le Nom Prénom \"" + np + "\" n'existe pas ou n'est pas dans les personnes sélectionnées.");
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        while (true) {
                            System.out.println("Veuillez fournir un nom prénom valide parmi les personnes sélectionnées :");
                            String nouveauNomPrenom = scanner.nextLine().trim().toLowerCase();
                            if (nomPersonnes.contains(nouveauNomPrenom)) {
                                NomPrenom.add(nouveauNomPrenom); // Ajouter le nom-prénom valide
                                break;
                            } else {
                                System.out.println("Le Nom Prénom \"" + nouveauNomPrenom + "\" n'est pas valide. Réessayez.");
                            }
                        }
                    } //si supprimer rien faire
                    case "recommencer" -> { return false; }
                }
            }
        }
        return !NomPrenom.isEmpty();
    }
    /**
     * Vérifie si les IDs fournis par l'utilisateur sont valides.
     * @param personnes Liste des personnes disponibles pour extraire les IDs.
     * @param ID Liste dans laquelle seront ajoutés les IDs valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins un ID valide est sélectionné, sinon false.
     */
    public static Boolean VerifID(ArrayList<Personne> personnes, ArrayList<Integer> ID, Scanner scanner) {
        System.out.println("Veuillez fournir les IDs (séparés par des virgules) :");
        String texte="";
        ArrayList<Integer> idPersonne = new ArrayList<>();
        for (Personne p : personnes) {
            idPersonne.add(p.getID());
            texte+=p.getID()+", ";
        }
        System.out.println("voici les ids possibles : "+texte);
        String[] IDs = scanner.nextLine().trim().replaceAll("\\s*,\\s*", ",").split(",");
        ArrayList<Integer> IDsasit= new ArrayList<>();
        // Conversion des entrées en nombres entiers
        for (String part : IDs) {
            try {
                IDsasit.add(Integer.valueOf(part.trim())); // Supprimer les espaces avant la conversion
            } catch (NumberFormatException e) {
                System.out.println("L'entrée \"" + part + "\" n'est pas un nombre entier valide et sera ignorée.");
                // L'entrée invalide est ignorée
            }
        }
        
        Iterator<Integer> iterator = IDsasit.iterator(); // Utilisation d'un iterator pour modifier la liste en cours de parcours
        while (iterator.hasNext()) {
            Integer currentID = iterator.next();
            if (!idPersonne.contains(currentID)) {
                System.out.println("Erreur de saisie : l'ID " + currentID + " n'existe pas."); 
                // Proposer une action corrective
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        // Demander un nouvel ID valide
                        System.out.println("Veuillez fournir un nouvel ID valide :");
                        int nouvelID;
                        while (true) {
                            try {
                                nouvelID = Integer.parseInt(scanner.nextLine().trim());
                                if (idPersonne.contains(nouvelID)) {
                                    iterator.remove(); // Supprimer l'ancien ID invalide
                                    ID.add(nouvelID); // Ajouter le nouvel ID valide
                                    break;
                                } else {
                                    System.out.println("Le nouvel ID " + nouvelID + " n'existe pas. Réessayez.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Veuillez entrer un nombre entier valide.");
                            }
                        }
                    }
                    case "recommencer" -> {return false;}
                }
            }
            else{
                ID.add(currentID);
            }
        }
        return true;
    }
    /**
     * Vérifie si les titulaires sélectionnés par l'utilisateur sont des étudiants valides.
     * @param personnes Liste des personnes disponibles pour extraire les titulaires.
     * @param Etudiants Liste dans laquelle seront ajoutés les étudiants valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins un étudiant valide est sélectionné, sinon false.
     */
    public static Boolean VerifTitulaire(ArrayList<Personne> personnes, ArrayList<Personne> Etudiants , Scanner scanner){
        String message = "vous avez choisit de trier des titulaires en fonction de leurs étudiants :\n- 'id' : si vous connaissez les ids des étudiants\n- 'nom' : si vous connaissez les noms et prenoms des étudiants";
        String CHOIX=Coherence.verificationOption(scanner, Set.of("id", "nom"), message);
        ArrayList<Personne> SaisitEtudiants = new ArrayList<>();
        if(CHOIX.equals("id")){
            ArrayList<Integer> ID = new ArrayList<>();
            if(!VerificationTrieTerminal.VerifID(personnes, ID, scanner)){
                return(false);
            }
            SaisitEtudiants = Trie.TrieID(personnes, ID);
        }
        else if(CHOIX.equals("nom")){
            ArrayList<String> NomPrenoms = new ArrayList<>();
            if(!VerificationTrieTerminal.VerifNomPrenom(personnes, NomPrenoms, scanner)){
                return(false);
            }
            SaisitEtudiants = Trie.TrieNomPrenom(personnes, NomPrenoms);
        }
        // Vérification que toutes les personnes sélectionnées sont bien des étudiants
        Iterator<Personne> iterator = SaisitEtudiants.iterator();
        while (iterator.hasNext()) {
            Personne pers = iterator.next();
            if (!(pers instanceof Etudiant)) {
                System.out.println("Erreur : " + pers.getNomPrenom() + " n'est pas un étudiant.");
                String action = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (action) {
                    case "modifier" -> {
                        if (CHOIX.equals("id")) {
                            while (true) {
                                System.out.println("Veuillez fournir un ID valide d'étudiant :");
                                try {
                                    int newID = Integer.parseInt(scanner.nextLine().trim());
                                    Personne newPers = personnes.stream()
                                            .filter(p -> p instanceof Etudiant && p.getID() == newID)
                                            .findFirst()
                                            .orElse(null);
                                    if (newPers != null) {
                                        Etudiants.add(newPers); // Ajoute l'étudiant valide
                                        break;
                                    } else {
                                        System.out.println("ID invalide ou ne correspond pas à un étudiant. Réessayez.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                                }
                            }
                        }
                        else if (CHOIX.equals("nom")) {
                            while (true) {
                                System.out.println("Veuillez fournir un nom-prénom valide d'étudiant :");
                                String newNomPrenom = scanner.nextLine().trim();
                                Personne newPers = personnes.stream()
                                        .filter(p -> p instanceof Etudiant && p.getNomPrenom().equalsIgnoreCase(newNomPrenom))
                                        .findFirst()
                                        .orElse(null);
                                if (newPers != null) {
                                    Etudiants.add(newPers); // Ajoute l'étudiant valide
                                    break;
                                } else {
                                    System.out.println("Nom-prénom invalide ou ne correspond pas à un étudiant. Réessayez.");
                                }
                            }
                        }
                    }
                    case "recommencer" -> {
                        return false; // Recommencer tout le processus
                    }
                }
            }
            else{
                Etudiants.add(pers);
            }
        }
        return !Etudiants.isEmpty();
    }
    /**
     * Vérifie si les sujets de thèse fournis par l'utilisateur sont valides.
     * @param personnes Liste des personnes disponibles pour extraire les sujets de thèse.
     * @param Sujets Liste dans laquelle seront ajoutés les sujets valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins un sujet valide est sélectionné, sinon false.
     */
    public static Boolean VerifSujetDeThese(ArrayList<Personne> personnes, ArrayList<String> Sujets, Scanner scanner){
        System.out.println("Vous avez choisi de trier par sujet de thèse. Veuillez fournir les noms des sujets à considérer, séparés par des virgules.");
        String texte="";
        ArrayList<String> SujetDeThese = new ArrayList<>();
        ArrayList<Etudiant> etudiants = Trie.convertionEtudiants(personnes);
        for (Etudiant p : etudiants) {
            SujetDeThese.add(p.getSujetDeThese().toLowerCase());
            texte+=p.getSujetDeThese().toLowerCase()+", ";
        }
        System.out.println("voici les sujet possibles : "+texte);
        String[] sujetsInput= scanner.nextLine().toLowerCase().replaceAll("\\s*,\\s*", ",").trim().split(",");
        Iterator<String> iterator = Arrays.asList(sujetsInput).iterator();
        while (iterator.hasNext()) {
            String sujet = iterator.next().trim();
            if (!SujetDeThese.contains(sujet)) {
                System.out.println("Erreur : le sujet de thèse \"" + sujet + "\" n'existe pas.");
                String action = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (action) {
                    case "modifier" -> {
                        while (true) {
                            System.out.println("Veuillez fournir un sujet valide parmi ceux existants :");
                            String nouveauSujet = scanner.nextLine().trim().toLowerCase();
                            if (SujetDeThese.contains(nouveauSujet)) {
                                // Remplacer le sujet incorrect par un sujet valide
                                Sujets.add(nouveauSujet);
                                break;
                            } else {
                                System.out.println("Le sujet \"" + nouveauSujet + "\" n'existe toujours pas. Réessayez.");
                            }
                        }
                    }
                    //case "supprimer" -> iterator.remove();
                    case "recommencer" -> {return false;}
                }
            }
            else {
                Sujets.add(sujet);
            }
        }
        return !Sujets.isEmpty();
    }
    /**
     * Vérifie si les encadrants fournis par l'utilisateur sont valides.
     * @param personnes Liste des personnes disponibles pour extraire les encadrants.
     * @param Encadrant Liste dans laquelle seront ajoutés les encadrants valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins un encadrant valide est sélectionné, sinon false.
     */
    public static Boolean VerifEncadrant(ArrayList<Personne> personnes, ArrayList<Personne> Encadrant , Scanner scanner) {
        String message = "vous avez choisit de trier des étudiants en fonction de leur encadrant :\n- 'id' : si vous connaissez les ids des encadrants\n- 'nom' : si vous connaissez les noms et prénoms des encadrants";
        String CHOIX = Coherence.verificationOption(scanner, Set.of("id", "nom"), message);
        ArrayList<Personne> SaisitEncadrant = new ArrayList<>();
        if(CHOIX.equals("id")){
            ArrayList<Integer> ID = new ArrayList<>();
            if(!VerificationTrieTerminal.VerifID(personnes, ID, scanner)){
                return(false);
            }
            SaisitEncadrant = Trie.TrieID(personnes, ID);
        }
        else if(CHOIX.equals("nom")){
            ArrayList<String> NomPrenoms = new ArrayList<>();
            if(!VerificationTrieTerminal.VerifNomPrenom(personnes, NomPrenoms, scanner)){
                return(false);
            }
            SaisitEncadrant = Trie.TrieNomPrenom(personnes, NomPrenoms);
        }
        // Vérification que toutes les personnes sélectionnées sont bien des étudiants
        Iterator<Personne> iterator = SaisitEncadrant.iterator();
        while (iterator.hasNext()) {
            Personne pers = iterator.next();
            if (!(pers instanceof Titulaire)) {
                System.out.println("Erreur : " + pers.getNomPrenom() + " n'est pas un Titulaire.");
                String action = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (action) {
                    case "modifier" -> {
                        if (CHOIX.equals("id")) {
                            while (true) {
                                System.out.println("Veuillez fournir un ID valide de titulaire :");
                                try {
                                    int newID = Integer.parseInt(scanner.nextLine().trim());
                                    Personne newPers = personnes.stream()
                                            .filter(p -> p instanceof Etudiant && p.getID() == newID)
                                            .findFirst()
                                            .orElse(null);
                                    if (newPers != null) {
                                        Encadrant.add(newPers); // Ajoute l'étudiant valide
                                        break;
                                    } else {
                                        System.out.println("ID invalide ou ne correspond pas à un titulaire. Réessayez.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Entrée invalide, veuillez entrer un nombre.");
                                }
                            }
                        }
                        else if (CHOIX.equals("nom")) {
                            while (true) {
                                System.out.println("Veuillez fournir un nom-prénom valide de titulaire :");
                                String newNomPrenom = scanner.nextLine().trim();
                                Personne newPers = personnes.stream()
                                        .filter(p -> p instanceof Etudiant && p.getNomPrenom().equalsIgnoreCase(newNomPrenom))
                                        .findFirst()
                                        .orElse(null);
                                if (newPers != null) {
                                    Encadrant.add(newPers); // Ajoute l'étudiant valide
                                    break;
                                } else {
                                    System.out.println("Nom-prénom invalide ou ne correspond pas à un titulaire. Réessayez.");
                                }
                            }
                        }
                    }
                    case "recommencer" -> {
                        return false; // Recommencer tout le processus
                    }
                }
            }
            else{
                Encadrant.add(pers);
            }
        }
        return !Encadrant.isEmpty();

    }
    /**
     * Vérifie si les numéros de bureau fournis par l'utilisateur sont valides.
     * @param personnes Liste des personnes disponibles pour extraire les numéros de bureau.
     * @param numBureau Liste dans laquelle seront ajoutés les numéros de bureau valides.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée de l'utilisateur.
     * @return true si au moins un numéro de bureau valide est sélectionné, sinon false.
     */
    public static Boolean VerifNumBureau(ArrayList<Personne> personnes, ArrayList<Integer> numBureau, Scanner scanner){
        System.out.println("Veuillez fournir les numéros de bureaux (séparés par des virgules) :");
        String texte="";
        ArrayList<Integer> num = new ArrayList<>();
        ArrayList<Titulaire> titulaires=Trie.convertionTitulaires(personnes);
        for (Titulaire p : titulaires){
            num.add(p.getNumBureau());
            texte+=p.getNumBureau()+", ";
        }
        System.out.println("voici les numéros de bureaux possibles : "+texte);
        String[] NumBureau = scanner.nextLine().trim().replaceAll("\\s*,\\s*", ",").trim().split(",");
        ArrayList<Integer> numBureausaisit= new ArrayList<>();
        for (String part : NumBureau) {
            try {
                numBureausaisit.add(Integer.valueOf(part.trim())); // Supprimer les espaces avant la conversion
            } catch (NumberFormatException e) {
                System.out.println("L'entrée \"" + part + "\" n'est pas un nombre entier valide et sera ignorée.");
                // L'entrée invalide est ignorée
            }
        }
        Iterator<Integer> iterator = numBureausaisit.iterator();
        while (iterator.hasNext()) {
            Integer currentBureau = iterator.next();
            if (!num.contains(currentBureau)) {
                System.out.println("Erreur de saisie : le numero de bureau " + currentBureau + " n'existe pas."); 
                // Proposer une action corrective
                String choix = Coherence.verificationOption(scanner, Set.of("modifier", "supprimer", "recommencer"), "Que voulez-vous faire ? (modifier, supprimer, recommencer)");
                switch (choix) {
                    case "modifier" -> {
                        System.out.println("Veuillez fournir un nouvel numero de bureau valide :");
                        int nouvelnumBureau;
                        while (true) {
                            try {
                                nouvelnumBureau = Integer.parseInt(scanner.nextLine().trim());
                                if (num.contains(nouvelnumBureau)) {
                                    numBureau.add(nouvelnumBureau);
                                    break;
                                } else {
                                    System.out.println("Le nouvel numero de bureau " + nouvelnumBureau + " n'existe pas. Réessayez.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Veuillez entrer un nombre entier valide.");
                            }
                        }
                    }
                    case "recommencer" -> {return false;}
                }
            }
            else{
                numBureau.add(currentBureau);
            }
        }
        return(true);
    }
    
}