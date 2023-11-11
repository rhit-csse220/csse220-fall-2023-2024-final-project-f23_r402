package mainApp;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Abstract class: Component
 * @author F23_R402
 * 
 * Purpose: provide generic functionality for all component classes within the program
 */
abstract class Component extends JComponent {
    protected Population population;

    public Population getPopulation() {
        return this.population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public void paint(Graphics g) {
    }
}
