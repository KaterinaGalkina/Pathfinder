package affichage.affichageAlgo.choixVille;
import affichage.affichageAlgo.PanelValiderSelection;
import filtrage.Trie;
import affichage.Frame;
import affichage.PanelTop;
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
 * Cette classe represente un panneau permettant de selectionner des villes parmi une liste.
 * Elle permet d'ajouter, de valider et de supprimer des villes selectionnees pour un processus ulterieur.
 * @version 1.0
 */

public class PanelNomVille extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des villes.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelNomVille(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // Organisation en colonnes
        
        // Panel du haut
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        // Panel du bas
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBackground(Color.WHITE);
        this.add(panelBas, BorderLayout.CENTER);

        // Recuperation des noms de villes disponibles
        List<String> listVilles = selection.stream()
        .map(Personne::getVille) // Recupere la ville de chaque personne
        .map(Ville::getNom)     // Recupere le nom de la ville
        .distinct()             // Evite les doublons
        .toList();

        ArrayList<String> villeSelected = new ArrayList<>();

        // Panel pour afficher les villes selectionnees
        JPanel panelVilleSelect = new JPanel();
        panelVilleSelect.setLayout(new BoxLayout(panelVilleSelect, BoxLayout.Y_AXIS));
        panelVilleSelect.setBackground(Color.LIGHT_GRAY);
        panelVilleSelect.setPreferredSize(new Dimension(400, 550));
        panelBas.add(panelVilleSelect, BorderLayout.EAST);

        JLabel selectionTitleLabel = new JLabel("Les villes sélectionnées sont :");
        selectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectionTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVilleSelect.add(selectionTitleLabel);

        final Runnable[] updateSelectedCities = new Runnable[1]; 
        //variables locales doivent être effectivement finales pour être utilisées dans une lambda ou une classe anonyme donc pas modifiable une fois définie.
        //Tableaux  exception, référence reste finale, mais leur contenu est mutable. 

        // Initialise updateSelectedCities
        updateSelectedCities[0] = () -> {
            panelVilleSelect.removeAll(); // Supprime les anciennes villes affichees
            panelVilleSelect.add(selectionTitleLabel);
            for (String ville : villeSelected) {
                JPanel villePanel = new JPanel();
                villePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                villePanel.setBackground(Color.LIGHT_GRAY);

                JLabel villeLabel = new JLabel(ville);
                JButton suppButton = new JButton("Supprimer");
                suppButton.addActionListener(e -> {
                    villeSelected.remove(ville); // Supprime la ville de la liste
                    updateSelectedCities[0].run(); // Rafraichit l'affichage
                });

                villePanel.add(villeLabel);
                villePanel.add(suppButton);
                panelVilleSelect.add(villePanel);
            }

            panelVilleSelect.revalidate(); // Met à jour la disposition
            panelVilleSelect.repaint();   // Rafraîchit l'affichage
        };

        // Champ de saisie pour ajouter une ville
        JTextField textField = new JTextField();
        panelBas.add(textField, BorderLayout.NORTH);

        // Modèle et liste des suggestions
        DefaultListModel<String> suggestionsModel = new DefaultListModel<>();
        JList<String> suggestionsList = new JList<>(suggestionsModel);
        suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panelBas.add(new JScrollPane(suggestionsList), BorderLayout.CENTER);

        // Panel des boutons
        JPanel panelBouton = new JPanel();
        panelBouton.setBackground(Color.WHITE);
        panelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Ajouter");
        JButton validerButton = new JButton("Valider");
        JButton retourButton = new JButton("Retour");
        panelBouton.add(addButton);
        panelBouton.add(validerButton);
        panelBouton.add(retourButton);
        panelBas.add(panelBouton, BorderLayout.SOUTH);

        // Bouton pour ajouter une ville
        addButton.addActionListener(e -> {
            String villeToAdd = textField.getText().trim().toUpperCase();
            if (!villeToAdd.isEmpty() && listVilles.contains(villeToAdd)) {
                if (!villeSelected.contains(villeToAdd.toLowerCase())) { // Verifie si la ville est déjà sélectionnée
                    villeSelected.add(villeToAdd.toLowerCase());
                    textField.setText("");
                    suggestionsModel.clear();
                    updateSelectedCities[0].run(); // Met a jour les villes sélectionnées
                } 
                else {
                    JOptionPane.showMessageDialog(frame, "Cette ville est déjà sélectionnée.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
            else if (villeToAdd.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un nom de ville.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } 
            else {
                JOptionPane.showMessageDialog(frame, "Cette ville n'est pas disponible dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton pour valider la sélection
        validerButton.addActionListener(e -> {
            if (villeSelected.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Pas de ville sélectionnée", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                frame.effacer();
                frame.add(new PanelValiderSelection(frame, Trie.TrieVille(selection, villeSelected), personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            }
        });

        // Bouton pour retourner au panneau precedent
        retourButton.addActionListener(e -> {
            frame.effacer();
            frame.add(new PanelVille(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });

        // Mise a jour des suggestions lors de la saisie
        textField.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Met a jour la liste des suggestions en fonction du texte saisi dans le champ de recherche.
             */
            private void updateSuggestions() {
                suggestionsModel.clear();
                String text = textField.getText().trim().toLowerCase();
                if (!text.isEmpty()) {
                    listVilles.stream()
                            .filter(ville -> ville.toLowerCase().startsWith(text))
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

        // Affichage initial des suggestions lors du clic sur le champ texte
        textField.addFocusListener(new FocusAdapter() {
            /**
             * Met a jour les suggestions en affichant toutes les villes disponibles lorsque le champ texte est focalise.
             * @param e L'événement de focus.
             */
            @Override
            public void focusGained(FocusEvent e) {
                suggestionsModel.clear();
                listVilles.forEach(suggestionsModel::addElement);
            }
        });

        // Double-clic sur une suggestion
        suggestionsList.addMouseListener(new MouseAdapter() {
            /**
             * Permet de selectionner une ville au double-clic dans la liste des suggestions.
             * @param evt L'evenement de souris.
             */
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-clic
                    String selectedVille = suggestionsList.getSelectedValue();
                    if (selectedVille != null) {
                        textField.setText(selectedVille);
                    }
                }
            }
        });
    }
}
