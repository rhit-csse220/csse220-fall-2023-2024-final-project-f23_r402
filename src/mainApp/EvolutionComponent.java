package mainApp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 * The EvolutionComponent class represents a component for visualizing the evolution process
 * of a population and drawing various data related to it.
 */
public class EvolutionComponent extends JComponent {
    public static final double X1_TO_FRAME_RATIO = 0.04;
    public static final double Y1_TO_FRAME_RATIO = 0.08;
    public static final double X2_TO_FRAME_RATIO = 0.96;
    public static final double Y2_TO_FRAME_RATIO = 0.82;

    public Population population;
    private int populationSize;
    private int generations = 100;
    private int elitism;
    private int genomeLength;
    private double mutationRate;
    private String selection;
    private Boolean crossover;
    private int x,y,xLimit,yLimit,xWidth,yHeight;
    public int generationCount;
    public ArrayList<BestFitLine2D> lineArray = new ArrayList<BestFitLine2D>();

     /**
     * Constructs a new EvolutionComponent with an initial population.
     */
    public EvolutionComponent() {
		  this.population = new Population();
    }
     /**
     * Gets the size of the population.
     *
     * @return The size of the population.
     */

    public int getPopulationSize() {
      return populationSize;
    }

       /**
     * Sets the size of the population.
     *
     * @param populationSize The new size of the population.
     */

    public void setPopulationSize(int populationSize) {
      this.populationSize = populationSize;
    }

      /**
     * Gets the number of generations for the evolution process.
     *
     * @return The number of generations.
     */

    public int getGenerations() {
      return generations;
    }

      /**
     * Sets the number of generations for the evolution process.
     *
     * @param generations The new number of generations.
     */

    public void setGenerations(int generations) {
      this.generations = generations;
    }
  /**
     * Gets the elitism value, which is the percentage of top individuals preserved in each generation.
     *
     * @return The elitism value.
     */
    public int getElitism() {
      return elitism;
    }

      /**
     * Sets the elitism value, which is the percentage of top individuals preserved in each generation.
     *
     * @param elitism The new elitism value.
     */

    public void setElitism(int elitism) {
      this.elitism = elitism;
    }


      /**
     * Gets the length of the genome for each individual in the population.
     *
     * @return The genome length.
     */

    public int getGenomeLength() {
      return genomeLength;
    }

        /**
     * Sets the length of the genome for each individual in the population.
     *
     * @param genomeLength The new genome length.
     */

    public void setGenomeLength(int genomeLength) {
      this.genomeLength = genomeLength;
    }

    /**
 * Gets the mutation rate.
 *
 * @return The mutation rate.
 */

    public double getMutationRate() {
      return mutationRate;
    }

    /**
 * Sets the mutation rate.
 *
 * @param mutationRate The new mutation rate.
 */

    public void setMutationRate(double mutationRate) {
      this.mutationRate = mutationRate;
    }


    
/**
 * Gets the selection method.
 *
 * @return The selection method.
 */
    public String getSelection() {
      return selection;
    }

    /**
 * Sets the selection method.
 *
 * @param selection The new selection method.
 */

    public void setSelection(String selection) {
      this.selection = selection;
    }

    /**
 * Gets whether crossover is enabled.
 *
 * @return `true` if crossover is enabled, `false` otherwise.
 */

    public Boolean getCrossover() {
      return crossover;
    }

    /**
 * Sets whether crossover is enabled.
 *
 * @param crossover `true` to enable crossover, `false` to disable it.
 */

    public void setCrossover(Boolean crossover) {
      this.crossover = crossover;
    }

    /**
 * Sets various parameters for the evolution process, including population size,
 * selection method, mutation rate, crossover, number of generations, genome length,
 * and elitism.
 *
 * @param populationSize The new population size.
 * @param selection The new selection method.
 * @param mutationRate The new mutation rate.
 * @param crossover `true` to enable crossover, `false` to disable it.
 * @param generations The new number of generations.
 * @param genomeLength The new genome length.
 * @param elitism The new elitism percentage.
 */

