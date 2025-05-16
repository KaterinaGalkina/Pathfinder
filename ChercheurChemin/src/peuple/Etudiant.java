package peuple;

import java.util.ArrayList;
import java.util.Set;

import territoire.Ville;

/**
 * Classe representant un etudiant. 
 * Elle contient des attributs supplementaires : annee de these, sujet de these, discipline et encadrant, 
 * ainsi que des methodes pour les manipuler.
 */
public class Etudiant extends Personne{
    private static final long serialVersionUID = 1L;
	private String sujetDeThese;
    private Discipline discipline;
    private int anneeDeThese;
    private Titulaire encadrant;

    /**
     * Constructeur de la classe Etudiant.
     *
     * @param nom Le nom de l'etudiant.
     * @param prenom Le prenom de l'etudiant.
     * @param age L'age de l'etudiant.
     * @param ville La ville de residence de l'etudiant.
     * @param sujetDeThese Le sujet de these de l'etudiant.
     * @param discipline La discipline de l'etudiant.
     * @param anneeDeThese L'annee de these de l'etudiant.
     * @param encadrant Le titulaire encadrant l'etudiant.
     */
    public Etudiant(String nom, String prenom, int age, Ville ville, String sujetDeThese, Discipline discipline, int anneeDeThese, Titulaire encadrant) {
        super(nom, prenom, age, ville);
        this.sujetDeThese = sujetDeThese;
        this.discipline = discipline;
        this.anneeDeThese = anneeDeThese;
        this.encadrant = encadrant;
    }
    
    /**
     * Retourne les fonctions associees a cette classe (ici "etudiant").
     *
     * @return Une liste contenant "etudiant".
     */
    @Override
    public ArrayList<String> getFonctions(){
    	ArrayList<String> fonctions = new ArrayList<>();
    	fonctions.add("etudiant");
    	return fonctions;
    }
    
    /**
     * Retourne le sujet de these de l'etudiant.
     *
     * @return Le sujet de these de l'etudiant.
     */
    public String getSujetDeThese() {
        return this.sujetDeThese;
    }  
    
    /**
     * Met a jour le sujet de these de l'etudiant.
     *
     * @param sujetDeT Le nouveau sujet de these (non null).
     */
    public void setSujetDeThese(String sujetDeT) {
    	if (sujetDeT != null) {
    		this.sujetDeThese = sujetDeT;
    	}
    }
    
    /**
     * Retourne La discipline de l'etudiant sous forme d'un ensemble.
     *
     * @return Un ensemble contenant uniquement la discipline de l'etudiant.
     */
    @Override
    public Set<Discipline> getDisciplines() {
        return Set.of(this.discipline);
    }
    
    /**
     * Retourne la discipline de l'etudiant.
     *
     * @return La discipline de l'etudiant.
     */
    public Discipline getDiscipline() {
        return this.discipline;
    }
    
    /**
     * Met a jour la discipline principale de l'etudiant.
     *
     * @param disc La nouvelle discipline (non null).
     */
    public void setDiscipline(Discipline disc) {
    	if (disc != null) {
    		this.discipline = disc;
    	}
    }
    
    /**
     * Retourne l'annee de these de l'etudiant.
     *
     * @return L'annee de these.
     */
    public int getAnneeDeThese(){
        return this.anneeDeThese;
    }
    
    /**
     * Met a jour l'annee de these de l'etudiant.
     *
     * @param anneeDeT Le nouvel annee de these (entre 1 et 3).
     */
    public void setAnneeDeThese(int anneeDeT) {
    	if (anneeDeT >= 1 && anneeDeT <=3) {
    		this.anneeDeThese = anneeDeT;
    	} else {
    		System.out.println("\nErreur : l'année de thèse doit être comprise entre 1 et 3 !");
    	}
    	
    }
    
    /**
     * Retourne le titulaire encadrant l'etudiant.
     *
     * @return Le titulaire encadrant.
     */
    public Titulaire getEncadrant(){
        return this.encadrant;
    }
    
    /**
     * Met a jour l'encadrant de l'etudiant, ainsi que la liste des etudiants de son nouveau titulaire 
     * (etudiant courrant est ajoute). Si un encadrant precedent existait,
     * sa liste des etudiants et mis a jour aussi (cet etudiant est retire).
     *
     * @param encadrant Le nouvel encadrant (non null).
     */
    public void setEncadrant(Titulaire encadrant){
    	if (encadrant != null) {
    		// Si il avait un autre encadrant au debut, on enleve cet etudiant de sa liste des etudiants en these
    		if (this.encadrant != null) {
    			this.encadrant.popEtudiant(this);
    		}
    		this.encadrant = encadrant;
    		encadrant.addEtudiant(this);
    	} 
    }
 
    /**
     * Verifie si le sujet de these contient au moins un des mots cles specifies.
     *
     * @param MotCle Une liste de mots cles a verifier.
     * @return {@code true} si au moins un mot cle est present dans le sujet, sinon {@code false}.
     */
    public boolean contientMotCleOUThese(ArrayList<String> MotCle) {
        for (String motCle : MotCle) {
            if (this.sujetDeThese.contains(motCle)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifie si le sujet de these contient tous les mots cles specifies.
     *
     * @param MotCle Une liste de mots cles a verifier.
     * @return {@code true} si tous les mots cles sont presents dans le sujet, sinon {@code false}.
     */
    public boolean contientMotCleETThese(ArrayList<String> MotCle) {
        for (String motCle : MotCle) {
            if (!this.sujetDeThese.contains(motCle)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Retourne une liste de titulaires pouvant encadrer l'etudiant.
     * Cette liste peut inclure ou exclure l'encadrant actuel selon le parametre {@code avecTitulaireCourant}.
     * Si on utilise l'interface graphique nous avons vesoin avec, si la version terminal : sans.
     *
     * @param baseDeDonneesCourrante La base de donnees actuelle des personnes.
     * @param avecTitulaireCourant {@code true} pour inclure l'encadrant actuel, {@code false} pour l'exclure.
     * @return Une liste de titulaires disponibles.
     */ 
    public ArrayList<Titulaire> listeTitulairesPossibles(ArrayList<Personne> baseDeDonneesCourrante, boolean avecTitulaireCourant) {
		ArrayList<Titulaire> titulairesPossibles = new ArrayList<>();
		if (avecTitulaireCourant) {
			titulairesPossibles.add(this.encadrant);
		}
		for (Personne p : baseDeDonneesCourrante) {
			if (p instanceof Titulaire) {
				Titulaire tit = (Titulaire) p;
				if (!this.encadrant.equals(tit) && tit.peutPrendreEtudiant(this)) {
					titulairesPossibles.add(tit);
				}
			} 
		} 
		return titulairesPossibles;
	}


    /**
     * Retourne une representation sous forme de chaine de caracteres d'un etudiant 
     * 
     * @return La representation d'un etudiant sous forme de chaine de caracteres.
     */
    @Override
    public String toString(){
    	if (this.encadrant == null) {
    		System.out.println("Encadrant est vide !!!!! Erreur ! Erreur ! Erreur ! Erreur ! Erreur ! Erreur ! Erreur ! Erreur !");
    		return "";
    	}
    	String str = "\nEtudiant : " + super.toString() + 
    		    "\n\tDiscipline : " + this.discipline.toString() + ", " + 
    		    "\tAnnee de these : " + this.anneeDeThese + ", " +
    		    "\tSujet de these : " + this.sujetDeThese + ", " +
    		    "\n\tEncadrant : " + this.encadrant.getNomPrenom() + "\n";
    	return str;
    }
}