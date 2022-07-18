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
		double delta = (now - lastTime) / timePerTick;

		if (delta >= 1) {
			lastTime = now;
			return true;
		} else return false;
	}
}

