import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
		//TODO: on click, start new game

		NewGame currentGame = new NewGame();
		JFrame myFrame = currentGame.clearGrid();
		
		BombSetter bombSetter = new BombSetter();
		String[][] bombGrid = bombSetter.setBombs();
		
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter(bombGrid);
		myFrame.addMouseListener(myMouseAdapter);
		
		
		
	}
}