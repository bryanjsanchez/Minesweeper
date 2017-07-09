import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Flags {
	public boolean[][] flagArray = new boolean[Settings.getColumns()][Settings.getRows()];
	BufferedImage flagImage;
	
	public Flags() {
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				flagArray[x][y] = false;
			}
		}
		
		try {
			BufferedImage flagImage = ImageIO.read(getClass().getResource("flag.jpg"));
			this.flagImage = flagImage;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "flag.jpg is either corrupt or missing.",
					"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
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
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				hideFlag(x, y);
			}
		}
	}
}
