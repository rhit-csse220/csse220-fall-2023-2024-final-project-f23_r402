package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JOptionPane;

public class Population {
    public static final int CROSSOVER_OFFSET = 1;
    
    private ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
    private int sizeOfPopulation = 100; // default
    private int genomeLength = 100; // default
    private double prevBestFitness, prevLowFitness, prevAvgFitness, prevHammingDistance, prevCountOf0s, prevCountOf1s, prevCountOfQs;
    private ArrayList<BestFitLine2D> lineArray = new ArrayList<>();
    private String targetString;
    private int fitnessFunctionType = 0;
    private boolean isResearch = false;

    public boolean isResearch() {
        return isResearch;
    }

    public int getSizeOfPopulation() {
        return sizeOfPopulation;
    }

    public void setSizeOfPopulation(int sizeOfPopulation) {
        this.sizeOfPopulation = sizeOfPopulation;
    }

    public double getPrevHammingDistance() {
        return prevHammingDistance;
    }
    
    // Create a Random object
    Random r = new Random();

    public Population(){}

    public Population(int sizeOfPopulation, int genomeLength){
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.initiatePopulation();
    }

    public Population(int sizeOfPopulation, int genomeLength, String fitnessFunction, boolean isResearch) {
        this.sizeOfPopulation = sizeOfPopulation;
        this.genomeLength = genomeLength;
        this.isResearch = isResearch;
        if (fitnessFunction.equals("Default")){
			this.fitnessFunctionType = 0;
        } else if (fitnessFunction.contains("Smiley")){
			this.fitnessFunctionType = 1;
            this.targetString = Chromosome.smileyGeneticData;
        }
        else if (fitnessFunction.contains("Sus")){
            this.fitnessFunctionType = 2;
            this.targetString = Chromosome.susGeneticData;
        }
        this.initiatePopulation();
    }

    public void initiatePopulation(){
        // System.out.println("Population.initiatePopulation() " + this.fitnessFunctionType);
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
        // find previous best + avg + lowest fitness
        this.prevBestFitness = this.chromosomes.get(0).getFitnessScore();
        this.prevAvgFitness = calculateAvgFitness();
        this.prevLowFitness = this.chromosomes.get(this.chromosomes.size()-1).getFitnessScore();
        //TODO FIGURE OUT HAMMING DISTANCE WITH THE THIRD BIT '?'
        this.prevHammingDistance = calculateHammingDistance();
        this.prevCountOf0s = calculateTotalNumOf0s();
        this.prevCountOf1s = calculateTotalNumOf1s();
        this.prevCountOfQs = calculateTotalNumOfQs();
        //this.prevHammingDistance = calculateUniqueStrings();
        this.lineArray.add(new BestFitLine2D(this.prevBestFitness, this.prevAvgFitness, this.prevLowFitness, this.prevHammingDistance, this.prevCountOf0s, this.prevCountOf1s, this.prevCountOfQs));
    }

    public double calculateTotalNumOf0s(){
        double count = 0;
        for (int i = 0; i < this.chromosomes.size(); i++){
            Chromosome currChromosome = this.chromosomes.get(i);
            // if (!currChromosome.isResearch()){
            //     return 0;
            // }
            count+= currChromosome.getNumberOf0s();
        }

        count = (count/(this.sizeOfPopulation*this.genomeLength)) * 100;
        return count;
    }
    
    public double calculateTotalNumOf1s(){
        double count = 0;
        for (int i = 0; i < this.chromosomes.size(); i++){
            Chromosome currChromosome = this.chromosomes.get(i);
            // if (!currChromosome.isResearch()){
            //     return 0;
            // }
            count+= currChromosome.getNumberOf1s();
        }

        count = (count/(this.sizeOfPopulation*this.genomeLength)) * 100;
        return count;
    }

    public double calculateTotalNumOfQs(){
        double count = 0;
        for (int i = 0; i < this.chromosomes.size(); i++){
            Chromosome currChromosome = this.chromosomes.get(i);
            // if (!currChromosome.isResearch()){
            //     return 0;
            // }
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

        // store all the elite chromosomes
        ArrayList<Chromosome> eliteChromosomes = new ArrayList<Chromosome>();
        this.sortPopulation();
        for (int i = 0; i < elitistSize; i++){
            eliteChromosomes.add(this.chromosomes.get(i));
        }

        // initializing new chromosomes
        this.chromosomes = new ArrayList<Chromosome>();

        // // performing crossover
        if (crossover){
            chosenChromosomes = this.performCrossover(chosenChromosomes);
        }

        // initiating new chromosomes
        for (int i = 0; i < initialSize/2; i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            try {
                this.chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate, this.fitnessFunctionType, this.isResearch));
                this.chromosomes.add(new Chromosome(currChromosomeData, true, mutationRate, this.fitnessFunctionType, this.isResearch));
            } catch (InvalidChromosomeFormatException e) {
                e.printStackTrace();
            }
        }

        // add elitist chromosomes
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

