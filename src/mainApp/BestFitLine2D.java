package mainApp;

public class BestFitLine2D {
    private double bestFitness, avgFitness, lowFitness, hammingDistance;

    public double getHammingDistance() {
        return hammingDistance;
    }

    public void setHammingDistance(double hammingDistance) {
        this.hammingDistance = hammingDistance;
    }

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

    public BestFitLine2D(double bestFitness, double avgFitness, double lowFitness, double hammingDistance) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.lowFitness = lowFitness;
        this.hammingDistance = hammingDistance;
    }
}
