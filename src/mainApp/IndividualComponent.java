package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;


public class IndividualComponent extends Component{

    public IndividualComponent(){}
    private int index;

    public Population getPopulation() {
        return this.population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int geneWidth = this.getWidth() / 11;
        if (population != null && !population.getChromosomes().isEmpty()) {
            if (index >= 0 && index < population.getChromosomes().size()) {
                population.getChromosomes().get(index).drawOn(g2, geneWidth, 0);
            } else {
                // Handle the case when the index is out of bounds by displaying the first chromosome
                population.getChromosomes().get(0).drawOn(g2, geneWidth, 0);
            }
        }
    }
    
}
