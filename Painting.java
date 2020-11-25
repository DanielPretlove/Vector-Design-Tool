package CAB302_Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle all operations on the GUI's canvas
 */
public class Painting extends JFrame implements MouseListener, MouseWheelListener{
    /**
     * Variables to be passed into the Painting class using this class.
     */
    public List<java.awt.Shape> shapes = new ArrayList<>();
    public List<Color> colors = new ArrayList<>();
    public List<Color> colorsFill = new ArrayList<>();

    /**
     * Global variables controlling operations of the class's methods.
     */
    public Color FillColor = null;
    public Color LineColor;
    public JPanel canvas = new Panel();
    public enum Shape {ELLIPSE, LINE, PLOT, POLYGON, RECTANGLE, DEFAULT}
    public Shape currentShape = Shape.DEFAULT;
    public int mouseState = 0;
    public int StartX = 0;
    public int StartY = 0;
    public List<Integer> polyX = new ArrayList<>();
    public List<Integer> polyY = new ArrayList<>();
    public boolean fillShapes = false;
    public List<String> VECText = new ArrayList<>();
    private DecimalFormat dF = new DecimalFormat("#0.0000");

    /**
     * Method to draw a line shape and add variables to global lists.
     * @param x1 Starting x coord
     * @param y1 Starting y coord
     * @param x2 End x coord
     * @param y2 End y coord
     */
    public void Line(int x1, int y1, int x2, int y2)
    {
        Line2D shape = new Line2D.Double(x1, y1, x2, y2);
        colorsFill.add(null);
        shapes.add(shape);
        colors.add(LineColor);
        String text = "LINE "+dF.format((float)x1/canvas.getHeight())+" "+dF.format((float)y1/canvas.getHeight())
                +" "+dF.format((float)x2/canvas.getHeight())+" "+dF.format((float)y2/canvas.getHeight());
        VECText.add(text);
        canvas.repaint();
    }

    /**
     * Method to draw a line shape and add variables to global lists.
     * @param x1 x coord
     * @param y1 y coord
     */
    public void Plot(int x1, int y1)
    {
        Line2D shape = new Line2D.Double(x1, y1, x1, y1);
        colorsFill.add(null);
        shapes.add(shape);
        colors.add(LineColor);
        String text = "PLOT "+dF.format((float)x1/canvas.getHeight())+" "+dF.format((float)y1/canvas.getHeight());
        VECText.add(text);
        canvas.repaint();
    }

    /**
     * Method to draw a line shape and add variables to global lists.
     * @param x Starting x coord
     * @param y Starting y coord
     * @param width
     * @param height
     */
    public void Rectangle(int x, int y, int width, int height)
    {
        Rectangle2D shape = new Rectangle2D.Double(x,y,width,height);
        if(fillShapes){
            colorsFill.add(FillColor);
        }
        else{
            colorsFill.add(null);
        }
        shapes.add(shape);
        colors.add(LineColor);
        String text = "RECTANGLE "+dF.format((float)x/canvas.getHeight())+" "+dF.format((float)y/canvas.getHeight())
                +" "+dF.format((float)(x+width)/canvas.getHeight())+" "+dF.format((float)(y+height)/canvas.getHeight());
        VECText.add(text);
        canvas.repaint();
    }

    /**
     * Method to draw a line shape and add variables to global lists.
     * @param x Starting x coord
     * @param y Starting y coord
     * @param width
     * @param height
     */
    public void Ellipse(int x, int y, int width, int height)
    {
        Ellipse2D shape = new Ellipse2D.Double(x,y,width,height);
        if(fillShapes){
            colorsFill.add(FillColor);
        }
        else{
            colorsFill.add(null);
        }
        shapes.add(shape);
        colors.add(LineColor);
        String text = "ELLIPSE "+dF.format((float)x/canvas.getHeight())+" "+dF.format((float)y/canvas.getHeight())
                +" "+dF.format((float)(x+width)/canvas.getHeight())+" "+dF.format((float)(y+height)/canvas.getHeight());
        VECText.add(text);
        canvas.repaint();
    }

    /**
     * Method to draw a line shape and add variables to global lists.
     * @param xPoints Array of x coords
     * @param yPoints Array of y coords
     * @param nPoints number of coords
     */
    public void Polygon(int[] xPoints, int[] yPoints, int nPoints)
    {
        Polygon shape = new Polygon(xPoints,yPoints,nPoints);
        if(fillShapes){
            colorsFill.add(FillColor);
        }
        else{
            colorsFill.add(null);
        }
        shapes.add(shape);
        colors.add(LineColor);
        String text = "POLYGON";
        for(int i = 0;i < xPoints.length; i++){
            String points = " "+dF.format((float)xPoints[i]/canvas.getHeight())+" "+dF.format((float)yPoints[i]/canvas.getHeight());
            text = text+points;
        }
        VECText.add(text);
        canvas.repaint();
    }

