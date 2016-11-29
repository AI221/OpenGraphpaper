//Copyright (C) 2016 Jackson McNeill. Licensing info can be found in "LICENSE.txt" in the same directory as this file.

package OpenGraphpaper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;


//import javax.swing.*;        
public class Cell extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String containedText = " ";
	private int posX;
	private int posY;
	private int lineType = 0; //0  for none, 1 for vertical, 2 for horizontal
	private int scriptType = 0; //0 for normal, 1 for super, 2 for sub
	private final int squareW = 20;
	private final int squareH = 20;
	private final BasicStroke lineThick = new BasicStroke(3);  
        private boolean highlighted = false;
        private final Color lBlue = new Color(50,200,255,255);
        private final Font defFont;
	private final Font superFont;
        

	
	public Cell() 
	{
            this.defFont = new Font("Sans Serif", Font.BOLD , 16);
	    this.superFont = new Font("Sans Serif", Font.BOLD , 10);

		
	}
	public void setLine(int type)
	{
	    lineType = type;
	}
	public int getLine()
	{
	    return lineType;
	}
	public void setText(String to)
	{
		containedText = to;
	}
        public String getText()
	{
		return containedText;
	}
	public void setScript(int type)
	{
	    scriptType = type;
	    
	}
	public void increaseScript()
	{
	    scriptType++;
	    if (scriptType == 3)
	    {
		scriptType=0;
	    }   
	}
	public int getScript()
	{
	    return scriptType;
	}
        public void setHighlighted(boolean is)
        {
            highlighted = is;
        }
	@Override
	public void paintComponent(Graphics g)
	{
            // NOTE: the coordinate system is relative to the start of this component, _NOT_ the frame!
            //System.out.println(posX);
            //System.out.println(posY);

            if (highlighted == true)
            {
		if (scriptType == 0)
		{
		    g.setColor(Color.BLUE);
		}
		else if (scriptType == 1)
		{
		    g.setColor(Color.MAGENTA);
		}
		else
		{
		    g.setColor(Color.RED);
		}
            }
            else
            {
                g.setColor(Color.WHITE);
            }
            g.fillRect(posX,posY,squareW,squareH);
            
            //g.setColor(Color.BLACK); for additional black square surrounding the white square 
            //g.drawRect(squareX+(x*20),squareY+(y*20),squareW,squareH);
            if (highlighted == false) //we've already set the color if it is highlighted
	    {
                g.setColor(lBlue); 
            }
            int squareAddX = squareW-1;
            int squareAddY = squareH-1;
            
            
            g.drawLine(posX,posY+squareAddY,posX+squareAddX,posY+squareAddY); //for the blue half-square
            g.drawLine(posX+squareAddX,posY,posX+squareAddX,posY+squareAddY);
	    
	    g.setColor(Color.BLACK);
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(lineThick);
	    if(lineType == 1)
	    {
		int xpos = posX+(squareW/2);
		g2.drawLine(xpos,posY,xpos,posY+squareH);
	    }
	    else if (lineType ==2)
	    {
		int ypos = posY+(squareH/2);
		g2.drawLine(posX,ypos,posX+squareW,ypos);
	    }
            
	    
	    if (scriptType == 0)
	    {
		g.setFont(defFont);
		FontMetrics metrics = g.getFontMetrics(defFont);
		// Determine the X coordinate for the text
		int x = (squareW - metrics.stringWidth(containedText)) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int y = ((squareH - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(containedText,x,y); //better way to "center" this?
	    }
	    else if (scriptType ==1)
	    {
		g.setFont(superFont);
		FontMetrics metrics = g.getFontMetrics(defFont);
		g.drawString(containedText,0,metrics.getHeight()/2);
	    }
	    else
	    {
		g.setFont(superFont);
		g.drawString(containedText,0,squareH-1);
	    }
	}
}
