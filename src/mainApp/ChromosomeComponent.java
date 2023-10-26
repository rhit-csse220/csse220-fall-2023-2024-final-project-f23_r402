package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class ChromosomeComponent extends JComponent{
	public static final int X_MOUSE_COORD_OFFSET = 7;
	public static final int Y_MOUSE_COORD_OFFSET = 45;
	
	private Chromosome chromosome;
	
	/**
	 * ensures: that a new ChromosomeComponent object is created, instantiating its own chromosome as well
	 */
	public ChromosomeComponent() {
		this.chromosome = new Chromosome();
		this.chromosome.initiateGene();
	} //ChromosomeComponent
	
	/**
	 * ensures: that the chromosome of the component is changed to a new chromosome
	 * @param c is the chromosome that is going to be used as the chromosome component's new chromosome
	 */
	public void setChromosome(Chromosome c) {this.chromosome = c;} //setChromosome
	
	/**
	 * ensures: that the chromosome object is returned when called for
	 * @return the chromosome object of the Chromosome component
	 */
	public Chromosome getChromosome() {return this.chromosome;} //getChromosome
	
	/**
	 * ensures: to access the chromosome and store the data being read from the file into the chromosome's file data
	 * @param s is the file data being passed back to be added to the chromosome's file data
	 */
    public void handleStoreChromosomeData(String s) {
        this.chromosome.storeChromosomeData(s);
    } //handleStoreChromosomeData
    
    /**
     * ensures: that the chromosome is able to load in its genes according to the values given in the chromosome's file data
     */
    public void handleInitiateGeneWithFile() {
        this.chromosome.initiateGeneWithFile();
    } //handleInitiateGeneWithFile
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		chromosome.drawOn(g2);	
	}
	
	/**
	 * ensures: that given the coordinates, the method is able to determine which gene object is being clicked on
	 * @param x is the x coordinates of the mouse on the screen
	 * @param y is the y coordinates of the mouse on the screen
	 * @return the gene object that is being clicked on, and null if it is not found
	 */
	public Gene containsGene(int x, int y) {
		Rectangle2D.Double box = new Rectangle2D.Double(x-X_MOUSE_COORD_OFFSET, y-Y_MOUSE_COORD_OFFSET, 1, 1); // Mouse click coordinates were at an offset
		for (int i = 0; i < chromosome.genes.length; i++) {
			if (chromosome.genes[i].isSelected(box)) {
				return chromosome.genes[i];
			}
		}
		return null;
	} //containsGene
}
