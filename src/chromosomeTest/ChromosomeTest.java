package chromosomeTest;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import mainApp.Chromosome;
import mainApp.InvalidChromosomeFormatException;

/**
 * Class: ChromosomeTest
 * @author: anisima and huaj1
 * 
 * Purpose: test the Chromosome class
 * Example:
 *   Run As -> JUnit Test
 */
public class ChromosomeTest {

    // Testing that a chromosome can be printed out
    @Test
    public void testPrintOutChromosome() {
        Chromosome c = new Chromosome();
        String genomeData = "0011001100";
        c.initiateGeneWithString(genomeData);

        // Testing we actually can print a Chromosome
        System.out.println(c);
        // Testing it prints out the correct data
        assertEquals(c.toString(), genomeData);
    }

    // Testing fintness score calculation
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

    // Testing storing genome data witin a chromosome
    @Test
    public void testStoreChromosomeData() {
        Chromosome c = new Chromosome();
        String genomeData = "00110011010011001011";
        c.initiateGeneWithString(genomeData);

        // Testing it stores the correct data
        assertEquals(c.getChromosomeDataAsString(), genomeData);
        // Testing it prints and stores the same values
        assertEquals(c.getChromosomeDataAsString(), c.toString());
    }

    // Additional tests are in ChromosomeManualTest.java
    @Test
    public void runAllManualTests() {
        // Don't forget to run ChromosomeManualTest.java
        assertTrue(true);
    }

}

