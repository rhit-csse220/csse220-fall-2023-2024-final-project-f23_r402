package mainApp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class PopulationComponent extends Component{
    protected ArrayList<Chromosome> chromosomes;

    protected int maxHeight = 0;

    public PopulationComponent(){}
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        chromosomes = new ArrayList<Chromosome>(this.population.getChromosomes());
        int geneWidth = (int)(this.getWidth() / (Chromosome.NUM_PER_ROW * Chromosome.NUM_PER_ROW)) ;
        //maxHeight = 0; // Initialize maxHeight to 0
        for (int i = 0; i < chromosomes.size() / Chromosome.NUM_PER_ROW; i++){
            int x = 0;
            int y = 0;
            for (int j = 0; j < Chromosome.NUM_PER_ROW; j++){
            //this.population.getChromosomes().get(i*Chromosome.NUM_PER_ROW+j).drawPopulationView(g2, geneWidth, geneWidth, geneWidth*((j*Chromosome.NUM_PER_ROW)+1), geneWidth*((i*Chromosome.NUM_PER_ROW)+1));
            //g2.drawRect(j*((geneWidth*Chromosome.NUM_PER_ROW)), i*((geneWidth*Chromosome.NUM_PER_ROW)), geneWidth, geneWidth);
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
        this.setPreferredSize(new Dimension(this.getWidth(), (int)(maxHeight*1.3)));   
    }
}