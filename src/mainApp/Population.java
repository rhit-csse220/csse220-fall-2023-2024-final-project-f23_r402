package mainApp;

import java.awt.Graphics;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * Class: Population
 * @author F23_402
 * 
 * Purpose: This class contains the set of the chromosomes. It contains the functions that will operate on the chromosomes, such as the selection functions, and the statistics for the simulation. 
 */
public class Population {
    // constants
    public static final int CROSSOVER_OFFSET = 1;
    public static final int BEST_INDEX = 0;
    public static final int TRUNCATION_SELECTION_NUM = 0;
    public static final int ROULETTE_SELECTION_NUM = 1;
    public static final int RANKED_SELECTION_NUM = 2;
    public static final int PERCENTAGE_CONVERTER = 100;
    public static final int SIZE_DIVIDER = 2;
    
    // fields
    private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100; // default
    private int genomeLength = 100; // default
    private double prevBestFitness, prevLowFitness, prevAvgFitness, prevHammingDistance, prevCountOf0s, prevCountOf1s, prevCountOfQs;
    private ArrayList<BestFitLine2D> lineArray = new ArrayList<>();
    private String targetString;
    private int fitnessFunctionType = 0;
    private boolean isResearch = false;
    
    // Create a Random object
    Random r = new Random();
    
    /**
     * Initialize Population without params
     */
    public Population(){}
    
    /**
     * Initialize Population
     * @param sizeOfPopulation
     * @param genomeLength
     */
    public Population(int sizeOfPopulation, int genomeLength){
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.initiatePopulation();
    }
    
    /**
     * Initialize Population for research
     * @param sizeOfPopulation
     * @param genomeLength
     * @param fitnessFunction
     * @param isResearch
     */
    public Population(int sizeOfPopulation, int genomeLength, String fitnessFunction, boolean isResearch) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.isResearch = isResearch;
        if (fitnessFunction.equals("Default")){
            this.fitnessFunctionType = Chromosome.ALL1S_FITNESS_NUM;
        } else if (fitnessFunction.contains("Smiley")){
            this.fitnessFunctionType = Chromosome.SMILEY_FITNESS_NUM;
            this.targetString = Chromosome.smileyGeneticData;
        } else if (fitnessFunction.contains("Sus")){
            this.fitnessFunctionType = Chromosome.SUS_FITNESS_NUM;
            this.targetString = Chromosome.susGeneticData;
        }
        