    public void setAll(String populationSize, String selection, String mutationRate, boolean crossover, String generations, String genomeLength, String elitism){
      this.setPopulationSize(Integer.parseInt(populationSize));
      this.setSelection(selection);
      this.setMutationRate(Integer.parseInt(mutationRate));
      this.setCrossover(crossover);
      this.setGenerations(Integer.parseInt(generations));
      this.setGenomeLength(Integer.parseInt(genomeLength));
      this.setElitism(Integer.parseInt(elitism));
      this.lineArray.removeAll(lineArray);
      this.population = new Population(this.populationSize, this.genomeLength);
    }

    /**
 * Handles the selection of individuals in the population based on the specified
 * selection method.
 */

    public void handleSelection(){
      String s = selection;
      if (s.equals("Truncation")){
        this.population.truncationSelection(mutationRate);
      }
      else if (s.equals("Roulette")){
        this.population.rouletteRankedSelection(mutationRate, true);
      }
      else if (s.equals("Rank")){
        this.population.rouletteRankedSelection(mutationRate, false);

      }
    }


    /**
 * Paints the component and draws various elements, including axes, divisions, lines, and legend.
 *
 * @param g The Graphics object used for painting.
 */

    @Override
    protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    // TODO remove magic numbers
      x = (int) (X1_TO_FRAME_RATIO * this.getWidth());
      y = (int) (Y1_TO_FRAME_RATIO * this.getHeight());
      xLimit = (int) (this.getWidth() * X2_TO_FRAME_RATIO);
      yLimit = (int) (this.getHeight() * Y2_TO_FRAME_RATIO);  
      this.drawOn(g2);
      this.drawLines(g2);
      this.drawLegend(g2);
      g2.drawString("Fitness over Generations", -x + (this.getWidth()/2), 10);
    }

    /**
 * Draws various elements on the specified Graphics2D object, including axes and a legend.
 *
 * @param g2 The Graphics2D object for drawing.
 */
    public void drawOn(Graphics2D g2){
      drawAxes(g2);
      drawLegend(g2);
    }

    /**
 * Draws the axes on the specified Graphics2D object, including divisions.
 *
 * @param g2 The Graphics2D object for drawing axes.
 */

    public void drawAxes(Graphics2D g2){
      g2.drawRect(x, y, xLimit, yLimit);
      drawXDivisions(g2);
      drawYDivisions(g2);
    }

    /**
 * Draws the divisions on the X-axis to indicate values.
 *
 * @param g2 The Graphics2D object for drawing X-axis divisions.
 */
    public void drawXDivisions(Graphics2D g2){
      // TODO FIGURE OUT WHY IT DOESNT UPDATE WHEN NUM OF GENERATIONS CHANGES
      xWidth = xLimit - x;
      yHeight = yLimit + y;
      g2.translate(x, yHeight);
      int num = 0;
      for (int i = 0; i <= xWidth; i+= xWidth/10){
        String sNum = Integer.toString(num);
        g2.drawLine(i, -5, i, 5);
        g2.drawString(sNum, i, 20);
        num += generations/10;
        if ((i+(xWidth/10)) >= xWidth){
          xWidth = i;
        }
      }
      g2.translate(-x, -yHeight);
    }

    /**
 * Draws the divisions on the Y-axis to indicate values.
 *
 * @param g2 The Graphics2D object for drawing Y-axis divisions.
 */
    public void drawYDivisions(Graphics2D g2){
      yHeight = yLimit - y;
      g2.translate(x, y);
      int num = 100;
      for (int i = 0; i <= yHeight; i+= yHeight/10){
        String sNum = Integer.toString(num);
        g2.drawLine(5, i, -5, i); // TODO magic num
        g2.drawString(sNum, -25, i+5);
        num -= 10;
        if ((i + yHeight/10) >= yHeight){
          yHeight = i;
        }
        //System.out.println(i+","+yHeight);
      }
      g2.translate(-x, -y);
    }

    
