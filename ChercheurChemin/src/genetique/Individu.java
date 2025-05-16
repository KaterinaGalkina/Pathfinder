package genetique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * C'est une classe abstraite qui permet de representer un individu pour l'algorithme genetique.
 * Un individu est defini par un {@code ArrayList<E>} liste de type generique qui represente une gene
 * de l'individu, ainsi que par un score sous forme d'un reel positif, cela mesure sa qualite. 
 * Cette classe implemente les interfaces {@code Croisement}
 * et {@code Mutation}, permettant de definir les operations sur les genes des individus 
 * (Comment une gene va muter ou comment le coisement des genes se passe)
 * 
 * @param <E> Le type des elements qui constituent les genes de l'individu.
 */
abstract class Individu<E> implements Croisement<E>, Mutation<E> {
	
	/**
     * Liste qui represente une gene qui d'un individu.
     */
    public ArrayList<E> gene;
    
    /**
     * Score de l'individu qui permet de mesurer la qualite d'un individu.
     */
    public double score;
    
    /**
     * Constructeur par defaut qui initialise le score de l'individu egale a -1 et 
     * cree ue liste vide qui va representer la gene de cet individu.
     */
    public Individu() {
    	this.score = -1;
        this.gene = new ArrayList<>();
    }
    
    /**
     * Retourne un comparateur permettant de comparer deux individus en fonction de
     * leur score.
     * 
     * @param <E> Le type des individus (la valeur dans la gene qui le defini).
     * @return Un comparateur pour comparer les individus selon leur score.
     */
    public static <E> Comparator<Individu<E>> comparatorParScore() {
        return new Comparator<Individu<E>>() {
            @Override
            public int compare(Individu<E> individu1, Individu<E> individu2) {
                return Double.compare(individu1.getScore(), individu2.getScore());
            }
        };
    }

    /**
     * Retourne le score de l'individu.
     *
     * @return Le score de l'individu en type {@code double}.
     */
    abstract public double getScore();
    
    /**
     * Permet de creer une copie de l'individu actuel.
     *
     * @return Un nouvel individu identique a l'individu actuel, (mais avec une adresse dans la memoire differente).
     */
    abstract public Individu<E> cloner();

    /**
     * Effectue un croisement entre l'individu actuel et un autre parent, generant
     * deux enfants.
     * 
     * @param parent2 L'autre parent avec lequel effectuer le croisement.
     * @param enfant1 Premier enfant genere.
     * @param enfant2 Deuxieme enfant genere.
     */
    public void croisement(Individu<E> parent2, Individu<E> enfant1, Individu<E> enfant2) {
    	this.croisement(this.gene, parent2.gene, enfant1.gene, enfant2.gene);
    }

    /**
     * Effectue une mutation sur la gene de l'individu actuel.
     */
    public void muter() {
	    this.muter(this.gene);
    }
    
    /**
     * Initialise une population d'individus de la taille du nombre passe entre parametres.
     * Cette methode doit etre implementee dans les classes derivees, puisque ce classe {@link Individu<E>} est abstrait.
     *
     * @param nb La taille de la population a generer.
     * @return Une population initialisee.
     */
    abstract public Population<E> initialiserPopulation(int nb);
    
    /**
     * La methode genere une population a partir d'un individu donne et lance l'algorithme genetique.
     *
     * @param taillePopulation La taille de la population qui sera generee.
     * @param probaMutation La probabilite qu'une mutation d'un individu se produise (entre 0 et 1).
     * @param ratioElite Le ratio d'individus elites a conserver (entre 0 et 1).
     * @param epsilon La condition sur la fitness pour arreter l'algorithme.
     * @param enfin1 {@code Individu<E>} representant un tampon (un espace) pour le premier enfant.
     *               C'est cet objet qui sera modifie pendant le processus de croisement et de mutation.
     * @param enfin2 {@code Individu<E>} representant un tampon (un espace) pour le deuxieme enfant.
     *               C'est cet objet qui sera modifie pendant le processus de croisement et de mutation.
     * @return {@code Individu<E>} representant le meilleur individu trouve.
     */
    public Individu<E> algorithmeGenetique(int taillePopulation, double probaMutation, double ratioElite, double epsilon, Individu<E> enfin1, Individu<E> enfin2){
    	Population<E> population = this.initialiserPopulation(taillePopulation);
    	// On va travailler directement sur la population trie
    	Collections.sort(population.personnes, Individu.comparatorParScore());
    	return population.algorithmeGenetique(taillePopulation, probaMutation, ratioElite, epsilon, enfin1, enfin2);
    }
}
