import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Flags {
	private int rows = MyPanel.TOTAL_ROWS;
	private int columns = MyPanel.TOTAL_COLUMNS;
	boolean[][] flagArray = new boolean[columns][rows];
	BufferedImage flagImage;
	
	public Flags() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				flagArray[x][y] = false;
			}
		}
		
		try {
			BufferedImage flagImage = ImageIO.read(getClass().getResource("img/flag.jpg"));
			this.flagImage = flagImage;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	public boolean[][] getFlagArray() {
		return flagArray;
	}
	
	public void revealFlag(int x, int y) {
		this.flagArray[x][y] = true;
	}
	
	public void hideFlag(int x, int y) {
		this.flagArray[x][y] = false;
	}
	
	public void hideAllFlags() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				hideFlag(x, y);
			}
		}
	}
}
