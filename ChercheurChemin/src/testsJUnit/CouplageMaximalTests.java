package testsJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import objetsAuxiliaires.GrapheBiparti;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Classe de tests pour la fonctionnalite de couplage maximal dans la classe GrapheBiparti.
 */
public class CouplageMaximalTests {
	private GrapheBiparti<Integer, Integer> graphe;
	private HashMap<Integer, Integer> couplageAttendu;

    /**
     * Cette methode initialise un petit graphe biparti avant chaque execution de test.
     */
    @BeforeEach
    public void initGrapheBiparti(){
    	Set<Integer> ens1 = new HashSet<>();
    	ens1.add(1);
    	ens1.add(3);
    	ens1.add(5);
    	Set<Integer> ens2 = new HashSet<>();
    	ens2.add(2);
    	ens2.add(4);
    	ens2.add(6);
    	HashMap<Integer, Set<Integer>> aretes = new HashMap<>();
    	aretes.put(1, Set.of(2,4));
    	aretes.put(3, Set.of(4));
    	aretes.put(5, Set.of(4,6));
    	graphe = new GrapheBiparti<>(ens1, ens2, aretes);
    	
    	couplageAttendu = new HashMap<>();
    	couplageAttendu.put(1, 2);
    	couplageAttendu.put(3, 4);
    	couplageAttendu.put(5, 6);
    }
    
    /**
     * Test de la methode couplageMaximal pour verifier qu'elle renvoie le couplage attendu.
     */
    @Test
    public void testCouplageMaximal(){
        assertEquals(couplageAttendu, graphe.couplageMaximal());
    }
    
    /**
     * Nettoie les donnees apres chaque test.
     */
    @AfterEach
    public void undefGrapheBiparti(){
    	graphe = null;
    }
    
}
