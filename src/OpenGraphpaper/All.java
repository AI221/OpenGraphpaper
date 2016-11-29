//Copyright (C) 2016 Jackson McNeill. Licensing info can be found in "LICENSE.txt" in the same directory as this file.
package OpenGraphpaper;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author jackson
 */
public class All {

    private static final int ammountSqrX = 150;
    private static final int ammountSqrY = 150;
    private static final int scrollSpeed = 20;
    private static final Cell[][] cells = new Cell[ammountSqrX][ammountSqrY];
    private int highlightedX = 0;
    private int highlightedY = 0;
    Keys keys = new Keys();
    JFrame frame = new JFrame("OpenGraphpaper");
    JFileChooser fc;

    public All() {
	for (int y = 0; y < ammountSqrY; y++) {
	    for (int x = 0; x < ammountSqrX; x++) {
		cells[x][y] = new Cell();
		//cells[x][y].setBounds(x*20,y*20,20,20);
	    }
	}
	String userDir = System.getProperty("user.home");
	File starting = new File(userDir+"/Documents/OpenGraphSaves");
	if (!starting.exists())
	{
	    starting.mkdirs();
	}
	fc = new JFileChooser(starting);

    }

    public void start() {
	SwingUtilities.invokeLater(() -> {
	    createAndShowGUI();
	});
    }

    public void createAndShowGUI() {
	JDialog dialog = new JDialog();
	dialog.setSize(640, 480);
	/*JLabel label = new JLabel("Please wait...");
        
        
	 dialog.add(label);
	 //dialog.pack();*/
	dialog.setTitle("Please Wait...");
	dialog.setLocationRelativeTo(null);
	dialog.setVisible(true); // show while this is loading

	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	Insets fins = frame.getInsets();
	frame.setSize(640, 480);
	JPanel pane = new JPanel();//frame.getContentPane();
	pane.setLayout(new GridBagLayout());
	JScrollPane scrollPane = new JScrollPane(pane);
	scrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
	scrollPane.getHorizontalScrollBar().setUnitIncrement(scrollSpeed);

	/*JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	 pane.setSize(ammountSqrX*20, ammountSqrY*20);
	 frame.setContentPane(pane);
	 fpane.setLayout(null); //manual style for layouts
	 fpane.add(pane);*/
	GridBagConstraints c = new GridBagConstraints();
        //c.fill = GridBagConstraints.HORIZONTAL;

        //c.weightx = 1;
	//c.weighty= 1;
	c.gridx = 0;
	c.gridy = 0;
	c.gridheight = 20;
	c.gridwidth = 20;
	c.ipadx = 19;
	c.ipady = 19;

	for (int y = 0; y < ammountSqrY; y++) {
	    c.gridx = 0;
	    for (int x = 0; x < ammountSqrX; x++) {
                //cells[x][y] = new Cell();            
		//cells[x][y].setBounds(x*20,y*20,20,20);
		pane.add(cells[x][y], c);
		c.gridx = c.gridx + 20;
	    }
	    c.gridy = c.gridy + 20;
	}
	System.out.println("Gx " + c.gridx + " gy " + c.gridy);

	cells[0][0].setHighlighted(true);

	frame.addKeyListener(keys);
	pane.addMouseListener(keys);

	JMenuBar menuBar = new JMenuBar();
	pane.add(menuBar);

	JMenu menu = new JMenu("File");

	menuBar.add(menu);

	JMenuItem saveSheet = new JMenuItem("Save Sheet");
	saveSheet.addActionListener(keys);

	menu.add(saveSheet);

	JMenuItem loadSheet = new JMenuItem("Load Sheet");
	loadSheet.addActionListener(keys);
	
	menu.add(loadSheet);
	
	JMenuItem about = new JMenuItem("About");
	about.addActionListener(keys);

	menu.add(about);
		
	JMenuItem clrSheet = new JMenuItem("Clear Sheet");
	clrSheet.addActionListener(keys);

	menu.add(clrSheet);

	//
	frame.add(scrollPane);
        //

	frame.setJMenuBar(menuBar);

	//pane.setVisible(true);
	frame.setVisible(true);
	dialog.setVisible(false); // we're done loading, remove the dialog.

    }

    /**
     *
     * @param x how much to push it right
     * @param y how much to push it down both can be negative
     */
    public void pushHighlighted(int x, int y) {
	/*int newHighlightedX = highlightedX+x;
	 int newHighlightedY = highlightedY+y;
	 if (newHighlightedX >= ammountSqrX)
	 {
	 return;
	 }
	 else if (newHighlightedX < 0)
	 {
	 return;
	 }
	 else if (newHighlightedY >= ammountSqrY)
	 {
	 return;
	 }
	 else if (newHighlightedY < 0)
	 {
	 return;
	 }
	 cells[highlightedX][highlightedY].setHighlighted(false);
	 cells[highlightedX][highlightedY].repaint();
	 highlightedX = newHighlightedX;
	 highlightedY = newHighlightedY;
	 System.out.println(highlightedX);
	 System.out.println(highlightedY);
	 cells[highlightedX][highlightedY].setHighlighted(true);
	 cells[highlightedX][highlightedY].repaint();*/
	moveHighlighted(highlightedX + x, highlightedY + y);

    }

    public void insertText(char character) {
	String text = Character.toString(character);
	cells[highlightedX][highlightedY].setText(text);
	cells[highlightedX][highlightedY].repaint();

	pushHighlighted(1, 0);
    }

