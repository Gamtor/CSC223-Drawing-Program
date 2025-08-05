
/**
 * Gets an image of black and white, then blurs it
 *
 * Tishar
 * Version 14
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.MouseInfo;
import java.awt.Color;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Arrays;
public class ultimateBlurringVersion14 extends JFrame implements ActionListener,MouseListener{
    int gridx = 50;
    int gridy = 40;
    int gridXOffset=20;
    int gridYOffset=20;

    public final DrawingPanel panel = new DrawingPanel();

    int mousex=0;
    int mousey=0;

    // int[] lastX = {0};
    // int[] lastY = {0};
    ArrayList<Integer> lastX = new ArrayList<>();
    ArrayList<Integer> lastY = new ArrayList<>();

    //int[][] setImage = new int[gridx+2][gridy+2]; Used for the blurring code
    int[][] setImageR = new int[gridx+2][gridy+2];
    int[][] setImageG = new int[gridx+2][gridy+2];
    int[][] setImageB = new int[gridx+2][gridy+2];

    int[][] finalImage = new int[gridx][gridy];
    int width=20;
    int length=20;

    int colorR=0;
    int colorG=0;
    int colorB=0;

    int colorOffsetX=800;
    int colorOffsetY=640;

    Color currentColor = new Color(0,0,0);
    //Color mouseColor = new Color(0,0,0);

    public class DrawingPanel extends JPanel{
        public DrawingPanel(){}

        public void paintComponent (Graphics g){
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            int xPos = 0;
            int yPos = 0;

            for(int y=0; y<gridy+2; y++){ // creates the board, makes the entire board black
                for(int x=0; x<gridx+2; x++){
                    currentColor = new Color(setImageR[x][y],setImageG[x][y],setImageB[x][y]);
                    g2.setColor(currentColor);
                    g2.fillRect(x*width+xPos+gridXOffset,y*length+yPos+gridYOffset,width,length);
                }
            }

            //These are shown so the player can pick which color to draw with.
            for(int x=0; x<18; x++){
                setImageR[x][0]=x*15;
                setImageG[x][0]=x*15;
                setImageB[x][0]=x*15;
            }

            int repeats=1;
            int colorGridX=0;
            int colorGridY=0;
            for(int blue=0; blue<12; blue++){
                for(int red=0; red<12; red++){
                    for(int green=0; green<12; green++){
                        Color currentColor = new Color(red*20,green*20,blue*20);
                        g2.setColor(currentColor);
                        if(colorGridY<12){
                            g2.fillRect(colorGridX*width+xPos+colorOffsetX+(width*24),(colorGridY*length+yPos)+colorOffsetY-(length*11),width,length);
                        }
                        repeats += 1;
                        colorGridX++;
                        if(colorGridX>=12){
                            colorGridX=0;
                            colorGridY++;
                        }
                    }
                }
            }

            repeats=0;
            colorGridX=0;
            colorGridY=0;
            for(int red=0; red<12; red++){
                for(int green=0; green<12; green++){
                    for(int blue=0; blue<12; blue++){
                        Color currentColor = new Color(red*20,green*20,blue*20);
                        g2.setColor(currentColor);
                        if(colorGridY<12){
                            g2.fillRect(colorGridX*width+xPos+colorOffsetX+(width*2),(colorGridY*length+yPos)+colorOffsetY+(length*11),width,length);
                        }
                        repeats += 1;
                        colorGridX++;
                        if(colorGridX>=12){
                            colorGridX=0;
                            colorGridY++;
                        }
                    }
                }
            }

            repeats=0;
            colorGridX=0;
            colorGridY=0;

            for(int green=0; green<12; green++){
                for(int blue=0; blue<12; blue++){
                    for(int red=0; red<12; red++){
                        Color currentColor = new Color(red*20,green*20,blue*20);
                        g2.setColor(currentColor);
                        if(colorGridY<12){
                            g2.fillRect(colorGridX*width+xPos+colorOffsetX+(width*13),(colorGridY*length+yPos)+colorOffsetY,width,length);
                        }
                        repeats += 1;
                        colorGridX++;
                        if(colorGridX>=12){
                            colorGridX=0;
                            colorGridY++;
                        }
                    }
                }
            }
            // Red
            setImageR[18][0] = 255;
            setImageG[18][0] = 0;
            setImageB[18][0] = 0;

            // Green
            setImageR[19][0] = 0;
            setImageG[19][0] = 255;
            setImageB[19][0] = 0;

            // Blue
            setImageR[20][0] = 0;
            setImageG[20][0] = 0;
            setImageB[20][0] = 255;
        }
    }
    public void mouseColor() throws Exception{
        PointerInfo pointer;

        pointer = MouseInfo.getPointerInfo();

        Point coord = pointer.getLocation();

        Robot robot = new Robot();
        robot.delay(10);
        coord = MouseInfo.getPointerInfo().getLocation();

        coord = MouseInfo.getPointerInfo().getLocation();
        System.out.println("Coord = " + coord);

        //print colors
        Color color = robot.getPixelColor((int)coord.getX(), (int)coord.getY());
        System.out.println("Color = " + color);

        setImageR[gridx+1][0] = color.getRed();
        setImageG[gridx+1][0] = color.getGreen();
        setImageB[gridx+1][0] = color.getBlue();
        panel.repaint();
    }

    public void mouseExited(MouseEvent e) {System.out.println("exit");}

    public void mouseEntered(MouseEvent e) {
        // for (int y=0; y<gridy+2; y++){
        // for(int x = 0; x<gridx+2; x++){ // this sets all grids to empty
        // setImageR[x][y] = 0;
        // setImageG[x][y] = 0;
        // setImageB[x][y] = 0;
        // }
        // }

        System.out.println("entered");
    }

    public void mouseReleased(MouseEvent e) {System.out.println("released");}

    public void mouseClicked(MouseEvent e) {System.out.println("clicked");}

    public void mousePressed(MouseEvent e) {
        if(e.getX()/30<=gridx && e.getY()/30<=gridy){
            mousex=Math.round(e.getX()/width)-1; // current mouse X pos
            mousey=Math.round(e.getY()/length)-4; // current mouse Y pos
        }
        //System.out.println("clicked at "+mousex+", "+mousey); // prints out the location of the mouse click.
        // start x pos, end x pos, start y pos, end y pos, and hex color (0 black to 255 white.). positions go up by 1 for each tile.
        if(mousey==0){
            if(mousex<18){
                setImageR[gridx+1][0] = mousex*15;
                setImageG[gridx+1][0] = mousex*15;
                setImageB[gridx+1][0] = mousex*15;
            } 
            if(mousex==18){
                setImageR[gridx+1][0] = 255;
                setImageG[gridx+1][0] = 0;
                setImageB[gridx+1][0] = 0;
            }
            if(mousex==19){
                setImageR[gridx+1][0] = 0;
                setImageG[gridx+1][0] = 255;
                setImageB[gridx+1][0] = 0;
            }
            if(mousex==20){
                setImageR[gridx+1][0] = 0;
                setImageG[gridx+1][0] = 0;
                setImageB[gridx+1][0] = 255;
            }
        }
        setImageR[mousex][mousey] = setImageR[gridx+1][0];
        setImageG[mousex][mousey] = setImageG[gridx+1][0];
        setImageB[mousex][mousey] = setImageB[gridx+1][0];
        lastX.add(mousex);
        lastY.add(mousey);
        //lastX[lastX.length] += mousex;
        //lastY[lastY.length] += mousey;
        for(int i=0; i<lastX.size(); i++){

            System.out.println("X"+lastX.get(i));
            System.out.println("Y"+lastY.get(i));
        }

        //System.out.println(colorR+", "+colorG+", "+colorB);
        //reblur();
        panel.repaint();
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

                setImageR[lastX.get(lastX.size()-1)][lastY.get(lastY.size()-1)]=0; 
                setImageG[lastX.get(lastX.size()-1)][lastY.get(lastY.size()-1)]=0; 
                setImageB[lastX.get(lastX.size()-1)][lastY.get(lastY.size()-1)]=0; 
                lastX.remove(lastX.size()-1);
                lastY.remove(lastY.size()-1);
                //reblur(); 
                panel.repaint(); // moves player up
                break;
            case "Find Color":
                try
                {
                    mouseColor();
                }
                catch (Exception f)
                {
                    f.printStackTrace();
                }
                break;
            case "Reset":
                for (int y=0; y<gridy+2; y++){
                    for(int x = 0; x<gridx+2; x++){ // this sets all grids to empty
                        setImageR[x][y] = 0;
                        setImageG[x][y] = 0;
                        setImageB[x][y] = 0;
                    }
                }
                panel.repaint();
                break;
            default: break;
        }
    }

    // public void paint (Graphics g){
    // super.paint(g);

    // }

    // public void createBox(int xStart, int xEnd, int yStart, int yEnd, int hexColor){
    // for(int y=yStart; y<yEnd; y++){
    // for(int x=xStart; x<xEnd; x++){
    // setImage[x][y] = hexColor;
    // }
    // }
    // }

    public ultimateBlurringVersion14(){
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
        menuItem=new JMenuItem("Reset");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu = new JMenu("Edit");
        menuBar.add(menu);
        menuItem=new JMenuItem("Undo");
        menuItem.addActionListener(this);
        menuItem.setAccelerator(KeyStroke.getKeyStroke('z'));
        menu.add(menuItem);

        menuItem=new JMenuItem("Find Color");
        menuItem.addActionListener(this);
        menuItem.setAccelerator(KeyStroke.getKeyStroke('e'));
        menu.add(menuItem);

        addMouseListener(this);

        add(panel);

        this.getContentPane().setPreferredSize(new Dimension(1500,1080));
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

        //panel.repaint();
        for (int y=0; y<gridy; y++){
            for(int x = 0; x<gridx; x++){ 
                System.out.print(finalImage[x][y]+" ");
            }
            System.out.println();
        }
    }
}
