package mainApp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class BestFitLine2D {
    private double bestFitness, avgFitness, lowFitness;

    public double getBestFitness() {
        return this.bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    public double getAvgFitness() {
        return this.avgFitness;
    }

    public void setAvgFitness(double avgFitness) {
        this.avgFitness = avgFitness;
    }

    public double getLowFitness() {
        return this.lowFitness;
    }

    public void setLowFitness(double lowFitness) {
        this.lowFitness = lowFitness;
    }

    public BestFitLine2D(double bestFitness, double avgFitness, double lowFitness) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.lowFitness = lowFitness;
    }
}
