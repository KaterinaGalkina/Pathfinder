/**
 * Ce package contient des classes auxiliaires pour la gestion de la base de donnees de personnes et de villes.
 *
 * <p>Les classes du package :</p>
 * <ul>
 *   <li>{@link ChargementDonnees} : Cette classe permet de recupere les informations stockes sur les villes et sur les personnes.</li>
 *   <li>{@link ModificationPersonnesTerminal} : Cette classe est utilisee pour permettre 
 *   	 a l'utilisateur de modifier la base de donnes de prsonnes a travers le terminal.</li>
 *   <li>{@link ListeEcosysteme} : Represente une liste chainee dans laquelle les valeurs sont des personnes de type Personne.
 *       Cette classe est utilisee comme un objet que l'on stocke dans un fichier.</li>
 * </ul>
 * 
 * <p>Les fichiers du package :</p>
 * <ul>
 *   <li>ecosysteme : Stocke la base de donnees de personnes sous forme d'un objet de type {@link ListeEcosysteme}.</li>
 *   <li>ecosystemeCopie : La copie du fichier {@code ecosysteme} pour que en cas de plantage 
 *       du programme ou de mauvais changements pouvoir quand meme de recuperer la base de donnees.</li>
 * </ul>
 *
 * @author Lou Toubiana et Ekaterina Galkina
 * @version 1.0
 */
package baseDeDonnees;