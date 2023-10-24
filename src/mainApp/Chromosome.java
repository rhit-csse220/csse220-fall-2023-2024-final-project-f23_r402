package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	public static final int NUM_OF_GENES = 100;
	public static final int NUM_PER_ROW = 10;
	
	private String fileData;
	public Gene[] genes = new Gene[NUM_OF_GENES];
	private double fitnessScore;
	
	Random r = new Random();
	
	// constructor
	public Chromosome() {
		//TODO idk what to put
		this.fileData="";
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
	
	public void loadGene() {
		for (int i = 0; i < fileData.length(); i++) {
			this.genes[i].setBit(this.fileData.charAt(i));
			System.out.println(this.fileData.charAt(i));
		}
	}
	
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < genes.length; i++) {
			genes[i].drawOn(g2);
			if (genes[i].getBit()=='1') {
				g2.setColor(Color.black);
			}
			else {
				g2.setColor(Color.white);
			}
			g2.drawString((String)(i+""), genes[i].getX(), 10+genes[i].getY());
		}
	}
}
