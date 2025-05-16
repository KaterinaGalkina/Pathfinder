package affichage.affichageEcosysteme;

import javax.swing.*;

import peuple.*;

import java.awt.*;
import java.util.stream.Collectors;

/**
 * Permet a l'utilisateur d'observer toutes les informations concernant la personne selectionnee.
 */
public class InfoPanel {
	// Les sections qui peuvent changer en fonction de la focntion (mcf, chercheur, etudiant) de la personne selectionnee
    private JScrollPane scrollPane;
    private JPanel panelBas;
    private JLabel nomLabel;
    private JLabel nomValue;
    private JLabel prenomLabel;
    private JLabel prenomValue;
    private JLabel ageValue;
    private JLabel ageLabel;
    private JLabel villeLabel;
    private JLabel villeValue;
    private JLabel disciplinesLabel; // Ca peut changer si etudiant -> discipline sinon avec s
    private JLabel disciplinesValue;
    private JLabel anneeDeTheseOUnumBureauLabel;
    private JLabel anneeDeTheseOUnumBureauValue;
    private JLabel encadrantOUetudinatLabel;
    private JLabel encadrantOUetudinatValue;
    private JLabel sujetDeTheseLabel;
    private JLabel sujetDeTheseValue1;
    private JLabel sujetDeTheseValue2;

