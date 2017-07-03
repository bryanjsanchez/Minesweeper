import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Minesweeper");
		MyPanel myPanel = new MyPanel();
		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(500, 200);
		myFrame.setSize(325, 375);
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.add(myPanel);
		myFrame.addMouseListener(myMouseAdapter);
		
		myPanel.resetButton.doClick();
	}
}
