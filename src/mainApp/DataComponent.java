package mainApp;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Abstract class: Component
 * @author F23_R402
 * 
 * Purpose: provide generic functionality for all component classes within the program
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
    }

    /**
     * ensures: sets the population to a new value
     * @param population
     */
    public void setPopulation(Population population) {
        this.population = population;
    }

    /**
     * ensures: draws the component
     */
    @Override
    public abstract void paint(Graphics g);
}