    /**
     * Constructeur pour creer un panel affichant toutes les informations disponibles 
     * sur une personne selectionnee dans la base de donnees.
     */
    public InfoPanel() {
        panelBas = new JPanel();
        panelBas.setLayout(new GridBagLayout());
        panelBas.setBorder(BorderFactory.createTitledBorder("Informations sélectionnées"));
        panelBas.setBackground(Color.WHITE);

        // Labels et champs pour afficher les informations
        nomLabel = new JLabel("Nom :");
        nomValue = new JLabel("-");
        prenomLabel = new JLabel("Prénom :");
        prenomValue = new JLabel("-");
        ageLabel = new JLabel("Âge :");
        ageValue = new JLabel("-");
        villeLabel = new JLabel("Ville :");
        villeValue = new JLabel("-");
        disciplinesLabel = new JLabel("Discipline :");
        disciplinesValue = new JLabel("-");
        anneeDeTheseOUnumBureauLabel = new JLabel("Année de thèse :"); // Par defaut on se trouve dans la table des etudiants donc au debut c'est annee de these
        anneeDeTheseOUnumBureauValue = new JLabel("-");
        encadrantOUetudinatLabel = new JLabel("Encadrant :");
        encadrantOUetudinatValue = new JLabel("-");
        sujetDeTheseLabel = new JLabel("Sujet de thèse :");
        sujetDeTheseValue1 = new JLabel(" - ");
        sujetDeTheseValue2 = new JLabel("");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel placeholder = new JLabel(" ".repeat(50)); // Label invisible pour fixer un peu la largeur
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(placeholder, gbc);

        // Ligne 1 : Nom et Prenom
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; 
        panelBas.add(nomLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST; 
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;  
        panelBas.add(nomValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST; 
        panelBas.add(prenomLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(prenomValue, gbc);

        // Ligne 2 : Age et Ville
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(ageValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(villeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(villeValue, gbc);

        // Ligne 3 : Discipline
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(disciplinesLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(disciplinesValue, gbc);

        // Ligne 4 : Annee de these
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(anneeDeTheseOUnumBureauLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(anneeDeTheseOUnumBureauValue, gbc);

        // Ligne 5 : Encadrant
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(encadrantOUetudinatLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(encadrantOUetudinatValue, gbc);

        // Ligne 6 : Sujet de these
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panelBas.add(sujetDeTheseLabel, gbc);

        gbc.gridx = 1;
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(sujetDeTheseValue1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panelBas.add(sujetDeTheseValue2, gbc);

        scrollPane = new JScrollPane(panelBas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Retourne le panel affichant toutes les informations disponibles 
     * sur une personne selectionnee dans la base de donnees.
     * Si certaines informations sont trop longues, l'utilisateur peut scroller.
     *
     * @return Le panel contenant les informations sur la personne selectionnee.
     */
    public JScrollPane getPanel() {
        return scrollPane;
    }

    private void reglerSujetDeTheseAffichage(String sujetDeThese){
        String partie1 = "";
        String partie2 = "";
        int n = "Sujet de these :".length();
        if (sujetDeThese.length() > n){
            String ligne1 = sujetDeThese.substring(0, n);
            int dernierEsp = ligne1.lastIndexOf(" ");

            // Pour ne pas couper un mot au milieu
            partie1 = sujetDeThese.substring(0, dernierEsp);
            partie2 = sujetDeThese.substring(dernierEsp);
        } else {
            partie1 = sujetDeThese;
        }
        sujetDeTheseValue1.setText(partie1);
        sujetDeTheseValue2.setText(partie2);

    }

    /**
     * Met a jour les informations affichees pour la personne selectionnee par l'utilisateur.
     *
     * @param pers La personne dont les informations doivent etre mises a jour.
     */
    public void mettreAjourLesInformations(Personne pers) {
        if (pers != null){
            nomValue.setText(pers.getNom());
            prenomValue.setText(pers.getPrenom());
            ageValue.setText(pers.getAge() + "");
            villeValue.setText(pers.getVille().getNomDep());
            disciplinesValue.setText(Discipline.setDisciplinesToString(pers.getDisciplines()));
            if (pers instanceof Etudiant) {
                disciplinesLabel.setText("Discipline :");
                Etudiant etudSelectionnee = (Etudiant)pers;
                anneeDeTheseOUnumBureauLabel.setText("Année de thèse :");
                anneeDeTheseOUnumBureauValue.setText(etudSelectionnee.getAnneeDeThese() + "");

                encadrantOUetudinatLabel.setText("Encadrant :");
                Titulaire encadrant = etudSelectionnee.getEncadrant();
                encadrantOUetudinatValue.setText(encadrant.getNomPrenom());

                sujetDeTheseLabel.setText("Sujet de thèse :");
                reglerSujetDeTheseAffichage(etudSelectionnee.getSujetDeThese());

            } else if (pers instanceof Titulaire){
                disciplinesLabel.setText("Disciplines :");
                Titulaire titulaireSelectionne = (Titulaire)pers;
                anneeDeTheseOUnumBureauLabel.setText("Numéro de Bureau :");
                anneeDeTheseOUnumBureauValue.setText(titulaireSelectionne.getNumBureau() + "");
                if (pers instanceof MCF mcf) {
                    encadrantOUetudinatLabel.setText("Étudiant :");
                    if (mcf.getEtudiant() != null){
                        encadrantOUetudinatValue.setText(mcf.getEtudiant().getNomPrenom());
                    } else {
                        encadrantOUetudinatValue.setText("");
                    }
                    reglerSujetDeTheseAffichage("");
                    
                } else if (pers instanceof Chercheur cherch){
                    encadrantOUetudinatLabel.setText("Étudiants :");
                    if (cherch.getEtudiantsSet() != null && !cherch.getEtudiantsSet().isEmpty()) {
                        String affichageEtudiants = cherch.getEtudiantsSet().stream()
                           .map(Etudiant::getNomPrenom)
                           .collect(Collectors.joining(", "));

                        int n = affichageEtudiants.indexOf(",");
                        if ( n!= -1) {
                            encadrantOUetudinatValue.setText(affichageEtudiants.substring(0, n + 1));
                            sujetDeTheseValue1.setText(affichageEtudiants.substring(n + 1));
                            sujetDeTheseValue2.setText("");
                        } else {
                            encadrantOUetudinatValue.setText(affichageEtudiants);
                            sujetDeTheseValue1.setText("");
                            sujetDeTheseValue2.setText("");
                        }
                        
                    } else {
                    	encadrantOUetudinatValue.setText("");
                        sujetDeTheseValue1.setText("");
                        sujetDeTheseValue2.setText("");
                    }
                    
                }
                sujetDeTheseLabel.setText("");
            }
        } else {
            nomValue.setText(" - ");
            prenomValue.setText(" - ");
            ageValue.setText(" - ");
            villeValue.setText(" - ");
            disciplinesValue.setText(" - ");
            disciplinesLabel.setText("Discipline :");
            anneeDeTheseOUnumBureauLabel.setText("Année de thèse :");
            anneeDeTheseOUnumBureauValue.setText(" - ");
            encadrantOUetudinatLabel.setText("Encadrant :");
            encadrantOUetudinatValue.setText(" - ");
            sujetDeTheseLabel.setText("");
            reglerSujetDeTheseAffichage("");
        }
    }
}

