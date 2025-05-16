package genetique;

import java.util.ArrayList;
import java.util.Random;

/**
 * Interface de type generique qui definit une operation de croisement (crossover)
 * entre deux individus parents pour generer leurs deux enfants.
 *
 * @param <E> Le type des elements constituant les individus.
 */
interface Croisement<E> {
	
	/**
     * Methode par defaut pour faire un croisement entre deux individus parents.
     * Le croisement genere deux nouveaux individus (enfants) a partir des deux parents.
     *
     * @param parent1 {@code ArrayList<E>} representant le premier parent.
     * @param parent2 {@code ArrayList<E>} representant le deuxieme parent (doit avoir la meme taille que le premier parent).
     * @param enfant1 {@code ArrayList<E>} representant le premier enfant (resultat du croisement).
     *                Cette liste doit avoir la meme taille que les parents.
     * @param enfant2 {@code ArrayList<E>} representant le deuxieme enfant (resultat du croisement).
     *                Cette liste doit avoir la meme taille que les parents.
     */
	default void croisement(ArrayList<E> parent1, ArrayList<E> parent2, ArrayList<E> enfant1, ArrayList<E> enfant2) {
        
		try {
			for (int i = 0; i < parent2.size(); i++) {
	        	enfant1.set(i,null);
	            enfant2.set(i,null);
	        }
		} catch (IllegalArgumentException e) {
			System.out.println("Erreur lors du croisement des individus ! Les listes enfants ne sont pas de meme taille que les parents.");
			return;
		}

        int i = 0;
        Random rand = new Random(); 
        
        ArrayList<ArrayList<E>> parents = new ArrayList<>();
        parents.add(parent1);
        parents.add(parent2);
        
        
        while (i < parents.get(0).size()) {
            int x = rand.nextInt(2); // selection du premier elements entre les Parents 1 et 2
            enfant1.set(i, parents.get(x).get(i));
            enfant2.set(i, parents.get(1 - x).get(i)); // x=1 ou 0 donc 1-x=0 ou 1

            // Trouver l'indice de la ville correspondante dans l'autre parent
            
            int ind = parents.get(1 - x).indexOf(enfant1.get(i));
            
            // Boucle pour placer les autres éléments
            while (ind != i && enfant1.get(ind) == null) {
            	enfant1.set(ind, parents.get(x).get(ind));
            	enfant2.set(ind, parents.get(1 - x).get(ind));
                ind = parents.get(1 - x).indexOf(enfant1.get(ind));
            }

            // Parcours jusqu'a un element non renseigne dans Enfant0 puisque Enfant0 et Enfant1 sont renseigne de maniere miroire
            while (i < parents.get(0).size() && enfant1.get(i) != null) {
                i++;
            }
        }
        
    }
}
