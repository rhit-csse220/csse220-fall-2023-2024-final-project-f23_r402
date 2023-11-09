package researchApp;

import mainApp.*;

public class Research extends Evolution{

    private ResearchPopulation population;

    public Research(){}

    public Research(ResearchPopulation population, int populationSize, int generations, double elitism,
    int genomeLength, double mutationRate, String selection, boolean crossover){
        this.population = population;
        this.populationSize = populationSize;
        this.generations = generations;
        this.elitism = elitism;
        this.genomeLength = genomeLength;
        this.mutationRate = mutationRate;
        this.selection = selection;
        this.crossover = crossover;
    }
}
