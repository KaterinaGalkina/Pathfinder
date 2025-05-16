/**
 * Ce package gere la logique de selection des personnes et l'affichage des resultats apres utilisation de l'algorithme genetique.  
 * Il permet a l'utilisateur de choisir les personnes qu'il souhaite visiter et de visualiser l'itineraire optimal.  
 *
 * Le package contient plusieurs classes et sous-packages pour organiser les fonctionnalites :  
 * <ul>
 *   <li>{@link affichage.affichageAlgo.choixPersonne} : Permet de selectionner les personnes a visiter en fonction de criteres tels que l'age, les disciplines...</li>
 *   <li>{@link affichage.affichageAlgo.choixVille} : Permet de selectionner les personnes a visiter en fonction de leur ville.</li>
 * </ul>
 *
 * <p>Les classes du package :</p>
 * <ul>
 *   <li>{@link PanelAlgo} : fait le lien avec les package ChoixPersonne et ChoixVille. </li>
 *   <li>{@link PanelFin} : Affiche les informations des personnes selectionnees ainsi que l'itineraire optimal calcule.</li>
 *   <li>{@link PanelPersonneVilleDisplay} : 'aide' PanelFin a l'affichage des information.</li>
 *   <li>{@link PanelNombre} : Gere la selection basee sur des criteres numeriques, comme la population ou la superficie.</li>
 *   <li>{@link PanelChoix} : Permet de faire des choix interactifs entre plusieurs options.</li>
 *   <li>{@link PanelValiderSelection} : Permet a l'utilisateur de continuer le tri ou de le valider.</li>
 *   <li>{@link PanelVilleDepart} : Permet de choisir sa ville de depart.</li>
 * </ul>
 *
 * @author Ekaterina Galkina et Lou Toubiana
 * @version 1.0
 */
package affichage.affichageAlgo;