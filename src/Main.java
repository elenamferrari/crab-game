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
    public Image crabPic;
    public Image bucketPic;
    public Image netPic;
    public Image background;
    private Crab[] crab;
    private Crab bucket;
    private Crab net;

    public static void main(String[] args) {
        Main ex = new Main();
        new Thread(ex).start();
    }

    public Main() {
        setUpGraphics();
        canvas.addKeyListener(this);

        background = Toolkit.getDefaultToolkit().getImage("oceanphoto.jpg");

        crab = new Crab[10];
        for(int x = 0; x<crab.length; x++) {
            crab[x] = new Crab (((int)(Math.random()*1500+15)), ((int)(Math.random()*1500+15)), ((int)(Math.random()*12+1)), ((int)(Math.random()*12+1)));
            crabPic = Toolkit.getDefaultToolkit().getImage("japaneseshorecrab.png");
        }

        net = new Crab (750, 400, 0, 0);
        netPic = Toolkit.getDefaultToolkit().getImage("net.png");

        bucket = new Crab(1500, 150, 0, 0);
        bucketPic = Toolkit.getDefaultToolkit().getImage("bucket.png");


    }

    public void run() {
        while(true) {
            render();
            crash();
            pause(20);
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
        if(crab[5].rec.intersects(net.rec) && crab[5].isAlive == true) {
            crab[5].dy = net.dy;
            crab[5].dx = net.dx;
            System.out.println("!");
        }

        if (crab[5].rec.intersects(net.rec) && crab[5].isAlive == true) {
            crab[5].isAlive = false;
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
        g.drawString("hee;p", 55, 550);
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
      //  System.out.println(background.getWidth(null));

        for(int i=0; i<crab.length; i++) {
            g.drawImage(crabPic, crab[i].xpos, crab[i].ypos, 120, 100, null);
        }
        g.drawImage(netPic, net.xpos, net.ypos, 400, 300, null);

        g.drawImage(bucketPic, bucket.xpos, bucket.ypos, 250, 300, null);

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

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
    }
}