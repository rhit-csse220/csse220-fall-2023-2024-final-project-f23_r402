package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;


public class IndividualComponent extends Component{
    
    final static int DEFAULT_BORDER = 3;
    final static int NUM_PER_ROW = 10;
    final static int MULTIPLIER = 2;
    final static int BEST_CHROMOSOME_INDEX = 0;
    
    public IndividualComponent(){}
    private int index;

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int geneWidth = findGeneWidth();
        if (population != null && !population.getChromosomes().isEmpty()) {
            if (index >= 0 && index < population.getChromosomes().size()) {
                population.getChromosomes().get(index).drawOn(g2, geneWidth, 0);
            } else {
                // Handle the case when the index is out of bounds by displaying the first chromosome
                population.getChromosomes().get(BEST_CHROMOSOME_INDEX).drawOn(g2, geneWidth, 0);
            }
        }
    }

    public int findGeneWidth() {
		int compHeight = this.getHeight();
		int compWidth = this.getWidth();

        int numColumn = population.getChromosomes().get(0).getNumPerColumn();
        if (numColumn < 10){
            return (compWidth - DEFAULT_BORDER * MULTIPLIER) / (NUM_PER_ROW);
        } else{
            return (compHeight) / numColumn;
        }
	}
}
