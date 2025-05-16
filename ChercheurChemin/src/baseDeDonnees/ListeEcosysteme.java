package baseDeDonnees;

import java.io.*;
import java.util.ArrayList;
import peuple.Personne;

/**
 * Cette classe permet de stocker et de recuperer en securite la base de donnees de personnes.
 * En effet, comme l'objet {@code ArrayList} en Java est de type generique, lorsque les objets 
 * sont transformes en flux d'octets pour etre stockes dans un fichier, leur type s'efface, ce qui genere un warning. 
 * Pour eviter cette insecurite, cette classe implemente un objet personnalise permettant de stocker la base de donnees 
 * sous la forme d'une liste chainee de type {@link Personne}.
 */
class ListeEcosysteme implements Serializable {
	
	private static class NoeudEcosysteme implements Serializable{
		static final long serialVersionUID = 1L;
		Personne val;
		NoeudEcosysteme suiv;
		NoeudEcosysteme (Personne p) {
			this.val = p;
			this.suiv = null;
		}
	}


	private static final long serialVersionUID = 1L;
	private NoeudEcosysteme racine; 

	/**
     * Constructeur qui initialise une liste chainee vide de type {@link Personne}.
     */
	protected ListeEcosysteme() {
        this.racine = null;
    }

	// Ajoute une personne a la liste chainee.
    private void ajouter(Personne val) {
    	NoeudEcosysteme nouveauNoeud = new NoeudEcosysteme(val);
        if (this.racine == null) { 
        	this.racine = nouveauNoeud;
        } else {
        	NoeudEcosysteme current = this.racine;
            while (current.suiv != null) {
                current = current.suiv;
            }
            current.suiv = nouveauNoeud;
        }
    }
    
    /**
     * Remplit un objet de type {@link ListeEcosysteme} avec les objets de type {@link Personne}
     * contenus dans la liste passee en parametre.
     *
     * @param population La liste {@code ArrayList<Personne>} contenant les personnes a charger.
     */
    protected void charger(ArrayList<Personne> population) {
    	for (Personne pers : population) {
            this.ajouter(pers);
        }
    }
    
    // Recupere les personnes stockees dans la liste chainee et les retourne dans un {@code ArrayList<Personne>} 
    // (c'est inverse de la methode void charger(ArrayList<Personne> population)).
    private ArrayList<Personne> decharger() {
    	ArrayList<Personne> population = new ArrayList<>();
    	
    	NoeudEcosysteme current = racine;
        while (current != null) {
        	population.add(current.val);
            current = current.suiv;
        }
        
        return population;
    }
    
    /**
     * Stocke l'objet courant de type {@link ListeEcosysteme} (contenant les personnes) dans un fichier
     * sous forme de flux d'octets a l'adresse suivante : {@code src/baseDeDonnees/ecosysteme}.
     */
    protected void stockerEcosysteme() {
        try {
        	ObjectOutputStream b = new ObjectOutputStream(new FileOutputStream(new File("src/baseDeDonnees/ecosysteme")));
            b.writeObject(this);
            b.close();
        } catch (IOException e) {
            System.err.println("Erreur d'ecriture dans le fichier ecosysteme : " + e.getMessage());
        }
    }

    /**
     * Lit un fichier (a l'adresse suivante : {@code src/baseDeDonnees/ecosysteme}) et recupere un objet de type 
     * {@link ListeEcosysteme} contenant la base de donnees des personnes, puis le transforme en ArrayList et retourne une liste
     * {@code ArrayList<Personne>}.
     *
     * @return Un objet {@code ArrayList<Personne>} contenant la base de donnees des personnes,
     *         ou {@code null} si une erreur s'est produite.
     */
    protected static ArrayList<Personne> recupererEcosysteme() {
        try (ObjectInputStream b = new ObjectInputStream(new FileInputStream(new File("src/baseDeDonnees/ecosysteme")))) {
            while (true) { 
                try{
                    Object obj = b.readObject();
                    if (obj instanceof ListeEcosysteme) {
                    	ListeEcosysteme ecosysteme = (ListeEcosysteme) obj;
                        return ecosysteme.decharger();
                    }
                }
                catch(ClassNotFoundException e){
                    System.out.println("Erreur : classe n'est pas trouvee");
                    break;
                } 
                catch(EOFException e){
                    break;
                } 
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture dans le fichier ecosysteme : " + e.getMessage());
        }
        return null;
    }
}
