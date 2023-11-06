package mainApp;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

public class Population {
    // TODO: change to privates
    public ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100; // default
    private int genomeLength = 100; // default
    public double prevBestFitness, prevLowFitness, prevAvgFitness, prevHammingDistance;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<>();

    private int fitnessFunctionType = 0;

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

    public Population(int sizeOfPopulation, int genomeLength, String fitnessFunction) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        if (fitnessFunction.equals("Default"))
			this.fitnessFunctionType = 0;
		else if (fitnessFunction.contains("Smiley"))
			this.fitnessFunctionType = 1;
        this.initiatePopulation();
    }

    public void initiatePopulation(){
        System.out.println("Population.initiatePopulation() " + this.fitnessFunctionType);
        this.chromosomes = new ArrayList<Chromosome>();
        this.lineArray = new ArrayList<BestFitLine2D>();
        for (int i = 0; i < this.sizeOfPopulation; i++){
            this.chromosomes.add(new Chromosome(genomeLength, this.fitnessFunctionType));
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
        // System.out.println(this.prevBestFitness);
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
        int elitistSize = (int) ((elitism / 100) * initialSize);

        // The collection is initially initialized to the initial collection of chromosomes
        ArrayList<Chromosome> eliteChromosomes = new ArrayList<Chromosome>();
        
        this.sortPopulation();
        for (int i = 0; i < elitistSize; i++){
            eliteChromosomes.add(this.chromosomes.get(i));
        }

        // initiating 
        this.chromosomes = new ArrayList<Chromosome>();

        for (int i = 0; i < initialSize/2; i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            try {
                this.chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate, this.fitnessFunctionType));
                this.chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate, this.fitnessFunctionType));
            } catch (InvalidChromosomeFormatException e) {
                e.printStackTrace();
            }
        }

        this.sortPopulation();

        int index = 0;
        for (int i = initialSize - 1; i >= initialSize - elitistSize; i--){
            this.chromosomes.set(i, eliteChromosomes.get(index));
            index++;
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

    public void performCrossover(ArrayList<Chromosome> selectedParents){
        // genomeLength
        

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

    public double calculateHammingDistance(){
        double hammingDistance = 0;
        int[][][] position1n0Array = new int[genomeLength][sizeOfPopulation][sizeOfPopulation];
        int numPairs = (this.sizeOfPopulation)*(this.sizeOfPopulation-1)/2;
        this.chromosomes.parallelStream().forEach(chromosome -> readData1n0(chromosome, position1n0Array));
        for (int i = 0; i < genomeLength; i++){
            hammingDistance+= (position1n0Array[i][0][0]*position1n0Array[i][0][1]);
        }
        //System.out.println(((hammingDistance/(numPairs))/genomeLength));
        return ((hammingDistance/(numPairs))/genomeLength)*100;
    }

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

    // public void setFitnessFunctionForChromosomes(String fitnessFunction) throws InvalidGenomeLengthException {
    //     if (fitnessFunction.contains("only") && fitnessFunction.contains("100") && this.genomeLength != 100) {
    //         throw new InvalidGenomeLengthException(100);
    //     }
    //     if (fitnessFunction.equals("Default"))
	// 		this.fitnessFunctionType = 0;
	// 	else if (fitnessFunction.contains("Smiley"))
	// 		this.fitnessFunctionType = 1;
    // }
}
