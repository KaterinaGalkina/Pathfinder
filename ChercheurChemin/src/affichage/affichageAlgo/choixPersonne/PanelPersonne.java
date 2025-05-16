package affichage.affichageAlgo.choixPersonne;

import affichage.affichageAlgo.PanelAlgo;
import affichage.affichageAlgo.PanelNombre;
import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.Personne;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente un panneau permettant de selectionner des criteres de trie.
 * Elle permet de naviguer entre différents écrans via des boutons.
 * @version 1.0
 */

public class PanelPersonne extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panel Personne.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelPersonne(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout()); //en colonne
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        
        JPanel PanelBas = new JPanel();
        PanelBas.setBackground(Color.WHITE);

        JButton Discipline = new JButton("Discipline");
        JButton Fonctions = new JButton("Fonctions");
        JButton Nom = new JButton("Nom");
        JButton ID = new JButton("ID");
        JButton titulaires = new JButton("Titulaires en fonction de leurs étudiants");
        JButton Age = new JButton("Age");
        JButton NombreEtudiants = new JButton("Nombre d'étudiants");
        JButton Sujet = new JButton("Sujet de these");
        JButton Encadrant = new JButton("Etudiants en fonction de leur encadrant");
        JButton AnnéeDeThèse = new JButton("Année de Thèse");
        JButton Bureau = new JButton("Numero de Bureau");
        JButton Retour = new JButton("Retour");
        
        PanelBas.add(Discipline);
        PanelBas.add(Fonctions);
        PanelBas.add(Nom);
        PanelBas.add(ID);
        PanelBas.add(titulaires);
        PanelBas.add(Age);
        PanelBas.add(NombreEtudiants);
        PanelBas.add(Sujet);
        PanelBas.add(Encadrant);
        PanelBas.add(AnnéeDeThèse);
        PanelBas.add(Bureau);
        PanelBas.add(Retour);
        
        Discipline.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelDiscipline(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Fonctions.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelFonction(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Nom.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelNom(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        ID.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelID(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        titulaires.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelTitulaire(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Age.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelNombre(frame, "Age", "", selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        NombreEtudiants.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelNombre(frame, "nombreetudiant", "", selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Sujet.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelSujetDeThese(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Encadrant.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelEncadrant(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        AnnéeDeThèse.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelAnneeDeThese(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Bureau.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelBureau(frame, selection, personnes, villes, regions));
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