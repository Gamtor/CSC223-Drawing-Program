/**
 * A drawing program that lets you draw, select color, undo, save and load.
 *
 * Tishar Sreekantam
 * Version 23
 */

// These packages are for the GUI, Arrays, Colors, and Mouse position + input.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.MouseInfo;
import java.awt.Color;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter; 
public class ultimateBlurringVersion extends JFrame implements ActionListener,MouseListener{
    public final DrawingPanel panel = new DrawingPanel(); // Used for using a JPanel inside the JFrame

    // Pixel size
    int width=16;
    int length=16;

    // Grid size and grid offset used for grid size and position.
    int gridx = 50;
    int gridy = 40;

    int gridXOffset=width/2;
    int gridYOffset=10;

    //int colorR=0;
    //int colorG=0;
    //int colorB=0;

    int[][] setImageR = new int[gridx+2][gridy+2];
    int[][] setImageG = new int[gridx+2][gridy+2];
    int[][] setImageB = new int[gridx+2][gridy+2];

    //Positions of the color grids
    int colorOffsetX=850;
    int colorOffsetY=300;

    //Used for setting pixel color
    Color currentColor = new Color(0,0,0);

    // Used for getting a list of all the places the user has placed, which is used for undo. 
    ArrayList<Integer> lastX = new ArrayList<>();
    ArrayList<Integer> lastY = new ArrayList<>();
    List<Point> undoPositions = new ArrayList<>();

    private void clickRegister(MouseEvent e){
        if(SwingUtilities.isLeftMouseButton(e)){
            int mousex=0;
            int mousey=0;
            if(e.getX()/30<=gridx && e.getY()/30<=gridy){ // Checks if the mouse is inside the grid.
                mousex=Math.round(e.getX()/width)-1; // current mouse X pos. These have a constant due to menus offsetting the canvas slightly.
                mousey=Math.round(e.getY()/length)-4; // current mouse Y pos

            }
            //System.out.println("clicked at "+mousex+", "+mousey); // prints out the location of the mouse click.

            if (lastX.isEmpty() || lastX.get(lastX.size() - 1) != mousex || lastY.get(lastY.size() - 1) != mousey) { // Checks if the mouse is inside the grid
                Point p = new Point(mousex, mousey);

                setImageR[mousex][mousey] = setImageR[gridx+1][0];
                setImageG[mousex][mousey] = setImageG[gridx+1][0];
                setImageB[mousex][mousey] = setImageB[gridx+1][0];

                lastX.add(mousex);
                lastY.add(mousey);
                if(!undoPositions.contains(p)){
                    undoPositions.add(p);
                }
            }
        }
        if(SwingUtilities.isRightMouseButton(e)){
            try
            {
                mouseColor();
            }
            catch (Exception f)
            {
                f.printStackTrace();
            }
        }
    }

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
                    // start x pos, end x pos, start y pos, end y pos, and hex color (0 black to 255 white.). positions go up by 1 for each tile.
                    g2.fillRect(x*width+xPos+gridXOffset,y*length+yPos+gridYOffset,width,length);
                }
            }

            //These are given so the user can pick which shade to draw with.
            for(int x=0; x<18; x++){
                setImageR[x][0]=x*15;
                setImageG[x][0]=x*15;
                setImageB[x][0]=x*15;
            }

            // These are for giving the user access to all the colors in the RGB
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

    public void mousePressed(MouseEvent e){
        clickRegister(e);
        panel.repaint();

    }

    public void mouseExited(MouseEvent e) {System.out.println("exit");}

    public void mouseEntered(MouseEvent e) {System.out.println("entered");}

    public void mouseReleased(MouseEvent e) {System.out.println("released");}

    public void mouseClicked(MouseEvent e) {System.out.println("clicked");}

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
                    try{ // This is to allow for the undo to work correctly.
                        undoPositions.remove(lastY.size()-1); // This works perfectly fine but throws errors anyway.
                    }catch(Exception d){
                        d.printStackTrace();
                    }
                    panel.repaint();
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
                setImageR[gridx+1][0] = (int)(Math.random() * 256);
                setImageG[gridx+1][0] = (int)(Math.random() * 256);
                setImageB[gridx+1][0] = (int)(Math.random() * 256);
                panel.repaint();
                undoPositions.clear();
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
                        if(loadCode[x][y]<255 && loadCode[x][y]>=0){
                            setImageR[x][y] = (int) Math.round(loadCode[x][y]);
                            setImageG[x][y] = (int) Math.round(loadCode[x][y]);
                            setImageB[x][y] = (int) Math.round(loadCode[x][y]);
                        } else {
                            setImageR[x][y] = 0;
                            setImageG[x][y] = 0;
                            setImageB[x][y] = 0;
                            System.out.println("Code contained an invalid value. Was the code saved correctly?");
                        }
                    }
                }
                panel.repaint();
                break;
            default: break;
        }
    }

    public ultimateBlurringVersion(){
        setTitle("Drawing Program by Tishar");

        setImageR[gridx+1][0] = (int)(Math.random() * 256);
        setImageG[gridx+1][0] = (int)(Math.random() * 256);
        setImageB[gridx+1][0] = (int)(Math.random() * 256);

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

        menu = new JMenu("Share");
        menuBar.add(menu);
        menuItem=new JMenuItem("Save");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem=new JMenuItem("Load");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu = new JMenu("Help");
        menuBar.add(menu);
        menu.add(new JLabel("Welcome to my drawing program!"));
        menu.add(new JLabel("Left click to draw, right click to select color"));
        menu.add(new JLabel("Click 'Z' on your keyboard to undo"));
        menu.add(new JLabel("'File->Reset' Resets the board"));
        menu.add(new JLabel("If you really like your drawing, You can share it!"));
        menu.add(new JLabel("Click on 'Share->Save' to recieve a save code. Copy that code and to share it."));
        menu.add(new JLabel("To load it, click 'Share->Load'. Then paste in the recieved code into it to load the drawing"));
        menu.add(new JLabel("(Note: The drawings are saved in Greyscale. Top right of the grid is the selected color.)"));

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
                    clickRegister(e);
                    panel.repaint();
                }
            });
    }
}