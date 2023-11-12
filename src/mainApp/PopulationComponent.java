package mainApp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/*
 * Class: PopulationComponent
 * @author F23_R402
 * 
 * Purpose: The class is used to visualize the population on its assigned frame
 */
public class PopulationComponent extends DataComponent{
    // constants
    public static final double FRAME_HEIGHT_RATIO = 1.33;
    public static final double SCALING_MULTIPLER = 1.1;
    
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
                currChromosome.setX((int) (SCALING_MULTIPLER * x + geneWidth));
                currChromosome.setY((int) (SCALING_MULTIPLER * y + geneWidth));
                currChromosome.drawPopulationView(g2, geneWidth, 0);
                if (y > maxHeight) {
                    maxHeight = y;
                }
            }
        }
        this.setPreferredSize(new Dimension(this.getWidth(), (int)(maxHeight * FRAME_HEIGHT_RATIO)));   
    }
}