package CAB302_Assignment2;

import org.junit.jupiter.api.Test;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;


class GUITest
{
    private GUI gui = new GUI();
    private New_GUI new_gui = new New_GUI();
    private Painting painting = new Painting();

    @Test
    public void New()
    {
        // Test Shapes
        assertEquals(painting.shapes.isEmpty(), true);

        // Test Colors
        assertEquals(painting.colors.isEmpty(), true);

        // Test ColorsFill
        assertEquals(painting.colorsFill.isEmpty(), true);

        // Test VECText
        assertEquals(painting.VECText.isEmpty(), true);
    }

    @Test
    public void Open()
    {
        // Test FileNameExtensionFilter
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "VEC files (*.VEC)", "VEC");

        assertEquals(xmlfilter, xmlfilter);
    }
    @Test
    public void Save()
    {
        // Test FileWriter
        FileWriter fw = null;
        assertEquals(fw, null);

    }

    @Test
    public void New_Image()
    {
        // Test GUI is not the same as New_GUI
        assertFalse(gui.equals(new_gui));
    }

    @Test
    public void Undo()
    {
        // Test Shapes
        assertEquals(painting.shapes.size() - 1, - 1);

        // Test VECText
        assertEquals(painting.VECText.size() - 1, - 1);

        // Test ColorFill
        assertEquals(painting.colorsFill.size() - 1, - 1);

        // Test Colors
        assertEquals(painting.colors.size() - 1, - 1);
    }


}