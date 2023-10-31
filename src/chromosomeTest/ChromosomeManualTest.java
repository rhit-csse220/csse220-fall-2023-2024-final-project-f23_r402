package chromosomeTest;

import java.util.Scanner;

import mainApp.Population;

public class ChromosomeManualTest {

    public static void testSortingChromosomes() {
        // This test is checked manually because populations are created randomly
        Population p = new Population(10, 10);
        System.out.println(p.getChromosomes());
        p.sortPopulation();
        System.out.println(p.getChromosomes());

        System.out.println("Please verify the latter arraylist is the sorted former arraylist");
    }

    public static void testTruncation() {
        Population p = new Population(10, 10);
        System.out.println("Before truncation: " + p.getChromosomes());
        p.performSelection(1, 0);  // Truncation with mutation rate 1
        System.out.println("After truncation:  " + p.getChromosomes());
    }

    public static void main(String[] args) {
        testSortingChromosomes();
        System.out.println("------------------");
        testTruncation();
    }
    
}
