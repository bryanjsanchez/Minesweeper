import java.awt.Color;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMouseAdapter extends MouseAdapter {

	private String[][] bombGrid;
	public int emptySquares;

	public MyMouseAdapter(String[][] bombGrid, int emptySquares) {
		this.bombGrid = bombGrid;
		this.emptySquares = emptySquares;
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


	public void revealSquare(MyPanel myPanel, int x, int y, String[][] bombGrid) {
		if (myPanel.colorArray[x][y].equals(Color.white)) {
			Color newColor = null;

			switch (bombGrid[x][y]) {
			case "0":
				newColor = new Color(0xeeeeee);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				for (int d = -1; d <= 1; d++) {
					if ((x + d >= 0) && (x + d < 9) && (y - 1 >= 0) && (y - 1 < 9)) { //index in range
						revealSquare(myPanel, x + d, y - 1, bombGrid);
					}
				}

				for (int d = -1; d <= 1; d += 2) {
					if ((x + d >= 0) && (x + d < 9) && (y >= 0) && (y < 9)) { //index in range
						revealSquare(myPanel, x + d, y, bombGrid);
					}
				}

				for (int d = -1; d <= 1; d++) {
					if ((x + d >= 0) && (x + d < 9) && (y + 1 >= 0) && (y+1 < 9)) { //index in range
						revealSquare(myPanel, x + d, y + 1, bombGrid);
					}
				}
				break;
			case "1":
				newColor = new Color(0xcccccc);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "2":
				newColor = new Color(0xaaaaaa);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "3":
				newColor = new Color(0x888888);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "4":
				newColor = new Color(0x666666);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "5": 
				newColor = new Color(0x444444);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "6":
				newColor = new Color(0x333333);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "7":
				newColor = new Color(0x222222);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "8":
				newColor = new Color(0x111111);
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				emptySquares--;
				break;
			case "b":
				newColor = Color.black;
				myPanel.colorArray[x][y] = newColor;
				myPanel.repaint();
				break;
			}

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

						revealSquare(myPanel, myPanel.mouseDownGridX, myPanel.mouseDownGridY, bombGrid);

						if (bombGrid[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == "b") {
							emptySquares = -1;
							for (int i = 0; i < 9; i++) {
								for (int j = 0; j < 9; j++) {
									if (myPanel.colorArray[j][i].equals(Color.white)) {
										revealSquare(myPanel, j, i, bombGrid);
									}
								}
							}
							JOptionPane.showMessageDialog(null, "Game Over");
						}
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
		if (emptySquares == 0) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (myPanel.colorArray[j][i].equals(Color.white)) {
						revealSquare(myPanel, j, i, bombGrid);
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Congratulations! You Win!");
		}
	}
}

