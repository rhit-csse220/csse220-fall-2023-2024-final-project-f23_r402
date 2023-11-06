package mainApp;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class EvolutionWorker extends SwingWorker<Void, Void> {
    private EvolutionComponent evComponent;
    private IndividualComponent individualComponent;
    private PopulationComponent populationComponent;
    private int generations;
    private JButton startEvolutionButton;
    private boolean shutAllFrames;
    private volatile boolean paused = false;
    private CountDownLatch pauseLatch = new CountDownLatch(1);

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (!paused) {
            pauseLatch.countDown(); // Resume the worker
        }
    }

    public boolean isShutAllFrames() {
        return shutAllFrames;
    }

    public void setShutAllFrames(boolean shutAllFrames) {
        this.shutAllFrames = shutAllFrames;
    }

    public EvolutionWorker(EvolutionComponent evComponent, IndividualComponent individualComponent, PopulationComponent populationComponent, int generations, JButton startEvolutionButton) {
        this.evComponent = evComponent;
        this.individualComponent = individualComponent;
        this.populationComponent = populationComponent;
        this.generations = generations;
        this.startEvolutionButton = startEvolutionButton;
        this.shutAllFrames = false;
    }

    @Override
    protected Void doInBackground() throws Exception {
        for (int generationCount = 0; generationCount <= generations; generationCount++) {
            evComponent.handleSelection();
            evComponent.generationCount = generationCount;
            publish();
    
            // Check if paused and wait
            while (paused || evComponent.checkForFitness100()) {
                try {
                    pauseLatch.await(); // Wait until signaled to resume
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
    
            // Repaint the graph (update the UI)
            SwingUtilities.invokeLater(() -> {
                evComponent.repaint();
            });
        }
    
        SwingUtilities.invokeLater(() -> {
            startEvolutionButton.setText("Start Evolution");
            this.shutAllFrames = true;
        });
    
        return null;
    }
    
}



