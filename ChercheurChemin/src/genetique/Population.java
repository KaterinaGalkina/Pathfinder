package genetique;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.Random;

/**
 * Cette classe represente une population d'individus utilisee dans un algorithme genetique.
 * Elle definit les fonctions pour la selection, le remplacement et le calcul de fitness
 * necessaires pour le calcul du meilleur chemin.
 * 
 * La classe implemente l'interface {@link Selection} pour pouvoir lancer l'algorithme 
 * genetique pour generer le meilleur individu.
 *
 * @param <E> Le type des elements constituant les individus.
 */
class Population<E> implements Selection<E>{
	double fitness;
	ArrayList<Individu<E>> personnes;
	
	/**
     * Constructeur pour initialiser une population vide.
     */
	Population(){
		this.fitness = -1;
		this.personnes = new ArrayList<>();
	}
	
	/**
     * Retourne le meilleur individu de la population, base sur une mesure de fitness.
     *
     * @return {@code Individu<E>} qui represente le meilleur individu.
     */
	@Override
	public Individu<E> meilleurIndividu() {
		// Comme personnes seront deja trie a l'endroit ou on appele la fonction on est pas oblige le retrier la population
		// Collections.sort(this.personnes, Individu.comparatorParScore());
		return this.personnes.get(0);
	}
	
	/**
     * Retourne la valeur de la fitness du meilleur individu de la population.
     *
     * @return La valeur de fitness en type {@code double}.
     */
	@Override
	public double getFitness() {
		if (this.fitness == -1) {
			this.fitness = this.meilleurIndividu().getScore();
		}
		return this.fitness;
	}
	
	/**
     * Remplace une certaine partie de la population actuelle par une partie de la nouvelle population:
     * Le pourcentage de cette population est defini par un ratioElile.
     * Par exemple si ratioElile = 0.3 alors si 30% de la nouvelle population = n individus, alors n meilleirs individus 
     * de l'ancienne population seront replaces par n individus de la nouvelle population. 
     *
     * @param ratioElite Le ratio d'individus elites a conserver (entre 0 et 1).
     * @param nouvellePopulation La nouvelle population a integrer dans la population actuelle.
     */
	@Override
	public void remplacer(double ratioElite, Population<E> nouvellePopulation) {
		// Notre population courante est deja trie, on a juste besoin de trier celle qu'on vient de generer 
		// Collections.sort(this.personnes, Individu.comparatorParScore());
		
		Collections.sort(nouvellePopulation.personnes, Individu.comparatorParScore());
		
		for (int i = 0; i < (int)nouvellePopulation.personnes.size()*ratioElite; i++) {
			// meilleur ancien au debut et pire nouveau a la fin
        	this.personnes.set(this.personnes.size() - 1 - i, nouvellePopulation.personnes.get(i));
        }

		Collections.sort(this.personnes, Individu.comparatorParScore());
	}
	
	/**
     * Selectionne aleatoirement (mais les individus qui ont une meilleure fitness 
     * on plus de chances d'etre tire) deux parents de la population.
     *
     * @return {@code Population<E>} contenant les individus selectionnes.
     */
	@Override
	public Population<E> selectionRandom(){
    	Random rand = new Random();
    	
    	ArrayList<Double> scoresChemins = new ArrayList<>(); 
    	
		double sommeTotale = 0.0;
		for(int i = 0; i < this.personnes.size(); i++) {
			// On doit compter aussi le chemin de notre ville de depart vers la premiere ville selectionne 
			// aletoirement et de la derniere ville vers la ville de depart
			scoresChemins.add(this.personnes.get(i).getScore());
			
			sommeTotale += this.personnes.get(i).getScore();
		}
    	
		Population<E> parents = new Population<E>();
		parents.personnes = new ArrayList<>();
    	double sum = 0.0;
    	
    	ArrayList<Double> bornesSomme = new ArrayList<>();
    	bornesSomme.add(0.0);
    	
    	
    	for(int i = 0; i < this.personnes.size(); i++) {
			sum += sommeTotale - scoresChemins.get(i);
    		bornesSomme.add(sum);
    	}
    	
    	int indPointLoc = rand.nextInt((int)sum); // Le premier chemin parent
    	
    	int index = 1;
    	while (bornesSomme.get(index) < indPointLoc) {
    		index ++;
    	}
    	
    	parents.personnes.add(this.personnes.get(index-1));
    	
    	sum = 0.0;
    	
    	bornesSomme = new ArrayList<>();
    	bornesSomme.add(0.0);
    	
    	for(int i = 0; i < this.personnes.size(); i++) {
    		if ( i != index-1 ) {
    			sum += sommeTotale - scoresChemins.get(i);
        		bornesSomme.add(sum);
    		} else {
    			// pour que la condition (bornesSomme.get(index) < indPointLoc) sera 
    			// surement verififiee et on prend pas le meme chemin deux fois
    			bornesSomme.add(0.0);
    		}
    	}
    	int borneMax = (int)sum;
    	
    	indPointLoc = rand.nextInt(borneMax); // Le deuxieme chemin parent
    	
    	index = 1;
    	while (bornesSomme.get(index) < indPointLoc) {
    		index ++;
    	}
    	
    	parents.personnes.add(this.personnes.get(index-1));
    	
        return parents;
    }
}