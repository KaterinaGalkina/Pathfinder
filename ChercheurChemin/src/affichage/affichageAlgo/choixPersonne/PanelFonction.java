package affichage.affichageAlgo.choixPersonne;

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
 * Cette classe represente un panneau permettant de selectionner des fonctions a l'aide de check boxes.
 * @version 1.0
 */

public class PanelFonction extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des villes.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelFonction(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout());
        
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        List<JCheckBox> checkBoxes = new ArrayList<>();
        ArrayList<String> selectedFonction = new ArrayList<>();
        ArrayList<String> NomFonctions = new ArrayList<>();
        NomFonctions.add("etudiant");
        NomFonctions.add("chercheur");
        NomFonctions.add("mcf");
        NomFonctions.add("titulaire");

        JPanel PanelBas = new JPanel(new BorderLayout());
        PanelBas.setBackground(Color.WHITE);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setBackground(Color.WHITE);
        for (String f : NomFonctions) {
            JCheckBox checkBox = new JCheckBox(f);
            checkBoxes.add(checkBox);
            checkBoxPanel.add(checkBox);
        }
        
        JPanel PanelBouton = new JPanel();
        PanelBouton.setBackground(Color.WHITE);
        PanelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton Retour = new JButton("Retour");
        JButton Valider = new JButton("Valider la seléction");
        PanelBouton.add(Retour);
        PanelBouton.add(Valider);

        Retour.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Valider.addActionListener(e->{
            if(checkBoxes.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Erreur : aucune fonction selectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else{
                selectedFonction.clear(); // Réinitialiser la liste
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            for(String f : NomFonctions){
                                if(f.equals(checkBox.getText())){
                                    selectedFonction.add(f);
                                }
                            }
                        }
                    }
                    if(Trie.TrieFonction(selection, selectedFonction).isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Erreur : aucune personne selectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        frame.effacer();
                        frame.add(new PanelValiderSelection(frame, Trie.TrieFonction(selection, selectedFonction), personnes, villes, regions));
                        frame.revalidate();
                        frame.repaint();
                    }
                }

        });
        PanelBas.add(checkBoxPanel, BorderLayout.CENTER);
        PanelBas.add(PanelBouton, BorderLayout.SOUTH);
        this.add(PanelBas, BorderLayout.CENTER);
    }
}