import java.awt.*;

public class Crab {
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle rec;


    public Crab(int pxpos, int pypos, int pdx, int pdy) {
        xpos = pxpos;
        ypos = pypos;
        dx = pdx;
        dy = pdy;
        isAlive = true;
        rec = new Rectangle (xpos, ypos, height, width);
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle (xpos, ypos, height, width);
    }

    public void bounce() {

    }
}
