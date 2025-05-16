package affichage;

import java.awt.*;
import javax.swing.*;
/**
 * Classe principale representant la fenetre de l'application.
 * @version 1.0
 */
public class Frame extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la fenetre principale.
     */
    public Frame(){
        this.setTitle("Voyageurs de Commerce");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
    }
    /**
     * Efface tout le contenu de la fenetre.
     */
    public void effacer(){
        this.getContentPane().removeAll();
        this.revalidate();
        this.repaint();
    }
}

