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
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.text.View;

public class IndividualViewer extends Viewer {
    
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

    public void driverMain(){
        final String frameTitle = "Best Chromosome";
        final int frameWidth = 400;
        final int frameHeight = 400;

        frame = new JFrame();
        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(590, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        frame.add(indComponent);

        //Adds the top label that constantly updates with the newest average hamming distance
        String hammingText = "Average hamming distance: ";
        JLabel hammingDistance = new JLabel(hammingText);
        frame.add(hammingDistance, BorderLayout.NORTH);

        timer = new Timer(timerDelay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // In order to fit it on the graph, the average hamming distance had to be multiplied by 100. Here, we divide it to obtain the actual value
                hammingDistance.setText(hammingText+indComponent.getPopulation().prevHammingDistance/100);
                frame.repaint();
            }
        });

        timer.start();

        frame.pack();
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
