import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 55;
	private static final int INNER_CELL_SIZE = 29;
	public static final int TOTAL_COLUMNS = 9;
	public static final int TOTAL_ROWS = 9;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	private Bombs bombs;
	public Flags flags = new Flags();
	private int emptySquares;


	public MyPanel(Bombs bombs) {   //This is the constructor... this code runs first to initialize

		this.bombs = bombs;
		emptySquares = (TOTAL_COLUMNS * TOTAL_ROWS) - bombs.getNumberOfBombs();

		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
		JButton resetButton = new JButton();
		try {
			Image img = ImageIO.read(getClass().getResource("img/resetButton.png")).getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH );
			resetButton.setIcon(new ImageIcon(img));
			resetButton.setBounds((320 - 30)/2,15,50,50);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		resetButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				flags.hideAllFlags();
				bombs.setBombs();
				bombs.printBombGrid();
				emptySquares = (TOTAL_COLUMNS * TOTAL_ROWS) - bombs.getNumberOfBombs();
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						colorArray[j][i] = Color.white;
					}
				}

				repaint();
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
		//g.setColor(new Color(0x964B00));
		//g.fillRect(x1, y1, width + 1, height + 1);
		try {
			BufferedImage background = ImageIO.read(getClass().getResource("img/background.jpg"));
			g.drawImage(background, 0, 0, null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
					"VoidSpace - Fatal Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}


		//Draw the grid
		//By default, the grid will be 9x9 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS)) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
				}
			}
		}

		//Paint flags
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
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

		if (x < 0 || x > TOTAL_COLUMNS - 1|| y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
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
		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}

	public void revealSquare(int x, int y) {
		if (colorArray[x][y].equals(Color.white)) {
			Color newColor = null;

			switch (bombs.getBombGrid()[x][y]) {
			case "0":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0xeeeeee);
					colorArray[x][y] = newColor;
					repaint();
				}

				emptySquares--;
				for (int d = -1; d <= 1; d++) {
					if ((x + d >= 0) && (x + d < 9) && (y - 1 >= 0) && (y - 1 < 9)) { //index in range
						revealSquare(x + d, y - 1);
					}
				}

				for (int d = -1; d <= 1; d += 2) {
					if ((x + d >= 0) && (x + d < 9) && (y >= 0) && (y < 9)) { //index in range
						revealSquare(x + d, y);
					}
				}

				for (int d = -1; d <= 1; d++) {
					if ((x + d >= 0) && (x + d < 9) && (y + 1 >= 0) && (y+1 < 9)) { //index in range
						revealSquare(x + d, y + 1);
					}
				}
				break;
			case "1":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0xcccccc);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "2":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0xaaaaaa);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "3":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0x888888);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "4":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0x666666);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "5": 
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0x444444);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "6":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0x333333);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "7":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0x222222);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "8":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = new Color(0x111111);
					colorArray[x][y] = newColor;
					repaint();
					emptySquares--;
				}
				break;
			case "b":
				if (flags.getFlagArray()[x][y] == false) {
					newColor = Color.black;
					colorArray[x][y] = newColor;
					repaint();
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
		if (emptySquares == bombs.getNumberOfBombs()) {
			flags.hideAllFlags();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (colorArray[j][i].equals(Color.white)) {
						revealSquare(j, i);
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Congratulations! You Win!");
		}
	}

	public void checkIfGameOver() {
		if (bombs.getBombGrid()[mouseDownGridX][mouseDownGridY] == "b") {
			flags.hideAllFlags();
			emptySquares = -1;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (colorArray[j][i].equals(Color.white)) {
						revealSquare(j, i);
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Game Over");
		}
	}
}
