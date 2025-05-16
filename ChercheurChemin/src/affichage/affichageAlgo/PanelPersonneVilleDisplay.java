package  affichage.affichageAlgo;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.*;
import peuple.*;
import territoire.*;

/**
 * Cette classe represente le panneau d'affichage des personnes selectionnee et des villes sur le paneau fin.
 * @version 1.0
 */

public class PanelPersonneVilleDisplay extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du panel pour afficher les personnes a visiter et le chemin a emprunter.
     * @param selection Liste des personnes a visiter.
     * @param villeAvisiter Liste des ville a visiter.
     */
    public PanelPersonneVilleDisplay(ArrayList<Personne> selection, ArrayList<Ville> villeAvisiter) {
        this.setLayout(new BorderLayout());

        // Panel pour les details de la personne (droite)
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(Color.WHITE);
        JScrollPane detailScrollPane = new JScrollPane(detailPanel);
        this.add(detailScrollPane, BorderLayout.CENTER);

        // Panel pour la liste des noms (gauche)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Personne p : selection) {
            listModel.addElement(p.getNom() + " " + p.getPrenom());
        }
        JList<String> nameList = new JList<>(listModel);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane nameScrollPane = new JScrollPane(nameList);
        nameScrollPane.setPreferredSize(new Dimension(200, 0));
        this.add(nameScrollPane, BorderLayout.WEST);

        // Affichage des détails lors du clic sur un nom
        nameList.addMouseListener(new MouseAdapter() {
            /**
             * Gere l'affichage des details d'une personne lorsqu'un utilisateur clique sur son nom dans la liste.
             * Cette methode est declenchee par un evenement de clic de souris sur la liste des noms de personnes.
             *
             * @param e l'evenement de clic de souris.
             *          Permet de recuperer l'indice de l'element selectionne dans la liste.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = nameList.getSelectedIndex();
                if (index != -1) {
                    Personne selectedPerson = selection.get(index);
                    detailPanel.removeAll();

                    // Affichage des informations communes
                    detailPanel.add(new JLabel("Nom : " + selectedPerson.getNom()));
                    detailPanel.add(new JLabel("Prénom : " + selectedPerson.getPrenom()));
                    detailPanel.add(new JLabel("ID : " + selectedPerson.getID()));
                    detailPanel.add(new JLabel("Ville : " + selectedPerson.getVille().getNom()));
                    detailPanel.add(new JLabel("Age : " + selectedPerson.getAge()));

                    // Affichage des informations spécifiques selon le type
                    if (selectedPerson instanceof Titulaire titulaire) {
                        detailPanel.add(new JLabel("Numero de Bureau : " + titulaire.getNumBureau()));
                        detailPanel.add(new JLabel("Disciplines : " + titulaire.getDisciplines()));
                    } 
                    if (selectedPerson instanceof Chercheur chercheur) {
                        detailPanel.add(new JLabel("Etudiants : "));
                        Set<Etudiant> etudiants = chercheur.getEtudiantsSet();
                        for (Etudiant etudiant : etudiants) {
                            detailPanel.add(new JLabel("- " + etudiant.getNom() + " " + etudiant.getPrenom()));
                        }
                    }
                    if (selectedPerson instanceof MCF mcf) {
                        detailPanel.add(new JLabel("Etudiant encadre : " + mcf.getEtudiant().getNomPrenom()));
                    }
                    if (selectedPerson instanceof Etudiant etudiant) {
                        detailPanel.add(new JLabel("Sujet de These : " + etudiant.getSujetDeThese()));
                        detailPanel.add(new JLabel("Discipline : " + etudiant.getDiscipline()));
                        detailPanel.add(new JLabel("Annee de These : " + etudiant.getAnneeDeThese()));
                        detailPanel.add(new JLabel("Encadrant : " + etudiant.getEncadrant().getNom() + " " + etudiant.getEncadrant().getPrenom()));
                    }

                    detailPanel.revalidate();
                    detailPanel.repaint();
                }
            }
        });

        // Panel pour les villes (bas)
        JPanel villePanel = new JPanel();
        villePanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Affichage sur une seule ligne
        villePanel.setBackground(Color.LIGHT_GRAY);

        StringBuilder villeText = new StringBuilder();
        for (int i = 0; i < villeAvisiter.size(); i++) {
            villeText.append(villeAvisiter.get(i).getNom());
            if (i < villeAvisiter.size() - 1) {
                villeText.append(" -> ");
            }
        }
        JLabel villeLabel = new JLabel(villeText.toString());
        villePanel.add(villeLabel);

        // Utilisation d'un panel intermediaire pour la taille dynamique
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(villePanel, BorderLayout.WEST); // Positionne le contenu a gauche
        wrapperPanel.setPreferredSize(villePanel.getPreferredSize()); // S'assure que la taille suit le contenu

        // Ajouter le panel des villes dans un JScrollPane
        JScrollPane villeScrollPane = new JScrollPane(wrapperPanel);
        villeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        villeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Fixer la hauteur du JScrollPane pour eviter les coupures
        villeScrollPane.setPreferredSize(new Dimension(800, 60));

        // Ajouter le JScrollPane en bas du panel principal
        this.add(villeScrollPane, BorderLayout.SOUTH);



    }
}