package chromosomeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import mainApp.Chromosome;
import mainApp.ChromosomeComponent;
import mainApp.Gene;
import mainApp.InvalidChromosomeFormatException;

/**
 * Class: ChromosomeComponentTest
 * @author: anisima and huaj1
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
        // Chromosome chromosome = component.getChromosome();

        // Store chromosome data using the method
        component.handleLoadDataFromFile("100");
        // component.handleStoreChromosomeData("100");

        // Check if the data is correctly stored in the chromosome
        // assertTrue(chromosome.checkChromosomeData());
        // assertEquals("100", chromosome.getChromosomeDataAsString());
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
        // component.handleStoreChromosomeData("100");

        // Check if the data is correctly stored in the chromosome
         Chromosome chromosome = component.getChromosome();
        assertEquals("1001011001", chromosome.getChromosomeDataAsString());
    }

    // Testing the .containsGene() method
    // TODO: fix
    @Test
    public void testContainsGene() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        // Create a mock gene
        Gene mockGene = new Gene('0', true, 0, 0, 30);

        // Set the chromosome genes to the mock gene
        chromosome.setGenes(new Gene[] { mockGene });

        // Call containsGene with coordinates within the mock gene
        Gene selectedGene = component.containsGene(mockGene.getX() + 30 / 2, mockGene.getY() + 30 / 2);

        // Verify that the selected gene is the mock gene
        assertNotNull(selectedGene);
        assertEquals(mockGene.getBit(), selectedGene.getBit());
    }

    // @Test
    // public void testHandleInitiateGeneWithFile() {
    //     ChromosomeComponent component = new ChromosomeComponent();
    //     Chromosome chromosome = component.getChromosome();

    //     // Store chromosome data
    //     component.handleStoreChromosomeData("0011001100");

    //     // Set chromosome genes to null
    //     chromosome.genes = null;

    //     // Call handleInitiateGeneWithFile
    //     component.handleInitiateGeneWithFile();

    //     // Verify that genes are initialized
    //     assertNotNull(chromosome.genes);
    //     assertEquals(10, chromosome.getNumOfGenes());
    // }
}
