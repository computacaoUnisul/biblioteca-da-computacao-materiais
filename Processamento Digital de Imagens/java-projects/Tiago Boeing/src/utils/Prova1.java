package utils;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class Prova1 {

    public static Image moldura(Image img, double r, double g, double b, int largura) {

        int w = (int) img.getWidth();
        int h = (int) img.getHeight();

        WritableImage wi = new WritableImage(w, h);
        PixelReader pr = img.getPixelReader();
        PixelWriter pw = wi.getPixelWriter();

        Color corMoldura = new Color(r, g, b, 1);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                Color oldCor = pr.getColor(i, j);
                if (i < largura || i >= w - largura && i < w) {
                    pw.setColor(i, j, corMoldura);
                } else {
                    pw.setColor(i, j, oldCor);
                }
                if (j < largura || j >= h - largura && j < h) {
                    pw.setColor(i, j, corMoldura);
                }

            }
        }
        return wi;
    }

    public static Image divideInverteTonsCinza(Image image) {

        try{
            int width = (int) image.getWidth();
            int height = (int) image.getHeight();

            int divAltura = Math.round(height / 2);

            // imagem final
            PixelReader pr = image.getPixelReader();
            WritableImage wi = new WritableImage(width, height);
            PixelWriter pw = wi.getPixelWriter();

            // aplica efeitos
            Image negativa = PDIClass.negativa(image);
            Image tonsDeCinza = PDIClass.tonsDeCinza(image, 0, 0, 0);

            // imagem negativa
            PixelReader prNegativa = negativa.getPixelReader();
            WritableImage wiNegativa = new WritableImage(width, height);
            PixelWriter pwNegativa = wiNegativa.getPixelWriter();

            // imagem tons de cinza
            PixelReader prTonsDeCinza = tonsDeCinza.getPixelReader();
            WritableImage wiTonsDeCinza = new WritableImage(width, height);
            PixelWriter pwTonsDeCinza = wiTonsDeCinza.getPixelWriter();

            // superior
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < divAltura; j++) {
                    Color cor = prNegativa.getColor(i, j);
                    pw.setColor(i, j, cor);
                }
            }

            // inferior
            for (int i = 0; i < width; i++) {
                for (int j = divAltura; j < height; j++) {
                    Color oldCor = prTonsDeCinza.getColor(i, j);
                    pw.setColor(i, j, oldCor);
                }
            }

            return wi;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String identificaFormas(Image image){

        String formaFinal = "";
        int x = 0, y = 0;

        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        PixelReader pr = image.getPixelReader();

        // varremos altura x largura
        for (int altura = 0; altura < height; altura++) {
            for (int largura = 0; largura < width; largura++) {

                // descobre locais com pixel preto
                if (isPreto(pr.getColor(largura, altura))) {
                    x = largura;
                    y = altura;
                }

            }
        }

        try {
            if(vizinhoIsPreto(image, x, (y-1)) && vizinhoIsPreto(image, (x-1), y)){
                formaFinal = "QUADRADO";
            } else {
                formaFinal = "CIRCULO";
            }
        } catch (Exception e){
            formaFinal = "Nenhum dos dois!";
        }

        return formaFinal;
    }

    // recebe uma cor e informa se é preta ou não
    public static boolean isPreto(Color cor){
        if((cor.getRed() == 0 && cor.getGreen() == 0 && cor.getBlue() == 0) && cor.getOpacity() == 1){
            return true;
        } else {
            return false;
        }
    }

    // recebe uma imagem, posiçãoX, PosiçãoY
    // retorna => cor é preta (true ou false)
    public static boolean vizinhoIsPreto(Image image, int posX, int posY){
        Color corPixel = image.getPixelReader().getColor(posX, posY);

        // perguntamos ao método se a cor é preta
        return isPreto(corPixel);
    }


}
