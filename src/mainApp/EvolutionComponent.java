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
  public static final int TITLE_Y_VALUE = 10;
  public static final double X1_TO_FRAME_RATIO = 0.04;
  public static final double Y1_TO_FRAME_RATIO = 0.08;
  public static final double X2_TO_FRAME_RATIO = 0.94;
  public static final double Y2_TO_FRAME_RATIO = 0.82;
  public static final int MAX_FITNESS_SCORE = 100;
  public static final int GENERATION_INTERVAL = 10;
  public static final int FITNESS_SCORE_INTERVAL = 10;
  public static final int AXIS_LABEL_LINE_WIDTH = 5;
  public static final int X_AXIS_LABEL_TO_LINE_VERTICAL_PADDING = 20;
  public static final int X_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING = -6;
  public static final int Y_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING = -25;
  public static final int Y_AXIS_LABEL_TO_LINE_VERTICAL_PADDING = 5;
  
  public static final int KEY_BOX_SIDE_LENGTH = 20;
  public static final double KEY_DISTANCE = 12.0;
  public static final double KEY_GREEN_Y = 30.0;
  public static final double KEY_ORANGE_Y = KEY_GREEN_Y - KEY_DISTANCE;
  public static final double KEY_RED_Y = KEY_ORANGE_Y - KEY_DISTANCE;
  public static final double KEY_YELLOW_Y  = KEY_RED_Y - KEY_DISTANCE;
  public static final double KEY_LABEL_OFFSET = -6;
  public static final double KEY_X_RATIO = 0.95;
  public static final double KEY_LABEL_X_RATIO = 0.975;
  
  public static final int DEFAULT_STROKE = 500;
  public static final int DEFAULT_GENERATION = 100;
  
  private int x,y,xLimit,yLimit,xWidth,yHeight;
  private int generationCount;
  private ArrayList<BestFitLine2D> lineArray = new ArrayList<BestFitLine2D>();
  private Evolution evolution;
  
  /**
  * Constructs a new EvolutionComponent with an initial population.
  */
  public EvolutionComponent() {
    this.evolution = new Evolution();
    this.evolution.setPopulation(new Population());
  }
  
  public Population handleGetPopulation(){
    return this.evolution.getPopulation();
  }

  public boolean checkForFitness100() {
    return this.evolution.checkForFitness100();
}

  /**
  * Sets various parameters for the evolution process, including population size,
  * selection method, mutation rate, crossover, number of generations, genome length,
  * and elitism.
  * @param populationSize The new population size.
  * @param selection The new selection method.
  * @param mutationRate The new mutation rate.
  * @param crossover `true` to enable crossover, `false` to disable it.
  * @param generations The new number of generations.
  * @param genomeLength The new genome length.
  * @param elitism The new elitism percentage.
   * @throws InvalidGenomeLengthException
  */
  public void setAll(String populationSize, String selection, String mutationRate, boolean crossover, String generations, String genomeLength, String elitism, String fitnessFunction) throws InvalidGenomeLengthException{
    System.out.println("=====setAll() is called=====");
    int populationSIZE = Integer.parseInt(populationSize);
    int mutationRATE = Integer.parseInt(mutationRate);
    int GENERATIONS = Integer.parseInt(generations);
    int genomeLENGTH = Integer.parseInt(genomeLength);
    double ELITISM = Double.parseDouble(elitism);
    this.evolution = new Evolution(new Population(populationSIZE, genomeLENGTH, fitnessFunction), populationSIZE, GENERATIONS, ELITISM, genomeLENGTH, mutationRATE, selection, crossover);
  }

  /**
  * Handles the selection of individuals in the population based on the specified
  * selection method.
  */
  public void handleSelection(){
    this.evolution.handleSelection();
  }
  
  /**
  * Paints the component and draws various elements, including axes, divisions, lines, and legend.
  * @param g The Graphics object used for painting.
  */
  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    this.x = (int) (X1_TO_FRAME_RATIO * this.getWidth());
    this.y = (int) (Y1_TO_FRAME_RATIO * this.getHeight());
    this.xLimit = (int) (this.getWidth() * X2_TO_FRAME_RATIO);
    this.yLimit = (int) (this.getHeight() * Y2_TO_FRAME_RATIO); 
    this.drawOn(g2);
    this.drawLines(g2);
    this.drawLegend(g2);
    g2.drawString("Fitness over Generations", -x + (this.getWidth()/2), TITLE_Y_VALUE);
  }
  
  /**
  * Draws various elements on the specified Graphics2D object, including axes and a legend.
  * @param g2 The Graphics2D object for drawing.
  */
  public void drawOn(Graphics2D g2){
    g2.drawRect(this.x, this.y, this.xLimit, this.yLimit);
    this.drawXDivisions(g2);
    this.drawYDivisions(g2);
    this.drawLegend(g2);
  }
  
  /**
  * Draws the divisions on the X-axis to indicate values.
  * @param g2 The Graphics2D object for drawing X-axis divisions.
  */
  public void drawXDivisions(Graphics2D g2){
    this.xWidth = this.xLimit - this.x;
    this.yHeight = this.yLimit + this.y;
    g2.translate(this.x, this.yHeight);
    
    int num = 0;
    for (int i = 0; i <= this.xWidth; i += this.xWidth / GENERATION_INTERVAL){
      String sNum = Integer.toString(num);
      g2.drawLine(i, -AXIS_LABEL_LINE_WIDTH, i, AXIS_LABEL_LINE_WIDTH);
      g2.drawString(sNum, i + X_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING, X_AXIS_LABEL_TO_LINE_VERTICAL_PADDING);
      num += this.evolution.getGenerations() / GENERATION_INTERVAL;
    }
    g2.translate(-this.x, -this.yHeight);
  }
  
  /**
  * Draws the divisions on the Y-axis to indicate values.
  * @param g2 The Graphics2D object for drawing Y-axis divisions.
  */
  public void drawYDivisions(Graphics2D g2){
    this.yHeight = this.yLimit - this.y;
    g2.translate(this.x, this.y);
    int num = MAX_FITNESS_SCORE;
    for (int i = 0; i <= this.yHeight; i += this.yHeight / GENERATION_INTERVAL){
      String sNum = Integer.toString(num);
      g2.drawLine(AXIS_LABEL_LINE_WIDTH, i, -AXIS_LABEL_LINE_WIDTH, i);
      g2.drawString(sNum, Y_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING, i+Y_AXIS_LABEL_TO_LINE_VERTICAL_PADDING);
      num -= FITNESS_SCORE_INTERVAL;
      if ((i + this.yHeight / FITNESS_SCORE_INTERVAL) >= this.yHeight){
        this.yHeight = i;
      }
      //System.out.println(i+","+yHeight);
    }
    g2.translate(-this.x, -this.y);
  }
  
  /**
  * Calculates the Y-coordinate based on a percentage value.
  * @param y The percentage value for Y-coordinate calculation.
  * @return The calculated Y-coordinate.
  */
  public int calculateY(double y){
    return (int) (this.yHeight - (y * (this.yHeight / (double) MAX_FITNESS_SCORE)));
  }
  
  /**
  * Calculates the X-coordinate based on the current generation.
  * @param x The generation number for X-coordinate calculation.
  * @return The calculated X-coordinate.
  */
  public int calculateX(double x){
    return (int) (x * ((double) this.xWidth / this.evolution.getGenerations()));
  }
  
  /**
  * Draws fitness lines on the specified Graphics2D object, including best fitness, average fitness, and lowest fitness.
  * @param g2 The Graphics2D object for drawing fitness lines.
  */
  public void drawLines(Graphics2D g2){
    int generations = this.evolution.getGenerations();
    g2.translate(this.x, this.y);
    // System.out.println(this.population.lineArray.size());
    if (generations <= 100){
      g2.setStroke(new BasicStroke(DEFAULT_STROKE / DEFAULT_GENERATION));
    } else {
      g2.setStroke(new BasicStroke(DEFAULT_STROKE / generations));
    }

    for (int i = 1; i < generations; i++){
      if (i < this.evolution.getLineArraySize()){
        //Line of best fit
        int pX = calculateX(i-1);
        int nX = calculateX(i);
        int pY = calculateY(this.evolution.getLineArrayIndex(i-1, "Best"));
        int nY = calculateY(this.evolution.getLineArrayIndex(i, "Best"));
        g2.setColor(Color.green);  
        g2.drawLine(pX, pY, nX, nY);
        
        //Line of avg
        pY = calculateY(this.evolution.getLineArrayIndex(i-1, "Avg"));
        nY = calculateY(this.evolution.getLineArrayIndex(i, "Avg"));
        g2.setColor(Color.orange);
        g2.drawLine(pX, pY, nX, nY);
        
        //Line of lowest
        pY = calculateY(this.evolution.getLineArrayIndex(i-1, "Low"));
        nY = calculateY(this.evolution.getLineArrayIndex(i, "Low"));
        g2.setColor(Color.red);
        g2.drawLine(pX, pY, nX, nY);

        //Line of hamming
        pY = calculateY(this.evolution.getLineArrayIndex(i-1, "Ham"));
        nY = calculateY(this.evolution.getLineArrayIndex(i, "Ham"));
        g2.setColor(Color.yellow);
        g2.drawLine(pX, pY, nX, nY);
      }
    }
    g2.translate(-this.x,-this.y);
  }
  
  /**
  * Draws a legend on the specified Graphics2D object, including colored boxes and labels for fitness lines.
  * @param g2 The Graphics2D object for drawing the legend.
  */
  
  public void drawLegend(Graphics2D g2){
    int generations = this.evolution.getGenerations();
    g2.setColor(Color.green);
    g2.fillRect(calculateX(KEY_X_RATIO * generations), calculateY(KEY_GREEN_Y), KEY_BOX_SIDE_LENGTH, KEY_BOX_SIDE_LENGTH);
    
    g2.setColor(Color.orange);
    g2.fillRect(calculateX(KEY_X_RATIO * generations), calculateY(KEY_ORANGE_Y), KEY_BOX_SIDE_LENGTH, KEY_BOX_SIDE_LENGTH);
    
    g2.setColor(Color.red);
    g2.fillRect(calculateX(KEY_X_RATIO * generations), calculateY(KEY_RED_Y), KEY_BOX_SIDE_LENGTH, KEY_BOX_SIDE_LENGTH);

    g2.setColor(Color.yellow);
    g2.fillRect(calculateX(KEY_X_RATIO * generations), calculateY(KEY_YELLOW_Y), KEY_BOX_SIDE_LENGTH, KEY_BOX_SIDE_LENGTH);
    
    g2.setColor(Color.black);
    g2.drawString("Best fitness", calculateX(KEY_LABEL_X_RATIO * generations), calculateY(KEY_GREEN_Y + KEY_LABEL_OFFSET));
    g2.drawString("Average fitness", calculateX(KEY_LABEL_X_RATIO * generations), calculateY(KEY_ORANGE_Y + KEY_LABEL_OFFSET));
    g2.drawString("Low fitness", calculateX(KEY_LABEL_X_RATIO * generations), calculateY(KEY_RED_Y + KEY_LABEL_OFFSET));
    g2.drawString("Hamming distance", calculateX(KEY_LABEL_X_RATIO * generations), calculateY(KEY_YELLOW_Y + KEY_LABEL_OFFSET));
  }

  /**
   * ensures: returns generationCount
   * @return generationCount
   */
  public int getGenerationCount() {
    return generationCount;
  }

  /**
   * ensures: sets generationCount
   */
  public void setGenerationCount(int generationCount) {
    this.generationCount = generationCount;
  }
}