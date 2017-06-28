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
		
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter(myPanel.getBombGrid());
		myFrame.addMouseListener(myMouseAdapter);
	}
}
