package mainApp;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
    public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100;
    private int genomeLength = 100;
    public double prevC,nextC, prevCLow, nextCLow;
    public int prevCAvg,nextCAvg;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<>();

    public Population(){
    }

    public Population(int sizeOfPopulation, int genomeLength){
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.initiatePopulation();
    }

    public void initiatePopulation(){
        // chromosomes.removeAll(chromosomes);
        chromosomes = new ArrayList<Chromosome>();
        lineArray = new ArrayList<BestFitLine2D>();
        for (int i = 0; i<sizeOfPopulation; i++){
            chromosomes.add(new Chromosome(genomeLength));
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
        prevC = this.chromosomes.get(0).getFitnessScore();
        prevCAvg = calculateAvgFitness();
        prevCLow = this.chromosomes.get((initialSize/2)-1).getFitnessScore();
        lineArray.add(new BestFitLine2D(prevC, prevCAvg, prevCLow));
        ArrayList<Chromosome> cloneArray = new ArrayList<>();
        for (Chromosome c : this.chromosomes){
            cloneArray.add(new Chromosome(c.getChromosomeDataAsString()));
        }
        this.chromosomes = new ArrayList<Chromosome>();
        for (int j = 0; j < initialSize/2; j++){
            for (int copy2 = 0; copy2 < 2; copy2++){
                Chromosome newGenChromosome = new Chromosome(cloneArray.get(j).getChromosomeDataAsString());
                newGenChromosome.mutateGenes(mutationRate);
                this.chromosomes.add(newGenChromosome);
        }
        }
        this.sortPopulation();
        // nextC = this.chromosomes.get(0);
        // nextCAvg = calculateAvgFitness();
        // nextCLow = this.chromosomes.get(this.chromosomes.size()-1);
        //this.giveFitness();
    }

    public int calculateAvgFitness(){
        int avg = 0;
        for (Chromosome chromosome : chromosomes){
            avg+= chromosome.getFitnessScore();
        }
        avg = avg/chromosomes.size();
        return avg;
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
