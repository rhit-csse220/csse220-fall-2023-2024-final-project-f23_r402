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

public class IndividualViewer implements Runnable {
    
    private IndividualComponent indComponent = new IndividualComponent();
    private int timerDelay;
    public int getTimerDelay() {
        return timerDelay;
    }

    public void setTimerDelay(int timerDelay) {
        this.timerDelay = timerDelay;
    }

    public IndividualComponent getIndComponent() {
        return indComponent;
    }

    public void setIndComponent(IndividualComponent indComponent) {
        this.indComponent = indComponent;
    }

    private JFrame frame;
    private Timer timer;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void stopTimer() {
        this.timer.stop();
    }

    public void driverMain(){
        final String frameTitle = "Individual Viewer";
        final int frameWidth = 400;
        final int frameHeight = 390;

        frame = new JFrame();
        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(1000, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        frame.add(indComponent);

        timer = new Timer(1000/33, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                indComponent.getChromosome();
                frame.repaint();
            }
        });

        timer.start();

        frame.pack();
    }

    public void shutDownFrame(){
        frame.dispose();
    }

    @Override
    public void run() {
        this.driverMain();
    }

    public void setSize(int width, int height){
        frame.setSize(width, height);
        frame.pack();
    }

    // public static void main(String[] args) {
    //     IndividualViewer indViewer = new IndividualViewer();
    //     indViewer.getIndComponent().setPopulation(new Population(100, 100));
    //     indViewer.driverMain();
    // }
}
