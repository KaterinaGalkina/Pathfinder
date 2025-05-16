package testsJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import filtrage.Trie;
import peuple.*;
import territoire.Ville;

/**
 * Classe de test pour la classe {@link Trie}.
 * Cette classe verifie les fonctionnalites de tri basees sur des criteres
 * specifiques comme la discipline, l'age ou la ville.
 */
public class TrieTests {
    private ArrayList<Personne> population;
    private ArrayList<Ville> villes;

    /**
     * Cette methode initialise une petite base de donnees de personnes et de villes avant chaque execution de test.
     */
    @BeforeEach
    public void initPopulation(){
        villes = new ArrayList<>();
        villes.add(new Ville("LILLE", "59000-59160-59260-59777-59800", 225800, 34.83, "59"));
        villes.add(new Ville("ORLEANS", "45000-45100", 113300, 27.48, "45"));
        villes.add(new Ville("RENNES", "35000-35200-35700", 206700, 50.39, "35"));

        population = new ArrayList<>();
        population.add(new MCF("Durand", "Paul", 45, villes.get(0), Set.of(Discipline.INFORMATIQUE), 101, null));
        population.add(new Chercheur("Lemoine", "Sophie", 38, villes.get(1), Set.of(Discipline.INFORMATIQUE, Discipline.MATHEMATIQUES), 102, null));
        population.add(new MCF("Bernard", "Luc", 50, villes.get(0), Set.of(Discipline.GESTION, Discipline.MATHEMATIQUES), 103, null));
  
        population.add(new Etudiant("Dupont", "Jean", 25, villes.get(2), "Optimisation des réseaux", Discipline.INFORMATIQUE, 2, null));
        ((Etudiant) (population.get(population.size()-1))).setEncadrant((Titulaire) population.get(1));
        population.add(new Etudiant("Martin", "Claire", 22, villes.get(0), "Analyse statistique", Discipline.MATHEMATIQUES, 1, null));
        ((Etudiant) (population.get(population.size()-1))).setEncadrant((Titulaire) population.get(1));
        population.add(new Etudiant("Roux", "Alice", 23, villes.get(1), "Gestion des risques", Discipline.GESTION, 3, null));
        ((Etudiant) (population.get(population.size()-1))).setEncadrant((Titulaire) population.get(2));
    }

    /**
     * Teste la methode {@code TrieDisciplineET} pour verifier qu'elle retourne
     * correctement les personnes appartenant a toutes les disciplines de la liste donnee.
     */
    @Test
    public void testTrieDisciplineET(){
        ArrayList<Personne> selectionTrouvee = Trie.TrieDisciplineET(population, new ArrayList<>(List.of(Discipline.INFORMATIQUE, Discipline.MATHEMATIQUES)));
        ArrayList<Personne> selectionCorrecte = new ArrayList<>(List.of(population.get(1)));
        assertEquals(selectionCorrecte, selectionTrouvee);
    }

    /**
     * Teste la methode {@code TrieDisciplineOU} pour verifier qu'elle retourne
     * correctement les personnes appartenant a au moins une discipline parmi la liste donnee.
     */
    @Test
    public void testTrieDisciplineOU(){
        ArrayList<Personne> selectionTrouvee = Trie.TrieDisciplineOU(population, new ArrayList<>(List.of(Discipline.INFORMATIQUE, Discipline.MATHEMATIQUES)));
        ArrayList<Personne> selectionCorrecte = new ArrayList<>(List.of(population.get(0), population.get(1), population.get(2), population.get(3), population.get(4)));
        assertEquals(selectionCorrecte, selectionTrouvee);
    }

    /**
     * Teste la methode {@code TrieAge} pour verifier qu'elle retourne correctement
     * les personnes dont l'age est compris entre deux bornes specifiees.
     */
    @Test 
    public void testTrieAge(){
        ArrayList<Personne> selectionTrouvee = Trie.TrieAge(population, 40, 100);
        ArrayList<Personne> selectionCorrecte = new ArrayList<>(List.of(population.get(0), population.get(2)));
        assertEquals(selectionCorrecte, selectionTrouvee);
    }

    /**
     * Teste la methode {@code TrieAge} avec des bornes incorrectes (max < min)
     * pour verifier qu'elle retourne une liste vide.
     */
    @Test 
    public void testTrieAgeBornesIncorrectes(){
        ArrayList<Personne> selectionTrouvee = Trie.TrieAge(population, 40, 0);
        ArrayList<Personne> selectionCorrecte = new ArrayList<>();
        assertEquals(selectionCorrecte, selectionTrouvee);
    }

    /**
     * Teste la methode {@code TrieVille} pour verifier qu'elle retourne
     * correctement les personnes residant dans une des villes specifiees.
     */
    @Test 
    public void testTrieVille(){
        ArrayList<Personne> selectionTrouvee = Trie.TrieVille(population, new ArrayList<>(List.of(villes.get(0).getNom().toLowerCase(), villes.get(1).getNom().toLowerCase())));
        ArrayList<Personne> selectionCorrecte = new ArrayList<>(List.of(population.get(0), population.get(1), population.get(2), population.get(4), population.get(5)));
        assertEquals(selectionCorrecte, selectionTrouvee);
    }

    /**
     * Nettoie les donnees apres chaque test.
     */
    @AfterEach
    public void undefPopulation(){
        population = null;
        villes = null;
    }

}