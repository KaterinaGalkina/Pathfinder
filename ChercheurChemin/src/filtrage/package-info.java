/**
 * Ce package contient des classes permettant de trier et de filtrer les personnes en fonction de divers criteres 
 * afin de faciliter la selection des individus dans une base de donnees.
 * 
 * Le package propose deux classes principales : 
 * l'une centralise les methodes de tri, et l'autre offre une interaction via le terminal pour effectuer des selections 
 * de personnes selon les criteres fournis par l'utilisateur.
 *
 * <p>Les classes du package :</p>
 * <ul>
 *   <li>{@link Trie} : Cette classe regroupe les methodes de tri generiques permettant de filtrer les personnes 
 *   selon des criteres tels que l'age, l'identifiant (ID), la ville, la region, etc. Elle opere sur des listes de 
 *   personnes et retourne celles correspondant aux criteres specifies.</li>
 *   <li>{@link TrieTerminal} : Cette classe fournit une interface de terminal pour permettre a l'utilisateur 
 *   de selectionner des personnes en fonction des criteres definis. Elle utilise les methodes de {@link Trie} 
 *   et s'appuie sur la classe {@link verificationEntree.VerificationTrieTerminal} pour valider les entrees utilisateur.</li>
 * </ul>
 * 
 * <p>Les points importants du package :</p>
 * <ul>
 *   <li>Trie par des criteres multiples : age, fonction, ville, region, sujet de these, etc.</li>
 *   <li>Validation des entrees utilisateur dans le terminal pour garantir la coherence et l'exactitude des donnees.</li>
 *   <li>Modularite : les methodes de tri et de validation sont reutilisables dans differents contextes.</li>
 * </ul>
 *
 * @author Ekaterina Galkina et Lou Toubiana
 * @version 1.0
 */
package filtrage;