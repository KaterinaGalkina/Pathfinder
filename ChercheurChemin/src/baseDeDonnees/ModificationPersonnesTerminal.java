package baseDeDonnees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import filtrage.Trie;
import peuple.*;
import territoire.Ville;
import verificationEntree.*;

/**
 * Cette classe permet de donner la possibilite a l'utilisateur de modifier la base de donnees de personnes en toute securite, 
 * tout en assurant la coherence des relations entre les personnes. 
 *
 * <p>Les contraintes a respecter lors de la modification de la base de donnees :</p>
 * <ul>
 *   <li>Un etudiant a exactement un encadrant.</li>
 *   <li>Un MCF a un ou zero etudiant en these.</li>
 *   <li>Un chercheur peut avoir zero ou plusieurs etudiants en these.</li>
 *   <li>La discipline d'un etudiant doit faire partie des disciplines de son encadrant.</li>
 *   <li>Un titulaire a une ou deux disciplines.</li>
 *   <li>Un etudiant a exactement une discipline.</li>
 * </ul>
 */
public class ModificationPersonnesTerminal {
	
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private ModificationPersonnesTerminal() {
		
	}
	
	/**
     * Remplace la base de donnees des personnes (stockee sous forme d'un objet {@link ListeEcosysteme}) 
     * dans le fichier situe a {@code src/baseDeDonnees/ecosysteme} par la nouvelle base mise a jour.
     *
     * @param nouvelleBaseDeDonnees Nouvelle base de donnees mise a jour contenant les modifications 
     * (ajouts, suppressions ou modifications des personnes).
     */
	public static void actualiserBaseDeDonnees(ArrayList<Personne> nouvelleBaseDeDonnees) {
		ListeEcosysteme ecos = new ListeEcosysteme();
		ecos.charger(nouvelleBaseDeDonnees);
		ecos.stockerEcosysteme();
	}