        else if (fitnessFunction.contains("Binary")){
            this.fitnessFunctionType = Chromosome.BINARY_ENCODING_FITNESS_NUM;
            this.targetString = Integer.toBinaryString(Chromosome.fitB);
        }
        this.initiatePopulation();
    }
    
    /**
    * ensures: initializes the population
    */
    public void initiatePopulation(){
        this.chromosomes = new ArrayList<Chromosome>();
        this.lineArray = new ArrayList<BestFitLine2D>();
        for (int i = 0; i < this.sizeOfPopulation; i++){
            this.chromosomes.add(new Chromosome(genomeLength, this.fitnessFunctionType, this.isResearch));
            this.chromosomes.get(i).initiateGeneLoad();
            if (!this.isResearch){
                this.chromosomes.get(i).calcFitnessFuction();
            }
        }
        if (!this.isResearch){
            this.sortPopulation();
        }
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
        this.prevBestFitness = this.chromosomes.get(BEST_INDEX).getFitnessScore();
        this.prevAvgFitness = calculateAvgFitness();
        this.prevLowFitness = this.chromosomes.get(this.chromosomes.size()-1).getFitnessScore();
        this.prevHammingDistance = calculateHammingDistance();
        this.prevCountOf0s = calculateTotalNumOf0s();
        this.prevCountOf1s = calculateTotalNumOf1s();
        this.prevCountOfQs = calculateTotalNumOfQs();
        this.lineArray.add(new BestFitLine2D(this.prevBestFitness, this.prevAvgFitness, this.prevLowFitness, this.prevHammingDistance, this.prevCountOf0s, this.prevCountOf1s, this.prevCountOfQs));
    }
    
    /**
    * ensures: calculates the total number of zeroes in the chromosomes
    * @return total number of zeroes in the chromosomes
    */
    public double calculateTotalNumOf0s(){
        double count = 0;
        for (int i = 0; i < this.chromosomes.size(); i++){
            Chromosome currChromosome = this.chromosomes.get(i);
            count+= currChromosome.getNumberOf0s();
        }
        count = (count/(this.sizeOfPopulation*this.genomeLength)) * PERCENTAGE_CONVERTER;
        return count;
    }
    
    /**
    * ensures: calculates the total number of ones in the chromosomes
    * @return total number of ones in the chromosomes
    */
    public double calculateTotalNumOf1s(){
        double count = 0;
        for (int i = 0; i < this.chromosomes.size(); i++){
            Chromosome currChromosome = this.chromosomes.get(i);
            count+= currChromosome.getNumberOf1s();
        }
        count = (count/(this.sizeOfPopulation*this.genomeLength)) * PERCENTAGE_CONVERTER;
        return count;
    }
    
    /**
    * ensures: calculates the total number of question marks in the chromosomes
    * @return total number of question marks in the chromosomes
    */
    public double calculateTotalNumOfQs(){
        double count = 0;
        for (int i = 0; i < this.chromosomes.size(); i++){
            Chromosome currChromosome = this.chromosomes.get(i);
            count+= currChromosome.getNumberOfQs();
        }
        count = (count/(this.sizeOfPopulation*this.genomeLength)) * 100;
        return count;
    }
    
    /**
    * performs truncation/roulette/ranked selection on the current population and updates this.population to the new population
    * @param mutationRate
    * @param selectionType 0 for truncation, 1 for roulette, 2 for ranked
    */
    public void performSelection(double mutationRate, int selectionType, double elitism, boolean crossover){
        // sort population
        this.sortPopulation();
        
        // create line
        this.createLine();
        
        // finding the parent chromosomes by selection algorithm
        ArrayList<Chromosome> currentChromosomes = new ArrayList<Chromosome>(chromosomes);
        ArrayList<Chromosome> chosenChromosomes = new ArrayList<Chromosome>();
        if (selectionType == TRUNCATION_SELECTION_NUM){
            chosenChromosomes = findTruncationList(currentChromosomes);
        } else if (selectionType == ROULETTE_SELECTION_NUM){
            chosenChromosomes = findRouletteList(currentChromosomes, new ArrayList<Chromosome>());
        } else if (selectionType == RANKED_SELECTION_NUM){
            chosenChromosomes = findRankedList(currentChromosomes, new ArrayList<Chromosome>());
        } else{
            throw new InvalidParameterException();
        }
        
        int initialSize = this.chromosomes.size();
        
        // The amount of the most fit population to be retained from the initial collection of chromosomes. 
        int elitistSize = (int) ((elitism / PERCENTAGE_CONVERTER) * initialSize); //Converts the elitism back from percentage value, which is then multiplied by initialSize to find elitistSize
        
        // store all the elite chromosomes
        ArrayList<Chromosome> eliteChromosomes = new ArrayList<Chromosome>();
        this.sortPopulation();
        for (int i = 0; i < elitistSize; i++){
            eliteChromosomes.add(this.chromosomes.get(i));
        }
        
        // initializing new chromosomes
        currentChromosomes = new ArrayList<Chromosome>();
        
        // // performing crossover
        if (crossover){
            chosenChromosomes = this.performCrossover(chosenChromosomes);
        }
        
        // initiating new chromosomes; Divided by 2 to ensure that only half the parents generate the pairs
        for (int i = 0; i < initialSize/2; i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            try {
                currentChromosomes.add(new Chromosome(currChromosomeData, true, mutationRate, this.fitnessFunctionType, this.isResearch));
                currentChromosomes.add(new Chromosome(currChromosomeData, true, mutationRate, this.fitnessFunctionType, this.isResearch));
            } catch (InvalidChromosomeFormatException e) {
                e.printStackTrace();
            }
        }
        
        // add elitist chromosomes
        this.sortPopulation();
        int index = 0;
        for (int i = initialSize - 1; i >= initialSize - elitistSize; i--){
            currentChromosomes.set(i, eliteChromosomes.get(index));
            index++;
        }

        // update this.chromosomes
        this.chromosomes = currentChromosomes;
        
        // sort population
        this.sortPopulation();
    }
    
    /**
    * calculates the population after truncation selection
    * @param currentChromosomes
    * @return an ArrayList of chromosomes that have been chosen with the truncation selection
    */
    public ArrayList<Chromosome> findTruncationList(ArrayList<Chromosome> currentChromosomes){
        int middleIndex = this.chromosomes.size()/SIZE_DIVIDER;
        for (int i = this.chromosomes.size()/SIZE_DIVIDER; i < this.chromosomes.size(); i++){
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
    
    // RESEARCH START
    /**
    * ensures: performs selection for the research part of the program
    */
    public void performSelectionResearch(){
        if (this.isResearch){
            this.chromosomes.parallelStream().forEach(chromosome -> chromosome.liveLife());
        }
        // sort population
        this.sortPopulation();
        
        this.createLine();
        
        ArrayList<Chromosome> currentChromosomes = new ArrayList<Chromosome>();
        
        // perform crossover
        for (int i = 0; i < this.sizeOfPopulation; i++){
            Chromosome kidChromosome = this.performResearchCrossover();
            currentChromosomes.add(kidChromosome);
        }
        
        this.chromosomes = currentChromosomes;
    }
    
    /**
    * ensures: selects a random parent chromosome and returns it
    * @return the parent chromosome
    */
    public Chromosome selectRandomParent(){
        
        ArrayList<Double> chromosomeScores = new ArrayList<Double>();
        
        // find total Score
        double totalScore = 0;
        for (Chromosome chromosome : this.chromosomes){
            totalScore += chromosome.getFitnessScore();
        }
        
        // find pctg range for each chromosome based of their score
        double currNum = 0;
        for (Chromosome chromosome : this.chromosomes){
            currNum += chromosome.getFitnessScore()/totalScore;
            chromosomeScores.add(currNum);
        }
        
        // chose random chromosome
        double randNum = r.nextDouble(0,1);
        for (int i = 0; i < chromosomeScores.size(); i++){
            if (chromosomeScores.get(i) >= randNum){
                return this.chromosomes.get(i);
            }
        }
        return null;
    }
    
    /**
    * ensures: selects a second random parent chromosome and returns it
    * @return the second parent chromosome
    */
    public Chromosome selectSecondRandomParent(Chromosome firstParent){
        ArrayList<Double> chromosomeScores = new ArrayList<Double>();
        ArrayList<Chromosome> currentChromosomes = new ArrayList<Chromosome>(this.chromosomes);
        currentChromosomes.remove(firstParent);
        
        // find total Score
        double totalScore = 0;
        for (Chromosome chromosome : currentChromosomes){
            totalScore += chromosome.getFitnessScore();
        }
        
        // find pctg range for each chromosome based of their score
        double currNum = 0;
        for (Chromosome chromosome : currentChromosomes){
            currNum += chromosome.getFitnessScore()/totalScore;
            chromosomeScores.add(currNum);
        }
        
        // chose random chromosome
        double randNum = r.nextDouble(0,1);
        for (int i = 0; i < chromosomeScores.size(); i++){
            if (chromosomeScores.get(i) >= randNum){
                return currentChromosomes.get(i);
            }
        }
        return null;
    }
    
    /**
    * ensures: performs crossover for the researc part of the program and returns the child
    * @return child chromosome after crossover
    */
    public Chromosome performResearchCrossover(){
        Chromosome parent1 = this.selectRandomParent();
        Chromosome parent2 = this.selectSecondRandomParent(parent1);
        
        int crossoverPoint = r.nextInt(CROSSOVER_OFFSET, this.genomeLength);
        
        String parent1Data = parent1.getOriginalGenomeData().substring(0, crossoverPoint);
        String parent2Data = parent2.getOriginalGenomeData().substring(crossoverPoint, this.genomeLength);
        String childData = parent1Data + parent2Data;
        try{
            Chromosome childChromosome = new Chromosome(childData, this.isResearch);
            return childChromosome;
        } catch (Exception e){}
        
        return null;
    }
    // RESEARCH END
    
    /**
    * ensures: performs crossover and returns the children
    * @return the arraylist of child chromosomes after crossover
    */
    public ArrayList<Chromosome> performCrossover(ArrayList<Chromosome> selectedParents){
        ArrayList<Chromosome> childChromosomes = new ArrayList<Chromosome>();
        for (int i = 0; i < selectedParents.size(); i++){
            // ensuring two children generated for each pair of parent
            int index = i;
            if (index % 2 == 1){ //Checks if odd
                index--;
            }
            
            // generate a random index for crossover, excluding first and last index
            int crossoverPoint = r.nextInt(CROSSOVER_OFFSET, this.genomeLength);
            
            // finds childData
            String parent1Data = selectedParents.get(index).getChromosomeDataAsString().substring(0, crossoverPoint);
            String parent2Data = selectedParents.get(index+1).getChromosomeDataAsString().substring(crossoverPoint, this.genomeLength);
            String childData = parent1Data + parent2Data;
            
            // adds each child chromosome to the list
            try{
                Chromosome childChromosome = new Chromosome(childData);
                childChromosomes.add(childChromosome);
            } catch (Exception e){}
        }
        
        return childChromosomes; // only returns half of population - rest has to be mutated
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
    
    /**
    * ensures: calcualtes the hamming distance
    * @return hamming distance
    */
    public double calculateHammingDistance(){
        double hammingDistance = 0;
        int[][] position1n0Array = new int[genomeLength][2];
        
        if (this.fitnessFunctionType > 0){
            for (int i = 0; i < targetString.length(); i++){
                if (targetString.charAt(i)=='0'){
                    position1n0Array[i][0]++;
                } else{
                    position1n0Array[i][1]++;
                }
            }
        }
        
        int numPairs;
        if (fitnessFunctionType==0){
            numPairs = (this.sizeOfPopulation)*(this.sizeOfPopulation-1)/2; //THE FORMULA WE FOUND CALCULATING NUM PAIRS
        } else{
            numPairs = this.sizeOfPopulation;
        }
        this.chromosomes.parallelStream().forEach(chromosome -> readData1n0(chromosome, position1n0Array));
        for (int i = 0; i < genomeLength; i++){
            hammingDistance += (position1n0Array[i][0]*position1n0Array[i][1]);
        }
        return ((hammingDistance/(numPairs))/genomeLength)*PERCENTAGE_CONVERTER;
    }
    
    /*
     * ensures: Calculates the number of 1s and 0s at the ith position in the string. Given that the '?' is in a state of flux and can be either 1 or 0, it is not counted.
     */
    public void readData1n0(Chromosome chromosome, int[][] position1n0Array){
        if (fitnessFunctionType==0){ //DEFAULT FITNESS
            String geneticData = chromosome.getChromosomeDataAsString();
            for (int i = 0; i < geneticData.length(); i++){
                if (geneticData.charAt(i)=='0'){
                    position1n0Array[i][0]++;
                }
                else{
                    position1n0Array[i][1]++;
                }
            }
        }
        else{ //This else statement is used for checking when the fitness function is comparing a target bitstring
            String geneticData = chromosome.getChromosomeDataAsString();
            
            for (int i = 0; i < targetString.length(); i++){
                if (geneticData.charAt(i)=='0' && geneticData.charAt(i)!=(targetString.charAt(i))){
                    position1n0Array[i][0]++;
                }
                else if (geneticData.charAt(i)=='1' && geneticData.charAt(i)!=(targetString.charAt(i))){
                    position1n0Array[i][1]++;
                }
            }
        }
    }
    
    /**
     * @return chromosomes arraylist
     */
    public ArrayList<Chromosome> getChromosomes() {
        return this.chromosomes;
    }
    
    /**
    * ensures: returns the size of the lineArray list
    * @return size of the lineArray list
    */
    public int getLineArraySize() {
        return this.lineArray.size();
    }
    
    /**
    * ensures: returns the best fitness line for a lineArray element at index i
    * @param i index
    * @return best fitness line
    */
    public double getBestFitnessForLineArrayElement(int i) {
        return this.lineArray.get(i).getBestFitness();
    }
    
    /**
    * ensures: returns the average fitness line for a lineArray element at index i
    * @param i index
    * @return average fitness line
    */
    public double getAvgFitnessForLineArrayElement(int i) {
        return this.lineArray.get(i).getAvgFitness();
    }
    
    /**
    * ensures: returns the lowest fitness line for a lineArray element at index i
    * @param i index
    * @return lowest fitness line
    */
    public double getLowFitnessForLineArrayElement(int i) {
        return this.lineArray.get(i).getLowFitness();
    }
    
    /**
    * ensures: returns the hamming distance for a lineArray element at index i
    * @param i index
    * @return hamming distance
    */
    public double getHammingDistancForLineArrayElement(int i) {
        return this.lineArray.get(i).getHammingDistance();
    }
    
    /**
    * ensures: returns the number of zeroes for a lineArray element at index i
    * @param i index
    * @return number of zeroes
    */
    public double getNumberOf0sForLineArrayElement(int i) {
        return this.lineArray.get(i).getNumberOf0s();
    }
    
    /**
    * ensures: returns the number of ones for a lineArray element at index i
    * @param i index
    * @return number of ones
    */
    public double getNumberOf1sForLineArrayElement(int i) {
        return this.lineArray.get(i).getNumberOf1s();
    }
    
    /**
    * ensures: returns the number of question marks for a lineArray element at index i
    * @param i index
    * @return number of question marks
    */
    public double getNumberOfQsForLineArrayElement(int i) {
        return this.lineArray.get(i).getNumberOfQs();
    }
    
    /**
    * ensures: returns the numPerColumn for a chromosome at index i
    * @param i index
    * @return numPerColumn
    */
    public int getNumPerColumnForChromosome(int i) {
        return this.chromosomes.get(i).getNumPerColumn();
    }
    
    /**
    * ensures: a wrapper for the drawOn(g, geneWidth, border) method for chromosome at index i
    * @param index
    * @param g
    * @param geneWidth
    * @param border
    */
    public void drawOnForChromosome(int index, Graphics g, int geneWidth, int border) {
        this.chromosomes.get(index).drawOn(g, geneWidth, border);
    }
    
    /**
    * ensures: returns wehther the chromosome array is empty
    * @return
    */
    public boolean isChromosomesEmpty() {
        return this.chromosomes.isEmpty();
    }
    
    /**
    * ensrues: returns the chromosome list size
    * @return
    */
    public int getChromosomesSize() {
        return this.chromosomes.size();
    }
    
    /**
    * ensures: returns whether the chromosome at index i is used for the research part
    * @param i
    * @return
    */
    public boolean isResearchChromosome(int i) {
        return this.getChromosomeByIndex(i).isResearch();
    }
    
    /**
    * ensures: returns a chromosome at index i
    * @param i
    * @return
    */
    public Chromosome getChromosomeByIndex(int i) {
        return this.chromosomes.get(i);
    }

    /**
    * returns whether this chromosome is used for the research part of the program
    * @return
    */
    public boolean isResearch() {
        return this.isResearch;
    }
    
    /**
     * @return size of population
     */
    public int getSizeOfPopulation() {
        return this.sizeOfPopulation;
    }
    
    /**
     * ensures size of population is updated
     * @param sizeOfPopulation
     */
    public void setSizeOfPopulation(int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
    }
    
    /**
     * @return previous hamming distance
     */
    public double getPrevHammingDistance() {
        return this.prevHammingDistance;
    }
} //End Population
