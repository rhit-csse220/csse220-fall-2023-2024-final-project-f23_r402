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
	public static final int CROSSOVER_OFFSET = 2;
	
	public static final Color GENE_0_TEXT_COLOR = Color.WHITE;
	public static final Color GENE_1_TEXT_COLOR = Color.BLACK;
	
	private int numOfGenes = 100;           //default values
	private int numPerColumn = 10;          //default values
	private int fitnessFunctionType = 0;;   //default values
	private Gene[] genes;
	private double fitnessScore;
	private int geneWidth = Gene.DEFAULT_GENE_SIDE;
	private int border = ChromosomeComponent.DEFAULT_BORDER;

	//ADDED X & Y VARIABLES FOR POPULATION OF CHROMOSOMES TO BE DRAWN; CAN BE CHANGED IN HINDSIGHT
	public int x = 0;
	public int y = 0;
	
	// Seeding the Random object
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
	public Chromosome(int numOfGenes, int fitnessFunctionType) {
		this(numOfGenes);
		this.fitnessFunctionType = fitnessFunctionType;
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
		//this.fileData = fileData;
		this.numOfGenes = fileData.length();
		this.initiateGeneWithString(fileData);
		this.calcFitnessFuction();
		//this.fitnessSmiley();
		
		if (mutate){this.mutateGenes(mutationRate);}
	}

	public Chromosome(String fileData, boolean mutate, double mutationRate, int fitnessFunctionType) throws InvalidChromosomeFormatException{
		//this.fileData = fileData;
		this.numOfGenes = fileData.length();
		this.fitnessFunctionType = fitnessFunctionType;
		this.initiateGeneWithString(fileData);
		this.calcFitnessFuction();
		//this.fitnessSmiley();
		
		if (mutate){this.mutateGenes(mutationRate);}
	}
	
	//methods
	/*
	* ensures: that the fitness score for the chromosome is calculated
	*/
	public void calcFitnessFuction() {
		if (this.fitnessFunctionType == 0) {
			this.calculateDefaultFitnessFunction();
		} else if (this.fitnessFunctionType == 1) {
			this.fitnessSmiley();
		} else {
			System.out.println("Warning. Wrong fitness function selected.");
		}
	}

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
	public void fitnessSmiley() {
		String targetString = "1111111111111111111111011110111111111111111111111111111111111111111111101111110110000000011111111111";
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
		this.fitnessScore = matchingBits / MAX_FITNESS_SCORE;
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
	
	/**
	* ensures: that a numOfGenes Gene objects is initialized into the chromosome
	*/
	public void initiateGene() {
		this.genes = new Gene[this.numOfGenes];
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int bit = r.nextInt(0,2);
				// this.genes[i*numPerColumn+j] = new Gene((char)(bit+'0'), true, this.geneSide*j, this.geneSide*i, this.geneSide);
				this.genes[i*NUM_PER_ROW+j] = new Gene((char)(bit+'0'), true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
		
		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
		//this.fitnessSmiley();
	}
	
	/**
	 * ensures: initiates the genome with a given data
	 * @param s given data in a form of "0100101"
	 * @throws InvalidChromosomeFormatException if s.length() % 10 != 0
	 */
	public void initiateGeneWithString(String s) throws InvalidChromosomeFormatException {
		if (s.length() % 10 != 0) {
			throw new InvalidChromosomeFormatException(s.length());
		}
		
		this.numOfGenes = s.length();
		
		this.genes = new Gene[numOfGenes];
		this.numPerColumn = numOfGenes / NUM_PER_ROW;
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = s.charAt(i*NUM_PER_ROW+j);
				this.genes[i*NUM_PER_ROW+j] = new Gene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}

		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
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

	public void doCrossover(Chromosome other){
		int crossoverPoint = r.nextInt(ORIGIN, numOfGenes-CROSSOVER_OFFSET); // set to 1 and numOfGenes-2 because there would no crossover if the point was at the last index, or at 0 as it would replace the entirety of the chromosome's data
		String crossoverData = other.getChromosomeDataAsString();
		System.out.println(crossoverPoint);
		for (int i = crossoverPoint; i<crossoverData.length(); i++){
			this.genes[i].setBit(crossoverData.charAt(i));
		}
	}
	
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
	public double getFitnessScore(){return this.fitnessScore;} // getFitnessScore
	
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
		drawGenes(g2);
		g2.translate(-x,-y);
	}

	//Is the drawing method called for drawing the best chromosome; In hindsight, can be removed/edited, as it is code duplcation.
	public void drawBestView(Graphics2D g, int geneWidth, int border, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(x,y);
		this.geneWidth = geneWidth;
		this.border = border;
		this.adjustGenePosition();
		drawGenes(g2);
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