/**
 * Ce package contient des classes et interfaces utilisees pour implementer un algorithme genetique pour resoudre le 
 * probleme du plus court chemin hamiltonien a partir d'une liste de villes.
 * 
 * Le package fournit des structures qui permettent de generer et d'evaluer des solutions possibles (chemins) via des mecanismes 
 * de croisement, mutation et selection, pour optimiser la distance totale parcouru.
 *
 * <p>Les classes du package :</p>
 * <ul>
 *   <li>{@link Chemin} : Cette classe represente un chemin (une sequence de villes) et fournit des methodes specifiques a villes pour
 *   l'algorithme genetique.</li>
 *   <li>{@link Individu} : Classe abstraite qui represente un individu dans la population. Elle definit des methodes de base 
 *   pour l'evaluation du score, le clonage, et l'implementation des operations genetiques de croisement et de mutation.</li>
 *   <li>{@link Population} : Cette classe contient une collection d'individus et implemente des methodes pour gerer l'evolution 
 *   de la population pour l'algorithme genetique (par exemple, selection, remplacement, evaluation).</li>
 * </ul>
 * 
 * <p>Les interfaces du package :</p>
 * <ul>
 *   <li>{@link Croisement} : Cette interface definit la methode pour effectuer des operations de croisement 
 *   entre deux individus parents pour de creer deux nouveaux enfants.</li>
 *   <li>{@link Mutation} : Cette interface definit la methode pour effectuer des operations de mutations sur un individu.</li>
 *   <li>{@link Selection} : Cette interface definit la logique de l'algorithme genetique pour calculer le meilleur chemin.</li>
 * </ul>
 *
 * @author Lou Toubiana et Ekaterina Galkina
 * @version 1.0
 */
package genetique;