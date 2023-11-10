package mainApp;

import java.awt.Graphics2D;

public abstract class InteractiveComponent {
    private int x, y, xLimit, yLimit, xWidth, yHeight;

    public void drawOn(Graphics2D g2){
        this.drawYDivisions(g2);
        this.drawXDivisions(g2);
    }

    public abstract void drawXDivisions(Graphics2D g2);
    public abstract void drawYDivisions(Graphics2D g2);

    public abstract int calculateX(double x);
    public abstract int calculateY(double y);
}
