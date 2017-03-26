import java.util.Random;

import javax.swing.JOptionPane;

public class BombSetter {
	
	private int numberOfBombs = 0;
	private String[][] bombGrid = new String[9][9];
	
	
	public String[][] setBombs() {
		//Player enters number of bombs
		while (numberOfBombs < 10 || numberOfBombs > 20) { 
			numberOfBombs= Integer.parseInt(JOptionPane.showInputDialog("Select a number of bombs between 10 and 20: "));
		} 
		
		//Bombs placed randomly in bombGrid
		int bombsPlaced = 0;
		while (bombsPlaced < numberOfBombs) {
			Random r = new Random();
			int x = r.nextInt(9);
			int y = r.nextInt(9);
			if (bombGrid[x][y] != "b") {
				bombGrid[x][y] = "b";
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
					bombGrid[i][j] = Integer.toString(surroundingBombs);
				}
			}
		}
		
		//Cheat: Reveals bombGrid in Console
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(bombGrid[j][i]);
			}
			System.out.println();
		}
		return bombGrid;
	}
	
}



