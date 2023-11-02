package mainApp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

public class Population {
    public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100; // default
    private int genomeLength = 100; // default
    public double prevBestFitness, prevLowFitness, prevAvgFitness, prevHammingDistance;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<>();

    public int getSizeOfPopulation() {
        return sizeOfPopulation;
    }

    public void setSizeOfPopulation(int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
    }
    
    // Seeding the Random object
    Random r = new Random();

    public Population(){}

    public Population(int sizeOfPopulation, int genomeLength){
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.initiatePopulation();
    }

    public void initiatePopulation(){
        this.chromosomes = new ArrayList<Chromosome>();
        this.lineArray = new ArrayList<BestFitLine2D>();
        for (int i = 0; i < this.sizeOfPopulation; i++){
            this.chromosomes.add(new Chromosome(genomeLength));
            this.chromosomes.get(i).initiateGene();
        }
        this.sortPopulation();
    }

    /**
     * sorts the chromosomes ArrayList from highest to lowest fitness score
     */
    public void sortPopulation(){
        Collections.sort(this.chromosomes);
    }

    /**
     * creates a BestFitLine2D object based on the previous best, average, and low fitness and add to lineArray
     */
    public void createLine(){
        // find previous best + avg + lowest fitness
        this.prevBestFitness = this.chromosomes.get(0).getFitnessScore();
        this.prevAvgFitness = calculateAvgFitness();
        this.prevLowFitness = this.chromosomes.get(this.chromosomes.size()-1).getFitnessScore();
        this.prevHammingDistance = calculateHammingDistance();
        //this.prevHammingDistance = calculateUniqueStrings();
        this.lineArray.add(new BestFitLine2D(this.prevBestFitness, this.prevAvgFitness, this.prevLowFitness, this.prevHammingDistance));
    }

    /**
     * performs truncation/roulette/ranked selection on the current population and updates this.population to the new population
     * @param mutationRate
     * @param selectionType 0 for truncation, 1 for roulette, 2 for ranked
     */
    public void performSelection(double mutationRate, int selectionType, double elitism){
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
        
        // The amount of the most fit population to be retained from the initial collection of chromosomes. 
        int elitistSize = (int)((elitism/100)*initialSize);
        //System.out.println(elitistSize);

        // The collection is initially initialized to the initial collection of chromosomes
        ArrayList<Chromosome> eliteChromosomes = new ArrayList<Chromosome>(chromosomes);

        // This removes all the chromosomes from bottom to top, until only the most fit chromosomes remain from 0 to elitistSize-1, eg. 0 to 9, i.e 10 elements
        for (int i = initialSize-1; i>elitistSize-1; i--){
            eliteChromosomes.remove(i);
        }
        this.chromosomes = new ArrayList<Chromosome>();

        for (int i = 0; i < initialSize/2; i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            try {
                this.chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate));
                this.chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate));
            } catch (InvalidChromosomeFormatException e) {
                // TODO: see if we actually need to do sth here
                e.printStackTrace();
            }
        }

        // This sets the newly initialized generation and replaces the existing chromosomes with the previously elite chromosomes through replacing it at their assigned index
        for (int i = 0; i < elitistSize; i++){
            String currChromosomeData = eliteChromosomes.get(i).getChromosomeDataAsString();
            try {
                this.chromosomes.set(i, new Chromosome(currChromosomeData, false, mutationRate));
            } catch (InvalidChromosomeFormatException e) {
                // TODO: see if we actually need to do sth here
                e.printStackTrace();
            }
        }

        // sort population
        this.sortPopulation();
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
    public double calculateAvgFitness(){
        double avg = 0;
        for (Chromosome chromosome : this.chromosomes){
            avg += chromosome.getFitnessScore();
        }
        avg = avg / this.chromosomes.size();
        return avg;
    }

    //USED THE GUIDE FOR THIS CHECK THE SPECIFICAITONS DOC
    public double calculateHammingDistance(){
        double hammingDistance = 0;
        int[][][] position1n0Array = new int[genomeLength][sizeOfPopulation][sizeOfPopulation];
        int numPairs = (this.sizeOfPopulation)*(this.sizeOfPopulation-1)/2;
        //CALLS THIS PARALLELY AND SOMEHOW MAKES THE FAST EVOLUTION THING WORK WITH IT
        this.chromosomes.parallelStream().forEach(chromosome -> readData1n0(chromosome, position1n0Array));
        for (int i = 0; i < genomeLength; i++){
            hammingDistance+= (position1n0Array[i][0][0]*position1n0Array[i][0][1]);
        }
        //DEBUG TO TEST IF IT IS APPROPRIATE VALUES
        System.out.println(((hammingDistance/(numPairs))/genomeLength));

        // RETURNS THE VALUE THAT CAN THEN BE PLOTTED ON GRAPH, AS ACTUAL VALUE IS TOO SMALL AND WILL BE LIKE 0 
        return ((hammingDistance/(numPairs))/genomeLength)*100;
    }

    // USING THE GUIDE FOR CALC OF HAMMING DISTANCE, CAME UP WITH THIS; FOR THE GIVEN CHARACTER, IT WILL EITHER ADD +1 TO THE NUMBER OF 0s, [i][0][0], or 1s, [i][0][1]; THIS WAS MY BS WAY U GUYS CAN PROBABLY THINK OF SMTH BETTER
    public void readData1n0(Chromosome chromosome, int[][][] position1n0Array){
        String geneticData = chromosome.getChromosomeDataAsString();
        for (int i = 0; i < geneticData.length(); i++){
            if (geneticData.charAt(i)=='0'){
                position1n0Array[i][0][0]++;
            }
            else{
                position1n0Array[i][0][1]++;
            }
        }
    }

    // Was another measure of diversity but idk how to code it 
    // public double calculateUniqueStrings(){
    //     int uniqueCount = 0;
    //     for (int i = 1; i < sizeOfPopulation; i++){
    //         if (!this.chromosomes.get(i).getChromosomeDataAsString().equals(this.chromosomes.get(i-1).getChromosomeDataAsString())){
    //             uniqueCount++;
    //         }
    //     }
    //     return ((double)uniqueCount/sizeOfPopulation);
    // }


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

    public ArrayList<Chromosome> getChromosomes() {
        return this.chromosomes;
    }
}
