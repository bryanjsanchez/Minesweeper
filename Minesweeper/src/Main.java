import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		JFrame myFrame = new JFrame("Minesweeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(325, 345);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);
		myFrame.setVisible(true);
		
		BombSetter bombSetter = new BombSetter();
		String[][] bombGrid = bombSetter.setBombs();
		int emptySquares = 81 - bombSetter.getNumberOfBombs();
		
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter(bombGrid, emptySquares);
		myFrame.addMouseListener(myMouseAdapter);
	}
}
