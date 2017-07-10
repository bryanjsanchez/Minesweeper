public class Flags {
	public boolean[][] flagArray = new boolean[Settings.getColumns()][Settings.getRows()];
	
	public Flags() {
		for (int y = 0; y < Settings.getRows(); y++) {
			for (int x = 0; x < Settings.getColumns(); x++) {
				flagArray[x][y] = false;
			}
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