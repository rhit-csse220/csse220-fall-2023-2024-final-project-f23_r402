package mainApp;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class InteractiveComponent extends JComponent{
    protected int x, y, xLimit, yLimit, xWidth, yHeight;

    public static final double X1_TO_FRAME_RATIO = 0.04;
    public static final double Y1_TO_FRAME_RATIO = 0.08;
    public static final double X2_TO_FRAME_RATIO = 0.94;
    public static final double Y2_TO_FRAME_RATIO = 0.82;
    public static final int FITNESS_SCORE_INTERVAL = 10;
    public static final int AXIS_LABEL_LINE_WIDTH = 5;
    public static final int X_AXIS_LABEL_TO_LINE_VERTICAL_PADDING = 20;
    public static final int X_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING = -6;
    public static final int Y_AXIS_LABEL_TO_LINE_HORIZONTAL_PADDING = -25;
    public static final int Y_AXIS_LABEL_TO_LINE_VERTICAL_PADDING = 5;

    public void drawOn(Graphics2D g2){
        this.drawYDivisions(g2);
        this.drawXDivisions(g2);
    }

    public abstract void drawXDivisions(Graphics2D g2);
    public abstract void drawYDivisions(Graphics2D g2);

    public abstract int calculateX(double x);
    public abstract int calculateY(double y);

    protected abstract void paintComponent(Graphics g);
}
