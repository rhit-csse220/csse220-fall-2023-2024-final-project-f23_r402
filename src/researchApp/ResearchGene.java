package researchApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Random;

import mainApp.*;

public class ResearchGene extends Gene {

    final Color GENE_2_COLOR = new Color(3, 199, 201);
    Random r = new Random();

    public ResearchGene(char bit, boolean changeable, int x, int y, int geneWidth) {
        super(bit, changeable, x, y, geneWidth);
	} //ResearchGene

    @Override
    public void drawOn(Graphics g) {
        // Treat Graphics as a Graphics2D
	    Graphics2D g2 = (Graphics2D) g;
	    if (this.bit == '0') {
	         g2.setColor(GENE_0_COLOR);
	    } else if (this.bit == '1') {
            g2.setColor(GENE_1_COLOR);
        } else if (this.bit == '?'){
            g2.setColor(GENE_2_COLOR);
        }
        // g2.fillRect(x, y, this.geneSide, this.geneSide);
		g2.fillRect(this.x + this.border, this.y, this.geneWidth, this.geneWidth);
    }

    public void changeBit(){
        if (this.bit=='?'){
            int value = r.nextInt(0,2);
            this.bit = value==0 ? '0' : '1';
        }
    }
}
