/**
 * Ce package contient les classes principales pour la gestion de l'interface graphique de l'application.  
 * Il centralise les panneaux principaux et la fenetre principale afin de fournir une interaction intuitive a l'utilisateur.  
 * 
 * Le package est organise en sous-packages specifiques pour gerer differentes fonctionnalites :  
 * <ul>
 *   <li>{@link affichage.affichageAlgo} : Gere la selection des personnes et l'affichage de l'itineraire optimal.</li>
 *   <li>{@link affichage.affichageEcosysteme} : Gere les modifications de la base de donnees (voir la section correspondante).</li>
 * </ul>
 *
 * <p>Les classes du package :</p>
 * <ul>
 *   <li>{@link Frame} : Cette classe herite de {@code JFrame} et represente la fenetre principale de l'application.</li>
 *   <li>{@link PanelTop} : Un panneau fixe, present sur toutes les vues, affichant les options principales de navigation.</li>
 *   <li>{@link PanelMenu} : Un panneau qui affiche une image.</li>
 * </ul>
 *
 * @author Ekaterina Galkina et Lou Toubiana
 * @version 1.0
 */
package affichage;