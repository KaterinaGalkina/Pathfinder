package baseDeDonnees;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;
import peuple.*;
import territoire.Ville;

/**
 * Cette classe permet de recuperer les donnees stockees pour les personnes et les villes.
 */
public class ChargementDonnees {
	
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private ChargementDonnees() {
		
	}

    /**
     * Cette fonction lit le fichier {@code ecosysteme} de ce package, recupere la base de donnees des personnes sous forme d'un objet {@link ListeEcosysteme},
     * puis le transforme en une {@code ArrayList} d'objets {@link Personne}. 
     *
     * @return {@code ArrayList<Personne>} La base de donnees des personnes stockee dans un fichier sous forme de flux d'octets.
     */
	public static ArrayList<Personne> chargerPersonnes() {
		ArrayList<Personne> pl = ListeEcosysteme.recupererEcosysteme();
		Personne.reinitialiserIdsNb(pl);
		return pl;
	}
	
    /**
     * Cette fonction se connecte a la base de donnees PostgreSQL de l'utilisateur en utilisant les informations saisies 
     * ({@code url}, {@code user}, {@code password}) et recupere toutes les informations necessaires pour construire une 
     * base de donnees des villes sous forme d'une {@code ArrayList} d'objets {@link Ville}.
     *
     * @return {@code ArrayList<Ville>} La base de donnees des villes.
     */
	public static ArrayList<Ville> chargerVilles() {
		ArrayList<Ville> baseVillesJava = new ArrayList<>();
		String villes = "SELECT ville_nom, ville_code_postal, ville_population_2012, ville_surface, ville_departement FROM villes_france_free";
		try {
			Connection connexion = DriverManager.getConnection(Main.getURL(), Main.getUser(), Main.getMDP());
			PreparedStatement statementVilles = connexion.prepareStatement(villes);
	        ResultSet villesResultSet = statementVilles.executeQuery();
	        while (villesResultSet.next()) {
	           Ville villeCourante = new Ville(villesResultSet.getString("ville_nom"), 
	           		villesResultSet.getString("ville_code_postal"), 
	           		villesResultSet.getInt("ville_population_2012"), 
	           		villesResultSet.getDouble("ville_surface"),
	           		villesResultSet.getString("ville_departement"));
	           baseVillesJava.add(villeCourante);
	        }
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    return baseVillesJava;
	}
}
