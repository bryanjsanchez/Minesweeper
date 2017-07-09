import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyPanel extends JPanel {
	
	private static final long serialVersionUID = 3426940946811133635L;
	
	private static final int GRID_X = 25;
	private static final int GRID_Y = 55;
	public static final int INNER_CELL_SIZE = 29;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	
	public Color[][] colorArray = new Color[Settings.getColumns()][Settings.getRows()];
	private Bombs bombs = new Bombs();
	public Flags flags = new Flags();

	private int emptySquares;
	public Color hiddenCellColor = new Color(0xeeeeee);
	public JButton resetButton;


	public MyPanel() {   //This is the constructor... this code runs first to initialize
		
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (Settings.getColumns() + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (Settings.getRows() + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		for (int y = 0; y < Settings.getRows(); y++) {   //The rest of the grid
			for (int x = 0; x < Settings.getColumns(); x++) {
				colorArray[x][y] = hiddenCellColor;
			}
		}
		
		resetButton = new JButton();
		try {
			Image img = ImageIO.read(getClass().getResource("/resetButton.png")).getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH );
			resetButton.setIcon(new ImageIcon(img));
			resetButton.setBounds((320 - 30)/2,15,50,50);
		} catch (Exception ex) {
			System.out.println("background.jpg is either corrupt or missing.");
		}
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.isNewGame = true;
				bombs.placeBombs();
				bombs.printBombGrid();
				flags.flagArray = new boolean[Settings.getColumns()][Settings.getRows()];
				flags.hideAllFlags();
				emptySquares = (Settings.getColumns() * Settings.getRows()) - Settings.getNumberOfBombs();
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				SwingUtilities.getWindowAncestor(MyPanel.this).setSize((INNER_CELL_SIZE + 1) * Settings.getColumns() + 56, (INNER_CELL_SIZE + 1) * Settings.getRows() + 106);
				SwingUtilities.getWindowAncestor(MyPanel.this).setLocation(dim.width/2 - SwingUtilities.getWindowAncestor(MyPanel.this).getSize().width/2, dim.height/2 - SwingUtilities.getWindowAncestor(MyPanel.this).getSize().height/2);
				colorArray = new Color[Settings.getColumns()][Settings.getRows()];
				for (int y = 0; y < Settings.getRows(); y++) {
					for (int x = 0; x < Settings.getColumns(); x++) {
						colorArray[x][y] = hiddenCellColor;
					}
				}

				repaint();
				System.out.println(emptySquares);
			}
		});
		add(resetButton);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		//int x2 = getWidth() - myInsets.right - 1;
		//int y2 = getHeight() - myInsets.bottom - 1;
		//int width = x2 - x1;
		//int height = y2 - y1;

		//Paint the background
		try {
			BufferedImage background = ImageIO.read(getClass().getResource("/background.jpg"));
			g.drawImage(background, 0, 0, null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "background.jpg is either corrupt or missing.",
					"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

		//Draw the grid
		g.setColor(Color.BLACK);
		for (int y = 0; y <= Settings.getRows(); y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * Settings.getColumns()), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= Settings.getColumns(); x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (Settings.getRows())));
		}

		//Paint cell colors
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				Color c = colorArray[x][y];
				g.setColor(c);
				g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
			}
		}

		//Paint cell labels
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				if (bombs.getRevealedCell()[x][y] == true) {
					g.drawImage(bombs.getLabelArray()[x][y], x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, null);
				}
			}
		}
		
		//Paint flags
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				if (flags.getFlagArray()[x][y] == true) {
					g.drawImage(flags.flagImage, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, null);
				}
			}
		}

	}
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x < 0 || x > Settings.getColumns() - 1|| y < 0 || y > Settings.getRows() - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > Settings.getColumns() - 1 || y < 0 || y > Settings.getRows() - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}

	public void revealSquare(int x, int y) {
		if (bombs.getRevealedCell()[x][y] == false) {
			switch (bombs.getBombGrid()[x][y]) {
			case "0":
				if (flags.getFlagArray()[x][y] == false) {
					colorArray[x][y] = Color.white;
					bombs.revealCell(x, y);
					repaint();
				}

				emptySquares--;
				
				for (int d = -1; d <= 1; d++) {
					if ((x + d >= 0) && (x + d < Settings.getColumns()) && (y - 1 >= 0)) { //index in range
						revealSquare(x + d, y - 1);
					}
					if ((x + d >= 0) && (x + d < Settings.getColumns()) && (y + 1 < Settings.getRows())) { //index in range
						revealSquare(x + d, y + 1);
					}
				}
				
				if (x - 1 >= 0) { //index in range
					revealSquare(x - 1, y);
				}
				if (x + 1 < Settings.getColumns()) { //index in range
					revealSquare(x + 1, y);
				}

				break;
			default:
				if (flags.getFlagArray()[x][y] == false) {
					bombs.revealCell(x, y);
					repaint();
					emptySquares--;
				}
				break;
			}
		}
	}
	public void revealSquare() {
		revealSquare(mouseDownGridX, mouseDownGridY);
	}

	public void checkIfWon() {
		System.out.println(emptySquares);
		if (emptySquares == 0) {
			for (int y = 0; y < Settings.getRows(); y++) {
				for (int x = 0; x < Settings.getColumns(); x++) {
					if (bombs.getRevealedCell()[x][y] == false) {
						revealSquare(x, y);
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Congratulations! You Win!");
		}
	}

	public void checkIfGameOver() {
		if (bombs.getBombGrid()[mouseDownGridX][mouseDownGridY] == "b") {
			emptySquares = -1;
			for (int y = 0; y < Settings.getRows(); y++) {
				for (int x = 0; x < Settings.getColumns(); x++) {
					if (bombs.getRevealedCell()[x][y] == false) {
						revealSquare(x, y);
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Game Over");
		}
	}
}
