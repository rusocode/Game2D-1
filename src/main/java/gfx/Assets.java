package gfx;

import java.awt.image.BufferedImage;

/**
 * Recursos del juego.
 */

public class Assets {

	public static BufferedImage player, dirt, grass, stone, tree;
	private static final int WIDTH = 32, HEIGHT = 32;

	/**
	 * Inicializa las subimagenes del SpriteSheet solo una vez (optimizacion).
	 */
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
		player = sheet.crop(0, 0, WIDTH, HEIGHT);
		dirt = sheet.crop(WIDTH, 0, WIDTH, HEIGHT);
		grass = sheet.crop(WIDTH * 2, 0, WIDTH, HEIGHT);
		stone = sheet.crop(WIDTH * 3, 0, WIDTH, HEIGHT);
		tree = sheet.crop(0, HEIGHT, WIDTH, HEIGHT);
	}

}
