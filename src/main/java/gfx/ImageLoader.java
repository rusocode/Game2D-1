package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Clase encargada de cargar imagenes ubicadas en la ruta de clases (classpath).
 */

public class ImageLoader {

	/**
	 * Carga la imagen utilizando la ruta especificada.
	 *
	 * @param path ruta de imagen.
	 * @return un objeto BufferedImage que contiene la imagen.
	 */
	public static BufferedImage loadImage(String path) {
		try {
			// Devuelve un BufferedImage como resultado de decodificar una URL suministrada con un ImageReader
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}