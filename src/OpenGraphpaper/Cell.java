//Copyright (C) 2016 Jackson McNeill. Licensing info can be found in "LICENSE.txt" in the same directory as this file.

package OpenGraphpaper;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
	private final int squareW = 20;
	private final int squareH = 20;
        private boolean highlighted = false;
        private final Color lBlue = new Color(50,200,255,255);
        private final Font font;
        

	
	public Cell() 
	{
            this.font = new Font("Sans Serif", Font.BOLD , 16);

		
	}
	public void setText(String to)
	{
		containedText = to;
	}
        public String getText()
	{
		return containedText;
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
                g.setColor(Color.BLUE);
            }
            else
            {
                g.setColor(Color.WHITE);
            }
            g.fillRect(posX,posY,squareW,squareH);
            
            //g.setColor(Color.BLACK); for additional black square surrounding the white square 
            //g.drawRect(squareX+(x*20),squareY+(y*20),squareW,squareH);
            if (highlighted == true)
            {
                g.setColor(Color.BLUE);
            }
            else
            {
                g.setColor(lBlue);
            }
            int squareAddX = squareW-1;
            int squareAddY = squareH-1;
            
            
            g.drawLine(posX,posY+squareAddY,posX+squareAddX,posY+squareAddY); //for the blue half-square
            g.drawLine(posX+squareAddX,posY,posX+squareAddX,posY+squareAddY);
            
            g.setColor(Color.BLACK);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);
            // Determine the X coordinate for the text
            int x = (squareW - metrics.stringWidth(containedText)) / 2;
            // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
            int y = ((squareH - metrics.getHeight()) / 2) + metrics.getAscent();
            g.drawString(containedText,x,y); //better way to "center" this?
	}
}
