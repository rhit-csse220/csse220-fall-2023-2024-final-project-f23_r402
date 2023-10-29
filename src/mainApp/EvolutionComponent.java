package mainApp;

import javax.swing.JComponent;

public class EvolutionComponent extends JComponent {
    public Population population;

    public EvolutionComponent() {
		this.population = new Population();
    }

    public void handleTruncationSelection(double mutationRate){
      this.population.truncationSelection(mutationRate);
    }
}
