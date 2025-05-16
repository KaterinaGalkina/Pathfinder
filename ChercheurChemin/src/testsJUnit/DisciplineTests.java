package testsJUnit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;

import peuple.Discipline;

/**
 * Classe de test pour la classe {@link Discipline}.
 * Cette classe utilise JUnit 5 pour verifier le comportement de la methode {@code getDiscipline}.
 */
public class DisciplineTests {
	
	/**
     * Test parametre pour verifier le comportement de la methode {@code getDiscipline}.
     *
     * @param disciplineText le texte representant la discipline a verifier.
     * @param discipline l'objet {@link Discipline} attendu apres appel de la methode.
     */
    @ParameterizedTest
    @CsvSource({"mathematiques, MATHEMATIQUES, true", 
                "MaThEmAtIqUeS, MATHEMATIQUES, true", 
                "sciences sociales, SCIENCESSOCIALES, true", 
                "SCIENCESSOCIALES, SCIENCESSOCIALES, true", 
                "DroiT, DROIT, true"})
    public void testGetDiscipline(String disciplineText, Discipline discipline){
        assertEquals(Discipline.getDiscipline(disciplineText),discipline);
    }
}