package mainApp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class HistogramViewer extends Viewer {

    private HistogramComponent histogramComponent;

    public HistogramViewer(){
        histogramComponent = new HistogramComponent(); 
    }

    public void driverMain(){
        frame = new JFrame();
        final int frameWidth = 800;
        final int frameHeight = 400;
        final String frameTitle = "GA Histogram";

        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLocation(200, 400);
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

    public void handleSetPopulation(Population population){
        this.histogramComponent.handleSetPopulation(population);
    }
}
