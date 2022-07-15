package display;

import javax.swing.*;
import java.awt.*;

/**
 * La clase Canvas es el lienzo para dibujar los graficos y la ventana seria el marco para mostrar el grafico dibujado.
 */

public class Display extends JFrame {

	private Canvas canvas;

	private final String title;
	private final int width;
	private final int height;

	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		createDisplay();
	}

	private void createDisplay() {

		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));

		add(canvas);
		pack();

	}

	public Canvas getCanvas() {
		return canvas;
	}


}
