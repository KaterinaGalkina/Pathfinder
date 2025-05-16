package territoire;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import main.*;
import objetsAuxiliaires.Couple;

/**
 * Cette classe represente une ville avec des informations comme le nom,
 * le code postal, la population, la superficie et le departement. Elle contient aussi les
 * methodes pour manipuler et recuperer des informations sur les villes.
 */
public class Ville implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nom;
    private String code_postale;
    private int population;
    private double superficie;
    private String departement; 
    
    /**
	 * Constructeur de la classe Ville.
	 *
	 * @param nom Nom de la ville
	 * @param code_postale Code postal de la ville
	 * @param population Population de la ville
	 * @param superficie Superficie de la ville
	 * @param departement Code du departement ou se situe la ville
	 */
    public Ville(String nom, String code_postale, int population, double superficie, String departement) {
        this.nom = nom;
        this.code_postale = code_postale;
        this.population = population;
        this.superficie = superficie;
        this.departement = departement;
    }
    
    /**
	 * Retourne le nom de la ville.
	 *
	 * @return Le nom de la ville
	 */
    public String getNom(){
        return this.nom;
    }
    
    /**
	 * Retourne le nom de la ville et son code de departement (Pour avoir une belle representation de chaque ville
	 * et de la representer de la maniere unique). 
	 *
	 * @return Le nom et le code du departement de la ville (par exemple : Paris (75))
	 */
    public String getNomDep() {
        return this.nom.substring(0, 1).toUpperCase() + this.nom.substring(1).toLowerCase() + " (" + this.departement + ")";
    }
    
    /**
	 * Retourne le code postal de la ville.
	 *
	 * @return Le code postal de la ville
	 */
    public String getCode_postale(){
        return this.code_postale;
    }
    
    /**
	 * Retourne la population de la ville.
	 *
	 * @return La population de la ville
	 */
    public int getPopulation(){
        return this.population;
    }
    
    /**
	 * Retourne la superficie de la ville.
	 *
	 * @return La superficie de la ville
	 */
    public double getSuperficie(){
        return this.superficie ;
    }
    
    /**
	 * Retourne le code du departement ou se situe la ville.
	 *
	 * @return Le code du departement
	 */
    public String getDepartement(){
        return this.departement ;
    }
    
    /**
	 * Retourne la region a laquelle appartient la ville.
	 *
	 * @return La region correspondant a la ville
	 */
    public Region getRegion() {
    	return Region.trouverRegion(this);
    }
    
    /**
	 * Compare deux villes en fonction de leur nom et de leur departement.
	 *
	 * @param v2 L'objet a comparer
	 * @return true si les villes ont le meme nom et le meme departement 
	 * 		   (car ce couple de valeur est unique pour chaque ville), sinon false
	 */
    @Override
    public boolean equals(Object v2){
        if (v2 instanceof Ville){
            Ville ville2 = (Ville) v2;
            if (this.nom.equals(ville2.nom) && this.departement.equals(ville2.departement)){
                return true;
            }
        }
        return false;
    }
    
    /**
	 * Cree un dictionnaire des distances entre chaque paire de villes dans une liste.
	 *
	 * @param ensembleVilles La liste des villes entre les quelles il y aura tous les distances
	 * @return Un dictionnaire des distances entre chaque pair de villes de l'ensembleVilles
	 */
    public static HashMap<Couple<Ville, Ville>, Double> setDictDistances(ArrayList<Ville> ensembleVilles) {
    	HashMap<Couple<Ville, Ville>, Double> distances = new HashMap<>();
    	
    	String distance_entre_2_villes = "SELECT v1.ville_nom as ville1, v1.ville_departement as ville1_dept, v2.ville_nom as ville2, v2.ville_departement as ville2_dept, "
				+ "( 2* 6371 *ASIN(SQRT(POWER( SIN( ABS(RADIANS(v1.ville_latitude_deg) - RADIANS(v2.ville_latitude_deg))/2 ), 2) + COS(RADIANS(v1.ville_latitude_deg)) * COS(RADIANS(v2.ville_latitude_deg)) * POWER(SIN( ABS(RADIANS(v1.ville_longitude_deg) - RADIANS(v2.ville_longitude_deg))/2 ), 2))) ) as dist_km "
				+ "FROM villes_france_free v1, villes_france_free v2 WHERE v1.ville_nom = ? AND v1.ville_departement = ? AND v2.ville_nom = ? AND v2.ville_departement = ?";
    	
		try (Connection connexion = DriverManager.getConnection(Main.getURL(), Main.getUser(), Main.getMDP());
	            PreparedStatement statement = connexion.prepareStatement(distance_entre_2_villes)) {
	             
			for(Ville v1: ensembleVilles) {
				for(Ville v2: ensembleVilles) {
					if (!v1.equals(v2)) {
						Couple<Ville, Ville> c = new Couple<>(v1, v2);
						if (!distances.containsKey(c)) {
							
							statement.setString(1, v1.nom);
				            statement.setString(2, v1.departement);
				            statement.setString(3, v2.nom);
				            statement.setString(4, v2.departement);
				            
				            ResultSet resultat = statement.executeQuery();
				            
				            // Traiter les résultats
				            while (resultat.next()) {
				                double dist = resultat.getDouble("dist_km");
				                distances.put(c, dist);
				            }
						}
					}
				}
			}
			for(Ville v: ensembleVilles) {
				Couple<Ville, Ville> c = new Couple<>(v, v); 
		        distances.put(c, 0.0);
			}
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	return distances;
    }

    /**
	 * Retourne une representation de la ville en chaine de caracteres.
	 *
	 * @return Une chaine de caracteres qui represente la ville
	 */
    @Override
    public String toString(){
        return("Ville : \n\tNom : " + this.nom + ", " + //
        		"\tPopulation : " + this.population + ", " + //
        		"\tSuperficie : " + this.superficie + ", " + //
                "\n\tDépartement : " + this.departement +
                "\n\tCode Postal : " + this.code_postale + "\n"
                );
    }
}
