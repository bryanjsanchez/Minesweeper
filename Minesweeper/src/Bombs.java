import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Bombs {
	private int rows = MyPanel.TOTAL_ROWS;
	private int columns = MyPanel.TOTAL_COLUMNS;
	private int numberOfBombs = 0;
	private static String[][] bombGrid = new String[MyPanel.TOTAL_COLUMNS][MyPanel.TOTAL_ROWS];
	public BufferedImage[][] labelArray = new BufferedImage[MyPanel.TOTAL_COLUMNS][MyPanel.TOTAL_ROWS];
	public boolean[][] revealedCell = new boolean[MyPanel.TOTAL_COLUMNS][MyPanel.TOTAL_ROWS];

	public int getNumberOfBombs() {
		return numberOfBombs;
	}

	public String[][] getBombGrid() {
		return bombGrid;
	}

	public boolean[][] getRevealedCell() {
		return revealedCell;
	}

	public BufferedImage[][] getLabelArray() {
		return labelArray;
	}

	public void revealCell(int x, int y) {
		this.revealedCell[x][y] = true;
	}

	public void hideCell(int x, int y) {
		this.revealedCell[x][y] = false;
	}
	
	private void hideAllCells() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				hideCell(x, y);
			}
		}
	}
	
	private void clearLabelArray() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				labelArray[x][y] = null;
			}
		}
	}

	public void setBombs() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				revealedCell[x][y] = false;
			}
		}
		//Player enters number of bombs
		numberOfBombs = 0;
		while (numberOfBombs < 10 || numberOfBombs > 20) { 
			numberOfBombs= Integer.parseInt(JOptionPane.showInputDialog("Select a number of bombs between 10 and 20: "));
		} 

		//Clear bombGrid if values not null
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				bombGrid[x][y] = null;
			}
		}
		
		clearLabelArray();
		hideAllCells();

		//Bombs placed randomly in bombGrid
		int bombsPlaced = 0;
		while (bombsPlaced < numberOfBombs) {
			Random r = new Random();
			int x = r.nextInt(columns);
			int y = r.nextInt(rows);
			if (bombGrid[x][y] != "b") {
				bombGrid[x][y] = "b";
				try {
					BufferedImage mine = ImageIO.read(getClass().getResource("img/mine.jpg"));
					labelArray[x][y] = mine;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
							"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					System.exit(-1);
				}

				bombsPlaced++;
			}
		}

		//Surrounding number of bombs is determined for all bombless squares
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (bombGrid[i][j] != "b") {
					int surroundingBombs = 0;

					//Checks 3 top squares for bombs
					for (int d = -1; d <= 1; d++) {
						if ((i+d >= 0) && (i+d < 9) && (j-1 >= 0) && (j-1 < 9)) { //index in range
							if (bombGrid[i+d][j-1] == "b") {
								surroundingBombs++;
							}
						}
					}

					//Check 2 middle squares for bombs
					for (int d = -1; d <= 1; d+=2) {
						if ((i+d >= 0) && (i+d < 9) && (j >= 0) && (j < 9)) { //index in range
							if (bombGrid[i+d][j] == "b") {
								surroundingBombs++;
							}
						}
					}

					//Check 3 bottom squares for bombs
					for (int d = -1; d <= 1; d++) {
						if ((i+d >= 0) && (i+d < 9) && (j+1 >= 0) && (j+1 < 9)) { //index in range
							if (bombGrid[i+d][j+1] == "b") {
								surroundingBombs++;
							}
						}
					}

					//Sets value for number of surrounding bombs in each square
					try {
						switch (surroundingBombs) {
						case 1:
							BufferedImage label1 = ImageIO.read(getClass().getResource("img/label-1.jpg"));
							labelArray[i][j] = label1;
							break;
						case 2:
							BufferedImage label2 = ImageIO.read(getClass().getResource("img/label-2.jpg"));
							labelArray[i][j] = label2;
							break;
						case 3: 
							BufferedImage label3 = ImageIO.read(getClass().getResource("img/label-3.jpg"));
							labelArray[i][j] = label3;
							break;
						case 4:
							BufferedImage label4 = ImageIO.read(getClass().getResource("img/label-4.jpg"));
							labelArray[i][j] = label4;
							break;
						case 5: 
							BufferedImage label5 = ImageIO.read(getClass().getResource("img/label-5.jpg"));
							labelArray[i][j] = label5;
							break;
						case 6: 
							BufferedImage label6 = ImageIO.read(getClass().getResource("img/label-6.jpg"));
							labelArray[i][j] = label6;
							break;
						case 7: 
							BufferedImage label7 = ImageIO.read(getClass().getResource("img/label-7.jpg"));
							labelArray[i][j] = label7;
							break;
						case 8: 
							BufferedImage label8 = ImageIO.read(getClass().getResource("img/label-8.jpg"));
							labelArray[i][j] = label8;
							break;
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "The graphic files are either corrupt or missing.",
								"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.exit(-1);
					}
					bombGrid[i][j] = Integer.toString(surroundingBombs);
				}
			}
		}
	}

	public void printBombGrid() {
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				System.out.print(bombGrid[x][y]);
			}
			System.out.println();
		}
	}
}










