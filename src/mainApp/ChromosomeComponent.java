package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

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

}