    /**
     *  Reads Vec file and passes to VECHandler to return variables to global lists.
     * @param file Vec file from selection dialogue.
     */
    public void OpenVec(File file){
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        VECHandler VECReader = new VECHandler();
        VECReader.Read(contentBuilder.toString(), canvas.getSize());
        shapes.addAll(VECReader.shapes);
        colors.addAll(VECReader.colors);
        colorsFill.addAll(VECReader.colorsFill);
        VECText.addAll(VECReader.VecText);
        canvas.repaint();
    }

    /**
     * @return Returns all text needed for VEC file.
     */
    public List<String> SaveVec(){
        return VECText;
    }

    /**
     * Adds mouse listener to the canvas.
     */
    public void AddMouseListeners()
    {
        canvas.addMouseListener(this);
    }




    /**
     * Triggers respective shape operations when mouse clicks canvas.
     * @param e The details of the mouse clicks.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(currentShape){
            case PLOT:
                Plot(e.getX(), e.getY());
                break;
            case POLYGON:
                if(mouseState == 0){
                    StartX = e.getX();
                    StartY = e.getY();
                    Plot(e.getX(), e.getY());
                    polyX.clear();
                    polyY.clear();
                    polyX.add(StartX);
                    polyY.add(StartY);
                    mouseState = 2;
                }
                else if(mouseState == 2){
                    if( Math.abs(e.getX()-StartX) < 5 && Math.abs(e.getY()-StartY) < 5){
                        //Finish Shape
                        Polygon(toIntArray(polyX),toIntArray(polyY),polyX.size());
                        mouseState = 0;
                    }
                    else{
                        polyX.add(e.getX());
                        polyY.add(e.getY());
                    }
                }
                else{mouseState = 0;}
                break;
            case DEFAULT:
                break;
        }
    }

    /**
     * Triggers respective shape operations when mouse is pressed.
     * @param e The details of the mouse clicks.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(currentShape != Shape.POLYGON) {
            if(currentShape != Shape.PLOT){
                StartX = e.getX();
                StartY = e.getY();
            }
            else{
                Plot(e.getX(), e.getY());
            }
        }
        else{
            if(mouseState == 0){
                StartX = e.getX();
                StartY = e.getY();
                Plot(e.getX(), e.getY());
                polyX.clear();
                polyY.clear();
                polyX.add(StartX);
                polyY.add(StartY);
                mouseState = 2;
            }
            else if(mouseState == 2){
                if( Math.abs(e.getX()-StartX) < 5 && Math.abs(e.getY()-StartY) < 5){
                    //Finish Shape
                    Polygon(toIntArray(polyX),toIntArray(polyY),polyX.size());
                    mouseState = 0;
                }
                else{
                    polyX.add(e.getX());
                    polyY.add(e.getY());
                }
            }
            else{mouseState = 0;}
        }
    }

    /**
     * Triggers respective shape operations when mouse is released.
     * @param e The details of the mouse clicks.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch(currentShape) {
            case LINE:
                Line(StartX,StartY,e.getX(),e.getY());
                break;
            case ELLIPSE:
                Ellipse(Math.min(StartX,e.getX()), Math.min(StartY,e.getY()), Math.abs(e.getX()-StartX), Math.abs(e.getY()-StartY));
                break;
            case RECTANGLE:
                Rectangle(Math.min(StartX,e.getX()), Math.min(StartY,e.getY()), Math.abs(e.getX()-StartX), Math.abs(e.getY()-StartY));
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }




    /**
     * Method to change a list into an array.
     * @param list List to be changed.
     * @return Array containing the elements of the list.
     */
    int[] toIntArray(List<Integer> list){
        int[] ret = new int[list.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = list.get(i);
        return ret;
    }

    /**
     * Panel class extends Jpanel so that paint method could be rewritten.
     */
    private class Panel extends JPanel {

        /**
         * Override of Paint method to uses the shapes and colours from global lists.
         * @param g graphic object to be painted on.
         */
        @Override
        public void paint(final Graphics g) {

            super.paint(g);

            Graphics2D g2d = (Graphics2D) g;
            for (int i = 0;i < shapes.size();i++) {
                if(colorsFill.get(i) != null){
                    g2d.setColor(colorsFill.get(i));
                    g2d.fill(shapes.get(i));
                }
                g2d.setColor(colors.get(i));
                g2d.draw(shapes.get(i));// or g2d.fill(shape) to have its interior filled
            }
        }

    }
}