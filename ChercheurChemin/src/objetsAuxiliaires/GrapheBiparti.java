package objetsAuxiliaires;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente un graphe biparti, où les sommets sont divisés en deux ensembles
 * stables disjoints, et les arêtes relient uniquement des sommets appartenant 
 * à des ensembles différents.
 *
 * <p>Les arêtes du graphe sont stockées sous forme de listes de successeurs
 * des sommets de l'ensemble 1.</p>
 *
 * @param <A> le type des sommets de l'ensemble 1
 * @param <B> le type des sommets de l'ensemble 2
 */
public class GrapheBiparti<A, B> {
    
    private Set<A> ensembleStable1;
    private Set<B> ensembleStable2;

    // Les aretes du graphe, ou les cles sont les sommets de l'ensemble A qui associe ces 
    // sommets voisins de l'ensemble B
    private HashMap<A, Set<B>> aretes; 
    

    /**
     * Constructeur pour créer un graphe biparti.
     *
     * @param ens1 l'ensemble stable 1 de sommets
     * @param ens2 l'ensemble stable 2 de sommets
     * @param aretes les arêtes du graphe, représentées comme un dictionnaire ou 
     * les cles sont les sommets de l'ensemble A qui associe ces 
     * sommets voisins de l'ensemble B
     */
    public GrapheBiparti(Set<A> ens1, Set<B> ens2, HashMap<A, Set<B>> aretes) {
        this.ensembleStable1 = ens1;
        this.ensembleStable2 = ens2;
        this.aretes = aretes;
    }

    /**
     * Trie les sommets de l'ensemble 1 en fonction du nombre de successeurs qu'ils possèdent.
     *
     * <p>Les sommets ayant le moins de successeurs apparaîtront en premier dans
     * la liste triée.</p>
     *
     * @return une liste des sommets de l'ensemble 1 triée par ordre croissant
     *         du nombre de successeurs
     */
    private ArrayList<A> sommetsTriesParSuccesseurs() {
        ArrayList<A> listeTriee = new ArrayList<>(ensembleStable1);

        listeTriee.sort((a1, a2) -> {
            int successeursA1 = aretes.getOrDefault(a1, Set.of()).size();
            int successeursA2 = aretes.getOrDefault(a2, Set.of()).size();

            return Integer.compare(successeursA1, successeursA2);
        });

        return listeTriee;
    }

    /**
     * Trouve un couplage maximal possible entre les sommets des deux ensembles stables.
     *
     * <p>Un couplage maximal possible essaie d'associer le maximum de sommets de l'ensemble 1 aux sommets de
     * l'ensemble 2.</p>
     *
     * @return un dictionnaire où chaque sommet de l'ensemble 1 est associé à un sommet 
     *         de l'ensemble 2. Si un sommet de l'ensemble 1 ne peut pas être 
     *         associé, il n'apparaît pas dans le dictionnaire.
     */
    public HashMap<A, B> couplageMaximal() {
        HashMap<A, B> solution = new HashMap<>();
        Set<B> sommetsLibres = new HashSet<>(this.ensembleStable2);
        ArrayList<A> sommetsEns1Trie = this.sommetsTriesParSuccesseurs();

        for (A sommet1 : sommetsEns1Trie) {
            if (this.aretes.containsKey(sommet1) && !this.aretes.get(sommet1).isEmpty()) {
                Set<B> intersectionSommetsLibres_Sommet2 = new HashSet<>(sommetsLibres);
                intersectionSommetsLibres_Sommet2.retainAll(this.aretes.get(sommet1));

                if (!intersectionSommetsLibres_Sommet2.isEmpty()) {
                    B sommet2 = intersectionSommetsLibres_Sommet2.iterator().next(); // On récupère le premier sommet libre
                    solution.put(sommet1, sommet2);
                    sommetsLibres.remove(sommet2);
                }
            }
        }
        return solution;
    }
}
