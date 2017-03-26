import javax.swing.JFrame;

public class NewGame {
	
	public JFrame clearGrid() {

		JFrame myFrame = new JFrame("Color Grid");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(325, 345);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);
		myFrame.setVisible(true);
		return myFrame;
	}
	
	
}
