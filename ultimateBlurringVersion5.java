
/**
 * Gets an image of black and white, then blurs it
 *
 * Tishar
 * Version 5
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
public class ultimateBlurringVersion5 extends JFrame implements ActionListener,MouseListener{
    int gridx = 30;
    int gridy = 16;

    int mousex=0;
    int mousey=0;

    //int[][] setImage = new int[gridx+2][gridy+2];

    //int[][] setLocation = new int[gridx+2][gridy+2];

    int[][] setImageR = new int[gridx+2][gridy+2];
    int[][] setImageG = new int[gridx+2][gridy+2];
    int[][] setImageB = new int[gridx+2][gridy+2];

    int[][] finalImage = new int[gridx][gridy];
    int width=30;
    int length=30;

    int colorR=0;
    int colorG=0;
    int colorB=0;
    
    int colorOffset=510;
    // int colorSwitch=255;
    // char color='n'; // n = none (uses colorSwitch instead for the shades), r = red, b = blue, g = green.

    // Color currentColor = new Color(0,0,0);

    public void mouseExited(MouseEvent e) {System.out.println("exit");}

    public void mouseEntered(MouseEvent e) {
        for (int y=0; y<gridy+2; y++){
            for(int x = 0; x<gridx+2; x++){ // this sets all grids to empty
                setImageR[x][y] = 0;
                setImageG[x][y] = 0;
                setImageB[x][y] = 0;
            }
        }
        // for(int x=gridx; x<gridx+2; x--){ // this sets all grids to empty
        // Color currentColor = new Color(255/x,255/x,255/x);
        // }

        System.out.println("entered");
    }

    public void mouseReleased(MouseEvent e) {System.out.println("released");}

    public void mousePressed(MouseEvent e) {System.out.println("pressed");}

    public void mouseClicked(MouseEvent e) {

        if(e.getX()/30<=gridx && e.getY()/30<=gridy){
            mousex=Math.round(e.getX()/30); // current mouse X pos
            mousey=Math.round(e.getY()/30); // current mouse Y pos
        }
        System.out.println("clicked at "+mousex/5+", "+mousey/5); // prints out the location of the mouse click.
        // start x pos, end x pos, start y pos, end y pos, and hex color (0 black to 255 white.). positions go up by 1 for each tile.
        if(mousey==1){
            if(mousex<18){
                setImageR[gridx+1][1] = mousex*15;
                setImageG[gridx+1][1] = mousex*15;
                setImageB[gridx+1][1] = mousex*15;
            } 
            if(mousex==18){
                setImageR[gridx+1][1] = 255;
                setImageG[gridx+1][1] = 0;
                setImageB[gridx+1][1] = 0;
            }
            if(mousex==19){
                setImageR[gridx+1][1] = 0;
                setImageG[gridx+1][1] = 255;
                setImageB[gridx+1][1] = 0;
            }
            if(mousex==20){
                setImageR[gridx+1][1] = 0;
                setImageG[gridx+1][1] = 0;
                setImageB[gridx+1][1] = 255;
            }
        }
        setImageR[mousex][mousey] = setImageR[gridx+1][1];
        setImageG[mousex][mousey] = setImageG[gridx+1][1];
        setImageB[mousex][mousey] = setImageB[gridx+1][1];
        //setLocation[mousex][mousey] =  colorSwitch;
        System.out.println(colorR+", "+colorG+", "+colorB);
        //reblur();
        repaint();
    }

    // public void reblur(){
    // for (int y=1; y<gridy; y++){
    // for(int x = 1; x<gridx; x++){ 
    // int pixelAverage = Math.round((setImage[x-1][y+1]+setImage[x][y+1]+setImage[x+1][y+1]) + (setImage[x-1][y]+setImage[x][y]+setImage[x+1][y]) + (setImage[x-1][y-1]+setImage[x][y-1]+setImage[x+1][y-1]));
    // finalImage[x][y] = pixelAverage / 9;
    // }
    // }

    // }

    public void actionPerformed(ActionEvent e){ // runs when an actionlistener is called.
        System.out.println(e);
        String cmd=e.getActionCommand();
        switch(cmd){ // When this item is selected, do this.
                // if the action is this, run this
            case "Quit": System.exit(0); // exits program
            case "Undo": 
                setImageR[mousex][mousey]=0; 
                setImageG[mousex][mousey]=0; 
                setImageB[mousex][mousey]=0; 
                //reblur(); 
                repaint(); // moves player up
                break;
            default: break;
        }
    }

    public void paint (Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int xPos = 0;
        int yPos = 0;

        int width=30;
        int length=30;
        for(int y=0; y<gridy+2; y++){
            for(int x=0; x<gridx+2; x++){
                Color currentColor = new Color(setImageR[x][y],setImageG[x][y],setImageB[x][y]);
                g2.setColor(currentColor);
                g2.fillRect(x*width+xPos,y*length+yPos,width,length);
            }
        }

        // for(int y=1; y<gridy; y++){
            // for(int x=1; x<gridx; x++){
                // Color currentColor = new Color((255/gridx)*x,(255/gridx)*y,(255/gridy)*y);
                // g2.setColor(currentColor);
                // g2.fillRect(x*width+xPos,(y*length+yPos)+600,width,length);
            // }
        // }
        for(int y=1; y<2; y++){
            for(int x=1; x<gridx; x++){
                Color currentColor = new Color((255/gridx)*x,0,0);
                g2.setColor(currentColor);
                g2.fillRect(x*width+xPos,(y*length+yPos)+colorOffset,width,length);
            }
        }
        for(int y=1; y<2; y++){
            for(int x=1; x<gridx; x++){
                Color currentColor = new Color(0,(255/gridx)*x,0);
                g2.setColor(currentColor);
                g2.fillRect(x*width+xPos,(y*length+yPos)+colorOffset+30,width,length);
            }
        }for(int y=1; y<2; y++){
            for(int x=1; x<gridx; x++){
                Color currentColor = new Color(0,0,(255/gridx)*x);
                g2.setColor(currentColor);
                g2.fillRect(x*width+xPos,(y*length+yPos)+colorOffset+60,width,length);
            }
        }
        
        //These are shown so the player can pick which color to draw with.
        for(int x=0; x<18; x++){
            setImageR[x][1]=x*15;
            setImageG[x][1]=x*15;
            setImageB[x][1]=x*15;
        }
        // Red
        setImageR[18][1] = 255;
        setImageG[18][1] = 0;
        setImageB[18][1] = 0;

        // Green
        setImageR[19][1] = 0;
        setImageG[19][1] = 255;
        setImageB[19][1] = 0;

        // Blue
        setImageR[20][1] = 0;
        setImageG[20][1] = 0;
        setImageB[20][1] = 255;
    }

    // public void createBox(int xStart, int xEnd, int yStart, int yEnd, int hexColor){
    // for(int y=yStart; y<yEnd; y++){
    // for(int x=xStart; x<xEnd; x++){
    // setImage[x][y] = hexColor;
    // }
    // }
    // }

    public ultimateBlurringVersion5(){
        setTitle("Image Blurring");

        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar=new JMenuBar();
        this.setJMenuBar(menuBar);

        menu = new JMenu("File");
        menuBar.add(menu);
        menuItem=new JMenuItem("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu = new JMenu("Edit");
        menuBar.add(menu);
        menuItem=new JMenuItem("Undo");
        menuItem.addActionListener(this);
        menuItem.setAccelerator(KeyStroke.getKeyStroke('z'));
        menu.add(menuItem);
        addMouseListener(this);

        this.getContentPane().setPreferredSize(new Dimension(1000,1080));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();
        this.toFront();
        this.setVisible(true);

        // for (int y=0; y<gridy+2; y++){
        // for(int x = 0; x<gridx+2; x++){ // this sets all grids to empty
        // setImage[x][y] = 0;
        // }
        // }

        // Creating a box goes in order of
        // start x pos, end x pos, start y pos, end y pos, and hex color (0 black to 255 white.). positions go up by 1 for each tile.
        //createBox(5,11,5,6,100);
        //createBox(10,11,6,13,255);

        // for (int y=0; y<gridy+2; y++){
        // for(int x = 0; x<gridx+2; x++){ 
        // System.out.print(setImage[x][y]+" ");
        // }
        // System.out.println();
        // }
        // System.out.println("\n\n");

        // for (int y=1; y<gridy; y++){
        // for(int x = 1; x<gridx; x++){ 
        // int pixelAverage = Math.round((setImage[x-1][y+1]+setImage[x][y+1]+setImage[x+1][y+1]) + (setImage[x-1][y]+setImage[x][y]+setImage[x+1][y]) + (setImage[x-1][y-1]+setImage[x][y-1]+setImage[x+1][y-1]));
        // finalImage[x][y] = pixelAverage / 9;
        // }
        // }

        repaint();
        for (int y=0; y<gridy; y++){
            for(int x = 0; x<gridx; x++){ 
                System.out.print(finalImage[x][y]+" ");
            }
            System.out.println();
        }
    }
}
