package researchApp;

import java.util.ArrayList;

import mainApp.Population;

public class ResearchPopulation extends Population{
    
    private ArrayList<ResearchChromosome> chromosomes;
    private ArrayList<ResearchBestFitLine2D> lineArray;
    
    public ResearchPopulation(int sizeOfPopulation, int genomeLength){
        super(sizeOfPopulation, genomeLength);
    }

    public ResearchPopulation(int sizeOfPopulation, int genomeLength, String fitnessFunction){
        super(sizeOfPopulation, genomeLength, fitnessFunction);
    }

    public ResearchPopulation(ResearchPopulation researchPopulation){
        for (int i = 0; i < researchPopulation.sizeOfPopulation; i++){
            
        }
    }

    @Override
    public void initiatePopulation() {
        this.chromosomes = new ArrayList<ResearchChromosome>();
        this.lineArray = new ArrayList<ResearchBestFitLine2D>();
    }
}
