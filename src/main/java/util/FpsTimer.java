package util;

import display.Display;

/**
 * <h3>¿Por que necesitamos utilizar Delta Time?</h3>
 * Seguro que has visto alguna vez un viejo juego funcionando en un dispositivo de gama alta como si alguien hubiera
 * apretado el boton de avance rapido, o el caso contrario, un juego con unos requisitos elevados intentando funcionar
 * en un viejo dispositivo con un resultado desesperante. Para solucionar este problema tenemos que hacer nuestro juego
 * <i>framerate independiente</i>, y para conseguirlo debemos aplicar el Delta Time.
 * <p>
 * Delta Time (Δt) es el tiempo transcurrido desde el ultimo frame, en otras palabras, es el tiempo entre cada frame
 * renderizado.
 * <p>
 * El tiempo para el delta se mide en nanosegundos ya que es una unidad mucho mas especifica para la CPU. Los
 * nanosegundos no depende del SO, sino del microprocesador y se miden tomando como referencia los ciclos de reloj del
 * procesador. Hay 1000000000 nanosegundos en un segundo, lo que significa que la diferencia de tiempo por llamada de
 * tick() aplicando 60 fps, es igual a 1000000000/60, que es aproximadamente 16666666 nanosegundos (tambien conocido
 * como timePerTick). Esto significa que cada vez que se llama a tick(), el game loop espera 16666666 nanosegundos antes
 * de volver a llamarlo.
 * <p>
 * Para encontrar el delta, es necesario restar el tiempo en nanosegundos del frame actual y el ultimo frame. El
 * objetivo es hacer que los metodos tick() y render() se activen solo cuando haya pasado 1/60 de segundo, lo que
 * significa que se activan cuando delta >= 1/60 de segundo. La division ((now - lastTime) / timePerTick), que es
 * innecesaria, solo esta ahi para hacer que el delta actue como un porcentaje decimal de 1 de cuanto ha pasado del
 * tiempo necesario. El 1 representa el 100% de 1/60 fps. Es decir que cuando el valor delta sea >= 1, entonces paso
 * 1/60 de segundo, lo que significa que el game loop ahora puede llamar a los metodos tick() y render() nuevamente.
 * <b>Esto hace posible que el juego se ejecute en cualquier dispositivo a la misma velocidad sin importar cual sea la
 * velocidad de fps.</b>
 * <br><br>
 * Fuentes:
 * <br>
 * <a href="https://www.parallelcube.com/es/2017/10/25/por-que-necesitamos-utilizar-delta-time/">¿Por que necesitamos utilizar Delta Time?</a>
 * <a href="https://stackoverflow.com/questions/26838286/delta-time-getting-60-updates-a-second-in-java">¿Como obtener 60 actualizaciones por segundo?</a>
 * <a href="https://stackoverflow.com/questions/37678799/how-to-get-accurate-deltatime-in-java">¿Como obtener el Delta Time preciso en Java?</a>
 * <a href="https://stackoverflow.com/questions/57710138/why-gameloops-render-more-times-than-updating#:~:text=A%20tick%20is%20whenever%20game,to%20a%20redstone%20circuit%20updating">¿Por que el game loop se renderiza mas veces de las que se actualiza?</a>
 * <a href="https://www.youtube.com/watch?v=3lSzfidowTE">Java Delta Time Tutorial</a>
 *
 * @author Juan
 */

public class FpsTimer {

	private Display display;

	private int timer, ticks;
	private final double timePerTick;
	private double delta;
	// Frame ultimo y actual
	private long lastTime, now;

	public FpsTimer(int fps) {
		timePerTick = 1e9 / fps;
		lastTime = System.nanoTime();
	}

	/**
	 * Comprueba si el delta alcanzo 1/60 de segundo.
	 */
	public boolean check() {

		now = System.nanoTime();

		delta += now - lastTime;
		timer += now - lastTime;

		lastTime = now;

		if (delta >= timePerTick) {
			/* Despues de que el delta alcanzo 1/60, simplemente elimine 1/60 de segundo del delta haciendo que comience
			 * a contar desde el "desbordamiento" de tiempo, hasta que alcance 1/60 de segundo nuevamente. */
			delta -= timePerTick;
			ticks++;
			return true;
		} else return false;

	}

	/**
	 * Muestra la cantidad de frames que se renderizaron en 1 segundo.
	 */
	public void showFPS() {
		if (timer >= 1000000000) {
			System.out.println("FPS: " + ticks);
			ticks = 0;
			timer = 0;
		}
	}

}

