package chromosomeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import mainApp.Chromosome;
import mainApp.Gene;


public class ChromosomeTest {
    // checkChromosomeData method not used
    // @Test
    // public void testCheckChromosomeDataWithValidData() {
    //     Chromosome chromosome = new Chromosome();
    //     chromosome.storeChromosomeData("0011001100");
    //     assertTrue(chromosome.checkChromosomeData());
    // }

    // @Test
    // public void testCheckChromosomeDataWithInvalidData() {
    //     Chromosome chromosome = new Chromosome();
    //     chromosome.storeChromosomeData("0012001100");
    //     assertFalse(chromosome.checkChromosomeData());
    // }

    @Test
    public void testStoreChromosomeData() {
        Chromosome chromosome = new Chromosome();
        // Create genes with the desired sequence
        chromosome.genes = new Gene[10];
        for (int i = 0; i < 10; i++) {
            char bit = "0011001100".charAt(i);
            chromosome.genes[i] = new Gene(bit, true, 0, 0, 30); // Set x and y to 0 for simplicity
        }

        chromosome.storeChromosomeData("0011001100");
        assertEquals("0011001100", chromosome.getChromosomeDataAsString());
    }



//    @Test
//    public void testCalcFunction() {
//        Chromosome chromosome = new Chromosome();
//        chromosome.initiateGene(); // Initialize genes
//        chromosome.calcFuction(); // Calculate fitness score (your implementation)
//        // Check if the fitness score is as expected
//        // You may need to update this based on your implementation
//        assertNotNull(chromosome.getFitnessScore());
//    }

    @Test
    public void testInitiateGeneWithRandomData() {
        Chromosome chromosome = new Chromosome();
        chromosome.initiateGene(); // Initialize genes with random data
        assertNotNull(chromosome.genes);
    }
}

