import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	
	private String[][] bombGrid;
	
	public MyMouseAdapter(String[][] bombGrid) {
		this.bombGrid = bombGrid;
	}
	
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:
		case 3:
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	
	
	public void revealSquare(MyPanel myPanel, String[][] bombGrid) {
		if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.white)) {
			Color newColor = null;
			switch (bombGrid[myPanel.mouseDownGridX][myPanel.mouseDownGridY]) {
			case "0":
			case "1":
			case "2":
			case "3":
			case "4":
			case "5": 
			case "6":
			case "7":
			case "8":
				newColor = Color.lightGray;
				break;
			case "b":
				newColor = Color.black;
				break;
			}
			myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
            myPanel.repaint();
        }
	}
	
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);
		
		switch (e.getButton()) {
		case 1:		//Left mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed

						//On the grid other than on the left column and on the top row:
						revealSquare(myPanel, bombGrid);
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Color newColor = null;
			if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.white)) {
				newColor = Color.red;
			} else if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.red)) {
				newColor = Color.white;
			} else {
				break;
			}
			myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
			myPanel.repaint();
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}