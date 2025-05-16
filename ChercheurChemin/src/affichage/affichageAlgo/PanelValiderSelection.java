package affichage.affichageAlgo;

import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.Personne;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente le panneau pour demander a l'utilisateur s'il a finit sa selection de personnes.
 * @version 1.0
 */

public class PanelValiderSelection extends  JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panel pour valider les personnes selectionnees.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelValiderSelection(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout());
        
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBackground(Color.WHITE);
        JPanel boutonsPanel = new JPanel(new FlowLayout()); // Boutons en ligne
        boutonsPanel.setBackground(Color.WHITE);

        JLabel texte = new JLabel("Souhaitez-vous continuer à sélectionner des personnes selon un autre critère ?");
        JButton Continuer = new JButton("Continuer");
        JButton Valider = new JButton("Valider la selection");
        JButton Recommancer = new JButton("Recommencer");
        
        boutonsPanel.add(Continuer);
        boutonsPanel.add(Valider);
        boutonsPanel.add(Recommancer);

        panelBas.add(texte, BorderLayout.NORTH);
        panelBas.add(boutonsPanel, BorderLayout.CENTER);

        Continuer.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelAlgo(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Valider.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelVilleDepart(frame, selection, personnes,villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Recommancer.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelAlgo(frame, personnes, personnes, villes, regions)); //reinitialise le trie
            frame.revalidate();
            frame.repaint();
        });

        this.add(panelBas, BorderLayout.CENTER);
    }
}