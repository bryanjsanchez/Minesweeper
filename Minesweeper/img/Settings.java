
public class Settings {
	
	private static final Object[] DIFFICULTY_OPTIONS = {"Easy", "Normal", "Hard"};
	private static Object difficulty = "";
	
	private static int rows = 9;
	private static int columns = 9;
	
	private static int numberOfBombs = 0;
	
	public static boolean isNewGame = true;
	
	
	public static int getNumberOfBombs() {
		return numberOfBombs;
	}
	public static void setNumberOfBombs(int numberOfBombs) {
		Settings.numberOfBombs = numberOfBombs;
	}
	public static int getRows() {
		return rows;
	}
	public static void setRows(int rows) {
		Settings.rows = rows;
	}
	public static int getColumns() {
		return columns;
	}
	public static void setColumns(int columns) {
		Settings.columns = columns;
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
	
}