    // TODO: RESEARCH
    public void performSelectionResearch(){
        if (this.isResearch){
            this.chromosomes.parallelStream().forEach(chromosome -> chromosome.liveLife());
        }
        // sort population
        this.sortPopulation();

        this.createLine();

        // for (Chromosome chromosome : this.chromosomes){
        //     System.out.println(chromosome.getFitnessScore());
        // }

        ArrayList<Chromosome> currentChromosomes = new ArrayList<Chromosome>();
        
        // int initialSize = this.chromosomes.size();
        // this.chromosomes = new ArrayList<Chromosome>();
        
        // perform crossover
        for (int i = 0; i < this.sizeOfPopulation; i++){
            Chromosome kidChromosome = this.performResearchCrossover();
            currentChromosomes.add(kidChromosome);
        }

        this.chromosomes = currentChromosomes;
        // sort population
        //this.sortPopulation();
    }

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

    public Chromosome performResearchCrossover(){
        Chromosome parent1 = this.selectRandomParent();
        Chromosome parent2 = this.selectRandomParent();

        int crossoverPoint = r.nextInt(CROSSOVER_OFFSET, this.genomeLength);

        String parent1Data = parent1.getChromosomeDataAsString().substring(0, crossoverPoint);
        String parent2Data = parent2.getChromosomeDataAsString().substring(crossoverPoint, this.genomeLength);
        String childData = parent1Data + parent2Data;
        try{
            Chromosome childChromosome = new Chromosome(childData, this.isResearch);
            return childChromosome;
        } catch (Exception e){}
        
        return null;
    }
    // TODO: RESEARCH END

    public ArrayList<Chromosome> performCrossover(ArrayList<Chromosome> selectedParents){
        ArrayList<Chromosome> childChromosomes = new ArrayList<Chromosome>();
        for (int i = 0; i < selectedParents.size(); i++){
            // ensuring two children generated for each pair of parent
            int index = i;
            if (index % 2 == 1){
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

    public double calculateHammingDistance(){
        double hammingDistance = 0;
        int[][] position1n0Array = new int[genomeLength][2];
        
        if (this.fitnessFunctionType > 0){
            for (int i = 0; i < targetString.length(); i++){
                if (targetString.charAt(i)=='0'){
                    position1n0Array[i][0]++;
                }
                else{
                    position1n0Array[i][1]++;
                }
            }
        }
        
        int numPairs;
        if (fitnessFunctionType==0){
            numPairs = (this.sizeOfPopulation)*(this.sizeOfPopulation-1)/2;
        }
        else{
            numPairs = this.sizeOfPopulation;
        }
        this.chromosomes.parallelStream().forEach(chromosome -> readData1n0(chromosome, position1n0Array));
        for (int i = 0; i < genomeLength; i++){
            hammingDistance += (position1n0Array[i][0]*position1n0Array[i][1]);
            //System.out.println(position1n0Array[i][0]+", "+position1n0Array[i][1]);
        }
        return ((hammingDistance/(numPairs))/genomeLength)*100;
    }

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
        else{ //SMILEY FITNESS (fitnessFunctionType==1)
            String geneticData = chromosome.getChromosomeDataAsString();

            for (int i = 0; i < geneticData.length(); i++){
                if (geneticData.charAt(i)=='0' && geneticData.charAt(i)!=(targetString.charAt(i))){
                    position1n0Array[i][0]++;
                }
                else if (geneticData.charAt(i)=='1' && geneticData.charAt(i)!=(targetString.charAt(i))){
                    position1n0Array[i][1]++;
                }
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

    public int getLineArraySize() {
        return this.lineArray.size();
    }

    public double getBestFitnessForLineArrayElement(int i) {
        return this.lineArray.get(i).getBestFitness();
    }

    public double getAvgFitnessForLineArrayElement(int i) {
        return this.lineArray.get(i).getAvgFitness();
    }

    public double getLowFitnessForLineArrayElement(int i) {
        return this.lineArray.get(i).getLowFitness();
    }

    public double getHammingDistancForLineArrayElement(int i) {
        return this.lineArray.get(i).getHammingDistance();
    }

    public double getNumberOf0sForLineArrayElement(int i) {
        return this.lineArray.get(i).getNumberOf0s();
    }

    public double getNumberOf1sForLineArrayElement(int i) {
        return this.lineArray.get(i).getNumberOf1s();
    }

    public double getNumberOfQsForLineArrayElement(int i) {
        return this.lineArray.get(i).getNumberOfQs();
    }

    public int getNumPerColumnForChromosome(int i) {
        return this.chromosomes.get(i).getNumPerColumn();
    }

    public void drawOnForChromosome(int index, Graphics g, int geneWidth, int border) {
        this.chromosomes.get(index).drawOn(g, geneWidth, border);
    }

    public boolean isChromosomesEmpty() {
        return this.chromosomes.isEmpty();
    }

    public int getChromosomesSize() {
        return this.chromosomes.size();
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
    // public static void main(String[] args) {
    //     Population p = new Population(1000, 20, "Default", true);
    //     for (int i = 0; i < 100; i++){
    //         p.performSelectionResearch();
    //         System.out.println("BRUH");
    //     }
    //     p.sortPopulation();
    //     System.out.println(p.getChromosomes());
    // }
}
