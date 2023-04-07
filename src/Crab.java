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
        height=40;
        width=40;
        isAlive = true;
        rec = new Rectangle (xpos, ypos, height, width);
    }

    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle (xpos, ypos, height, width);
    }

    public void bounce() {
        move();
        if (xpos>=2000) {
            dx=-dx;
        }
        if (ypos>=1500) {
            dy=-dy;
        }
        if(ypos<=750) {
            dy=-dy;
        }
        if (xpos<=5) {
            dx=-dx;
        }
        rec = new Rectangle (xpos, ypos, height, width);
    }

    public void wrap() {
        dy = 0;
        dx = ((int) (Math.random() * 15));
        move();
        if (xpos >= 2000) {
            xpos = 5;
        }
        rec = new Rectangle (xpos, ypos, height, width);
    }
}
