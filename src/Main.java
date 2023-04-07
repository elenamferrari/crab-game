import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main implements Runnable, KeyListener, MouseMotionListener, MouseListener {

    final int WIDTH = 2000;
    final int HEIGHT = 1500;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;
    public Boolean GameStart;
    public Image startscreen;
    public Image losescreen;
    public Image winscreen;
    public Image crabPic;
    public Image bucketPic;
    public Image netPic;
    public Image snailPic;
    public Image background;
    private Crab[] crab;
    private Crab bucket;
    private Crab net;
    private Crab[] snail;

    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main() {
        setUpGraphics();
        canvas.addKeyListener(this);

        background = Toolkit.getDefaultToolkit().getImage("oceanphoto.jpg");
        startscreen = Toolkit.getDefaultToolkit().getImage("startscreen.jpg");
        winscreen = Toolkit.getDefaultToolkit().getImage("winscreen.jpg");
        losescreen = Toolkit.getDefaultToolkit().getImage("losescreen.jpg");

        crab = new Crab[6];
        for(int x = 0; x<crab.length; x++) {
            crab[x] = new Crab (((int)(Math.random()*1500+15)), ((int)(Math.random()*750)+700), ((int)(Math.random()*12+1)), ((int)(Math.random()*12+1)));
            crabPic = Toolkit.getDefaultToolkit().getImage("japaneseshorecrab.png");
            crab[x].height=100;
            crab[x].width=120;
        }

        snail = new Crab[11];
        for(int x = 0; x<snail.length; x++) {
            snail[x] = new Crab(((int) (Math.random() * 1500 + 15)), ((int) (Math.random() * 750) + 700), ((int) (Math.random() * 12 + 1)), 0);
            snail[x].height=50;
            snail[x].width=60;
        }
            snailPic = Toolkit.getDefaultToolkit().getImage("snail.png");

        net = new Crab (750, 400, 0, 0);
        netPic = Toolkit.getDefaultToolkit().getImage("net.png");
        net.height=300;
        net.width=400;

        bucket = new Crab(1500, 150, 0, 0);
        bucketPic = Toolkit.getDefaultToolkit().getImage("bucket.png");
        bucket.height=300;
        bucket.width=250;

        GameStart=false;


    }

    public void run() {
        while(true) {
            render();
            crash();
            pause(20);
           // bounce();
            for(int i=0; i<snail.length; i++) {
                snail[i].wrap();
            }
            for(int i=0; i<crab.length; i++) {
                crab[i].bounce();
            }
            //add bounce command, parameters for crabs, "fish" to save wrap at bottom, net moves w mouse
        }
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
          //  throw new RuntimeException(e);
        }
    }

    public void crash() {
        for(int i=0; i<crab.length; i++) {
            if (crab[i].rec.intersects(net.rec) /*&& crab[i].isAlive == true*/) {
                crab[i].xpos = net.xpos;
                crab[i].ypos = net.ypos+120;
                crab[i].dx=net.dx;
                crab[i].dy=net.dy;
                crab[i].rec=new Rectangle(crab[i].xpos, crab[i].ypos, crab[i].width, crab[i].height);
                System.out.println("!");
            }
        }

        for(int i=0; i<crab.length; i++) {
            if (crab[i].rec.intersects(bucket.rec) && crab[i].isAlive == true) {
                crab[i].isAlive = false;
            }
        }

        for(int i=0; i<snail.length; i++) {
            for(int j=0; j<crab.length; j++) {
                if (crab[j].rec.intersects(snail[i].rec)) {
                    snail[i].isAlive=false;
                    System.out.println("nom");
                }
            }
        }
    }


    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addKeyListener(this);

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    private void render() {

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0,0,WIDTH,HEIGHT);

        if(GameStart==true) {
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
            //  System.out.println(background.getWidth(null));

            if(bucket.isAlive==true) {
                g.drawImage(bucketPic, bucket.xpos, bucket.ypos, 250, 300, null);
            }

                for (int i = 0; i < crab.length; i++) {
                    if (crab[i].isAlive) {
                    g.drawImage(crabPic, crab[i].xpos, crab[i].ypos, 120, 100, null);
                    //g.drawRect(crab[i].xpos, crab[i].ypos, 120, 100);
                }
            }

            if(net.isAlive==true) {
                g.drawImage(netPic, net.xpos, net.ypos, 400, 300, null);
                //g.drawRect(net.rec.x, net.rec.y, net.rec.width, net.rec.height);
            }

                for (int i = 0; i < snail.length; i++) {
                    if (snail[i].isAlive) {
                    g.drawImage(snailPic, snail[i].xpos, snail[i].ypos, 60, 50, null);
                }
            }
        }

        else {
            g.drawImage(startscreen, 0, 0, WIDTH, HEIGHT, null);
        }

        for(int i=0; i<crab.length; i++) {
            for(int j=0; j<snail.length; j++) {
                if(crab[i].isAlive==false && snail[j].isAlive==true) {
                    snail[j].isAlive=false;
                    g.drawImage(winscreen, 0, 0, WIDTH, HEIGHT, null);
                    bucket.isAlive=false;
                    net.isAlive=false;
                }
            }
        }

        for(int i=0; i<crab.length; i++) {
            for(int j=0; j<snail.length; j++) {
                if(crab[i].isAlive==false && snail[j].isAlive==false) {
                    g.drawImage(losescreen, 0, 0, WIDTH, HEIGHT, null);
                    bucket.isAlive=false;
                    net.isAlive=false;
                }
            }
        }

        g.dispose();
                bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode() + " what you pressed" + e.getKeyChar());
        if(e.getKeyCode()==32) {
            System.out.println("press space bar");
            GameStart=true;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        net.xpos=e.getX();
        net.ypos=e.getY();
        net.rec=new Rectangle(net.xpos, net.ypos+120, 150, 120);
    }
}