package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Adicao {
	
	public static Image adicao(Image img1, Image img2, String valor1, String valor2) {

		double p1 = Double.parseDouble(valor1) / 100;
		double p2 = Double.parseDouble(valor2) / 100;

		try {

			int w1 = (int) img1.getWidth(); // Largura 1
			int h1 = (int) img1.getHeight(); // Altura 1
			int w2 = (int) img2.getWidth(); // Largura 2
			int h2 = (int) img2.getHeight(); // Altura 2
			int w;
			int h;

			if (w1 <= w2) {
				w = w1;
			} else {
				w = w2;
			}

			if (h1 <= h2) {
				h = h1;
			} else {
				h = h2;
			}

			PixelReader pr1 = img1.getPixelReader(); // Com o pixelReader é possivel pegar as cores
			PixelReader pr2 = img2.getPixelReader(); // Com o pixelReader é possivel pegar as cores

			WritableImage wi = new WritableImage(w, h); // Serve para escrever na imagem
			PixelWriter pw = wi.getPixelWriter();// Escrever o pixel. Utilizar o pw para gravar o que deseja

			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {

					Color prevColor1 = pr1.getColor(i, j);
					Color prevColor2 = pr2.getColor(i, j);

					double color1 = (prevColor2.getRed() * p2) + (prevColor1.getRed() * p1);
					double color2 = (prevColor2.getGreen() * p2) + (prevColor1.getGreen() * p1);
					double color3 = (prevColor2.getBlue() * p2) + (prevColor1.getBlue() * p1);

					Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
					pw.setColor(i, j, newColor);

				}
			}

			return wi;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
