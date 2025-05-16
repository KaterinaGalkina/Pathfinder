package affichage.affichageAlgo.choixPersonne;

import affichage.affichageAlgo.PanelValiderSelection;
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
 * Cette classe represente un panneau permettant de selectionner des noms parmi une liste.
 * Elle permet d'ajouter, de valider et de supprimer des Noms selectionnees pour un processus ulterieur.
 * @version 1.0
 */

public class PanelNom extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des noms.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelNom(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // En colonne
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        JPanel PanelBas = new JPanel(new BorderLayout());
        PanelBas.setBackground(Color.WHITE);
        this.add(PanelBas, BorderLayout.CENTER);

        List<String> ListNom = selection.stream()
        .map(Personne::getNomPrenom) // Récupère les nom prenom de chaque personne
        .map(String :: toLowerCase)
        .distinct() // Évite les doublons
        .toList();
        
        ArrayList<String> NomSelected = new ArrayList<>();

        // Panneau pour afficher les noms sélectionnées
        JPanel PanelNomSelect = new JPanel();
        PanelNomSelect.setLayout(new BoxLayout(PanelNomSelect, BoxLayout.Y_AXIS));
        PanelNomSelect.setBackground(Color.LIGHT_GRAY);
        PanelNomSelect.setPreferredSize(new Dimension(400, 550));
        PanelBas.add(PanelNomSelect, BorderLayout.EAST);

        // Ajout du label au-dessus des noms sélectionnées
        JLabel selectionTitleLabel = new JLabel("Les personnes selectionnées sont :");
        selectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectionTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PanelNomSelect.add(selectionTitleLabel);

        final Runnable[] updateSelectednom = new Runnable[1]; 
        //variables locales doivent être effectivement finales pour être utilisées dans une lambda ou une classe anonyme donc pas modifiable une fois définie.
        //Tableaux  exception, référence reste finale, mais leur contenu est mutable. 

        // Méthode locale pour mettre à jour les noms sélectionnées
        updateSelectednom[0] = () -> {
            PanelNomSelect.removeAll(); // Supprime les anciens noms affichées
            PanelNomSelect.add(selectionTitleLabel);
            for (String nom : NomSelected) {
                JPanel nomPanel = new JPanel();
                nomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                nomPanel.setBackground(Color.LIGHT_GRAY);

                JLabel nomLabel = new JLabel(nom);
                JButton suppButton = new JButton("Supprimer");
                suppButton.addActionListener(e -> {
                    NomSelected.remove(nom); // Supprime le nom de la liste
                    updateSelectednom[0].run(); // Rafraîchit l'affichage
                });

                nomPanel.add(nomLabel);
                nomPanel.add(suppButton);
                PanelNomSelect.add(nomPanel);
            }

            PanelNomSelect.revalidate(); // Met à jour la disposition
            PanelNomSelect.repaint();   // Rafraîchit l'affichage
        };

        //Zone de saisit de texte
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
            String NomToAdd = textField.getText().trim().toLowerCase();
            if (!NomToAdd.isEmpty() && ListNom.contains(NomToAdd)) {
                if(!NomSelected.contains(NomToAdd)){
                    NomSelected.add(NomToAdd.toLowerCase());
                    textField.setText("");
                    suggestionsModel.clear();
                    updateSelectednom[0].run();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Cette personne est déjà sélectionnée.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            } else if (NomToAdd.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un nom prenom.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Cette personne n'est pas disponible dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        valider.addActionListener(e-> {
            if(NomSelected.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Pas de nom selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else{
                frame.effacer();
                frame.add(new PanelValiderSelection(frame, filtrage.Trie.TrieNomPrenom(selection, NomSelected), personnes, villes, regions));
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
                    ListNom.stream()
                        .filter(nom -> nom.toLowerCase().startsWith(text))
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
             * Met a jour les suggestions en affichant toutes les noms disponibles lorsque le champ texte est focalise.
             * @param e L'événement de focus.
             */
            @Override
            public void focusGained(FocusEvent e) {
                suggestionsModel.clear();
                ListNom.forEach(suggestionsModel::addElement); 
            }
        });
        // Double clic sur une suggestion
        suggestionsList.addMouseListener(new MouseAdapter() {
            /**
             * Permet de selectionner un nom au double-clic dans la liste des suggestions.
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