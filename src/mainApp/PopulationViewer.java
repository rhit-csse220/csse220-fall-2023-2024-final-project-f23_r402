package mainApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class PopulationViewer implements Runnable {
    
    private JFrame frame;
    private PopulationComponent populationComponent = new PopulationComponent();
    private Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void stopTimer(){
        timer.stop();
    }
    
    public void driverMain(){
        final String frameTitle = "Population Viewer";
        final int frameWidth = 400;
        final int frameHeight = 400;

        frame = new JFrame();
        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(1000, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        frame.add(populationComponent);

        timer = new Timer(1000/33, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.repaint();
            }
        });

        timer.start();

        frame.pack();
    }

    public void handleSetPopulation(Population population){
        this.populationComponent.setPopulation(population);
    }

    public PopulationComponent getPopComponent(){
        return this.populationComponent;
    }

    public void shutDownFrame(){
        frame.dispose();
    }

    @Override
    public void run() {
        this.driverMain();
    }
}
