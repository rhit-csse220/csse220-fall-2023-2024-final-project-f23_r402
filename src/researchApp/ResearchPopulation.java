package researchApp;

import java.util.ArrayList;
import java.util.Random;

import mainApp.Chromosome;
import mainApp.InvalidChromosomeFormatException;
import mainApp.Population;

public class ResearchPopulation extends Population{
    
    private ArrayList<ResearchChromosome> chromosomes;
    private ArrayList<ResearchBestFitLine2D> lineArray;

    Random r = new Random();
    
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

    public void performSelection(){
        // sort population
        this.sortPopulation();

        ArrayList<ResearchChromosome> currentChromosomes = new ArrayList<ResearchChromosome>(chromosomes);
        ArrayList<ResearchChromosome> chosenChromosomes = findCurrent(currentChromosomes, new ArrayList<ResearchChromosome>());
        
        int initialSize = this.chromosomes.size();
        this.chromosomes = new ArrayList<ResearchChromosome>();
        // perform crossover
        chosenChromosomes = this.performResearchCrossover(chosenChromosomes);

        // initiating new chromosomes
        for (int i = 0; i < initialSize/2; i++){
            String currChromosomeData = chosenChromosomes.get(i).getChromosomeDataAsString();
            this.chromosomes.add(new ResearchChromosome(currChromosomeData));
            this.chromosomes.add(new ResearchChromosome(currChromosomeData));
        }

        // sort population
        this.sortPopulation();
    }

    public ArrayList<ResearchChromosome> findCurrent(ArrayList<ResearchChromosome> currentChromosomes, ArrayList<ResearchChromosome> chosenChromosomes){
        if (currentChromosomes.size() == chosenChromosomes.size()){
            return chosenChromosomes;
        }

        ArrayList<Double> chromosomeScores = new ArrayList<Double>();

        // find total Score
        double totalScore = 0;
        for (ResearchChromosome chromosome : currentChromosomes){
            totalScore += 1 + (chromosome.getDaysRemaining()*19.0)/1000;
        }

        // find pctg range for each chromosome based of their score
        double currNum = 0;
        for (ResearchChromosome chromosome : currentChromosomes){
            currNum += chromosome.getFitnessScore()/totalScore;
            chromosomeScores.add(currNum);
        }

        // chose random chromosome
        double randNum = r.nextDouble(0,1);
        for (int i = 0; i < chromosomeScores.size(); i++){
            if (chromosomeScores.get(i) >= randNum){
                chosenChromosomes.add(currentChromosomes.get(i));
                break;
            }
        }
        currentChromosomes.removeAll(chosenChromosomes);
        return findCurrent(currentChromosomes, chosenChromosomes);
    }

    public ArrayList<ResearchChromosome> performResearchCrossover(ArrayList<ResearchChromosome> selectedParents){
        ArrayList<ResearchChromosome> childChromosomes = new ArrayList<ResearchChromosome>();
        for (int i = 0; i < selectedParents.size(); i++){
            // ensuring two children generated for each pair of parent
            int index = i;
            if (index % 2 == 1){
                index--;
            }

            // generate a random index for crossover, excluding first and last index
            int crossoverPoint = r.nextInt(CROSSOVER_OFFSET, this.genomeLength);

            // finds childData
            String parent1Data = selectedParents.get(index).getChromosomeDataAsString().substring(0, crossoverPoint);
            String parent2Data = selectedParents.get(index+1).getChromosomeDataAsString().substring(crossoverPoint, this.genomeLength);
            String childData = parent1Data + parent2Data;

            // adds each child chromosome to the list
            try{
                ResearchChromosome childChromosome = new ResearchChromosome(childData);
                childChromosomes.add(childChromosome);
            } catch (Exception e){}
        }
        
        return childChromosomes; // only returns half of population - rest has to be mutated
    }

}
