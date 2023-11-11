package mainApp;

import java.util.ArrayList;

public class Histogram {
    private Population population;
    private int[] fitnessFrequency;

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public int getFitnessFrequency(int i){
        return fitnessFrequency[i];
    }

    public void updateFitnessFrequency(){
        fitnessFrequency = new int[101];
        for (int i = 0; i < population.getChromosomesSize(); i++){
            Chromosome currChromosome = population.getChromosomeByIndex(i);  //getChromosomes().get(i);
            int fitnessCurrChromosome = (int)(currChromosome.getFitnessScore());
            //fitnessFrequency.set(fitnessCurrChromosome, fitnessFrequency.get(fitnessCurrChromosome)+1); 
            fitnessFrequency[fitnessCurrChromosome]++;
        }
    }

    public int getAllElements(){
        int sum = 0;
        for (int i = 0; i < fitnessFrequency.length; i++){
            sum+=fitnessFrequency[i];
        }
        return sum;
    }

    public int getSizeOfPopulation() {
        return this.population.getSizeOfPopulation();
    }
}
