package affichage.affichageEcosysteme;

import javax.swing.*;

import baseDeDonnees.ModificationPersonnesTerminal;
import peuple.*;
import territoire.Ville;
import verificationEntree.Coherence;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Permet a l'utilisateur de modifier la personne selectionnee.
 */
public class ModificationPersonne extends JDialog {
    private static final long serialVersionUID = 1L;
	private boolean estConfirme = false; // Indique si l'utilisateur a confirme les modifications
    
	// Les sections generales
    private JTextField nomField;
    private JTextField prenomField;
    private JComboBox<Integer> ageComboBox;
    private JComboBox<String> villeComboBox;

    // Les sections pour des etudiants
    private JComboBox<String> disciplineComboBox;
    private JComboBox<Integer> anneeDeTheseComboBox;
    private JComboBox<String> encadrantComboBox;
    private JTextField sujetDeTheseField;

    // Les sections pour des titulaires
    private JComboBox<Integer> numBureauComboBox;
    private JPanel disciplinesPanel;
    private JComboBox<String> etudiantComboBox; // Pour les mcf
    private JPanel etudiantsPanel; // Pour les chercheurs

    /**
     * Constructeur pour creer un panel de dialogue permettant de modifier une personne dans la base de donnees.
     *
     * @param parent La fenetre principale.
     * @param personneModifie La personne selectionnee que l'utilisateur veut modifier.
     * @param villes Toutes les villes de la base de donnees.
     * @param tousPersonnes Toutes les personnes de la base de donnees.
     */
    public ModificationPersonne(Frame parent, Personne personneModifie, ArrayList<Ville> villes, ArrayList<Personne> tousPersonnes) {   	    	
        super(parent, "Modifier une personne", true); 
        
        JPanel contentPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int ligneCourante = 0;
    
        nomField = new JTextField(personneModifie.getNom(), 20);
    
        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        contentPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        contentPanel.add(nomField, gbc);

        prenomField = new JTextField(personneModifie.getPrenom(), 20);

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        contentPanel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        contentPanel.add(prenomField, gbc);
    
        ageComboBox = new JComboBox<>();
        for (int i = 18; i <= 100; i++) { 
            ageComboBox.addItem(i);
        }
        ageComboBox.setSelectedItem(personneModifie.getAge()); 

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        contentPanel.add(new JLabel("Âge :"), gbc);
        gbc.gridx = 1;
        contentPanel.add(ageComboBox, gbc);
    
        villeComboBox = new JComboBox<>();
        for (Ville v : villes) { 
            villeComboBox.addItem(v.getNomDep());
        }
        villeComboBox.setSelectedItem(personneModifie.getVille().getNomDep()); 

        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        contentPanel.add(new JLabel("Ville :"), gbc);
        gbc.gridx = 1;
        contentPanel.add(villeComboBox, gbc);
    
        if (personneModifie instanceof Etudiant etudiant) {
            disciplineComboBox = new JComboBox<>();
            for (Discipline disc : Discipline.values()) { 
                disciplineComboBox.addItem(disc.toString());
            }
            disciplineComboBox.setSelectedItem(etudiant.getDiscipline().toString()); 

            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            contentPanel.add(new JLabel("Discipline :"), gbc);
            gbc.gridx = 1;
            contentPanel.add(disciplineComboBox, gbc);

            anneeDeTheseComboBox = new JComboBox<>();
            for (int i = 1; i<4; i++){
                anneeDeTheseComboBox.addItem(i);
            }
            anneeDeTheseComboBox.setSelectedItem(etudiant.getAnneeDeThese());

            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            contentPanel.add(new JLabel("Année de thèse :"), gbc);
            gbc.gridx = 1;
            contentPanel.add(anneeDeTheseComboBox, gbc);

            encadrantComboBox = new JComboBox<>();
            ArrayList<Titulaire> titPossibles = etudiant.listeTitulairesPossibles(tousPersonnes, true);
            for (Titulaire tit: titPossibles){
                encadrantComboBox.addItem(tit.getNomPrenom() + " (id : " + tit.getID() + ")");
            }
            encadrantComboBox.setSelectedItem(etudiant.getAnneeDeThese());

            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            contentPanel.add(new JLabel("Encadrant :"), gbc);
            gbc.gridx = 1;
            contentPanel.add(encadrantComboBox, gbc);

            sujetDeTheseField = new JTextField(etudiant.getSujetDeThese(), 20);

            // On stocke les valeurs initiales pour apres interdire de modifier discipline et encadrant en meme temps
            String disciplineInitiale = etudiant.getDiscipline().toString();
            String encadrantInitial = encadrantComboBox.getSelectedItem().toString(); 

            disciplineComboBox.addActionListener(e -> {
                String selectedDiscipline = (String) disciplineComboBox.getSelectedItem();
                if (!selectedDiscipline.equals(disciplineInitiale)) {
                    // On desactive encadrantComboBox si discipline change
                    encadrantComboBox.setEnabled(false);
                } else {
                    // On reactive encadrantComboBox si discipline revient a sa valeur initiale
                    encadrantComboBox.setEnabled(true);
                }
            });

            encadrantComboBox.addActionListener(e -> {
                String selectedEncadrant = (String) encadrantComboBox.getSelectedItem();
                if (!selectedEncadrant.equals(encadrantInitial)) {
                    // On desactive disciplineComboBox si encadrant change
                    disciplineComboBox.setEnabled(false);
                } else {
                    // On reactive disciplineComboBox si encadrant revient a sa valeur initiale
                    disciplineComboBox.setEnabled(true);
                }
            });

            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            contentPanel.add(new JLabel("Sujet de thèse :"), gbc);
            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            contentPanel.add(sujetDeTheseField, gbc);

        } else if (personneModifie instanceof Titulaire titulaire) {
            
            numBureauComboBox = new JComboBox<>();
            for (int i = 100; i < 200; i++) { 
                numBureauComboBox.addItem(i);
            }
            numBureauComboBox.setSelectedItem(titulaire.getNumBureau()); 

            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            contentPanel.add(new JLabel("Numéro de bureau :"), gbc);
            gbc.gridx = 1;
            contentPanel.add(numBureauComboBox, gbc);

            ArrayList<JCheckBox> boxsEtudiants = new ArrayList<>(); // Si c'est une chercheur 

            disciplinesPanel = new JPanel();
            disciplinesPanel.setLayout(new BoxLayout(disciplinesPanel, BoxLayout.Y_AXIS)); // Disposition verticale

            ArrayList<JCheckBox> checkBoxDisciplines = new ArrayList<>();

            // On fait pareil ici qu'avec les etudiants: l'utilisateur n'a pas de droit de changer disciplines et etudiants en meme temps
            ArrayList<JCheckBox> boxsDisciplineCochesInitialement = new ArrayList<>();

            for (Discipline disc : Discipline.values()) {
                JCheckBox checkBox = new JCheckBox(disc.toString());
                disciplinesPanel.add(checkBox);
                checkBoxDisciplines.add(checkBox);

                if (titulaire.getDisciplines() != null && titulaire.getDisciplines().contains(disc)) {
                    checkBox.setSelected(true);
                    boxsDisciplineCochesInitialement.add(checkBox);
                }

                checkBox.addItemListener(e -> {
                    // Si l'utilisateur a modifie des disciplines il ne peux pas modifier etudiant
                    if ((!boxsDisciplineCochesInitialement.contains(checkBox) && checkBox.isSelected()) ||
                        (!checkBox.isSelected() && boxsDisciplineCochesInitialement.contains(checkBox))){
                            if (etudiantComboBox != null){
                                etudiantComboBox.setEnabled(false); // Pour mcf
                            } else {
                                for (JCheckBox checkB : boxsEtudiants) { // Pour chercheur
                                    checkB.setEnabled(false);
                                }
                            }
                    } else { // Si non on doit verififer si tous les cases initialement selectionnes sont selectionnes
                        boolean tousBonnesCasesSelectionnes = true;
                        for (JCheckBox checkB: checkBoxDisciplines){
                            // On verifie que tous les case initialement coches sont bien coches
                            if (boxsDisciplineCochesInitialement.contains(checkB) && !checkB.isSelected()){
                                tousBonnesCasesSelectionnes = false; 
                            } 
                            // On verifie que tous les case initialement pas coches sont bien pas coches
                            if (!boxsDisciplineCochesInitialement.contains(checkB) && checkB.isSelected()){
                                tousBonnesCasesSelectionnes = false;
                            }
                        }
                        if (tousBonnesCasesSelectionnes){
                            if (etudiantComboBox != null){
                                etudiantComboBox.setEnabled(true);
                            } else {
                                for (JCheckBox checkB : boxsEtudiants) { // Pour chercheur
                                    checkB.setEnabled(true);
                                }
                            }
                        }
                    }

                });
            }

            gbc.gridx = 0;
            gbc.gridy = ligneCourante++;
            contentPanel.add(new JLabel("Disciplines :"), gbc);
            gbc.gridx = 1;
            contentPanel.add(disciplinesPanel, gbc);
            
            if (personneModifie instanceof MCF mcf) {
 
                etudiantComboBox = new JComboBox<>();
                String etudNull = "aucun";
                etudiantComboBox.addItem(etudNull);
                ArrayList<Etudiant> etudPossibles = mcf.listeEtudiantsPossibles(tousPersonnes, true);
                for (Etudiant etud : etudPossibles) { 
                    etudiantComboBox.addItem(etud.getNomPrenom() + " (id : " + etud.getID() + ")");
                }

                Etudiant etudiantCourant = mcf.getEtudiant();
                if (etudiantCourant == null) {
                    etudiantComboBox.setSelectedItem(etudNull);
                } else {
                    etudiantComboBox.setSelectedItem(etudiantCourant.getNomPrenom() + " (id : " + etudiantCourant.getID() + ")");
                }

                String etudiantInitial = etudiantComboBox.getSelectedItem().toString();

                // Listener pour desactiver/activer les cases a cocher en fonction de la selection dans le comboBox
                etudiantComboBox.addActionListener(e -> {
                    String selection = (String) etudiantComboBox.getSelectedItem();
                    if (etudiantInitial != selection){
                        for (JCheckBox checkBox : checkBoxDisciplines) {
                            checkBox.setEnabled(false);
                        }
                    } else {
                        for (JCheckBox checkBox : checkBoxDisciplines) {
                            checkBox.setEnabled(true);
                        }
                    }
                });

                gbc.gridx = 0;
                gbc.gridy = ligneCourante++;
                contentPanel.add(new JLabel("Étudiant en thèse :"), gbc);
                gbc.gridx = 1;
                contentPanel.add(etudiantComboBox, gbc);


            } else if (personneModifie instanceof Chercheur chercheur) {
                etudiantsPanel  = new JPanel();

                etudiantsPanel.setLayout(new BoxLayout(etudiantsPanel, BoxLayout.Y_AXIS)); 
                ArrayList<Etudiant> etudiantsPossibles = chercheur.listeEtudiantsPossibles(tousPersonnes, true);
                ArrayList<JCheckBox> boxsEtudiantsCochesInitialement = new ArrayList<>();

                for (Etudiant etud : etudiantsPossibles) {
                    JCheckBox checkBox = new JCheckBox(etud.getNomPrenom() + " (id : " + etud.getID()+ ")");
                    etudiantsPanel.add(checkBox);
                    boxsEtudiants.add(checkBox);

                    if (chercheur.getEtudiantsSet() != null && chercheur.getEtudiantsSet().contains(etud)) {
                        checkBox.setSelected(true);
                        boxsEtudiantsCochesInitialement.add(checkBox);
                    }

                    checkBox.addItemListener(e -> {
                        // Si l'utilisateur a modifie des disciplines il ne peux pas modifier les etudiants
                        if ((!boxsEtudiantsCochesInitialement.contains(checkBox) && checkBox.isSelected()) ||
                            (!checkBox.isSelected() && boxsEtudiantsCochesInitialement.contains(checkBox))){
                            for (JCheckBox checkB : checkBoxDisciplines) {
                                checkB.setEnabled(false);
                            }
                        } else {
                            boolean tousBonnesCasesSelectionnes = true;
                            for (JCheckBox checkB: boxsEtudiants){
                                if (boxsEtudiantsCochesInitialement.contains(checkB) && !checkB.isSelected()){
                                    tousBonnesCasesSelectionnes = false;
                                }
                                if (!boxsEtudiantsCochesInitialement.contains(checkB) && checkB.isSelected()){
                                    tousBonnesCasesSelectionnes = false;
                                }
                            }
                            if (tousBonnesCasesSelectionnes){
                                for (JCheckBox checkB : checkBoxDisciplines) {
                                    checkB.setEnabled(true);
                                }
                            }
                        }
                    });
                }

                gbc.gridx = 0;
                gbc.gridy = ligneCourante++;
                contentPanel.add(new JLabel("Étudiants en thèse :"), gbc);
                gbc.gridx = 1;
                contentPanel.add(etudiantsPanel, gbc);
            }
        }
    
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Annuler");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
    
        gbc.gridx = 0;
        gbc.gridy = ligneCourante++;
        gbc.gridwidth = 2;
        contentPanel.add(buttonPanel, gbc);
    
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    
        okButton.addActionListener(e -> {
        	// On met a jour les valeurs des que l'utilisateur a confirme
            String nom = nomField.getText();
            String prenom = prenomField.getText();

            boolean nomEstvalide = Coherence.nomPrenomEstValide(nom);
            boolean prenomEstvalide = Coherence.nomPrenomEstValide(prenom);
    
            // Si le nom/prenom n'est pas valide on met en rouge la bordure
            bordureRouge(nomField, !nomEstvalide);
            bordureRouge(prenomField, !prenomEstvalide);
    
            if (!nomEstvalide || !prenomEstvalide) {
                JOptionPane.showMessageDialog(this, "Le nom ou prénom est invalide. Veuillez vérifier les champs.", 
                	    "Erreur de validation", JOptionPane.ERROR_MESSAGE);
                return;
            }

            personneModifie.setNom(nom);
            personneModifie.setPrenom(prenom);

            int age = (int) ageComboBox.getSelectedItem();
            personneModifie.setAge(age);

            Ville ville = getVilleSelectionnee(villes);
            personneModifie.setVille(ville);

            if (personneModifie instanceof Etudiant etudiantModifie) {

                String sujetDeT = (String) sujetDeTheseField.getText();
                
                boolean sujetEstValide = Coherence.chaineNonVide(sujetDeT);
                
                bordureRouge(sujetDeTheseField, !sujetEstValide);
                
                if (!sujetEstValide){
                    JOptionPane.showMessageDialog(this, "Le sujet de thèse ne peut pas être vide.", 
                    	    "Sujet de thèse vide", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean disciplineEstChangee = false; 
                Discipline nouvelleDiscipline = Discipline.getDiscipline((String) disciplineComboBox.getSelectedItem());
                Discipline ancienneDiscipline = etudiantModifie.getDiscipline();
                if (!nouvelleDiscipline.equals(ancienneDiscipline) && !etudiantModifie.getEncadrant().getDisciplines().contains(nouvelleDiscipline)) { // on est oblige d'attribuer cet etudiant a un autre titulaire
                    etudiantModifie.setDiscipline(nouvelleDiscipline);
                    disciplineEstChangee = true;
                    boolean reussi = false;
                    for (Personne p: tousPersonnes){
                        if (p instanceof Titulaire tit && tit.peutPrendreEtudiant(etudiantModifie)){
                            etudiantModifie.setEncadrant(tit);
                            reussi = true;
                            break;
                        }
                    }
                    if (!reussi) { // si on a pas reussi de trouver un encardant on remet la discipline et l'indiquons a l'utilisateur
                        JOptionPane.showMessageDialog(this, "Erreur lors du changement de discipline, car l'étudiant \n"
                        		+ "avec la nouvelle discipline n'a pas pu être attribué à un titulaire.", 
                        	    "Erreur de changement de discipline", JOptionPane.ERROR_MESSAGE);
                        etudiantModifie.setDiscipline(ancienneDiscipline);
                        disciplineEstChangee = false;
                    }
                } else if (etudiantModifie.getEncadrant().getDisciplines().contains(nouvelleDiscipline)) { // le titulaire contient la nouvelle discipline de l'etudiant donc on peut la changer
                	etudiantModifie.setDiscipline(nouvelleDiscipline);
                	disciplineEstChangee = true;
                }

                int anneeDeT = (int) anneeDeTheseComboBox.getSelectedItem();
                etudiantModifie.setAnneeDeThese(anneeDeT);

                if (!disciplineEstChangee) {
                	String encadrantText = (String) encadrantComboBox.getSelectedItem();
                    for (Personne p: tousPersonnes){
                        if (p instanceof Titulaire tit && (tit.getNomPrenom() + " (id : " + tit.getID() + ")").equals(encadrantText)){
                            etudiantModifie.setEncadrant(tit);
                            break;
                        }
                    }
                }
                
            } else if (personneModifie instanceof Titulaire titulaireModifie) {
                // Traitement de changement du numero de Bureau du titulaire
                titulaireModifie.setNumBureau((int) numBureauComboBox.getSelectedItem());

                Set<Discipline> nvDisciplines = getDisciplinesSelectionnees();

                if (nvDisciplines.size() > 2 || nvDisciplines.size() < 1){
                    JOptionPane.showMessageDialog(this, "Le nombre de disciplines pour un titulaire doit être compris entre 1 et 2.", 
                    	    "Nombre de disciplines invalide", JOptionPane.ERROR_MESSAGE);
                    return;
                }
               
                if (personneModifie instanceof MCF mcfModifie) {
                    // Traitement de changement des disciplines d'un MCF
                    Etudiant etudiantMcf = mcfModifie.getEtudiant();
                    
                    boolean disciplineEstChangee = false; 
                    
                    if (!nvDisciplines.equals(mcfModifie.getDisciplines())){
                        if (etudiantMcf != null) {
                            
                            if (!nvDisciplines.contains(etudiantMcf.getDiscipline())){ // Dans ce cas on doit verifier que pour cet etudiant on peut trouver un titulaire compatible

                                if (!mcfModifie.essaieSupprimerEtudiant(tousPersonnes)) {
                                    JOptionPane.showMessageDialog(this, "Impossible de changer les disciplines car l'étudiant de ce MCF \n"
                                    		+ "ne peut pas être attribué à un autre titulaire.", 
                                    	    "Erreur lors du changement de disciplines", JOptionPane.ERROR_MESSAGE);
                                    
                                } else { // Si on a reussi a trouve un nouveau titulaire pour l'ancien etudiant on est good
                                    mcfModifie.setDisciplinesSet(nvDisciplines);
                                    disciplineEstChangee = true;
                                }
                            } else { // Si on peut laisser le etudiant parce que sa discipline est toujours dans la liste des disciplines du mcf on est bon
                                mcfModifie.setDisciplinesSet(nvDisciplines);
                                disciplineEstChangee = true;
                            }
                        } else { // Si il n'a pas d'etudiant en these on peut modifier
                            mcfModifie.setDisciplinesSet(nvDisciplines);
                            disciplineEstChangee = true;
                        }
                    }
                    
                    if (!disciplineEstChangee) {
                    	// Traitement de changement d'un etudiant d'un MCF
                        Etudiant nvEtudiant = null;
                        String etudText = (String) etudiantComboBox.getSelectedItem();
                        for (Personne p : tousPersonnes){
                            if (p instanceof Etudiant etud && (etud.getNomPrenom() + " (id : " + etud.getID() + ")").equals(etudText)){
                            	nvEtudiant = etud;
                                break;
                            }
                        }
                        if (nvEtudiant!= null) {
                            if (!nvEtudiant.equals(mcfModifie.getEtudiant())){
                                if (!mcfModifie.essaieSupprimerEtudiant(tousPersonnes)) { // meme si etudiant courrant est null ca va renvoyer true, car en soi le mcf n'a plus d'etudiant
                                    JOptionPane.showMessageDialog(this, "Impossible de changer l'étudiant en thèse car il ne peut pas être attribué à un autre titulaire.", 
                                            "Erreur lors de la suppression de l'étudiant courant", JOptionPane.ERROR_MESSAGE);
                                } else { // on a reussi d'attribuer l'etudiant a un autre encadrant et on peut poursuivre pour ajouter un autre etudiant
                                	nvEtudiant.setEncadrant(mcfModifie);
                                }
                            }
                        } else { // Soit l'utilisateur a choisi de supprimer etudiant courrant, soit le mcf n'en avait pas de base et on garde comme ca
                            if (mcfModifie.getEtudiant() != null){
                                if (!mcfModifie.essaieSupprimerEtudiant(tousPersonnes)) {
                                    JOptionPane.showMessageDialog(this, "Impossible d'attribuer l'étudiant en thèse à un autre titulaire.", 
                                            "Erreur lors de la suppression de l'étudiant courant", JOptionPane.ERROR_MESSAGE);
                                    
                                } 
                            } 
                        }
                    }

                } else if (personneModifie instanceof Chercheur chercheurModifie) {
                    // Traitement de changement des disciplines d'un chercheur
                	
                	boolean disciplineEstChangee = false;
                    if (chercheurModifie.getEtudiantsSet() == null || chercheurModifie.getEtudiantsSet().isEmpty()) {
                		chercheurModifie.setDisciplinesSet(nvDisciplines);
                		disciplineEstChangee = true;
                		
                	} else if (!nvDisciplines.equals(chercheurModifie.getDisciplines())){
                        // On verifie si il y a une discipline en commun avec les anciennes ou pas pour savoir si on doit supprimer tous
                        // les etudiants en these ou non 
                        Set<Discipline> intersectionDisciplines = new HashSet<>(chercheurModifie.getDisciplines());
                        intersectionDisciplines.retainAll(nvDisciplines);
                        Set<Etudiant> etudiantsAAttribuerAuxAutresTit = new HashSet<>(chercheurModifie.getEtudiantsSet());
                        if (!intersectionDisciplines.isEmpty()){
                            Set<Etudiant> etudiantsCompatiblesAvecNVDisc = new HashSet<>();
                            
                            for (Etudiant etud: etudiantsAAttribuerAuxAutresTit){
                                if (intersectionDisciplines.contains(etud.getDiscipline())){
                                    etudiantsCompatiblesAvecNVDisc.add(etud);
                                }
                            }
                            etudiantsAAttribuerAuxAutresTit.removeAll(etudiantsCompatiblesAvecNVDisc);
                        }
                        if (!etudiantsAAttribuerAuxAutresTit.isEmpty()) {
                            ArrayList<Chercheur> chercheursTrouvePourEtudiants = new ArrayList<>(); 
                            ArrayList<Set<Etudiant>> etudiantsParChercheur = new ArrayList<>();
                            HashMap<Etudiant, MCF> couplage = new HashMap<>();

                            // Soit notre fonction a reussi de supprimer la personne et on peut tkl le supprimer de la base de donnes
                            // Soit on donne le choix a l'utilisateur si il veut ou pas de supprimer le max ou d'annuler tout
                            if (chercheurModifie.essaieSupprimerTousEtudiants(etudiantsAAttribuerAuxAutresTit, tousPersonnes, chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage)){ // On a reussi a attribue tous les etudiants aux autres encadrants
                                chercheurModifie.setDisciplinesSet(nvDisciplines);
                                disciplineEstChangee = true;
                            } else {
                            	if (chercheursTrouvePourEtudiants.isEmpty() && couplage.keySet().isEmpty()) { // Si on a vraiment trouve aucune couple etudiant - titulaire
                            		JOptionPane.showMessageDialog(this, "Impossible de modifier les disciplines de ce chercheur car il a des étudiants en thèse pour \n"
                            				+ "lesquels aucun autre encadrant compatible n'a été trouvé dans la base de données", 
                                            "Impossibilite de supprimer", JOptionPane.ERROR_MESSAGE);
                            	} else {
                            		String message = "Impossible de modifier les disciplines de ce chercheur car il a des étudiants en thèse \n"
                            				+ "pour lesquels aucun autre encadrant compatible n'a été trouvé dans la base de données.\n\n"
                                            + "Souhaitez-vous attribuer un maximum d'étudiants incompatibles aux autres \n"
                                            + "encadrants ou annuler la modification des disciplines ?";
                            		String[] options2 = {"Supprimer le maximum", "Annuler"};

                                    int choix = JOptionPane.showOptionDialog(null, message, "Supprimer un chercheur", JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE, null, options2, options2[1] 
                                    );
                                    if (choix == JOptionPane.YES_OPTION) {
                                        chercheurModifie.supprimerMaxDesEtudiants(chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage);
                                    } // Par defaut on annule l'action
                            	}
                            }
                        } else { // Si tous les etudiants sont compatibles on est bon 
                            chercheurModifie.setDisciplinesSet(nvDisciplines);
                            disciplineEstChangee = true;
                        }
                    }
                    
                    if (!disciplineEstChangee) { // Parce que dans ce cas le programme a deja change les etudiants pour que ce titulaire a que des etudiants compatibles
                    	// Traitement de changement des etudiants d'un chercheur
                        Set<Etudiant> etudiants = getEtudiantsSelectionnee(tousPersonnes);
                        if (chercheurModifie.getEtudiantsSet() != null && chercheurModifie.getEtudiantsSet().isEmpty()){
                            Set<Etudiant> etudiantsASupprimerDeCeCherch = new HashSet<>(chercheurModifie.getEtudiantsSet());
                            etudiantsASupprimerDeCeCherch.removeAll(etudiants);
                            Set<Etudiant> etudiantsAAjouterACeCherch = new HashSet<>(etudiants);
                            etudiantsAAjouterACeCherch.removeAll(chercheurModifie.getEtudiantsSet());

                            ArrayList<Chercheur> chercheursTrouvePourEtudiants = new ArrayList<>(); 
                            ArrayList<Set<Etudiant>> etudiantsParChercheur = new ArrayList<>();
                            HashMap<Etudiant, MCF> couplage = new HashMap<>();
                            // Dans ce cas meme si on a pas reussi a supprimer tous le monde on veut quand meme supprimmer le plus possible
                            if (!chercheurModifie.essaieSupprimerTousEtudiants(etudiantsASupprimerDeCeCherch, tousPersonnes, chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage)){
                                chercheurModifie.supprimerMaxDesEtudiants(chercheursTrouvePourEtudiants, etudiantsParChercheur, couplage);
                                // c'est juste un avertissement
                                JOptionPane.showMessageDialog(this, "Tous les étudiants n'ont pas pu être attribués aux autres encadrants, car aucun autre encadrant compatible n'est disponible.", 
                                                "Attribution partielle", JOptionPane.ERROR_MESSAGE);
                            }

                            for (Etudiant etud: etudiantsAAjouterACeCherch){ // on ajoute les etudiants a ajouter
                                etud.setEncadrant(chercheurModifie);
                            }

                        } else { // Si non on doit juste ajouter (si l'utilisateur a selectionne des etudiants) les etuds selectionne au chercheur
                            for (Etudiant etud: etudiants){
                                etud.setEncadrant(chercheurModifie);
                            }
                        }
                    }
                }
            }
            ModificationPersonnesTerminal.actualiserBaseDeDonnees(tousPersonnes);
            estConfirme = true; 
            this.dispose();
        });
    
        cancelButton.addActionListener(e -> {
        	estConfirme = false;
            this.dispose(); 
        });
    
        this.pack();
        this.setLocationRelativeTo(parent); 
    }

    // En cas de mauvaise selection met en rouge les zones de texte
    private void bordureRouge(JTextField textField, boolean erreur) {
        if (erreur) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); 
        } else {
            textField.setBorder(UIManager.getBorder("TextField.border")); 
        }
    }

