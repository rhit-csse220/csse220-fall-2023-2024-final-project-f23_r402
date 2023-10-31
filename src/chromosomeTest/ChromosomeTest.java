package chromosomeTest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.Test;

import mainApp.Chromosome;
import mainApp.Gene;
import mainApp.InvalidChromosomeFormatException;
import mainApp.Population;


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
    public void testPrintOutChromosome() {
        Chromosome c = new Chromosome();
        String genomeData = "0011001100";
        try {
            c.initiateGeneWithString(genomeData);
        } catch (InvalidChromosomeFormatException e) {
            fail("Threw InvalidChromosomeFormatException");
        }

        // Testing we actually can print a Chromosome
        System.out.println(c);
        // Testing it prints out the correct data
        assertEquals(c.toString(), genomeData);
    }

    @Test
    public void testCalculateFitnessScore() {
        HashMap<String, Double> genomesToFitnessScores = new HashMap<>() {{
            put("0011100110", 50.0);
            put("11111111111111111011", 19.0 / 20 * 100);
            put("000000000000000000000000000000", 0.0);
            put("1111111111", 100.0);
        }};

        for (String genome : genomesToFitnessScores.keySet()) {
            Chromosome c = null;
            try {
                c = new Chromosome(genome);
            } catch (InvalidChromosomeFormatException e) {
                fail("Threw InvalidChromosomeFormatException");
            }
            assertEquals(genomesToFitnessScores.get(genome), Double.valueOf(c.getFitnessScore()));
        }
    }

    @Test
    public void testStoreChromosomeData() {
        Chromosome c = new Chromosome();
        String genomeData = "00110011010011001011";
        try {
            c.initiateGeneWithString(genomeData);
        } catch (InvalidChromosomeFormatException e) {
            fail("Threw InvalidChromosomeFormatException");
        }

        // Testing it stores the correct data
        assertEquals(c.getChromosomeDataAsString(), genomeData);
        // Testing it prints and stores the same values
        assertEquals(c.getChromosomeDataAsString(), c.toString());
    }

    @Test(expected = InvalidChromosomeFormatException.class)
    public void testCreateChromosomeWithInvalidNumberOfGenes() throws InvalidChromosomeFormatException {
        Chromosome c = new Chromosome("01010");
    }

    @Test(expected = InvalidChromosomeFormatException.class)
    public void testInitialiazeChromosomeWithInvalidNumberOfGenes() throws InvalidChromosomeFormatException {
        Chromosome c1 = new Chromosome();
        c1.initiateGeneWithString("000101010");
    }

    @Test
    public void runAllManualTests() {
        // Don't forget to run ChromosomeManualTest.java
        assertTrue(true);
    }

    // @Test
    // public void testStoreChromosomeData() {
    //     Chromosome chromosome = new Chromosome();
    //     // Create genes with the desired sequence
    //     chromosome.genes = new Gene[10];
    //     for (int i = 0; i < 10; i++) {
    //         char bit = "0011001100".charAt(i);
    //         chromosome.genes[i] = new Gene(bit, true, 0, 0, 30); // Set x and y to 0 for simplicity
    //     }

    //     chromosome.storeChromosomeData("0011001100");
    //     assertEquals("0011001100", chromosome.getChromosomeDataAsString());
    // }



//    @Test
//    public void testCalcFunction() {
//        Chromosome chromosome = new Chromosome();
//        chromosome.initiateGene(); // Initialize genes
//        chromosome.calcFuction(); // Calculate fitness score (your implementation)
//        // Check if the fitness score is as expected
//        // You may need to update this based on your implementation
//        assertNotNull(chromosome.getFitnessScore());
//    }

    // @Test
    // public void testInitiateGeneWithRandomData() {
    //     Chromosome chromosome = new Chromosome();
    //     chromosome.initiateGene(); // Initialize genes with random data
    //     assertNotNull(chromosome.genes);
    // }

    // @Test
    // public void testPrintOutChromosomes() {
    //     // testing new branch
    // }
}

