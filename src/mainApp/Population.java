package mainApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
    public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100;
    private int genomeLength = 100;
    public double prevC,nextC, prevCLow, nextCLow;
    public int prevCAvg,nextCAvg;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<>();

    Random r = new Random(); // TODO: do we need to seed this as well

    public Population(){}

    public Population(int sizeOfPopulation, int genomeLength){
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.initiatePopulation();
    }

    public void initiatePopulation(){
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


    // roulette selection
    public void rouletteSelection(double mutationRate){
        ArrayList<Chromosome> currentChromosomes = new ArrayList<Chromosome>(chromosomes);
        ArrayList<Chromosome> chosenChromosomes = findRouletteScore(currentChromosomes, new ArrayList<Chromosome>());
        chromosomes = new ArrayList<Chromosome>();
        for (int i = 0; i < chromosomes.size(); i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate));
            chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate));
        }
        this.giveFitness();
    }

    public ArrayList<Chromosome> findRouletteScore(ArrayList<Chromosome> currentChromosomes, ArrayList<Chromosome> chosenChromosomes){
        if (currentChromosomes.size() == chosenChromosomes.size()){
            return chosenChromosomes;
        }

        ArrayList<Double> rouletteScores = new ArrayList<Double>();

        // find total fitness Score
        double totalFitness = 0;
        for (Chromosome chromosome : currentChromosomes){
            totalFitness += chromosome.getFitnessScore();
        }

        // find pctg range for each chromosome based of their fitness score
        double currNum = 0;
        for (Chromosome chromosome : currentChromosomes){
            currNum += chromosome.getFitnessScore()/totalFitness;
            rouletteScores.add(currNum);
        }

        // chose random chromosome
        double randNum = r.nextDouble(0,1);
        for (int i = 0; i < rouletteScores.size(); i++){
            if (rouletteScores.get(i) >= randNum){
                chosenChromosomes.add(currentChromosomes.get(i));
                currentChromosomes.remove(i);
                return findRouletteScore(currentChromosomes, chosenChromosomes);
            }
        }
        return null;
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
        // this.sortPopulation();
        for (Chromosome chromosome : this.chromosomes){
            System.out.println(chromosome.getFitnessScore());
        }
        System.out.println(this.chromosomes.size());
    }
}