    public void insertText(int value) //special operations
    {
	if (value == 0) 
	{
	    cells[highlightedX][highlightedY].setText(" ");
	    cells[highlightedX][highlightedY].setLine(0);
	    cells[highlightedX][highlightedY].setScript(0);
	    cells[highlightedX][highlightedY].repaint();
	    pushHighlighted(-1, 0);
	    cells[highlightedX][highlightedY].setText(" ");
	    cells[highlightedX][highlightedY].setLine(0);
	    cells[highlightedX][highlightedY].setScript(0);
	    cells[highlightedX][highlightedY].repaint();
	}
	else if (value == 1)
	{
	    cells[highlightedX][highlightedY].increaseScript();
	    cells[highlightedX][highlightedY].repaint();
	}
    }

    public void clearAll() {
	for (int y = 0; y < ammountSqrY; y++) {
	    for (int x = 0; x < ammountSqrX; x++) {
		cells[x][y].setText(" ");
		cells[x][y].setHighlighted(false);
		cells[x][y].setLine(0);
		cells[x][y].setScript(0);
		//cells[x][y].repaint();
	    }
	}
	cells[0][0].setHighlighted(true);
	frame.repaint();
	highlightedX = 0;
	highlightedY = 0;
    }

    public void moveHighlighted(int x, int y) {

	if (x >= ammountSqrX) {
	    return;
	} else if (x < 0) {
	    return;
	} else if (y >= ammountSqrY) {
	    return;
	} else if (y < 0) {
	    return;
	}
	cells[highlightedX][highlightedY].setHighlighted(false);
	cells[highlightedX][highlightedY].repaint();
	highlightedX = x;
	highlightedY = y;
	System.out.println(highlightedX);
	System.out.println(highlightedY);
	cells[highlightedX][highlightedY].setHighlighted(true);
	cells[highlightedX][highlightedY].repaint();

    }

    @SuppressWarnings("empty-statement")
    public void save() {
	int returnVal = fc.showSaveDialog(frame);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = fc.getSelectedFile();

	    System.out.println("SAVE!!!");
	    String cellStuffs[][] = new String[ammountSqrX][ammountSqrY];
	    int cellStuffsLines[][] = new int[ammountSqrX][ammountSqrY];
	    int cellStuffsScript[][] = new int[ammountSqrX][ammountSqrY];
            //JSONObject stuffs = new JSONOject();

	    for (int y = 0; y < ammountSqrY; y++) {
		for (int x = 0; x < ammountSqrX; x++) {
		    cellStuffs[x][y] = cells[x][y].getText();
		    cellStuffsLines[x][y] = cells[x][y].getLine();
		    cellStuffsScript[x][y] = cells[x][y].getScript();
		}
	    }
	    //no idea what this does
	    ObjectOutputStream out;
	    try 
	    {
		out = new ObjectOutputStream
		(
		    new FileOutputStream(file.getPath())
		);
		out.writeObject(cellStuffs);
		out.writeObject(cellStuffsLines);
		out.writeObject(cellStuffsScript);
	    } 
	    catch (IOException ex) 
	    {
		JOptionPane.showMessageDialog(frame, ex.getMessage(), ex.getMessage(), 0);
	    }
	}

    }

    public void load() {
	int returnVal = fc.showOpenDialog(frame);
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    ObjectInputStream in;
	    try {
		in = new ObjectInputStream(new FileInputStream(fc.getSelectedFile().getPath()));
		String[][] cellStuffs;
		int[][] cellStuffsLines;
		int[][] cellStuffsScript;
		cellStuffs = (String[][]) in.readObject();
		cellStuffsLines = (int[][]) in.readObject();
		cellStuffsScript = (int[][]) in.readObject();
		in.close();
		for (int y = 0; y < ammountSqrY; y++) {
		    for (int x = 0; x < ammountSqrX; x++) {
			cells[x][y].setText(cellStuffs[x][y]);
			cells[x][y].setLine(cellStuffsLines[x][y]);
			cells[x][y].setScript(cellStuffsScript[x][y]);
		    }
		}
	    } catch (IOException ex) {
		Logger.getLogger(All.class.getName()).log(Level.SEVERE, null, ex);
		JOptionPane.showMessageDialog(frame, "File selected is not an OpenGraphpaper save.", "Invalid file", 0);
	    } catch (ClassNotFoundException ex) {
		Logger.getLogger(All.class.getName()).log(Level.SEVERE, null, ex);
		JOptionPane.showMessageDialog(frame, "ClassNotFoundException for in.readObject()", "ClassNotFoundException", 0);

	    }

	}
	cells[highlightedX][highlightedY].setHighlighted(false);
	cells[0][0].setHighlighted(true);
	highlightedX = 0;
	highlightedY = 0;
	frame.repaint();
    }
    public void about()
    {
	JOptionPane.showMessageDialog(frame, "OpenGraphpaper is a tool for student's use. It enables students to do 'hand' math on the computer,\n which is helpful to those who do not have good handwriting, those who are on the go, or even those with disabilities. \nIt does not solve the equations for you, it is for you to learn by solving them for yourself.\nCheck out the github at: https://github.com/AI221/OpenGraphpaper");
    }
    public void drawLine(int x, int y, int startTileX, int startTileY)
    {
	if (!(x-startTileX==0))
	{
	    for (int i=min(startTileX,x);i<max(startTileX,x)+1;i++) 
	    {
	       cells[i][y].setLine(2);
	       System.out.println("Y: "+y+" X: "+i);
	    }
	    frame.repaint();
	}
	else if (!(y-startTileY == 0))
	{
	    for (int i=min(startTileY,y);i<max(startTileY,y)+1;i++) 
	    {
	       cells[x][i].setLine(1);
	       System.out.println("Y: "+i+" X: "+x);
	    }
	    frame.repaint();
	}
	
    }
}
