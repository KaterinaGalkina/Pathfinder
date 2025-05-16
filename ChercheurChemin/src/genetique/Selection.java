package genetique;

import java.util.Random;

/**
 * Interface de type generique qui definit les operations necessaires pour la selection et l'evolution
 * d'une population. 
 * 
 * Elle implemente un algorithme genetique general et definit des methodes abstraites
 * que toute classe implementant cette interface doit avoir, pour pouvoir
 * executer l'algorithme genetique.
 *
 * @param <E> Le type des elements constituant les individus.
 */
interface Selection<E> {
	
	/**
     * Retourne le meilleur individu de la population, base sur une mesure de fitness.
     *
     * @return {@code Individu<E>} qui represente le meilleur individu.
     */
	abstract public Individu<E> meilleurIndividu();
	
	/**
     * Retourne la valeur de la fitness du meilleur individu de la population.
     *
     * @return La valeur de fitness en type {@code double}.
     */
	abstract public double getFitness();
	
	/**
     * Remplace une certaine partie de la population actuelle par une partie de la nouvelle population:
     * Le pourcentage de cette population est defini par un ratioElile.
     * Par exemple si ratioElile = 0.3 alors si 30% de la nouvelle population = n individus, alors n meilleirs individus 
     * de l'ancienne population seront replaces par n individus de la nouvelle population. 
     *
     * @param ratioElite Le ratio d'individus elites a conserver (entre 0 et 1).
     * @param nouvellePopulation La nouvelle population a integrer dans la population actuelle.
     */
	abstract public void remplacer(double ratioElite, Population<E> nouvellePopulation);
	
	/**
     * Selectionne aleatoirement (mais les individus qui ont une meilleure fitness 
     * on plus de chances d'etre tire) deux parents de la population.
     *
     * @return {@code Population<E>} contenant les individus selectionnes.
     */
	abstract public Population<E> selectionRandom();
	
	/**
     * La methode qui implemente la logique de l'algorithme genetique pour la generation du meilleur individu.
     *
     * @param taillePopulation La taille de la population pour la premiere population, ensuite c'est ce nombre
     * 			      divise par deux qui sera la taille de la nouvelle population generee a chaque tour de la boucle while.
     * @param probaMutation La probabilite qu'une mutation d'un individu se produise (entre 0 et 1).
     * @param ratioElite Le ratio d'individus elites a conserver (entre 0 et 1).
     * @param epsilon La condition sur la fitness pour arreter l'algorithme (si la fitness de 
     * 				  la population est inferieur a epsilon, l'algorithme s'arrete).
     * @param enfant1 {@code Individu<E>} representant un tampon (un espace) pour le premier enfant.
     *                C'est cet objet qui sera modifie pendant le processus de croisement et de mutation.
     * @param enfant2 {@code Individu<E>} representant un tampon (un espace) pour le deuxieme enfant.
     *                C'est cet objet qui sera modifie pendant le processus de croisement et de mutation.
     * @return {@code Individu<E>} representant le meilleur individu trouve.
     */
	public default Individu<E> algorithmeGenetique(int taillePopulation, double probaMutation, double ratioElite, double epsilon, Individu<E> enfant1, Individu<E> enfant2){
		Random rand = new Random();
		int iter = 0;
		int maxIterations = taillePopulation * 10;
		double valeurFitnessInitiale = this.getFitness()*0.5;
		
		while (iter < maxIterations && (this.getFitness() - valeurFitnessInitiale) > epsilon) {
			Population<E> nouvellePopulation = new Population<>();
			for(int i = 1; i < (int)(taillePopulation/2); i++) {
				
				Population<E> parents = this.selectionRandom();
				
				Individu<E> parent1 = parents.personnes.get(0);
				Individu<E> parent2 = parents.personnes.get(1);
				
				parent1.croisement(parent2, enfant1, enfant2);
				
				float x = rand.nextFloat(1);
				
				if (x <= probaMutation) {
					enfant1.muter();
				}
				
				x = rand.nextFloat(1);
				
				if (x <= probaMutation) {
					enfant2.muter();
				}
				
				nouvellePopulation.personnes.add(enfant1.cloner());
				nouvellePopulation.personnes.add(enfant2.cloner());
			}
			
			this.remplacer(ratioElite, nouvellePopulation);
			iter++;
		}
		return this.meilleurIndividu();
	}
}
