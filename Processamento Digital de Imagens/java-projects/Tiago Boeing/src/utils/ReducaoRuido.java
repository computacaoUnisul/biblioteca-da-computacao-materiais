package utils;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ReducaoRuido {

	public static ArrayList<Double> vizinhosR = new ArrayList<Double>();
	public static ArrayList<Double> vizinhosG = new ArrayList<Double>();
	public static ArrayList<Double> vizinhosB = new ArrayList<Double>();
	
	public static ArrayList<Double> medianaCanais = new ArrayList<Double>();
	
	// redução de ruído com vizinhança em X	
	//	params => Imagem, posição eixo X, posição eixo Y
	//	return => Arraylist com mediana dos três canais (R, G, B), nesta ordem
	// código necessita otimização de processamento (com imagens pesadas há travamentos)	
	public static ArrayList<Double> reducaoEmX(Image image, double posicaoX, double posicaoY) {
		
		medianaCanais.clear();
		limpaListas();
		
		try {
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			// largura X
			for(int contX = 0; contX < width; contX++) {
				
				// altura Y
				for(int contY = 0; contY < height; contY++) {

					// checa se está no pixel informado
					if(contX == posicaoX && contY == posicaoY) {
																
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							
							if(z == 0) {
								Color corVizinho = pr.getColor(contX-1, contY+1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}		
							if(z == 2) { 
								Color corVizinho = pr.getColor(contX+1, contY+1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 4) { 
								Color corVizinho = pr.getColor(contX, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 6) { 
								Color corVizinho = pr.getColor(contX-1, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 8) { 
								Color corVizinho = pr.getColor(contX+1, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}							
						}		
						
						// obrigatório antes de calcular mediana
						ordenaListas();
						
						medianaCanais.add(mediana(vizinhosR));
						medianaCanais.add(mediana(vizinhosG));
						medianaCanais.add(mediana(vizinhosB));
					}
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// retorna a mediana dos pixels na posição
		return medianaCanais;
	}
	
	// redução 3x3	
	public static ArrayList<Double> reducao3x3(Image image, double posicaoX, double posicaoY) {
		
		medianaCanais.clear();
		limpaListas();
		
		try {
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			// largura X
			for(int contX = 0; contX < width; contX++) {
				
				// altura Y
				for(int contY = 0; contY < height; contY++) {

					// checa se está no pixel informado
					if(contX == posicaoX && contY == posicaoY) {
																
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							
							if(z == 0) {
								Color corVizinho = pr.getColor(contX-1, contY+1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							
							if(z == 1) { 
								Color corVizinho = pr.getColor(contX, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 2) { 
								Color corVizinho = pr.getColor(contX+1, contY+1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 3) { 
								Color corVizinho = pr.getColor(contX-1, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 4) { 
								Color corVizinho = pr.getColor(contX, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 5) { 
								Color corVizinho = pr.getColor(contX+1, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 6) { 
								Color corVizinho = pr.getColor(contX-1, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 7) { 
								Color corVizinho = pr.getColor(contX, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 8) { 
								Color corVizinho = pr.getColor(contX+1, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}							
						}		
						
						// obrigatório antes de calcular mediana
						ordenaListas();
						
						medianaCanais.add(mediana(vizinhosR));
						medianaCanais.add(mediana(vizinhosG));
						medianaCanais.add(mediana(vizinhosB));
					}
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// retorna a mediana dos pixels na posição
		return medianaCanais;
	}
	
	// redução em cruz	
	public static ArrayList<Double> reducaoEmCruz(Image image, double posicaoX, double posicaoY) {
		
		medianaCanais.clear();
		limpaListas();
		
		try {
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			// largura X
			for(int contX = 0; contX < width; contX++) {
				
				// altura Y
				for(int contY = 0; contY < height; contY++) {

					// checa se está no pixel informado
					if(contX == posicaoX && contY == posicaoY) {
																
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							
							if(z == 1) { 
								Color corVizinho = pr.getColor(contX, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 3) { 
								Color corVizinho = pr.getColor(contX-1, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 4) { 
								Color corVizinho = pr.getColor(contX, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 5) { 
								Color corVizinho = pr.getColor(contX+1, contY);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}
							if(z == 7) { 
								Color corVizinho = pr.getColor(contX, contY-1);
								vizinhosR.add(corVizinho.getRed());
								vizinhosG.add(corVizinho.getGreen());
								vizinhosB.add(corVizinho.getBlue());
							}	
							
						}		
						
						// obrigatório antes de calcular mediana
						ordenaListas();
						
						medianaCanais.add(mediana(vizinhosR));
						medianaCanais.add(mediana(vizinhosG));
						medianaCanais.add(mediana(vizinhosB));
					}
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// retorna a mediana dos pixels na posição
		return medianaCanais;
	}
	
	// calcula mediana de uma lista a ser informada
	public static Double mediana(ArrayList<Double> lista) {		

		int restoDivisao = lista.size() % 2;
		
		// tem número ao centro
        if(restoDivisao > 0) {
            return lista.get(Math.round(lista.size() / 2));
        } else {
        	// caso não exista número ao centro
            int menor = (lista.size() /2) -1;
            int maior = (lista.size() /2);

            return (lista.get(menor) + lista.get(maior)) /2;
        }
		
	}
	
	public static void ordenaListas() {	
		Collections.sort(vizinhosR);
		Collections.sort(vizinhosG);
		Collections.sort(vizinhosB);
	}
	
	public static void limpaListas() {
		vizinhosR.clear();
		vizinhosG.clear();
		vizinhosB.clear();
	}
	
	
}
