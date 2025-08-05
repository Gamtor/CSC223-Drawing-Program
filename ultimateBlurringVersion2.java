
/**
 * Gets an image of black and white, then blurs it
 *
 * Tishar
 * Version 2
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
public class ultimateBlurringVersion2 extends JFrame implements ActionListener,MouseListener{
    int gridx = 30;
    int gridy = 16;

    int mousex=0;
    int mousey=0;

    int[][] setImage = new int[gridx+2][gridy+2];
    int[][] finalImage = new int[gridx][gridy];
    int width=30;
    int length=30;
    
    int colorSwitch=255;

    // Color currentColor = new Color(0,0,0);

    public void mouseExited(MouseEvent e) {System.out.println("exit");}

    public void mouseEntered(MouseEvent e) {
        for (int y=0; y<gridy+2; y++){
            for(int x = 0; x<gridx+2; x++){ // this sets all grids to empty
                setImage[x][y] = 0;
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
        if(mousey==1 && mousex<18){
            colorSwitch = mousex*15;
            
        }
        setImage[mousex][mousey] = colorSwitch;
        System.out.println(colorSwitch);
        reblur();
        repaint();
    }

    public void reblur(){
        for (int y=1; y<gridy; y++){
            for(int x = 1; x<gridx; x++){ 
                int pixelAverage = Math.round((setImage[x-1][y+1]+setImage[x][y+1]+setImage[x+1][y+1]) + (setImage[x-1][y]+setImage[x][y]+setImage[x+1][y]) + (setImage[x-1][y-1]+setImage[x][y-1]+setImage[x+1][y-1]));
                finalImage[x][y] = pixelAverage / 9;
            }
        }
        for(int x=0; x<18; x++){
            setImage[x][1]=x*15;
            //System.out.println(Math.round(255/x));
        }
    }

    public void actionPerformed(ActionEvent e){ // runs when an actionlistener is called.
        System.out.println(e);
        String cmd=e.getActionCommand();
        switch(cmd){ // When this item is selected, do this.
                // if the action is this, run this
            case "Quit": System.exit(0); // exits program
            case "Undo": setImage[mousex][mousey]=0; reblur(); repaint(); // moves player up
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
                //Color currentColor = new Color(setImage[x][y],setImage[x][y],setImage[x][y]);
                Color currentColor = new Color(setImage[x][y],setImage[x][y],setImage[x][y]);
                g2.setColor(currentColor);
                g2.fillRect(x*width+xPos,y*length+yPos,width,length);
            }
        }

        for(int y=1; y<gridy; y++){
            for(int x=1; x<gridx; x++){
                Color currentColor = new Color(finalImage[x][y],finalImage[x][y],finalImage[x][y]);
                g2.setColor(currentColor);
                g2.fillRect(x*width+xPos,(y*length+yPos)+600,width,length);
            }
        }
    }

    public void createBox(int xStart, int xEnd, int yStart, int yEnd, int hexColor){
        for(int y=yStart; y<yEnd; y++){
            for(int x=xStart; x<xEnd; x++){
                setImage[x][y] = hexColor;
            }
        }
    }

    public ultimateBlurringVersion2(){
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

        for (int y=0; y<gridy+2; y++){
            for(int x = 0; x<gridx+2; x++){ 
                System.out.print(setImage[x][y]+" ");
            }
            System.out.println();
        }
        System.out.println("\n\n");

        for (int y=1; y<gridy; y++){
            for(int x = 1; x<gridx; x++){ 
                int pixelAverage = Math.round((setImage[x-1][y+1]+setImage[x][y+1]+setImage[x+1][y+1]) + (setImage[x-1][y]+setImage[x][y]+setImage[x+1][y]) + (setImage[x-1][y-1]+setImage[x][y-1]+setImage[x+1][y-1]));
                finalImage[x][y] = pixelAverage / 9;
            }
        }

        repaint();
        for (int y=0; y<gridy; y++){
            for(int x = 0; x<gridx; x++){ 
                System.out.print(finalImage[x][y]+" ");
            }
            System.out.println();
        }
    }
}
