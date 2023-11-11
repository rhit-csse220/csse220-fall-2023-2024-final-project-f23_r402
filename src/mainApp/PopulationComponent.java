package mainApp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class PopulationComponent extends DataComponent{
    // constants
    public static final double FRAME_HEIGHT_RATIO = 1.33;
    
    // fields
    private ArrayList<Chromosome> chromosomes;
    private int maxHeight = 0;

    public PopulationComponent(){}
    
    /**
     * ensures: the population is painted
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        chromosomes = new ArrayList<Chromosome>(this.population.getChromosomes());
        int geneWidth = (int)(this.getWidth() / (Chromosome.NUM_PER_ROW * Chromosome.NUM_PER_ROW)) ;
        for (int i = 0; i < chromosomes.size() / Chromosome.NUM_PER_ROW; i++){
            int x = 0;
            int y = 0;
            for (int j = 0; j < Chromosome.NUM_PER_ROW; j++){
                Chromosome currChromosome = this.chromosomes.get(Chromosome.NUM_PER_ROW * i + j);
                x = geneWidth * Chromosome.NUM_PER_ROW * j;
                y = geneWidth * chromosomes.get(i).getNumPerColumn() * i;
                currChromosome.setX((int) (1.1 * x + geneWidth));
                currChromosome.setY((int) (1.1 * y + geneWidth));
                currChromosome.drawPopulationView(g2, geneWidth, 0);
                if (y > maxHeight) {
                    maxHeight = y;
                }
            }
        }
        this.setPreferredSize(new Dimension(this.getWidth(), (int)(maxHeight * FRAME_HEIGHT_RATIO)));   
    }
}