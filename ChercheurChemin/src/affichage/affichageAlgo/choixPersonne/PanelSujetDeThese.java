package affichage.affichageAlgo.choixPersonne;

import affichage.affichageAlgo.PanelValiderSelection;
import affichage.Frame;
import affichage.PanelTop;
import filtrage.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import peuple.*;
import territoire.*;

/**
 * Cette classe represente un panneau permettant de selectionner des sujets de these parmi une liste.
 * Elle permet d'ajouter, de valider et de supprimer des sujets de these selectionnees pour un processus ulterieur.
 * @version 1.0
 */


public class PanelSujetDeThese extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des sujets.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelSujetDeThese(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // En colonne
        if (Trie.convertionEtudiants(selection).isEmpty()) {
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

            JPanel PanelBas = new JPanel(new BorderLayout());
            PanelBas.setBackground(Color.WHITE);
            this.add(PanelBas, BorderLayout.CENTER);
            ArrayList<Etudiant> etud = Trie.convertionEtudiants(selection);
            List<String> ListSujet = etud.stream()
            .map(Etudiant::getSujetDeThese) // Récupère les de thèse de chaque de chaque personne
            .map(String :: toLowerCase)
            .distinct() // Évite les doublons
            .toList();
            
            ArrayList<String> SujetSelected = new ArrayList<>();

            // Panneau pour afficher les sujets sélectionnées
            JPanel PanelSujetSelect = new JPanel();
            PanelSujetSelect.setLayout(new BoxLayout(PanelSujetSelect, BoxLayout.Y_AXIS));
            PanelSujetSelect.setBackground(Color.LIGHT_GRAY);
            PanelSujetSelect.setPreferredSize(new Dimension(400, 550));
            PanelBas.add(PanelSujetSelect, BorderLayout.EAST);

            JLabel selectionTitleLabel = new JLabel("Les sujets sélectionnés sont :");
            selectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            selectionTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            PanelSujetSelect.add(selectionTitleLabel);

            final Runnable[] updateSelectedSujet = new Runnable[1]; 
            //variables locales doivent être effectivement finales pour être utilisées dans une lambda ou une classe anonyme donc pas modifiable une fois définie.
            //Tableaux  exception, référence reste finale, mais leur contenu est mutable. 

            updateSelectedSujet[0] = () -> {
                PanelSujetSelect.removeAll(); // Supprime les anciens noms affichées
                PanelSujetSelect.add(selectionTitleLabel);
                for (String sujet : SujetSelected) {
                    JPanel sujetPanel = new JPanel();
                    sujetPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    sujetPanel.setBackground(Color.LIGHT_GRAY);

                    JLabel sujetLabel = new JLabel(sujet);
                    JButton suppButton = new JButton("Supprimer");
                    suppButton.addActionListener(e -> {
                        SujetSelected.remove(sujet); // Supprime le sujet de la liste
                        updateSelectedSujet[0].run(); // Rafraîchit l'affichage
                    });
                    sujetPanel.add(sujetLabel);
                    sujetPanel.add(suppButton);
                    PanelSujetSelect.add(sujetPanel);
                }
                PanelSujetSelect.revalidate(); // Met a jour la disposition
                PanelSujetSelect.repaint();   // Rafraichit l'affichage
            };


            JTextField textField = new JTextField();
            PanelBas.add(textField, BorderLayout.NORTH);

            // Modèle et liste des suggestions
            DefaultListModel<String> suggestionsModel = new DefaultListModel<>();
            JList<String> suggestionsList = new JList<>(suggestionsModel);
            suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            PanelBas.add(new JScrollPane(suggestionsList), BorderLayout.CENTER);

            JPanel PanelBouton=new JPanel();
            PanelBouton.setBackground(Color.WHITE);
            PanelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
            JButton addButton = new JButton("Ajouter");
            JButton valider = new JButton("Valider");
            JButton Retour = new JButton("Retour");
            PanelBouton.add(addButton);
            PanelBouton.add(valider);
            PanelBouton.add(Retour);
            PanelBas.add(PanelBouton, BorderLayout.SOUTH);
            
            Retour.addActionListener(e->{
                frame.effacer();
                frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            });
            addButton.addActionListener(e -> {
                String SujetToAdd = textField.getText().trim().toLowerCase();
                if (!SujetToAdd.isEmpty() && ListSujet.contains(SujetToAdd)) {
                    if(!SujetSelected.contains(SujetToAdd)){
                        SujetSelected.add(SujetToAdd);
                        textField.setText("");
                        suggestionsModel.clear();
                        updateSelectedSujet[0].run();
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Ce sujet est déjà selectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (SujetToAdd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un sujet.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Ce sujet n'est pas disponible dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            valider.addActionListener(e-> {
                if(SujetSelected.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Pas de sujet selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    frame.effacer();
                    frame.add(new PanelValiderSelection(frame, Trie.TrieSujetdeThese(selection, SujetSelected), personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                }
            });

            // Écouteur pour la saisie dans le champ texte
            textField.getDocument().addDocumentListener(new DocumentListener() {
                /**
                 * Met a jour la liste des suggestions en fonction du texte saisi dans le champ de recherche.
                 */
                private void updateSuggestions() {
                    suggestionsModel.clear();
                    String text = textField.getText().trim().toLowerCase();
                    if (!text.isEmpty()) {
                        ListSujet.stream()
                            .filter(sujet -> sujet.toLowerCase().startsWith(text))
                            .forEach(suggestionsModel::addElement);
                    }
                }
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateSuggestions();
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateSuggestions();
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    // Rien à faire ici
                }
            });
             // Affichage initial de toutes les suggestion lors du clic sur le champ texte
            textField.addFocusListener(new FocusAdapter() {
                /**
                 * Met a jour les suggestions en affichant toutes les sujets disponibles lorsque le champ texte est focalise.
                 * @param e L'événement de focus.
                 */
                @Override
                public void focusGained(FocusEvent e) {
                    suggestionsModel.clear();
                    ListSujet.forEach(suggestionsModel::addElement); 
                }
            });
            // Double clic sur une suggestion
            suggestionsList.addMouseListener(new MouseAdapter() {
                /**
                 * Permet de selectionner un sujet au double-clic dans la liste des suggestions.
                 * @param evt L'evenement de souris.
                 */
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) { // Double-clique
                        String nomSelected = suggestionsList.getSelectedValue();
                        if (nomSelected != null) {
                            textField.setText(nomSelected);
                        }
                    }
                }
            });
        }
    }
}