package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class ChromosomeComponent extends JComponent{
	public static final int X_MOUSE_COORD_OFFSET = 7;
	public static final int Y_MOUSE_COORD_OFFSET = 45;
	
	private Chromosome chromosome;
	
	public ChromosomeComponent() {
		this.chromosome = new Chromosome();
	}
	
	public void setChromosome(Chromosome c) {this.chromosome = c;}
	public Chromosome getChromosome() {return this.chromosome;}
	
	// Method to access the private object of Chromosome and its method, storeChromosomeData
    public void handleStoreChromosomeData(String s) {
        this.chromosome.storeChromosomeData(s);
    }
    
    // Method to access the private object of Chromosome and its method, loadGene
    public void handleLoadGene() {
        this.chromosome.loadGene();
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		chromosome.drawOn(g2);	
	}
	
	//TODO Make method for selecting gene and stuff
	public Gene containsGene(int x, int y) {
		Rectangle2D.Double box = new Rectangle2D.Double(x-X_MOUSE_COORD_OFFSET, y-Y_MOUSE_COORD_OFFSET, 1, 1); // Mouse click coordinates were at an offset
		for (int i = 0; i < chromosome.genes.length; i++) {
			if (chromosome.genes[i].isSelected(box)) {
				return chromosome.genes[i];
			}
		}
		return null;
	}
}