/**
 * Stores and draws lines on the specified Graphics2D object.
 *
 * @param g2 The Graphics2D object for storing and drawing lines.
 */

    public void storeLines(Graphics2D g2){
      drawLines(g2);
      // if (generationCount>=generations){
      //   g2.setColor(g2.getBackground());
      //   g2.drawPolyline(xPoints, yPoints, xPoints.length);
      // }
    }
    // public void drawBestLine(Graphics2D g2){
    //   g2.translate(x, y);
    //   if (generationCount!=-1 && this.population.prevC!=null){
    //     int pX = generationCount*((xWidth)/generations);
    //     int nX = (generationCount+1)*((xWidth)/generations);
    //     int pY = yHeight-(int)(this.population.prevC.getFitnessScore()*((yHeight)/100));
    //     int nY = yHeight-(int)(this.population.nextC.getFitnessScore()*((yHeight)/100));
    //     xPoints[generationCount]=pX;
    //     xPoints[generationCount+1]=nX;
    //     yPoints[generationCount]=pY;
    //     yPoints[generationCount+1]=nY;
    //     g2.drawPolyline(xPoints, yPoints, this.generations);
    //   }
    //   g2.translate(-x,-y);
    // }

    /**
 * Calculates the Y-coordinate based on a percentage value.
 *
 * @param y The percentage value for Y-coordinate calculation.
 * @return The calculated Y-coordinate.
 */
    public int calculateY(double y){
      return (int) (yHeight - (y * (yHeight / 100.0)));
    }

    /**
 * Calculates the X-coordinate based on the current generation.
 *
 * @param x The generation number for X-coordinate calculation.
 * @return The calculated X-coordinate.
 */
    public int calculateX(double x){
      return (int) (x * ((double) xWidth / generations));
    }

/**
 * Draws fitness lines on the specified Graphics2D object, including best fitness, average fitness, and lowest fitness.
 *
 * @param g2 The Graphics2D object for drawing fitness lines.
 */
    public void drawLines(Graphics2D g2){
      g2.translate(x, y);
      // System.out.println(this.population.lineArray.size());
        for (int i = 1; i < generations; i++){
          if (i < this.population.lineArray.size()){
          //Line of best fit
          int pX = calculateX(i-1);
          int nX = calculateX(i);
          int pY = calculateY(this.population.lineArray.get(i-1).getBestFitness());
          int nY = calculateY(this.population.lineArray.get(i).getBestFitness());
          g2.setColor(Color.green);
          g2.setStroke(new BasicStroke(5));
          g2.drawLine(pX, pY, nX, nY);

          //Line of avg
          pY = calculateY(this.population.lineArray.get(i-1).getAvgFitness());
          nY = calculateY(this.population.lineArray.get(i).getAvgFitness());
          g2.setColor(Color.orange);
          g2.drawLine(pX, pY, nX, nY);


          //Line of lowest
          //TODO FIGURE OUT LOGIC FOR WHY THIS IS SO JAGGED; Assumably, u can use the array in such a way that the newest chromosome is preserved, and then the previous index where the last chromosome was preserved can be used to find the initial x,y, with the current chromsome being the final x,y. this may require restructuring of linearray rn.
          pY = calculateY(this.population.lineArray.get(i-1).getLowFitness());
          nY = calculateY(this.population.lineArray.get(i).getLowFitness());
          g2.setColor(Color.red);
          g2.drawLine(pX, pY, nX, nY);
          
        }
      }
      g2.translate(-x,-y);
    }
    
    /**
 * Draws a legend on the specified Graphics2D object, including colored boxes and labels for fitness lines.
 *
 * @param g2 The Graphics2D object for drawing the legend.
 */

    public void drawLegend(Graphics2D g2){
      g2.setColor(Color.green);
      g2.fillRect(calculateX(0.95*generations), calculateY(55.0), 20, 20);

      g2.setColor(Color.orange);
      g2.fillRect(calculateX(0.95*generations), calculateY(40.0), 20, 20);
      
      g2.setColor(Color.red);
      g2.fillRect(calculateX(0.95*generations), calculateY(25.0), 20, 20);
      
      g2.setColor(Color.black);
      g2.drawString("Best fitness", calculateX(0.98*generations), calculateY(49));
      g2.drawString("Ave fitness", calculateX(0.98*generations), calculateY(34));
      g2.drawString("Low fitness", calculateX(0.98*generations), calculateY(19));
    }
}
