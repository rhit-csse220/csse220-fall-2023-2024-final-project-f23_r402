package mainApp;

public class BestFitLine2D {
    private double bestFitness, avgFitness, lowFitness, hammingDistance;
    private double numberOf0s, numberOf1s, numberOfQs;

    public double getNumberOf0s() {
        return numberOf0s;
    }

    public void setNumberOf0s(double numberOf0s) {
        this.numberOf0s = numberOf0s;
    }

    public double getNumberOf1s() {
        return numberOf1s;
    }

    public void setNumberOf1s(double numberOf1s) {
        this.numberOf1s = numberOf1s;
    }

    public double getNumberOfQs() {
        return numberOfQs;
    }

    public void setNumberOfQs(double numberOfQs) {
        this.numberOfQs = numberOfQs;
    }

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
    
    public BestFitLine2D(double bestFitness, double avgFitness, double lowFitness, double hammingDistance,
            double numberOf0s, double numberOf1s, double numberOfQs) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.lowFitness = lowFitness;
        this.hammingDistance = hammingDistance;
        this.numberOf0s = numberOf0s;
        this.numberOf1s = numberOf1s;
        this.numberOfQs = numberOfQs;
    }
}
