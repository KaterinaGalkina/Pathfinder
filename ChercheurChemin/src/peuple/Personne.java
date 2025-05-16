package peuple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import territoire.*;

/**
 * Classe abstraite representant une personne. 
 * Elle contient des attributs de base (nom, prenom, age et ville ou cette personne habite), 
 * ainsi que des methodes pour les manipuler.
 */
public abstract class Personne implements Serializable{
    private static final long serialVersionUID = 1L;
	private String nom;
    private String prenom;
    private int age;
    private Ville ville;
	// private static int nbPersonne;
    private final int ID;
    private static int prochainID; 

    /**
     * Constructeur permettant de creer une personne.
     *
     * @param nom Le nom de la nouvelle personne.
     * @param prenom Le prenom de la nouvelle personne.
     * @param age L'age de la nouvelle personne.
     * @param ville La ville ou habite la nouvelle personne.
     */
    public Personne(String nom, String prenom, int age, Ville ville){
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.ville = ville;
        this.ID = prochainID++;
        // nbPersonne++;
    }
    
    /**
     * Methode permettant de mettre a jour l'ID de la prochaine personne ajoutee.
     * Cette reinitialisation est necessaire lors de la recuperation des personnes depuis un fichier. 
     * En effet, le constructeur de cette classe n'est pas appele durant cette phase, 
     * ce qui peut engendrer des incoherences dans les IDs. Cette methode met a jour cette valeur
     * avant l'ajout de nouvelles personnes dans la base.
     *
     * @param baseDeDonnees La liste de personnes recuperee depuis le fichier, sous forme de {@code ArrayList<Personne>}.
     */
    public static void reinitialiserIdsNb(ArrayList<Personne> baseDeDonnees) {
    	// nbPersonne = baseDeDonnees.size();
    	prochainID = baseDeDonnees.get(baseDeDonnees.size()-1).ID + 1;
    }
    
    /**
     * Retourne la liste des fonctions de la personne.
     *
     * @return Une liste de fonctions sous forme de {@code ArrayList<String>} (par exemple : etudiant, mcf...).
     */
    public abstract ArrayList<String> getFonctions();
    
    /**
     * Retourne les disciplines de la personne.
     *
     * @return Un ensemble de disciplines sous forme de {@code Set<Discipline>}.
     */
    public abstract Set<Discipline> getDisciplines();
    
    /**
     * Retourne le nom de la personne.
     *
     * @return Le nom de la personne sous forme de {@code String}.
     */
    public String getNom() {
    	return this.nom;
    }
    
    /**
     * Met a jour le nom de la personne.
     *
     * @param nom Le nouveau nom de la personne.
     */
    public void setNom(String nom) {
    	if (nom != null) {
    		this.nom = nom;
    	}
    }
    
    /**
     * Retourne le prenom de la personne.
     *
     * @return Le prenom de la personne sous forme de {@code String}.
     */
    public String getPrenom(){
    	return this.prenom;
    }
    
    /**
     * Met a jour le prenom de la personne.
     *
     * @param prenom Le nouveau prenom de la personne.
     */
    public void setPrenom(String prenom){
    	if (prenom != null) {
    		this.prenom = prenom;
    	}
    }
    
    /**
     * Retourne le nom complet de la personne (nom et prenom).
     *
     * @return Le nom complet de la personne sous forme de {@code String}.
     */
    public String getNomPrenom(){
        return(this.nom.toUpperCase().trim()+" "+this.prenom);
    } 
    
    /**
     * Retourne l'age de la personne.
     *
     * @return L'age de la personne sous forme de {@code int}.
     */
    public int getAge(){
    	return this.age;
    }
    
    /**
     * Met a jour l'age de la personne.
     *
     * @param age Le nouvel age de la personne (doit etre strictement positif).
     */
    public void setAge(int age) {
    	if (age > 0) {
    		this.age = age;
    	}
    }

    /**
     * Retourne la ville ou habite la personne.
     *
     * @return La ville de la personne sous forme d'objet {@code Ville}.
     */
    public Ville getVille(){
        return this.ville;
    }
    
    /**
     * Met a jour la ville ou habite la personne.
     *
     * @param ville La nouvelle ville de la personne.
     */
    public void setVille(Ville ville) {
    	if (ville != null) {
    		this.ville = ville;
    	}
    }
    
    /**
     * Retourne l'ID unique de la personne.
     *
     * @return L'ID de la personne sous forme de {@code int}.
     */
    public int getID(){
        return this.ID;
    }

    /**
     * Retourne une representation sous forme de chaine de caracteres de la personne 
     * 
     * @return La representation de la personne sous forme de chaine de caracteres.
     */
    @Override
    public String toString(){
        return "\n\tNom : " + this.nom + ", " +
        		"\tPrenom : " + this.prenom + ", " +
        		"\tAge : " + this.age + ", " +
        		"\tVille : " + this.ville.getNomDep() + ", " +
        		"\tID : " + this.ID;

    }
}