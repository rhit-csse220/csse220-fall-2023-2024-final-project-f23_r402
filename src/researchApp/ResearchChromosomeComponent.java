package researchApp;

import java.awt.geom.Rectangle2D;

import mainApp.ChromosomeComponent;
import mainApp.Gene;

public class ResearchChromosomeComponent extends ChromosomeComponent{
    public ResearchChromosomeComponent(){
        this.chromosome = new ResearchChromosome();
        this.chromosome.initiateGene();
    }

    @Override
    public ResearchGene containsGene(int x, int y) {
        Rectangle2D.Double box = new Rectangle2D.Double(x-X_MOUSE_COORD_OFFSET, y-Y_MOUSE_COORD_OFFSET, 1, 1); // Mouse click coordinates were at an offset
		return ((ResearchChromosome)chromosome).handleGetSelectedGene(box);
    }
}
