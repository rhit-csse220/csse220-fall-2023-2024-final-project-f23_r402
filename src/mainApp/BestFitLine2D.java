package mainApp;

/**
 * class: BestFitLine2D
 * @author F23_R402
 * 
 * Purpose:
 *   Helps draw lines in EvolutionComponent
 */
public class BestFitLine2D {
    // fields
    private double bestFitness, avgFitness, lowFitness, hammingDistance;
    private double numberOf0s, numberOf1s, numberOfQs;

    /**
     * Initializes the BestFitLine2D object
     * @param bestFitness  the best fitness score
     * @param avgFitness   the average fitness score
     * @param lowFitness   the lowest fitness score
     * @param hammingDistance  the hamming distance
     */
    public BestFitLine2D(double bestFitness, double avgFitness, double lowFitness, double hammingDistance) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.lowFitness = lowFitness;
        this.hammingDistance = hammingDistance;
    } //BestFitLine2D
    
    /**
     * Initializes the BestFitLine2D object for the research part
     * @param bestFitness  the best fitness score
     * @param avgFitness   the average fitness score
     * @param lowFitness   the lowest fitness score
     * @param hammingDistance  the hamming distance
     * @param numberOf0s   the number of zeroes in genome 
     * @param numberOf1s   the number of ones in genome
     * @param numberOfQs   the number of question marks in genome
     */
    public BestFitLine2D(double bestFitness, double avgFitness, double lowFitness, double hammingDistance,
            double numberOf0s, double numberOf1s, double numberOfQs) {
        this.bestFitness = bestFitness;
        this.avgFitness = avgFitness;
        this.lowFitness = lowFitness;
        this.hammingDistance = hammingDistance;
        this.numberOf0s = numberOf0s;
        this.numberOf1s = numberOf1s;
        this.numberOfQs = numberOfQs;
    } //BestFitLine2D
    
    /**
     * ensures: returns the number of zeroes in genome
     * @return the number of zeroes in genome
     */
    public double getNumberOf0s() {
        return numberOf0s;
    } //getNumberOf0s

    /**
     * ensures: sets the number of zeroes in genome to a new number
     * @param numberOf0s new value of the number of zeroes
     */
    public void setNumberOf0s(double numberOf0s) {
        this.numberOf0s = numberOf0s;
    } //setNumberOf0s

    /**
     * ensures: returns the number of ones in genome
     * @return the number of ones in genome
     */
    public double getNumberOf1s() {
        return numberOf1s;
    } //getNumberOf1s

    /**
     * ensures: sets the number of ones in genome to a new number
     * @param numberOf1s new value of the number of ones
     */
    public void setNumberOf1s(double numberOf1s) {
        this.numberOf1s = numberOf1s;
    } //setNumberOf1s

    /**
     * ensures: returns the number of question marks in genome
     * @return the number of question marks in genome
     */
    public double getNumberOfQs() {
        return numberOfQs;
    } //getNumberOfQs

    /**
     * ensures: sets the number of question marks in genome to a new number
     * @param numberOfQs new value of the number of question marks
     */
    public void setNumberOfQs(double numberOfQs) {
        this.numberOfQs = numberOfQs;
    } //setNumberOfQs

     /**
     * ensures: returns the hamming distance
     * @return hamming distance
     */
    public double getHammingDistance() {
        return hammingDistance;
    } //getHammingDistance

    /**
     * ensures: sets the hamming distance to a new number
     * @param hammingDistance new value of the hamming distance
     */
    public void setHammingDistance(double hammingDistance) {
        this.hammingDistance = hammingDistance;
    } //setHammingDistance

    /**
     * ensures: returns the best fitness score
     * @return best fitness score
     */
    public double getBestFitness() {
        return this.bestFitness;
    } //getBestFitness

    /**
     * ensures: sets the best fitness score to a new number
     * @param bestFitness new value of the best fitness score
     */
    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    } //setBestFitness

    /**
     * ensures: returns the average fitness score
     * @return average fitness score
     */
    public double getAvgFitness() {
        return this.avgFitness;
    } //getAvgFitness

    /**
     * ensures: sets the average fitness score to a new number
     * @param avgFitness new value of the average fitness score
     */
    public void setAvgFitness(double avgFitness) {
        this.avgFitness = avgFitness;
    } //setAvgFitness

    /**
     * ensures: returns the lowest fitness score
     * @return lowest fitness score
     */
    public double getLowFitness() {
        return this.lowFitness;
    } //getLowFitness

    /**
     * ensures: sets the lowest fitness score to a new number
     * @param lowFitness new value of the lowest fitness score
     */
    public void setLowFitness(double lowFitness) {
        this.lowFitness = lowFitness;
    } //setLowFitness
} //End BestFitLine2D
