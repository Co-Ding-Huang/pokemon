import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Image {

	private void display() {
		JFrame frame = new JFrame();
		var source = "C:/Users/andre/Desktop/Pokemon/150_2.png";
		JLabel label = new JLabel (new ImageIcon(source));
		
		frame.add(BorderLayout.CENTER,label);
		frame.setSize(500,500);
		frame.setVisible(true);
		
	}
	public static void main (String[] args) {
		Image gui = new Image ();
		gui.display();
	}
}
