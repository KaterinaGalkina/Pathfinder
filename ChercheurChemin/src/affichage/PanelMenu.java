package affichage;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import peuple.*;
import territoire.*;
/**
 * Cette classe represente le menu principal de l'application.
 * @version 1.0
 */
public class PanelMenu extends JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur du menu principal.
     * @param frame Fenetre principale de l'application.
     * @param selection Liste des personnes selectionnees.
     * @param personnes Liste de toutes les personnes.
     * @param villes Liste des villes disponibles.
     * @param regions Liste des regions disponibles.
     */
    public PanelMenu(Frame frame, ArrayList<Personne> selection, ArrayList<Personne> personnes, ArrayList<Ville> villes, ArrayList<Region> regions){
        this.setLayout(new BorderLayout()); //en colonne

        JPanel panelTop = new PanelTop(frame, selection, personnes, villes, regions);
        
        JPanel PanelBas = new JPanel();
        PanelBas.setBackground(Color.WHITE);

        ImageIcon originalIcon = new ImageIcon("src/affichage/ImageMenu.jpg");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(700, 500, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        PanelBas.add(new JLabel(resizedIcon));
        
        this.add(panelTop, BorderLayout.NORTH);
        this.add(PanelBas, BorderLayout.CENTER);
        }
}