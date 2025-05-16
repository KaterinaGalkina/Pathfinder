package peuple;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import territoire.Ville;

/**
 * Classe abstraite representant un titulaire. 
 * Elle contient des attributs supplementaires propres a ce type de personne 
 * (numero de bureau, disciplines) et des methodes pour les manipuler.
 */
public abstract class Titulaire extends Personne{
	
    private static final long serialVersionUID = 1L;
	private Set<Discipline> disciplines; 
    private int numBureau;

    /**
     * Constructeur permettant de creer un titulaire.
     *
     * @param nom Le nom du titulaire.
     * @param prenom Le prenom du titulaire.
     * @param age L'age du titulaire.
     * @param ville La ville ou habite le titulaire.
     * @param disciplines Les disciplines enseignees par le titulaire.
     * @param numBureau Le numero de bureau du titulaire.
     */
    public Titulaire(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau){
        super(nom, prenom, age, ville);
        this.disciplines = disciplines;
        this.numBureau = numBureau;
    }
    
    /**
     * Retourne les disciplines du titulaire.
     *
     * @return Un ensemble de disciplines sous forme de {@code Set<Discipline>}.
     */
    @Override
    public Set<Discipline> getDisciplines() {
        return this.disciplines;
    }
    
    /**
     * Met a jour les disciplines du titulaire.
     *
     * @param disciplines Un ensemble de nouvelles disciplines sous forme de {@code Set<Discipline>}.
     */
    public void setDisciplinesSet(Set<Discipline> disciplines) {
    	if (disciplines != null) {
    		this.disciplines = disciplines;
    	}
    }
    
    /**
     * Retourne le numero de bureau du titulaire.
     *
     * @return Le numero de bureau du titulaire sous forme de {@code int}.
     */
    public int getNumBureau(){
        return this.numBureau;
    }
    
    /**
     * Met a jour le numero de bureau du titulaire.
     *
     * @param numB Le nouveau numero de bureau du titulaire.
     */
    public void setNumBureau(int numB) {
    	if (numB >= 0) {
    		this.numBureau = numB;
    	}
    }
    
    /**
     * Verifie si le titulaire peut prendre un etudiant specifique (en fonction des disciplines et si il a une place pour un autre etudiant).
     *
     * @param e L'etudiant a evaluer.
     * @return {@code true} si le titulaire peut prendre cet etudiant, sinon {@code false}.
     */
    public abstract boolean peutPrendreEtudiant(Etudiant e);
    
    /**
     * Ajoute un etudiant a la liste des etudiants du titulaire. S'utilise UNIQUEMENT dans la fonction 
     * setTitulaire de la classe {@link Etudiant} (Pour mettre a jour l'encadrant de 
     * l'etudiant et les etudiants de l'encadrant en meme temps). 
     * L'utilisation de cette fonction ailleurs necessite egalement de mettre a jour le titulaire de l'etudiant ajoute.
     *
     * @param e L'etudiant a ajouter.
     */
    protected abstract void addEtudiant(Etudiant e);
    
    /**
     * Retire un etudiant de la liste des etudiants du titulaire. S'utilise surtout dans la fonction 
     * setTitulaire de la classe {@link Etudiant} (Pour mettre a jour l'encadrant de 
     * l'etudiant et les etudiants de l'encadrant en meme temps). Mais peut s'utiliser egalement lors de la suppression de la base
     * de donnnees d'un etudiant. 
     *
     * @param e L'etudiant a retirer.
     */
    public abstract void popEtudiant(Etudiant e);
    
    /**
     * Retourne une liste des etudiants qui peuvent etre pris en charge par le titulaire.
     *
     * @param baseDeDonneesCourrante La base de donnees actuelle sous forme de {@code ArrayList<Personne>}.
     * @param avecEtudiantsCourants Indique si les etudiants deja pris en charge doivent etre inclus dans la liste. 
     * 		  (Puisque pour l'interface graphique nous avons besoin d'avoir la liste avec les etudiants courrants, alors que pour la version 
     * 		   terminal on souhaite sans) 
     * @return Une liste d'etudiants sous forme de {@code ArrayList<Etudiant>}.
     */
    public abstract ArrayList<Etudiant> listeEtudiantsPossibles(ArrayList<Personne> baseDeDonneesCourrante, boolean avecEtudiantsCourants);
    
    /**
     * Permet au titulaire d'ajouter un ou plusieurs etudiants differents de ses anciens etudiants.
     *
     * @param scanner Utilisee pour lire l'entree de l'utilisateur.
     * @param personnes La base de donnees des personnes.
     */
 	public abstract void ajouterEtudiantAEncadrant(Scanner scanner, ArrayList<Personne> personnes);
 	
 	/**
     * Retourne l'ensemble des etudiants encadres par le titulaire.
     *
     * @return Un ensemble d'etudiants sous forme de {@code Set<Etudiant>}.
     */
 	public abstract Set<Etudiant> getEtudiantsSet();
 	
 	/**
     * Verifie si au moins un des etudiants passes en parametre est deja encadre par le titulaire.
     *
     * @param etudiants La liste d'etudiants a verifier sous forme de {@code ArrayList<Etudiant>}.
     * @return {@code true} si au moins un etudiant est deja encadre, sinon {@code false}.
     */
 	public Boolean ContientEtudiantOU(ArrayList<Etudiant> etudiants){
		ArrayList<Etudiant> E = new ArrayList<>();
		E.addAll(this.getEtudiantsSet());
		for(Etudiant e: etudiants){
			if(E.contains(e)){
				return(true);
			}
		}
		return(false);
	}
 	
 	/**
     * Verifie si tous les etudiants passes en parametre sont deja encadres par le titulaire.
     *
     * @param etudiants La liste d'etudiants a verifier sous forme de {@code ArrayList<Etudiant>}.
     * @return {@code true} si tous les etudiants sont deja encadres, sinon {@code false}.
     */
	public Boolean ContientEtudiantET(ArrayList<Etudiant> etudiants){
		ArrayList<Etudiant> E= new ArrayList<>();
		E.addAll(this.getEtudiantsSet());
		for(Etudiant e: etudiants){
			if(!E.contains(e)){
				return(false);
			}
		}
		return(true);
	}
    
	/**
     * Retourne une representation sous forme de chaine de caracteres du tituliare 
     * 
     * @return La representation du tituliare sous forme de chaine de caracteres.
     */
    @Override
    public String toString(){
    	return super.toString() + 
    			"\n\tNumero de bureau : " + this.numBureau + ", " + 
    		    "\tDisciplines : " + Discipline.setDisciplinesToString(this.disciplines);
    }
}
