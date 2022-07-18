import display.Display;
import gfx.*;

import java.awt.*;
import java.awt.image.*;

/**
 * Se encarga de manejar la logica del juego a travez del game loop.
 * <p>
 * La clase BufferStrategy es como una pantalla oculta que se usa para de evitar parpadedos.
 * La clase Graphics seria como un pincel para dibujar en el lienzo.
 */

public class Game implements Runnable {

	private BufferStrategy buffer;
	private Graphics g;

	private Display display;
	private Thread thread;
	private SpriteSheet sheet;
	private BufferedImage image;

	private final String title;
	private final int width;
	private final int height;
	private boolean running;

	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	/**
	 * Game loop.
	 */
	@Override
	public void run() {

		init();

		while (running) {
			update();
			render();
		}

		stop();

	}

	/**
	 * Crea la ventana y carga los recursos.
	 */
	private void init() {
		display = new Display(title, width, height);
		Assets.init();
	}

	/**
	 * Actualiza las posiciones, etc.
	 */
	private void update() {

	}

	/**
	 * Dibuja en pantalla utilizando un buffer.
	 */
	private void render() {
		// Obtiene el buffer del lienzo
		buffer = display.getCanvas().getBufferStrategy();
		if (buffer == null) {
			// Crea 3 buffers para el lienzo en caso de que no haya uno
			display.getCanvas().createBufferStrategy(3);
			return;
		}

		// Obtiene el pincel
		g = buffer.getDrawGraphics();

		// Limpia el rectangulo (ventana) usando el color de fondo actual
		g.clearRect(0, 0, width, height);

		g.drawImage(Assets.dirt, 20, 20, null);

		// Dibuja una imagen
		// g.drawImage(image, 20, 20, null);

		// Dibuja un perro de color gris utilizando rectangulos
		/* g.setColor(Color.gray);
		g.fillRect(30, 50, 80, 15);
		g.fillRect(20, 55, 10, 5);
		g.fillRect(30, 60, 7, 15);
		g.fillRect(39, 60, 5, 15);
		g.fillRect(98, 60, 5, 15);
		g.fillRect(105, 60, 5, 15);
		g.fillRect(105, 40, 13, 13); */

		// Hace visible el buffer
		buffer.show();
		// Elimina este contexto de graficos y libera cualquier recurso del sistema que este utilizando
		g.dispose();

	}

	/**
	 * Ejecuta el juego.
	 */
	public synchronized void start() {
		// En caso de que el juego ya este ejecutado, no se ejecuta
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Detiene el juego.
	 */
	public synchronized void stop() {
		// En caso de que el juego ya este detenido, no se detiene
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
