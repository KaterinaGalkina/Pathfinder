package objetsAuxiliaires;

/**
 * Represente une structure de donnees composee de deux elements, ou l'ordre des elements
 * n'a pas d'importance (un Couple de {@code (a, b)} est equivalent a un Couple de {@code (b, a)}).
 *
 * <p>Cette classe est utilisee pour stocker des relations symetriques comme les distances entre deux villes.</p>
 *
 * @param <A> le type du premier element
 * @param <B> le type du deuxième element
 */
public class Couple<A, B> {
    
    private final A premier;
    private final B deuxieme;

    /**
     * Constructeur pour creer un couple avec deux elements.
     *
     * @param premier le premier element du couple
     * @param deuxieme le deuxieme element du couple
     */
    public Couple(A premier, B deuxieme) {
        this.premier = premier;
        this.deuxieme = deuxieme;
    }

    /**
     * Recupere le premier element du couple.
     *
     * @return le premier element
     */
    public A getP() {
        return this.premier;
    }

    /**
     * Recupere le deuxieme element du couple.
     *
     * @return le deuxieme element
     */
    public B getD() {
        return this.deuxieme;
    }

    /**
     * Redefini le calcul du code de hachage pour le couple. 
     * Pour que ce code soit independant de l'ordre des elements dans le couple
     *
     * @return un entier représentant le code de hachage
     */
    @Override
    public int hashCode() {
        return (premier.hashCode() + deuxieme.hashCode());
    }

    /**
     * Redefini la comparaison des deux couples, pour comparer les elements a l'interieur
     * du couple et de ne pas prendre en compte l'ordre des elements
     *
     * @param o2 l'objet a comparer avec ce couple
     * @return {@code true} si les deux couples sont egaux, sinon {@code false}
     */
    @Override
    public boolean equals(Object o2) {
        if (o2 instanceof Couple) {
            Couple<?, ?> c2 = (Couple<?, ?>) o2;
            return (c2.premier.equals(this.premier) && c2.deuxieme.equals(this.deuxieme)) ||
                   (c2.premier.equals(this.deuxieme) && c2.deuxieme.equals(this.premier));
        }

        return false;
    }

    /**
     * Retourne une representation en chaine de caracteres du couple.
     *
     * @return une chaîne de caracteres au format {@code "(premier, deuxieme)"}
     */
    @Override
    public String toString() {
        return "(" + this.premier.toString() + ", " + this.deuxieme.toString() + ")";
    }
}