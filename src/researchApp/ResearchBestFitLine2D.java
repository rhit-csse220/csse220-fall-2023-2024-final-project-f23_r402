package researchApp;

import mainApp.BestFitLine2D;

public class ResearchBestFitLine2D extends BestFitLine2D {

    private int count1;
    private int count0;
    private int countQ;

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getCount0() {
        return count0;
    }

    public void setCount0(int count0) {
        this.count0 = count0;
    }

    public int getCountQ() {
        return countQ;
    }

    public void setCountQ(int countQ) {
        this.countQ = countQ;
    }

    public ResearchBestFitLine2D(double bestFitness, double avgFitness, double lowFitness, double hammingDistance, int num1s, int num0s, int numQs) {
        super(bestFitness, avgFitness, lowFitness, hammingDistance);
        this.count0 = num0s;
        this.count1 = num1s;
        this.countQ = numQs;
    }
}
