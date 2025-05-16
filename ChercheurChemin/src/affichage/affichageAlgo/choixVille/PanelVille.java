package affichage.affichageAlgo.choixVille;

import affichage.affichageAlgo.PanelAlgo;
import affichage.affichageAlgo.PanelChoix;
import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList; 
import javax.swing.*;
import peuple.Personne;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente un panneau permettant de selectionner des criteres de trie en fonction des villes des personnes.
 * Elle permet de naviguer entre différents écrans via des boutons.
 * @version 1.0
 */

public class PanelVille extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panel Ville.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelVille(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout()); //en colonne
        
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        panelTop.setPreferredSize(new Dimension(800, 35));

        JPanel PanelBas = new JPanel();
        PanelBas.setBackground(Color.WHITE);

        JButton Population = new JButton("Population");
        JButton Superficie = new JButton("Superficie");
        JButton Departement = new JButton("Département");
        JButton Region = new JButton("Région");
        JButton Nom = new JButton("Nom Ville");
        JButton VilleChefLieu = new JButton("Ville chef-lieu");
        JButton Retour = new JButton("Retour");

        PanelBas.add(Population);
        PanelBas.add(Superficie);
        PanelBas.add(Departement);
        PanelBas.add(Region);
        PanelBas.add(Nom);
        PanelBas.add(VilleChefLieu);
        PanelBas.add(Retour);

        Population.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelChoix(frame, "Ville", "Region", "population",null, null, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Superficie.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelChoix(frame, "Ville", "Region", "superficie",null, null, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Departement.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelDepartement(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Region.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelRegion(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Nom.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelNomVille(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        VilleChefLieu.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelVilleChefLieu(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Retour.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelAlgo(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });

        this.add(panelTop, BorderLayout.NORTH);
        this.add(PanelBas, BorderLayout.CENTER);
    }

}
