package mainApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * class: Chromosome
 * @author F23_R402
 * 
 * Purpose:
 *   Is the class for the testing individual in the simulation, the chromosome. 
 */
public class Chromosome implements Comparable {
	// constants
	public static final int NUM_PER_ROW = 10;
	public static final int X_COORD_LETTER_OFFSET = 5;
	public static final double MAX_FITNESS_SCORE = 100.0;
	public static final int ORIGIN = 1;
	public static final int CROSSOVER_OFFSET = 1;	
	public static final Color GENE_0_TEXT_COLOR = Color.WHITE;
	public static final Color GENE_1_TEXT_COLOR = Color.BLACK;
	public static final Color GENE_2_TEXT_COLOR = Color.RED;
	public static final String smileyGeneticData = "1111111111111111111111011110111111111111111111111111111111111111111111101111110110000000011111111111";
	public static final String susGeneticData = "1111111111111000001111011111010001110001010111110101011111010001111101110110110111011011011110010011";
	public static final int fitB = 135300;
	public static final int MAX_DAYS = 1000;
	public static final int DAYS_OFFSET = 1;
	public static final double SCALING_MULTIPLER = 100;
	public static final int ALL1S_FITNESS_NUM = 0;
	public static final int SMILEY_FITNESS_NUM = 1;
	public static final int SUS_FITNESS_NUM = 2;
	public static final int BINARY_ENCODING_FITNESS_NUM = 3;
	public static final int QUESTION_MARK_LIMIT = 4;
	public static final int NORMAL_LIMIT = 2;
	public static final double PRE_CALCULATION_FITNESS = -1;
	public static final int FONT_SIZE_DIVIDER = 3;

	// fields
	private int numOfGenes = 100;           //default values
	private int numPerColumn = 10;          //default values
	private int fitnessFunctionType = 0;    //default values
	private Gene[] genes;
	private double fitnessScore;
	private int geneWidth = Gene.DEFAULT_GENE_SIDE;
	private int border = ChromosomeComponent.DEFAULT_BORDER;
	private String originalGenomeData;
	private int numberOf1s, numberOf0s, numberOfQs = 0;
	private ArrayList<Integer> qIndex = new ArrayList<>();

	// research
	private boolean isResearch = false;
	private boolean isPerfect = false;
	private int daysRemaining = 0;
	private int x = 0;
	private int y = 0;

	// Create a Random object
	Random r = new Random();
	
	/**
	* Creates a new Chromosome object
	*/
	public Chromosome(){}// Chromosome
	
	/**
	* Creates a new Chromosome object with different number of genes than the default 100
	* @param numOfGenes
	*/
	public Chromosome(int numOfGenes) {
		this.numOfGenes = numOfGenes;
		this.numPerColumn = numOfGenes/NUM_PER_ROW;
	} //Chromosome

	/**
	 * Initializes with specific fitness function type
	 * @param numOfGenes
	 * @param fitnessFunctionType
	 */
	public Chromosome(int numOfGenes, int fitnessFunctionType, boolean isResearch) {
		this(numOfGenes);
		this.fitnessFunctionType = fitnessFunctionType;
		this.isResearch = isResearch;
	} //Chromosome
	
	/**
	 * Initiatizes with fileData
	 * @param fileData
	 * @throws InvalidChromosomeFormatException
	 */
	public Chromosome(String fileData) throws InvalidChromosomeFormatException{ 
		this.initiateGeneWithString(fileData);
	} //Chromosome
	
	/**
	 * Initializes with fileData, mutate, and mutation rate
	 * @param fileData
	 * @param mutate
	 * @param mutationRate
	 * @throws InvalidChromosomeFormatException
	 */
	public Chromosome(String fileData, boolean mutate, double mutationRate) throws InvalidChromosomeFormatException{
		this.numOfGenes = fileData.length();
		this.initiateGeneWithString(fileData);
		this.calcFitnessFuction();
		
		if (mutate){this.mutateGenes(mutationRate);}
	} //Chromosome

