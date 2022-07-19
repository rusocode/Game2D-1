package util;

public class FpsTimer {

	private final double timePerTick;
	private long lastTime;

	public FpsTimer(int fps) {
		timePerTick = 1e9 / fps;
		lastTime = System.nanoTime();
	}

	public boolean check() {
		long now = System.nanoTime();
		double delta = now - lastTime;

		if (delta >= timePerTick) {
			System.out.println("now = " + now);
			System.out.println("lastTime = " + lastTime);
			System.out.println("delta = " + delta);
			lastTime = now;
			return true;
		} else return false;
	}
}

