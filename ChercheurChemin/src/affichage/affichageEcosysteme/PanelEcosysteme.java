package affichage.affichageEcosysteme;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import affichage.Frame;
import affichage.PanelTop;
import baseDeDonnees.ModificationPersonnesTerminal;
import peuple.*;
import territoire.*;

/**
 * Represente un panel permettant de visualiser la base de donnees des personnes 
 * et de la modifier (ajouter, modifier ou supprimer des personnes).
 */
public class PanelEcosysteme extends JPanel {
	// Pour que l'utilisateur ne puisse pas modifier les valeurs dans les tables (que en cliquant sur le boutton Modifier)
	private static class ModeleTableauNonModifiable extends DefaultTableModel {
	    private static final long serialVersionUID = 1L;

		ModeleTableauNonModifiable(Object[][] data, Object[] columnNames) {
	        super(data, columnNames);
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	        return false; // Rend toutes les cellules non éditables
	    }
	}
	
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur pour creer le panel de l'ecosysteme.
     *
     * @param frame La fenetre principale.
     * @param selection La partie de la population selectionnee a cette etape dans l'algorithme de tri 
			  Pour pouvoir reinitialiser la liste des personnes a visiter puisque l'utilisateur 
			  n'a pas le droit de trier et modifier la base de donnees en meme temps). 
     * @param baseDeDonnees Toutes les personnes de la base de donnees.
     * @param baseDeDonneesVilles Toutes les villes de la base de donnees.
     * @param regions Toutes les regions de la base de donnees.
     */
	public PanelEcosysteme(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> baseDeDonnees, ArrayList<Ville> baseDeDonneesVilles, ArrayList<Region> regions){
        this.setLayout(new BorderLayout()); 
        
        JPanel panelTop = new PanelTop(frame, baseDeDonnees, baseDeDonnees, baseDeDonneesVilles, regions);

        // Trie des personnes pour les placer dans un tableau selon leur fonction
        ArrayList<Etudiant> etudiants = new ArrayList<>();
        ArrayList<MCF> mcfs = new ArrayList<>();
        ArrayList<Chercheur> chercheurs = new ArrayList<>();

        for (Personne p: baseDeDonnees){
            if (p instanceof Etudiant etudiant){
                etudiants.add(etudiant);
            } else if (p instanceof MCF mcf){
                mcfs.add(mcf);
            } else {
                chercheurs.add((Chercheur)p);
            }
        }

        String[][] infoEtudiants = new String[etudiants.size()][5];
        int i = 0;
        for (Etudiant etud: etudiants){
            infoEtudiants[i][0] = etud.getNom();
            infoEtudiants[i][1] = etud.getPrenom();
            infoEtudiants[i][2] = etud.getAge() + "";
            infoEtudiants[i][3] = etud.getVille().getNomDep();
            infoEtudiants[i][4] = etud.getID() + "";
            i++;
        }

        String[][] infoMCFs = new String[mcfs.size()][5];
        i = 0;
        for (MCF mcf: mcfs){
            infoMCFs[i][0] = mcf.getNom();
            infoMCFs[i][1] = mcf.getPrenom();
            infoMCFs[i][2] = mcf.getAge() + "";
            infoMCFs[i][3] = mcf.getVille().getNomDep();
            infoMCFs[i][4] = mcf.getID() + "";
            i++;
        }

        String[][] infoChercheurs = new String[chercheurs.size()][5];
        i = 0;
        for (Chercheur cherch: chercheurs){
            infoChercheurs[i][0] = cherch.getNom();
            infoChercheurs[i][1] = cherch.getPrenom();
            infoChercheurs[i][2] = cherch.getAge() + "";
            infoChercheurs[i][3] = cherch.getVille().getNomDep();
            infoChercheurs[i][4] = cherch.getID() + "";
            i++;
        }

        JPanel PanelBas = new JPanel();
        PanelBas.setLayout(new GridLayout(1, 2));
        PanelBas.setBackground(Color.WHITE);

        // Panel pour observer la base de donnees
        JPanel gauchePanel = new JPanel();
        gauchePanel.setLayout(new BorderLayout());
        gauchePanel.setBackground(new Color(204, 169, 221));

        JLabel titleLabel = new JLabel("Écosystème courant", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gauchePanel.add(titleLabel, BorderLayout.NORTH);

        // Panel superieur (qui contient les tableaux des personnes selon leur fonction)
        JPanel panelHaut = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        String[] colonnes = {"Nom", "Prénom", "Âge", "Ville", "ID"};

        ModeleTableauNonModifiable modelEtudiants = new ModeleTableauNonModifiable(infoEtudiants, colonnes);
        ModeleTableauNonModifiable modelMCFs = new ModeleTableauNonModifiable(infoMCFs, colonnes);
        ModeleTableauNonModifiable modelChercheurs = new ModeleTableauNonModifiable(infoChercheurs, colonnes);

        // Dans chaqune des tables on ne peut selectionner que une personne a la fois 
        // (pour ensuite modifier cette personne ou supprimmer si c'est possible)
        JTable etudiantsTable = new JTable(modelEtudiants);
        etudiantsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("Etudiants", new JScrollPane(etudiantsTable));

        JTable mcfsTable = new JTable(modelMCFs);
        mcfsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("MCFs", new JScrollPane(mcfsTable));

        JTable chercheursTable = new JTable(modelChercheurs);
        chercheursTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabbedPane.addTab("Chercheurs", new JScrollPane(chercheursTable));

        panelHaut.add(tabbedPane, BorderLayout.CENTER);

        InfoPanel panelInfos = new InfoPanel();

        JScrollPane panelInformations = panelInfos.getPanel();

    
        // On reinitialise les informations supplementaires sur la personne selectionne si aucune n'est selectionnee
        panelInfos.mettreAjourLesInformations(null);

        gauchePanel.add(panelHaut, BorderLayout.CENTER);
        gauchePanel.add(panelInformations, BorderLayout.SOUTH);

        // Panel pour modifier la base de donnees
        JPanel droitPanel = new JPanel();
        droitPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 lignes pour les boutons en une colonne
        droitPanel.setBackground(Color.WHITE);

        JButton btnModifier = new JButton("Modifier");
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnSupprimer = new JButton("Supprimer");

        // Au debut aucune personne n'est selectionnee donc on desactive les bouttons
        btnModifier.setEnabled(false); 
        btnSupprimer.setEnabled(false);

        ListSelectionListener listener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Pour eviter les doublons

                    JTable tableActive = null;
                    String tableNom = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
                    if (tableNom.equals("Etudiants")) {
                    	tableActive = etudiantsTable;
                    } else if (tableNom.equals("MCFs")) {
                    	tableActive = mcfsTable;
                    } else if (tableNom.equals("Chercheurs")) {
                    	tableActive = chercheursTable;
                    }

                    if (tableActive != null) {
                        int ligneSelectionnee = tableActive.getSelectedRow();
                        if (ligneSelectionnee != -1) {
                            int id = Integer.parseInt((String) tableActive.getValueAt(ligneSelectionnee, 4));
                            Personne personneSelectionee = null;
                            for (Personne p: baseDeDonnees){
                                if (p.getID() == id){
                                    personneSelectionee = p;
                                }
                            }
                            // Deselectionner les autres tables si ce n'est pas la table qui a declenche l'evenement
                            if (tableActive != etudiantsTable) {
                            	etudiantsTable.clearSelection();
                            }
                            if (tableActive != mcfsTable) {
                            	mcfsTable.clearSelection();
                            }
                            if (tableActive != chercheursTable) {
                            	chercheursTable.clearSelection();
                            }
                
                            // Si une ligne est selectionnee on active les bouttons
                            btnModifier.setEnabled(true);
                            btnSupprimer.setEnabled(true);
                
                            // Si l'utilisateur a selectionne une personne on affiche en bas tous ces caracteristiques 
                            if (ligneSelectionnee != -1) {
                            	panelInfos.mettreAjourLesInformations(personneSelectionee);
                            } else {
                                // Reinitialiser les informations si aucune ligne n'est selectionnee
                                panelInfos.mettreAjourLesInformations(null);
                            }
                        } 
                    }
                }
            }
        };
        
        etudiantsTable.getSelectionModel().addListSelectionListener(listener);
        mcfsTable.getSelectionModel().addListSelectionListener(listener);
        chercheursTable.getSelectionModel().addListSelectionListener(listener);

        btnModifier.addActionListener(e -> {
            // On identifie la table active 
            JTable tableActive = null;
            String tableNom = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            if (tableNom.equals("Etudiants")) {
            	tableActive = etudiantsTable;
            } else if (tableNom.equals("MCFs")) {
            	tableActive = mcfsTable;
            } else if (tableNom.equals("Chercheurs")) {
            	tableActive = chercheursTable;
            }
        
            if (tableActive != null) {
                int ligneSelectionnee = tableActive.getSelectedRow();
                if (ligneSelectionnee != -1) {
                    int id = Integer.parseInt((String) tableActive.getValueAt(ligneSelectionnee, 4));

                    Personne personneSelectionnee = null;
                    for (Personne pers: baseDeDonnees){
                        if (pers.getID() == id){
                            personneSelectionnee = pers;
                        }
                    }
                    
                    ModificationPersonne dialogueModification = new ModificationPersonne(frame, personneSelectionnee, baseDeDonneesVilles, baseDeDonnees);
                    dialogueModification.setVisible(true);

                    // Si l'utilisateur confirme, on va mettre a jour les donnees
                    // Comme un tableau contient des donnes reduit, nous ne sommes pas obliges de mettre a jour tous les tables, mais juste la ligne
                    // du personne modifie
                    if (dialogueModification.estConfirme()) {
                    	tableActive.setValueAt(dialogueModification.getNomSelectionneTable(), ligneSelectionnee, 0);
                    	tableActive.setValueAt(dialogueModification.getPrenomSelectionneTable(), ligneSelectionnee, 1);
                    	tableActive.setValueAt(dialogueModification.getAgeSelectionneTable(), ligneSelectionnee, 2);
                    	tableActive.setValueAt(dialogueModification.getVilleSelectionneTable(), ligneSelectionnee, 3);

                        panelInfos.mettreAjourLesInformations(personneSelectionnee);
                        
                    }
                }
            }
        });

        btnAjouter.addActionListener(e -> {
            AjoutPersonne dialogueAjout = new AjoutPersonne(frame, baseDeDonneesVilles, baseDeDonnees);
            dialogueAjout.setVisible(true);
            
            if (dialogueAjout.estConfirme()){
            	// On recupere la personne qu'on vient d'ajouter qui se situe a la fin de la liste des personnes
            	// et mettons a jour la table de cette personne et le panneau d'informations supplementaires 
                Personne nvPersonne = baseDeDonnees.get(baseDeDonnees.size()-1);

                String [] nouvellePersonne = {nvPersonne.getNom(), nvPersonne.getPrenom(), nvPersonne.getAge() + "", nvPersonne.getVille().getNomDep(), nvPersonne.getID() + ""};
                if (nvPersonne instanceof Etudiant) {
                    modelEtudiants.addRow(nouvellePersonne);
                    
                } else if (nvPersonne instanceof MCF) {
                    modelMCFs.addRow(nouvellePersonne);

                } else if (nvPersonne instanceof Chercheur){
                    modelChercheurs.addRow(nouvellePersonne);
                    
                }
                
                panelInfos.mettreAjourLesInformations(nvPersonne);
            }
        });

        btnSupprimer.addActionListener(e -> {
        	// On identifie la table active 
            JTable tableActive = null;
            String tableNom = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            if (tableNom.equals("Etudiants")) {
            	tableActive = etudiantsTable;
            } else if (tableNom.equals("MCFs")) {
            	tableActive = mcfsTable;
            } else if (tableNom.equals("Chercheurs")) {
            	tableActive = chercheursTable;
            }
        
            if (tableActive != null) {
                int ligneSelectionnee = tableActive.getSelectedRow();
                if (ligneSelectionnee != -1) {
                    int id = Integer.parseInt((String) tableActive.getValueAt(ligneSelectionnee, 4));

                    Personne personneSelectionnee = null;
                    for (Personne pers: baseDeDonnees){
                        if (pers.getID() == id){
                            personneSelectionnee = pers;
                        }
                    }

                    Object[] options = { "Oui", "Non" };
                    int response = JOptionPane.showOptionDialog(null, "Voulez-vous vraiment supprimer cette personne ?\n" + personneSelectionnee.getNomPrenom(), 
                            "Confirmation de suppression", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]
                    );

                    if (response == JOptionPane.YES_OPTION) {
                    	boolean suppressionReussite = false;
                    	
                        if (personneSelectionnee != null && personneSelectionnee instanceof Etudiant etud){
                            Titulaire tit = etud.getEncadrant();
                            tit.popEtudiant(etud);
                            baseDeDonnees.remove(personneSelectionnee);
                            modelEtudiants.removeRow(ligneSelectionnee);
                            suppressionReussite = true;
                            
                        } else if (personneSelectionnee != null && personneSelectionnee instanceof MCF mcf) {
                            if (mcf.getEtudiant() != null){
                                Etudiant etudSansEnc = mcf.getEtudiant();
                                Titulaire nvTitulaire = null;
                                for (Personne pers: baseDeDonnees){
                                    if (pers instanceof Titulaire tit && tit.peutPrendreEtudiant(etudSansEnc)){
                                        if ((tit instanceof Chercheur) || (tit instanceof MCF mcf2 && !mcf.equals(mcf2))) {
                                            nvTitulaire = tit;
                                            break; // Des que nous avons trouve un titulaire qui peut prendre etudiant on peut s'arreter
                                        }
                                    }
                                }
                                if (nvTitulaire != null) { // On a trouve un titulaire donc c'est bon
                                    etudSansEnc.setEncadrant(nvTitulaire);
                                    baseDeDonnees.remove(personneSelectionnee);
                                    modelMCFs.removeRow(ligneSelectionnee);
                                    suppressionReussite = true;
                                } else {
                                    JOptionPane.showMessageDialog(this, "Impossible de supprimer ce MCF, puisque il n'existe pas de titulaire \n"
                                    		+ "qui pourra prendre en thèse son étudiant.", 
                                    		"Impossibilité de supprimer", JOptionPane.ERROR_MESSAGE);
                                }
                            } else { // Si mcf n'a pas d'etudiant on peut le supprimer avec l'esprit tranquille
                                baseDeDonnees.remove(personneSelectionnee);
                                modelMCFs.removeRow(ligneSelectionnee);
                                suppressionReussite = true;
                            }
                        } else if (personneSelectionnee != null && personneSelectionnee instanceof Chercheur chercheur) {
                            if (chercheur.getEtudiantsSet() != null && !chercheur.getEtudiantsSet().isEmpty()){ // il a des etudiants en these
                                // On initialise les listes pour que apres obtenir les paires etudiants - titulaires que nous aurons trouve 
                            	ArrayList<Chercheur> chercheursTrouvePourEtudiants = new ArrayList<>(); 
                                ArrayList<Set<Etudiant>> etudiantsParChercheur = new ArrayList<>();
                                HashMap<Etudiant, MCF> couplage = new HashMap<>();

                                // Soit notre fonction a reussi de supprimer la personne et on peut tkl le supprimer de la base de donnes
                                // Soit on donne le choix a l'utilisateur si il veut ou pas de supprimer le max des etudiants incompatibles 
                                // ou d'annuler tout
                                if (chercheur.essaieSupprimerTousEtudiants(chercheur.getEtudiantsSet(), baseDeDonnees, chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage)){ // On a reussi a attribue tous les etudiants aux autres encadrants
                                    baseDeDonnees.remove(personneSelectionnee);
                                    modelChercheurs.removeRow(ligneSelectionnee);
                                    suppressionReussite = true;
                                } else {
                                	if (chercheursTrouvePourEtudiants.isEmpty() && couplage.keySet().isEmpty()) { // Si on vraiment trouve aucune couple etudiant - titulaire
                                		JOptionPane.showMessageDialog(this, "Impossible de supprimer ce chercheur, puisque il n'existe pas de titulaires \n"
                                                + "à qui peuvent être attribués tous les étudiants de ce chercheur.", 
                                                "Impossibilité de supprimer", JOptionPane.ERROR_MESSAGE);
                                	} else { // Si on a trouve au moins une, on propose d'attribuer le maximum des etudiants
                                		String message = "Il est impossible de supprimer ce chercheur \n"
                                				+ "puisqu'il a des étudiants en thèse pour lesquels, dans la base de données,\n"
                                                + "il n'existe pas d'encadrant à qui ils peuvent être attribués.\n\n"
                                                + "Est-ce que vous voulez attribuer le maximum des étudiants \n"
                                                + "que possible aux autres titulaires ou annuler la suppression du chercheur ?";

                                        String[] options2 = {"Supprimer le maximum", "Annuler"};

                                        int choix = JOptionPane.showOptionDialog(null, message, "Supprimer le chercheur", JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE, null, options2, options2[1] 
                                        );

                                        if (choix == JOptionPane.YES_OPTION) {
                                            chercheur.supprimerMaxDesEtudiants(chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage);
                                        } 
                                	}
                                }
                                
                            } else { // Si le chercheur n'a pas d'etudiants en these on peut le supprimer avec l'esprit tranquille
                                baseDeDonnees.remove(personneSelectionnee);
                                modelChercheurs.removeRow(ligneSelectionnee);
                                suppressionReussite = true;
                            }
                        }
                        if (suppressionReussite) {
                        	panelInfos.mettreAjourLesInformations(null);
                            btnModifier.setEnabled(false); 
                            btnSupprimer.setEnabled(false);
                            ModificationPersonnesTerminal.actualiserBaseDeDonnees(baseDeDonnees);
                        }
                    } 
                }
            }
        });

        droitPanel.add(btnAjouter);
        droitPanel.add(btnModifier);
        droitPanel.add(btnSupprimer);

        PanelBas.add(gauchePanel);
        PanelBas.add(droitPanel);
        
        this.add(panelTop, BorderLayout.NORTH);
        this.add(PanelBas, BorderLayout.CENTER);
    }
}