	/**
	 * Initializes Chromosome with fileData, mutate, mutation rate, fitness function type, and isResearch
	 * @param fileData
	 * @param mutate
	 * @param mutationRate
	 * @param fitnessFunctionType
	 * @param isResearch
	 * @throws InvalidChromosomeFormatException
	 */
	public Chromosome(String fileData, boolean mutate, double mutationRate, int fitnessFunctionType, boolean isResearch) throws InvalidChromosomeFormatException{
		this.numOfGenes = fileData.length();
		this.fitnessFunctionType = fitnessFunctionType;
		this.initiateGeneWithString(fileData);
		this.calcFitnessFuction();
		this.isResearch = isResearch;

		if (mutate){this.mutateGenes(mutationRate);}
	} //Chromosome

	/**
	 * Initializes Chromosome with isResearch
	 * @param isResearch
	 */
	public Chromosome(boolean isResearch){
		this.isResearch = isResearch;
		this.fitnessScore = PRE_CALCULATION_FITNESS;
	} //Chromosome

	/**
	 * Initializes Chromosome with fileData and isResearch
	 * @param fileData
	 * @param isResearch
	 */
	public Chromosome(String fileData, boolean isResearch){
		this.isResearch = isResearch;
		this.fitnessScore = PRE_CALCULATION_FITNESS;
		initiateGeneWithString(fileData);
	} //Chromosome

	/**
	* ensures: initializes the array of genes and calculates the fitness function for the chromosome
	*/
	public void initiateGene() {
		this.initiateGeneLoad();
		// SET THE FITNESS SCORE
		this.calcFitnessFuction();
	} //initiateGene

