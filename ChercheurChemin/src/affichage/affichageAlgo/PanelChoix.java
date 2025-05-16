package affichage.affichageAlgo;

import affichage.affichageAlgo.choixPersonne.*;
import affichage.affichageAlgo.choixVille.*;
import affichage.Frame;
import affichage.PanelTop;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.*;
import territoire.*;

/**
 * Cette classe represente un panneau permettant de faire un choix entre deux options.
 * @version 1.0
 */
 
public class PanelChoix extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de choix.
     * @param frame Fenetre principale de l'application.
     * @param choix1 Premier choix propose.
     * @param choix2 Deuxieme choix propose.
     * @param nom Nom du critere de selection (e.g., "population", "superficie").
     * @param disciplines Liste des disciplines disponibles.
     * @param titulaires Liste des titulaires.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des régions disponibles.
     */
    public PanelChoix(Frame frame, String choix1, String choix2, String nom, ArrayList<Discipline> disciplines, ArrayList<Personne> titulaires, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout()); //en colonne

        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        panelTop.setPreferredSize(new Dimension(800, 35));
        this.add(panelTop, BorderLayout.NORTH);

        JPanel panelBas = new JPanel(new BorderLayout());
        JPanel boutonsPanel = new JPanel(new FlowLayout()); // Boutons en ligne
        panelBas.setBackground(Color.WHITE);
        boutonsPanel.setBackground(Color.WHITE);

        String phrase="";
        if("population".equals(nom.toLowerCase()) || "superficie".equals(nom.toLowerCase())){
            phrase="Voulez-vous selection la "+nom+" pour :";
        }
        else if ("discipline".equals(nom.toLowerCase())){
        	phrase="Voulez-vous que les titulaires fassent toutes les disciplines (ET) ou au moins une (OU)";
        }
        else if("titulaire".equals(nom.toLowerCase())){
        	phrase="Voulez-vous que les titulaires aient tous les étudiants (ET) ou au moins un (OU)";
        }
        JLabel texte = new JLabel(phrase);

        JButton C1=new JButton(choix1);
        JButton C2=new JButton(choix2);
        JButton Retour = new JButton("Retour");
        boutonsPanel.add(C1);
        boutonsPanel.add(C2);
        panelBas.add(Retour, BorderLayout.SOUTH);

        C1.addActionListener(e->{
            switch (nom.toLowerCase()) { //Ajouter pour discipline si ou et ET .... 
                case "population":
                    frame.effacer();
                    frame.add(new PanelNombre(frame, nom, choix1, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "superficie":
                    frame.effacer();
                    frame.add(new PanelNombre(frame, nom, choix1, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "discipline":
                    frame.effacer();
                    frame.add(new PanelValiderSelection(frame, filtrage.Trie.TrieDisciplineOU(selection, disciplines), personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "titulaire":
                    frame.effacer();
                    frame.add(new PanelValiderSelection(frame, filtrage.Trie.TrieTitulaireEtudiantOU(selection, titulaires), personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                default:
                    break;
            }
        });
        C2.addActionListener(e->{
            switch (nom.toLowerCase()) {
                case "population":
                    frame.effacer();
                    frame.add(new PanelNombre(frame, nom, choix2, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "superficie":
                    frame.effacer();
                    frame.add(new PanelNombre(frame, nom, choix2, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "discipline":
                    frame.effacer();
                    frame.add(new PanelValiderSelection(frame, filtrage.Trie.TrieDisciplineET(selection, disciplines), personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "titulaire":
                    frame.effacer();
                    frame.add(new PanelValiderSelection(frame, filtrage.Trie.TrieTitulaireEtudiantET(selection, titulaires), personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                default:
                    throw new AssertionError();
            }
        });
        Retour.addActionListener(e->{
            switch (nom.toLowerCase()) {
                case "population":
                    frame.effacer();
                    frame.add(new PanelVille(frame, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "superficie":
                    frame.effacer();
                    frame.add(new PanelVille(frame, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "discipline":
                    frame.effacer();
                    frame.add(new PanelDiscipline(frame, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                case "titulaire":
                    frame.effacer();
                    frame.add(new PanelTitulaire(frame, selection, personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                    break;
                default:
                    throw new AssertionError();
            }
        });
        panelBas.add(texte, BorderLayout.NORTH);
        panelBas.add(boutonsPanel, BorderLayout.CENTER);

        this.add(panelBas, BorderLayout.CENTER);
    }
}
