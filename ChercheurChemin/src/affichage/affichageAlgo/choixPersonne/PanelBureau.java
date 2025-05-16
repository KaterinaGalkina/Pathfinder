package affichage.affichageAlgo.choixPersonne;

import affichage.affichageAlgo.PanelValiderSelection;
import affichage.Frame;
import affichage.PanelTop;
import filtrage.*;
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
import verificationEntree.Coherence;

/**
 * Cette classe represente un panneau permettant de selectionner des numero de bureau parmi une liste.
 * Elle permet d'ajouter, de valider et de supprimer des numeros de bureaux selectionnees pour un processus ulterieur.
 * @version 1.0
 */

public class PanelBureau extends JPanel{
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panneau de selection des numero de bureau.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelBureau(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions) {
        this.setLayout(new BorderLayout()); // En colonne

        ArrayList<Titulaire> titulaires = Trie.convertionTitulaires(selection);
        if (titulaires.isEmpty()) {
            int result = JOptionPane.showOptionDialog(frame,"Erreur : aucun titulaire dans la sélection","Erreur",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,new Object[]{"OK"}, "OK");
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

            List<String> ListNumBureau = titulaires.stream()
            .map(titulaire -> String.valueOf(titulaire.getNumBureau())) // Recupere les id chaque personne
            .distinct() // Évite les doublons
            .toList();
            
            ArrayList<Integer> NumBureauSelected = new ArrayList<>();

            // Panneau pour afficher les numero selectionnees
            JPanel PanelNumBureauSelect = new JPanel();
            PanelNumBureauSelect.setLayout(new BoxLayout(PanelNumBureauSelect, BoxLayout.Y_AXIS));
            PanelNumBureauSelect.setBackground(Color.LIGHT_GRAY);
            PanelNumBureauSelect.setPreferredSize(new Dimension(400, 550));
            PanelBas.add(PanelNumBureauSelect, BorderLayout.EAST);

            // Methode locale pour mettre a jour les numero selectionnees
            Runnable updateSelectedNumBureau = () -> {
                PanelNumBureauSelect.removeAll(); // Supprime les anciennes ids
                String listNumtexte = "<html>Les numeros de bureaux selectionnes sont :<br>";
                for (Integer id : NumBureauSelected) {
                    listNumtexte += "- " + id + "<br>";
                }
                listNumtexte += "</html>";
                JLabel t = new JLabel(listNumtexte);
                PanelNumBureauSelect.add(t);
                PanelNumBureauSelect.revalidate(); // Met a jour la disposition
                PanelNumBureauSelect.repaint();    // Rafraîchit l'affichage
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
            PanelBouton.add(Retour, BorderLayout.SOUTH);
            PanelBas.add(PanelBouton, BorderLayout.SOUTH);

            Retour.addActionListener(e->{
                frame.effacer();
                frame.add(new PanelPersonne(frame, selection, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            });
            
            addButton.addActionListener(e-> {
                String NumBureauToAdd =textField.getText().trim();
                if (!NumBureauToAdd.isEmpty() && ListNumBureau.contains(NumBureauToAdd)) {
                	int numBureauToAdd = Coherence.verificationEntier(NumBureauToAdd);
                    if(!NumBureauSelected.contains(numBureauToAdd)){
                       NumBureauSelected.add(numBureauToAdd);
                        textField.setText("");
                        suggestionsModel.clear();
                        updateSelectedNumBureau.run(); 
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "Ce numéro de bureau est déjà sélectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    }
                } else if (NumBureauToAdd.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner au moins un numéro de bureau.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Ce numéro de bureau n'est pas disponible dans la liste.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            valider.addActionListener(e-> {
                if(NumBureauSelected.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Pas de numéro de bureau selectionne", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    frame.effacer();
                    frame.add(new PanelValiderSelection(frame, Trie.TrieNumBureau(selection, NumBureauSelected), personnes, villes, regions));
                    frame.revalidate();
                    frame.repaint();
                }
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
                        ListNumBureau.stream()
                            .filter(numBureau -> numBureau.toLowerCase().startsWith(text))
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
                 * Met a jour les suggestions en affichant toutes les numeros de bureaux disponibles lorsque le champ texte est focalise.
                 * @param e L'événement de focus.
                 */
                @Override
                public void focusGained(FocusEvent e) {
                    suggestionsModel.clear();
                    ListNumBureau.forEach(suggestionsModel::addElement); 
                }
            });
            // Double clic sur une suggestion
            suggestionsList.addMouseListener(new java.awt.event.MouseAdapter() {
                /**
                 * Permet de selectionner un numero de bureau au double-clic dans la liste des suggestions.
                 * @param evt L'evenement de souris.
                 */
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) { // Double-clique
                        String Numselected = suggestionsList.getSelectedValue();
                        if (Numselected != null) {
                            textField.setText(Numselected);
                        }
                    }
                }
            });
        }
    }
}