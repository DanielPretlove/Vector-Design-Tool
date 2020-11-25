package CAB302_Assignment2;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class GUI implements ActionListener, KeyListener {

    /**
     * global variables
     */
    private JPanel panel = new JPanel();
    private JMenuItem new_item, open_item, save_item, newimage_item, large_size, normal_size, undo;
    private JMenu file, edit, view, images, colours, help, zoom;
    private JCheckBoxMenuItem fill;
    private JMenuBar menuBar = new JMenuBar();
    private JFrame frame = new JFrame();
    private JButton btnline = new JButton(new ImageIcon(getClass().getResource("images\\line.png")));
    private JButton btnPlot = new JButton(new ImageIcon(getClass().getResource("images\\plot.png")));
    private JButton btnRectangle = new JButton(new ImageIcon(getClass().getResource("images\\rectangle.png")));
    private JButton btnEllipse = new JButton(new ImageIcon(getClass().getResource("images\\ellipse.png")));
    private JButton btnPolygon = new JButton(new ImageIcon(getClass().getResource("images\\polygon.png")));
    private JButton btn_fill_shapes = new JButton("Fill Shapes Colour");
    private JButton btn_line_colour = new JButton("Line Colour");
    private JColorChooser colorChooser = new JColorChooser();
    private Painting painting = new Painting();
    private JFileChooser fc = new JFileChooser();


    /**
     * calling methods that gets displayed within the graphical user interface
     */
    public GUI() {
        displayMenuItem_and_Menu();
        AddedItemsInMenuBar();
        displayCheckBox();
        frame.setBounds(2,2, 200, 200);
        displayCanvas();
        displayButtons();
        displayColourChooser();
        displayMenuBar();
        displayFrame();
        painting.AddMouseListeners();
        AddKeyListeners();
    }

    /**
     * displaying the menuitems and the menu
     */
    public void displayMenuItem_and_Menu() {
        new_item = new JMenuItem("New");
        open_item = new JMenuItem("Open");
        save_item = new JMenuItem("Save");
        newimage_item = new JMenuItem("New Image");
        large_size = new JMenuItem("Large Size");
        normal_size = new JMenuItem("Normal Size");
        undo = new JMenuItem("Undo");
        /**
         * adding action listeners to the JMenuItems
         */
        new_item.addActionListener(this);
        open_item.addActionListener(this);
        save_item.addActionListener(this);
        newimage_item.addActionListener(this);
        large_size.addActionListener(this);
        normal_size.addActionListener(this);
        undo.addActionListener(this);
        file = new JMenu("File");
        edit = new JMenu("Edit");
        view = new JMenu("View");
        zoom = new JMenu("Zoom");
        images = new JMenu("Image");
        colours = new JMenu("Colours");
        help = new JMenu("Help");
        file.add(new_item);
        file.add(open_item);
        file.add(save_item);
        file.add(newimage_item);
        edit.add(undo);
        view.add(zoom);
        zoom.add(large_size);
        zoom.add(normal_size);
    }

    /**
     * adding menuitems to the menubar
     */
    public void AddedItemsInMenuBar() {
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(view);
        menuBar.add(images);
        menuBar.add(colours);
        menuBar.add(help);
    }

    /**
     * adding a check box menu item and adding an actionListener to the fill JCheckBoxMenuItem
     */
    public void displayCheckBox()
    {
        fill = new JCheckBoxMenuItem("Fill Shapes");
        colours.add(fill);
        fill.addActionListener(this);
    }

    /**
     * displaying a menubar within the graphical user interface
     */
    public void displayMenuBar() {
        frame.add(menuBar);
        frame.setJMenuBar(menuBar);
    }

    /**
     * displaying the canvas within the graphical user interface
     */
    public void displayCanvas() {
        frame.add(painting.canvas);
        painting.canvas.setBackground(Color.WHITE);
        painting.canvas.setBounds(170, 0, 550, 550);
    }

    /**
     * displaying the buttons within the graphical user interface
     */
    public void displayButtons() {
        frame.add(btnline);
        frame.add(btnPlot);
        frame.add(btnRectangle);
        frame.add(btnEllipse);
        frame.add(btnPolygon);
        panel.setLayout(null);
        /**
         * setting the location and the size to the buttons
         */
        btnline.setBounds(10,2,150,115);
        btnPlot.setBounds(10, 142, 150, 115);
        btnRectangle.setBounds(10, 277, 150, 115);
        btnEllipse.setBounds(10, 412, 150, 115);
        btnPolygon.setBounds(10, 547, 150, 115);

        /**
         * adding listeners to the buttons
         */
        btnline.addActionListener(this);
        btnPlot.addActionListener(this);
        btnRectangle.addActionListener(this);
        btnEllipse.addActionListener(this);
        btnPolygon.addActionListener(this);
    }

    /**
     * displaying the colourchooser within the graphical user interface
     */
    public void displayColourChooser() {
        frame.add(btn_fill_shapes);
        frame.add(btn_line_colour);
        btn_fill_shapes.setBounds(170, 555, 400, 105);
        btn_line_colour.setBounds(578, 555, 400, 105);
        btn_fill_shapes.addActionListener(this);
        btn_line_colour.addActionListener(this);
        btn_fill_shapes.setEnabled(false);
    }

    /**
     * setting up the frame of the graphical user  interface
     */
    public void displayFrame() {
        frame.setTitle("Vector Design Tool");
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.pack();
        frame.setBounds(500, 100, 1000, 730);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * clearing the shapes, colors, colorfills and the VECText within the canvas
     */
    public void New()
    {
        painting.shapes.clear();
        painting.colors.clear();
        painting.colorsFill.clear();
        painting.VECText.clear();
        painting.canvas.repaint();
    }


    /**
     * opening up a VEC file by using a JFileChooser and displaying the VEC file within the appplication
     * and listing the VEC file description to the output
     */
    public void Open()
    {
        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "VEC files (*.VEC)", "VEC");

        fc.setDialogTitle("Open VEC file");
        // set selected filter
        fc.setFileFilter(xmlfilter);
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            painting.OpenVec(file);
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
    }

    /**
     * Saves your drawn shapes to a VEC file
     * when you click save within the jFileChooser
     */
    public void Save()
    {
        List<String> VECText = painting.SaveVec();

        FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
                "VEC files (*.VEC)", "VEC");

        fc.setDialogTitle("Save VEC file");
        fc.setFileFilter(xmlfilter);

        int returnVal = fc.showSaveDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String filename = file.toString();
            FileWriter fw = null;
            try {
                fw = new FileWriter(file);
                for(String line: VECText){
                    fw.write(line+"\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }finally {
                if(fw != null) {
                    try {
                        fw.close();
                    } catch (IOException ex) {
                    }
                }
            }

        }


    }

    /**
     * opens a new graphical user interface
     */
    public void New_Image()
    {
        new New_GUI();
    }

    /**
     *  removes the previous drawn shape in the program
     */
    public void Undo()
    {
        if (!painting.shapes.isEmpty()) {
            painting.shapes.remove(painting.shapes.size() - 1);
            painting.VECText.remove(painting.VECText.size() - 1);
            painting.colorsFill.remove(painting.colorsFill.size() - 1);
            painting.colors.remove(painting.colors.size() - 1);
            painting.canvas.repaint();
        }
    }

    /**
     * Adds key listener to the canvas
     * and get the canvas to focus onto the window
     */
    public void AddKeyListeners()
    {
        painting.canvas.addKeyListener(this);
        painting.canvas.setFocusable(true);
        painting.canvas.requestFocusInWindow();
    }
    @Override
    public void actionPerformed (ActionEvent e) {

        /**
         * clears everything in the screen
         * when you click the new_item menuitem
         */
        if (e.getSource() == new_item) {
            New();
        }

        /**
         * opening up a VEC file by using a JFileChooser and displaying the VEC file within the appplication
         * when you click the open_item menuitem
         */
        else if(e.getSource() == open_item) {
           Open();
        }

        /**
         * Saves your drawn shapes to a VEC file
         * when you click the save_item menuitem
         */
        else if(e.getSource() == save_item) {
            Save();
        }

        /**
         * when new_item has been clicked a separate CAB302_Assignment2.GUI will appear
         */
        else if(e.getSource() == newimage_item)
        {
            New_Image();
        }

        /**
         * when btnline is click you will be able to continuously draw an line in the canvas
         */
        else if(e.getSource() == btnline) {
            painting.currentShape = Painting.Shape.LINE;
            btnline.setEnabled(false);
            btnPlot.setEnabled(true);
            btnRectangle.setEnabled(true);
            btnEllipse.setEnabled(true);
            btnPolygon.setEnabled(true);
            painting.mouseState = 0;
        }

        /**
         * when btnPlot is clicked you will be able to continuously draw an plot in the canvas
         */
        else if(e.getSource() == btnPlot) {
            painting.currentShape = Painting.Shape.PLOT;
            btnline.setEnabled(true);
            btnPlot.setEnabled(false);
            btnRectangle.setEnabled(true);
            btnEllipse.setEnabled(true);
            btnPolygon.setEnabled(true);
            painting.mouseState = 0;
        }

        /**
         * when btnRectangle is clicked you will be able to continuously draw an rectangle in the canvas
         */
        else if(e.getSource() == btnRectangle) {
            painting.currentShape = Painting.Shape.RECTANGLE;
            btnline.setEnabled(true);
            btnPlot.setEnabled(true);
            btnRectangle.setEnabled(false);
            btnEllipse.setEnabled(true);
            btnPolygon.setEnabled(true);
            painting.mouseState = 0;
        }

        /**
         * when btnEllipse is clicked you will be able to continuously draw an ellipse in the canvas
         */
        else if(e.getSource() == btnEllipse) {
            painting.currentShape = Painting.Shape.ELLIPSE;
            btnline.setEnabled(true);
            btnPlot.setEnabled(true);
            btnRectangle.setEnabled(true);
            btnEllipse.setEnabled(false);
            btnPolygon.setEnabled(true);
            painting.mouseState = 0;
        }

        /**
         * when btnPolygon is clicked you will be able to continuously draw an polygon in the canvas
         */
        else if(e.getSource() == btnPolygon) {
            painting.currentShape = Painting.Shape.POLYGON;
            btnline.setEnabled(true);
            btnPlot.setEnabled(true);
            btnRectangle.setEnabled(true);
            btnEllipse.setEnabled(true);
            btnPolygon.setEnabled(false);
            painting.mouseState = 0;
        }

        /**
         * removes the last drawn shape when you click the undo menuitem
         */
        else if(e.getSource() == undo) {
            Undo();
        }

        else if(e.getSource() == large_size) {
        }

        else if(e.getSource() == normal_size) {
        }

        else if(e.getSource() == fill) {
            //
            if(fill.isSelected()){
                /**
                 * when fill is selected you will be able to draw filled shapes
                 */
                painting.fillShapes = true;
                btn_fill_shapes.setEnabled(true);
            }
            else{
                /**
                 * when fill isn't selected you will be able to draw line shapes
                 */
                btn_fill_shapes.setEnabled(false);
                painting.FillColor = null;
                painting.VECText.add("FILL OFF");
            }

        }
        /**
         * when btn_fill_shapes is pressed a Colour Chooser will pop up
         * where you will be able to choose the colour of your filled shapes
         */
        else if(e.getSource() == btn_fill_shapes) {
            Color initialBackground = btn_fill_shapes.getBackground();
            Color fill_shapes_colour = colorChooser.showDialog(null,"Fill Colour", initialBackground);

            if(fill_shapes_colour != null)
            {
                painting.FillColor = fill_shapes_colour;
                painting.VECText.add("FILL #"+Integer.toHexString(fill_shapes_colour.getRGB()).substring(2));
            }
        }

        /**
         * when btn_line_colour is pressed a Colour Chooser will pop up
         * where you will be able to choose the colour of your line shapes
         */
        else if(e.getSource() == btn_line_colour) {
            Color initialForeground = btn_line_colour.getForeground();
            Color line_colour = colorChooser.showDialog(null,"Line Colour", initialForeground);
            if(line_colour != null)
            {
                painting.LineColor = line_colour;
                painting.VECText.add("PEN #"+Integer.toHexString(line_colour.getRGB()).substring(2));
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Removes previous drawn shape when Crtl+Z is pressed
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0))
        {
            if (!painting.shapes.isEmpty()) {
                painting.shapes.remove(painting.shapes.size() - 1);
                painting.VECText.remove(painting.VECText.size() - 1);
                painting.colorsFill.remove(painting.colorsFill.size() - 1);
                painting.colors.remove(painting.colors.size() - 1);
                painting.canvas.repaint();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
