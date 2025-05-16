package main;

import genetique.*;
import peuple.*;
import representationSolution.FichierHTMLGenerateur;
import representationSolution.FichierTexteGenerateur;
import territoire.*;
import verificationEntree.Coherence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import affichage.*;
import baseDeDonnees.*;
import filtrage.*;

/**
 * Cette classe contient uniquement la methode main qui rassemble toutes les fonctionnalites apportees dans tout le projet 
 * et les rend disponibles pour l'utilisateur.
 */
public class Main {
	
	// Ce constructeur prive par defaut est defini pour eviter le warning lors de la generation du Javadoc, 
	// concernant l'absence de commentaire sur un constructeur par defaut.
	private Main() {
		
	}
	
//	public static final String url = "jdbc:postgresql://localhost:5433/Localisations";
//	public static final String user = "katya";
//	public static final String password = "katya";
	
	private static String url;
	private static String user;
	private static String mdp;
	
	/**
	 * Recupere l'URL de la base de donnees.
	 *
	 * @return l'URL de la base de donnees sous forme de chaine
	 */
	public static String getURL() {
		return url;
	}
	
	/**
	 * Recupere le nom d'utilisateur pour la connexion a la base de donnees.
	 *
	 * @return le nom d'utilisateur sous forme de chaine
	 */
	public static String getUser() {
		return user;
	}
	
	/**
	 * Recupere le mot de passe pour la connexion a la base de donnees.
	 *
	 * @return le mot de passe sous forme de chaine
	 */
	public static String getMDP() {
		return mdp;
	}
	
