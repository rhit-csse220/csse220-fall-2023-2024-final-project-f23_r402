package mainApp;

/**
 * Class: Evolution
 * @author F23_R402
 * 
 * Purpose: provides the actions required for evolving the assigned population per generation
 */
public class Evolution {
    // fields
    private Population population;
    private int populationSize;
    private int generations = 100;
    private double elitism;
    private int genomeLength;
    private double mutationRate;
    private String selection;
    private boolean crossover;
    
    /*
     * ensures: Instantiates a new Evolution object with no parameters set
     */
    public Evolution(){} //Evolution
    
    /**
     * ensures: Instantiates a new Evolution object with all parameters set
     * @param population The population
     * @param populationSize The size of the population
     * @param generations The number of generations
     * @param elitism The elitism rate
     * @param genomeLength The genome length for an individual chromosome
     * @param mutationRate The mutation rate
     * @param selection The type of selection function to be used
     * @param crossover To decide if crossover is enabled or not
     */
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
    } //Evolution

    /**
     * ensures: returns the population
     * @return
     */
    public Population getPopulation() {
        return population;
    } //getPopulation

    /**
     * ensures: sets popoulation to a new value
     * @param population
     */
    public void setPopulation(Population population) {
        this.population = population;
    } //setPopulation

    /**
     * returns the population size
     * @return
     */
    public int getPopulationSize() {
        return populationSize;
    } //getPopulationSize

    /**
     * ensures: sets the population size to a new values
     * @param populationSize
     */
    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    } //setPopulationSize

    /**
     * ensures: returns the nunmber of generations
     * @return
     */
    public int getGenerations() {
        return generations;
    } //getGenerations

    /**
     * ensures: sets the number of generations to a new number
     * @param generations
     */
    public void setGenerations(int generations) {
        this.generations = generations;
    } //setGenerations

    /**
     * ensures: returns elitism
     * @return
     */
    public double getElitism() {
        return elitism;
    } //getElitism

    /**
     * ensures: sets elitism to a new number
     * @param elitism
     */
    public void setElitism(double elitism) {
        this.elitism = elitism;
    } //setElitism

    /**
     * ensures: returns the genome lenght
     * @return
     */
    public int getGenomeLength() {
        return genomeLength;
    } //getGenomeLength

    /**
     * ensures: sets the genome lenght to a new value
     * @param genomeLength
     */
    public void setGenomeLength(int genomeLength) {
        this.genomeLength = genomeLength;
    } //setGenomeLength

    /**
     * ensures: returns the mutation rate
     * @return
     */
    public double getMutationRate() {
        return mutationRate;
    } //getMutationRate

    /**
     * ensures: sets the mutation rate to a new value
     * @param mutationRate
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    } //setMutationRate

    /**
     * ensures: returns the selection string
     * @return
     */
    public String getSelection() {
        return selection;
    } //getSelection

    /**
     * ensures: sets the selection string to a new value
     * @param selection
     */
    public void setSelection(String selection) {
        this.selection = selection;
    } //setSelection

    /**
     * ensures: returns crossover
     * @return
     */
    public boolean isCrossover() {
        return crossover;
    } //isCrossover

    /**
     * ensures: sets crossover to a new value
     * @param crossover
     */
    public void setCrossover(boolean crossover) {
        this.crossover = crossover;
    } //setCrossover
    
    /**
     * ensures: returns the size of the lineArray size
     * @return
     */
    public int getLineArraySize(){
        return this.population.getLineArraySize();
    } //getLineArraySize
    
    /**
     * ensures: gets fitness for lineArray element at index i
     * @param i index
     * @param s LineArray type
     * @return
     */
    public double getLineArrayIndex(int i, String s){
        if (s.equals("Best")){
            return this.population.getBestFitnessForLineArrayElement(i);
        } else if (s.equals("Avg")){
            return this.population.getAvgFitnessForLineArrayElement(i);
        }
        else if (s.equals("Low")){
            return this.population.getLowFitnessForLineArrayElement(i);
        }
        else if (s.equals("Ham")){
            return this.population.getHammingDistancForLineArrayElement(i);
        }
        else if (s.equals("0")){
            return this.population.getNumberOf0sForLineArrayElement(i);
        }
        else if (s.equals("1")){
            return this.population.getNumberOf1sForLineArrayElement(i);
        }
        else if (s.equals("?")){
            return this.population.getNumberOfQsForLineArrayElement(i);
        }
        else{
            return -1;
        }
    } //getLineArrayIndex

    /**
     * ensures: check if any of the chromosomes within the population has a 100 fitness score
     * @return
     */
    public boolean checkForFitness100() {
        for (Chromosome chromosome : population.getChromosomes()) {
            if (chromosome.getFitnessScore() == 100) {
                return true;
            }
        }
        return false;
    } //checkForFitness100
    
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
    } //handleSelection
    
    /**
    * handles truncation selection of the population
    */
    public void handleTruncationSelection(){
        this.population.performSelection(this.mutationRate, 0, this.elitism, this.crossover);
    } //handleTruncationSelection
    
    /**
    * handles roulette selection of the population
    */
    public void handleRouletteSelection(){
        this.population.performSelection(this.mutationRate, 1, this.elitism, this.crossover);
    } //handleRouletteSelection
    
    /**
    * handles ranked selection of the population
    */
    public void handleRankedSelection(){
        this.population.performSelection(this.mutationRate, 2, this.elitism, this.crossover);
    } //handleRankedSelection
    
    /**
    * checks whether the population is the research or not
    * @return population.isResearch()
    */
    public boolean isResearchPopulation() {
        return this.population.isResearch();
    } //isResearchPopulation
    
    /**
    * performs selection on the research population
    */
    public void performSelectionResearch() {
        this.population.performSelectionResearch();
    } //performSelectionResearch
    
    /**
     * checks if the chromosome in the population at index i is a research chromosome
     * @param i index
     * @return if the chromosome in the population at index i is a research chromosome
     */
    public boolean isResearchChromosome(int i) {
        return this.population.isResearchChromosome(i);
    } //isResearchChromosome
} //End Evolution