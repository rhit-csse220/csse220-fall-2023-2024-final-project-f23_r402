package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Chromosome implements Comparable {
	public static final int NUM_PER_ROW = 10;
	public static final int X_COORD_LETTER_OFFSET = 5;
	public static final int Y_COORD_LETTER_OFFSET = 10;
	
	public static final Color GENE_0_TEXT_COLOR = Color.WHITE;
	public static final Color GENE_1_TEXT_COLOR = Color.BLACK;
	
	private int numOfGenes = 100; //default values
	private int numPerColumn = 10; //default values
	private String fileData = "";
	public Gene[] genes;
	private double fitnessScore;
	private int geneWidth = Gene.DEFAULT_GENE_SIDE;
	private int border = ChromosomeComponent.DEFAULT_BORDER;
	
	//Setting seed for the Random object
	Random r = new Random(100010001);
	
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

	public Chromosome(String fileData){
		this.fileData = fileData;
		this.initiateGeneWithFile();
		this.calcFitnessFuction();
	}

	//methods
	/*
	 * ensures: that the fitness score for the chromosome is calculated
	 */
	public void calcFitnessFuction() {
		int fitnessScore = 0;
		String fileData = getChromosomeDataAsString();
		for (int i = 0; i < fileData.length(); i++){
			if (fileData.charAt(i) == '1'){
				fitnessScore++;
			}
		}
		this.fitnessScore = fitnessScore;
	}
	
	  public void fitnessSmiley() {
	        String targetString = "1111111111111111111111011110111111111111111111111111111111111111111111101111110110000000011111111111";
	        int matchingBits = 0;

	        // Ensure that the targetString and chromosome data have the same length
	        if (targetString.length() != getNumOfGenes()) {
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
	        this.fitnessScore = matchingBits;
	    }
	  
	  
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
		genes = new Gene[numOfGenes];
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int bit = r.nextInt(0,2);
				// this.genes[i*numPerColumn+j] = new Gene((char)(bit+'0'), true, this.geneSide*j, this.geneSide*i, this.geneSide);
				this.genes[i*NUM_PER_ROW+j] = new Gene((char)(bit+'0'), true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}

		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
	}

	/**
	 * ensures: that a numOfGenes Gene objects is initialized into the chromosome
	 */
	public void initiateGeneWithFile() {
		genes = new Gene[numOfGenes];
		numPerColumn = numOfGenes / NUM_PER_ROW;
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = this.fileData.charAt(i*NUM_PER_ROW+j);
				this.genes[i*NUM_PER_ROW+j] = new Gene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}

		// TO SET THE FITNESS SCORE
		this.calcFitnessFuction();
	}

	/**
	 * ensures: adjusts positioning of genes with a new border and new gene width
	 */
	public void adjustGenePosition(){
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				this.genes[i*NUM_PER_ROW+j].setX(this.geneWidth*j + this.border);
				this.genes[i*NUM_PER_ROW+j].setY(this.geneWidth*i);
				this.genes[i*NUM_PER_ROW+j].setGeneWidth(this.geneWidth);
			}
		}
	}

	/**
	 * ensures: mutates the genes in the chromosome
	 * @param mutationRate
	 */
	public void mutateGenes(double mutationRate){
		for (Gene gene : this.genes){
			gene.mutate(mutationRate, this.numOfGenes);
		}
		this.calcFitnessFuction();
	}

	/**
	 * ensures: that the data from the file is loaded into the chromosome's file data
	 * @param s is the input for the method to add into the file data of the chromosome
	 */
	public void storeChromosomeData(String s) {
		this.fileData = this.fileData.concat(s);
		this.numOfGenes = this.fileData.length();
	}

	/**
	 * ensures: that the chromosome data, i.e the genes and their values, are concatenated into a single string
	 * @return the chromosome data
	 */
	public String getChromosomeDataAsString() {
	    StringBuilder data = new StringBuilder();
	    for (Gene gene : genes) {
	        // Append '1' for black and '0' for green
	        if (gene.getBit() == '0') {
	            data.append('0');
	        } else if (gene.getBit() == '1'){
	            data.append('1');
	        }
	    }
	    return data.toString();
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
	 * @return fitness score of the chromosome
	 */
	public double getFitnessScore(){return this.fitnessScore;} //getFitnessScore
	
	/**
	 * ensures: genes in the chromosome are drawn onto the frame
	 * @param g
	 * @param geneWidth
	 * @param border
	 */
	public void drawOn(Graphics g, int geneWidth, int border) {
	    Graphics2D g2 = (Graphics2D) g;
	    this.geneWidth = geneWidth;
	    this.border = border;
		adjustGenePosition();
 
	    for (int i = 0; i < genes.length; i++) {
			genes[i].drawOn(g2);
			if (genes[i].getBit()=='1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (genes[i].getBit()=='0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			}
			g2.drawString((String)(i+""), genes[i].getX() + X_COORD_LETTER_OFFSET, Y_COORD_LETTER_OFFSET+genes[i].getY());
		}
	}

	/**
	 * ensures: compares
	 * @param otherChromosome
	 * @return
	 */
	public int compareTo(Object otherChromosome) {
		double thisFitness = this.getFitnessScore();
		double otherFitness = ((Chromosome)otherChromosome).getFitnessScore();

		// Compare the fitness scores of this chromosome and the other chromosome
		if (thisFitness < otherFitness) {
			return 1;
		} else if (thisFitness > otherFitness) {
			return -1;
		} else {
			return 0;
		}
	}
}
