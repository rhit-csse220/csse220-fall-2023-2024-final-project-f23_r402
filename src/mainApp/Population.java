package mainApp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
    public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100; // default
    private int genomeLength = 100; // default
    public double prevBestFitness, nextBestFitness, prevLowFitness, nextLowFitness;
    public int prevAvgFitness, nextAvgFitness;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<>();

    // Seeding the Random object
    Random r = new Random(1000);

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

    /**
     * sorts the chromosomes ArrayList from highest to lowest fitness score
     */
    public void sortPopulation(){
        Collections.sort(chromosomes);
    }

    /**
     * creates a BestFitLine2D object based on the previous best, average, and low fitness and add to lineArray
     */
    public void createLine(){
        // find previous best + avg + lowest fitness
        prevBestFitness = this.chromosomes.get(0).getFitnessScore();
        prevAvgFitness = calculateAvgFitness();
        prevLowFitness = this.chromosomes.get(chromosomes.size()-1).getFitnessScore();
        lineArray.add(new BestFitLine2D(prevBestFitness, prevAvgFitness, prevLowFitness));
    }

    /**
     * performs truncation/roulette/ranked selection on the current population and updates this.population to the new population
     * @param mutationRate
     * @param selectionType 0 for truncation, 1 for roulette, 2 for ranked
     */
    public void performSelection(double mutationRate, int selectionType){
        // sort population
        this.sortPopulation();

        // create line
        this.createLine();

        ArrayList<Chromosome> currentChromosomes = new ArrayList<Chromosome>(chromosomes);
        ArrayList<Chromosome> chosenChromosomes = new ArrayList<Chromosome>();
        if (selectionType == 0){
            chosenChromosomes = findTruncationList(currentChromosomes);
        } else if (selectionType == 1){
            chosenChromosomes = findRouletteList(currentChromosomes, new ArrayList<Chromosome>());
        } else if (selectionType == 2){
            chosenChromosomes = findRankedList(currentChromosomes, new ArrayList<Chromosome>());
        } else{
            throw new InvalidParameterException();
        }
       
        int initialSize = this.chromosomes.size();
        chromosomes = new ArrayList<Chromosome>();
        for (int i = 0; i < initialSize/2; i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate));
            chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate));
        }
        // sort population
        this.sortPopulation();

        // create line
        this.createLine();
    }

    /**
     * calculates the population after truncation selection
     * @param currentChromosomes
     * @return an ArrayList of chromosomes that have been chosen with the truncation selection
     */
    public ArrayList<Chromosome> findTruncationList(ArrayList<Chromosome> currentChromosomes){
        int middleIndex = this.chromosomes.size()/2;
        for (int i = this.chromosomes.size()/2; i < this.chromosomes.size(); i++){
            currentChromosomes.remove(middleIndex);
        }
        return currentChromosomes;
    }

    /**
     * calculates the population after roulette selection
     * @param currentChromosomes
     * @param chosenChromosomes
     * @return an ArrayList of chromosomes that have been chosen with the roulette selection
     */
    public ArrayList<Chromosome> findRouletteList(ArrayList<Chromosome> currentChromosomes, ArrayList<Chromosome> chosenChromosomes){
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
                break;
            }
        }
        currentChromosomes.removeAll(chosenChromosomes);
        return findRouletteList(currentChromosomes, chosenChromosomes);
    }

    /**
     * calculates the population after ranked selection
     * @param currentChromosomes
     * @param chosenChromosomes
     * @return an ArrayList of chromosomes that have been chosen with the ranked selection
     */
    public ArrayList<Chromosome> findRankedList(ArrayList<Chromosome> currentChromosomes, ArrayList<Chromosome> chosenChromosomes){
        if (currentChromosomes.size() == chosenChromosomes.size()){
            return chosenChromosomes;
        }

        ArrayList<Double> rankedScores = new ArrayList<Double>();

        // find total fitness Score
        double totalScore = (1 + currentChromosomes.size()) * (currentChromosomes.size()/2.0);

        // find pctg range for each chromosome based of their fitness score
        double currNum = 0;
        for (int i = 0; i < currentChromosomes.size(); i++){
            currNum += (currentChromosomes.size() - i)/totalScore;
            rankedScores.add(currNum);
        }

        // chose random chromosome
        double randNum = r.nextDouble(0,1);
        for (int i = 0; i < rankedScores.size(); i++){
            if (rankedScores.get(i) >= randNum){
                chosenChromosomes.add(currentChromosomes.get(i));
                break;
            }
        }
        currentChromosomes.removeAll(chosenChromosomes);
        return findRankedList(currentChromosomes, chosenChromosomes);
    }

    /**
     * calculates the average fitness of the population
     * @return average fitness of the population
     */
    public int calculateAvgFitness(){
        int avg = 0;
        for (Chromosome chromosome : chromosomes){
            avg+= chromosome.getFitnessScore();
        }
        avg = avg/chromosomes.size();
        return avg;
    }

    // Debugger methods

    // to check the fitness score of chromosomes in the ArrayList
    // public void printArrayList(ArrayList<Chromosome> chromosomes){
    //     for (Chromosome c : chromosomes){
    //         System.out.print(c.getFitnessScore() + ", ");
    //     }
    //     System.out.println();
    // }

    // to check if the sorting by fitness gave the correct result
    // public void Fitness(){
    //     // this.sortPopulation();
    //     for (Chromosome chromosome : this.chromosomes){
    //         System.out.println(chromosome.getFitnessScore());
    //     }
    //     System.out.println(this.chromosomes.size());
    // }
}
