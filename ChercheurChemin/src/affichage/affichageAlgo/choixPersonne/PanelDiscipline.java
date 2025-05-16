package affichage.affichageAlgo.choixPersonne;

import affichage.affichageAlgo.PanelChoix;
import affichage.affichageAlgo.PanelValiderSelection;
import filtrage.Trie;
import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import peuple.Discipline;
import peuple.Personne;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente un panneau permettant de selectionner des disciplines a l'aide de check boxes.
 * @version 1.0
 */

public class PanelDiscipline extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des disciplines.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelDiscipline(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout());
        
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        List<JCheckBox> checkBoxes = new ArrayList<>();
        ArrayList<Discipline> selectedDisciplines = new ArrayList<>();

        JPanel PanelBas = new JPanel(new BorderLayout());
        PanelBas.setBackground(Color.WHITE);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setBackground(Color.WHITE);
        for (Discipline discipline : Discipline.values()) {
            JCheckBox checkBox = new JCheckBox(discipline.name());
            checkBoxes.add(checkBox);
            checkBoxPanel.add(checkBox);
        }
        JPanel PanelBouton = new JPanel();
        PanelBouton.setBackground(Color.WHITE);
        PanelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton Retour = new JButton("Retour");
        JButton Valider = new JButton("Valider la sélection");
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
                JOptionPane.showMessageDialog(frame, "Erreur : aucune discipline disponible à sélectionner.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else{
                    selectedDisciplines.clear(); // Réinitialiser la liste
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            selectedDisciplines.add(Discipline.valueOf(checkBox.getText()));
                        }
                    }
                    if(Trie.TrieDisciplineOU(selection, selectedDisciplines).isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Erreur : vous n'avez sélectionné aucune discipline.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        frame.effacer();
                        if(selectedDisciplines.size()==2){
                            frame.add(new PanelChoix(frame, "Ou", "Et", "discipline", selectedDisciplines, null, selection, personnes, villes, regions));
                        }
                        else{
                            frame.add(new PanelValiderSelection(frame, Trie.TrieDisciplineOU(selection, selectedDisciplines), personnes, villes, regions));
                        }
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