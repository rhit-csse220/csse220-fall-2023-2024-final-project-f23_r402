package mainApp;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Abstract class: DataViewer
 * @author F23_R402
 * 
 * Purpose: provide generic functionality for all visualization additional viewer classes within the program
 */
public abstract class DataViewer {
    // fields
    protected DataComponent component;
    protected JFrame frame;
    protected int timerDelay;
    protected Timer timer;

    /*
     * ensures: The abstract method where it creates and initializes the frame and components for the application
     */
    public abstract void driverMain(); //driverMain

    /*
     * ensures: The given frame of the class is shut down
     */
    public void shutDownFrame(){
        frame.dispose();
    } //shutDownFrame

    /*
     * ensures: sets a new timer delay
     */
    public void setTimerDelay(int timerDelay) {
        this.timerDelay = timerDelay;
    } //setTimerDelay

    /*
     * ensures: stops the timer
     */
    public void stopTimer() {
        this.timer.stop();
    } //stopTimer

    /*
     * ensures: starts the timer
     */
    public void startTimer(){
        this.timer.start();
    } //startTimer

    /*
     * ensures: sets the population of the assigned component
     */
    public void handleSetPopulation(Population population){
        component.setPopulation(population);
    } //handleSetPopulation
}
