package affichage.affichageAlgo;

import affichage.Frame;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import peuple.*;
import territoire.*;

/**
 * Cette classe represente un panneau permettant de selectionner la ville de depart pour un itineraire.
 * Elle propose une interface avec un champ de texte, des suggestions dynamiques et des options de navigation.
 * @version 1.0
 */
public class PanelVilleDepart extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection de la ville de depart.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelVilleDepart(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout());

        // Titre en haut
        JLabel label = new JLabel("Veuillez sélectionner votre ville de départ :", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(label, BorderLayout.NORTH);

        // Centre avec champ texte et suggestions
        JPanel centerPanel = new JPanel(new BorderLayout());
        this.add(centerPanel, BorderLayout.CENTER);

        // Liste des villes disponibles
        List<String> listVilles = villes.stream()
                .map(Ville::getNom) // Recupere les noms des villes
                .distinct() // Evite les doublons
                .toList();

        JTextField textField = new JTextField();
        centerPanel.add(textField, BorderLayout.NORTH);

        DefaultListModel<String> suggestionsModel = new DefaultListModel<>();
        JList<String> suggestionsList = new JList<>(suggestionsModel);
        suggestionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        centerPanel.add(new JScrollPane(suggestionsList), BorderLayout.CENTER);

        // Boutons en bas
        JPanel PanelBouton = new JPanel(new BorderLayout());
        PanelBouton.setBackground(Color.WHITE);
        PanelBouton.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton Retour = new JButton("Retour");
        JButton validerButton = new JButton("Valider");
        PanelBouton.add(validerButton);
        PanelBouton.add(Retour);
        this.add(PanelBouton, BorderLayout.SOUTH);

        validerButton.addActionListener(e -> {
            String villeSelected = textField.getText().trim().toUpperCase(); // Ville selectionnee
            if (listVilles.contains(villeSelected)) {
                Ville VillesDepart = null;
                for(Ville v : villes){
                    if(v.getNom().equals(villeSelected)){
                        VillesDepart=v;
                        break;
                    }
                }
                frame.effacer();
                frame.add(new PanelFin(frame, selection, VillesDepart, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une ville valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        Retour.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelAlgo(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });

        // Mise a jour des suggestions
        textField.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Met a jour la liste des suggestions en fonction du texte saisi.
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
        // Affichage initial de toutes les suggestion lors du clic sur le champ texte
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                suggestionsModel.clear();
                listVilles.forEach(suggestionsModel::addElement); 
            }
        });
        // Double clic sur une suggestion pour sélectionner une ville
        suggestionsList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double clic
                    String selectedVille = suggestionsList.getSelectedValue();
                    if (selectedVille != null) {
                        textField.setText(selectedVille);
                    }
                }
            }
        });
    }
}
