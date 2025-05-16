package affichage.affichageEcosysteme;

import javax.swing.*;

import baseDeDonnees.ModificationPersonnesTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import peuple.*;
import territoire.Ville;
import verificationEntree.Coherence;

/**
 * Permet a l'utilisateur d'ajouter une nouvelle personne dans la base de donnees,
 * en prenant en compte les differentes fonctions de la nouvelle personne
 * (etudiants, chercheurs, mcf).
 */
public class AjoutPersonne extends JDialog {
    private static final long serialVersionUID = 1L;

	private boolean estConfirme = false;

    private String nomNVPers, prenomNVPers;
    private int ageNVPers;
    private Ville villeNVPers;
    private String fonctionNVPers;

    private Discipline disciplineNVEtud;
    private int anneeTheseNVEtud;
    private String sujetTheseNVEtud;
    private Titulaire encadrantNVEtud;

    private int NumBureauNVTit;
    private Set<Discipline> disciplinesNVTit;

    private Etudiant etudiantNVMCF;
    private Set<Etudiant> etudiantNVCherch;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    /**
     * Constructeur pour creer un panel de dialogue permettant d'ajouter une personne 
     * dans la base de donnees.
     *
     * @param parent La fenetre principale.
     * @param villes La liste de toutes les villes disponibles dans la base de donnees.
     * @param tousPersonnes La liste de toutes les personnes existantes dans la base de donnees.
     */
    public AjoutPersonne(Frame parent, ArrayList<Ville> villes, ArrayList<Personne> tousPersonnes) {
        super(parent, "Ajouter une personne", true); 

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        cardPanel.setLayout(cardLayout);

        JPanel Etape1Panel = etape1Panel(tousPersonnes, villes);
        cardPanel.add(Etape1Panel, "Etape1");

        this.setLayout(new BorderLayout());
        this.add(cardPanel, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(parent);
    }

    // Premiere etape : selection des informations generales
    private JPanel etape1Panel(ArrayList<Personne> tousPersonnes, ArrayList<Ville> villes) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField nomField = new JTextField(20);
        JTextField prenomField = new JTextField(20);
        JComboBox<Integer> ageComboBox = new JComboBox<>();
        for (int i = 18; i <= 100; i++) {
            ageComboBox.addItem(i);
        }
        JComboBox<String> villeComboBox = new JComboBox<>();
        for (Ville v : villes) {
            villeComboBox.addItem(v.getNomDep());
        }
        JComboBox<String> fonctionComboBox = new JComboBox<>(new String[]{"Etudiant", "MCF", "Chercheur"});
    
        int ligneCourante = 0;
    
        gbc.gridx = 0; gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        panel.add(nomField, gbc);
    
        gbc.gridx = 0; gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        panel.add(prenomField, gbc);
    
        gbc.gridx = 0; gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Âge:"), gbc);
        gbc.gridx = 1;
        panel.add(ageComboBox, gbc);
    
        gbc.gridx = 0; gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Ville:"), gbc);
        gbc.gridx = 1;
        panel.add(villeComboBox, gbc);
    
        gbc.gridx = 0; gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Fonction:"), gbc);
        gbc.gridx = 1;
        panel.add(fonctionComboBox, gbc);
    
        // Bouton pour passer a l'etape suivante
        JButton continuerButton = new JButton("Continuer");
        continuerButton.addActionListener(e -> {
            nomNVPers = nomField.getText().trim();
            prenomNVPers = prenomField.getText().trim();
            ageNVPers = (Integer) ageComboBox.getSelectedItem();
            fonctionNVPers = (String) fonctionComboBox.getSelectedItem();
    
            String villeString = (String) villeComboBox.getSelectedItem();
            for (Ville v : villes) {
                String villeCourante = v.getNomDep();
                if (villeCourante.equals(villeString)) {
                    villeNVPers = v;
                    break;
                }
            }
            if (Coherence.nomPrenomEstValide(nomNVPers) && Coherence.nomPrenomEstValide(prenomNVPers)) {
            	etape2(tousPersonnes);
            } else {
            	JOptionPane.showMessageDialog(this, 
            		    "Le nom ou prénom n'est pas sous la bonne forme.\n"
            		  + "Vérifiez que ces champs ne sont pas vides, ne commencent pas par un tiret,\n"
            		  + "et qu'ils ne contiennent pas de caractères spéciaux ni de chiffres.", 
            		    "Nom ou prénom invalide", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridy = ligneCourante++;
        panel.add(continuerButton, gbc);
    
        return panel;
    }

    private void etape2(ArrayList<Personne> tousPersonnes) {
        if (fonctionNVPers != null) {
            switch (fonctionNVPers) {
                case "Etudiant":
                    cardPanel.add(etudiantPanelEtape2(tousPersonnes), "Etape2_Etudiant");
                    cardLayout.show(cardPanel, "Etape2_Etudiant");
                    break;
                case "MCF":
                case "Chercheur":
                    cardPanel.add(titulairePanelEtape2(tousPersonnes), "Etape2_Titulaire");
                    cardLayout.show(cardPanel, "Etape2_Titulaire");
                    break;
            }
        }
    }

    // Deuxieme etape : selection des informations specifiques au type de la nouvelle personne
    private JPanel etudiantPanelEtape2(ArrayList<Personne> tousPersonnes) {
        // Panneau pour les informations spécifiques aux étudiants
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ligneCourante = 0;
        
        JComboBox<String> disciplineComboBox;
        disciplineComboBox = new JComboBox<>();
        for (Discipline disc : Discipline.values()) {
            disciplineComboBox.addItem(disc.toString());
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Discipline:"), gbc);
        gbc.gridx = 1;
        panel.add(disciplineComboBox, gbc);

        JComboBox<Integer> anneeDeTheseComboBox = new JComboBox<>();
        for (int i = 1; i<4; i++){
            anneeDeTheseComboBox.addItem(i);
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Année de thèse"), gbc);
        gbc.gridx = 1;
        panel.add(anneeDeTheseComboBox, gbc);

        JTextField sujetDeTheseField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Sujet de thèse:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(sujetDeTheseField, gbc);

        JButton continuerButtonEtape2 = new JButton("Continuer");
        continuerButtonEtape2.addActionListener(e -> {
            anneeTheseNVEtud = (Integer) anneeDeTheseComboBox.getSelectedItem();
            sujetTheseNVEtud = (String) sujetDeTheseField.getText();
            if (!sujetTheseNVEtud.equals("")){
                disciplineNVEtud = Discipline.getDiscipline((String)disciplineComboBox.getSelectedItem());

                boolean existeTitulaire = false;
                for (Personne p: tousPersonnes){
                    if (p instanceof Titulaire tit && tit.getDisciplines().contains(disciplineNVEtud) && (tit instanceof Chercheur || (tit instanceof MCF mcf && mcf.getEtudiant() == null))) {
                        existeTitulaire = true; // Il existe au moins un titulaire qui peut prendre en these cet etudiant
                    }
                }

                if (!existeTitulaire){
                    JOptionPane.showMessageDialog(this, "Il n'existe pas de titulaire qui peut prendre en these un etudiant \n"
                    		+ "avec la discipline choisi. Vous pouvez soit ajouter un nouveau titulaire, \n"
                    		+ "soit modifier un titulaire existant afin de trouver une place, \n"
                    		+ "soit annuler l'ajout.", 
                            "Impossible d'ajouter cet etudiant", JOptionPane.ERROR_MESSAGE);
                    
                } else {
                	etape3(tousPersonnes);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Le sujet de these ne peut pas etre vide.", 
                                              "Sujet de these incorrect", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridy = ligneCourante++;

        panel.add(continuerButtonEtape2, gbc);
        return panel;
    }

    // Deuxieme etape : selection des informations specifiques au type de la nouvelle personne
    private JPanel titulairePanelEtape2(ArrayList<Personne> tousPersonnes) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ligneCourante = 0;

        JComboBox<Integer> numBureauComboBox = new JComboBox<>();
        for (int i = 100; i < 200; i++) { 
            numBureauComboBox.addItem(i);
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Numéro de bureau:"), gbc);
        gbc.gridx = 1;
        panel.add(numBureauComboBox, gbc);

        JPanel disciplinesPanel = new JPanel();
        ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
        disciplinesPanel.setLayout(new BoxLayout(disciplinesPanel, BoxLayout.Y_AXIS)); 
        for (Discipline disc : Discipline.values()) {
            JCheckBox checkBox = new JCheckBox(disc.toString());
            disciplinesPanel.add(checkBox);
            checkBoxList.add(checkBox);
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Disciplines:"), gbc);
        gbc.gridx = 1;
        panel.add(disciplinesPanel, gbc);

        JButton continuerButtonEtape2 = new JButton("Continuer");
        continuerButtonEtape2.addActionListener(e -> {
            NumBureauNVTit = (Integer) numBureauComboBox.getSelectedItem();

            disciplinesNVTit = new HashSet<>();
            for (JCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    for (Discipline disc : Discipline.values()) {
                        if (disc.toString().equals(checkBox.getText())) {
                            disciplinesNVTit.add(disc);
                            break;
                        }
                    }
                }
            }

            if (disciplinesNVTit.size() > 2 || disciplinesNVTit.size() < 1){
                JOptionPane.showMessageDialog(this, "Le nombre de disciplines pour un titulaire doit être compris entre 1 et 2.", 
                                              "Mauvais nombre de disciplines", JOptionPane.ERROR_MESSAGE);
            } else {
            	etape3(tousPersonnes);
            }
        });
        gbc.gridy = ligneCourante++;

        panel.add(continuerButtonEtape2, gbc);

        return panel;
    }

    private void etape3(ArrayList<Personne> tousPersonnes) {
        if (fonctionNVPers != null) {
            switch (fonctionNVPers) {
                case "Etudiant":
                    cardPanel.add(etudiantPanelEtape3(tousPersonnes), "Etape3_Etudiant");
                    cardLayout.show(cardPanel, "Etape3_Etudiant");
                    break;
                case "MCF":
                    cardPanel.add(MCFPanelEtape3(tousPersonnes), "Etape3_MCF");
                    cardLayout.show(cardPanel, "Etape3_MCF");
                    break;
                case "Chercheur":
                    cardPanel.add(chercheurPanelEtape3(tousPersonnes), "Etape3_Chercheur");
                    cardLayout.show(cardPanel, "Etape3_Chercheur");
                    break;
            }
        }
    }

    // Troisieme etape : selection des informations specifiques a la discipline du nouveau etudiant
    private JPanel etudiantPanelEtape3(ArrayList<Personne> tousPersonnes) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ligneCourante = 0;

        JComboBox<String> encadrantComboBox = new JComboBox<>();
        ArrayList<Titulaire> titPossibles = new ArrayList<>();
        for (Personne p: tousPersonnes){
            if (p instanceof Titulaire tit && tit.getDisciplines().contains(disciplineNVEtud) && (tit instanceof Chercheur || (tit instanceof MCF mcf && mcf.getEtudiant() == null))) {
                titPossibles.add(tit);
                encadrantComboBox.addItem(tit.getNomPrenom() + " (id : " + tit.getID() + ")");
            }
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Encadrant:"), gbc);
        gbc.gridx = 1;
        panel.add(encadrantComboBox, gbc);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> {
            String titulaireString = (String) encadrantComboBox.getSelectedItem();
            for (Titulaire tit: titPossibles){
                String titulaireCourant = tit.getNomPrenom() + " (id : " + tit.getID() + ")";
                if (titulaireCourant.equals(titulaireString)){
                    encadrantNVEtud = tit;
                    break;
                }
            }

            Etudiant etudiant = new Etudiant(nomNVPers, prenomNVPers, ageNVPers, villeNVPers, sujetTheseNVEtud, disciplineNVEtud, anneeTheseNVEtud, null);
            etudiant.setEncadrant(encadrantNVEtud);
            tousPersonnes.add(etudiant);
            ModificationPersonnesTerminal.actualiserBaseDeDonnees(tousPersonnes);
            estConfirme = true;
            this.dispose();
        });
        gbc.gridx = 1;
        gbc.gridy = ligneCourante++;
        panel.add(validerButton, gbc);

        return panel;
    }

    // Troisieme etape : selection des informations specifiques a la discipline du nouveau MCF
    private JPanel MCFPanelEtape3(ArrayList<Personne> tousPersonnes) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ligneCourante = 0;

        JComboBox<String> etudiantComboBox = new JComboBox<>();
        String etudNull = "aucun";
        etudiantComboBox.addItem(etudNull);
        ArrayList<Etudiant> etudPossibles = new ArrayList<>();
        for (Personne pers: tousPersonnes){
            if (pers instanceof Etudiant etud && disciplinesNVTit.contains(etud.getDiscipline())) {
                etudPossibles.add(etud);
            }
        }
        
        for (Etudiant etud: etudPossibles){
            etudiantComboBox.addItem(etud.getNomPrenom() + " (id : " + etud.getID() + ")");
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Étudiant en thèse :"), gbc);
        gbc.gridx = 1;
        panel.add(etudiantComboBox, gbc);

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> {
            String etudString = (String) etudiantComboBox.getSelectedItem();
            for (Etudiant etud: etudPossibles){
                String etudiantCourant = etud.getNomPrenom() + " (id : " + etud.getID() + ")";
                if (etudiantCourant.equals(etudString)){
                    etudiantNVMCF = etud;
                    break;
                }
            }
            MCF mcf = new MCF(nomNVPers, prenomNVPers, ageNVPers, villeNVPers, disciplinesNVTit, NumBureauNVTit, etudiantNVMCF);
            if (etudiantNVMCF != null){
                etudiantNVMCF.setEncadrant(mcf);
            }
            tousPersonnes.add(mcf);
            ModificationPersonnesTerminal.actualiserBaseDeDonnees(tousPersonnes);
            estConfirme = true;
            this.dispose();
        });
        gbc.gridx = 1;
        gbc.gridy = ligneCourante++;
        panel.add(validerButton, gbc);

        return panel;
    }

 // Troisieme etape : selection des informations specifiques a la discipline du nouveau chercheur
    private JPanel chercheurPanelEtape3(ArrayList<Personne> tousPersonnes) {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int ligneCourante = 0;

        JPanel etudiantsPanel  = new JPanel();
        
        etudiantsPanel.setLayout(new BoxLayout(etudiantsPanel, BoxLayout.Y_AXIS));
        ArrayList<Etudiant> etudiantsPossibles = new ArrayList<>();
        for (Personne pers: tousPersonnes){
            if (pers instanceof Etudiant etud && disciplinesNVTit.contains(etud.getDiscipline())) {
                etudiantsPossibles.add(etud);
            }
        }

        ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
        for (Etudiant etud : etudiantsPossibles) {
            JCheckBox checkBox = new JCheckBox(etud.getNomPrenom() + " (id : " + etud.getID()+ ")");
            etudiantsPanel.add(checkBox);
            checkBoxList.add(checkBox);
        }

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        panel.add(new JLabel("Étudiants en thèse :"), gbc);
        gbc.gridx = 1;
        panel.add(etudiantsPanel, gbc);
                

        JButton validerButton = new JButton("Valider");
        validerButton.addActionListener(e -> {
            etudiantNVCherch = new HashSet<>();
            for (JCheckBox checkBox : checkBoxList) {
                if (checkBox.isSelected()) {
                    for (Etudiant etud : etudiantsPossibles) {
                        if ((etud.getNomPrenom() + " (id : " + etud.getID()+ ")").equals(checkBox.getText())) {
                            etudiantNVCherch.add(etud);
                            break;
                        }
                    }
                }
            }
            Chercheur chercheur = new Chercheur(nomNVPers, prenomNVPers, ageNVPers, villeNVPers, disciplinesNVTit, NumBureauNVTit, etudiantNVCherch);
            if (!etudiantNVCherch.isEmpty()){ // on met a jour les titulaires de ces etudiants pour les mettre le titulaire qu'on vient d'ajouter
                for(Etudiant et: etudiantNVCherch){
                    et.setEncadrant(chercheur);
                }
            }
            tousPersonnes.add(chercheur);
            ModificationPersonnesTerminal.actualiserBaseDeDonnees(tousPersonnes);
            estConfirme = true;
            this.dispose();
        });
        gbc.gridx = 1;
        gbc.gridy = ligneCourante++;
        panel.add(validerButton, gbc);

        return panel;
    }

    /**
     * Indique si l'utilisateur a confirme l'ajout.
     *
     * @return {@code true} si l'utilisateur a confirme, sinon {@code false}.
     */
    public boolean estConfirme() {
        return estConfirme;
    }
}
