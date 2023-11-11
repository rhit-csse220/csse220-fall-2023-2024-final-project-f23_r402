package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * Class: HistogramComponent
 * @author F23_R402
 * 
 * Purpose: The visualization of the histogram diagram on its assigned frame given the data
 */
public class HistogramComponent extends EvolutionComponent{
    //constants
    public static final int FITNESS_SCORE_X_AXIS_INTERVAL = 5;
    public static final int FITNESS_SCORE_INITIAL_LIMIT = 96;
    public static final int FITNESS_SCORE_2ND_LAST_LIMIT = 99;
    public static final int SCORE_STRING_X_OFFSET = 2;

    // fields
    private Histogram histogram;

    public HistogramComponent(){
        this.histogram = new Histogram();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.x = (int) (X1_TO_FRAME_RATIO * this.getWidth());
        this.y = (int) (Y1_TO_FRAME_RATIO * this.getHeight());
        this.xLimit = (int) (this.getWidth() * X2_TO_FRAME_RATIO);
        this.yLimit = (int) (this.getHeight() * Y2_TO_FRAME_RATIO);
        this.xWidth = this.xLimit - this.x;
        this.yHeight = this.yLimit+this.y; 
        this.histogram.updateFitnessFrequency();
        this.drawOn(g2);
    }

    @Override
    public void drawOn(Graphics2D g2){
        this.drawYDivisions(g2);
        this.drawXDivisions(g2);
        this.drawRectangles(g2);
    }

    /*
     * ensures: It takes the data of how many chromosomes are there per fitness score interval from 0 to 100, and then draws the rectangles based on those values
     */
    public void drawRectangles(Graphics2D g2){
        g2.translate(x, yHeight);
        for (int i = 0; i <= FITNESS_SCORE_INITIAL_LIMIT; i+=FITNESS_SCORE_X_AXIS_INTERVAL){
            int sum = 0;
            for (int j = i; j <i+FITNESS_SCORE_X_AXIS_INTERVAL; j++){
                sum += this.histogram.getFitnessFrequency(j);
                if (j==FITNESS_SCORE_2ND_LAST_LIMIT){
                    sum += this.histogram.getFitnessFrequency(MAX_FITNESS_SCORE);
                }
            }
            int xCoord = calculateX(i);
            int yCoord = -calculateY(sum);
            int width = calculateX(FITNESS_SCORE_X_AXIS_INTERVAL);
            int height = calculateY(sum);
            g2.drawRect(xCoord, yCoord, width, height);
            String sNum = Integer.toString(sum);
            if (sum != 0){
                g2.drawString(sNum, xCoord+calculateX(SCORE_STRING_X_OFFSET), yCoord);
            }
        }
        g2.translate(-x, -yHeight);
    }

    @Override
    public int calculateX(double x) {
        return (int) (x * ((double) this.xLimit / MAX_FITNESS_SCORE));
    }

    @Override
    public int calculateY(double y) {
        return (int) (((y) * ((this.yHeight) / (double) histogram.getSizeOfPopulation())));
    }

    @Override
    public void drawYDivisions(Graphics2D g2) {
        g2.translate(this.x, this.yHeight);
        g2.drawLine(0, 0, 0, -this.yHeight);
        int num = this.histogram.getSizeOfPopulation();
        int populationYInterval = (int) ((this.yHeight) / FITNESS_SCORE_INTERVAL);
        int populationInterval = num / FITNESS_SCORE_INTERVAL;
        for (int i = 0; num >= 0; i -= populationYInterval){
            String sNum = Integer.toString(Math.abs(num - this.histogram.getSizeOfPopulation()));
            g2.drawLine(AXIS_LABEL_LINE_WIDTH, i, -AXIS_LABEL_LINE_WIDTH, i);
            g2.drawString(sNum, Y_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING, i + Y_AXIS_LABEL_TO_LINE_VERTICAL_PADDING);
            num -= populationInterval;
        }
        g2.translate(-this.x, -this.yHeight);
    }

    @Override
    public void drawXDivisions(Graphics2D g2) {
        g2.translate(this.x, this.yHeight);
        int num = 0;
        for (int i = 0; num <= 100; i += (int)(this.xWidth / (20))){
            String sNum = Integer.toString(num);
            g2.drawLine(i, -AXIS_LABEL_LINE_WIDTH, i, AXIS_LABEL_LINE_WIDTH);
            g2.drawString(sNum, i + X_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING, X_AXIS_LABEL_TO_LINE_VERTICAL_PADDING);
            num += 5;
            xLimit = i;
        }
        g2.drawLine(x, 0, xLimit, 0);
        g2.translate(-this.x, -this.yHeight);
    }

    public void handleSetPopulation(Population population){
        histogram = new Histogram();
        histogram.setPopulation(population);
    }
} //End HistogramComponent
