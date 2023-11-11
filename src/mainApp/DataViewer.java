package mainApp;

import javax.swing.JFrame;
import javax.swing.Timer;

public abstract class DataViewer {
    // fields
    protected DataComponent component;
    protected JFrame frame;
    protected int timerDelay;
    protected Timer timer;

    public abstract void driverMain();

    public void shutDownFrame(){
        frame.dispose();
    }

    public void setTimerDelay(int timerDelay) {
        this.timerDelay = timerDelay;
    }

    public void stopTimer() {
        this.timer.stop();
    }

    public void startTimer(){
        this.timer.start();
    }

    public void handleSetPopulation(Population population){
        component.setPopulation(population);
    }
}
