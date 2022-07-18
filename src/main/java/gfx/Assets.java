package gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Mejora el rendimiento del juego ya que los recursos se inicializan solo una vez desde esta clase.
 *
 * @author Juan
 */

public class Assets {

	private static final SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
	public static BufferedImage player, dirt, grass, stone, tree;
	private static final ArrayList<BufferedImage> sprites = new ArrayList<>();

	private static final int SHEET_WIDTH = sheet.getSheet().getWidth();
	private static final int SHEET_HEIGHT = sheet.getSheet().getHeight();
	private static final int SPRITE_SIZE = 32;

	/**
	 * Inicializa los sprites del SpriteSheet.
	 */
	public static void init() {
		player = sheet.crop(0, 0, SPRITE_SIZE, SPRITE_SIZE);
		dirt = sheet.crop(SPRITE_SIZE, 0, SPRITE_SIZE, SPRITE_SIZE);
		grass = sheet.crop(SPRITE_SIZE * 2, 0, SPRITE_SIZE, SPRITE_SIZE);
		stone = sheet.crop(SPRITE_SIZE * 3, 0, SPRITE_SIZE, SPRITE_SIZE);
		tree = sheet.crop(0, SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE);
	}

	/**
	 * Inicializa los sprites del SpriteSheet usando un ArrayList para almacenarlos.
	 * <p>
	 * Este metodo es util en caso de que el SpriteSheet tenga muchos sprites.
	 */
	public static void init2() {
		// Agrega los sprites de izquierda a derecha como un SpriteSheet tradicional
		for (int y = 0; y < SHEET_HEIGHT / SPRITE_SIZE; y++) {
			for (int x = 0; x < SHEET_WIDTH / SPRITE_SIZE; x++) {
				sprites.add(sheet.crop(x * SPRITE_SIZE, y * SPRITE_SIZE, SPRITE_SIZE, SPRITE_SIZE));
			}
		}
	}

	public static ArrayList<BufferedImage> getSprites() {
		return sprites;
	}


}
