package mainApp;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.util.concurrent.CountDownLatch;

public class EvolutionWorker extends SwingWorker<Void, Void> {
    private EvolutionComponent evComponent;
    private int generations;
    private JButton startEvolutionButton;
    private boolean shutAllFrames;
    private volatile boolean paused = false;
    private boolean autoStopEnabled = false;
    private CountDownLatch pauseLatch = new CountDownLatch(1); // Add this line


    public void setPaused(boolean paused) {
        this.paused = paused;
        if (!paused) {
            pauseLatch.countDown(); // Resume the worker
        }
    }
    public void setAutoStopEnabled(boolean autoStopEnabled) {
        this.autoStopEnabled = autoStopEnabled;
    }
    
    public boolean isShutAllFrames() {
        return shutAllFrames;
    }

    public void setShutAllFrames(boolean shutAllFrames) {
        this.shutAllFrames = shutAllFrames;
    }

    public EvolutionWorker(EvolutionComponent evComponent, int generations, JButton startEvolutionButton) {
        this.evComponent = evComponent;
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
            while (paused) {
                try {
                    pauseLatch.await(); // Wait until signaled to resume
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

               if (autoStopEnabled && evComponent.checkForFitness100()) {
                // Automatically reset the button to "Start Evolution" when fitness is 100
                final int generationCounts = evComponent.generationCount;
                SwingUtilities.invokeLater(() -> {
                    startEvolutionButton.setText("Start Evolution");
                    JOptionPane.showMessageDialog(null, "The first generation with perfect genes is " + generationCounts, "Perfect Genes Found", JOptionPane.INFORMATION_MESSAGE);
                });
                return null; // Exit the loop and stop the worker
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