	/**
	 * ensures: initializes the array of genes
	 */
	public void initiateGeneLoad(){
		qIndex = new ArrayList<>();
		this.numberOf0s = 0;
		this.numberOf1s = 0;
		this.numberOfQs = 0;
		this.genes = new Gene[this.numOfGenes];
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int limit = isResearch ? QUESTION_MARK_LIMIT : NORMAL_LIMIT; //Sets variable to the first value if expression true, else second value set
				int bit = r.nextInt(0, limit);
				char aBit = ' ';
				if (bit == 0){
					aBit = '0';
					numberOf0s++;
				} else if (bit == 1){
					aBit = '1';
					numberOf1s++;
				} else{
					aBit = '?';
					numberOfQs++;
					qIndex.add(i * NUM_PER_ROW + j);
				}
				this.genes[i * NUM_PER_ROW + j] = new Gene(aBit, true, this.geneWidth * j + this.border, this.geneWidth * i, this.geneWidth);
			}
		}
	} //initiateGeneLoad
	
	/**
	 * ensures: initiates the genome with a given data and calculates the fitness score for the chromosome
	 * @param s given data in a form of a String
	 */
	public void initiateGeneWithString(String s) {
		this.initiateGeneWithStringLoad(s);
		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
	} //initiateGeneWithString

	/**
	 * ensures: initiates the genome with a given data
	 * @param s given data in a form of a String
	 * @throws InvalidChromosomeFormatException if s.length() % 10 != 0
	 */
	public void initiateGeneWithStringLoad(String s) {
		qIndex = new ArrayList<>();
		this.numberOf0s = 0;
		this.numberOf1s = 0;
		this.numberOfQs = 0;
		this.originalGenomeData = s;
		this.numOfGenes = s.length();

		this.genes = new Gene[numOfGenes];
		this.numPerColumn = numOfGenes / NUM_PER_ROW;
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = s.charAt(i * NUM_PER_ROW + j);
				this.numberOf0s = (bit == '0') ? this.numberOf0s + 1 : this.numberOf0s; //Expression adds one to the given value else it remains same
				this.numberOf1s = (bit == '1') ? this.numberOf1s + 1 : this.numberOf1s; //Same as above but for different variable
				if (bit == '?'){
					this.numberOfQs++;
					qIndex.add(i * NUM_PER_ROW + j);
				}
				this.genes[i * NUM_PER_ROW + j] = new Gene(bit, true, this.geneWidth * j + this.border, this.geneWidth * i, this.geneWidth);
			}
		}
	} //initiateGeneWithStringLoad
	
	/**
	 * ensures: the correct fitness function is used
	 */
	public void calcFitnessFuction() {
		if (this.fitnessFunctionType == ALL1S_FITNESS_NUM) {
			this.calculateDefaultFitnessFunction();
		} else if (this.fitnessFunctionType == SMILEY_FITNESS_NUM || this.fitnessFunctionType == SUS_FITNESS_NUM || this.fitnessFunctionType == BINARY_ENCODING_FITNESS_NUM) {
			this.fitnessTarget();
		} else {
			System.out.println("Warning. Wrong fitness function selected.");
		}
	} //calcFitnessFuction

	/*
	* ensures: that the fitness score for the chromosome is calculated according to the All1s fitness function
	*/
	public void calculateDefaultFitnessFunction() {
		int score = 0;
		String fileData = getChromosomeDataAsString();
		for (int i = 0; i < fileData.length(); i++){
			if (fileData.charAt(i)=='1'){
				score++;
			}
		}
		this.fitnessScore = (int) ((score / (double) numOfGenes) * MAX_FITNESS_SCORE);
	} //calculateDefaultFitnessFunction
	
	/**
	* ensures: calculates fitness score based on similarity with the targetString
	*/
	public void fitnessTarget() {
		String targetString;
		if (this.fitnessFunctionType==SMILEY_FITNESS_NUM){
			targetString = smileyGeneticData;
		}
		else if (this.fitnessFunctionType==BINARY_ENCODING_FITNESS_NUM){
			targetString = Integer.toBinaryString(fitB);
		}
		else {
			targetString = susGeneticData;
		}
		int matchingBits = 0;
		
		// Ensure that the targetString and chromosome data have the same length
		if (targetString.length() != this.numOfGenes && this.fitnessFunctionType != 3) {
			throw new IllegalArgumentException("Chromosome length does not match target string length.");
		}
		
		String chromosomeData = getChromosomeDataAsString();
		
		// Compare each bit of the chromosome with the target string
		for (int i = 0; i < targetString.length(); i++) {
			if (chromosomeData.charAt(i) == targetString.charAt(i)) {
				matchingBits++;
			}
		}
	
		// Set the fitness score based on the number of matching bits
		this.fitnessScore = (int) ((matchingBits / (double) numOfGenes) * MAX_FITNESS_SCORE);
		if (this.fitnessFunctionType == BINARY_ENCODING_FITNESS_NUM){
			// This specific section is used to set the fitness score according to how CLOSE the genetic data's encoded decimal number is to the target decimal number
			this.fitnessScore = fitnessBinaryToDecimalTarget();
		}
	} //fitnessTarget

	/**
	* ensures: calculates fitness score based on how close the number encoded by the chromosome's genetic data is to the target number
	* @return the value of the fitness score
	*/
	public double fitnessBinaryToDecimalTarget() {
		String chromosomeData = getChromosomeDataAsString();
		
		// Convert binary to decimal
		double decimalValue = Integer.parseInt(chromosomeData, 2);
		
		// Define a target decimal value
		int targetDecimalValue = fitB; 
		
		// Calculate fitness based on the absolute difference from the target
		double fitness = 1 / (1 + Math.abs(decimalValue - targetDecimalValue));
		
		return fitness * SCALING_MULTIPLER; // Scale the fitness to a percentage
	} //fitnessBinaryToDecimalTarget
	
	
	/**
	* ensures: calculates fitness score based on the largest number of consecutive 1s
	*/
	public void fitnessMax() {
		String chromosomeData = getChromosomeDataAsString();
		int maxConsecutiveOnes = 0;
		int currentConsecutiveOnes = 0;
		
		for (int i = 0; i < chromosomeData.length(); i++) {
			if (chromosomeData.charAt(i) == '1') {
				currentConsecutiveOnes++;
				if (currentConsecutiveOnes > maxConsecutiveOnes) {
					maxConsecutiveOnes = currentConsecutiveOnes;
				}
			} else {
				currentConsecutiveOnes = 0; // Reset consecutive ones counter
			}
		}
		
		// Set the fitness score based on the maximum consecutive ones
		this.fitnessScore = maxConsecutiveOnes;
	} //fitnessMax
	
	/**
	 * ensures: the information is displayed correctly on resizing the window
	 */
	public void adjustGenePosition(){
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				this.genes[i*NUM_PER_ROW+j].setX(this.geneWidth*j + this.border);
				this.genes[i*NUM_PER_ROW+j].setY(this.geneWidth*i);
				this.genes[i*NUM_PER_ROW+j].setGeneWidth(this.geneWidth);
			}
		}
	} //adjustGenePosition
	
	/**
	 * ensures: performs mutation of the genes
	 * @param mutationRate mutaition rate for the mutation
	 */
	public void mutateGenes(double mutationRate){
		for (Gene gene : this.genes){
			gene.mutate(mutationRate, this.numOfGenes);
		}
		this.calcFitnessFuction();
	} //mutateGenes
	
	/**
	* ensures: that the chromosome data, i.e the genes and their values, are concatenated into a single string
	* @return the chromosome data
	*/
	public String getChromosomeDataAsString() {
		String data = "";
		for (Gene gene : this.genes) {
			data += gene.getBit();
		}
		return data;
	} //getChromosomeDataAsString
	
	/**
	* ensures: that the number of genes per column in this chromosome is returned
	* @return the number of genes per column
	*/
	public int getNumPerColumn() {return this.numPerColumn;} //getNumPerColumn
	
	/**
	* ensures: that the number of genes in this chromosome is returned
	* @return the number of genes 
	*/
	public int getNumOfGenes() {return this.numOfGenes;} //getNumOfGenes
	
	/**
	 * ensures: that the fitness score of the chromosome is returned
	 * @return the fitness score of the chromosome
	 */
	public double getFitnessScore(){
		if (!this.isResearch){
			this.calcFitnessFuction();
			return this.fitnessScore;
		}
		else{
			return this.fitnessScore;
		}
	} // getFitnessScore
	
	/**
	 * ensures: that the genes in the chromosome is set to the param genes
	 * @param genes
	 */
	public void setGenes(Gene[] genes) {this.genes = genes;} //setGenes
	
	/**
	 * ensures: that the genes in the chromosome is returned
	 * @return the genes array of the chromosome
	 */
	public Gene[] getGenes() {return this.genes;} //getGenes
	
	/**
	 * ensurs: the gene is drawn
	 * @param g
	 * @param geneWidth
	 * @param border
	 */
	public void drawOn(Graphics g, int geneWidth, int border) {
		Graphics2D g2 = (Graphics2D) g;
		this.geneWidth = geneWidth;
		this.border = border;
		this.adjustGenePosition();
		
		for (int i = 0; i < this.genes.length; i++) {
			this.genes[i].drawOn(g2);
			if (this.genes[i].getBit() == '1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (this.genes[i].getBit() == '0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			} else {
				g2.setColor(GENE_2_TEXT_COLOR);
			}
			g2.setFont(new Font(null, Font.PLAIN, geneWidth/FONT_SIZE_DIVIDER));
			g2.drawString((String)(i + ""), this.genes[i].getX() + X_COORD_LETTER_OFFSET, geneWidth/FONT_SIZE_DIVIDER + this.genes[i].getY());
		}
	} //drawOn

	/**
	 * ensures: draws the chromosomes (population)
	 * @param g Graphics2D object to draw on
	 * @param geneWidth gene width
	 * @param border border width
	 */
	public void drawPopulationView(Graphics2D g, int geneWidth, int border) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(x,y);
		this.geneWidth = geneWidth;
		this.border = border;
		this.adjustGenePosition();
		this.drawGenes(g2);
		g2.translate(-x,-y);
	} //drawPopulationView

	/**
	 * ensures: draws genes
	 * @param g2 Graphics2D element on which to draw
	 */
	public void drawGenes(Graphics2D g2){
		for (int i = 0; i < this.genes.length; i++) {
			this.genes[i].drawOn(g2);
			if (this.genes[i].getBit() == '1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (this.genes[i].getBit() == '0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			} else if (this.genes[i].getBit() == '?'){
				g2.setColor(GENE_2_TEXT_COLOR);
			}
		}
	} //drawGenes
	
	/**
	 * ensures: fitness of this chromosome and another chromosome passed in as param is compared
	 * @param otherChromosome
	 * @return 1 if otherChromosome is fitter, 0 if the same, -1 if this chromosome is fitter
	 */
	public int compareTo(Object otherChromosome) {
		double thisFitness = this.getFitnessScore();
		double otherFitness = ((Chromosome) otherChromosome).getFitnessScore();
		
		// Compare the fitness scores of this chromosome and the other chromosome
		if (thisFitness < otherFitness) {
			return 1;
		} else if (thisFitness > otherFitness) {
			return -1;
		} else {
			return 0;
		}
	} //compareTo
	
	/**
	 * ensures: the selected gene is found
	 * @param box
	 * @return the gene being selected
	 */
	public Gene handleGetSelectedGene(Rectangle2D.Double box) {
		for (int i = 0; i < this.genes.length; i++) {
			if (this.genes[i].isSelected(box)) {
				return this.genes[i];
			}
		}
		return null;
	} //handleGetSelectedGene

	/**
	 * ensures: simulates 1,000 days of lifespan for a chromosome
	 */
	public void liveLife() {
		this.originalGenomeData = this.getChromosomeDataAsString();
        for (int days = 0; days < MAX_DAYS; days++){
            this.loadGeneFromOriginalData();
            for (int i = 0; i < qIndex.size(); i++){
                int indexOfQ = qIndex.get(i); //qIndex is the arraylist wherein the index of the question marks in the gene list are stored; Used to increase speed of array
				Gene gene = genes[indexOfQ];
                if (!isPerfect && gene.getBit() == '?'){
                    gene.setRandomBit();
                }
            }
            if (checkAll1s(this.getChromosomeDataAsString())){
                this.isPerfect = true;
				this.daysRemaining = MAX_DAYS - (days+DAYS_OFFSET); //Days starts at 0 and goes to 999; Therefore subtracting 1 more to get accurate value
                return;
            }
            this.daysRemaining = MAX_DAYS - (days+DAYS_OFFSET);
        }
		this.loadGeneFromOriginalData();
		this.fitnessScore = calculateFitnessScoreResearch();
    } //liveLife

	/**
	 * ensures: loads genes from the original genome data
	 */
	public void loadGeneFromOriginalData(){
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = this.originalGenomeData.charAt(i*NUM_PER_ROW+j);
				this.genes[i*NUM_PER_ROW+j] = new Gene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
	} //loadGeneFromOriginalData

	/**
	 * ensures: checks if the given string has all ones in its genome
	 * @param s chromosome data
	 * @return whether the string has all ones
	 */
	public boolean checkAll1s(String s){
        int score = 0;
        for (int i = 0; i < s.length(); i++){
            if (Character.getNumericValue(s.charAt(i)) == 1) {
                score++;
            }
        }
        return (score == s.length());
    } //checkAll1s

	/**
	 * ensures: calculates the fitness score for the research part of the program (The formula is given here)
	 * @return the fitness score
	 */
	public double calculateFitnessScoreResearch(){
		double fitnessScore = (1 + (19*this.daysRemaining)/1000)*5;
		return fitnessScore;
	} //calculateFitnessScoreResearch
	
	/**
	 * ensures: returns genome as a string for printing purposes
	 */
	@Override
	public String toString() {
		return this.getChromosomeDataAsString();
	} //toString

	/**
	 * sets the fitness function type to another value
	 * @param fitnessFunction new value of the fitness function
	 */
	public void setFitnessFunctionType(int fitnessFunction) {
		this.fitnessFunctionType = fitnessFunction;
	} //setFitnessFunctionType

	/**
	 * ensures: gets the original genome data
	 * @return original genome data
	 */
	public String getOriginalGenomeData(){
		return this.originalGenomeData;
	} //getOriginalGenomeData

	/**
	 * @return number of 1s in the chromosome
	 */
	public int getNumberOf1s() {
		return numberOf1s;
	} //getNumberOf1s

	/**
	 * @return number of 0s in the chromosome
	 */
	public int getNumberOf0s() {
		return numberOf0s;
	} //getNumberOf0s

	/**
	 * @return number of ?s in the chromosome
	 */
	public int getNumberOfQs() {
		return numberOfQs;
	} //getNumberOfQs

	/**
	 * ensures: returns whether this chromosome is perfect
	 * @return whether this chromosome is perfect
	 */
	public boolean isPerfect() {
		return isPerfect;
	} //isPerfect
	
	/**
	 * ensures: returns whether this chromosome is used for the research part of the project
	 * @return isResearch
	 */
	public boolean isResearch() {
		return isResearch;
	} //isResearch
	
	/**
	 * ensures: sets the x value to a new number
	 * @param x new x value
	 */
	public void setX(int x) {
		this.x = x;
	} //setX

	/**
	 * ensures: sets the y value to a new number
	 * @param y new y value
	 */
	public void setY(int y) {
		this.y = y;
	} //setY
} //End Chromosome
