package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CorAtualUtils {
	
	// preto e branco
	public static String RGBdaPosicao(Image image, int posX, int posY) {
		
		String mensagem = "";
		
		try {
			
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(width, height);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					
					if(i == posX && j == posY) {
						Color corAtual = pr.getColor(i, j);
						
						String canalRed = (int) (corAtual.getRed()*255)+"";
						String canalGreen = (int) (corAtual.getGreen()*255)+"";
						String canalBlue = (int) (corAtual.getBlue()*255)+"";
		
						mensagem = "R = " + canalRed + "\n"
									+ "G = " + canalGreen + "\n"
									+ "B = " + canalBlue;
					}
				}
			}
			
			return mensagem;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
