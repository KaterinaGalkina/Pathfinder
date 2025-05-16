package affichage.affichageAlgo;

import affichage.affichageAlgo.choixPersonne.PanelPersonne;
import affichage.affichageAlgo.choixVille.PanelVille;
import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.Personne;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente le menu Algo lorsque l'utilisateur doit choisir quelles personnes il veut visiter.
 * @version 1.0
 */

public class PanelAlgo extends JPanel{
    private static final long serialVersionUID = 1L;
     /**
     * Constructeur du panneau pour choisir les criteres de trie.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelAlgo(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout()); //en colonne
        
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        JPanel panelBas = new JPanel(new BorderLayout());
        JPanel boutonsPanel = new JPanel(new FlowLayout()); // Boutons en ligne
        panelBas.setBackground(Color.WHITE);
        boutonsPanel.setBackground(Color.WHITE);

        JLabel texte = new JLabel("Sélectionnez les personnes à voir par : ");
        JButton ville = new JButton("Ville");
        JButton personne = new JButton("Personne");
        JButton valider = new JButton("valider");
        JButton recommancer = new JButton("recommencer");
        
        boutonsPanel.add(ville);
        boutonsPanel.add(personne);
        boutonsPanel.add(valider);
        boutonsPanel.add(recommancer);

        panelBas.add(texte, BorderLayout.NORTH);
        panelBas.add(boutonsPanel, BorderLayout.CENTER);

        ville.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelVille(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        personne.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        valider.addActionListener(e -> {
            if (selection == personnes){
                int choix = JOptionPane.showConfirmDialog(frame, "Vous avez sélectionné toutes les personnes de la base de données. Êtes-vous sûr ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (choix == JOptionPane.YES_OPTION) {
                    frame.effacer();
                    frame.add(new PanelVilleDepart(frame, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                } 
            }
            else{
                frame.effacer();
                frame.add(new PanelVilleDepart(frame, selection, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            }
        });
        recommancer.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelAlgo(frame, personnes, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });

        this.add(panelBas, BorderLayout.CENTER);
    }
}
