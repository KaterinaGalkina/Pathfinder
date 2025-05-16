package affichage.affichageAlgo;

import affichage.Frame;
import affichage.PanelTop;
import genetique.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.*;
import representationSolution.*;
import territoire.*;

 /**
 * Cette classe represente le panneau affiché à la fin du processus.
 * Il permet de visualiser l'itineraire optimal et les informations des personnes a visiter.
 * @version 1.0
 */

public class PanelFin extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de fin apres execution de l'algorithme.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param VilleDepart Ville de départ pour l'itineraire.
     * @param personnes Liste de toutes les personnes. Si jamais l'utilisateur veut recommencer
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelFin(Frame frame, ArrayList<Personne> selection, Ville VilleDepart, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // En colonne
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);
        
        ArrayList<Ville> villesAvisiter = new ArrayList<>();
        for(Personne pers : selection){
            Ville v = pers.getVille();
            if(!villesAvisiter.contains(v)){
                villesAvisiter.add(v);
            }
        }
        if(!villesAvisiter.contains(VilleDepart)){
            villesAvisiter.add(VilleDepart);
        }
        ArrayList<Ville> MeilleurChemin = new ArrayList<>();
        if(villesAvisiter.size()>3){
            Chemin meilleurChemin = Chemin.algorithmeGenetique(villesAvisiter, 50, 0.3, 0.5, 1);
            MeilleurChemin = meilleurChemin.gene;
        }
        else{
            for(Ville v : villesAvisiter){
                MeilleurChemin.add(v);
            }
        }
        villesAvisiter.clear();
        int indexDepart = MeilleurChemin.indexOf(VilleDepart);
        for (int i = 0; i < MeilleurChemin.size(); i++) {
            // Calcul de l'index circulaire
            int indexActuel = (indexDepart + i) % MeilleurChemin.size();
            villesAvisiter.add(MeilleurChemin.get(indexActuel));
        }
        villesAvisiter.add(VilleDepart); // Ajoute VilleDepart a la fin
        
        //Ajoute les informations des personnes selectionnees et les villes a visiter
        this.add(new PanelPersonneVilleDisplay(selection, villesAvisiter), BorderLayout.CENTER); 

        JPanel panelBouton = new JPanel();
        JButton BoutonFichier = new JButton("Enregistrer le chemin");
        JButton nouveauChemin = new JButton("Nouveau chemin");
        panelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBouton.add(BoutonFichier);
        panelBouton.add(nouveauChemin);
        this.add(panelBouton, BorderLayout.SOUTH);

        BoutonFichier.addActionListener(e -> {
            // Demander le nom du fichier
            JDialog dialog = new JDialog(frame, "Nom du fichier", true);
            dialog.setLayout(new FlowLayout());

            // Champ de texte pour le nom du fichier
            JTextField fileNameField = new JTextField(20);

            // Cases a cocher pour le type de fichier
            JCheckBox htmlCheckBox = new JCheckBox("HTML");
            JCheckBox textCheckBox = new JCheckBox("Fichier texte");

            // Boutons OK et Annuler
            JButton okButton = new JButton("OK");
            okButton.setEnabled(false); // Desactiver le bouton OK par defaut
            JButton cancelButton = new JButton("Annuler");

            // Gestion des cases a cocher : selection mutuelle et activation du bouton OK
            htmlCheckBox.addActionListener(event -> {
                if (htmlCheckBox.isSelected()) {
                    textCheckBox.setSelected(false);
                    okButton.setEnabled(true); // Activer le bouton OK
                } else if (!textCheckBox.isSelected()) {
                    okButton.setEnabled(false); // Désactiver le bouton si aucune case n'est sélectionnée
                }
            });

            textCheckBox.addActionListener(event -> {
                if (textCheckBox.isSelected()) {
                    htmlCheckBox.setSelected(false);
                    okButton.setEnabled(true); // Activer le bouton OK
                } else if (!htmlCheckBox.isSelected()) {
                    okButton.setEnabled(false); // Désactiver le bouton si aucune case n'est sélectionnée
                }
            });

            // Action du bouton OK
            okButton.addActionListener(event -> {
                String nomFichier = fileNameField.getText().trim();

                // Validation du nom et traitement du fichier
                if (nomFichier.isEmpty()) {
                	JOptionPane.showMessageDialog(dialog, "Le nom du fichier ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    String extension = htmlCheckBox.isSelected() ? ".html" : ".txt";
                    String fullFileName = nomFichier + extension;

                    // Traitement en fonction du type de fichier choisi
                    if (htmlCheckBox.isSelected()) {
                        FichierHTMLGenerateur.genererFichierHTML(selection, villesAvisiter, fullFileName);
                    } else {
                        FichierTexteGenerateur fichier = new FichierTexteGenerateur(villesAvisiter, selection, VilleDepart);
				    		fichier.genererFichierText(nomFichier);
                    }

                    dialog.dispose(); // Fermer la boite de dialogue
                }
            });

            // Action du bouton Annuler
            cancelButton.addActionListener(event -> {
                fileNameField.setText(""); // Reinitialiser le champ texte
                dialog.dispose(); // Fermer la boîte de dialogue
            });

            // Ajouter les composants au dialogue
            dialog.add(new JLabel("Entrez le nom du fichier (sans extension) :"));
            dialog.add(fileNameField);
            dialog.add(htmlCheckBox);
            dialog.add(textCheckBox);
            dialog.add(okButton);
            dialog.add(cancelButton);

            // Configurer le dialogue
            dialog.setSize(500, 200);
            dialog.setLocationRelativeTo(frame); // Centrer la boite de dialogue
            dialog.setVisible(true);
        });
        nouveauChemin.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelAlgo(frame, personnes, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });

    
    }    
    
}
