package mainApp;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
    private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100;

    public Population(){
        this.initiatePopulation();
    }

    public Population(int sizeOfPopulation){
        this.sizeOfPopulation = sizeOfPopulation;
        this.initiatePopulation();
        this.sortPopulation();
    }

    public void initiatePopulation(){
        for (int i = 0; i<sizeOfPopulation; i++){
            chromosomes.add(new Chromosome());
            chromosomes.get(i).initiateGene();
        }
        this.sortPopulation();
    }

    public void sortPopulation(){
        Collections.sort(chromosomes);
    }

    //Debugger to check if the sorting by fitness gave the correct result
    public void giveFitness(){
        this.sortPopulation();
        for (Chromosome chromosome : this.chromosomes){
            System.out.println(chromosome.getFitnessScore());
        }
    }
}
