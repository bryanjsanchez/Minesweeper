import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {

		JFrame myFrame = new JFrame("Minesweeper");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(500, 200);
		myFrame.setSize(325, 375);
		myFrame.setResizable(false);

		Bombs bombs = new Bombs();
		MyPanel myPanel = new MyPanel(bombs);
		myFrame.add(myPanel);
		myFrame.setVisible(true);

		bombs.setBombs();
		bombs.printBombGrid();
		
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		
		myFrame.addMouseListener(myMouseAdapter);
	}
}
