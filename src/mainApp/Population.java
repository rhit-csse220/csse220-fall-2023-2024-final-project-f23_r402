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

    public void truncationSelection(double mutationRate){
        this.sortPopulation();
        int initialSize = this.chromosomes.size();
        int i = initialSize-1;
        while (i>=initialSize/2){
            this.chromosomes.remove(i);
            i-=1;
        }
        for (int j = 0; j < initialSize/2; j++){
            Chromosome newGenChromosome = new Chromosome(this.chromosomes.get(j).getChromosomeDataAsString());
            newGenChromosome.mutateGenes(mutationRate);
            this.chromosomes.add(newGenChromosome);
        }
        this.giveFitness();
    }

    //Debugger to check if the sorting by fitness gave the correct result
    public void giveFitness(){
        this.sortPopulation();
        for (Chromosome chromosome : this.chromosomes){
            System.out.println(chromosome.getFitnessScore());
        }
        System.out.println(this.chromosomes.size());
    }
}
