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

	private static final int sheetWidth = sheet.getSheet().getWidth();
	private static final int sheetHeight = sheet.getSheet().getHeight();
	private static final int spriteSize = 32;

	/**
	 * Inicializa los sprites del SpriteSheet.
	 */
	public static void init() {
		player = sheet.crop(0, 0, spriteSize, spriteSize);
		dirt = sheet.crop(spriteSize, 0, spriteSize, spriteSize);
		grass = sheet.crop(spriteSize * 2, 0, spriteSize, spriteSize);
		stone = sheet.crop(spriteSize * 3, 0, spriteSize, spriteSize);
		tree = sheet.crop(0, spriteSize, spriteSize, spriteSize);
	}

	/**
	 * Inicializa los sprites del SpriteSheet usando un ArrayList para almacenarlos.
	 * <p>
	 * Este metodo es util en caso de que el SpriteSheet tenga muchos sprites.
	 */
	public static void init2() {
		// Agrega los sprites de izquierda a derecha como un SpriteSheet tradicional
		for (int y = 0; y < sheetHeight / spriteSize; y++) {
			for (int x = 0; x < sheetWidth / spriteSize; x++) {
				sprites.add(sheet.crop(x * spriteSize, y * spriteSize, spriteSize, spriteSize));
			}
		}
	}

	public static ArrayList<BufferedImage> getSprites() {
		return sprites;
	}


}
