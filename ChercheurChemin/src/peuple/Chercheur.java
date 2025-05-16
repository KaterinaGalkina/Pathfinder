package peuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import objetsAuxiliaires.GrapheBiparti;
import territoire.Ville;
import verificationEntree.Coherence;

/**
 * Classe representant un chercheur.
 * Un chercheur est un titulaire qui peut encadrer des etudiants en these.
 * Cette classe fournit des methodes pour gerer les etudiants en these, 
 * ainsi que des outils pour attribuer des etudiants a d'autres encadrants en cas de suppression.
 */
public class Chercheur extends Titulaire {
    private static final long serialVersionUID = 1L;
	private Set<Etudiant> etudiants; 
    
	/**
     * Constructeur de la classe Chercheur.
     *
     * @param nom Le nom du chercheur.
     * @param prenom Le prenom du chercheur.
     * @param age L'age du chercheur.
     * @param ville La ville ou habite le chercheur.
     * @param disciplines Les disciplines du chercheur.
     * @param numBureau Le numero de bureau du chercheur.
     * @param etudiants Les etudiants en these encadres par ce chercheur.
     */
    public Chercheur(String nom, String prenom, int age, Ville ville, Set<Discipline> disciplines, int numBureau, Set<Etudiant> etudiants) {
        super(nom, prenom, age, ville, disciplines, numBureau);
        this.etudiants=etudiants;
    }
    
    /**
     * Retourne les fonctions du chercheur.
     *
     * @return Une liste des fonctions que le chercheur occupe (chercheur et titulaire).
     */
    @Override
    public ArrayList<String> getFonctions(){
    	ArrayList<String> fonctions = new ArrayList<>();
    	fonctions.add("chercheur");
    	fonctions.add("titulaire");
    	return fonctions;
    }
    
    /**
     * Retourne le nombre d'etudiants encadres par le chercheur.
     *
     * @return Le nombre d'etudiants en these.
     */
    public int getNbrEtudiant(){
        return this.etudiants.size();
    }
    
    /**
     * Retourne l'ensemble des etudiants encadres par ce chercheur.
     *
     * @return Un ensemble contenant les etudiants en these.
     */
    public Set<Etudiant> getEtudiantsSet(){
    	return this.etudiants;
    }
    
