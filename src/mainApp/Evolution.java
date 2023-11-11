package mainApp;

public class Evolution {
    private Population population;
    private int populationSize;
    private int generations = 100;
    private double elitism;
    private int genomeLength;
    private double mutationRate;
    private String selection;
    private boolean crossover;
    
    public Evolution(){}
    
    public Evolution(Population population, int populationSize, int generations, double elitism,
    int genomeLength, double mutationRate, String selection, boolean crossover) {
        this.population = population;
        this.populationSize = populationSize;
        this.generations = generations;
        this.elitism = elitism;
        this.genomeLength = genomeLength;
        this.mutationRate = mutationRate;
        this.selection = selection;
        this.crossover = crossover;
    }
    public Population getPopulation() {
        return population;
    }
    public void setPopulation(Population population) {
        this.population = population;
    }
    public int getPopulationSize() {
        return populationSize;
    }
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }
    public int getGenerations() {
        return generations;
    }
    public void setGenerations(int generations) {
        this.generations = generations;
    }
    public double getElitism() {
        return elitism;
    }
    public void setElitism(double elitism) {
        this.elitism = elitism;
    }
    public int getGenomeLength() {
        return genomeLength;
    }
    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    }
    public double getMutationRate() {
        return mutationRate;
    }
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
    public String getSelection() {
        return selection;
    }
    public void setSelection(String selection) {
        this.selection = selection;
    }
    public boolean isCrossover() {
        return crossover;
    }
    public void setCrossover(boolean crossover) {
        this.crossover = crossover;
    }
    
    public int getLineArraySize(){
        return this.population.getLineArraySize(); //lineArray.size();
    }
    
    public double getLineArrayIndex(int i, String s){
        if (s.equals("Best")){
            return this.population.getBestFitnessForLineArrayElement(i);  //lineArray.get(i).getBestFitness();
        }
        else if (s.equals("Avg")){
            return this.population.getAvgFitnessForLineArrayElement(i);  //lineArray.get(i).getAvgFitness();
        }
        else if (s.equals("Low")){
            return this.population.getLowFitnessForLineArrayElement(i);  //lineArray.get(i).getLowFitness();
        }
        else if (s.equals("Ham")){
            return this.population.getHammingDistancForLineArrayElement(i);   //lineArray.get(i).getHammingDistance();
        }
        else if (s.equals("0")){
            return this.population.getNumberOf0sForLineArrayElement(i); //lineArray.get(i).getHammingDistance();
        }
        else if (s.equals("1")){
            return this.population.getNumberOf1sForLineArrayElement(i);   //lineArray.get(i).getHammingDistance();
        }
        else if (s.equals("?")){
            return this.population.getNumberOfQsForLineArrayElement(i);   //lineArray.get(i).getHammingDistance();
        }
        else{
            return -1;
        }
    }
    
    public boolean checkForFitness100() {
        for (Chromosome chromosome : population.getChromosomes()) {
            if (chromosome.getFitnessScore() == 100) {
                return true;
            }
        }
        return false;
    }
    
    /**
    * Handles the selection of individuals in the population based on the specified
    * selection method.
    */
    public void handleSelection(){
        String s = this.selection;
        if (s.equals("Truncation")){
            this.handleTruncationSelection();
        }
        else if (s.equals("Roulette")){
            this.handleRouletteSelection();
        }
        else if (s.equals("Rank")){
            this.handleRankedSelection();
        }
    }
    
    /**
    * handles truncation selection of the population
    */
    public void handleTruncationSelection(){
        this.population.performSelection(this.mutationRate, 0, this.elitism, this.crossover);
    }
    
    /**
    * handles roulette selection of the population
    */
    public void handleRouletteSelection(){
        this.population.performSelection(this.mutationRate, 1, this.elitism, this.crossover);
    }
    
    /**
    * handles ranked selection of the population
    */
    public void handleRankedSelection(){
        this.population.performSelection(this.mutationRate, 2, this.elitism, this.crossover);
    }
    
    /**
    * checks whether the population is the research or not
    * @return population.isResearch()
    */
    public boolean isResearchPopulation() {
        return this.population.isResearch();
    }
    
    /**
    * performs selection on the research population
    */
    public void performSelectionResearch() {
        this.population.performSelectionResearch();
    }
    
    public boolean isResearchChromosome(int i) {
        return this.population.isResearchChromosome(i);
    }
}
