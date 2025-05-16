package filtrage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import peuple.*;
import territoire.*;
import verificationEntree.*;

/**
 * Cette classe permet de trier une liste de personnes selon divers criteres choisis par l'utilisateur.
 * Les criteres incluent des villes, des disciplines, des fonctions, des ages, des sujets de thèse, et bien plus.
 * @version 1.0
 */
public class TrieTerminal {
    
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private TrieTerminal() {
		
	}
		
    /**
     * Permet de trier une liste de personnes selon des criteres choisis par l'utilisateur.
     * @param personnes Liste des personnes a trier.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     * @return La liste triée des personnes selon les critères choisis.
     */
    public static ArrayList<Personne> Trier(ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        ArrayList<Personne> selection = Trie.monClone(personnes); //copie personnes mais si selection modifié personnes ne l'ait pas
        ArrayList<Personne> teste;
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;
        while (continuer){
            String message = "Choisissez une méthode de tri :\n- 'ville' : Trier les personnes en fonction de leur ville.\n- 'personne' : Trier les personnes en fonctions de leur age, nom prénom ...\n- 'fin' : Terminer le tri.";
            
            String choix = Coherence.verificationOption(scanner, Set.of("ville", "personne", "fin") , message);
            if(choix.equals("ville")){
                teste  = trierParVille(selection, villes, regions, scanner);
            }
            else if (choix.equals("personne")){
                teste = trierParPersonne(selection,personnes, scanner);
                
            }
            else{
                continuer=false;
                teste = Trie.monClone(selection);
            }
            if (teste!=null && teste.isEmpty()){
                continuer=true;
                selection = Trie.monClone(personnes);
                System.out.println("erreur : aucune personne n'a été selectionnée, veuillez recommance votre dernier tri.");
            }
            else if (teste!=null){
                selection = Trie.monClone(teste);
            }

            if (teste!=null && continuer) {//car si test == nul alors recommancer donc oui forcément
                String reponse = Coherence.verificationOption(scanner, Set.of("oui", "non") , "Souhaitez-vous ajouter un autre critère ? (oui/non)"); 
                if (!reponse.equals("oui")) {
                    continuer = false;
                }
            }
        }
        return selection;
    }

