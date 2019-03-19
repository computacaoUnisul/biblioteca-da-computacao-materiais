package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Moldura {

	public static Image moldura(Image image, int x1, int x2, int y1, int y2) {

		try {
			int w1 = (int)image.getWidth();
			int h1 = (int)image.getHeight();
			
			PixelReader pr1 = image.getPixelReader();
			
			WritableImage wi = new WritableImage(w1,h1);
			PixelWriter pw = wi.getPixelWriter();
			
			for (int i = 0; i < w1; i++) {
				for (int j = 0; j < h1; j++) {
					Color prevColor1 = pr1.getColor(i, j);
					pw.setColor(i, j, prevColor1);
				}
			}
			for (int k = x1; k < x2; k++) {
				Color prevColor1 = pr1.getColor(k, y1);
				if (k <= x2) {
					double color1 = (25/255);
					double color2 = (1);
					double color3 = (40/255);
					Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
					pw.setColor(k, y1, newColor);
				}
			}
			for (int k = x1; k < x2; k++) {
				Color prevColor1 = pr1.getColor(k, y2);
				if (k <= x2) {
					double color1 = (25/255);
					double color2 = (1);
					double color3 = (40/255);
					Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
					pw.setColor(k, y2, newColor);
				}
			}
			for (int k = y1; k < y2; k++) {
				Color prevColor1 = pr1.getColor(x1, k);
				if (k <= y2) {
					double color1 = (25/255);
					double color2 = (1);
					double color3 = (40/255);
					Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
					pw.setColor(x1, k, newColor);
				}
			}			
			for (int k = y1; k < y2; k++) {
				Color prevColor1 = pr1.getColor(x2, k);
				if (k <= y2) {
					double color1 = (25/255);
					double color2 = (1);
					double color3 = (40/255);
					Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
					pw.setColor(x2, k, newColor);
				}
			}	
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
