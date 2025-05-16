package peuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import territoire.Ville;
import verificationEntree.Coherence;

/**
 * Classe representant un MCF. 
 * Elle contient un attribut supplementaire : les etudiant (l'etudiant en these de ce MCF si il en a un) 
 * ainsi que des methodes pour les manipuler.
 */
public class MCF extends Titulaire{
    private static final long serialVersionUID = 1L;
	private Etudiant etudiant;

	/**
     * Constructeur de la classe MCF.
     *
     * @param nom Le nom du chercheur.
     * @param prenom Le prenom du chercheur.
     * @param age L'age du chercheur.
     * @param ville La ville ou habite le chercheur.
     * @param disciplines Les disciplines du chercheur.
     * @param numBureau Le numero de bureau du chercheur.
     * @param etudiant L'etudiants en these encadre par ce MCF (si il existe un, sinon {@code null}).
     */
    public MCF(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Etudiant etudiant) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiant = etudiant;
    }
    
    /**
     * Retourne une liste des fonctions associees a un MCF.
     * 
     * @return Une liste contenant les fonctions "mcf" et "titulaire".
     */
    @Override
    public ArrayList<String> getFonctions(){
    	ArrayList<String> fonctions = new ArrayList<>();
    	fonctions.add("mcf");
    	fonctions.add("titulaire");
    	return fonctions;
    }
    
    /**
     * Retourne l'etudiant actuellement encadre par ce MCF.
     * 
     * @return L'etudiant encadre, ou null si aucun etudiant n'est associe.
     */
    public Etudiant getEtudiant() {
    	return this.etudiant;
    }
    
    /**
     * Verifie si ce MCF peut encadrer un etudiant donne.
     * 
     * @param e L'etudiant a verifier.
     * @return {@code true} si le MCF peut prendre cet etudiant en these, {@code false} sinon.
     */
    @Override 
    public boolean peutPrendreEtudiant(Etudiant e) {
    	if (this.etudiant == null && e != null && this.getDisciplines().contains(e.getDiscipline())) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Associe un etudiant a ce MCF.
     * Si ce MCF n'encadre aucun etudiant (pour ne pas laisser un autre sans titulaire), 
     * l'etudiant donne sera ajoute comme encadre.
     * 
     * @param e L'etudiant a associer.
     */
    @Override
    protected void addEtudiant(Etudiant e){
    	if (e!= null && this.etudiant == null) {
    		this.etudiant = e;
    	}
    }
    
    /**
     * Retire l'etudiant associe a ce MCF, si l'etudiant correspond a celui en these.
     * 
     * @param e L'etudiant a retirer.
     */
    @Override 
    public void popEtudiant(Etudiant e) {
    	if (e!= null && e.equals(this.etudiant)) {
    		this.etudiant = null;
    	}
    }
    
    /**
     * Tente de transferer l'etudiant actuel a un autre titulaire compatible.
     * 
     * @param baseDeDonnees La base de donnees contenant l'ensemble des titulaires disponibles.
     * @return {@code true} si le transfert a reussi, {@code false} sinon.
     */
    public boolean essaieSupprimerEtudiant(ArrayList<Personne> baseDeDonnees){
        if (this.etudiant != null){
            for (Personne p : baseDeDonnees) {
                if (p instanceof Titulaire tit && tit.peutPrendreEtudiant(this.etudiant)){
                    Etudiant etud = this.etudiant;
                    etud.setEncadrant(tit);
                    return true;
                }
            }
            return false; // On a parcouru tout le mode mais pas reussi a trouve d'encadrant :(
        }
        return true;
    }
    
    /**
     * Retourne une liste des etudiants que ce MCF peut encadrer.
     * 
     * @param baseDeDonneesCourrante La base de donnees des personnes.
     * @param avecEtudiantsCourants  Indique si l'etudiant actuellement encadre doit etre inclus dans la liste.
     * @return Une liste d'etudiants que ce MCF peut prendre en these.
     */
    @Override
    public ArrayList<Etudiant> listeEtudiantsPossibles(ArrayList<Personne> baseDeDonneesCourrante, boolean avecEtudiantsCourants) {
		ArrayList<Etudiant> etudiantsPossibles = new ArrayList<>();
		for (Personne pers: baseDeDonneesCourrante){
			if (pers instanceof Etudiant etud && this.getDisciplines().contains(etud.getDiscipline())) {
				etudiantsPossibles.add(etud);
			}
		}
		if (!avecEtudiantsCourants && this.etudiant != null) {
			etudiantsPossibles.remove(this.etudiant);
		}
		return etudiantsPossibles;
	}
    
    /**
     * Retourne l'etudiant associe a ce MCF sous forme d'ensemble.
     * 
     * @return Un ensemble contenant l'etudiant encadre.
     */
    public Set<Etudiant> getEtudiantsSet(){
    	return Set.of(this.etudiant);
    }
    
    /**
     * Permet d'ajouter un etudiant a ce MCF via une interaction avec l'utilisateur (pour la version terminal).
     * 
     * @param scanner Pour lire les entrees utilisateur.
     * @param personnes La base de donnees des personnes.
     */
    @Override
    public void ajouterEtudiantAEncadrant(Scanner scanner, ArrayList<Personne> personnes) {
 		String reponseUtilisateur = "";
    	System.out.println("\nVoici la liste des étudiants que ce MCF peut prendre en thèse : ");
 		Set<Integer> idEtudPossibles = new HashSet<>();
 		
		for(Personne p: personnes) {
			if (p instanceof Etudiant) {
				Etudiant e = (Etudiant)p;
				// Si ce MCF a deja un etudiant on ne veut peut le meme qu'il a
				if (e != this.getEtudiant() && this.getDisciplines().contains(e.getDiscipline())) {
					System.out.println(e.toString());
					idEtudPossibles.add(e.getID());
				}
			}
		}
 	
		boolean idCorrect = false;
		int idEtudiantChoisi = 0;
		while(!idCorrect) {
			System.out.println("Tapez l'ID de l'étudiant que vous voulez choisir :");
			idEtudiantChoisi = Coherence.verificationEntier(scanner.nextLine().trim());
			if (idEtudPossibles.contains(idEtudiantChoisi)) {
				idCorrect = true;
			} else {
				System.out.println("L'identifiant saisi n'est pas correct, veuillez réessayer.");
				
				reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("reessayer", "abandonner"), "\nVoulez-vous réessayer de saisir l'ID ?\n"
		                + "Tapez 'reessayer' pour ressaisir\nTapez 'abandonner' pour garder seulement celles qui sont bien sélectionnées.");
            	if (reponseUtilisateur.equals("abandonner")) { // Si l'utilisateur a change d'avis on ne va pas ajouter
            		return;
            	}
			}
		}
		for (Personne p: personnes) {
			if (p.getID() == idEtudiantChoisi) {
				((Etudiant)p).setEncadrant(this);
				break;
			}
		}
 		
 	}

    /**
     * Retourne une representation sous forme de chaine de caracteres d'un MCF 
     * 
     * @return La representation d'un MCF sous forme de chaine de caracteres.
     */
    @Override
    public String toString(){
    	String str = "\nMCF :" + super.toString() + "\n";
    	if (etudiant != null) {
    		str += "\tEtudiant en these : " + this.etudiant.getNomPrenom()  + "\n";
    	}
        return str;
    }
}