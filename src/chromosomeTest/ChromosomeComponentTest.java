package chromosomeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import mainApp.Chromosome;
import mainApp.ChromosomeComponent;
import mainApp.Gene;
import mainApp.InvalidChromosomeFormatException;

public class ChromosomeComponentTest {
    @Test
    public void testChromosomeComponentInitialization() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        assertNotNull(chromosome); // Make sure the chromosome is initialized
        assertNotNull(chromosome.getGenes()); // Make sure genes are initialized
        assertEquals(100, chromosome.getNumOfGenes());
    }

    
    @Test(expected = InvalidChromosomeFormatException.class)
    public void testHandleStoreInvalidChromosomeData() throws InvalidChromosomeFormatException {
        ChromosomeComponent component = new ChromosomeComponent();
        // Chromosome chromosome = component.getChromosome();

        // Store chromosome data using the method
        component.handleLoadDataFromFile("100");
        // component.handleStoreChromosomeData("100");

        // Check if the data is correctly stored in the chromosome
        // assertTrue(chromosome.checkChromosomeData());
        // assertEquals("100", chromosome.getChromosomeDataAsString());
    }

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
