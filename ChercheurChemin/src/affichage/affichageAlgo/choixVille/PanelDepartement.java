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
 * Cette classe represente un panneau permettant de selectionner des departements parmi une liste.
 * Elle permet d'ajouter, de valider et de supprimer des departements selectionnes pour un processus ulterieur.
 * @version 1.0
 */

public class PanelDepartement extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des departements.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelDepartement(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // En colonne
        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        panelTop.setPreferredSize(new Dimension(800, 35));
        this.add(panelTop, BorderLayout.NORTH);

        JPanel PanelBas = new JPanel(new BorderLayout());
        PanelBas.setBackground(Color.WHITE);
        this.add(PanelBas, BorderLayout.CENTER);

        List<String> ListDepartement = selection.stream()
        .map(Personne::getVille) // Recupere la ville de chaque personne
        .map(Ville::getDepartement) // Recupere le departement de la ville
        .map(String :: toLowerCase)
        .distinct() // Evite les doublons
        .toList();
        
        ArrayList<String> DepartementSelected = new ArrayList<>();

        // Panneau pour afficher les dep sélectionnées
        JPanel PanelDepSelect = new JPanel();
        PanelDepSelect.setLayout(new BoxLayout(PanelDepSelect, BoxLayout.Y_AXIS));
        PanelDepSelect.setBackground(Color.LIGHT_GRAY);
        PanelDepSelect.setPreferredSize(new Dimension(400, 550));
        PanelBas.add(PanelDepSelect, BorderLayout.EAST);

        JLabel selectionTitleLabel = new JLabel("Les départements selectionnés sont :");
        selectionTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectionTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PanelDepSelect.add(selectionTitleLabel);

        final Runnable[] updateSelectedDept = new Runnable[1]; 
        //variables locales doivent être effectivement finales pour être utilisées dans une lambda ou une classe anonyme donc pas modifiable une fois définie.
        //Tableaux  exception, référence reste finale, mais leur contenu est mutable. 

        // Méthode locale pour mettre à jour les noms sélectionnées
        updateSelectedDept[0] = () -> {
            PanelDepSelect.removeAll(); // Supprime les anciens noms affichées
            PanelDepSelect.add(selectionTitleLabel);
            for (String dep : DepartementSelected) {
                JPanel nomPanel = new JPanel();
                nomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                nomPanel.setBackground(Color.LIGHT_GRAY);

                JLabel nomLabel = new JLabel(dep);
                JButton suppButton = new JButton("Supprimer");
                suppButton.addActionListener(e -> {
                    DepartementSelected.remove(dep); // Supprime le nom de la liste
                    updateSelectedDept[0].run(); // Rafraîchit l'affichage
                });

                nomPanel.add(nomLabel);
                nomPanel.add(suppButton);
                PanelDepSelect.add(nomPanel);
            }

            PanelDepSelect.revalidate(); // Met à jour la disposition
            PanelDepSelect.repaint();   // Rafraîchit l'affichage
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
       
        addButton.addActionListener(e -> {
            String DepartementToAdd = textField.getText().trim().toLowerCase();
            if (!DepartementToAdd.isEmpty() && ListDepartement.contains(DepartementToAdd)) {
                if(!DepartementSelected.contains(DepartementToAdd)){
                    DepartementSelected.add(DepartementToAdd);
                    textField.setText("");
                    suggestionsModel.clear();
                    updateSelectedDept[0].run();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Ce département est déjà sélectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            } else if (DepartementToAdd.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un département.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Ce département n'est pas disponible dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        valider.addActionListener(e-> {
            if(DepartementSelected.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Pas de département selectionné", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else{
                frame.effacer();
                frame.add(new PanelValiderSelection(frame, Trie.TrieDepartement(selection, DepartementSelected), personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            }
        });
        Retour.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelVille(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
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
                    ListDepartement.stream()
                        .filter(departement -> departement.toLowerCase().startsWith(text))
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
             * Met a jour les suggestions en affichant toutes les departements disponibles lorsque le champ texte est focalise.
             * @param e L'événement de focus.
             */
            @Override
            public void focusGained(FocusEvent e) {
                suggestionsModel.clear();
                ListDepartement.forEach(suggestionsModel::addElement); 
            }
        });
        // Double clic sur une suggestion
        suggestionsList.addMouseListener(new MouseAdapter() {
            /**
             * Permet de selectionner un departement au double-clic dans la liste des suggestions.
             * @param evt L'evenement de souris.
             */
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double-clique
                    String dep = suggestionsList.getSelectedValue();
                    if (dep != null) {
                        textField.setText(dep);
                    }
                }
            }
        });
    }
}
