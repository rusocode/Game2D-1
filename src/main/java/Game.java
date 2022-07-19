import display.Display;
import gfx.*;
import util.FpsTimer;

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
	private FpsTimer timer;

	private final String title;
	private final int width;
	private final int height;
	private static final int FPS = 1;
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

		// Cantidad de veces que llama a tick y render en cada segundo
		int fps = 70;
		/* Calcula la cantidad de tiempo en nanosegundos que hay entre cada frame. El tiempo se mide en nanosegundos
		 * (1_000_000_000 nanosegundos equivalen a 1 segundo) ya que es una unidad mucho mas especifica para la CPU. */
		double timePerTick = 1_000_000_000 / fps; // O se puede usar el literal 1e9
		/* Delta time (Δt) es el tiempo transcurrido desde el ultimo frame, o usando otras palabras, es el tiempo entre
		 * cada frame renderizado. */
		double delta = 0;
		// Variables para calcular el tiempo transcurrido
		long now;
		long lastTime = System.nanoTime(); // Devuelve la hr actual de la PC en nanosegundos

		while (running) {

			now = System.nanoTime();
			/* Obtiene el tiempo transcurrido desde la ultima vez que llamamos a esta linea de codigo. Luego divide esa
			 * cantidad de tiempo por la cantidad maxima de tiempo que podemos tener para llamar a tick y render. */
			delta += (now - lastTime)/* / timePerTick*/;
			lastTime = now;

			/* Ahora con el valor delta calculado, el programa sabe cuanto tiempo tiene hasta que tenga que llamar a los
			 * metodos tick y render nuevamente. */

			/* Si el delta es mayor o igual a 1 segundo, significa que tiene que llamar a tick y render para lograr 60
			 * frames por segundo. */
			if (delta >= timePerTick) {
				tick();
				render();
				// Resta 1 al delta para poder acumular 1 segundo desde 0
				delta -= timePerTick;
			}

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

		// Limpia el rectangulo (ventana) usando el color de fondo actual
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
