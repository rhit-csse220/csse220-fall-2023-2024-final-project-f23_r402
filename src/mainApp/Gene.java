package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Class: Gene
 * @author F23_R402
 * 
 * Purpose: The Gene class is used for each gene in the chromosome. Each gene contains a value of 1 or 0, and if research, ?, which can either be 0 or 1. 
 */
public class Gene {
	// constants
	public static final int DEFAULT_GENE_SIDE = 30; // side length of gene square
	public static final Color GENE_0_COLOR = Color.BLACK;
	public static final Color GENE_1_COLOR = Color.GREEN;
	public static final Color GENE_2_COLOR = new Color(255, 227, 43);
	
	// fields
	private char bit;
	private boolean changeable;
	// x and y are top left corners of square
	private int x;
	private int y;
	private int geneWidth = DEFAULT_GENE_SIDE;
	private int border = ChromosomeComponent.DEFAULT_BORDER;

	// Initiate a new Random variable
	Random r = new Random();

	/**
	 * ensures: The instantiation of a new Gene object according to the given parameters
	 * @param bit is two values, a '1' and '0', determining its current state
	 * @param changeable determines if the gene can be clicked on to be changed or not
	 * @param x is the current x-coordinate position of the gene
	 * @param y is the current y-coordinate position of the gene
	 * @param geneSide is the width and height of the gene when drawn in the frame
	 */
	public Gene(char bit, boolean changeable, int x, int y, int geneWidth) {
		this.bit = bit;
		this.changeable = changeable;
		this.x = x;
		this.y = y;
		this.geneWidth = geneWidth;
	} //Gene
	
	/**
	 * ensures: the gene object is drawn
	 * @param g
	 */
	public void drawOn(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    if (this.bit == '0') {
	         g2.setColor(GENE_0_COLOR);
	    } else if (this.bit == '1') {
            g2.setColor(GENE_1_COLOR);
        } else if (this.bit == '?'){
			g2.setColor(GENE_2_COLOR);
		}
		g2.fillRect(this.x + this.border, this.y, this.geneWidth, this.geneWidth);
    }
	
	/**
	 * ensures: changes bit from 0 to 1 or 1 to 0
	 * note: used for when the bit is 0 or 1
	 */
	public void changeBit() {
        if (this.bit == '0') {
            this.bit = '1';
        } else if (this.bit == '1') {
            this.bit = '0';
        }
    }

	/**
	 * ensures: randomizes the bit value to 0 or 1
	 */
	public void setRandomBit(){
		int value = r.nextInt(0,2);
		this.bit = (value == 0 ? '0' : '1');
	}

	/**
	 * ensures: that the currently selected gene is returned if the inputted box overlaps with the gene's box
	 * @param box is the current coordinates of the mouse
	 * @return the currently selected gene
	 */
	public boolean isSelected(Rectangle2D.Double box) {
		Rectangle2D.Double boundingBox = new Rectangle2D.Double(x, y, this.geneWidth, this.geneWidth);
		if (boundingBox.contains(box)){
			return true;
		} else {
			return false;
		}
	} //isSelected

	/**
	 * @return whether the gene is editable through clicking or not
	 */
    public boolean isChangeable() {return changeable;} //isChangeable
	
	/**
	 * ensures: that the gene's state can be changed by clicking on it
	 */
	public void setChangeable() {this.changeable = true;} //setChangeable
	
	/**
	 * ensures: that the gene's state can no longer be changed by clicking on it
	 */
	public void setNonChangeable() {this.changeable = false;} //setNonChangeable

	/**
	 * @return the value of the bit
	 */
	public char getBit() {return bit;} //getBit

	/**
	 * ensures: that the gene's state/bit is modified according to the inputted parameter
	 * @param bit is the new character value for the gene state, varies between '1' and '0'
	 */ 
	public void setBit(char bit) {this.bit = bit;} //setBit

	/**
	 * @return the x-coordinate of the gene
	 */
	public int getX() {return x;} //getX

	/**
	 * ensures: the x-coordinate of the gene is updated
	 * @param x is the new x-coordinate
	 */
	public void setX(int x) {this.x = x;} //setX

	/**
	 * @return the y-coordinate of the gene
	 */
	public int getY() {return y;} //getY

	/**
	 * ensures: the y-coordinate of the gene is updated
	 * @param y is the new y-coordinate
	 */
	public void setY(int y) {this.y = y;} //setY

	/**
	 * @return side length of gene
	 */
	public int getGeneWidth(){return this.geneWidth;}

	/**
	 * ensures: the side length of gene is updated
	 * @param geneSide is the new side length of gene
	 */
	public void setGeneWidth(int geneWidth){this.geneWidth = geneWidth;}

    /**
	 * ensures: the border is updated
	 * @param border
	 */
	public void setBorder(int border) {this.border = border;}

	/**
	 * ensures: mutate the gene
	 * @param mutationRate
	 * @param numOfGenes
	 */
	public void mutate(double mutationRate, int numOfGenes){
		int randomNum = (int)(Math.random() * numOfGenes) + 1;
		if (randomNum <= mutationRate) {
			// Mutate the gene
			this.changeBit();
		}
	}

	@Override
	public String toString() {
		return "Gene [x=" + x + ", y=" + y + ", bit=" + bit + ", changeable=" + changeable + "]";
	}
}