    /**
     * Trie les personnes par ville selon les critères spécifiés.
     * @param selection Liste des personnes sélectionnées.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des régions disponibles.
     * @param scanner Objet Scanner pour la saisie de l'utilisateur.
     * @return Liste des personnes triées selon les critères de ville.
     */
    private static ArrayList<Personne> trierParVille(ArrayList<Personne> selection, ArrayList<Ville> villes, ArrayList<Region> regions, Scanner scanner) {
        ArrayList<Personne> result = new ArrayList<>();
        String message = "Choisissez un critère de tri pour les villes :\n- 'population' : Trier par population des villes/regions.\n- 'superficie' : Trier par superficie des villes/regions.\n- 'departement' : Trier par département des villes\n- 'region' : Trier par region des villes\n- 'nom' : Trier par noms des villes\n- 'ville chef-lieu' : Trier par la ville chef-lieu de leur region des villes\n- 'retour' : Retourner.";
        String choix = Coherence.verificationOption(scanner, Set.of("population","superficie","departement", "region", "nom", "ville chef-lieu", "retour"), message); 
        switch (choix) {
            case "population" -> {
                System.out.println("valeur min de population");
                double population1= Coherence.verificationDouble(scanner.nextLine().trim());
                System.out.println("valeur max de population");
                double population2= Coherence.verificationDouble(scanner.nextLine().trim()); 
                if (!VerificationTrieTerminal.verifIntervalle(population1, population2)){
                    return(null);
                }
                String CHOIX = Coherence.verificationOption(scanner, Set.of("ville", "region") , "population de :\n- 'ville'\n- 'region' ");
                if(CHOIX.equals("ville")){
                    result= Trie.TriePopulationVille(selection, population1, population2);
                }
                else if(CHOIX.equals("region")){
                    result = Trie.TriePopulationRegion(selection, population1, population2);
                }
            }
            case "superficie" -> {
                System.out.println("valeur min de superficie");
                double Superficie1=Coherence.verificationDouble(scanner.nextLine().trim());
                System.out.println("valeur max de superficie");
                double Superficie2=Coherence.verificationDouble(scanner.nextLine().trim());
                if (!VerificationTrieTerminal.verifIntervalle(Superficie1,Superficie2)){
                    return(null);
                }
                String CHOIX = Coherence.verificationOption(scanner, Set.of("ville", "region") , "population de :\n- 'ville'\n- 'region'");
                if(CHOIX.equals("ville")){
                    result= Trie.TrieSuperficieVille(selection, Superficie1, Superficie2);
                }
                else if(CHOIX.equals("region")){
                    result = Trie.TrieSuperficieRegion(selection, Superficie1, Superficie2);
                }
            }
            case "departement" -> {
                ArrayList<String> codeDepartement = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifDepartement(villes, codeDepartement, scanner)){
                    return(null);
                }
                result = Trie.TrieDepartement(selection, codeDepartement);
            }
            case "region" -> {
                ArrayList<Region> Regions = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifNomRegion(Regions, scanner)){
                    return(null);
                }
                result=Trie.TrieRegion(selection, Regions);
            }
            case "nom" -> {
                ArrayList<String> nomVilles = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifNomVille(villes, nomVilles, scanner)){
                    return(null);
                }
                result=Trie.TrieVille(selection, nomVilles);
            }
            case "ville chef-lieu" -> {
                ArrayList<String> ChefLieu = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifVilleChefLieu(villes,regions, ChefLieu, scanner)){
                    return(null);
                }
                ArrayList<Ville> VilleChefLieu= new ArrayList<>();
                for(Region reg : Region.values()){
                    if(ChefLieu.contains(reg.getChefLieu().getNom().toLowerCase())){
                        VilleChefLieu.add(reg.getChefLieu());
                    }
                }
                result= Trie.TrieRegionVilleChefLieu(selection, VilleChefLieu);
            }
            case "retour" -> result = Trie.monClone(selection);
        }
        return result;
    }

    /**
     * Trie les personnes par des critères choisis et renvoie la liste triée.
     * @param selection Liste des personnes sélectionnées.
     * @param personnes Liste des personnes à trier.
     * @param scanner Objet Scanner pour la saisie de l'utilisateur.
     * @return Liste des personnes triées selon les critères choisis.
     */
    public static ArrayList<Personne> trierParPersonne(ArrayList<Personne> selection, ArrayList<Personne> personnes, Scanner scanner) {
        ArrayList<Personne> result = new ArrayList<>();
        String message = "Choisissez un critère de tri pour les personnes :\n- 'discipline' : Trier par discipline.\n- 'fonction' : Trier par fonction.\n- 'nom' : Trier par nom prenom.\n- 'id' : Trier par ID.\n- 'titulaire' : Trier des titulaire en fonction de leur etudiants.\n- 'age' : Trier par age.\n- 'etudiants' : Trier par nombre d'étudiants\n- 'sujet' : Trier par sujet de thèse.\n- 'annee' : Trier par année de thèse.\n- 'encadrant' : Trier par encadrant des étudiants.\n- 'bureau' : Trier par numéro de bureau.\n- 'retour' : Retourner.";
        String choix = Coherence.verificationOption(scanner, Set.of("discipline", "fonction", "nom", "id", "titulaire", "age","etudiants",  "sujet", "annee", "encadrant", "bureau", "retour") , message);
        switch (choix.toLowerCase()) {
            case "discipline" -> {
                ArrayList<Discipline> disciplines = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifDiscipline(disciplines, scanner)){
                    return(null);
                }
                if(disciplines.size()==2){ //si plus de 2 discipline pas ET car au max 2 discipline par titulaire
                    message ="Voulez-vous trier :\n- 'ou': pour que les personnes fassent au moins une discipline\n- 'et': pour que les personnes fassent tous les disciplines saisies";
                    String CHOIX = Coherence.verificationOption(scanner, Set.of("ou", "et") , message);
                    if(CHOIX.equals("ou")){
                        result = Trie.TrieDisciplineOU(selection, disciplines);
                    }
                    else if(CHOIX.equals("et")){
                        result = Trie.TrieDisciplineET(selection, disciplines);
                    }
                }
                else{
                    result = Trie.TrieDisciplineOU(selection, disciplines);
                }
            }
            case "fonction" -> {
                ArrayList<String> Fonctions = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifFonction(Fonctions, scanner)){
                    return(null);
                }
                result= Trie.TrieFonction(selection, Fonctions);
            }
            case "nom" -> {
                ArrayList<String> NomPrenom= new ArrayList<>();
                if (!VerificationTrieTerminal.VerifNomPrenom(selection, NomPrenom, scanner)){
                    return(null);
                }
                result= Trie.TrieNomPrenom(selection, NomPrenom);
            }
            case "id" -> {
                ArrayList<Integer> ID = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifID(selection, ID, scanner)){
                    return(null);
                }
                result= Trie.TrieID(selection, ID);
            }
            case "titulaire" -> {
                ArrayList<Personne> Etudiants= new ArrayList<>();
                if(!VerificationTrieTerminal.VerifTitulaire(personnes, Etudiants, scanner)){
                    return(null);
                }
                if(Etudiants.size()>=2){
                    message = "vous les vous que : \n- 'ou' : les titulaires aient au moins un des etudiants\n- 'et' : les titulaires aient tous les etudiants";
                    String CHOIX = Coherence.verificationOption(scanner, Set.of("ou", "et") , message);
                    if(CHOIX.equals("ou")){
                        result = Trie.TrieTitulaireEtudiantOU(selection, Etudiants);
                    }
                    else if(CHOIX.equals("et")){
                        result = Trie.TrieTitulaireEtudiantET(selection, Etudiants);
                    }
                }
                else{
                    result= Trie.TrieTitulaireEtudiantOU(selection, Etudiants);
                }
            }
            case "age" -> {
                System.out.println("valeur min d'age");
                double age1=Coherence.verificationDouble(scanner.nextLine().trim());
                System.out.println("valeur max d'age");
                double age2=Coherence.verificationDouble(scanner.nextLine().trim());
                if(!VerificationTrieTerminal.verifIntervalle(age1, age2)){
                    return(null);
                }
                result= Trie.TrieAge(selection, age1, age2);
            }
            case "etudiants" -> {
                System.out.println("valeur min d'étudiants");
                int nbretudiantmin=Integer.parseInt(scanner.nextLine().trim());
                System.out.println("valeur max d'étudiants");
                int nbretudiantmax=Integer.parseInt(scanner.nextLine().trim());
                if(!VerificationTrieTerminal.verifIntervalle(nbretudiantmin, nbretudiantmax)){
                    return(null);
                }
                result = Trie.TrieNbrEtudiant(selection, nbretudiantmin, nbretudiantmax);
            }
            case "sujet" -> {
            	message = "Vous avez choisi de trier par Sujet de Thèse :\n- 'sujet': si vous voulez donner les sujets de thèse exact\n- 'ou': si vous voulez trier par mots clés les sujets et que au moins un mot clé apparaisse dans les sujets de thèse\n- 'et': si vous vooulez trier par mots clés les sujets et que tous les mots clés apparaissent dans les sujets de thèse";
                String CHOIX = Coherence.verificationOption(scanner, Set.of("sujet", "ou", "et"), message);
                switch (CHOIX) {
                    case "sujet" -> {
                        ArrayList<String> Sujet = new ArrayList<>();
                        if(!VerificationTrieTerminal.VerifSujetDeThese(selection, Sujet, scanner)){
                            return(null);
                        }
                        result = Trie.TrieSujetdeThese(selection, Sujet);
                    }
                    case "ou" -> {
                        System.out.println("Vous avez choisi de trier mots clés. Veuillez fournir les mots clés à considérer, séparés par des virgules"); 
                        String[] motcle= scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
                        ArrayList<String> MotCle = new ArrayList<>(Arrays.asList(motcle));
                        result = Trie.TrieSujetdeTheseMotclOU(selection, MotCle);
                    }
                    case "et" -> {
                        System.out.println("Vous avez choisi de trier mots clés. Veuillez fournir les mots clés à considérer, séparés par des virgules");
                        String[] motcle = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
                        ArrayList<String> MotCle = new ArrayList<>(Arrays.asList(motcle));
                        result = Trie.TrieSujetdeTheseMotclOU(selection, MotCle);
                    }
                }
            }
            case "annee" -> {
                System.out.println("Si vous voulez selectionner les etudiants en premiere annee de thèse tapez 1 sinon 0 : ");
                int annee1=Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Si vous voulez selectionner les etudiants en premiere annee de thèse tapez 2 sinon 0 : ");
                int annee2=Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Si vous voulez selectionner les etudiants en premiere annee de thèse tapez 3 sinon 0 : ");
                int annee3=Integer.parseInt(scanner.nextLine().trim());
                result= Trie.TrieAnneedeThese(selection, annee1, annee2, annee3);
            }
            case "encadrant" -> {
                ArrayList<Personne> Encadrants= new ArrayList<>();
                if(!VerificationTrieTerminal.VerifEncadrant(personnes, Encadrants, scanner)){
                    return(null);
                }
                result= Trie.TrieEtudiantEncadrant(selection, Encadrants);
            }
            case "bureau" -> {
                ArrayList<Integer> numbureau = new ArrayList<>();
                if(!VerificationTrieTerminal.VerifNumBureau(personnes, numbureau, scanner)){
                    return(null);
                }
                result= Trie.TrieNumBureau(selection, numbureau);
            }
            case "retour" -> result = Trie.monClone(selection);
        }
        return result;
    }
}

 