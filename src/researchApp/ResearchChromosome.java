package researchApp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Random;

import mainApp.*;

public class ResearchChromosome extends Chromosome{
    public static final Color Gene_2_TEXT_COLOR = new Color(201,5,3);

    private String originalGenomeData;
    private boolean isPerfect = false;
    private ResearchGene[] genes;
    private int dayRemaining = 0;
    private HashMap<Character,Integer> numsOf1s0sQs = new HashMap<>(); 

    public String getOriginalGenomeData() {
        return originalGenomeData;
    }

    public void setOriginalGenomeData(String originalGenomeData) {
        this.originalGenomeData = originalGenomeData;
    }

    public boolean isPerfect() {
        return isPerfect;
    }

    public void setPerfect(boolean isPerfect) {
        this.isPerfect = isPerfect;
    }

    public ResearchGene[] getGenes() {
        return genes;
    }

    public void setGenes(ResearchGene[] genes) {
        this.genes = genes;
    }

    //THERE IS NO MUTATION IN RESEARCH CHROMOSOME
    public ResearchChromosome(int numOfGenes, int fitnessFunctionType){
        super(numOfGenes, fitnessFunctionType);
    }

    public ResearchChromosome() {
    }

    @Override
    public void initiateGeneWithString(String s) throws InvalidChromosomeFormatException {
        if (s.length() % 10 != 0) {
			throw new InvalidChromosomeFormatException(s.length());
		}
		
		this.numOfGenes = s.length();
		
		this.genes = new ResearchGene[numOfGenes];
		this.numPerColumn = numOfGenes / NUM_PER_ROW;
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				char bit = s.charAt(i*NUM_PER_ROW+j);
				this.genes[i*NUM_PER_ROW+j] = new ResearchGene(bit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
        checkAlleles();
    }

    @Override
    public void drawOn(Graphics g, int geneWidth, int border) {
        Graphics2D g2 = (Graphics2D) g;
		this.geneWidth = geneWidth;
		this.border = border;
		this.adjustGenePosition();
		
		for (int i = 0; i < this.genes.length; i++) {
			this.genes[i].drawOn(g2);
			if (this.genes[i].getBit()=='1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (this.genes[i].getBit()=='0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			} else if (this.genes[i].getBit()=='?'){
                g2.setColor(Gene_2_TEXT_COLOR);
            }
			g2.setFont(new Font(null, Font.PLAIN, geneWidth/3));
			g2.drawString((String)(i+""), this.genes[i].getX() + X_COORD_LETTER_OFFSET, geneWidth/3 + this.genes[i].getY());
		}
    }

    @Override
    public void drawGenes(Graphics2D g2) {
        for (int i = 0; i < this.genes.length; i++) {
			this.genes[i].drawOn(g2);
			if (this.genes[i].getBit()=='1') {
				g2.setColor(GENE_1_TEXT_COLOR);
			} else if (this.genes[i].getBit()=='0') {
				g2.setColor(GENE_0_TEXT_COLOR);
			} else if (this.genes[i].getBit()=='?'){
                g2.setColor(Gene_2_TEXT_COLOR);
            }
		}
    }

    @Override
    public void initiateGene() {
        Random r = new Random();
        this.genes = new ResearchGene[this.numOfGenes];
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				int bit = r.nextInt(0,4);
                char aBit = (bit == 0) ? '0' : (bit == 1) ? '1' : '?';
				this.genes[i*NUM_PER_ROW+j] = new ResearchGene(aBit, true, this.geneWidth*j + this.border, this.geneWidth*i, this.geneWidth);
			}
		}
        checkAlleles();
    }

    public void liveLife(){
        for (int days = 0; days < 1000; days++){
            for (int i = 0; i < genes.length; i++){
                ResearchGene gene = this.genes[i];
                if (this.checkAlleles()==100){
                    this.isPerfect = true;
                    this.originalGenomeData = this.getChromosomeDataAsString();
                    dayRemaining = 1000 - i;
                    this.fitnessScore = checkFitnessScore(dayRemaining);
                }
                if (!isPerfect){
                    gene.changeBit();
                    this.originalGenomeData = this.getChromosomeDataAsString();
                    dayRemaining = 1000 - i;
                    this.fitnessScore = checkFitnessScore(dayRemaining);
                }
            }
        }
    }

    public int checkAlleles() {
		int score = 0;
		String fileData = getChromosomeDataAsString();
		for (int i = 0; i < fileData.length(); i++){
            char bit = fileData.charAt(i);
			int sum = numsOf1s0sQs.get(bit);
            numsOf1s0sQs.put(bit, sum+1);
		}
        //int fitnessScore = (int) ((score / (double) numOfGenes) * MAX_FITNESS_SCORE);
        return score;
	}

    public double checkFitnessScore(int dayRemaining){
        return (1 + (19*dayRemaining / 1000));
    }

    @Override
    public String getChromosomeDataAsString() {
		String data = "";
		for (ResearchGene gene : this.genes) {
			data += gene.getBit();
		}
		return data;
	}

    @Override
    public void adjustGenePosition(){
		for (int i = 0; i < this.numPerColumn; i++) {
			for (int j = 0; j < NUM_PER_ROW; j++) {
				this.genes[i*NUM_PER_ROW+j].setX(this.geneWidth*j + this.border);
				this.genes[i*NUM_PER_ROW+j].setY(this.geneWidth*i);
				this.genes[i*NUM_PER_ROW+j].setGeneWidth(this.geneWidth);
			}
		}
	}

    @Override
    public ResearchGene handleGetSelectedGene(Rectangle2D.Double box) {
		for (int i = 0; i < this.genes.length; i++) {
			if (this.genes[i].isSelected(box)) {
				return this.genes[i];
			}
		}
		return null;
	}
}
