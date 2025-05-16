package affichage.affichageAlgo;

import affichage.affichageAlgo.choixPersonne.PanelPersonne;
import affichage.Frame;
import affichage.PanelTop;
import filtrage.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.*;
import territoire.Region;
import territoire.Ville;

/**
 * Cette classe represente un panneau permettant à l'utilisateur de saisir deux nombres : un minimum et un maximum.
 * Ces nombres peuvent correspondre a differentes choses en fonction des parametres du constructeur.
 * @version 1.0
 */

public class PanelNombre extends JPanel{
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de nombre.
     * @param frame Fenetre principale de l'application.
     * @param nom Nom du critere de selection (e.g., "population", "superficie").
     * @param choix Choix si pour le critere plusieurs options sont possibles (e.g., "ville", "region").
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des régions disponibles.
     */
    public PanelNombre(Frame frame, String nom, String choix, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); //en colonne

        if(nom.equals("nombreetudiant")&& Trie.convertionTitulaires(selection).isEmpty()){ 
            int result = JOptionPane.showOptionDialog(frame,"Erreur : aucun étudiant dans la sélection","Erreur",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,new Object[]{"OK"}, "OK");
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

            JPanel panelBas = new JPanel(new BorderLayout());
            panelBas.setBackground(Color.WHITE);
            panelBas.setLayout(new GridLayout(3, 2, 10, 10)); // Grille avec 3 lignes, 2 colonnes, espace de 10px

            JLabel minLabel = new JLabel("Entrez le minimum :");
            JTextField minField = new JTextField();
            JLabel maxLabel = new JLabel("Entrez le maximum :");
            JTextField maxField = new JTextField();
            JButton validateButton = new JButton("Valider");
            JButton Retour = new JButton("Retour");

            panelBas.add(minLabel);
            panelBas.add(minField);
            panelBas.add(maxLabel);
            panelBas.add(maxField);
            panelBas.add(Retour);
            panelBas.add(validateButton);

            validateButton.addActionListener(e-> {
                    try {
                        double min = Integer.parseInt(minField.getText());
                        double max = Integer.parseInt(maxField.getText());
                        // Validation des contraintes
                        if (min > max) {
                            JOptionPane.showMessageDialog(frame, "Erreur : Le minimum doit être inférieur ou égal au maximum.", "Erreur", JOptionPane.ERROR_MESSAGE); 
                        } 
                        else{
                            switch (nom.toLowerCase()) {
                                case "population" -> {
                                    if(choix.equals("Ville")){
                                        if(Trie.TriePopulationVille(selection, min, max).isEmpty()){
                                            JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else{
                                            frame.effacer();
                                            frame.add(new PanelValiderSelection(frame, Trie.TriePopulationVille(selection, min, max), personnes, villes, regions));
                                            frame.revalidate();
                                            frame.repaint();
                                        }
                                    }
                                    else {
                                        if(Trie.TriePopulationRegion(selection, min, max).isEmpty()){
                                            JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else{
                                            frame.effacer();
                                            frame.add(new PanelValiderSelection(frame, Trie.TriePopulationRegion(selection, min, max), personnes, villes, regions));
                                            frame.revalidate();
                                            frame.repaint();
                                        }
                                    }
                                }
                                case "superficie" -> {
                                    if(choix.equals("Ville")){
                                        if(Trie.TrieSuperficieVille(selection, min, max).isEmpty()){
                                            JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else{
                                            frame.effacer();
                                            frame.add(new PanelValiderSelection(frame, Trie.TrieSuperficieVille(selection, min, max), personnes, villes, regions));
                                            frame.revalidate();
                                            frame.repaint();
                                        }

                                    }
                                    else {
                                        if(Trie.TrieSuperficieRegion(selection, min, max).isEmpty()){
                                            JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                        }
                                        else{
                                            frame.effacer();
                                            frame.add(new PanelValiderSelection(frame, Trie.TrieSuperficieRegion(selection, min, max), personnes, villes, regions));
                                            frame.revalidate();
                                            frame.repaint();
                                        }
                                    }
                                }
                                case "age" -> {
                                    if(Trie.TrieAge(selection, min, max).isEmpty()){
                                        JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    }
                                    else{
                                        frame.effacer();
                                        frame.add(new PanelValiderSelection(frame, Trie.TrieAge(selection, min, max), personnes, villes, regions));
                                        frame.revalidate();
                                        frame.repaint();
                                    }
                                }
                                case "nombreetudiant" -> {
                                    if(Trie.TrieNbrEtudiant(selection, min, max).isEmpty()){
                                        JOptionPane.showMessageDialog(frame, "Erreur : aucune personne sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
                                    }
                                    else{
                                        frame.effacer();
                                        frame.add(new PanelValiderSelection(frame, Trie.TrieNbrEtudiant(selection, min, max), personnes, villes, regions));
                                        frame.revalidate();
                                        frame.repaint();
                                    }
                                }
                                default -> {
                                }
                            }
                        }
                    } catch (NumberFormatException ex) {
                    	String message = "Erreur : veuillez entrer des nombres entiers valides.";
                        JOptionPane.showMessageDialog(frame, message, "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
            });
            Retour.addActionListener(e->{
                switch (nom.toLowerCase()) {
                    case "population" -> {
                        frame.effacer();
                        frame.add(new PanelChoix(frame, "Ville", "Region", "population",null, null, selection, personnes, villes, regions));
                        frame.revalidate();
                        frame.repaint();
                    }
                    case "superficie" -> {
                        frame.effacer();
                        frame.add(new PanelChoix(frame, "Ville", "Region", "superficie",null, null, selection, personnes, villes, regions));
                        frame.revalidate();
                        frame.repaint();
                    }
                    case "age" -> {
                        frame.effacer();
                        frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
                        frame.revalidate();
                        frame.repaint();
                    }
                    case "nombreetudiant" -> {
                        frame.effacer();
                        frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
                        frame.revalidate();
                        frame.repaint();
                    }
                    default -> {
                    }
                }
            });

            this.add(panelBas, BorderLayout.CENTER);
        }
    }
}
