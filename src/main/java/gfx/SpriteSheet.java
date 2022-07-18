package gfx;

import java.awt.image.BufferedImage;

/**
 * Serie de imagenes unidas en un mismo archivo una al lado de otra y que representan al mismo personaje (u objeto) en
 * distintas posiciones.
 */

public class SpriteSheet {

	private final BufferedImage sheet;

	public SpriteSheet(BufferedImage sheet) {
		this.sheet = sheet;
	}

	/**
	 * Corta un sprite definido por una region rectangular especificada del SpriteSheet. La BufferedImage devuelta
	 * comparte la misma matriz de datos que la imagen original.
	 *
	 * @param x      la coordenada X de la esquina superior izquierda de la region rectangular especificada.
	 * @param y      la coordenada Y de la esquina superior izquierda de la region rectangular especificada.
	 * @param width  el ancho de la region rectangular especificada.
	 * @param height la altura de la region rectangular especificada.
	 * @return una BufferedImage que es el sprite de esta BufferedImage.
	 */
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}

	public BufferedImage getSheet() {
		return sheet;
	}

}
