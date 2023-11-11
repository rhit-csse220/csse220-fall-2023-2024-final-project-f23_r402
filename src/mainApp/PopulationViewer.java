package mainApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class PopulationViewer extends DataViewer {
    // fields
    private PopulationComponent populationComponent = new PopulationComponent();
    
    /**
     * ensures: the population viewer can be run
     */
    public void driverMain(){
        final String frameTitle = "Population Viewer";
        final int frameWidth = 400;
        final int frameHeight = 400;

        frame = new JFrame();
        frame.setTitle(frameTitle);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocation(1150, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(frameWidth, frameHeight));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Adds the component to the frame
        frame.add(populationComponent);
        
        timer = new Timer(1000/33, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Constantly redraws the frame according to the given component
                frame.setSize(frameWidth, populationComponent.getPreferredSize().height); 
                frame.repaint();
            }
        });

        timer.start();
        frame.pack();
    }

    /**
     * ensures: the population in populationComponent is updated
     */
    public void handleSetPopulation(Population population){
        this.populationComponent.setPopulation(population);
    }

    /**
     * @return the population component
     */
    public PopulationComponent getPopComponent(){
        return this.populationComponent;
    }
}
