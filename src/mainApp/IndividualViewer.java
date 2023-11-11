package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/*
 * Class: IndividualViewer
 * @author F23_R402
 * 
 * Purpose: To create and view the frame required for visualizing the best chromosome 
 */
public class IndividualViewer extends DataViewer {
    // fields
    private IndividualComponent indComponent = new IndividualComponent();
    private int timerDelay;
    
    public int getTimerDelay() {
        return this.timerDelay;
    }

    public void setTimerDelay(int timerDelay) {
        this.timerDelay = timerDelay;
    }

    public IndividualComponent getIndComponent() {
        return this.indComponent;
    }

    public void setIndComponent(IndividualComponent indComponent) {
        this.indComponent = indComponent;
    }

    public void driverMain(){
        // constants
        final String frameTitle = "Best Chromosome";
        final int frameWidth = 400;
        final int frameHeight = 400;
        final int MAX_PERCENTAGE = 100;

        this.frame = new JFrame();
        this.frame.setTitle(frameTitle);
        this.frame.setSize(frameWidth, frameHeight);
        this.frame.setLocation(767, 393);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        this.frame.setLayout(new BorderLayout());
        this.frame.setVisible(true);
        this.frame.add(indComponent);

        //Adds the top label that constantly updates with the newest average hamming distance
        String hammingText = "Average hamming distance: ";
        JLabel hammingDistance = new JLabel(hammingText);
        this.frame.add(hammingDistance, BorderLayout.NORTH);

        this.timer = new Timer(this.timerDelay, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // In order to fit it on the graph, the average hamming distance had to be multiplied by 100. Here, we divide it to obtain the actual value
                hammingDistance.setText(hammingText + indComponent.getPopulationPrevHammingDistance() / MAX_PERCENTAGE);
                frame.repaint();
            }
        });

        this.timer.start();
        this.frame.pack();
    }

    /**
     * ensures: sets new size for the frame and packs it
     * @param width
     * @param height
     */
    public void setSize(int width, int height){
        this.frame.setSize(width, height);
        this.frame.pack();
    }

} //End IndividualViewer
