package mainApp;

import java.awt.Graphics;

import javax.swing.JComponent;

public class Component extends JComponent {
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
