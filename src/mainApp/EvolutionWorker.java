package mainApp;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.util.List;

public class EvolutionWorker extends SwingWorker<Void, Void> {
    private EvolutionComponent evComponent;
    private int generations;
    private JButton startEvolutionButton;

    public EvolutionWorker(EvolutionComponent evComponent, int generations, JButton startEvolutionButton) {
        this.evComponent = evComponent;
        this.generations = generations;
        this.startEvolutionButton = startEvolutionButton;
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int generationCount = 0; generationCount <= generations; generationCount++) {
            evComponent.handleSelection();
            evComponent.generationCount = generationCount;
            publish();
            // System.out.println("Generation " + generationCount + " completed");
        }
        // System.out.println("Evolution process completed");

        // Update the button label when the evolution is completed
        SwingUtilities.invokeLater(() -> {
            startEvolutionButton.setText("Start Evolution");
        });

        return null;
    }
      @Override
    protected void process(List<Void> chunks) {
        // Update UI during or after each iteration (e.g., repaint)
        evComponent.repaint();
    }

}

  

