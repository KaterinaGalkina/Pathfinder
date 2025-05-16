package affichage.affichageAlgo.choixPersonne;

import affichage.affichageAlgo.PanelValiderSelection;
import affichage.Frame;
import affichage.PanelTop;
import filtrage.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import peuple.*;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente un panneau permettant de selectionner des annee (1, 2 ou 3) a l'aide de check boxes.
 * @version 1.0
 */

public class PanelAnneeDeThese extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des annees de these.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelAnneeDeThese(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout());

        if (Trie.convertionEtudiants(selection).isEmpty()) {
            int result = JOptionPane.showOptionDialog(frame,"Erreur : Aucun étudiant n'est présent dans la sélection actuelle.","Erreur",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,new Object[]{"OK"}, "OK");
            if (result == JOptionPane.OK_OPTION) {
                frame.effacer();
                frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            }
        }
        else{
            JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
            this.add(panelTop, BorderLayout.NORTH);
            
            List<JCheckBox> checkBoxes = new ArrayList<>();
            ArrayList<String> selectedAnnee = new ArrayList<>();

            JPanel PanelBas = new JPanel(new BorderLayout());
            PanelBas.setBackground(Color.WHITE);

            JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            checkBoxPanel.setBackground(Color.WHITE);
            for (int i =1; i<4; i++) {
                JCheckBox checkBox = new JCheckBox("Année "+i);
                checkBoxes.add(checkBox);
                checkBoxPanel.add(checkBox);
            }

            JPanel PanelBouton = new JPanel(new BorderLayout());
            PanelBouton.setBackground(Color.WHITE);
            PanelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
            JButton Retour = new JButton("Retour");
            JButton Valider = new JButton("Valider la sélection");
            PanelBouton.add(Valider);
            PanelBouton.add(Retour);

            Retour.addActionListener(e->{
                frame.effacer();
                frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            });
            Valider.addActionListener(e->{
                if(checkBoxes.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Erreur : aucune année sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    selectedAnnee.clear(); // Reinitialiser la liste
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            selectedAnnee.add(checkBox.getText());
                        }
                    }
                    int annee1=0,annee2=0, annee3=0;
                    if(selectedAnnee.contains("Année 1")){
                        annee1=1;
                    }
                    if(selectedAnnee.contains("Année 2")){
                        annee2=2;
                    }
                    if(selectedAnnee.contains("Année 3")){
                        annee3=3;
                    }
                    if(Trie.TrieAnneedeThese(selection, annee1, annee2, annee3).isEmpty()){
                        JOptionPane.showMessageDialog(frame, "Erreur : aucune année sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        
                        frame.effacer();
                        frame.add(new PanelValiderSelection(frame, Trie.TrieAnneedeThese(selection, annee1, annee2, annee3), personnes, villes, regions));
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
}