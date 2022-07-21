package util;

/**
 * El tiempo se mide en nanosegundos ya que es una unidad mucho mas especifica para la CPU.
 * Hay 1000000000 nanosegundos en un segundo, lo que significa que la diferencia de tiempo por llamada de tick()
 * es igual a 1000000000/60. Que es aproximadamente 16666666 nanosegundos. Esto significa que cada vez que se
 * llama a tick(), el programa esperara 16666666 nanosegundos antes de volver a llamar a tick().
 * Lo que debes hacer es encontrar el tiempo entre el frame actual y el ultimo frame en nanosegundos. Este
 * valor dividido por el timestep (16666666) le dara un porcentaje decimal de cuanto ha pasado del tiempo
 * necesario. Si agrega este porcentaje decimal a la variable delta, cuando la variable delta >= 1, ha pasado
 * 1/60 de segundo, lo que significa que el programa ahora puede llamar a tick().
 * <br>
 * ¿Que es un timestep?
 * "step" es un proceso de calculo del siguiente estado del sistema. "timestep" es el intervalo de tiempo durante el
 * cual la simulacion progresara durante el siguiente "step".
 *
 * <a href="https://stackoverflow.com/questions/26838286/delta-time-getting-60-updates-a-second-in-java">...</a>
 * <a href="https://stackoverflow.com/questions/57710138/why-gameloops-render-more-times-than-updating#:~:text=A%20tick%20is%20whenever%20game,to%20a%20redstone%20circuit%20updating">...</a>.
 */

public class FpsTimer {

	private final double nsBetweenTicks;
	private double delta;
	private long lastTime, now;

	public FpsTimer(int fps) {
		nsBetweenTicks = 1e9 / fps;
		// Obtiene el tiempo del ultimo frame en nanosegundos
		lastTime = System.nanoTime();
	}

	public boolean check() {

		now = System.nanoTime();

		/* Calcula el tiempo transcurrido entre cada frame renderizado, que se obtiene restando el tiempo del primer
		 * y ultimo frame. Luego divide la diferencia en nsBetweenTicks (cantidad maxima de tiempo que podemos tener
		 * para llamar a tick y render) para obtener el tiempo en nanosegundos. Ahora con el valor delta calculado,
		 * el programa sabe cuanto tiempo tiene hasta que tenga que llamar a los metodos tick y render nuevamente.
		 * Esto hace posible que se ejecute en cualquier PC a la misma velocidad. */
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

