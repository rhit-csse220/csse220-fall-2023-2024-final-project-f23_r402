package mainApp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/*
 * Class: HistogramViewer
 * @author F23_R402
 * 
 * Purpose: To create the frame for visualizing the histogram graph for the population and where its chromosomes fit in each fitness score interval
 */
public class HistogramViewer extends DataViewer {
    // fields
    private HistogramComponent histogramComponent;

    public HistogramViewer(){
        histogramComponent = new HistogramComponent(); 
    }

    /**
     * the main class, the entry point
     */
    public void driverMain(){
        frame = new JFrame();
        // constants
        final int frameWidth = 780;
        final int frameHeight = 400;
        final int xLocation = 0;
        final int yLocation = 393;
        final String frameTitle = "Population Fitness Histogram";

        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLocation(xLocation, yLocation);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(this.histogramComponent);

        timer = new Timer(timerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.repaint();
            }
        });
        timer.start();
        frame.pack();
    }

    /**
     * a wrapper method for HistogramComponent.handleSetPopulation(population)
     */
    public void handleSetPopulation(Population population){
        this.histogramComponent.handleSetPopulation(population);
    }
} //End HistogramViewer
