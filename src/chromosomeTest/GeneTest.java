package chromosomeTest;

import static org.junit.Assert.*;
import org.junit.Test;

import mainApp.Gene;

import java.awt.geom.Rectangle2D;

public class GeneTest {
	@Test
	public void testChangeBitFrom0To1() {
	    Gene gene = new Gene('0', true, 0, 0, Gene.GENE_SIDE);
	    gene.changeBit();
	    assertEquals('1', gene.getBit());
	}

	@Test
	public void testChangeBitFrom1To0() {
	    Gene gene = new Gene('1', true, 0, 0, Gene.GENE_SIDE);
	    gene.changeBit();
	    assertEquals('0', gene.getBit());
	}

	@Test
	public void testSetNonChangeable() {
	    Gene gene = new Gene('0', true, 0, 0, Gene.GENE_SIDE);
	    gene.setNonChangeable();
	    assertFalse(gene.isChangeable());
	}

	@Test
	public void testSetChangeable() {
	    Gene gene = new Gene('0', false, 0, 0, Gene.GENE_SIDE);
	    gene.setChangeable();
	    assertTrue(gene.isChangeable());
	}

	@Test
	public void testIsSelectedWithinBounds() {
	    Gene gene = new Gene('0', true, 0, 0, Gene.GENE_SIDE);
	    assertTrue(gene.isSelected(new Rectangle2D.Double(0, 0, Gene.GENE_SIDE, Gene.GENE_SIDE)));
	}

	@Test
	public void testIsSelectedOutsideBounds() {
	    Gene gene = new Gene('0', true, 0, 0, Gene.GENE_SIDE);
	    assertFalse(gene.isSelected(new Rectangle2D.Double(100, 100, Gene.GENE_SIDE, Gene.GENE_SIDE)));
	}

}

