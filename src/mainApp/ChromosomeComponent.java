package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class ChromosomeComponent extends JComponent{
	private Chromosome chromosome;
	
	public ChromosomeComponent() {
		this.chromosome = new Chromosome();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		chromosome.drawOn(g2);	
	}

	
	//TODO Make method for selecting gene and stuff
	public Gene containsGene(int x, int y) {
		Rectangle2D.Double box = new Rectangle2D.Double(x-7, y-30, 1, 1); // Mouse click coordinates were at an offset
		for (int i = 0; i < chromosome.genes.length; i++) {
			if (chromosome.genes[i].isSelected(box)) {
				return chromosome.genes[i];
			}
		}
		return null;
	}
}
