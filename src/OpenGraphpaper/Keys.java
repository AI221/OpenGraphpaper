//Copyright (C) 2016 Jackson McNeill. Licensing info can be found in "LICENSE.txt" in the same directory as this file.

package OpenGraphpaper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;


public class Keys implements KeyListener, ActionListener, ItemListener, MouseListener {
    private int startTileX = -1;
    private int startTileY = -1;
    public Keys() 
    {
        System.out.println("hello");
    }
    @Override
    public void keyTyped(KeyEvent e) {
        char character = e.getKeyChar();
        if (character == '\b')
        {
            Main.all.insertText(0);
        }
	else if (character == '^')
	{
	    Main.all.insertText(1);
	}
        else
        {
            Main.all.insertText(character);
        }
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();
        if (id == KeyEvent.VK_DOWN )
        {
            Main.all.pushHighlighted(0,1);
        }
        else if (id == KeyEvent.VK_UP )
        {
            Main.all.pushHighlighted(0,-1);
        }
        else if (id == KeyEvent.VK_LEFT)
        {
            Main.all.pushHighlighted(-1, 0);
        }
        else if (id == KeyEvent.VK_RIGHT)
        {
            Main.all.pushHighlighted(1, 0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        JMenuItem source = (JMenuItem) e.getSource();
        String srcTxt = source.getText();
        if (null != srcTxt)
        switch (srcTxt) {
            case "Clear Sheet":
                Main.all.clearAll();
                break;
            case "Save Sheet":
                Main.all.save();
                break;
            case "Load Sheet":
                Main.all.load();
                break;
	    case "About":
		Main.all.about();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = (int) Math.floor(e.getX()/20);
        int y = (int) Math.floor((e.getY())/20);
        Main.all.moveHighlighted(x,y);
	Main.all.drawLine(x,y,startTileX,startTileY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
	startTileX = (int) Math.floor(e.getX()/20);
        startTileY = (int) Math.floor((e.getY())/20);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

