package affichage.affichageAlgo.choixVille;

import affichage.affichageAlgo.PanelValiderSelection;
import filtrage.Trie;
import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import peuple.Personne;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente un panneau permettant de selectionner des villes chef lieu a l'aide de check boxes.
 * @version 1.0
 */

public class PanelVilleChefLieu extends JPanel{
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panel Ville chef-Lieu.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelVilleChefLieu(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout());
        
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        panelTop.setPreferredSize(new Dimension(800, 35));
        this.add(panelTop, BorderLayout.NORTH);

        List<JCheckBox> checkBoxes = new ArrayList<>();
        ArrayList<Ville> selectedVille = new ArrayList<>();

        JPanel PanelBas = new JPanel(new BorderLayout());
        PanelBas.setBackground(Color.WHITE);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setBackground(Color.WHITE);
        for (Region reg : Region.values()) {
            JCheckBox checkBox = new JCheckBox(reg.getChefLieu().getNom());
            checkBoxes.add(checkBox);
            checkBoxPanel.add(checkBox);
        }

        JPanel PanelBouton = new JPanel(new BorderLayout());
        PanelBouton.setBackground(Color.WHITE);
        PanelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton Valider = new JButton("Valider la sélection");
        JButton Retour = new JButton("Retour");
        PanelBouton.add(Valider);
        PanelBouton.add(Retour);
        PanelBas.add(PanelBouton, BorderLayout.SOUTH);
        Valider.addActionListener(e->{
            if(checkBoxes.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Erreur : aucune ville sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else{
                selectedVille.clear(); // Réinitialiser la liste
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            for(Region reg : Region.values()){
                                if(reg.getChefLieu().getNom().equals(checkBox.getText())){
                                    selectedVille.add(reg.getChefLieu());
                                }
                            }
                        }
                    }
                    if(Trie.TrieRegionVilleChefLieu(selection, selectedVille).isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        frame.effacer();
                        frame.add(new PanelValiderSelection(frame, Trie.TrieRegionVilleChefLieu(selection, selectedVille), personnes, villes, regions));
                        frame.revalidate();
                        frame.repaint();
                    }
                }

        });
        Retour.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelVille(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
       
        PanelBas.add(checkBoxPanel, BorderLayout.CENTER);
        this.add(PanelBas, BorderLayout.CENTER);
    }
    
}
