package mainApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Gene {
	public static final int GENE_SIDE = 30; // side length of gene square // TODO CHANGE THIS
	public static final Color GENE_0_COLOR = Color.BLACK;
	public static final Color GENE_1_COLOR = Color.GREEN;
	
	
	public char bit;
	private boolean changeable;
	// x and y are top left corners of square
	private int x;
	private int y;
	// private int geneSide;

	// constructor
	public Gene(char bit, boolean changeable, int x, int y, int geneSide) {
		this.bit = bit;
		this.changeable = changeable;
		this.x = x;
		this.y = y;
		// this.geneSide = geneSide;
	}
	
	// methods
	public void drawOn(Graphics g) {
	    // Treat Graphics as a Graphics2D
	    Graphics2D g2 = (Graphics2D) g;
	    if (this.bit == '0') {
	         g2.setColor(GENE_0_COLOR);
	    } else if (this.bit == '1') {
            g2.setColor(GENE_1_COLOR);
        }
        // g2.fillRect(x, y, this.geneSide, this.geneSide);
		g2.fillRect(x, y, GENE_SIDE, GENE_SIDE);
    }

	
	@Override
	public String toString() {
		return "Gene [x=" + x + ", y=" + y + ", bit=" + bit + ", changeable=" + changeable + "]";
	}
	
	// used for when the bit is 0 or 1
	public void changeBit() {
        if (this.bit == '0') {
            this.bit = '1';
        } else if (this.bit == '1') {
            this.bit = '0';
        }
    }
	
	public void setNonChangeable() {this.changeable = false;}
	
	public void setChangeable() {this.changeable = true;}

	public char getBit() {return bit;}

	public void setBit(char bit) {this.bit = bit;}

	public int getX() {return x;}

	public void setX(int x) {this.x = x;}

	public int getY() {return y;}

	public void setY(int y) {this.y = y;}
	
    public boolean isChangeable() {return changeable;}

	public int getGeneSide() {return GENE_SIDE;}

	// public void setGeneSide(int geneSide) {this.geneSide = geneSide;}
	
	public boolean isSelected(Rectangle2D.Double box) {
		Rectangle2D.Double boundingBox = new Rectangle2D.Double(x, y, GENE_SIDE, GENE_SIDE);
		if (boundingBox.contains(box)){
			return true;
		} else {
			return false;
		}
	}
}
