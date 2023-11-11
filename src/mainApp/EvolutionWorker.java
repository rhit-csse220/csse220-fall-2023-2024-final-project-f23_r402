package mainApp;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.util.concurrent.CountDownLatch;

/**
 * Class: EvolutionWorker
 * @author F23_R402
 * 
 * Purpose: The EvolutionWorker class extends SwingWorker and represents the background
 * worker responsible for handling the evolution process in the EvolutionComponent.
 * It operates perform evolutionary iterations and update the UI,
 * and handle user interactions such as pausing and auto-stopping.
 */
public class EvolutionWorker extends SwingWorker<Void, Void> {
    // fields
    private EvolutionComponent evComponent;
    private int generations;
    private JButton startEvolutionButton;
    private boolean shutAllFrames;
    private volatile boolean paused = false;
    private boolean autoStopEnabled = false;
    private CountDownLatch pauseLatch = new CountDownLatch(1);

    /**
     * Constructs an EvolutionWorker with the specified EvolutionComponent, number of generations,
     * and the JButton used to start evolution.
     * @param evComponent          The EvolutionComponent instance for evolution handling.
     * @param generations          The total number of generations for evolution.
     * @param startEvolutionButton The JButton used to start the evolution process.
     */
    public EvolutionWorker(EvolutionComponent evComponent, int generations, JButton startEvolutionButton) {
        this.evComponent = evComponent;
        this.generations = generations;
        this.startEvolutionButton = startEvolutionButton;
        this.shutAllFrames = false;
    } //EvolutionWorker

    /**
     * Sets the paused state of the worker.
     * @param paused True if the worker should be paused, false otherwise.
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
        if (!paused) {
            pauseLatch.countDown(); // Resume the worker
        }
    } //setPaused

    /**
     * Sets the auto-stop feature of the worker.
     * @param autoStopEnabled True if auto-stop is enabled, false otherwise.
     */
    public void setAutoStopEnabled(boolean autoStopEnabled) {
        this.autoStopEnabled = autoStopEnabled;
    } //setAutoStopEnabled

    /**
     * Checks if all frames should be shut down.
     * @return True if all frames should be shut down, false otherwise.
     */
    public boolean isShutAllFrames() {
        return this.shutAllFrames;
    } //isShutAllFrames

    /**
     * Sets the flag to shut down all frames.
     * @param shutAllFrames True if all frames should be shut down, false otherwise.
     */
    public void setShutAllFrames(boolean shutAllFrames) {
        this.shutAllFrames = shutAllFrames;
    } //setShutAllFrames

    /**
     * The background task that performs evolutionary iterations and updates the UI.
     * @return null when the task is complete.
     * @throws Exception if an error occurs during the background task.
     */
    @Override
    protected Void doInBackground() throws Exception {
        for (int generationCount = 0; generationCount <= generations; generationCount++) {
            evComponent.handleSelection();
            publish();

            while (paused) {
                try {
                    pauseLatch.await(); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (autoStopEnabled && evComponent.checkForFitness100()) {
                final int generationCounts = generationCount;
                SwingUtilities.invokeLater(() -> {
                    startEvolutionButton.setText("Start Evolution");
                    JOptionPane.showMessageDialog(null, "The first generation with perfect genes is " + generationCounts, "Perfect Genes Found", JOptionPane.INFORMATION_MESSAGE);
                });
                return null;
            }

            SwingUtilities.invokeLater(() -> {
                evComponent.repaint();
            });
        }

        SwingUtilities.invokeLater(() -> {
            startEvolutionButton.setText("Start Evolution");
            this.shutAllFrames = true;
        });

        return null;
    } //doInBackground
} //End EvolutionWorker
