/**
 * Code cleaned up.
 *
 * Tishar
 * Version 21
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
import java.lang.*;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter; 
public class ultimateBlurringVersion21 extends JFrame implements ActionListener,MouseListener{
    int gridx = 50;
    int gridy = 40;

    public final DrawingPanel panel = new DrawingPanel();

    int mousex=0;
    int mousey=0;

    ArrayList<Integer> lastX = new ArrayList<>();
    ArrayList<Integer> lastY = new ArrayList<>();

    //int[][] setImage = new int[gridx+2][gridy+2]; Used for the blurring code
    int[][] setImageR = new int[gridx+2][gridy+2];
    int[][] setImageG = new int[gridx+2][gridy+2];
    int[][] setImageB = new int[gridx+2][gridy+2];
    int[][] finalImage = new int[gridx][gridy];

    int width=16;
    int length=16;

    int gridXOffset=width/2;
    int gridYOffset=0;

    int colorR=0;
    int colorG=0;
    int colorB=0;

    int colorOffsetX=850;
    int colorOffsetY=300;

    Color currentColor = new Color(0,0,0);
    void createDialog(String title, String text){ // Method to create a dialog box
        JDialog box = new JDialog(this); // creates a dialog box
        box.setBounds(400,400,800,120); // dialog box size

        // adds an area to show text in the dialog box
        TextArea area = new TextArea(text); // sets text to passed string
        box.add(area);
        box.setTitle(title); // sets title to passed string

        //Shows the Dialog box.
        box.toFront();
        box.setVisible(true);

        area.setEditable(false); // Makes it so the dialog box text isn't editable by the user.
    }
    public class DrawingPanel extends JPanel{ // implements MouseMotionListener{

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

    public void mouseEntered(MouseEvent e) {System.out.println("entered");}

    public void mouseReleased(MouseEvent e) {System.out.println("released");}

    public void mouseClicked(MouseEvent e) {System.out.println("clicked");}

    public void mouseDragged(MouseEvent e){
        System.out.println("dragged");
    }

    public void mousePressed(MouseEvent e) {
        if(e.getX()/30<=gridx && e.getY()/30<=gridy){
            mousex=Math.round(e.getX()/width)-1; // current mouse X pos
            mousey=Math.round(e.getY()/length)-3; // current mouse Y pos

        }
        //System.out.println("clicked at "+mousex+", "+mousey); // prints out the location of the mouse click.
        // start x pos, end x pos, start y pos, end y pos, and hex color (0 black to 255 white.). positions go up by 1 for each tile.
        
        setImageR[mousex][mousey] = setImageR[gridx+1][0];
        setImageG[mousex][mousey] = setImageG[gridx+1][0];
        setImageB[mousex][mousey] = setImageB[gridx+1][0];
        lastX.add(mousex);
        lastY.add(mousey);

        panel.repaint();
    }

    public void actionPerformed(ActionEvent e){ // runs when an actionlistener is called.
        System.out.println(e);
        String cmd=e.getActionCommand();
        switch(cmd){ // When this item is selected, do this.
                // if the action is this, run this
            case "Quit": System.exit(0); // exits program
            case "Undo":
                if(lastX.size()>0){
                    setImageR[lastX.get(lastX.size()-1)][lastY.get(lastY.size()-1)]=0;
                    setImageG[lastX.get(lastX.size()-1)][lastY.get(lastY.size()-1)]=0;
                    setImageB[lastX.get(lastX.size()-1)][lastY.get(lastY.size()-1)]=0;
                    lastX.remove(lastX.size()-1);
                    lastY.remove(lastY.size()-1);

                    panel.repaint(); // moves player up
                }
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

            case "Save":
                ArrayList<Double> saveCode = new ArrayList<>();
                saveCode.clear();
                for (int y=0; y<gridy+2; y++){
                    for(int x = 0; x<gridx+2; x++){ // saves the drawing in greyscale
                        double save = (setImageR[x][y]*0.299) + (setImageG[x][y]*0.587) + (setImageB[x][y]*0.114);
                        saveCode.add(save);
                    }
                }
                createDialog("Drawing code! Share or save for later! (Click inside the box and then click Ctrl+A to select all then press Ctrl+C to copy!)",""+saveCode);
                break;

            case "Load":
                String[] stringToInt = new String[(gridx+2)*(gridy+2)];
                float[][] loadCode = new float[gridx+2][gridy+2];
                String num = JOptionPane.showInputDialog(null, "Load Code");
                stringToInt = num.replace("[", "").split(",");

                int counter=0;
                for (int y=0; y<gridy+2; y++){
                    for(int x = 0; x<gridx+2; x++){ // this sets all grids to empty
                        //System.out.println(Math.round(Float.parseFloat(stringToInt[counter].replace("]", ""))));
                        try{
                            loadCode[x][y] = Math.round(Float.parseFloat(stringToInt[counter].replace("]", "")));
                        } catch(NumberFormatException ex){ // handle your exception
                            ex.printStackTrace();
                        }
                        counter++;
                    }
                }

                for(int j=0; j<loadCode.length; j++){
                    System.out.println(loadCode[j]);
                }

                for (int y=0; y<gridy+2; y++){
                    for(int x = 0; x<gridx+2; x++){ // loads the save.
                        setImageR[x][y] = (int) Math.round(loadCode[x][y]);
                        setImageG[x][y] = (int) Math.round(loadCode[x][y]);
                        setImageB[x][y] = (int) Math.round(loadCode[x][y]);
                    }
                }
                panel.repaint();
                break;
            default: break;
        }
    }

    public ultimateBlurringVersion21(){
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

        menu = new JMenu("Share");
        menuBar.add(menu);
        menuItem=new JMenuItem("Save");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem=new JMenuItem("Load");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        addMouseListener(this);
        add(panel);
        this.getContentPane().setPreferredSize(new Dimension(1515,1105));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();
        this.toFront();
        this.setVisible(true);

        panel.repaint();
        this.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if(e.getX()/30<=gridx && e.getY()/30<=gridy){
                        mousex=Math.round(e.getX()/width)-1; // current mouse X pos
                        mousey=Math.round(e.getY()/length)-3; // current mouse Y pos

                    }
                    //System.out.println("clicked at "+mousex+", "+mousey); // prints out the location of the mouse click.
                    // start x pos, end x pos, start y pos, end y pos, and hex color (0 black to 255 white.). positions go up by 1 for each tile.
                    setImageR[mousex][mousey] = setImageR[gridx+1][0];
                    setImageG[mousex][mousey] = setImageG[gridx+1][0];
                    setImageB[mousex][mousey] = setImageB[gridx+1][0];
                    lastX.add(mousex);
                    lastY.add(mousey);

                    panel.repaint();
                }
            });

        for (int y=0; y<gridy; y++){
            for(int x = 0; x<gridx; x++){
                System.out.print(finalImage[x][y]+" ");
            }
            System.out.println();
        }
    }
}
