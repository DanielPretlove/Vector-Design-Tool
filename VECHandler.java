package CAB302_Assignment2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to Read VEC files into Painting class
 */
public class VECHandler {
    /**
     * Variables to be passed into the Painting class using this class.
     */
    public List<java.awt.Shape> shapes = new ArrayList<>();
    public List<Color> colors = new ArrayList<>();
    public List<Color> colorsFill = new ArrayList<>();
    public List<String> VecText = new ArrayList<>();

    /**
     * Variables used to assign active color when shape is drawn
     */
    private Color LineColor = Color.black;
    private Color FillColor = null;
    /**
     * Variable with dimensions of canvas.
     */
    public Dimension dimension;

    /**
     * Method to split all lines of VEC file and individually implement them to the LineReader method.
     *
     * @param text String Containing all text from VEC file.
     * @param dimension Dimensions of active canvas.
     */
    public void Read(String text, Dimension dimension){
        String[] lines = text.split("\n");
        this.dimension = dimension;
        for (String line: lines) {
            LineReader(line);
        }

    }

    /**
     * Method that processes a line of text and adds variables to global list variables.
     * Or Changes active colour.
     * @param line String containing the line of text being processed.
     */
    private void LineReader(String line){
        VecText.add(line);
        String[] figures = line.split(" ");
        if(figures[0].matches("LINE")){
            Line2D shape = new Line2D.Double(Double.parseDouble(figures[1])*dimension.height, Double.parseDouble(figures[2])*dimension.height,
                    Double.parseDouble(figures[3])*dimension.height, Double.parseDouble(figures[4])*dimension.height);
            colorsFill.add(null);
            shapes.add(shape);
            colors.add(LineColor);
        }
        else if(figures[0].matches("PLOT")){
            Line2D shape = new Line2D.Double(Double.parseDouble(figures[1])*dimension.height, Double.parseDouble(figures[2])*dimension.height,
                    Double.parseDouble(figures[1])*dimension.height, Double.parseDouble(figures[2])*dimension.height);
            colorsFill.add(null);
            shapes.add(shape);
            colors.add(LineColor);
        }
        else if(figures[0].matches("RECTANGLE")){
            Rectangle2D shape = new Rectangle2D.Double(Math.min(Double.parseDouble(figures[1]),Double.parseDouble(figures[3]))*dimension.height,
                    Math.min(Double.parseDouble(figures[2]),Double.parseDouble(figures[4]))*dimension.height,
                    Math.abs(Double.parseDouble(figures[1])-Double.parseDouble(figures[3]))*dimension.height,
                    Math.abs(Double.parseDouble(figures[2])-Double.parseDouble(figures[4]))*dimension.height);
            colorsFill.add(FillColor);
            shapes.add(shape);
            colors.add(LineColor);
        }
        else if(figures[0].matches("ELLIPSE")){
            Ellipse2D shape = new Ellipse2D.Double(Math.min(Double.parseDouble(figures[1]),Double.parseDouble(figures[3]))*dimension.height,
                    Math.min(Double.parseDouble(figures[2]),Double.parseDouble(figures[4]))*dimension.height,
                    Math.abs(Double.parseDouble(figures[1])-Double.parseDouble(figures[3]))*dimension.height,
                    Math.abs(Double.parseDouble(figures[2])-Double.parseDouble(figures[4]))*dimension.height);
            colorsFill.add(FillColor);
            shapes.add(shape);
            colors.add(LineColor);
            System.out.println(FillColor);
        }
        else if(figures[0].matches("POLYGON")){
            int[] xPoints;
            List<Integer> polyX = new ArrayList<>();
            List<Integer> polyY = new ArrayList<>();

            for(int i = 1;i < figures.length; i += 2){
                polyX.add(Integer.parseInt(figures[i])*dimension.height);
                polyY.add(Integer.parseInt(figures[i+1])*dimension.height);
            }

            Polygon shape = new Polygon(toIntArray(polyX),toIntArray(polyY),polyX.size());
            colorsFill.add(FillColor);
            shapes.add(shape);
            //colors.add(LineColor);
        }
        else if(figures[0].matches("PEN")){
            LineColor = Color.decode(figures[1]);
            System.out.println(LineColor);
        }
        else if(figures[0].matches("FILL")){
            FillColor = Color.decode(figures[1]);
            if(figures[1].matches("OFF")){
                FillColor = null;
            }
        }
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
}
