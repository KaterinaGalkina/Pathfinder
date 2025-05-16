package peuple;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Cette enumeration represente toutes les disciplines que les personnes peuvent avoir dans cette base de donnees.
 * Elle inclut des methodes permettant de manipuler et d'afficher les disciplines.
 */
public enum Discipline {
	/**
     * Discipline mathematiques.
     */
    MATHEMATIQUES,
    
    /**
     * Discipline informatique.
     */
    INFORMATIQUE,
    
    /**
     * Discipline gestion.
     */
    GESTION,
    
    /**
     * Discipline droit.
     */
    DROIT,
    
    /**
     * Discipline sciences sociales.
     */
    SCIENCESSOCIALES;
	
	/**
     * Recupere une instance de {@code Discipline} correspondant a une chaine de caracteres passee en parametres.
     * 
     * @param str La chaine de caracteres representant une discipline.
     * @return L'instance correspondante de {@code Discipline}, ou {@code null} si la chaine n'est pas reconnue.
     */
	public static Discipline getDiscipline(String str) {
		if ("droit".equals(str.toLowerCase())) {
			return  Discipline.DROIT;
		} else if ("mathematiques".equals(str.toLowerCase())) {
			return  Discipline.MATHEMATIQUES;
		} else if ("informatique".equals(str.toLowerCase())) {
			return  Discipline.INFORMATIQUE;
		} else if ("gestion".equals(str.toLowerCase())) {
			return  Discipline.GESTION;
		} else if ("sciencessociales".equals(str.replace("\s", "").toLowerCase())) {
			return Discipline.SCIENCESSOCIALES;
		} else {
			System.out.println("Erreur : la discipline \"" + str + "\" n'existe pas ou a été mal saisie.");
			return null;
		}
	}
	
	/**
     * Affiche toutes les disciplines disponibles dans l'enumeration.
     * Chaque discipline est formattee de maniere a etre plus lisible et jolie (premiere lettre en majuscule).
     */
	public static void afficherDisciplines() {
		for (Discipline d : Discipline.values()) {
			if (d == Discipline.SCIENCESSOCIALES) {
				System.out.println(" - " + "Sciences Sociales");
			} else {
				System.out.println(" - " + d.name().substring(0, 1).toUpperCase() + d.name().substring(1).toLowerCase());
			}
        }
	}
	
	/**
     * Retourne une representation sous forme de chaine de caracteres jolie de la discipline.
     *
     * @return Une chaine de caracteres representant la discipline.
     */
	@Override
	public String toString() {
		if (this != Discipline.SCIENCESSOCIALES){
			return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
		} else {
			return "Sciences Sociales";
		}
	}
	
	/**
     * Convertit un ensemble de disciplines en une representation sous forme de chaine de caracteres.
     *
     * @param disciplines L'ensemble des disciplines a convertir.
     * @return Une chaine de caracteres representant les disciplines.
     */
	public static String setDisciplinesToString(Set<Discipline> disciplines) {
		return disciplines.stream().map(Discipline::toString).collect(Collectors.joining(", "));
	}
}

