package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * Class: ChromosomeComponent
 * @author F23_R402
 * 
 * Purpose: the class is used for the code required for the visuals on the frame of the viewer
 */
public class ChromosomeComponent extends JComponent{
	// constants
	public static final int X_MOUSE_COORD_OFFSET = 7;
	public static final int Y_MOUSE_COORD_OFFSET = 45;
	public static final int DEFAULT_BORDER = 3;
	public static final int SCALING_NUMBER = 2;
	
	// fields
	private Chromosome chromosome;
	
	/**
	 * ensures: that a new ChromosomeComponent object is created, instantiating its own chromosome as well
	 */
	public ChromosomeComponent() {
		this.chromosome = new Chromosome(true);
		this.chromosome.initiateGene();
	} //ChromosomeComponent
	
	/**
	 * ensures: calculates the gene width based on the frame's dimensions
	 * 
	 * @return the gene width based on the component's dimensons
	 */
	public int findGeneWidth() {
		int compHeight = this.getHeight();
		int compWidth = this.getWidth();
		if (compWidth <= compHeight) {
			return (compWidth - DEFAULT_BORDER * SCALING_NUMBER) / (Chromosome.NUM_PER_ROW);
		} else {
			return (compHeight) / (Chromosome.NUM_PER_ROW);
		}
	} // findGeneWidth

	/**
	 * ensures: calculates the horizontal border for the component
	 * @return the horizontal border based on the component's dimensions
	 */
	public int findBorder(){
		int compHeight = this.getHeight();
		int compWidth = this.getWidth();
		if (compWidth <= compHeight) {
			return DEFAULT_BORDER;
		} else {
			return (compWidth - compHeight)/SCALING_NUMBER;
		}
	} // findBorder

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
	 * ensures: draws the chromosome
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		chromosome.drawOn(g2, findGeneWidth(), findBorder()); // debugger
	} //paintComponent
	
	/**
	 * ensures: that given the coordinates, the method is able to determine which gene object is being clicked on
	 * @param x is the x coordinates of the mouse on the screen
	 * @param y is the y coordinates of the mouse on the screen
	 * @return the gene object that is being clicked on, and null if it is not found
	 */
	public Gene containsGene(int x, int y) {
		Rectangle2D.Double box = new Rectangle2D.Double(x-X_MOUSE_COORD_OFFSET, y-Y_MOUSE_COORD_OFFSET, 1, 1); // Mouse click coordinates were at an offset
		return chromosome.handleGetSelectedGene(box);
	} //containsGene

	/**
	 * ensures: loads genome data with String similarly to Chromosome.initiateGeneWithString()
	 * @param fileData data in the format of "01001001"
	 * @throws InvalidChromosomeFormatException if fileData.length() % 10 != 0
	 */
	public void handleLoadDataFromFile(String fileData) throws InvalidChromosomeFormatException {
		this.setChromosome(new Chromosome());
		this.chromosome.initiateGeneWithString(fileData);
	} //handleLoadDataFromFile

	/**
	 * ensures: mutates genes in the chromosome
	 * @param mutationRate
	 */
	public void handleMutateGenesInChromosome(double mutationRate) {
		this.chromosome.mutateGenes(mutationRate);
	} //handleMutateGenesInChromosome

	/**
	 * ensures: returns the length of the genome
	 * @return length of the genome
	 */
	public int handleGetNumberOfGenesInChromosome() {
		return this.chromosome.getNumOfGenes();
	} //handleGetNumberOfGenesInChromosome

	/**
	 * ensures: returns chromosome data in te form of "10010100"
	 * @return chromosome data
	 */
	public String handleGetChromosomeDataAsString() {
		return this.chromosome.getChromosomeDataAsString();
	} //handleGetChromosomeDataAsString
} //End ChromosomeComponent
