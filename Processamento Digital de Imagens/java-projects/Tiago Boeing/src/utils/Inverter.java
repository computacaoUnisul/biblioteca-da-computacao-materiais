package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Inverter {

//	// Função de inverter imagem
//	public static Image inverterImagem(Image img, int x, int y) {
//
//		
//		
//		
//		try {
//			
//			Double w = img.getWidth();
//			Double h = img.getHeight();
//
//			int x1; // Largura 1
//			int y1; // Altura 1
//			int x2; // Largura 2
//			int y2; // Altura 2
//
//			for (int i = 0; i < w; i++) {
//				for (int j = 0; j < h; j++) {
//
//					Color prevColor1 = pr1.getColor(i, j);
//					Color prevColor2 = pr2.getColor(i, j);
//
//					double color1 = (prevColor2.getRed() - prevColor1.getRed());
//					double color2 = (prevColor2.getGreen() - prevColor1.getGreen());
//					double color3 = (prevColor2.getBlue() - prevColor1.getBlue());
//
//					color1 = color1<0?0:color1;
//					color2 = color2<0?0:color2;
//					color3 = color3<0?0:color3;
//					
//					Color newColor = new Color(color1, color2, color3, prevColor1.getOpacity());
//					pw.setColor(i, j, newColor);
//
//				}
//			}
//
//			return wi;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}

	
}
