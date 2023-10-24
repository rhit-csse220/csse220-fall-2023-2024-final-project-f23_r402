package mainApp;

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
	}
	
	// methods
	public void storeChromosomeData(String s) {
		this.fileData = this.fileData.concat(s);
	}
	
	public void calcFuction() {
		//TODO calc + store fitness score
		this.fitnessScore = 0;
	}
	
	public void addGene(Gene gene) {
		for (int i = 0; i < NUM_PER_ROW; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int bit = r.nextInt(0,2);
				genes[i] = new Gene((char)(bit+'0'), true, Gene.GENE_SIDE*j, Gene.GENE_SIDE*i);
			}
		}
	}
}