	// Ajoute autant de personnes que souhaite l'utilisateur a la base de donnees courante.
	private static void ajouter(Scanner scanner, ArrayList<Personne> baseDeDonneesCourrante, ArrayList<Ville> baseDeVilles) {
	    boolean continuer = true; 
	    boolean reponseValide;
	    String reponseUtilisateur;
	    
	    // Pour ajouter autant de personnes que l'utilisateur veut
	    while (continuer) {
	        System.out.println("\nEntrez son nom :");
	        String nomNouvellePersonne = Coherence.verificationChaineChar(scanner);

	        System.out.println("\nEntrez son prénom :");
	        String prenomNouvellePersonne = Coherence.verificationChaineChar(scanner);

	        int ageNouvellePersonne = -1;
	        while (ageNouvellePersonne < 0) {
	        	System.out.println("\nEntrez son âge :");
	        	ageNouvellePersonne = Coherence.verificationEntier(scanner.nextLine().trim());
	        	if (ageNouvellePersonne < 0) {
	        		System.out.println("Age doit etre un entier positif. Veuillez reessayer.");
	        	}
	        }

	        Ville villeNouvellePersonne = Coherence.verifierVille(scanner, baseDeVilles, true);

	        reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("etudiant", "titulaire", "annuler"), "\nPour ajouter un(e) étudiant(e), tapez 'etudiant'\n"
	                + "Pour ajouter un(e) titulaire, tapez 'titulaire'\n"
	                + "Pour annuler l'ajout de cette personne, tapez 'annuler'");
	        
	        switch (reponseUtilisateur) {
	            case "etudiant":
	                System.out.println("\nEntrez son sujet de thèse :");
	                String sujetDeTheseNouveauEtud = scanner.nextLine().trim();

	                System.out.println("\nVeuillez choisir sa discipline parmi ci-dessous :");
	                Discipline.afficherDisciplines();
	                
	                Discipline disciplineNouveauEtud = null;
	                boolean disciplineCorrecte = false;
	                while (!disciplineCorrecte) {
	                    String discip = scanner.nextLine();
	                    disciplineNouveauEtud = Discipline.getDiscipline(discip);
	                    if (disciplineNouveauEtud != null) {
	                        disciplineCorrecte = true;
	                    } else {
	                    	System.out.println("\nVeuillez choisir sa discipline parmi ci-dessous :");
	    	                Discipline.afficherDisciplines();
	                    }
	                }

	                int anneeDeTheseNouveauEtud = -1;
	                while (anneeDeTheseNouveauEtud < 1 || anneeDeTheseNouveauEtud > 3) {
	                	System.out.println("\nEntrez son année de thèse (un entier entre 1 et 3) :");
	    	        	anneeDeTheseNouveauEtud = Coherence.verificationEntier(scanner.nextLine().trim());
	    	        	if (anneeDeTheseNouveauEtud < 1 || anneeDeTheseNouveauEtud > 3) {
	    	        		System.out.println("Année de thèse n'est pas valide. Veuillez réessayer.");
	    	        	}
	    	        	
	    	        }

	                Etudiant nouveauEtudiant = new Etudiant(
	                        nomNouvellePersonne,
	                        prenomNouvellePersonne,
	                        ageNouvellePersonne,
	                        villeNouvellePersonne,
	                        sujetDeTheseNouveauEtud,
	                        disciplineNouveauEtud,
	                        anneeDeTheseNouveauEtud,
	                        null 
	                );

	                System.out.println("\nVeuillez choisir son encadrant parmi ci-dessous :\n");

	                // On affiche uniquement ceux a qui est possible d'ajouter cet etudiant selon les disciplines et
	                // si le titulaire peut prendre encore un etudiant ou non (Si la place est prise, on ne veut pas supprimmer l'autre etudiant)
	               
	                Set<Integer> idTutilairesPossibles = new HashSet<>();
	                for (Personne p : baseDeDonneesCourrante) {
	                    if (p instanceof Titulaire) {
	                        if (((Titulaire) p).peutPrendreEtudiant(nouveauEtudiant)) {
	                            System.out.println(p.toString());
	                            idTutilairesPossibles.add(p.getID());
	                        }
	                    } 
	                } 
	                
	                if (idTutilairesPossibles.isEmpty()) {
	                	System.out.println("\nL'ajout est impossible puisqu'il n'existe pas de titulaire qui puisse prendre en thèse cet étudiant.\n");
	                	
	                	nouveauEtudiant = null;
	                } else {
	                	
	                	// On cherche le titulaire selectionne par l'utilisateur
		                int idTitulaireNouveauEtud = -1;
		                while (true) {
		                	System.out.println("\nVeuillez entrer l'ID du titulaire choisi : ");
		                	idTitulaireNouveauEtud = Coherence.verificationEntier(scanner.nextLine().trim());
		                	if (idTitulaireNouveauEtud != -1 && idTutilairesPossibles.contains(idTitulaireNouveauEtud)) {
		                		break;
		                	} else {
		                		System.out.println("L'ID n'est pas reconnu, veuillez réessayer.");
		                	}
		    	        }
		                
		                // On met a jour le titulaire de l'etudiant 
		                // (ainsi que la liste des etudiants du titulaire en meme temps, a l'interieur de setEncadrant)
		                for (Personne p: baseDeDonneesCourrante) {
		                	if (p.getID() == idTitulaireNouveauEtud) {
		                		nouveauEtudiant.setEncadrant((Titulaire)p);
		                		break;
		                	}
		                }

		                System.out.println("\nL'Étudiant ajouté :");
		                System.out.println(nouveauEtudiant.toString());

		                baseDeDonneesCourrante.add(nouveauEtudiant);
	                }
	                break;

	            case "titulaire":
	            	
	            	System.out.println("\nVeuillez choisir ses disciplines (il faut en choisir 1 ou 2) parmi celles ci-dessous (séparées par des virgules) :");
	    			Discipline.afficherDisciplines();
	                
	    			Set<Discipline> disciplinesNouveauTitulaire = new HashSet<>();
	                reponseValide = false;

	                // Tant que l'utilisateur n'a pas selectionne exactement 1 ou 2 disciplines valides, on continue 
	                while (!reponseValide) {
	                    System.out.println("\nLes disciplines du titulaire :");
	                    String[] disciplinesSelectionnees = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
	                     
	                    // Si il s'est trompe la premiere fois on doit vider la liste precedente des disciplines
	                    disciplinesNouveauTitulaire.clear();

	                    for (String disc : disciplinesSelectionnees) {
	                        Discipline discipline = Discipline.getDiscipline(disc.trim().toLowerCase()); // Si la discipline passe entre parametres est invalide, cette focntion l'indique
	                        if (discipline != null) { 
	                        	disciplinesNouveauTitulaire.add(discipline);
	                        } else {
	                        	// Si jamais l'utilisateur s'est trompe sur une discipline dans la liste on lui autorise de la reselectionner
	                        	while(true) {
	                        		reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("reessayer", "abandonner"), "\nVoulez-vous réessayer de saisir cette discipline ?\n"
	                        		        + "Tapez 'reessayer' pour ressaisir\n"
	                        		        + "Tapez 'abandonner' pour garder uniquement celles qui sont bien sélectionnées.");
	                            	if (reponseUtilisateur.equals("reessayer")) {
	                            		System.out.println("\nVeuillez sélectionner la discipline corrigée :");
	                            		discipline = Discipline.getDiscipline(scanner.nextLine().trim().toLowerCase());
	                            		if (discipline != null) {
	                            			disciplinesNouveauTitulaire.add(discipline);
	                            			break;
	        	                        } else {
	        	                        	System.out.println("\nLa discipline sélectionnée n'est pas reconnue.");
	        	                        }
	                            	} else {
	                            		break;
	                            	}
	                        	}
	                        }
	                    }
	                    
	                    if (disciplinesNouveauTitulaire.size() >= 1 && disciplinesNouveauTitulaire.size() <= 2) {
	                    	reponseValide = true; 
	                    } else {
	                    	System.out.println("\nErreur : Vous devez choisir exactement 1 ou 2 disciplines parmi celles listées.");
	                    }
	                }

	                System.out.println("\nEntrez son numéro de bureau (un entier positif) :");
	                int numBureauNouveauTitulaire = Coherence.verificationEntier(scanner.nextLine().trim());
	                while (numBureauNouveauTitulaire < 0) {
	                	System.out.println("\nErreur : Le numéro de bureau doit être un entier positif.\n"
	                            + "Veuillez réessayer");
	                	numBureauNouveauTitulaire = Coherence.verificationEntier(scanner.nextLine().trim());
	                }
	                
	                reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("mcf", "chercheur", "annuler"), "\nSi vous voulez que ce titulaire soit MCF, tapez 'mcf', si Chercheur - 'chercheur' \n"
	                        + "Si vous voulez annuler l'ajout de cette personne, tapez 'annuler' :");
	                
	                if (reponseUtilisateur.toLowerCase().compareTo("mcf") == 0) {
                		MCF nouveauMCF = new MCF(
	                            nomNouvellePersonne,
	                            prenomNouvellePersonne,
	                            ageNouvellePersonne,
	                            villeNouvellePersonne,
	                            disciplinesNouveauTitulaire,
	                            numBureauNouveauTitulaire,
	                            null
	                    );
                		
                		// Si l'utilisateur veut il peut ou non ajouter un etudiant en these a ce MCF
                		reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("ajouter", "finir"), "\nSi vous voulez ajouter un étudiant en thèse pour ce MCF, tapez 'ajouter' \n"
                				+ "ou 'finir' pour sauvegarder l'ajout");
	    				if (reponseUtilisateur.equals("ajouter")) {
	    					nouveauMCF.ajouterEtudiantAEncadrant(scanner, baseDeDonneesCourrante);
	    				}