    // Methodes pour mettre les nouvelles valeurs dans les tables 
    
    /**
     * Retourne le nom saisi dans le champ de texte correspondant. (Pour mettre a jour les donnees dans les tables des personnes)
     *
     * @return Le nom saisi par l'utilisateur.
     */
    public String getNomSelectionneTable() {
        return nomField.getText();
    }

    /**
     * Retourne le prenom saisi dans le champ de texte correspondant. (Pour mettre a jour les donnees dans les tables des personnes)
     *
     * @return Le prenom saisi par l'utilisateur.
     */
    public String getPrenomSelectionneTable() {
        return prenomField.getText();
    }

    /**
     * Retourne l'age selectionne dans la liste deroulante. (Pour mettre a jour les donnees dans les tables des personnes)
     *
     * @return L'age selectionne par l'utilisateur sous forme de chaine de caracteres.
     */
    public String getAgeSelectionneTable() {
        return (int) ageComboBox.getSelectedItem() + "";
    }

    /**
     * Retourne la ville selectionnee dans la liste deroulante. (Pour mettre a jour les donnees dans les tables des personnes)
     *
     * @return La ville selectionnee par l'utilisateur.
     */
    public String getVilleSelectionneTable() {
        return (String) villeComboBox.getSelectedItem(); 
    }

