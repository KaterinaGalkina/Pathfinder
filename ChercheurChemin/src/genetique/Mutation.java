package genetique;

import java.util.ArrayList;
import java.util.Random;

/**
 * Interface de type generique qui defini une operation de mutation sur un individu represente 
 * par une liste d'elements de type {@code E} (generique).
 * 
 * @param <E> Le type des elements constituant l'individu.
 */
interface Mutation<E> {
	
	/**
     * Methode par defaut pour faire muter sur un individu.
     * Ici la mutation est defini comme un changement aleatoire des deux elements distincts 
     * dans une liste qui represente l'individu.
     * 
     * @param individu Un {@code ArrayList<E>} d'elements qui represnte l'individu que nous devons faire muter.
     *                 La mutation modifie directement cette liste.
     * @throws IllegalArgumentException Si le {@code ArrayList<E>} qui represente l'individu est vide ou nulle.
     */
	default void muter(ArrayList<E> individu) {
	    Random rand = new Random(); //initialisation de la graine random
	    
	    try {
	    	int ind1 = rand.nextInt(individu.size()); // indice de l'element1 de permutation
		    int ind2 = rand.nextInt(individu.size()); // indice de l'element2 de permutation
		    // On continue jusqu'a obtenir des indices differentes
		    while (ind1 == ind2) {
		    	ind2 = rand.nextInt(individu.size());
		    }
		    E temp = individu.get(ind1);
		    individu.set(ind1, individu.get(ind2));
		    individu.set(ind2, temp);
		    
	    } catch (IllegalArgumentException e) {
	    	System.err.println("Erreur lors de la mutation d'un individu ! La liste representant l'individu est vide. ");
	    }
    }
}