    /**
     * Verifie si le chercheur peut encadrer un etudiant donne (selon les disciplines).
     *
     * @param e L'etudiant a verifier.
     * @return true si le chercheur peut encadrer cet etudiant, false sinon.
     */
    @Override 
    public boolean peutPrendreEtudiant(Etudiant e) {
    	if (e != null && this.getDisciplines().contains(e.getDiscipline())) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Ajoute un etudiant a la liste des etudiants encadres par ce chercheur.
     *
     * @param e L'etudiant a ajouter.
     */
    @Override
    public void addEtudiant(Etudiant e){
    	if (e!= null) {
    		if (this.etudiants == null) {
    			this.etudiants = new HashSet<Etudiant>();
    		} 
    		this.etudiants.add(e);
    	}
    }
    
    /**
     * Retire un etudiant de la liste des etudiants encadres.
     *
     * @param e L'etudiant a retirer.
     */
    @Override 
    public void popEtudiant(Etudiant e) {
    	if (e!= null && this.etudiants.contains(e)) {
    		this.etudiants.remove(e);
    	}
    }
    
    /**
     * Retourne une liste d'etudiants que ce chercheur peut encadrer.
     *
     * @param baseDeDonneesCourrante La base de donnees contenant toutes les personnes.
     * @param avecEtudiantsCourants  Si false, exclut les etudiants deja encadres.
     * @return Une liste d'etudiants que ce chercheur peut encadrer.
     */
    @Override
    public ArrayList<Etudiant> listeEtudiantsPossibles(ArrayList<Personne> baseDeDonneesCourrante, boolean avecEtudiantsCourants) {
		ArrayList<Etudiant> etudiantsPossibles = new ArrayList<>();
		for (Personne pers: baseDeDonneesCourrante){
			if (pers instanceof Etudiant etud && this.getDisciplines().contains(etud.getDiscipline())) {
				etudiantsPossibles.add(etud);
			}
		}
		if (!avecEtudiantsCourants && this.etudiants != null && !this.etudiants.isEmpty()) {
			etudiantsPossibles.removeAll(this.etudiants);
		}
		return etudiantsPossibles;
	}
    

    /**
     * Tentative de suppression de tous les etudiants encadres par ce chercheur.
     * Cette methode effectue un transfert d'etudiants vers d'autres encadrants ou MCF disponibles, si cela est possible.
     * Si ce n'est pas possible elle ne fait rien, mais dans les deux cas tous les modifications des encadrants des etudiants possibles sont stockes
     * dans les listes passes entre parametres ({@code ArrayList<Chercheur> chercheursTrouvePourEtudiants}, 
     * {@code ArrayList<Set<Etudiant>> etudiantsParChercheur}, {@code HashMap<Etudiant, MCF> couplage}).
     *
     * @param etudiantsASupprimer Les etudiants a supprimer de cet encadrant (peut etre tous les etudiants ou lors de la mise a jour
     * 	      des disciplines seulement ceux qui sont incompatibles).
     * @param personnes La liste de toutes les personnes dans la base.
     * @param chercheursTrouvePourEtudiants Une liste pour stocker les chercheurs trouves pour les etudiants.
     * @param etudiantsParChercheur Une liste des etudiants compatibles par chercheur.
     * @param couplage Une map contenant le couplage maximal entre etudiants et MCFs.
     * @return true si tous les etudiants ont ete transferes avec succes, false sinon.
     */
	public boolean essaieSupprimerTousEtudiants(Set<Etudiant> etudiantsASupprimer, ArrayList<Personne> personnes, ArrayList<Chercheur> chercheursTrouvePourEtudiants, ArrayList<Set<Etudiant>> etudiantsParChercheur, HashMap<Etudiant, MCF> couplage){
		if (etudiantsASupprimer != null && !etudiantsASupprimer.isEmpty()) {
			Set<Etudiant> etudiantsAPlacer = new HashSet<>(etudiantsASupprimer);
			
			// on stocke tous les disciplines des etudiants qui n'ont plus de titulaire
			Set<Discipline> disciplinesEtudiantsAPlacer = new HashSet<>();
			for (Etudiant e: etudiantsAPlacer){
				if (!disciplinesEtudiantsAPlacer.contains(e.getDiscipline())){
					disciplinesEtudiantsAPlacer.add(e.getDiscipline());
				}
			}
			// on stocke aussi les mcfs possibles pour les etudiants pour ne pas reparcourir toute la liste des personnes si nous n'avons pas pu trouver de chercheur pour chaque etudiant
			Set<MCF> mcfPossibles = new HashSet<>();
			for (Personne pers: personnes){
				// On va d'abord attribuer le max des etudiants qu'on peut aux chercheurs
				if (pers instanceof Chercheur cherch && !cherch.equals(this)) { // On ne veut pas attribuer les etudinats au chercheur qu'on est en train de supprimer
					
					Set<Discipline> intersectionDisciplines = new HashSet<>(disciplinesEtudiantsAPlacer);
					intersectionDisciplines.retainAll(cherch.getDisciplines()); 
					if (!intersectionDisciplines.isEmpty()) {  

						Set<Etudiant> etudAttribues = new HashSet<>(); // Les etudiants que nous avons pu attribuer aux titulaire
						for (Etudiant e: etudiantsAPlacer){
							if (intersectionDisciplines.contains(e.getDiscipline())){
								etudAttribues.add(e);
							}
						}

						// Sinon on doit stocker les chercheurs pour les etudiants, car on doit d'abord savoir si c'est possible d'attribuer tous les etudiants aux nouveaux titulaires
						chercheursTrouvePourEtudiants.add(cherch);
						etudiantsParChercheur.add(etudAttribues);
						
						etudiantsAPlacer.removeAll(etudAttribues); // On enleve tous les etudiants qui peuvent etre attribues aux autres chercheurs
						disciplinesEtudiantsAPlacer.removeAll(intersectionDisciplines);
					}

				} else if (pers instanceof MCF mcf && mcf.getEtudiant() == null){
					Set<Discipline> intersectionDisciplines = new HashSet<>(disciplinesEtudiantsAPlacer);
					intersectionDisciplines.retainAll(mcf.getDisciplines());
					if (!intersectionDisciplines.isEmpty()) { 
						mcfPossibles.add(mcf);
					}
				}
			}
			
			if (!etudiantsAPlacer.isEmpty()){ // Si il nous reste toujours des etudiants sans encadrants, alors on verifie si on peut les affecter aux mcf
				HashMap<Etudiant, Set<MCF>> compatibilites = new HashMap<>(); // On stocke les compatibilites entre les etudiants et les MCFs
				for (Etudiant e: etudiantsAPlacer){
					for (MCF mcf: mcfPossibles){
						if (mcf.getDisciplines().contains(e.getDiscipline())){
							if (!compatibilites.containsKey(e)) {
								compatibilites.put(e, new HashSet<>());
							}
							compatibilites.get(e).add(mcf);
						}
					}
				}
				GrapheBiparti<Etudiant, MCF> graphe = new GrapheBiparti<>(etudiantsAPlacer, mcfPossibles, compatibilites);
				HashMap<Etudiant, MCF> couplageCopie = graphe.couplageMaximal();
				
				for (Etudiant e: couplageCopie.keySet()) { // On remplir notre set entre parametres
					couplage.put(e, couplageCopie.get(e));
				}
				
				if (couplage.size() != etudiantsAPlacer.size()){ 		
					return false;
				} 
				// En cas de reussite de trouver les encadrants a chaque etudiant on change les encadrants des etudiants
				for (Etudiant e: couplage.keySet()){
					e.setEncadrant(couplage.get(e)); // On lui attribue le MCF que notre fonction de couplage maximal a trouve
				}
				int i = 0;
				for (Set<Etudiant> etudiants: etudiantsParChercheur){
					Chercheur cherch = chercheursTrouvePourEtudiants.get(i);
					for (Etudiant etud: etudiants){
						etud.setEncadrant(cherch);
					}
					i++;
				}
				return true; 

			} else { // Si on a reussi a placer tous les etudiants avec les chercheurs on est bon
				int i = 0;
				for (Set<Etudiant> etudiants: etudiantsParChercheur){
					Chercheur cherch = chercheursTrouvePourEtudiants.get(i);
					for (Etudiant etud: etudiants){
						etud.setEncadrant(cherch);
					}
					i++;
				}
				return true;
			}
		}
		return true; // Si le chercheur n'a pas d'etudiants on est bon, on peut le supprimer tkl
	}

	/**
     * Est appele UNIQUEMENT apres la premiere fonction. (La separation est necessaire pour assurer la possibilite 
     * d'utilisation de cette fonction pour la version avec et sans interface graphique)
     * Utilise pour mettre a jour les affectations des etudiants possibles trouves a l'aide de la focntion 
     * {@code essaieSupprimerTousEtudiants} precedente.
     * 
     * @param chercheursTrouvePourEtudiants Une liste des chercheurs trouves pour les etudiants.
     * @param etudiantsParChercheur Une liste des etudiants compatibles par chercheur.
     * @param couplage Une map contenant le couplage maximal entre etudiants et MCFs.
     */
	public void supprimerMaxDesEtudiants(ArrayList<Chercheur> chercheursTrouvePourEtudiants, ArrayList<Set<Etudiant>> etudiantsParChercheur, HashMap<Etudiant, MCF> couplage){
		int i = 0;
		for(Chercheur cherch: chercheursTrouvePourEtudiants){
			Set<Etudiant> etudsCherch = etudiantsParChercheur.get(i);
			for (Etudiant e: etudsCherch){
				e.setEncadrant(cherch);
			}
			i++;
		}
		for (Etudiant e: couplage.keySet()){
			e.setEncadrant(couplage.get(e));
		}
	}
	
	/**
     * Est utilise pour ajouter autant d'etudiant a ce chercheur que l'utilisateur veut
     * 
     * @param scanner Pour lire l'entree de l'utilisateur.
     * @param personnes La base de donnees des personnes.
     */
	@Override
	public void ajouterEtudiantAEncadrant(Scanner scanner, ArrayList<Personne> personnes) {
 		System.out.println("\nVoici la liste des étudiants que ce chercheur peut encadrer en thèse :");
 		Set<Integer> idEtudPossibles = new HashSet<>();
 		String reponseUtilisateur = "";
 		
		// Si ce titulaire a deja des etudiants on ne veut peut proposer les memes qu'il a
 		// On fait moins de comparaisons en separant la boucle
		if (this.etudiants!= null && !this.etudiants.isEmpty()) {
			for(Personne p: personnes) {
				if (p instanceof Etudiant) {
					Etudiant e = (Etudiant)p;
					if (!this.etudiants.contains(e) && this.getDisciplines().contains(e.getDiscipline())) {
						System.out.println(e.toString());
						idEtudPossibles.add(e.getID());
					}
				}
			}
		} else {
			for(Personne p: personnes) {
				if (p instanceof Etudiant) {
					Etudiant e = (Etudiant)p;
					if (this.getDisciplines().contains(e.getDiscipline())) {
						System.out.println(e.toString());
						idEtudPossibles.add(e.getID());
					}
				}
			}
		}
		
		System.out.println("\nVeuillez entrer les ID des étudiants que vous voulez ajouter à ce chercheur (séparés par des virgules) :");
		String[] ids = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
		Set<Integer> idEtudiantsAAjouter = new HashSet<>();
		for(String idCourant: ids) {
			// On verifie si c'est bien un entier avant de le convertir
			int idEtudiant = Coherence.verificationEntier(idCourant);
			if (idEtudPossibles.contains(idEtudiant)) {
				idEtudiantsAAjouter.add(idEtudiant);
			} else {
				while(true) {
					System.out.println("\nL'ID sélectionné n'est pas reconnu.");
					reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("reessayer", "abandonner"), "\nVoulez-vous réessayer de saisir cet ID ?\n"
                			+ "Tapez 'réessayer' pour ressaisir\nTapez 'abandonner' pour garder seulement celles qui sont bien sélectionnées.");
                	if (reponseUtilisateur.equals("reessayer")) {
                		System.out.println("\nSélectionnez l'ID corrigé :");
                		idEtudiant = Coherence.verificationEntier(scanner.nextLine().trim()); 
                		if (idEtudPossibles.contains(idEtudiant)) {
                			idEtudiantsAAjouter.add(idEtudiant);
                        } 
                	} else {
                		break;
                	}
				}
			}
		}
		
		for(Personne p: personnes) {
			if (idEtudiantsAAjouter.contains(p.getID())) {
				((Etudiant)p).setEncadrant(this);
			}
		}
 	}
    
	/**
     * Retourne une representation sous forme de chaine de caracteres du chercheur 
     * 
     * @return La representation du chercheur sous forme de chaine de caracteres.
     */
    @Override
    public String toString(){
    	String str = "\nChercheur :" + super.toString() + "\n";
		if (this.etudiants != null) {
			str += "\tEtudiants en these :";
			Iterator<Etudiant> iterator = this.etudiants.iterator();
			while (iterator.hasNext()) {
				Etudiant etudiant = iterator.next();
			    str += " -> " + etudiant.getNomPrenom();
			}
			str += "\n";
		}
    	return str;
    }
}
