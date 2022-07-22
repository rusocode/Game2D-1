import display.Display;
import gfx.*;
import util.FpsTimer;

import java.awt.*;
import java.awt.image.*;

/**
 * Se encarga de manejar la logica del juego a travez del game loop que actua como subproceso.
 * <p>
 * Los nanosegundos no depende del SO, sino del microprocesador y se miden tomando como referencia los ciclos de reloj
 * del procesador.
 */

public class Game implements Runnable {

	// Es como una pantalla oculta que se usa para de evitar parpadedos
	private BufferStrategy buffer;
	// Seria como un pincel para dibujar en el lienzo
	private Graphics g;

	private Display display;
	private Thread thread;
	private FpsTimer timer;

	private final String title;
	private final int width;
	private final int height;
	private static final int FPS = 60;
	private int x;
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

			if (timer.check()) {
				tick();
				render();
			}

			timer.showFPS();

		}

		stop();

	}

	/**
	 * Carga los recursos y crea la ventana.
	 */
	private void init() {
		Assets.init();
		timer = new FpsTimer(FPS);
		display = new Display(title, width, height);
	}

	/**
	 * Actualiza las posiciones, etc.
	 */
	private void tick() {
		x++;
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

		// Limpia la ventana usando el color de fondo actual
		g.clearRect(0, 0, width, height);

		// Dibuja una imagen
		g.drawImage(Assets.dirt, x, 20, null);

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
