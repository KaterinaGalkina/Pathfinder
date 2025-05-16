package affichage;

import affichage.affichageAlgo.PanelAlgo;
import affichage.affichageEcosysteme.PanelEcosysteme;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.*;
import territoire.*;
/**
 * Cette classe represente le panneau superieur de l'interface utilisateur.
 * Elle permet de naviguer entre différents écrans via des boutons.
 * @version 1.0
 */
 
public class PanelTop extends JPanel {
    private static final long serialVersionUID = 1L;
    /**
     * Constructeur du panneau superieur.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
	public PanelTop(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new FlowLayout(FlowLayout.CENTER)); // Aligner les boutons en ligne
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(800, 35));
        
        JButton Algo = new JButton("Algorithme");
        JButton Ecosysteme = new JButton("Ecosysteme");
        this.add(Ecosysteme);
        this.add(Algo);
        Algo.addActionListener(e->{
            frame.effacer();
            frame.add(new PanelAlgo(frame, selection, personnes, villes, regions));
            frame.revalidate();
            frame.repaint();
        });
        Ecosysteme.addActionListener(e->{
            if(selection.size()<personnes.size()){
            	JDialog dialog = new JDialog((Frame) null, "Attention", true);
            	dialog.setLayout(new BorderLayout());
            	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            	JLabel messageLabel = new JLabel("Attention: la sélection va être abandonnée", JLabel.CENTER);
            	dialog.add(messageLabel, BorderLayout.CENTER);

            	JPanel buttonPanel = new JPanel(new FlowLayout());
            	JButton continueButton = new JButton("Continuer");
            	JButton cancelButton = new JButton("Annuler");

            	continueButton.addActionListener(i -> {
            	    dialog.dispose();
            	    frame.effacer();
            	    frame.add(new PanelEcosysteme(frame, selection, personnes, villes, regions));
            	    frame.revalidate();
            	    frame.repaint();
            	});

            	cancelButton.addActionListener(i -> dialog.dispose());

            	buttonPanel.add(continueButton);
            	buttonPanel.add(cancelButton);

            	dialog.add(buttonPanel, BorderLayout.SOUTH);

            	dialog.setSize(400, 200);
                // dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            else{
                frame.effacer();
                frame.add(new PanelEcosysteme(frame, selection, personnes, villes, regions));
                frame.revalidate();
                frame.repaint();
            }
        });
    }
}