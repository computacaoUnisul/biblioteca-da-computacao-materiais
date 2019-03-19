package utils;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class HistogramUtils {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void getGrafico(Image img, BarChart<String, Number> grafico) {
        CategoryAxis eixoX = new CategoryAxis();
        NumberAxis eixoY = new NumberAxis();
        eixoX.setLabel("Canal");
        eixoY.setLabel("valor");
        XYChart.Series vlr = new XYChart.Series();
        vlr.setName("Intensidade");
        int[] hist = histogramaUnico(img);
        for (int i = 0; i < hist.length; i++) {
            vlr.getData().add(new XYChart.Data(i + "", hist[i]));
        }
        grafico.getData().addAll(vlr);
    }

    public static int[] histograma(Image img, int canal) {
        int[] qt = new int [256];
        PixelReader pr = img.getPixelReader();
        int w = (int)img.getWidth();
        int h = (int)img.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (canal == 1)
                    qt[(int)(pr.getColor(i, j).getRed()*255)]++;
                else
                if (canal == 2)
                    qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
                else
                    qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
            }
        }
        return qt;
    }

    public static int[] histogramaAcumulado(int[] hist) {
        int[] ret = new int[hist.length];
        int v1 = hist[0];
        for (int i = 0; i < hist.length - 1; i++) {
            ret[i] = v1;
            v1 += hist[i + 1];
        }
        return ret;
    }

    public static int[] histogramaUnico(Image img) {
        int[] qt = new int [256];
        PixelReader pr = img.getPixelReader();
        int w = (int)img.getWidth();
        int h = (int)img.getHeight();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                qt[(int)(pr.getColor(i, j).getRed()*255)]++;
                qt[(int)(pr.getColor(i, j).getGreen()*255)]++;
                qt[(int)(pr.getColor(i, j).getBlue()*255)]++;
            }
        }
        return qt;
    }

    public static Image equalizaHistograma(Image img) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        WritableImage wi = new WritableImage(w, h);
        PixelReader pr = img.getPixelReader();
        PixelWriter pw = wi.getPixelWriter();
        double n = w * h;
        int[] histR = histograma(img, 1);
        int[] histG = histograma(img, 2);
        int[] histB = histograma(img, 3);
        int[] histAcR = histogramaAcumulado(histR);
        int[] histAcG = histogramaAcumulado(histG);
        int[] histAcB = histogramaAcumulado(histB);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color cor = pr.getColor(i, j);
                Color corNova = new Color(((254.0 / n) * histAcR[(int) (cor.getRed() * 255)])/255,
                        ((254.0 / n) * histAcG[(int) (cor.getGreen() * 255)])/255,
                        ((254.0 / n) * histAcB[(int) (cor.getBlue() * 255)])/255, cor.getOpacity());
                pw.setColor(i, j, corNova);
            }
        }
        return wi;
    }

    public static Image segmentacao(Image img) {
        int w = (int) img.getWidth();
        int h = (int) img.getHeight();
        PixelReader pr = img.getPixelReader();
        WritableImage wi = new WritableImage(w, h);
        PixelWriter pw = wi.getPixelWriter();
        double[] limiar = new double[] { 0.33, 0.66 };

        for (int i = 1; i < w; i++) {
            for (int j = 1; j < h; j++) {

                Color oldCor = pr.getColor(i, j);
                Color newCor = null;
                if (oldCor.getRed() < limiar[0]) {
                    newCor = new Color(1, 0, 0, 1);
                } else {
                    if (oldCor.getRed() > limiar[0] && oldCor.getRed() < limiar[1]) {
                        newCor = new Color(1, 1, 0, 1);
                    } else {
                        newCor = new Color(0, 0, 1, 1);
                    }
                }
                pw.setColor(i, j, newCor);
            }
        }
        // return ruido(wi, 2, 3);
        return wi;
    }

    public static Image segmentacaoPorHistograma(Image img) {
        Image imgCinza = PDIClass.tonsDeCinza(img, 0, 0, 0);
        int[] hist = histograma(imgCinza, 1);
        int inicio = inicioHistograma(hist);
        int fim = fimHistograma(hist, inicio);
        System.out.println(inicio);
        System.out.println(fim);
        int maior = 0;
        int menor = 999999;
        int posMaior = 0, posMenor = 0, j = 0;

        for (int i : hist) {
            if (i > maior) {
                maior = i;
                posMaior = j;
            }
            if (i < menor && i > 0) {
                menor = i;
                posMenor = j;
            }
            j++;
        }

        return imgCinza;
    }

    public static int inicioHistograma(int[] hist) {
        int j = 0;
        for (int i : hist) {
            if (i > 0)
                return j;
            j++;
        }
        return j;
    }

    public static int fimHistograma(int[] hist, int inicio) {
        int j = inicio;
        for (int i : hist) {
            if (i == 0)
                return j;
            j++;
        }
        return j;
    }

}
