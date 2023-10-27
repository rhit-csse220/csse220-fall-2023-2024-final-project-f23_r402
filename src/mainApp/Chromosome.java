package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Chromosome {
	public static final int NUM_PER_ROW = 10;
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
	
	Random r = new Random();
	
	/**
	 * Creates a new Chromosome object
	 */
	public Chromosome(){}// Chromosome

	/**
	 * Creates a new Chromosome object with different number of genes than the default 100
	 * @param numOfGenes
	 */
	// Are we still adding the resizable geneSide according to frameWidth/Height?
	// Will add it but not for Milestone 1 for now - need to ask Thomas
	public Chromosome(int numOfGenes) {
		this.numOfGenes = numOfGenes;
		// this.geneSide = geneSide

		this.numPerColumn = numOfGenes/NUM_PER_ROW;
	}

	//methods
	/**
	 * ensures: that the fitness score for the chromosome is calculated
	 */
	public void calcFitnessFuction() {
		//TODO calc + store fitness score
		this.fitnessScore = 0;
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
				this.genes[i*numPerColumn+j] = new Gene((char)(bit+'0'), true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
	}

	public void initiateGeneWithFile() {
		genes = new Gene[numOfGenes];
		numPerColumn = numOfGenes / NUM_PER_ROW;
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = this.fileData.charAt(i*numPerColumn+j);
				this.genes[i*numPerColumn+j] = new Gene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
	}

	public void adjustGenePosition(){
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				this.genes[i*numPerColumn+j].setX(this.geneWidth*j + this.border);
				this.genes[i*numPerColumn+j].setY(this.geneWidth*i);
				this.genes[i*numPerColumn+j].setGeneWidth(this.geneWidth);
			}
		}
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

	// public int getGeneSide() {return geneSide;}

	// public void setGeneSide(int geneSide) {
	// 	for (int i = 0; i < this.genes.length; i++){
	// 		this.genes[i].setGeneSide(geneSide)
	// 	}
	// }
	
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
			g2.drawString((String)(i+""), genes[i].getX()+this.border, Y_COORD_LETTER_OFFSET+genes[i].getY());
		}
	}
}