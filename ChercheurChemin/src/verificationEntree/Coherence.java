package verificationEntree;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import territoire.Ville;

/**
 * Cette classe contient des methodes statiques pour verifier la coherence l'entree
 * de l'utilisateur, comme l'entree des entiers, de chaines de caracteres,
 * de villes, d'options, et d'identifiants.
 */
public class Coherence {
	
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private Coherence() {
		
	}
	
	/**
     * Verifie que l'utilisateur a bien entre un entier.
     * 
     * @param nombreString La chaine a verifier.
     * @return L'entier correspondant si la conversion est valide, sinon -1.
     */ 
	public static int verificationEntier(String nombreString) {
		try {
            int nombre = Integer.parseInt(nombreString.trim());
            return nombre;
        } catch (NumberFormatException e) {
            System.out.println("Erreur : '" + nombreString + "' n'est pas un entier.");
        }
		return -1;
	}
	
	/**
     * Verifie que l'utilisateur a bien entre un double.
     * 
     * @param nombreString La chaine a verifier.
     * @return Le reel correspondant si la conversion est valide, sinon -1.
     */ 
	public static double verificationDouble(String nombreString) {
		try {
            double nombre = Double.parseDouble(nombreString.trim());
            return nombre;
        } catch (NumberFormatException e) {
            System.out.println("Erreur : '" + nombreString + "' n'est pas un nombre.");
        }
		return -1;
	}
	
	/**
     * Verifie qu'une chaine de caracteres ne contient que des lettres, des espaces et des tirets.
     * (Utilise pour verifier l'entree des noms et des prenoms en particulier)
     * 
     * @param scanner Le scanner pour lire l'entree utilisateur.
     * @return La chaine de caracteres valide, avec la premiere lettre en majuscule et le reste en minuscule.
     */
	public static String verificationChaineChar(Scanner scanner) {
		String chaine = "";
		boolean contientSpeciaux = true;
		// Tant qu'il y a dans la chaine des carasteres autres que les lettres
		while (true) {
			chaine = scanner.nextLine().trim();
			contientSpeciaux = Pattern.compile("[^a-zA-Z\\s-]").matcher(chaine).find();
			if (chaine.startsWith("-")) {
				System.out.println("\nErreur : La chaîne de caractères ne peut pas commencer par un tiret. Veuillez réessayer.");
			} else if (chaine.equals("")) {
				System.out.println("\nErreur : La chaîne de caractères ne peut pas être vide. Veuillez réessayer.");
			} else if (contientSpeciaux) {
				System.out.println("\nErreur : Les caractères spéciaux et les nombres ne sont pas acceptés. Veuillez réessayer.");
			} else {
				break;
			}
		}
		return chaine.substring(0, 1).toUpperCase() + chaine.substring(1).toLowerCase();
	}
	
	/**
     * Verifie si le nom/prenom entre est valide.
     * 
     * @param chaine Le nom/prenom a verifier.
     * @return true si le nom/prenom est correct, false sinon.
     */
    public static boolean nomPrenomEstValide(String chaine) {
		return !chaine.equals("") && !Pattern.compile("[^a-zA-Z\\s-]").matcher(chaine).find() && !chaine.startsWith("-") && !chaine.startsWith("-");
	}
	
	/**
     * Verifie si une ville existe dans la base de villes.
     * 
     * @param scanner Le scanner pour lire l'entree utilisateur.
     * @param baseDeVilles La liste des villes disponibles.
     * @param ajout Indique si c'est une operation d'ajout (dans ce cas on va legerement ajuster les affichages).
     * @return L'objet Ville correspondant des que l'utilisateur la bien selectionnee
     */
	public static Ville verifierVille(Scanner scanner, ArrayList<Ville> baseDeVilles, boolean ajout) {
	    Ville nvVille = null;
	    boolean villeTrouvee = false;
	
	    while (!villeTrouvee) {
	    	String villeNom = null;
	    	String villeDep = null;
	    	if (ajout) {
	    		System.out.println("\nEntrez le nom de sa ville :");
		        villeNom = scanner.nextLine().trim();
		        System.out.println("\nEntrez le code du département de sa ville :");
		        villeDep = scanner.nextLine().trim();
	    	} else {
	    		System.out.println("\nEntrez le nom de sa nouvelle ville :");
		        villeNom = scanner.nextLine().trim();
		        System.out.println("\nEntrez le code du département de sa nouvelle ville :");
		        villeDep = scanner.nextLine().trim();
	    	}
	        
	        for (Ville v : baseDeVilles) {
	            if (v.getNom().equalsIgnoreCase(villeNom) && v.getDepartement().equals(villeDep)) {
	                nvVille = v;
	                villeTrouvee = true;
	                break;
	            }
	        }
	
	        if (!villeTrouvee) {
	            System.out.println("La ville sélectionnée n'a pas été trouvée. Voici les villes disponibles :");
	            for (Ville v : baseDeVilles) {
	                System.out.println(" - " + v.getNom().substring(0, 1).toUpperCase() + v.getNom().substring(1).toLowerCase() + " (Departement : " + v.getDepartement() + ")");
	            }
	        }
	    }
	    return nvVille;
	}
	
	/**
     * Verifie que l'utilisateur a entre une option valide parmi un ensemble d'options possibles.
     * 
     * @param scanner Le scanner pour lire l'entree utilisateur.
     * @param optionsPossibles L'ensemble des options valides.
     * @param messageChoix Le message qui force l'utilisateur a choisir une option.
     * @return L'option choisie par l'utilisateur.
     */
	public static String verificationOption(Scanner scanner, Set<String> optionsPossibles, String messageChoix) {
		String reponseUtilisateur;
		while(true) {
			System.out.println(messageChoix); 
			reponseUtilisateur = scanner.nextLine().trim().toLowerCase();
			if (optionsPossibles.contains(reponseUtilisateur)) {
				return reponseUtilisateur;
			} else {
				System.out.println("Option '" + reponseUtilisateur + "' n'a pas été reconnue. Veuillez réessayer.");
			}
		}
	}
	
	/**
     * Verifie qu'un identifiant entre par l'utilisateur est valide.
     * 
     * @param scanner Le scanner pour lire l'entree utilisateur.
     * @param idPossibles L'ensemble des identifiants valides.
     * @param message Le message qui force l'utilisateur a entrer un identifiant.
     * @return L'identifiant valide.
     */
	public static int verificationId(Scanner scanner, Set<Integer> idPossibles, String message) {
		boolean idCorrect = false;
		int idChoisi = 0;
		while(!idCorrect) {
			System.out.println(message);
			idChoisi = Coherence.verificationEntier(scanner.nextLine().trim());
			if (idChoisi > 0 && idPossibles.contains(idChoisi)) {
				idCorrect = true;
			} else {
				System.out.println("\nL'ID saisi n'est pas correct, veuillez réessayer.");
			}
		}
		return idChoisi;
	}	
	
	/**
     * Verifie si une chaine de caracteres n'est pas vide apres avoir enleve les espaces.
     * (Utilise en particuler pour verifier si le sujet de these entre par l'utilisateur
     * n'est pas nul)
     * 
     * @param chaine La chaine a verifier.
     * @return true si la chaine n'est pas vide, false sinon.
     */
	public static boolean chaineNonVide(String chaine) {
        if (chaine == null) { 
            return false;
        }
        return !chaine.replaceAll("\\s", "").isEmpty();
    }
}
