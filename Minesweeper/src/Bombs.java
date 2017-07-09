import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Bombs {
	
	private static String[][] bombGrid = new String[Settings.getColumns()][Settings.getRows()];
	public BufferedImage[][] labelArray = new BufferedImage[Settings.getColumns()][Settings.getRows()];
	public boolean[][] revealedCell = new boolean[Settings.getColumns()][Settings.getRows()];

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
		revealedCell = new boolean[Settings.getColumns()][Settings.getRows()];
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				hideCell(x, y);
			}
		}
	}
	
	private void clearBombGrid() {
		bombGrid = new String[Settings.getColumns()][Settings.getRows()];
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				bombGrid[x][y] = null;
			}
		}
	}
	
	private void clearLabelArray() {
		labelArray = new BufferedImage[Settings.getColumns()][Settings.getRows()];
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				labelArray[x][y] = null;
			}
		}
	}
	
	private void promptDifficulty() {
		Settings.setDifficulty("");
		while (!Arrays.asList(Settings.getDifficultyOptions()).contains(Settings.getDifficulty())) { 
			Settings.setDifficulty(JOptionPane.showInputDialog(null, "Select game difficulty.", "Minesweeper", JOptionPane.INFORMATION_MESSAGE, null,
					Settings.getDifficultyOptions(), Settings.getDifficultyOptions()[0]));
		}
		
		switch ((String) Settings.getDifficulty()) {
			case "Easy":
				Settings.setColumns(9);
				Settings.setRows(9);
				Settings.setNumberOfBombs(10);
				break;
			case "Normal":
				Settings.setColumns(16);
				Settings.setRows(16);
				Settings.setNumberOfBombs(40);
				break;
			case "Hard":
				Settings.setColumns(30);
				Settings.setRows(16);
				Settings.setNumberOfBombs(99);
				break;
		}
	}

	public void placeBombs() {
		promptDifficulty();
		clearBombGrid();
		clearLabelArray();
		hideAllCells();

		//Bombs placed randomly in bombGrid
		int bombsPlaced = 0;
		Random r = new Random();
		while (bombsPlaced < Settings.getNumberOfBombs()) {
			int x = r.nextInt(Settings.getColumns());
			int y = r.nextInt(Settings.getRows());
			if (bombGrid[x][y] != "b") {
				bombGrid[x][y] = "b";
				try {
					BufferedImage mine = ImageIO.read(getClass().getResource("/mine.jpg"));
					labelArray[x][y] = mine;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "mine.jpg is either corrupt or missing.",
							"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					System.exit(-1);
				}
				bombsPlaced++;
			}
		}
		printBombGrid();
		//Surrounding number of bombs is determined for all bombless squares
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				if (bombGrid[x][y] != "b") {
					
					int surroundingBombs = 0;
					
					for (int d = -1; d <= 1; d++) {
						if (x + d >= 0 && x + d < Settings.getColumns()) { //index in range
							//Checks 3 top squares for bombs
							if (y - 1 >= 0 && bombGrid[x+d][y-1] == "b") {
								surroundingBombs++;
							}
							//Check 3 bottom squares for bombs
							if ( y + 1 < Settings.getRows() && bombGrid[x+d][y+1] == "b") {
								surroundingBombs++;
							}
						}
					}

					//Check 2 middle squares for bombs
					if ( x + 1 < Settings.getColumns() && bombGrid[x + 1][y] == "b") { //index in range
						surroundingBombs++;
					}
					if ( x - 1 >= 0 && bombGrid[x - 1][y] == "b") { //index in range
						surroundingBombs++;
					}

					//Sets value for number of surrounding bombs in each square
					bombGrid[x][y] = Integer.toString(surroundingBombs);
					
					try {
						switch (surroundingBombs) {
						case 1:
							BufferedImage label1 = ImageIO.read(getClass().getResource("/label-1.jpg"));
							labelArray[x][y] = label1;
							break;
						case 2:
							BufferedImage label2 = ImageIO.read(getClass().getResource("/label-2.jpg"));
							labelArray[x][y] = label2;
							break;
						case 3: 
							BufferedImage label3 = ImageIO.read(getClass().getResource("/label-3.jpg"));
							labelArray[x][y] = label3;
							break;
						case 4:
							BufferedImage label4 = ImageIO.read(getClass().getResource("/label-4.jpg"));
							labelArray[x][y] = label4;
							break;
						case 5: 
							BufferedImage label5 = ImageIO.read(getClass().getResource("/label-5.jpg"));
							labelArray[x][y] = label5;
							break;
						case 6: 
							BufferedImage label6 = ImageIO.read(getClass().getResource("/label-6.jpg"));
							labelArray[x][y] = label6;
							break;
						case 7: 
							BufferedImage label7 = ImageIO.read(getClass().getResource("/label-7.jpg"));
							labelArray[x][y] = label7;
							break;
						case 8: 
							BufferedImage label8 = ImageIO.read(getClass().getResource("/label-8.jpg"));
							labelArray[x][y] = label8;
							break;
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Label images are either corrupt or missing.",
								"Minesweeper - Fatal Error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						System.exit(-1);
					}
				}
			}
		}
	}

	public void printBombGrid() {
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				System.out.print(bombGrid[x][y]);
			}
			System.out.println();
		}
	}
}










