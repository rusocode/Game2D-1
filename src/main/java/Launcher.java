import javax.swing.*;

public class Launcher {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Game("2D Game", 800, 600).start();
	}

}
