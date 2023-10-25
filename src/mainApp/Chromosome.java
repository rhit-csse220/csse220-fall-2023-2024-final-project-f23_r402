package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Chromosome {
	public static final int NUM_OF_GENES = 100;
	public static final int NUM_PER_ROW = 10;
	
	public static final Color GENE_0_TEXT_COLOR = Color.WHITE;
	public static final Color GENE_1_TEXT_COLOR = Color.BLACK;
	
	private String fileData;
	public Gene[] genes = new Gene[NUM_OF_GENES];
	private double fitnessScore;
	
	Random r = new Random();
	
	// constructor
	public Chromosome() {
		this.fileData=""; // TODO don't think this is necessary?
		initiateGene();
	}
	
	// methods
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
		// TODO add exceptions
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
	
	// maybe not needed
	public void initiateGeneWithFile() {
		for (int i = 0; i < NUM_PER_ROW; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int bit = this.fileData.charAt(i*10+j);
				this.genes[i*10+j] = new Gene((char)(bit+'0'), true, Gene.GENE_SIDE*j, Gene.GENE_SIDE*i);
			}
		}
	}
	
	public void loadGene() {
//		for (int i = 0; i < fileData.length(); i++) {
//			this.genes[i].setBit(this.fileData.charAt(i));
//			System.out.println(this.fileData.charAt(i));
//		}
		if (fileData.length() != NUM_OF_GENES) {
			System.out.println("Wrong number of genes in file");
		} else {
			initiateGeneWithFile();
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
	        if (gene.getBit() == '1') {
	            data.append('1');
	        } else {
	            data.append('0');
	        }
	    }
	    return data.toString();
	}
	
}