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

		// https://stackoverflow.com/questions/26838286/delta-time-getting-60-updates-a-second-in-java
		// https://stackoverflow.com/questions/57710138/why-gameloops-render-more-times-than-updating#:~:text=A%20tick%20is%20whenever%20game,to%20a%20redstone%20circuit%20updating.
		int fps = 60; // Cantidad de veces que llama a tick y render en cada segundo
		/* Calcula la cantidad de tiempo en nanosegundos que hay entre cada frame. El tiempo se mide en nanosegundos
		 * (1_000_000_000 nanosegundos equivalen a 1 segundo) ya que es una unidad mucho mas especifica para la CPU.
		 * El fin de esta variable es saber cuantos ns tienen que transcurrir para actualizar el frame 60 veces en un
		 * segundo.
		 * TODO El nombre nsBetweenTicks o nsPerTick tienen mas sentido. */
		double timePerTick = 1e9 / fps;
		double delta = 0;
		long now, lastTime = System.nanoTime(); // Devuelve la hr actual de la PC en nanosegundos
		int ticks = 0;

		while (running) {

			now = System.nanoTime();

			/* Obtiene el tiempo transcurrido entre cada frame renderizado. Luego divide esa cantidad de tiempo por la
			 * cantidad maxima de tiempo que podemos tener para llamar a tick y render. Ahora con el valor delta
			 * calculado, el programa sabe cuanto tiempo tiene hasta que tenga que llamar a los metodos tick y render
			 * nuevamente. */
			/* Acumula la diferencia entre la hr actual y ultima sobre timePerTick. Esto hace posible que se ejecute
			 * en cualquier PC a la misma velocidad. */
			delta += (now - lastTime) / timePerTick;

			// 86470255333400 - 86470255330500 = 2900 / 16.666.666 =

			System.out.println("now = " + now + " ____ lastTime " + lastTime);

			lastTime = now;

			/* Si el delta es mayor o igual a 1 segundo, significa que tiene que llamar a tick y render para lograr 60
			 * frames por segundo. Esto significa que cada vez que se llama a tick(), el programa esperara 16666666
			 * nanosegundos antes de volver a llamar a tick(). */
			if (delta >= 1) {
				tick();
				render();
				delta--;
				ticks++;
			}

			if (ticks >= 60) {
				System.out.println("Se actualizaron 60 frames en un segundo!");
				System.out.println("La posicion x del frame esta en " + x);
				ticks = 0;
				/* try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} */
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
