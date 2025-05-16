package affichage.affichageAlgo.choixPersonne;

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
import verificationEntree.Coherence;

/**
 * Cette classe represente un panneau permettant de selectionner des ids parmi une liste.
 * Elle permet d'ajouter, de valider et de supprimer des ids selectionnees pour un processus ulterieur.
 * @version 1.0
 */

public class PanelID extends JPanel{
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des ids.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelID(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // En colonne
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        this.add(panelTop, BorderLayout.NORTH);

        JPanel PanelBas = new JPanel(new BorderLayout());
        PanelBas.setBackground(Color.WHITE);
        this.add(PanelBas, BorderLayout.CENTER);

        List<String> ListID = selection.stream()
        .map(personne -> String.valueOf(personne.getID())) // Recupere les id chaque personne
        .distinct() // Evite les doublons
        .toList();
        
        ArrayList<Integer> IDSelected = new ArrayList<>();

        // Panneau pour afficher les ids sélectionnees
        JPanel PanelIDSelect = new JPanel();
        PanelIDSelect.setLayout(new BoxLayout(PanelIDSelect, BoxLayout.Y_AXIS));
        PanelIDSelect.setBackground(Color.LIGHT_GRAY);
        PanelIDSelect.setPreferredSize(new Dimension(400, 550));
        PanelBas.add(PanelIDSelect, BorderLayout.EAST);

        JLabel selectionTitleLabel = new JLabel("Les IDs selectionnés sont :");
        selectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectionTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PanelIDSelect.add(selectionTitleLabel);

        final Runnable[] updateSelectedId = new Runnable[1]; 
        //variables locales doivent être effectivement finales pour être utilisées dans une lambda ou une classe anonyme donc pas modifiable une fois définie.
        //Tableaux  exception, référence reste finale, mais leur contenu est mutable. 

        // Méthode locale pour mettre à jour les noms sélectionnées
        updateSelectedId[0] = () -> {
            PanelIDSelect.removeAll(); // Supprime les anciens noms affichées
            PanelIDSelect.add(selectionTitleLabel);
            for (Integer id : IDSelected) {
                JPanel nomPanel = new JPanel();
                nomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                nomPanel.setBackground(Color.LIGHT_GRAY);

                JLabel nomLabel = new JLabel(String.valueOf(id));
                JButton suppButton = new JButton("Supprimer");
                suppButton.addActionListener(e -> {
                    IDSelected.remove(id); // Supprime le nom de la liste
                    updateSelectedId[0].run(); // Rafraichit l'affichage
                });

                nomPanel.add(nomLabel);
                nomPanel.add(suppButton);
                PanelIDSelect.add(nomPanel);
            }

            PanelIDSelect.revalidate(); // Met a jour la disposition
            PanelIDSelect.repaint();   // Rafraichit l'affichage
        };

        //Zone de saisit de texte
        JTextField textField = new JTextField();
        PanelBas.add(textField, BorderLayout.NORTH);

        // Modele et liste des suggestions
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
        addButton.addActionListener(e-> {
            String IDToAdd =textField.getText().trim();
            if (!IDToAdd.isEmpty() && ListID.contains(IDToAdd)) {
            	int idToAdd = Coherence.verificationEntier(IDToAdd);
            	
                if(!IDSelected.contains(idToAdd)){
                    IDSelected.add(idToAdd);
                    textField.setText("");
                    suggestionsModel.clear();
                    updateSelectedId[0].run();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Cet ID est déjà sélectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            } else if (IDToAdd.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un ID.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Cet ID n'est pas disponible dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        valider.addActionListener(e-> {
            if(IDSelected.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Pas d'ID selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else{
                frame.effacer();
                frame.add(new PanelValiderSelection(frame, Trie.TrieID(selection, IDSelected), personnes, villes, regions));
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
                    ListID.stream()
                        .filter(id -> id.toLowerCase().startsWith(text))
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
             * Met a jour les suggestions en affichant toutes les ids disponibles lorsque le champ texte est focalise.
             * @param e L'événement de focus.
             */
            @Override
            public void focusGained(FocusEvent e) {
                suggestionsModel.clear();
                ListID.forEach(suggestionsModel::addElement);
            }
        });
        // Double clic sur une suggestion
        suggestionsList.addMouseListener(new MouseAdapter() {
            /**
             * Permet de selectionner un id au double-clic dans la liste des suggestions.
             * @param evt L'evenement de souris.
             */
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-clique
                    String IDselected = suggestionsList.getSelectedValue();
                    if (IDselected != null) {
                        textField.setText(IDselected);
                    }
                }
            }
        });
    }
}