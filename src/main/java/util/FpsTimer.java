package util;

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
 * El tiempo para el delta se mide en nanosegundos ya que es una unidad mucho mas especifica para la CPU. Hay
 * 1000000000 nanosegundos en un segundo, lo que significa que la diferencia de tiempo por llamada de tick() aplicando
 * 60 fps, es igual a 1000000000/60, que es aproximadamente 16666666 nanosegundos. Esto significa que cada vez que se
 * llama a tick(), el programa espera 16666666 nanosegundos antes de volver a llamar a tick().
 * <p>
 * Para encontrar el delta, es necesario restar el tiempo en nanosegundos del frame actual y el ultimo frame. La
 * diferencia se divide por el timestep* (16666666), que da un porcentaje decimal de cuanto ha pasado del tiempo
 * necesario. Este porcentaje decimal se agrega al delta (este no es el caso), y si condiciona delta >= 1 en verdadero,
 * entonces quiere decir que ha pasado 1/60 de segundo, lo que significa que el game loop ahora puede llamar a tick().
 * Con el valor delta calculado, el programa sabe cuanto tiempo tiene hasta que tenga que llamar a los metodos tick y
 * render nuevamente. Esto hace posible que el juego se ejecute en cualquier dispositivo a la misma velocidad.
 * <p>
 * TODO El ultimo paso es restar 1 de la variable delta. La razon por la que no establece la variable delta en 0 es
 * porque puede haber un timestep mayor que 1 y la variable delta estara por encima de 1, lo que significa que debera
 * tenerlo en cuenta la proxima vez que realice un bucle y llame al metodo tick().
 * <br><br>
 * *"step" es un proceso de calculo del siguiente estado del sistema. "timestep" es el intervalo de tiempo durante el
 * cual la simulacion progresara durante el siguiente "step".
 * <br><br>
 * Fuentes:
 * <br>
 * <a href="https://www.parallelcube.com/es/2017/10/25/por-que-necesitamos-utilizar-delta-time/">¿Por que necesitamos utilizar Delta Time?</a>
 * <a href="https://stackoverflow.com/questions/26838286/delta-time-getting-60-updates-a-second-in-java">...</a>
 * <a href="https://stackoverflow.com/questions/57710138/why-gameloops-render-more-times-than-updating#:~:text=A%20tick%20is%20whenever%20game,to%20a%20redstone%20circuit%20updating">...</a>
 */

public class FpsTimer {

	private final double nsBetweenTicks;
	private double delta;
	private long lastTime, now;

	public FpsTimer(int fps) {
		nsBetweenTicks = 1e9 / fps;
		lastTime = System.nanoTime();
	}

	public boolean check() {

		now = System.nanoTime();

		delta = (now - lastTime) / nsBetweenTicks;

		/* El 1 representa el 100% de 1/60 fps. Es decir que cuando el valor delta sea >= 1, entonces paso 1/60 de
		 * segundo, lo que significa que el programa ahora puede llamar a tick(). Si el valor delta es menor a 1,
		 * entonces solo tiene un porcentaje decimal de cuanto ha pasado del tiempo necesario. */
		if (delta >= 1) {
			lastTime = now;
			return true;
		} else return false;
	}
}

