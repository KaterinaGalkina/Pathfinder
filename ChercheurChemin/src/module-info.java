/**
 * Ce module represente l'application Chercheur Chemin.
 * <p>
 * Il contient des fonctionnalites pour trouver le meilleur chemin
 * pour parcourir un ensemble des personnes dans un ensemble des villes en utilisant un algorithme genetique.
 * </p>
 * 
 * Dependances :
 * <ul>
 *   <li><code>java.sql</code> : pour la gestion des bases de donnees des villes.</li>
 *   <li><code>java.desktop</code> : pour la version avec l'interface graphique.</li>
 *   <li><code>org.junit.jupiter.api</code> : pour les tests JUnit.</li>
 *   <li><code>org.junit.platform.launcher</code> : pour les tests JUnit.</li>
 *   <li><code>jorg.junit.jupiter.params</code> : pour les tests JUnit.</li>
 * </ul>
 * 
 * @author Lou Toubiana et Ekaterina Galkina
 * @version 1.0
 */
module PathFinder {
	requires java.sql;
	requires java.desktop;
	requires org.junit.jupiter.api;
    requires org.junit.platform.launcher;
    requires org.junit.jupiter.params;
}