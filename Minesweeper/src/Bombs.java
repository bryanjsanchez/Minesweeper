import java.awt.image.BufferedImage;
import java.util.Random;

public class Bombs {
	private GraphicsManager graphicsManager = new GraphicsManager();
	private String[][] bombGrid = new String[Settings.getColumns()][Settings.getRows()];
	private BufferedImage[][] labelArray = new BufferedImage[Settings.getColumns()][Settings.getRows()];
	private boolean[][] revealedCellArray = new boolean[Settings.getColumns()][Settings.getRows()];

	public String[][] getBombGrid() {
		return bombGrid;
	}
	public boolean[][] getRevealedCell() {
		return revealedCellArray;
	}
	public BufferedImage[][] getLabelArray() {
		return labelArray;
	}
	
	public void revealCell(int x, int y) {
		this.revealedCellArray[x][y] = true;
	}
	
	private void hideCell(int x, int y) {
		this.revealedCellArray[x][y] = false;
	}
	
	private void hideAllCells() {
		revealedCellArray = new boolean[Settings.getColumns()][Settings.getRows()];
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

	public void placeBombs() {
		Settings.promptDifficulty();
		clearBombGrid();
		clearLabelArray();
		hideAllCells();
		//Bombs placed randomly in bombGrid
		Random r = new Random();
		int bombsPlaced = 0;
		while (bombsPlaced < Settings.getNumberOfBombs()) {
			int x = r.nextInt(Settings.getColumns());
			int y = r.nextInt(Settings.getRows());
			if (bombGrid[x][y] != "b") {
				bombGrid[x][y] = "b";
				labelArray[x][y] = graphicsManager.getMineImage();
				bombsPlaced++;
			}
		}
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
					if (surroundingBombs >= 1 && surroundingBombs <= 8) {
						labelArray[x][y] = graphicsManager.getLabels(surroundingBombs);
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
			System.out.print("\n");
		}
	}
}