    /**
     * Indique si l'utilisateur a confirme la modification.
     *
     * @return {@code true} si l'utilisateur a confirme, sinon {@code false}.
     */
    public boolean estConfirme() {
        return estConfirme;
    }

    // Les methodes pour mettre a jour la personne elle meme de la base de donnee
    
    // Pour recuperer la ville que l'utilisateur a selectionne
    private Ville getVilleSelectionnee(ArrayList<Ville> baseDeDonneeVilles) {
        Ville villeSelectionnee = null;
        String villeText = (String) villeComboBox.getSelectedItem();
        for (Ville v: baseDeDonneeVilles){
            if (v.getNomDep().equals(villeText)){
                villeSelectionnee = v;
                break;
            }
        }
        return villeSelectionnee;
    }

    // Pour recuperer les disciplines que l'utilisateur a selectionne pour un titulaire
    private Set<Discipline> getDisciplinesSelectionnees() {
        Set<Discipline> disciplinesSelectionnees = new HashSet<>();
        for (Component comp : disciplinesPanel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    disciplinesSelectionnees.add(Discipline.getDiscipline(checkBox.getText()));
                }
            }
        }
        return disciplinesSelectionnees;
    }

    // Pour recuperer les etudiants que l'utilisateur a selectionne pour un chercheur
    private Set<Etudiant> getEtudiantsSelectionnee(ArrayList<Personne> baseDeDonnees) {
        Set<Etudiant> etudiantsSelectionnees = new HashSet<>();
        for (Component comp : etudiantsPanel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    String etudiantText = checkBox.getText();
                    for (Personne p : baseDeDonnees){  
                        if (p instanceof Etudiant etud && (etud.getNomPrenom() + " (id : " + etud.getID() + ")").equals(etudiantText)){
                            etudiantsSelectionnees.add(etud);
                        }
                        
                    }
                }
            }
        }
        return etudiantsSelectionnees;
    }
}
