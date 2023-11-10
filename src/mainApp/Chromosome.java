package mainApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Chromosome implements Comparable {
	public static final int NUM_PER_ROW = 10;
	public static final int X_COORD_LETTER_OFFSET = 5;
	// public static final int Y_COORD_LETTER_OFFSET = 10;
	public static final double MAX_FITNESS_SCORE = 100.0;
	public static final int ORIGIN = 1;
	public static final int CROSSOVER_OFFSET = 1;
	
	public static final Color GENE_0_TEXT_COLOR = Color.WHITE;
	public static final Color GENE_1_TEXT_COLOR = Color.BLACK;
	public static final Color GENE_2_TEXT_COLOR = Color.RED;
	
	public static final String smileyGeneticData = "1111111111111111111111011110111111111111111111111111111111111111111111101111110110000000011111111111";
	public static final String susGeneticData = "1111111111111000001111011111010001110001010111110101011111010001111101110110110111011011011110010011";
	
	private int numOfGenes = 100;           //default values
	private int numPerColumn = 10;          //default values
	private int fitnessFunctionType = 0;;   //default values
	private Gene[] genes;
	private double fitnessScore;
	private int geneWidth = Gene.DEFAULT_GENE_SIDE;
	private int border = ChromosomeComponent.DEFAULT_BORDER;
	private String originalGenomeData;

	// research
	private boolean isResearch = false;

	private boolean isPerfect = false;

	private int daysRemaining = 0;

	//ADDED X & Y VARIABLES FOR POPULATION OF CHROMOSOMES TO BE DRAWN; CAN BE CHANGED IN HINDSIGHT
	private int x = 0;
	private int y = 0;

	public boolean isPerfect() {
		return isPerfect;
	}
	
	public boolean isResearch() {
		return isResearch;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

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
	}

	/**
	 * Initializes with specific fitness function type
	 * @param numOfGenes
	 * @param fitnessFunctionType
	 */
	public Chromosome(int numOfGenes, int fitnessFunctionType, boolean isResearch) {
		this(numOfGenes);
		this.fitnessFunctionType = fitnessFunctionType;
		this.isResearch = isResearch;
	}
	
	public Chromosome(String fileData) throws InvalidChromosomeFormatException{ 
		this.initiateGeneWithString(fileData);
		// this.fileData = fileData;
		// this.numOfGenes = this.fileData.length();
		// this.initiateGeneWithFile();
		// this.calcFitnessFuction();
		//this.fitnessSmiley();
	}
	
	public Chromosome(String fileData, boolean mutate, double mutationRate) throws InvalidChromosomeFormatException{
		this.numOfGenes = fileData.length();
		this.initiateGeneWithString(fileData);
		this.calcFitnessFuction();
		
		if (mutate){this.mutateGenes(mutationRate);}
	}

	public Chromosome(String fileData, boolean mutate, double mutationRate, int fitnessFunctionType, boolean isResearch) throws InvalidChromosomeFormatException{
		this.numOfGenes = fileData.length();
		this.fitnessFunctionType = fitnessFunctionType;
		this.initiateGeneWithString(fileData);
		this.calcFitnessFuction();
		this.isResearch = isResearch;

		if (mutate){this.mutateGenes(mutationRate);}
	}

	public Chromosome(boolean isResearch){
		this.isResearch = isResearch;
		this.fitnessScore = -1;
	}

	public Chromosome(String fileData, boolean isResearch){
		this.isResearch = isResearch;
		this.fitnessScore = -1;
		initiateGeneWithString(fileData);
	}

	/**
	* ensures: that a numOfGenes Gene objects is initialized into the chromosome
	*/
	public void initiateGene() {
		this.initiateGeneLoad();
		
		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
		//this.fitnessSmiley();
	}

	public void initiateGeneLoad(){
		this.genes = new Gene[this.numOfGenes];
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int limit = isResearch ? 4 : 2;
				int bit = r.nextInt(0,limit);
				char aBit = ' ';
				if (bit == 0){
					aBit = '0';
				}
				else if (bit == 1){
					aBit = '1';
				} else{
					aBit = '?';
				}
				// this.genes[i*numPerColumn+j] = new Gene((char)(bit+'0'), true, this.geneSide*j, this.geneSide*i, this.geneSide);
				this.genes[i*NUM_PER_ROW+j] = new Gene(aBit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
	}
	
	/**
	 * ensures: initiates the genome with a given data
	 * @param s given data in a form of "0100101"
	 * @throws InvalidChromosomeFormatException if s.length() % 10 != 0
	 */
	public void initiateGeneWithString(String s) {
		this.initiateGeneWithStringLoad(s);
		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
	}

	/**
	 * ensures: initiates the genome with a given data
	 * @param s given data in a form of "0100101"
	 * @throws InvalidChromosomeFormatException if s.length() % 10 != 0
	 */
	public void initiateGeneWithStringLoad(String s) {
		this.originalGenomeData = s;
		this.numOfGenes = s.length();

		this.genes = new Gene[numOfGenes];
		this.numPerColumn = numOfGenes / NUM_PER_ROW;
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = s.charAt(i*NUM_PER_ROW+j);
				this.genes[i*NUM_PER_ROW+j] = new Gene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
	}
	
	//methods
	/**
	 * ensures: the correct fitness function is used
	 */
	public void calcFitnessFuction() {
		if (this.fitnessFunctionType == 0) {
			this.calculateDefaultFitnessFunction();
		} else if (this.fitnessFunctionType == 1 || this.fitnessFunctionType == 2) {
			this.fitnessTarget();
		}
		else {
			System.out.println("Warning. Wrong fitness function selected.");
		}
	}

	/*
	* ensures: that the fitness score for the chromosome is calculated
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
	}
	
	/**
	* ensures: calculates fitness score based on similarity with the targetString
	*/
	public void fitnessTarget() {
		String targetString;
		if (this.fitnessFunctionType==1){
			targetString = smileyGeneticData;
		}
		else {
			targetString = susGeneticData;
		}
		int matchingBits = 0;
		
		// Ensure that the targetString and chromosome data have the same length
		if (targetString.length() != this.numOfGenes) {
			throw new IllegalArgumentException("Chromosome length does not match target string length.");
		}
		
		String chromosomeData = getChromosomeDataAsString();
		
		// Compare each bit of the chromosome with the target string
		for (int i = 0; i < chromosomeData.length(); i++) {
			if (chromosomeData.charAt(i) == targetString.charAt(i)) {
				matchingBits++;
			}
		}
		
		// Set the fitness score based on the number of matching bits
		this.fitnessScore = (int) ((matchingBits / (double) numOfGenes) * MAX_FITNESS_SCORE);
	}
	
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
	}
	
	public void adjustGenePosition(){
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				this.genes[i*NUM_PER_ROW+j].setX(this.geneWidth*j + this.border);
				this.genes[i*NUM_PER_ROW+j].setY(this.geneWidth*i);
				this.genes[i*NUM_PER_ROW+j].setGeneWidth(this.geneWidth);
			}
		}
	}
	
	public void mutateGenes(double mutationRate){
		for (Gene gene : this.genes){
			gene.mutate(mutationRate, this.numOfGenes);
		}
		this.calcFitnessFuction();
		//this.fitnessSmiley();
	}

	// think this would make more sense to be done in population?

	// public void doCrossover(Chromosome other){
	// 	int crossoverPoint = r.nextInt(CROSSOVER_OFFSET, numOfGenes-CROSSOVER_OFFSET); // set to 1 and numOfGenes-1 because there would no crossover if the point was at the last index, or at 0 as it would replace the entirety of the chromosome's data
	// 	String crossoverData = other.getChromosomeDataAsString();
	// 	System.out.println(crossoverPoint);
	// 	for (int i = crossoverPoint; i<crossoverData.length(); i++){
	// 		this.genes[i].setBit(crossoverData.charAt(i));
	// 	}
	// }
	
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
	}
	
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
		this.calcFitnessFuction();
		return this.fitnessScore;
	} // getFitnessScore
	
	/**
	 * ensures: that the genes in the chromosome is set to the param genes
	 * @param genes
	 */
	public void setGenes(Gene[] genes) {this.genes = genes;}
	
	/**
	 * ensures: that the genes in the chromosome is returnde
	 * @return the genes array of the chromosome
	 */
	public Gene[] getGenes() {return this.genes;}
	
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
			if (this.genes[i].getBit()=='1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (this.genes[i].getBit()=='0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			} else {
				g2.setColor(GENE_2_TEXT_COLOR);
			}
			g2.setFont(new Font(null, Font.PLAIN, geneWidth/3));
			g2.drawString((String)(i+""), this.genes[i].getX() + X_COORD_LETTER_OFFSET, geneWidth/3 + this.genes[i].getY());
		}
	}

	//Is the drawing method called for drawing the chromosomes; In hindsight, can be removed/edited, as it is code duplication
	public void drawPopulationView(Graphics2D g, int geneWidth, int border) {
		Graphics2D g2 = (Graphics2D) g;
		//This could just be replaced with Chromosome.drawOn(?), the only part that matters is that the chromosome is drawn at the given coordinates
		g2.translate(x,y);
		this.geneWidth = geneWidth;
		this.border = border;
		this.adjustGenePosition();
		this.drawGenes(g2);
		g2.translate(-x,-y);
	}

	//Is the drawing method called for drawing the best chromosome; In hindsight, can be removed/edited, as it is code duplcation.
	public void drawBestView(Graphics2D g, int geneWidth, int border, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(x,y);
		this.geneWidth = geneWidth;
		this.border = border;
		this.adjustGenePosition();
		this.drawGenes(g2);
	}

	public void drawGenes(Graphics2D g2){
		for (int i = 0; i < this.genes.length; i++) {
			this.genes[i].drawOn(g2);
			if (this.genes[i].getBit()=='1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (this.genes[i].getBit()=='0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			}
		}
	}
	
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
	}
	
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
	}

	//TODO CHANGE LIMIT OF DAYS BACK TO 1000
	public void liveLife() {
		// TODO FIGURE OUT WHERE THE FITNESS SCORE IS BEING SET AGAIN :/ FOR NOW JUST SETTING IT TO -1 HERE CUZ YEAH
		this.fitnessScore = -5;
		this.originalGenomeData = this.getChromosomeDataAsString();
        for (int days = 0; days < 1000; days++){
            this.loadGeneFromOriginalData();
            for (int i = 0; i < genes.length; i++){
                Gene gene = this.genes[i];
                if (!isPerfect && gene.getBit()=='?'){
                    gene.setRandomBit();
                }
            }
            if (checkAll1s(this.getChromosomeDataAsString())){
                this.isPerfect = true;
				this.daysRemaining = 1000 - days - 1;
				//this.fitnessScore = calculateFitnessScoreResearch();
				//System.out.println(this.fitnessScore);
                return;
            }
            this.daysRemaining = 1000 - days -1 ;
        }
		this.loadGeneFromOriginalData();
		this.fitnessScore = calculateFitnessScoreResearch();
		//System.out.println(this.fitnessScore);
    }

	public void loadGeneFromOriginalData(){
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = this.originalGenomeData.charAt(i*NUM_PER_ROW+j);
				this.genes[i*NUM_PER_ROW+j] = new Gene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
	}

	public boolean checkAll1s(String s){
        int score = 0;
        for (int i = 0; i < s.length(); i++){
            if (Character.getNumericValue(s.charAt(i)) == 1) {
                score++;
            }
        }
        return (score == s.length());
    }

	public double calculateFitnessScoreResearch(){
		double fitnessScore = (1 + (19*this.daysRemaining)/1000)*5;
		return fitnessScore;
	}
	
	/**
	 * ensures: returns genome as a string for printing purposes
	 */
	@Override
	public String toString() {
		return this.getChromosomeDataAsString();
	}

	public void setFitnessFunctionType(int fitnessFunction) {
		this.fitnessFunctionType = fitnessFunction;
	}
}
