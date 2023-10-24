package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	public static final int NUM_OF_GENES = 100;
	public static final int NUM_PER_ROW = 10;
	
	private String fileData;
	private Gene[] genes = new Gene[NUM_OF_GENES];
	private double fitnessScore;
	
	Random r = new Random();
	
	// constructor
	public Chromosome() {
		//TODO idk what to put
		initiateGene();
	}
	
	// methods
	public void storeChromosomeData(String s) {
		this.fileData = this.fileData.concat(s);
	}
	
	public void calcFuction() {
		//TODO calc + store fitness score
		this.fitnessScore = 0;
	}
	
	public void initiateGene() {
		for (int i = 0; i < NUM_PER_ROW; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int bit = r.nextInt(0,2);
				this.genes[i*10+j] = new Gene((char)(bit+'0'), true, Gene.GENE_SIDE*j, Gene.GENE_SIDE*i);
			}
		}
	}
	
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		for (Gene gene : genes) {
			gene.drawOn(g2);
		}
	}
}
