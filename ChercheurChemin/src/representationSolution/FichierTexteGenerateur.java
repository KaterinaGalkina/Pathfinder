package representationSolution;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import filtrage.Trie;
import peuple.Personne;
import territoire.Ville;

/**
 * Cette classe permet de generer un fichier Texte representant le meilleur chemin genere
 * pour visiter un ensemble de villes et de personnes, a partir d'une ville de depart specifique.
 */
public class FichierTexteGenerateur {
	private ArrayList<Ville> villesAVisiter;
	private Ville villeDep;
	private ArrayList<Personne> personnesAVisiter;
	
	/**
     * Constructeur pour initialiser les donnees necessaires a la generation 
     * du chemin et du fichier.
     *
     * @param villesAVisiter Liste des villes a visiter.
     * @param personnesAVisiter Liste des personnes a visiter.
     * @param villeDep Ville de depart pour le circuit.
     */
	public FichierTexteGenerateur(ArrayList<Ville> villesAVisiter, ArrayList<Personne> personnesAVisiter, Ville villeDep) {
		this.villesAVisiter = villesAVisiter;
		this.villeDep = villeDep;
		this.personnesAVisiter = personnesAVisiter;
	}
	
	/**
     * Genere une representation du chemin le plus court trouve pour visiter 
     * les villes et les personnes.
     *
     * @return Une chaine de caracteres representant le chemin optimal, 
     *         incluant les villes et les personnes associees.
     */
	public String representationContenu() {
		int n = this.villesAVisiter.indexOf(this.villeDep);
		if (n!= -1) {
			ArrayList<Ville> circuit = new ArrayList<>(this.villesAVisiter.subList(n, this.villesAVisiter.size()));
			circuit.addAll(new ArrayList<>(this.villesAVisiter.subList(0, n)));
			
			String str = "";
			ArrayList<ArrayList<Personne>> personnesParVilles = Trie.personnesParVille(personnesAVisiter, villesAVisiter);
			int i = 0;
			for (Ville v : circuit.subList(0, circuit.size()-1)) { // sauf la ville de depart 
				str += "\t\t" + v.getNom() + "\n\n";
				str += "\t\t   |\n";
				ArrayList<Personne> personnesVilleActuelle = personnesParVilles.get(i);
				for (Personne pers: personnesVilleActuelle) {
					str += "\t\t   |";
					str += "\t\t " + pers.getNomPrenom() + "\n";
				}
				str += "\t\t   v\n\n";
				i++;
			}
			str += "\t\t" + villeDep.getNom() + "\n";
			return str;
		}
		return "";
	}
	
	/**
     * Genere un fichier Texte contenant le chemin optimal.
     *
     * @param nomFichier Nom du fichier dans lequel ecrire la representation.
     */
	public void genererFichierText(String nomFichier) {
		String representation = this.representationContenu();
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier))) {
			writer.write("\nLe meilleur chemin généré par le programme : \n\n"); 
		    writer.write(representation); 
		    writer.newLine();  
		} catch (IOException e) {
		    System.err.println("Une erreur est survenue : " + e.getMessage());
		}
	}
}
