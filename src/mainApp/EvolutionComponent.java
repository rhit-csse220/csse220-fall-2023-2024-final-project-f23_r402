package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class EvolutionComponent extends JComponent {
    public Population population;
    private int populationSize;
    private int generations;
    private int elitism;
    private int genomeLength;
    private double mutationRate;
    private String selection;
    private Boolean crossover;

    public EvolutionComponent() {
		this.population = new Population();
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

    public int getElitism() {
      return elitism;
    }

    public void setElitism(int elitism) {
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

    public Boolean getCrossover() {
      return crossover;
    }

    public void setCrossover(Boolean crossover) {
      this.crossover = crossover;
    }

    public void setAll(String populationSize, String selection, String mutationRate, boolean crossover, String generations, String genomeLength, String elitism){
      this.setPopulationSize(Integer.parseInt(populationSize));
      this.setSelection(selection);
      this.setMutationRate(Integer.parseInt(mutationRate));
      this.setCrossover(crossover);
      this.setGenerations(Integer.parseInt(generations));
      this.setGenomeLength(Integer.parseInt(genomeLength));
      this.setElitism(Integer.parseInt(elitism));
    }

    public void handleSelection(){
      String s = selection;
      if (s.equals("Truncation")){
        this.population.truncationSelection(mutationRate);
      }
      else if (s.equals("Roulette")){
        //TODO Add roulette selection
      }
      else if (s.equals("Rank")){
        //TODO Add rank selection
      }
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      this.drawOn(g2);
    }

    public void drawOn(Graphics2D g2){
    }
}
