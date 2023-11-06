package chromosomeTest;

import java.util.Scanner;

import mainApp.Population;

/**
 * Class: ChromosomeManualTest
 * @author anisima
 * 
 * Purpose: test cases that cannot be done with JUnit
 * Example:
 *   Run As -> Java Application
 */
public class ChromosomeManualTest {

    /**
     * ensures: tests that Chromosomes within a Population are sorted correctly
     */
    public static void testSortingChromosomes() {
        Population p = new Population(10, 10);
        System.out.println(p.getChromosomes());
        p.sortPopulation();
        System.out.println(p.getChromosomes());

        System.out.println("Please verify the latter arraylist is the sorted former arraylist");
    }

    /**
     * ensures: tests that Chromosomes within a Population are truncated correctly
     */
    public static void testTruncation() {
        Population p = new Population(10, 10);
        System.out.println("Before truncation: " + p.getChromosomes());
        p.performSelection(1, 0, 0, true);  // Truncation with mutation rate 1, elitism 0, and crossover
        System.out.println("After truncation:  " + p.getChromosomes());
    }

    /**
     * ensures: runs all tests
     * @param args unused
     */
    public static void main(String[] args) {
        testSortingChromosomes();
        System.out.println("------------------");
        testTruncation();
    }
    
}
