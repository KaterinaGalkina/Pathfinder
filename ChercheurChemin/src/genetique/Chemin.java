package genetique;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;

import objetsAuxiliaires.Couple;
import territoire.Ville;

/**
 * Classe represente un chemin (une liste de villes a visiter).
 * Un chemin est constitue d'une liste de villes, et son score est egale a la distance totale du chemin plus de la derniere ville a la ville initiale.
 * Cette classe étend {@code Individu<Ville>} et implémente les méthodes de croisement, de mutation et d'évaluation de fitness.
 */
public class Chemin extends Individu<Ville>{
	
	// Dictionnaire des distances entre chaque paire de villes, qui sera utilise pour calculer et comparer les 
	// differents chemins pour trouver le meilleur.
	private static HashMap<Couple<Ville, Ville>, Double> distances;
	
	/**
     * Constructeur pour initialiser un chemin avec une liste vide de villes.
     */
	public Chemin(){
		this.gene = new ArrayList<>();
	}
	
	/**
     * Calcule et retourne le score (la distance totale) du chemin.
     * Le score ici est la somme des distances entre chaque ville consecutive
     * dans le chemin, dont la distance entre la derniere ville et la premiere.
     * Le score est stocke dans la memoire pour ne pas avoir a le recalculer.
     *
     * @return La distance totale du chemin en type {@code double}.
     */
	@Override
	public double getScore() {
		if (this.score == -1) {
			int i = 0;
			double sum = 0;
			while (i < this.gene.size()-1) {
				Couple<Ville, Ville> c = new Couple<>(this.gene.get(i), this.gene.get(i+1));
				sum += Chemin.distances.get(c);
				
				i++;
			}
			Couple<Ville, Ville> c = new Couple<>(this.gene.get(this.gene.size()-1), this.gene.get(0));
			sum += Chemin.distances.get(c);
			this.score = sum;
		}
		return this.score;
	}
	
	/**
     * Permet de creer une copie d'un chemin actuel.
     *
     * @return Un nouveau chemin identique a actuel, (mais avec une adresse dans la memoire differente).
     */
	@Override
	public Chemin cloner(){
		Chemin clone = new Chemin();
		for(Ville v: this.gene) {
			clone.gene.add(v);
		}
		return clone;
	}
	
	 /**
     * Lance l'algorithme genetique pour trouver le meilleur chemin.
     * Cette methode initialise une population de chemins et l'evolue pour touver le meilleur.
     *
     * @param ensembleVilles Liste des villes a visiter.
     * @param taillePopulation Taille de la population initiale.
     * @param probaMutation Probabilite de mutation pour chaque enfant.
     * @param ratioElite Proportion des meilleurs individus de la nouvelle population 
     * 		  qui seront remplacer par les pires dans l'ancienne population. 
     * @param epsilon Seuil de convergence pour arreter l'algorithme si l'individu est assez bon.
     * @return Le meilleur chemin trouve par l'algorithme genetique.
     */
	public static Chemin algorithmeGenetique(ArrayList<Ville> ensembleVilles, int taillePopulation, double probaMutation, double ratioElite, double epsilon){
		// Car on a besoin que l'ensemble des villes initiales soit de type chemin 
		Chemin cheminCourant = new Chemin();
		cheminCourant.gene = ensembleVilles;
		
		Chemin meilleurChemin = new Chemin();
		Chemin CheminEnfant1 = new Chemin();
		Chemin CheminEnfant2 = new Chemin();
		for(int i = 0; i<cheminCourant.gene.size(); i++) {
			CheminEnfant1.gene.add(null);
			CheminEnfant2.gene.add(null);
		}
		
		Chemin.distances = Ville.setDictDistances(ensembleVilles);
		
		Individu<Ville> cheminEnClasseMere = ((Individu<Ville>) cheminCourant).algorithmeGenetique(taillePopulation, probaMutation, ratioElite, epsilon, CheminEnfant1, CheminEnfant2);
		meilleurChemin.gene = cheminEnClasseMere.gene;
		// On met a jour le score du chemin 
		meilleurChemin.getScore();
		return meilleurChemin;
	}
	
	/**
     * Retourne une representation sous forme de chaine de caracteres le chemin avec les villes 
     * et la distance totale.
     * 
     * @return La representation du chemin sous forme de chaine de caracteres.
     */
	@Override
	public String toString(){
		String str = "";
		boolean existeNull = false;
		for (int i = 0; i < this.gene.size()-1; i++) {
			if (this.gene.get(i) != null) {
				str += this.gene.get(i).getNom() + " -> ";
			} else {
				str +=  "null -> ";
				existeNull = true;
			}
		}
		str += this.gene.get(this.gene.size()-1).getNom();
		if (!existeNull) {
			str += "\nLa longeur totale est egale a " + this.getScore() + "\n\n";
		}
		return str;
	}
	
	/**
     * Retourne une representation du chemin sous forme de chaine de caracteres, mais en commencant et en se terminant dans une ville de depart
     *
     * @param villeDep La ville de depart a placer en premier et en dernier dans le circuit.
     * @return La representation du chemin sous forme de chaine de caracteres avec la ville de depart.
     */
	public String circuitToString(Ville villeDep) {
		int n = this.gene.indexOf(villeDep);
		if (n!= -1) {
			ArrayList<Ville> circuit = new ArrayList<>(this.gene.subList(n, this.gene.size()));
			circuit.addAll(new ArrayList<>(this.gene.subList(0, n)));
			circuit.add(villeDep);
			String str = circuit.stream().map(Ville::getNom).collect(Collectors.joining(" -> "));
			str += "\nLa longueur totale du circuit est égale à" + this.getScore() + "\n\n";
			return str;
		}
		return "";
	}
	
	/**
     * Initialise une population de chemins en generant des chemins aleatoires.
     * Chaque chemin est constitue de villes placees dans un ordre aleatoire (construction de permutation des villes par insertion).
     *
     * @param nb Le nombre de chemins a initialiser dans la population.
     * @return La population de nouveaux chemins.
     */
	@Override
	public Population<Ville> initialiserPopulation(int nb){
      Random rand = new Random();
      Population<Ville> nvPopulation = new Population<>();
      Iterator<Ville> it;
      int i = 0;
      int ind = 0;
      int longeurCourrante;
      while (i < nb){
    	  Chemin nvIndividu = new Chemin();
          longeurCourrante = 0;
          it = this.gene.iterator();
          ind = 0;
          while(it.hasNext()){
              nvIndividu.gene.add(ind, it.next());
              longeurCourrante++;
              ind = rand.nextInt(longeurCourrante);
          }
          nvPopulation.personnes.add(nvIndividu);
          i++;
      }
      return nvPopulation;
  }

}