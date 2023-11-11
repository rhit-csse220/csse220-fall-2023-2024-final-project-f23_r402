package mainApp;


public class Histogram {
    private Population population;
    private int[] fitnessFrequency;

    /**
     * ensures: returns the population
     * @return population
     */
    public Population getPopulation() {
        return population;
    }

    /**
     * ensures: sets population to a new value
     * @param population new population
     */
    public void setPopulation(Population population) {
        this.population = population;
    }

    /**
     * ensures: gets fitnessFrequency at index i
     * @param i index
     * @return fitnessFrequency[i]
     */
    public int getFitnessFrequency(int i){
        return fitnessFrequency[i];
    }

    /**
     * ensures: updates fitness frequency
     */
    public void updateFitnessFrequency(){
        fitnessFrequency = new int[101];
        for (int i = 0; i < population.getChromosomesSize(); i++){
            Chromosome currChromosome = population.getChromosomeByIndex(i);  //getChromosomes().get(i);
            int fitnessCurrChromosome = (int)(currChromosome.getFitnessScore());
            //fitnessFrequency.set(fitnessCurrChromosome, fitnessFrequency.get(fitnessCurrChromosome)+1); 
            fitnessFrequency[fitnessCurrChromosome]++;
        }
    }

    /**
     * ensures: returns the sum of all fitness frequencies
     * @return
     */
    public int getAllElements(){
        int sum = 0;
        for (int i = 0; i < fitnessFrequency.length; i++){
            sum+=fitnessFrequency[i];
        }
        return sum;
    }

    /**
     * ensures: returns the size of population
     * @return size of population
     */
    public int getSizeOfPopulation() {
        return this.population.getSizeOfPopulation();
    }
}
