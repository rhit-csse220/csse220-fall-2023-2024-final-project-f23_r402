package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * Class: IndividualComponent
 * @author F23_R402
 * 
 * Purpose: To visualize the best chromosome on its assigned frame
 */
public class IndividualComponent extends DataComponent{
    // constant
    final static int DEFAULT_BORDER = 3;
    final static int NUM_PER_ROW = 10;
    final static int MULTIPLIER = 2;
    final static int BEST_CHROMOSOME_INDEX = 0;
    
    // int
    private int index;

    public IndividualComponent(){}

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int geneWidth = this.findGeneWidth();
        if (this.population != null && !this.population.isChromosomesEmpty()) {
            if (index >= 0 && index < this.population.getChromosomesSize()) {
                this.population.drawOnForChromosome(index, g2, geneWidth, 0);
            } else {
                // Handle the case when the index is out of bounds by displaying the first chromosome
                this.population.drawOnForChromosome(BEST_CHROMOSOME_INDEX, g2, geneWidth, 0);
            }
        }
    }

    /**
     * ensures: returns the gene width
     * @return gene width
     */
    public int findGeneWidth() {
		int compHeight = this.getHeight();
		int compWidth = this.getWidth();

        int numColumn = this.population.getNumPerColumnForChromosome(0);
        if (numColumn < 10){
            return (compWidth - DEFAULT_BORDER * MULTIPLIER) / (NUM_PER_ROW);
        } else{
            return (compHeight) / numColumn;
        }
	}

    /**
     * a wrapper method wrapper for Population.getPrevHammingDistance()
     */
    public double getPopulationPrevHammingDistance() {
        return this.population.getPrevHammingDistance();
    }
} //End IndividualComponent
