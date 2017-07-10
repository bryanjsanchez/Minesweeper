import javax.swing.JOptionPane;

public class Settings {
	private static final Object[] DIFFICULTY_OPTIONS = {"Easy", "Normal", "Hard"};
	private static Object difficulty = "";
	private static int rows = 9;
	private static int columns = 9;
	private static int numberOfBombs = 0;
	public static boolean isGameOver = false;

	public static int getNumberOfBombs() {
		return numberOfBombs;
	}
	public static int getRows() {
		return rows;
	}
	public static int getColumns() {
		return columns;
	}
	public static final Object[] getDifficultyOptions() {
		return DIFFICULTY_OPTIONS;
	}
	public static Object getDifficulty() {
		return difficulty;
	}
	public static void setDifficulty(Object setDifficulty) {
		Settings.difficulty = setDifficulty;
	}

	public static void promptDifficulty() {
		Settings.setDifficulty(JOptionPane.showInputDialog(null, "Select game difficulty:", "Minesweeper", JOptionPane.PLAIN_MESSAGE, null,
				Settings.getDifficultyOptions(), Settings.getDifficulty()));
		switch ((String) Settings.getDifficulty()) {
		case "Easy":
			columns = 9;
			rows = 9;
			numberOfBombs = 10;
			break;
		case "Normal":
			columns = 16;
			rows = 16;
			numberOfBombs = 40;
			break;
		case "Hard":
			columns = 30;
			rows = 16;
			numberOfBombs = 99;
			break;
		}
	}
}