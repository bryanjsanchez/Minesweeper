import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class GraphicsManager {
	private BufferedImage resetImage;
	private BufferedImage background;
	private BufferedImage[] labels = new BufferedImage[9];
	private BufferedImage mineImage;
	private BufferedImage flagImage;

	public BufferedImage getResetImage() {
		return resetImage;
	}
	public BufferedImage getBackground() {
		return background;
	}
	public BufferedImage getLabels(int n) {
		return labels[n];
	}
	public BufferedImage getMineImage() {
		return mineImage;
	}
	public BufferedImage getFlagImage() {
		return flagImage;
	}

	public GraphicsManager() {
		try {
			resetImage = ImageIO.read(getClass().getResource("/resetButton.jpg"));
		} catch (IOException ex) {
			System.out.println("background.jpg is either corrupt or missing.");
		}
		
		try {
			background = ImageIO.read(getClass().getResource("/background.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "background.jpg is either corrupt or missing.",
					"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
		try {	
			labels[1] = ImageIO.read(getClass().getResource("/label-1.jpg"));
			labels[2] = ImageIO.read(getClass().getResource("/label-2.jpg"));
			labels[3] = ImageIO.read(getClass().getResource("/label-3.jpg"));
			labels[4] = ImageIO.read(getClass().getResource("/label-4.jpg"));
			labels[5] = ImageIO.read(getClass().getResource("/label-5.jpg"));
			labels[6] = ImageIO.read(getClass().getResource("/label-6.jpg"));
			labels[7] = ImageIO.read(getClass().getResource("/label-7.jpg"));
			labels[8] = ImageIO.read(getClass().getResource("/label-8.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Label images are either corrupt or missing.",
					"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
		try {
			flagImage = ImageIO.read(getClass().getResource("flag.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "flag.jpg is either corrupt or missing.",
					"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
		try {
			mineImage = ImageIO.read(getClass().getResource("/mine.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "mine.jpg is either corrupt or missing.",
					"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}
}