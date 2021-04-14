package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class PDIClass {

    // preto e branco
    public static Image tonsDeCinza(Image image, double pcR, double pcG, double pcB) {

        try {

            int width = (int)image.getWidth();
            int height = (int)image.getHeight();

            PixelReader pr = image.getPixelReader();
            WritableImage wi = new WritableImage(width, height);
            PixelWriter pw = wi.getPixelWriter();


            for(int i = 0; i < width; i++) {
                for(int j = 0; j < height; j++) {

                    Color previousColor = pr.getColor(i, j);
                    double media = ((previousColor.getRed() +
                            previousColor.getGreen() +
                            previousColor.getBlue())
                            / 3);

                    if(pcR != 0 || pcG != 0 || pcB != 0 ) {
                        media = (previousColor.getRed() * pcR
                                + previousColor.getGreen() * pcG
                                + previousColor.getBlue() * pcB)
                                /100; //Media Ponderada do RGD
                    }

                    Color newColor = new Color(media, media, media, previousColor.getOpacity());
                    pw.setColor(i, j, newColor);

                }
            }

            return wi;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Função de limiarização
    public static Image limiarizacao(Image imagem, double limiar) {
        try {
            int w = (int)imagem.getWidth(); //Largura
            int h = (int)imagem.getHeight(); //Altura

            PixelReader pr = imagem.getPixelReader(); //Com o pixelReader é possivel pegar as cores
            WritableImage wi = new WritableImage(w, h); //Serve para escrever na imagem
            PixelWriter pw = wi.getPixelWriter(); //Escrever o pixel. Utilizar o pw para gravar o que deseja

            for (int i=0; i<w; i++) {
                for (int j = 0; j < h; j++) {
                    Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
                    Color corNova;

                    if(corAnterior.getRed() >= limiar) {
                        corNova = new Color(1, 1, 1, corAnterior.getOpacity());
                    }else {
                        corNova = new Color(0, 0, 0, corAnterior.getOpacity());
                    }
                    pw.setColor(i, j, corNova);
                }
            }

            return wi;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    //Função para converter imagem para negativa
    public static Image negativa(Image imagem) {
        try {
            int w = (int)imagem.getWidth(); //Largura
            int h = (int)imagem.getHeight(); //Altura

            PixelReader pr = imagem.getPixelReader(); //Com o pixelReader é possivel pegar as cores
            WritableImage wi = new WritableImage(w, h); //Serve para escrever na imagem
            PixelWriter pw = wi.getPixelWriter();//Escrever o pixel. Utilizar o pw para gravar o que deseja

            for (int i=0; i<w; i++) {
                for (int j=0; j<h; j++) {

                    Color corAnterior = pr.getColor(i, j); //Consegue pegar a cor de um determinado pixel
                    Color corNova;

                    corNova = new Color(
                            (1 - corAnterior.getRed()),
                            (1 - corAnterior.getGreen()),
                            (1 - corAnterior.getBlue()),
                            corAnterior.getOpacity()
                    );

                    pw.setColor(i, j, corNova);

                }
            }

            return wi;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    // calcula media de uma cor
    // return -> double
    public static Double mediaCores(double valorR, double valorG, double valorB) {
        return (valorR + valorG + valorB) /3;
    }

    // divide a imagem em quadrantes
    public static Image dividirQuadrantes(Image img, Double quad1, Double quad2) {

        int largura = (int)img.getWidth();
        int altura = (int)img.getHeight();

        // metade das dimensões
        int divLargura = largura/2;
        int divAltura = altura/2;

        ArrayList<Color> quadrante1 = new ArrayList<>();
        ArrayList<Color> quadrante2 = new ArrayList<>();
        ArrayList<Color> quadrante3 = new ArrayList<>();
        ArrayList<Color> quadrante4 = new ArrayList<>();

        PixelReader pr = img.getPixelReader();
        WritableImage wi = new WritableImage(largura, altura);
        PixelWriter pw = wi.getPixelWriter();

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                pw.setColor(i, j, pr.getColor(i, j));
            }
        }

        // quadrante 1
        if (quad1 == 1.0 || quad2 == 1.0) {
            for (int i = 0; i < divLargura; i++) {
                for (int j = 0; j < divAltura; j++) {
                    quadrante1.add(pr.getColor(i, j));
                }
            }

            int count = 0;
            for (int i = (divLargura)-1; i > 0; i--) {
                for (int j = (divAltura)-1; j > 0; j--) {
                    pw.setColor(i, j, quadrante1.get(count));
                    count++;
                }
                count++;
            }
        }

        // quadrante 2
        if (quad1 == 2.0 || quad2 == 2.0) {
            for (int i = divLargura; i < largura; i++) {
                for (int j = 0; j < divAltura; j++) {
                    quadrante2.add(pr.getColor(i, j));
                }
            }

            int count = 0;
            for (int i = largura-1; i > (divLargura)-1; i--) {
                for (int j = (divAltura)-1; j > 0; j--) {
                    pw.setColor(i, j, quadrante2.get(count));
                    count++;
                }
                count++;
            }
        }

        // quadrante 3
        if (quad1 == 3.0 || quad2 == 3.0) {
            for (int i = 0; i < divLargura; i++) {
                for (int j = divAltura; j < altura; j++) {
                    quadrante3.add(pr.getColor(i, j));
                }
            }

            int count = 0;
            for (int i = (divLargura)-1; i > 0; i--) {
                for (int j = altura-2; j > (divAltura)-1; j--) {
                    pw.setColor(i, j, quadrante3.get(count));
                    count++;
                }
                count++;
            }
        }

        // quadrante 4
        if (quad1 == 4.0 || quad2 == 4.0) {
            for (int i = divLargura; i < largura; i++) {
                for (int j = divAltura; j < altura; j++) {
                    quadrante4.add(pr.getColor(i, j));
                }
            }

            int count = 0;
            for (int i = largura-1; i > (divLargura)-1; i--) {
                for (int j = altura-2; j > (divAltura)-1; j--) {
//                    Color c = new Color(1, 0, 1, 1);
                    pw.setColor(i, j, quadrante4.get(count));
                    count++;
                }
                count++;
            }
        }

        return wi;

    }

    public static Image equalizaHistogramaDiagonal(Image image){

        try {

            int width = (int)image.getWidth();
            int height = (int)image.getHeight();

            // imagem original
            PixelReader pr = image.getPixelReader();
            WritableImage wi = new WritableImage(width, height);
            PixelWriter pw = wi.getPixelWriter();

            // passa imagem e recebe equalizada com base em histograma
            Image equalizada = HistogramUtils.equalizaHistograma(image);

            // Buffer para ler imagem equalizada
            PixelReader prEqualizada = equalizada.getPixelReader();
            WritableImage wiEqualizada = new WritableImage(width, height);
            PixelWriter pwEqualizada = wiEqualizada.getPixelWriter();


            for(int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {

                    // pega diagonais
                    if(i == j){

                        // diagonal secundária - imagem original
                        for(int a = i; a < width; a++){
                            Color previousColor = pr.getColor(i, a);
                            pw.setColor(i, a, previousColor);
                        }

                        // diagonal principal - imagem equalizada
                        for(int a = i; a < width; a++){
                            Color newColor = prEqualizada.getColor(a, j);
                            pw.setColor(a, j, newColor);
                        }

                        // linha separadora - em último para evitar ser sobrescrita
                        for(int a = i; a < width; a++){
                            Color newColor = new Color(0, 0, 0, 1);
                            pw.setColor(i, j, newColor);
                        }

                    }
                }
            }

            return wi;

        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // return true if = opened
    // return false if = closed
    public static Boolean identificaSegmentos(Image image) {
        
        return true;
    }

}
