package chromosomeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import mainApp.Chromosome;
import mainApp.ChromosomeComponent;
import mainApp.Gene;

public class ChromosomeComponentTest {
    @Test
    public void testChromosomeComponentInitialization() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        assertNotNull(chromosome); // Make sure the chromosome is initialized
        assertNotNull(chromosome.genes); // Make sure genes are initialized
        assertEquals(100, chromosome.getNumOfGenes());
    }

    
    @Test
    public void testHandleStoreChromosomeData() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        // Store chromosome data using the method
        component.handleStoreChromosomeData("100");

        // Check if the data is correctly stored in the chromosome
        assertTrue(chromosome.checkChromosomeData());
        assertEquals("100", chromosome.getChromosomeDataAsString());
    }


    @Test
    public void testContainsGene() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        // Create a mock gene
        Gene mockGene = new Gene('0', true, 0, 0, Gene.GENE_SIDE);

        // Set the chromosome genes to the mock gene
        chromosome.genes = new Gene[] { mockGene };

        // Call containsGene with coordinates within the mock gene
        Gene selectedGene = component.containsGene(mockGene.getX() + Gene.GENE_SIDE / 2, mockGene.getY() + Gene.GENE_SIDE / 2);

        // Verify that the selected gene is the mock gene
        assertNotNull(selectedGene);
        assertEquals(mockGene.getBit(), selectedGene.getBit());
    }

    @Test
    public void testHandleInitiateGeneWithFile() {
        ChromosomeComponent component = new ChromosomeComponent();
        Chromosome chromosome = component.getChromosome();

        // Store chromosome data
        component.handleStoreChromosomeData("0011001100");

        // Set chromosome genes to null
        chromosome.genes = null;

        // Call handleInitiateGeneWithFile
        component.handleInitiateGeneWithFile();

        // Verify that genes are initialized
        assertNotNull(chromosome.genes);
        assertEquals(10, chromosome.getNumOfGenes());
    }
}
