package mainApp;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.util.List;

/**
 * The `EvolutionWorker` class is a SwingWorker used for running the evolution process in the background.
 * It performs evolutionary iterations, updates the user interface (UI) during or after each iteration, and
 * notifies when the evolution process is completed.
 */

public class EvolutionWorker extends SwingWorker<Void, Void> {
    private EvolutionComponent evComponent;
    private IndividualComponent individualComponent;
    private PopulationComponent populationComponent;
    private int generations;
    private JButton startEvolutionButton;
    private boolean shutAllFrames;

    public boolean isShutAllFrames() {
        return shutAllFrames;
    }

    public void setShutAllFrames(boolean shutAllFrames) {
        this.shutAllFrames = shutAllFrames;
    }

    /**
     * Constructs an `EvolutionWorker` with the specified parameters.
     *
     * @param evComponent         The `EvolutionComponent` responsible for the evolution process and UI updates.
     * @param generations         The number of generations to simulate in the evolution process.
     * @param startEvolutionButton The button used to control the evolution process.
     */
    public EvolutionWorker(EvolutionComponent evComponent, IndividualComponent individualComponent, PopulationComponent populationComponent, int generations, JButton startEvolutionButton) {
        this.evComponent = evComponent;
        this.individualComponent = individualComponent;
        this.populationComponent = populationComponent;
        this.generations = generations;
        this.startEvolutionButton = startEvolutionButton;
        this.shutAllFrames = false;
    }

    /**
     * Executes the evolution process in the background. It iterates over a specified number of generations,
     * updates the UI, and notifies when the process is completed.
     *
     * @return A `Void` object to indicate that no result value is returned.
     * @throws Exception If an error occurs during the execution.
     */
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
            this.shutAllFrames = true;
        });

        return null;
    }

    /**
     * Process a list of `Void` chunks during or after each iteration. This method is responsible for updating
     * the user interface, such as triggering a repaint of the `EvolutionComponent`.
     *
     * @param chunks A list of `Void` objects representing the updates.
     */
    @Override
    protected void process(List<Void> chunks) {
        // Update UI during or after each iteration (e.g., repaint)
        evComponent.repaint();
    }
}
