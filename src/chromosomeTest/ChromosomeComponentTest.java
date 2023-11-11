package chromosomeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import mainApp.Chromosome;
import mainApp.ChromosomeComponent;
import mainApp.InvalidChromosomeFormatException;

/**
 * Class: ChromosomeComponentTest
 * 
 * Purpose: test the ChromosomeComponentTest class
 * Example:
 *  Run As -> JUnit Test
 */
public class ChromosomeComponentTest {
    // Testing intialization of genes
    @Test
    public void testChromosomeComponentInitialization() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        assertNotNull(chromosome); // Make sure the chromosome is initialized
        assertNotNull(chromosome.getGenes()); // Make sure genes are initialized
        assertEquals(100, chromosome.getNumOfGenes());
    }

    // Testing handinling loading invalid data
    @Test(expected = InvalidChromosomeFormatException.class)
    public void testHandleLoadInvalidChromosomeData() throws InvalidChromosomeFormatException {
        ChromosomeComponent component = new ChromosomeComponent();

        // Store chromosome data using the method
        component.handleLoadDataFromFile("100");
    }

    // Testing handling loading and storing valid data
    @Test
    public void testHandleStoreValidChromosomeData() {
        ChromosomeComponent component = new ChromosomeComponent();

        // Store chromosome data using the method
        try {
            component.handleLoadDataFromFile("1001011001");
        } catch (InvalidChromosomeFormatException e) {
            fail("Threw InvalidChromosomeFormatException");
        }

        // Check if the data is correctly stored in the chromosome
         Chromosome chromosome = component.getChromosome();
        assertEquals("1001011001", chromosome.getChromosomeDataAsString());
    }
}
