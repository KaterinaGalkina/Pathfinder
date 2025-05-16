/**
 * Ce package contient des classes pour representer l'ecosysteme des personnes.
 *
 * <p>Les classes du package :</p>
 * <ul>
 *   <li>{@link Personne} : Classe abstraite representant une personne. Elle contient des attributs de base 
 *       (nom, prenom, age et ville ou cette personne habite) ainsi que des methodes pour les manipuler.</li>
 *   <li>{@link Titulaire} : Classe abstraite (heritee de {@link Personne}) representant un titulaire 
 *       (MCF ou Chercheur). Elle contient des attributs supplementaires propres a ce type de personne 
 *       (numero de bureau, disciplines) et des methodes pour les manipuler.</li>
 *   <li>{@link MCF} : Classe representant un MCF (heritee de {@link Titulaire}). 
 *       Elle contient un attribut supplementaire : l'etudiant (l'etudiant en these encadre par ce MCF) 
 *       ainsi que des methodes pour le manipuler.</li>
 *   <li>{@link Chercheur} : Classe representant un Chercheur (heritee de {@link Titulaire}). 
 *       Elle contient un attribut supplementaire : les etudiants (la liste des etudiants en these encadres par ce Chercheur) 
 *       ainsi que des methodes pour les manipuler.</li>
 *   <li>{@link Etudiant} : Classe representant un etudiant (heritee de {@link Personne}). 
 *       Elle contient des attributs supplementaires : annee de these, sujet de these, discipline et encadrant, 
 *       ainsi que des methodes pour les manipuler.</li>
 *   <li>{@link Discipline} : Enumeration representant toutes les disciplines que les personnes peuvent avoir dans cette base de donnees.</li>
 * </ul>
 *
 * <p>Ces classes definissent des methodes de base permettant de manipuler et modifier la base de donnees 
 * des personnes tout en preservant sa coherence.</p>
 *
 * @author Lou Toubiana et Ekaterina Galkina
 * @version 1.0
 */
package peuple;