package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	// when numOfGenes == 100
	// // public static final int NUM_OF_GENES = 100;
	// public static final int NUM_PER_ROW_100 = 10;
	// // when numOfGenes == 20
	// public static final int NUM_PER_ROW_20 = 5;
	
	public static final Color GENE_0_TEXT_COLOR = Color.WHITE;
	public static final Color GENE_1_TEXT_COLOR = Color.BLACK;
	
	private int numOfGenes = 100; //default values
	private int numPerRow = 10; //default values
	private int numPerColumn = 10; //default values
	private String fileData = "";
	public Gene[] genes;
	private double fitnessScore;
	// private int geneSide;
	
	Random r = new Random();
	
	/**
	 * Creates a new Chromosome object
	 */
	public Chromosome(){}// Chromosome

	/**
	 * ensures: 
	 * @param numOfGenes
	 * @param geneSide
	 */
	// Are we still adding the resizable geneSide according to frameWidth/Height?
	public Chromosome(int numOfGenes, int geneSide) {
		this.numOfGenes = numOfGenes;
		// this.geneSide = geneSide;
		if (numOfGenes == 20){
			this.numPerRow = 5;
			this.numPerColumn = 4;
		} else if (numOfGenes == 100){
			this.numPerRow = 10;
			this.numPerColumn = 10;
		}
	}
	
	// methods
	public int getNumPerRow() {return this.numPerRow;}
	public int getNumPerColumn() {return this.numPerColumn;}
	public int getNumOfGenes() {return this.numOfGenes;}

	// public int getGeneSide() {return geneSide;}

	// public void setGeneSide(int geneSide) {
	// 	for (int i = 0; i < this.genes.length; i++){
	// 		this.genes[i].setGeneSide(geneSide)
	// 	}
	// }

	public boolean checkChromosomeData() {
        if (fileData.length()%10!=0 && fileData!="") {
            return false;
        }
        for (int i = 0; i < fileData.length(); i++) {
            if (!(fileData.charAt(i)=='0' || fileData.charAt(i)=='1')) {
                return false;
            }
        }
        return true;
    }
	
	public void storeChromosomeData(String s) {
		this.fileData = this.fileData.concat(s);
		this.numOfGenes=this.fileData.length();
		// TODO add exceptions
	}
	
	public void calcFuction() {
		//TODO calc + store fitness score
		this.fitnessScore = 0;
	}
	
	public void initiateGene() {
		genes = new Gene[numOfGenes];
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < numPerRow; j++) {
				int bit = r.nextInt(0,2);
				// this.genes[i*numPerColumn+j] = new Gene((char)(bit+'0'), true, this.geneSide*j, this.geneSide*i, this.geneSide);
				this.genes[i*numPerColumn+j] = new Gene((char)(bit+'0'), true, Gene.GENE_SIDE*j, Gene.GENE_SIDE*i, Gene.GENE_SIDE);
			}
		}
	}
	
	public void initiateGeneWithFile() {
		genes = new Gene[numOfGenes];
		numPerColumn=numOfGenes/numPerRow;
		for (int i = 0; i < numPerColumn; i++) {
			for (int j = 0; j < numPerRow; j++) {
				char bit = this.fileData.charAt(i*numPerColumn+j);
				this.genes[i*numPerRow+j] = new Gene(bit, true, Gene.GENE_SIDE*j, Gene.GENE_SIDE*i, Gene.GENE_SIDE);
			}
		}
	}
	
	/***
     * Debugger method
     */
//  public String genesToString() {
//      String s = "";
//      for (int i = 0; i < genes.length; i++) {
//          s = s.concat(""+genes[i].getBit());
//      }
//      return s;
//  }
	
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < genes.length; i++) {
			genes[i].drawOn(g2);
			if (genes[i].getBit()=='1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (genes[i].getBit()=='0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			}
			g2.drawString((String)(i+""), genes[i].getX(), 10+genes[i].getY());
		}
	}
	
	// TODO check this
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
	
}