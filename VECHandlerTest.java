package CAB302_Assignment2;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class VECHandlerTest {

    // Test create class
    @Test
    public void TestCreate()
    {
        VECHandler VH1 = new VECHandler();
        assertNotNull(VH1);
    }

    @Test
    public void TestRead()
    {
        String file = "RECTANGLE 0.1982 0.2564 0.6709 0.5891\n" +
                "LINE 0.3091 0.1000 0.1273 0.4891\n" +
                "LINE 0.7982 0.3927 0.5164 0.6945\n";

        VECHandler VECReader = new VECHandler();
        VECReader.Read(file, new Dimension(100,100));
        List<Shape> shapes = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        List<Color> colorsFill = new ArrayList<>();
        List<String> VecText = new ArrayList<>();
        assertEquals(VECReader.shapes.size(),3);
        colors.add(Color.BLACK);
        colors.add(Color.BLACK);
        colors.add(Color.BLACK);
        assertEquals(VECReader.colors,colors);
        colorsFill.add(null);
        colorsFill.add(null);
        colorsFill.add(null);
        assertEquals(VECReader.colorsFill,colorsFill);
        String Text = "";
        for(String line:VECReader.VecText){
            Text = Text+line+"\n";
        }
        assertEquals(Text,file);
    }

    @Test
    public void TestLineReadShape()
    {
        String file = "PEN #BBBBBB\n" +
                "FILL #BBBBBB\n" +
                "RECTANGLE 0.1982 0.2564 0.6709 0.5891\n";
        VECHandler VECReader = new VECHandler();
        VECReader.Read(file, new Dimension(100,100));
        List<Shape> shapes = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        List<Color> colorsFill = new ArrayList<>();
        List<String> VecText = new ArrayList<>();

        //Test Shape
        assertEquals(VECReader.shapes.get(0).getClass(),(new Rectangle2D.Double()).getClass());

        //Test Color
        colors.add(Color.decode("#BBBBBB"));
        assertEquals(VECReader.colors,colors);

        //Test Fill Color
        colorsFill.add(Color.decode("#BBBBBB"));
        assertEquals(VECReader.colorsFill,colorsFill);

        //Test File Text
        String Text = "";
        for(String line:VECReader.VecText){
            Text = Text+line+"\n";
        }
        assertEquals(Text,file);
    }

}
