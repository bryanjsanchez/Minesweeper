import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		
		JFrame myFrame = new JFrame("Minesweeper");
		MyPanel myPanel = new MyPanel();
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		
		myFrame.add(myPanel);
		myFrame.addMouseListener(myMouseAdapter);
		
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		myFrame.setLocation(dim.width/2 - myFrame.getSize().width/2, dim.height/2 - myFrame.getSize().height/2);
		myFrame.setLocation(500, 200);
		myFrame.setSize((MyPanel.INNER_CELL_SIZE) * Settings.getColumns() + 70, MyPanel.INNER_CELL_SIZE * Settings.getRows() + 120);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		
		myPanel.resetButton.doClick();
		
	}
}
