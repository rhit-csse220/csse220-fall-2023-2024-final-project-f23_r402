package mainApp;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Abstract class: DataComponent
 * @author F23_R402
 * 
 * Purpose: provide generic functionality for all the visualization additional component classes within the program
 */
abstract class DataComponent extends JComponent {
    // fields
    protected Population population;

    /**
     * ensures: returns the population
     * @return
     */
    public Population getPopulation() {
        return this.population;
    } //getPopulation

    /**
     * ensures: sets the population to a new value
     * @param population
     */
    public void setPopulation(Population population) {
        this.population = population;
    } //setPopulation

    /**
     * ensures: draws the component
     */
    @Override
    public abstract void paint(Graphics g); //paint
} //End DataComponent
