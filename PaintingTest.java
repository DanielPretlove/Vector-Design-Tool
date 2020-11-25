package CAB302_Assignment2;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaintingTest
{
    // Create Global Variables
    private Painting canvas = new Painting();
    private DecimalFormat dF = new DecimalFormat("#0.0000");

    @Test
    public void TestLine()
    {
        //Run Method
        canvas.Line(1,1,5,5);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Line2D.Double(1,1,5,5).getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),null);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),null);

        //Test File Text
        String text = "LINE "+dF.format((float)1/canvas.getHeight())+" "+dF.format((float)1/canvas.getHeight())
                +" "+dF.format((float)5/canvas.getHeight())+" "+dF.format((float)5/canvas.getHeight());
        assertEquals(canvas.VECText.get(0),text);
    }

    @Test
    public void TestPlot()
    {
        //Run Method
        canvas.Plot(1,1);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Line2D.Double(1,1,5,5).getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),null);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),null);

        //Test File Text
        String text = "PLOT "+dF.format((float)1/canvas.getHeight())+" "+dF.format((float)1/canvas.getHeight());
        assertEquals(canvas.VECText.get(0),text);
    }

    @Test
    public void TestRectangle()
    {
        //Run Method
        canvas.Rectangle(1,1,5,5);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Rectangle2D.Double(1,1,5,5).getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),null);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),null);

        //Test File Text
        String text = "RECTANGLE "+dF.format((float)1/canvas.getHeight())+" "+dF.format((float)1/canvas.getHeight())
                +" "+dF.format((float)(1+5)/canvas.getHeight())+" "+dF.format((float)(1+5)/canvas.getHeight());
        assertEquals(canvas.VECText.get(0),text);
    }

    @Test
    public void TestEllipse()
    {
        //Run Method
        canvas.Ellipse(1,1,5,5);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Ellipse2D.Double(1,1,5,5).getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),null);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),null);

        //Test File Text
        String text = "ELLIPSE "+dF.format((float)1/canvas.getHeight())+" "+dF.format((float)1/canvas.getHeight())
                +" "+dF.format((float)(1+5)/canvas.getHeight())+" "+dF.format((float)(1+5)/canvas.getHeight());
        assertEquals(canvas.VECText.get(0),text);
    }

    @Test
    public void TestPolygon()
    {
        //Run Method
        int[] xPoints = {1,2,3};
        int[] yPoints = {1,2,3};
        canvas.Polygon(xPoints,yPoints,3);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Polygon().getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),null);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),null);

        //Test File Text
        String text = "POLYGON";
        for(int i = 0;i < xPoints.length; i++){
            String points = " "+dF.format((float)xPoints[i]/canvas.getHeight())+" "+dF.format((float)yPoints[i]/canvas.getHeight());
            text = text+points;
        }
        assertEquals(canvas.VECText.get(0),text);
    }

    @Test
    public void TestColorFillOff()
    {
        //Run Method
        canvas.LineColor = Color.blue;
        canvas.FillColor = Color.GREEN;
        canvas.fillShapes = false;
        canvas.Rectangle(1,1,5,5);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Rectangle2D.Double(1,1,5,5).getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),Color.blue);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),null);

        //Test File Text
        String text = "RECTANGLE "+dF.format((float)1/canvas.getHeight())+" "+dF.format((float)1/canvas.getHeight())
                +" "+dF.format((float)(1+5)/canvas.getHeight())+" "+dF.format((float)(1+5)/canvas.getHeight());
        assertEquals(canvas.VECText.get(0),text);
    }

    @Test
    public void TestColorFillOn()
    {
        //Run Method
        canvas.LineColor = Color.blue;
        canvas.FillColor = Color.GREEN;
        canvas.fillShapes = true;
        canvas.Rectangle(1,1,5,5);

        //Test Shape
        assertEquals(canvas.shapes.get(0).getClass(),new Rectangle2D.Double(1,1,5,5).getClass());

        //Test Color
        assertEquals(canvas.colors.get(0),Color.blue);

        //Test Fill Color
        assertEquals(canvas.colorsFill.get(0),Color.GREEN);

        //Test File Text
        String text = "RECTANGLE "+dF.format((float)1/canvas.getHeight())+" "+dF.format((float)1/canvas.getHeight())
                +" "+dF.format((float)(1+5)/canvas.getHeight())+" "+dF.format((float)(1+5)/canvas.getHeight());
        assertEquals(canvas.VECText.get(0),text);
    }

}