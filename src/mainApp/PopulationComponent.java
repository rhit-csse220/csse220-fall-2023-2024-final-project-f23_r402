package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class PopulationComponent extends JComponent{
    private Population population;
    private ArrayList<Chromosome> chromosomes; 

    public PopulationComponent(){}

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        chromosomes = new ArrayList<Chromosome>(this.population.getChromosomes());
        int geneWidth = this.getWidth()/(this.population.getSizeOfPopulation()) ;
        for (int i = 0; i < chromosomes.size()/Chromosome.NUM_PER_ROW; i++){
            for (int j = 0; j < Chromosome.NUM_PER_ROW; j++){
            //this.population.getChromosomes().get(i*Chromosome.NUM_PER_ROW+j).drawPopulationView(g2, geneWidth, geneWidth, geneWidth*((j*Chromosome.NUM_PER_ROW)+1), geneWidth*((i*Chromosome.NUM_PER_ROW)+1));
            //g2.drawRect(j*((geneWidth*Chromosome.NUM_PER_ROW)), i*((geneWidth*Chromosome.NUM_PER_ROW)), geneWidth, geneWidth);
            Chromosome currChromosome = this.chromosomes.get(Chromosome.NUM_PER_ROW*i + j);
            int x = geneWidth*Chromosome.NUM_PER_ROW*j;
            int y = geneWidth*Chromosome.NUM_PER_ROW*i;
            currChromosome.x=(int)(1.1*x+geneWidth);
            currChromosome.y=(int)(1.1*y+geneWidth);
            currChromosome.drawPopulationView(g2, geneWidth, 0);
        }
    }
}

}
