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
    private String cloneGenomeData;
    private boolean isPerfect = false;
    private ResearchGene[] genes;
    private int daysRemaining = 0;
    private int num1s;
    private int num0s;
    private int numQs;

    public ResearchChromosome(String fileData){ 
		this.initiateGeneWithString(fileData);
	}

    public String getOriginalGenomeData() {
        return this.originalGenomeData;
    }

    public void setOriginalGenomeData(String originalGenomeData) {
        this.originalGenomeData = originalGenomeData;
    }

    public boolean isPerfect() {
        return this.isPerfect;
    }

    public void setPerfect(boolean isPerfect) {
        this.isPerfect = isPerfect;
    }

    public ResearchGene[] getGenes() {
        return this.genes;
    }

    public void setGenes(ResearchGene[] genes) {
        this.genes = genes;
    }

    public int getDaysRemaining(){
        return this.daysRemaining;
    }

    //THERE IS NO MUTATION IN RESEARCH CHROMOSOME
    public ResearchChromosome(int numOfGenes, int fitnessFunctionType){
        super(numOfGenes, fitnessFunctionType);
        
    }

    public ResearchChromosome() {
    }

    @Override
    public void initiateGeneWithString(String s) {
        this.num0s = 0;
        this.num1s = 0;
        this.numQs = 0;
        // if (s.length() % 10 != 0) {
		// 	throw new InvalidChromosomeFormatException(s.length());
		// }
		
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
        this.num0s = 0;
        this.num1s = 0;
        this.numQs = 0;
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

    public void liveLife() throws InvalidChromosomeFormatException{
        for (int days = 0; days < 1000; days++){
            this.initiateGeneWithString(this.originalGenomeData);
            for (int i = 0; i < genes.length; i++){
                ResearchGene gene = this.genes[i];
                if (!isPerfect){
                    gene.changeBit();
                }
            }
            if (checkAll1s(this.getChromosomeDataAsString())){
                    this.isPerfect = true;
                    break;
                }
            this.daysRemaining = 1000 - days;
        }
    }

    public void checkAlleles() {
		String fileData = originalGenomeData;
		for (int i = 0; i < fileData.length(); i++){
            char bit = fileData.charAt(i);
            int checkBit = Character.getNumericValue(bit);
            if (checkBit==0){
                num0s++;
            }
            else if (checkBit==1){
                num1s++;
            }
            else{
                numQs++;
            }
		}
	}

    public boolean checkAll1s(String s){
        int score = 0;
        for (int i = 0; i < s.length(); i++){
            if (Character.getNumericValue(s.charAt(i)) == 1) {
                score++;
            }
        }
        return (score == s.length());
    }

    //TODO REMOVE
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