    /**
     * Methode principale pour lancer le programme.
     *
     * @param args Parametres de la ligne de commande (Mais ils ne sont pas utilises). 
     */
	public static void main(String[] args) {
		String reponseUtilisateur;
		Scanner scan = new Scanner(System.in);
		
		while (true) {
            System.out.println("\nVeuillez entrer l'URL de votre base de donnees : ");
            url = scan.nextLine().trim();
            
            System.out.println("\nVeuillez entrer le nom de l'utilisateur de votre base de donnees : ");
            user = scan.nextLine().trim();
            
            System.out.println("\nVeuillez entrer le mot de passe de votre base de donnees : ");
            mdp = scan.nextLine().trim();
            
            try (Connection connection = DriverManager.getConnection(url, user, mdp)) {
                break;
            } catch (SQLException e) {
                System.err.println("\nEchec de la connexion a la base de donnees.\nVeuillez reessayer.");
            }
        }
		
		ArrayList<Personne> personnes = ChargementDonnees.chargerPersonnes();
		ArrayList<Ville> villes = ChargementDonnees.chargerVilles();

		if (villes == null || villes.size() == 0) {
	            System.err.println("\nErreur : les villes n'ont pas pu être récupérées.");
	            scan.close();
	            return;
	    }
		
		ArrayList<Region> regions = Region.transformerEnListe();
		
        reponseUtilisateur = Coherence.verificationOption(scan, Set.of("terminal", "ui"), "\nVoulez-vous utiliser la version Terminal de l'application ou avoir une interface graphique?\nTapez 'terminal' ou 'ui' (pour interface graphique) :");
		
		if (reponseUtilisateur.equals("ui")) {
			scan.close(); // On ferme le scan car on ne va plus l'utiliser
			Frame frame = new Frame();
	        frame.add(new PanelMenu(frame, personnes, personnes, villes, regions));
	        frame.setVisible(true);
			return;
		}
		
		boolean continuer = true;
		
		String messageMenu = "\nSi vous voulez observer la base de donnees de personnes, tapez 'observer'\n"
                + "Si vous voulez modifier la base de donnees de personnes, tapez 'modifier'\n"
                + "Si vous voulez effectuer un filtrage de la population que vous voulez visiter, tapez 'filtrer'\n"
                + "Si vous voulez arreter le programme, tapez 'arreter'\n";
        Set<String> optionsMenu = Set.of("observer", "modifier", "filtrer", "arreter");
        
        while (continuer) {
            
            reponseUtilisateur = Coherence.verificationOption(scan, optionsMenu, messageMenu);
            
            switch (reponseUtilisateur){
                case "observer":
                    Trie.afficheParOrdre(personnes);
                    break;
                    
                case "modifier":  
                    ModificationPersonnesTerminal.modifier(scan, personnes, villes);
                    break;
                    
                case "filtrer":  
                    System.out.println("\nNous allons maintenant décider les villes que vous allez visiter en fonction des personnes que vous voulez voir."); 
                    ArrayList<Personne> selection = TrieTerminal.Trier(personnes, villes, regions);
                    
                    System.out.println("\nChoisissez votre ville de depart : ");
                    Ville villeDep = Coherence.verifierVille(scan, villes, true);
                    
                    if (!villes.contains(villeDep)) {
                        villes.add(villeDep);
                    }
                    
                    ArrayList<Ville> villesAvisiter = new ArrayList<>();
                    for(Personne pers : selection){
                        Ville v = pers.getVille();
                        if(!villesAvisiter.contains(v)){
                            villesAvisiter.add(v);
                        }
                    }
                    if(!villesAvisiter.contains(villeDep)){
                        villesAvisiter.add(villeDep);
                    }

                    if (villesAvisiter.size() > 2) {
                        int taillePopulation = villesAvisiter.size()*5;
                        double probaDeMutation = 0.5;
                        double ratioElite = 0.5;
                        double epsilon = 0.001;
                        Chemin meilleurCheminGenere = Chemin.algorithmeGenetique(villesAvisiter, taillePopulation, probaDeMutation, ratioElite, epsilon);
                        ArrayList<Ville> meilleurChemin = meilleurCheminGenere.gene;
                        
                        villesAvisiter.clear();
                        
                        int indexDepart = meilleurChemin.indexOf(villeDep);
                        for (int i = 0; i < meilleurChemin.size(); i++) {
                            // Calcul de l'index circulaire
                            int indexActuel = (indexDepart + i) % meilleurChemin.size();
                            villesAvisiter.add(meilleurChemin.get(indexActuel));
                        }
                        villesAvisiter.add(villeDep); // Ajoute VilleDepart a la fin
                        
                    } else if (villesAvisiter.size() == 2){ // Si on a que deux villes dont une celle de depart, on doit juste l'ajouter au debut pour creer un cycle
                        villesAvisiter.add(0, villeDep);
                        
                    } 
                    
                    reponseUtilisateur = Coherence.verificationOption(scan, Set.of("fichier", "terminal"), "Tapez 'fichier' si vous voulez que le chemin soit écrit dans un fichier,"
                            + "\net 'terminal' si vous preferez qu'il soit affiche dans le terminal : ");
                    
                    if (reponseUtilisateur.equals("fichier")) {
                        System.out.println("Entrez le nom du fichier dans lequel vous voulez stocker le chemin :");
                        String nomFichier = scan.nextLine().trim();
                        
                        reponseUtilisateur = Coherence.verificationOption(scan, Set.of("text", "html"), "Tapez 'text' si vous voulez placer le chemin dans un fichier text,"
                                + "\net 'html' si vous preferez dans un fichier html : ");
                        
                        if (reponseUtilisateur.equals("html")) {
                            FichierHTMLGenerateur.genererFichierHTML(selection, villesAvisiter, nomFichier+ ".html");
                        } else {
                            FichierTexteGenerateur fichier = new FichierTexteGenerateur(villesAvisiter, selection, villeDep);
                            fichier.genererFichierText(nomFichier);
                        }
                    } else {
                        System.out.println("\n\nLe meilleur Chemin : ");
                        System.out.println(villesAvisiter.stream().map(Ville::getNomDep).collect(Collectors.joining(" -> ")));
                    }
                    break;
                   
                default:
                    continuer = false;
            }
        } 
		scan.close();
	}
}