	                    System.out.println("Le MCF ajouté : ");
	                    System.out.println(nouveauMCF.toString());
	                    baseDeDonneesCourrante.add(nouveauMCF);
	                    
                	} else if (reponseUtilisateur.toLowerCase().compareTo("chercheur") == 0) {
                		Chercheur nouveauChercheur = new Chercheur(
	                            nomNouvellePersonne,
	                            prenomNouvellePersonne,
	                            ageNouvellePersonne,
	                            villeNouvellePersonne,
	                            disciplinesNouveauTitulaire,
	                            numBureauNouveauTitulaire,
	                            null
	                    );
                		
                		// Si l'utilisateur veut il peut ou non ajouter un ou plusiers etudiants en these a ce chercheur
                		reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("ajouter", "finir"), "\nSi vous voulez ajouter un ou plusieurs étudiants en thèse pour ce chercheur, tapez 'ajouter'\n"
                				+ "ou 'finir' pour sauvegarder l'ajout");
                		if (reponseUtilisateur.equals("ajouter")) {
                			nouveauChercheur.ajouterEtudiantAEncadrant(scanner, baseDeDonneesCourrante);
                		}
                		System.out.println("Le Chercheur ajouté : ");
	                    System.out.println(nouveauChercheur.toString());
	                    baseDeDonneesCourrante.add(nouveauChercheur);
                	} 
	                break;
	                
	            default:
	            	continuer = false;
	                break;
	        }
	        reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("continuer", "quitter"), "Est-ce que vous voulez continuer à ajouter des personnes ? "
	                + "\nTapez 'continuer' ou 'quitter' :");
	        
            if (reponseUtilisateur.compareTo("quitter") == 0) {
                	continuer = false;
            }
	    }
	}

	// Modifie autant de personnes que souhaite l'utilisateur de la base de donnees courante
	private static void changer(Scanner scanner, ArrayList<Personne> baseDeDonneesCourrante, ArrayList<Ville> baseDeVilles) {
	    boolean continuer = true;
	    String reponseUtilisateur;
	    
	    Personne persModifie = null;

	    // Pour pouvoir modifier autant de personnes que l'utilisateur veut
	    while (continuer) {
		    Trie.afficheParOrdre(baseDeDonneesCourrante);
		    
	   
            System.out.println("\nPour modifier une personne, tapez son ID : ");
            int id = Coherence.verificationEntier(scanner.nextLine().trim());
            if (id != -1) {
            	for (Personne p : baseDeDonneesCourrante) {
	                if (p.getID() == id) {
	                    persModifie = p;
	                    break;
	                }
	            }
            }
            if (persModifie == null) {
        		System.out.println("\nL'identifiant saisi n'est pas correct, veuillez réessayer.\n");
        		break;
        	}
	        

	        System.out.println("\nVoici la personne sélectionnée :\n" 
	                + persModifie.toString() 
	                + "\nVoici les caractéristiques modifiables :"
	                + "\n(Tapez les noms des caractéristiques à modifier, séparées par des virgules)");

	        System.out.println(" - Nom\n - Prénom\n - Âge\n - Ville");
	        if (persModifie instanceof Etudiant) {
	            System.out.println(" - Sujet de thèse\n - Discipline\n - Année de thèse\n - Encadrant\n");
	        } else {
	            System.out.println(" - Disciplines\n - Numéro de bureau");
	            if (persModifie instanceof MCF) {
	                System.out.println(" - Étudiant en thèse\n");
	            } else if (persModifie instanceof Chercheur){
	                System.out.println(" - Étudiants en thèse\n");
	            }
	        }

	        String[] caracteristiques = scanner.nextLine()
	            .replaceAll("\\s*,\\s*", ",").trim().split(",");

	        // On modifie chaque caracteristique par ordre d'entree
	        for (String caract : caracteristiques) {
	            String car = caract.replaceAll("\\s+", "").toLowerCase(); 
	            switch (car) {
	                case "nom":
	                    System.out.println("\nEntrez son nouveau nom :");
	                    persModifie.setNom(Coherence.verificationChaineChar(scanner));
	                    break;

	                case "prenom":
	                case "prénom":
	                    System.out.println("\nEntrez son nouveau prénom :");
	                    persModifie.setPrenom(Coherence.verificationChaineChar(scanner));
	                    break;

	                case "age":
	                case "âge":
	                	int ageNv = -1;
	        	        while (ageNv < 0) {
	        	        	System.out.println("\nEntrez son nouveau âge :");
	        	        	ageNv = Coherence.verificationEntier(scanner.nextLine().trim());
	        	        	if (ageNv < 0) {
	        	        		System.out.println("L'âge doit être un entier positif. Veuillez réessayer.");
	        	        	}
	        	        }
	                    persModifie.setAge(ageNv);
	                    break;

	                case "ville":
	                    persModifie.setVille(Coherence.verifierVille(scanner, baseDeVilles, false));
	                    break;

	                default:
	                    changerCaracteristiqueSpecifique(scanner, persModifie, car, baseDeDonneesCourrante);
	                    break;
	            }
	        }
	        
	        reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("continuer", "quitter"), "\nEst-ce que vous voulez continuer à modifier des personnes ?\nTapez 'continuer', ou 'quitter'\n");
            if (reponseUtilisateur.compareTo("quitter") == 0) {
                	continuer = false;
            }
	    }
	}
	
	// Fonction auxiliaire pour la precedente 
	// Permet de gerer la modification des caracteristiques specifiques selon la fonction (Etudiant, MCF, Chercheur) 
	// du personne que l'utilisateur est en train de modifier
	private static void changerCaracteristiqueSpecifique(Scanner scanner, Personne persModifie, String car, ArrayList<Personne> baseDeDonneesCourrante) {
	    boolean reponseValide = false;
	    String reponseUtilisateur;
	    
		if (persModifie instanceof Etudiant etudiantCourant) {
	    	if (car.equalsIgnoreCase("sujetdethèse") || car.equalsIgnoreCase("sujetdethese")) {
                System.out.println("\nEntrez le nouveau sujet de thèse :");
                etudiantCourant.setSujetDeThese(scanner.nextLine().trim());
                
            } else if (car.equalsIgnoreCase("discipline")) {
            	System.out.println("\nVeuillez choisir une nouvelle discipline parmi celles-ci :");
            	Discipline.afficherDisciplines();
            	
	            Discipline nvDiscipline = null;
	            while (nvDiscipline == null) {
	            	System.out.println("\nLa discipline de l'étudiant :");
	                String discip = scanner.nextLine().trim();
	                nvDiscipline = Discipline.getDiscipline(discip);
	            }
	            // Il faut verifier que c'est possible de changer sa discipline
	            // Si c'est l'une des disciplines que l'encadrant de l'etudiant a, alors on peut directement changer la discipline de l'etudiant
	    		if (etudiantCourant.getEncadrant().getDisciplines().contains(nvDiscipline)) {
	    			etudiantCourant.setDiscipline(nvDiscipline);
	    		} else { 
	    			// Si non il faut trouver un nouveau encadrant si il existe tel qui peut prendre cet etudiant en these
	    			// Sinon il faut interdire le changement de discipline
	    			Discipline ancienneDiscipline = etudiantCourant.getDiscipline();
	    			etudiantCourant.setDiscipline(nvDiscipline);
	    			
	    			ArrayList<Titulaire> titPossibles = etudiantCourant.listeTitulairesPossibles(baseDeDonneesCourrante, false);
	    			
	    			if (titPossibles.isEmpty()) { // si il n'existe pas de titulaire qui peut le prendre en these avec nouvelle discipline il faut interdire de changer la discipline
	    				etudiantCourant.setDiscipline(ancienneDiscipline); // et on remet la meme discipline qu'il avait avant
	    			} else {
	    				etudiantCourant.setEncadrant(titPossibles.get(0)); // si on a reussi on met le premier qu'on peut
	    			}
	    		}
	    		
	            
            } else if (car.equalsIgnoreCase("annéedethèse") || car.equalsIgnoreCase("anneedethese")) {
    			System.out.println("\nEntrez sa nouvelle année de thèse :");
                int nvAnneeThese =  Coherence.verificationEntier(scanner.nextLine().trim());
                while (nvAnneeThese < 0) {
                	System.out.println("Année de thèse invalide. Cela doit être un entier compris entre 1 et 3.\nVeuillez réessayer.");
                	nvAnneeThese =  Coherence.verificationEntier(scanner.nextLine().trim());
                }
                etudiantCourant.setAnneeDeThese(nvAnneeThese);
    			
    		} else if (car.equalsIgnoreCase("encadrant")) {
    			
    			ArrayList<Titulaire> titPossibles = etudiantCourant.listeTitulairesPossibles(baseDeDonneesCourrante, false);
    			
    			if (titPossibles.isEmpty()) { // si il n'existe pas de titulaire (autre que le courrant) qui peut le prendre en these il faut interdire de changer le titulaire
    				System.out.println("Impossible de changer le titulaire, puisqu'il n'existe pas d'autre encadrant qui pourrait prendre cet étudiant en thèse.");
    			} else {
    				System.out.println("Voici la liste des titulaires possibles :");
    				Set<Integer> idTitulairesPossibles = new HashSet<>();
    				for (Titulaire t: titPossibles) {
            			System.out.println(t.toString());
            			idTitulairesPossibles.add(t.getID());
            		}
    				
    				int idChoisi = Coherence.verificationId(scanner, idTitulairesPossibles, "Veuillez entrer l'ID du titulaire choisi :");
    				
					for (Titulaire t: titPossibles) {
            			if (t.getID() == idChoisi) {
            				etudiantCourant.setEncadrant(t);
            				break;
            			}
            		}
    			}
    		} else {
    			System.out.println("Erreur : Caractéristique '" + car + "' n'a pas été reconnue.");
    		}
	    } else if (persModifie instanceof Titulaire titulaireCourant) {
	    	
	    	if (car.equalsIgnoreCase("disciplines")) {
    			System.out.println("\nVeuillez choisir ses nouvelles disciplines (il faut en choisir 1 ou 2) parmi ci-dessous (séparées par des virgules) :");
    			Discipline.afficherDisciplines();
                
                Set<Discipline> nvDisciplines = new HashSet<>();
                reponseValide = false;

                while (!reponseValide) {
                	System.out.println("\nLes disciplines du titulaire :");
                    String[] parties = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");

                    nvDisciplines.clear();

                    for (String disc : parties) {
                        Discipline discipline = Discipline.getDiscipline(disc.trim().toLowerCase());
                        if (discipline != null) {
                            nvDisciplines.add(discipline);
                        } else {
                        	while(true) {
                        		reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("reessayer", "abandonner"), "\nVoulez-vous réessayer de saisir cette discipline ?\n"
                            			+ "Tapez 'reessayer' pour ressaisir\nTapez 'abandonner' pour garder uniquement celles qui sont bien sélectionnées.");
                            	if (reponseUtilisateur.equals("reessayer")) {
                            		System.out.println("\nSélectionnez la discipline corrigée :");
                            		discipline = Discipline.getDiscipline(scanner.nextLine().trim().toLowerCase());
                            		if (discipline != null) {
                            			nvDisciplines.add(discipline);
                            			break;
        	                        } else {
        	                        	System.out.println("\nLa discipline sélectionnée n'est pas reconnue.");
        	                        }
                            	} else {
                            		break;
                            	}
                        	}
                        }
                    }
                    
                    if (nvDisciplines.size() >= 1 && nvDisciplines.size() <= 2) {
                    	reponseValide = true; 
                    } else {
                    	System.out.println("\nErreur : Vous devez choisir exactement 1 ou 2 disciplines parmi celles listées.");
                    }
                }
                
                // Maintenant il faut s'assurer que les etudiants en these sont compatibles avec les disciplines
                if (titulaireCourant instanceof MCF mcf) {
                	Etudiant etudiantCourant = mcf.getEtudiant();
                	// Si ce mcf n'a pas d'etudiant tout court 
                	// ou si la discipline de son etudiant est toujours dans l'ensemble des nouvelles disciplines 
                	// Alors on a le droit de changer les disciplines de ce mcf directement
                	if (etudiantCourant == null || nvDisciplines.contains(etudiantCourant.getDiscipline())) {
                		titulaireCourant.setDisciplinesSet(nvDisciplines);
                	} else { 
                		
                		ArrayList<Titulaire> titPossibles = etudiantCourant.listeTitulairesPossibles(baseDeDonneesCourrante, false);  
                		if (titPossibles.isEmpty()) {
                			System.out.println("Impossible de changer les disciplines, puisque ce MCF contient un étudiant \n"
                					+ "(incompatible avec les nouvelles disciplines) qui ne peut pas être affecté à un autre encadrant.");
                		} else { // Si on reussi a trouver au moins un titulaire lible pour l'ancien etudiant on peut effectuer le changement
                			etudiantCourant.setEncadrant(titPossibles.get(0));
                			titulaireCourant.setDisciplinesSet(nvDisciplines);
                		}
                		
                	}
                } else if ((titulaireCourant instanceof Chercheur chercheur)){ 
                	// De meme si il n'a pas d'etudiants alors on peut directement changer les disciplines
                	if (chercheur.getEtudiantsSet() == null || chercheur.getEtudiantsSet().isEmpty()) {
                		chercheur.setDisciplinesSet(nvDisciplines);
                		
                	} else if (!nvDisciplines.equals(titulaireCourant.getDisciplines())){ // le cas ou notre chercheur a des etudiants en these
                        // On verifie si il y a une discipline en commun avec les anciennes ou pas pour savoir si on doit attribuer tous
                        // les etudiants en these aux autres titulaires compatibles ou non 
                        Set<Discipline> intersectionDisciplines = new HashSet<>(chercheur.getDisciplines());
                        intersectionDisciplines.retainAll(nvDisciplines);
                        
                        Set<Etudiant> etudiantsAAttribuerAuxAutresTit = new HashSet<>(chercheur.getEtudiantsSet());
                        
                        if (!intersectionDisciplines.isEmpty()){
                            Set<Etudiant> etudiantsCompatiblesAvecNVDisc = new HashSet<>();
                            for (Etudiant etud: etudiantsAAttribuerAuxAutresTit){
                                if (intersectionDisciplines.contains(etud.getDiscipline())){
                                    etudiantsCompatiblesAvecNVDisc.add(etud);
                                }
                            }
                            etudiantsAAttribuerAuxAutresTit.removeAll(etudiantsCompatiblesAvecNVDisc);
                        }
                        
                        if (!etudiantsAAttribuerAuxAutresTit.isEmpty()) {
                        	// Les listes qui seront remplit dans la fonction essaieSupprimerTousEtudiants, pour stocker et recuperer tous les affectations des etudiants possibles
                            ArrayList<Chercheur> chercheursTrouvePourEtudiants = new ArrayList<>(); 
                            ArrayList<Set<Etudiant>> etudiantsParChercheur = new ArrayList<>();
                            HashMap<Etudiant, MCF> couplage = new HashMap<>();

                            // Soit notre fonction a reussi d'attribuer tous les etudiants incompatibles aux autres titulaires et on peut changer les disciplines
                            if (chercheur.essaieSupprimerTousEtudiants(etudiantsAAttribuerAuxAutresTit, baseDeDonneesCourrante, chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage)){ // On a reussi a attribue tous les etudiants aux autres encadrants
                            	chercheur.setDisciplinesSet(nvDisciplines);

                            } else { // Soit on donne le choix a l'utilisateur si il veut ou pas de supprimer le max des etudiants incmpatibles ou d'annuler tout
                            	System.out.println("Il est impossible de modifier les disciplines de ce chercheur puisqu'il a des étudiants en thèse pour lesquels,\n"
                                        + "dans la base de données, il n'existe pas d'encadrant à qui ils peuvent être attribués.\n");

                                // si on a trouve au moins un etudiant incompatible qui peut etre attribue a un autre titulare, alors on propose a l'utilisateur de supprimer le max
                                if (!chercheursTrouvePourEtudiants.isEmpty() || !couplage.keySet().isEmpty()) { 
                                	reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("supprimer le maximum", "annuler"), 
                                            "\nSi vous voulez supprimer le maximum des étudiants incompatibles avec les disciplines sélectionnées, tapez 'supprimer le maximum'\n"
                                            + "Si vous voulez ne pas effectuer de changements, tapez 'annuler'.\n");

                                    if (reponseUtilisateur.equals("Supprimer le maximum")) {
                                        chercheur.supprimerMaxDesEtudiants(chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage);
                                    } // Par defaut on annule l'action
                                }
                            }
                        } else { // Si tous les etudiants sont compatibles on peux changer les disciplines
                        	chercheur.setDisciplinesSet(nvDisciplines);
                        }
                    }
                }
                
        	} else if (car.equalsIgnoreCase("numérodebureau") || car.equalsIgnoreCase("numerodebureau")) {
        		System.out.println("\nEntrez son nouveau numéro de bureau (un entier positif) :");
                int nvNumBureau = Coherence.verificationEntier(scanner.nextLine().trim());
                while (nvNumBureau < 0) {
                	System.out.println("\nErreur : Le numéro de bureau doit être un entier positif.\n"
                            + "Veuillez réessayer.");
                	nvNumBureau = Coherence.verificationEntier(scanner.nextLine().trim());
                }
                titulaireCourant.setNumBureau(nvNumBureau);
                
        	} else if (titulaireCourant instanceof MCF mcf && (car.equalsIgnoreCase("étudiantenthèse") || car.equalsIgnoreCase("etudiantenthese"))) {
        		ArrayList<Etudiant> etudPossibles = mcf.listeEtudiantsPossibles(baseDeDonneesCourrante, false);
        		
        		if (mcf.getEtudiant() != null) {
        			Etudiant etudCourant = mcf.getEtudiant();
        			System.out.println("\nL'étudiant courant de ce MCF est \n" + etudCourant.toString());
        			
        			ArrayList<Titulaire> titPossiblesPourEtudiantCourant = etudCourant.listeTitulairesPossibles(baseDeDonneesCourrante, false);
        			
        			
        			if (titPossiblesPourEtudiantCourant.isEmpty()) {
        				System.out.println("Aucune action n'est possible puisque l'étudiant de ce MCF ne peut pas être supprimé.");
        			} else { // Si on peut le supprimmer alors on peut soit de le supprimer soit remplacer par un nouveau si c'est possible
        				if (etudPossibles.isEmpty()) { // On ne peut pas ajouter un nouveau etudiant mais on peut supprimer l'ancien
        					reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("supprimer", "annuler"), 
        				            "\nSi vous voulez supprimer l'étudiant courant en thèse, tapez 'supprimer'\n"
        				            + "Si vous voulez arrêter de modifier cette caractéristique, tapez 'annuler'");
            			} else { 
            				reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("supprimer", "remplacer", "annuler"), 
            			            "\nSi vous voulez supprimer l'étudiant courant en thèse, tapez 'supprimer'\n"
            			            + "Si vous voulez remplacer l'étudiant courant par un autre étudiant, tapez 'remplacer'\n"
            			            + "Si vous voulez arrêter de modifier cette caractéristique, tapez 'annuler'");
            			}
        				
        				if (!reponseUtilisateur.equals("annuler")) {
        					etudCourant.setEncadrant(titPossiblesPourEtudiantCourant.get(0));
        					if (reponseUtilisateur.equals("remplacer")) { // A ce stade on a forcement la possibilite d'ajouter un nouveau etudiant car si non la reponse de l'utilisateur n'a pas ete accepte
        						System.out.println("Voici les étudiants que ce MCF peut prendre en thèse.\nPour ajouter un étudiant, tapez son ID :");
        						
        						Set<Integer> idPossibles = new HashSet<>();
        						for(Etudiant e: etudPossibles) {
        							System.out.println(e.toString());
        							idPossibles.add(e.getID());
        						}
        						
        						int idChoisi = Coherence.verificationId(scanner, idPossibles, "Veuillez entrer l'ID de l'étudiant choisi");
        						for(Etudiant e: etudPossibles) {
        							if (e.getID() == idChoisi) {
        								e.setEncadrant(mcf);
        								break;
        							}
        						}
        						
        					}
        				}
        			}	
        		} else { // MCF courrant n'a pas d'etudiant en these
        			if (etudPossibles.isEmpty()) {
        				System.out.println("Aucune action n'est possible puisque aucun étudiant ne peut être ajouté à ce MCF.");
        			} else {
        				reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("ajouter", "annuler"), 
        			            "\nSi vous voulez ajouter un étudiant en thèse, tapez 'ajouter'\n"
        			            + "Si vous voulez arrêter de modifier cette caractéristique, tapez 'annuler'");
        				if (reponseUtilisateur.equalsIgnoreCase("ajouter")) {
        					System.out.println("Voici les étudiants que ce MCF peut prendre en thèse.\nPour ajouter un étudiant, tapez son ID :");
    						
    						Set<Integer> idPossibles = new HashSet<>();
    						for(Etudiant e: etudPossibles) {
    							System.out.println(e.toString());
    							idPossibles.add(e.getID());
    						}
    						
    						int idChoisi = Coherence.verificationId(scanner, idPossibles, "Veuillez entrer l'ID de l'étudiant choisi");
    						for(Etudiant e: etudPossibles) {
    							if (e.getID() == idChoisi) {
    								e.setEncadrant(mcf);
    								break;
    							}
    						}
        				}
        			}
        		}
        	} else if (titulaireCourant instanceof Chercheur chercheur && (car.equalsIgnoreCase("étudiantsenthèse") || car.equalsIgnoreCase("etudiantsenthese"))) { // Ici on propose soit de supprimer ou ajouter des etudinat en these (quand c'est possible)
        		Set<Integer> idEtudiantsCourrants = new HashSet<>();
        		if (chercheur.getEtudiantsSet() != null && !chercheur.getEtudiantsSet().isEmpty()) {
        			System.out.println("\nVoici la liste des étudiants de ce titulaire :");
            		for (Etudiant e: chercheur.getEtudiantsSet()) {
            			System.out.println(e.toString());
            			idEtudiantsCourrants.add(e.getID());
            		}
        		}
        		
    			ArrayList<Etudiant> etudPossibles = chercheur.listeEtudiantsPossibles(baseDeDonneesCourrante, false);
    			
    			reponseUtilisateur = "";
    			
    			if (idEtudiantsCourrants.isEmpty() && etudPossibles.isEmpty()) {
    			    System.out.println("\nAucune action n'est possible sur la liste des étudiants de ce chercheur.");

    			} else if (idEtudiantsCourrants.isEmpty() && !etudPossibles.isEmpty()) { // On ne peut pas supprimer, mais on peut ajouter
    			    reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("ajouter", "annuler"),
    			            "\nSi vous voulez ajouter un ou plusieurs étudiants en thèse, tapez 'ajouter'\n"
    			            + "Si vous voulez arrêter de modifier cette caractéristique, tapez 'annuler'");

    			} else if (!idEtudiantsCourrants.isEmpty() && etudPossibles.isEmpty()) { // On peut supprimer, mais pas ajouter
    			    reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("supprimer", "annuler"),
    			            "\nSi vous voulez supprimer un ou plusieurs étudiants, tapez 'supprimer'\n"
    			            + "Si vous voulez arrêter de modifier cette caractéristique, tapez 'annuler'");

    			} else { // On peut supprimer et ajouter
    			    reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("ajouter", "supprimer", "annuler"),
    			            "\nSi vous voulez supprimer un ou plusieurs étudiants en thèse, tapez 'supprimer'\n"
    			            + "Si vous voulez ajouter un ou plusieurs étudiants, tapez 'ajouter'\n"
    			            + "Si vous voulez arrêter de modifier cette caractéristique, tapez 'annuler'");
    			}

    		
    			
    			if (reponseUtilisateur.equals("supprimer")) {
    				System.out.println("\nTapez un ou plusieurs ID des étudiants en thèse que vous voulez supprimer, séparés par des virgules :");
					String[] ids = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
        			Set<Etudiant> EtudiantsASupprimer = new HashSet<>();
        			for(String idCourrant: ids) {
        				int idEtudiant = Coherence.verificationEntier(idCourrant);
        				if (idEtudiant > 0 && idEtudiantsCourrants.contains(idEtudiant)) {
        					for (Etudiant e: chercheur.getEtudiantsSet()) {
        						if (e.getID() == idEtudiant) {
        							if (!EtudiantsASupprimer.contains(e)) {
        								EtudiantsASupprimer.add(e);
        							}
        						}
        					}
        				} else {
        					
        					System.out.println("L'ID = " + idCourrant + " n'est pas reconnu.");
        					
        					while(true) {
        						reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("reessayer", "abandonner"), 
        						        "\nVoulez-vous réessayer de saisir cet ID ?\n"
        						        + "Tapez 'reessayer' pour ressaisir\n"
        						        + "Tapez 'abandonner' pour garder ceux qui sont bien sélectionnés.");
                            	if (reponseUtilisateur.equals("reessayer")) {
                            		System.out.println("\nSélectionnez l'ID corrigé :");
                            		idEtudiant = Coherence.verificationEntier(scanner.nextLine().trim()); 
                            		if (idEtudiantsCourrants.contains(idEtudiant)) {
                            			for (Etudiant e: chercheur.getEtudiantsSet()) {
                    						if (e.getID() == idEtudiant) {
                    							if (!EtudiantsASupprimer.contains(e)) {
                    								EtudiantsASupprimer.add(e);
                    							}
                    							break;
                    						}
                    					}
        	                        } else {
        	                        	System.out.println("\nL'ID selectionné n'est pas reconnu.");
        	                        }
                            	} else {
                            		break;
                            	}
        					}
        				}
        			}
        			
        			// Code pour suppression des etudiants
        			ArrayList<Chercheur> chercheursTrouvePourEtudiants = new ArrayList<>(); 
                    ArrayList<Set<Etudiant>> etudiantsParChercheur = new ArrayList<>();
                    HashMap<Etudiant, MCF> couplage = new HashMap<>();

                    // Soit notre fonction a reussi d'attribuer tous les etudiants aux autres titlaires que l'utilisateur a voulu supprimer de ce checrcheur
                    if (!chercheur.essaieSupprimerTousEtudiants(EtudiantsASupprimer, baseDeDonneesCourrante, chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage)){ // On n'a pas reussi a attribue tous les etudiants aux autres encadrants
                    	System.out.println("\nIl est impossible de supprimer certains étudiants de ce chercheur, car il n'existe aucun encadrant \n"
                    			+ "dans la base de données qui puisse les prendre en charge.\n");
                        
                        
                        Set<Etudiant> etudiantsImpossibleASupprimer = new HashSet<>(chercheur.getEtudiantsSet());
                        
                        // On enleve tous les etudiants qu'on a pu attribuer aux autres titulaires pour pouvoir les affichier a l'utilisateur
                        for (Set<Etudiant> ens: etudiantsParChercheur) {
                        	etudiantsImpossibleASupprimer.retainAll(ens);
                        }
                        etudiantsImpossibleASupprimer.retainAll(couplage.keySet());
                        System.out.println("\nVoici la liste des étudiants qui ne peuvent pas être supprimés :");
                        for (Etudiant e: etudiantsImpossibleASupprimer) {
                        	System.out.println(e.toString());
                        }
                        
                        // Dans tous les cas on essaie de supprimmer le maximum des etudiants que l'utilidateur a selectionne
                        chercheur.supprimerMaxDesEtudiants(chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage); 
                    }	
				} 
    			
    			if (reponseUtilisateur.equals("ajouter")) {
    				System.out.println("Voici la liste des étudiants qu'il est possible d'attribuer à ce chercheur :");
					
					Set<Integer> idPossibles = new HashSet<>();
					for (Etudiant e: etudPossibles) {
						System.out.println(e.toString());
						idPossibles.add(e.getID());
					}
					
					System.out.println("\nVeuillez entrer un ou plusieurs ID d'étudiants à ajouter à ce chercheur, séparés par des virgules :");
            		String[] ids = scanner.nextLine().replaceAll("\\s*,\\s*", ",").trim().split(",");
            		Set<Etudiant> EtudiantsAAjouter = new HashSet<>();
            		
            		for(String idCourrant: ids) {
            			int idEtudiant = Coherence.verificationEntier(idCourrant);
            			if (idEtudiant >  0 && idPossibles.contains(idEtudiant)) {
            				for (Etudiant e: etudPossibles) {
            					if (e.getID() == idEtudiant) {
            						if (!EtudiantsAAjouter.contains(e)) {
            							EtudiantsAAjouter.add(e);
            						}
            					}
            				}
            			} else {
            				System.out.println("L'ID = " + idCourrant + " n'est pas reconnu.");
            				
            				while (true) {
            					reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("reessayer", "abandonner"), 
            					        "\nVoulez-vous reessayer de saisir cet ID ?\n"
            					        + "Tapez 'reessayer' pour resaisir\n"
            					        + "Tapez 'abandonner' pour garder que celles qui sont bien sélectionnées.");
	                        	if (reponseUtilisateur.equals("reessayer")) {
	                        		System.out.println("\nSélectionnez l'ID corrigé :");
	                        		idEtudiant = Coherence.verificationEntier(scanner.nextLine().trim()); 
	                        		if (idEtudiant > 0 && idEtudiantsCourrants.contains(idEtudiant)) {
	                        			for (Etudiant e: etudPossibles) {
	                						if (e.getID() == idEtudiant) {
	                							if (!EtudiantsAAjouter.contains(e)) {
	                								EtudiantsAAjouter.add(e);
	                							}
	                							break;
	                						}
	                					}
	    	                        } else {
	    	                        	System.out.println("\nL'ID sélectionné n'est pas reconnu.");
	    	                        }
	                        	} else { // l'utilisateur a selectionnee d'abandonner
	                        		break;
	                        	}
            				}
            			}
            		}
        			for (Etudiant e: EtudiantsAAjouter) {
        				e.setEncadrant(chercheur);
        			}
				}
        	} else {
        		System.out.println("\nErreur : Caractéristique '" + car + "' n'a pas été reconnue.");
        	}
	    }
	}
	
	// Supprimme autant de personnes que souhaite l'utilisateur de la base de donnees courante
	private static void supprimer(Scanner scanner, ArrayList<Personne> baseDeDonneesCourrante) {
		boolean continuer = true;
		String reponseUtilisateur;

		Personne persSupprime = null;
		
        // Pour supprimer autant de personnes d'affile que l'utilisateur veut
        while (continuer) {
        	Trie.afficheParOrdre(baseDeDonneesCourrante);
          
        	// Pour les titulaires il faut verifier si il est possible de mettre les autres encadrants pour leurs etudiants
        	boolean suppressionImpossible = false;
        	
            
        	System.out.println("Pour supprimer une personne, tapez son ID : "); 
        	int id = Coherence.verificationEntier(scanner.nextLine().trim());
        	if (id > 0) {
        		for(Personne p: baseDeDonneesCourrante) {
                	if (p.getID() == id) {
                		persSupprime = p;
                		break;
                	}
                }
        	}
        	if (persSupprime == null) {
        		System.out.println("\nL'identifiant saisi n'est pas correct, veuillez réessayer.\n");
        		break;
        	}
            
            
            if (persSupprime instanceof Etudiant) {
            	Etudiant e = (Etudiant) persSupprime;
            	Titulaire tit = e.getEncadrant();
            	tit.popEtudiant(e);
            	
            } else if (persSupprime instanceof MCF mcf) {
            	if (mcf.getEtudiant() != null){
                    Etudiant etudSansEnc = mcf.getEtudiant();
                    Titulaire nvTitulaire = null;
                    for (Personne pers: baseDeDonneesCourrante){
                        if (pers instanceof Titulaire tit && tit.peutPrendreEtudiant(etudSansEnc)){
                            if ((tit instanceof MCF mcf2 && !mcf.equals(mcf2)) || (tit instanceof Chercheur)) {
                                nvTitulaire = tit;
                            break; // Des que nous avons trouve un titulaire qui peut prendre etudiant on peut s'arreter
                            }
                        }
                    }
                    if (nvTitulaire != null) { // On a trouve un titulaire donc c'est bon
                        etudSansEnc.setEncadrant(nvTitulaire);
                    } else {
                    	System.out.println("Impossible de supprimer ce MCF, puisqu'il n'existe pas de titulaire qui pourra prendre en thèse son étudiant.");
                        suppressionImpossible = true;
                    }
                } // Si mcf n'a pas d'etudiant on peut le supprimer tkl
                  
            } else if (persSupprime instanceof Chercheur chercheur) {
            	if (chercheur.getEtudiantsSet() != null && !chercheur.getEtudiantsSet().isEmpty()){
                    ArrayList<Chercheur> chercheursTrouvePourEtudiants = new ArrayList<>(); // Car on stocke dans l'ordre pour apres plus simplement d'attribuer les etudiants aux chercheurs
                    ArrayList<Set<Etudiant>> etudiantsParChercheur = new ArrayList<>();
                    HashMap<Etudiant, MCF> couplage = new HashMap<>();

                    // Soit notre fonciton a reussi de supprimer la personne et on peut tkl le supprimer de la base de donnes
                    // Soit on donne le choix a l'utilisateur si il veut ou pas de supprimer le max ou d'annuler tout
                    if (!chercheur.essaieSupprimerTousEtudiants(chercheur.getEtudiantsSet(), baseDeDonneesCourrante, chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage)){ // On a reussi a attribue tous les etudiants aux autres encadrants
                    	System.out.println("Il est impossible de supprimer ce chercheur puisqu'il a des étudiants en thèse pour lesquels,\n"
                    	        + "dans la base de données, il n'existe pas d'encadrant à qui ils peuvent être attribués.\n\n"
                    	        + "Est-ce que vous voulez attribuer le maximum des étudiants possibles aux autres titulaires ou laisser ce chercheur inchangé ?");
                    	
                    	reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("supprimer le maximum", "annuler"),
                    	        "\nSi vous voulez supprimer le maximum des étudiants en thèse, tapez 'supprimer le maximum'\n"
                    	        + "Si vous voulez laisser ce chercheur inchangé, tapez 'annuler'");
                        
                        if (reponseUtilisateur.equals("supprimer le maximum")) {
                            chercheur.supprimerMaxDesEtudiants(chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage);
                            suppressionImpossible = true;
                        } 
                    }
                    
                } 
            }
            if (!suppressionImpossible) {
            	baseDeDonneesCourrante.remove(persSupprime);
            }
            
            reponseUtilisateur = Coherence.verificationOption(scanner, Set.of("continuer", "quitter"), "Voulez-vous continuer à supprimer ?\n"
            	    + "Tapez 'continuer', ou 'quitter'\n");
            
            if (reponseUtilisateur.compareTo("quitter") == 0) {
            	continuer = false;
            }
            
        }
	}

	/**
	 * Permet a l'utilisateur de modifier la base de donnees comme il le souhaite tout en preservant la coherence de la base de donnees.
	 *
	 * @param scanner Scanner utilise pour lire l'entree de l'utilisateur.
	 * @param baseDeDonneesCourrante Base de donnees des personnes (modifiable).
	 * @param baseDeVilles Base de donnees des villes (non modifiable).
	 */
	public static void modifier(Scanner scanner, ArrayList<Personne> baseDeDonneesCourrante, ArrayList<Ville> baseDeVilles) {
	    boolean continuer = true;
	    
	    while (continuer) {
	    	System.out.println("\nPour ajouter une personne, tapez 'ajouter'\n" 
	    		    + "Pour supprimer une personne, tapez 'supprimer'\n" 
	    		    + "Pour modifier une personne, tapez 'modifier'\n" 
	    		    + "Pour quitter, tapez 'quitter'");
	
	        String choix = scanner.nextLine().toLowerCase();
	
	        switch (choix) {
	            case "ajouter":
	            	ajouter(scanner, baseDeDonneesCourrante, baseDeVilles);
	                break;
	
	            case "supprimer":
	            	supprimer(scanner, baseDeDonneesCourrante);
	                break;
	
	            case "modifier":
	            	changer(scanner, baseDeDonneesCourrante, baseDeVilles);
	                break;
	
	            case "quitter":
	                continuer = false; 
	                break;
	
	            default:
	            	System.out.println("Option non reconnue. Veuillez réessayer.");
	                break;
	        }
	        actualiserBaseDeDonnees(baseDeDonneesCourrante);
	    }
	}
}
