package utils;

import javafx.scene.image.Image;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class OpenCV {

    // CARREGA BIBLIOTECAS DO OPENCV
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static Image detectPatterns(File file, String classificador) {

        CascadeClassifier faceDetector = new CascadeClassifier("./src/classificadores/" + classificador);
        Mat image = Imgcodecs.imread(file.getAbsolutePath());

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

//        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 3);
        }

        MatOfByte mtb = new MatOfByte();
        Imgcodecs.imencode(".png", image, mtb);

        Image img = new Image(new ByteArrayInputStream(mtb.toArray()));
        return img;
    }

    public static File tonsDeCinza(File file){

        try {
            File input = file;
            BufferedImage image = ImageIO.read(input);

            byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
            mat.put(0, 0, data);

            Mat mat1 = new Mat(image.getHeight(),image.getWidth(), CvType.CV_8UC1);
            Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_RGB2GRAY);

            byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int)(mat1.elemSize())];
            mat1.get(0, 0, data1);
            BufferedImage image1 = new BufferedImage(mat1.cols(),mat1.rows(), BufferedImage.TYPE_BYTE_GRAY);
            image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

            File output = new File("./src/imgs/temp/grayscale.png");
            ImageIO.write(image1, "png", output);

            return new File("./src/imgs/temp/grayscale.png");

        } catch (IOException e) {
            System.out.println("Problema no tratamento do arquivo");
            return null;
        }
    }

    public static void canny(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/canny.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            Imgproc.Canny(matImgSrc, matImgDst, 10, 100);
            Imgcodecs.imwrite(outputFile, matImgDst);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void canny(){
//        final Mat img = Imgcodecs.imread("./src/imgs/temp/temp.png");
//        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
//
//        // aplica canny
//        Imgproc.Canny(img, img, threshold, threshold * 3, 3, true);
//
//        // guarda arquivo
//        Imgcodecs.imwrite("./src/imgs/temp/canny.png", img);
//    }

    public static void prewitt(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/prewitt.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            int kernelSize = 9;

            Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F) {
                {
                    put(0,0,-1);
                    put(0,1,0);
                    put(0,2,1);

                    put(1,0-1);
                    put(1,1,0);
                    put(1,2,1);

                    put(2,0,-1);
                    put(2,1,0);
                    put(2,2,1);
                }
            };

            Imgproc.filter2D(matImgSrc, matImgDst, -1, kernel);
            Imgcodecs.imwrite(outputFile, matImgDst);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sobel(){

        String inputFile = "./src/imgs/temp/temp.png";
        String outputFile = "./src/imgs/temp/sobel.png";

        // matriz do arquivo de entrada
        Mat matImgSrc = Imgcodecs.imread(inputFile);

        Mat grayMat = new Mat(); // guardar resultado de preto e branco
        Mat sobel = new Mat(); // guardar resultado

        Mat grad_x = new Mat();
        Mat abs_grad_x = new Mat();

        Mat grad_y = new Mat();
        Mat abs_grad_y = new Mat();

        // converte para preto e branco
        Imgproc.cvtColor(matImgSrc, grayMat, Imgproc.COLOR_BGR2GRAY);

        // calcula gradiente na direção horizontal
        Imgproc.Sobel(grayMat, grad_x, CvType.CV_16S, 1, 0, 3, 1, 0);

        // calcula gradiente na direção vertical
        Imgproc.Sobel(grayMat, grad_y, CvType.CV_16S, 0, 1, 3, 1, 0);

        // calcula valores absolutos de gradiente nas duas direções
        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);

        // calculando o gradiente resultante
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 1, sobel);

        // grava imagem na pasta
        Imgcodecs.imwrite(outputFile, sobel);
    }

    public static void erosao(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/erosao.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            int kernel = 5;

            Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * kernel + 1, 2 * kernel + 1), new Point(kernel, kernel));
            Imgproc.erode(matImgSrc, matImgDst, element);

            Imgcodecs.imwrite(outputFile, matImgDst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dilatacao(){
        try {
            String inputFile = "./src/imgs/temp/temp.png";
            String outputFile = "./src/imgs/temp/dilatacao.png";

            Mat matImgDst = new Mat();
            Mat matImgSrc = Imgcodecs.imread(inputFile);

            int kernel = 5;

            Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * kernel + 1, 2 * kernel + 1), new Point(kernel, kernel));
            Imgproc.dilate(matImgSrc, matImgDst, element);

            Imgcodecs.imwrite(outputFile, matImgDst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void laplace(){
        Mat src, src_gray = new Mat(), dst = new Mat();
        int kernel_size = 3;
        int scale = 1;
        int delta = 0;
        int ddepth = CvType.CV_16S;

        src = Imgcodecs.imread("./src/imgs/temp/temp.png", Imgcodecs.IMREAD_COLOR); // Load an image

        // Redução de ruído
        // Reduce noise by blurring with a Gaussian filter ( kernel size = 3 )
        Imgproc.GaussianBlur( src, src, new Size(3, 3), 0, 0, Core.BORDER_DEFAULT );

        // Converte para preto e branco
        Imgproc.cvtColor( src, src_gray, Imgproc.COLOR_RGB2GRAY );

        /// Aplica função de laplace
        Mat abs_dst = new Mat();
        Imgproc.Laplacian(src_gray, dst, ddepth, kernel_size, scale, delta, Core.BORDER_DEFAULT);

        // converting back to CV_8U
        Core.convertScaleAbs(dst, abs_dst);

        // guarda na pasta temporária
        Imgcodecs.imwrite("./src/imgs/temp/laplace.png", abs_dst);
    }

    public static void remocaoRuido3x3(){
        Mat imagemOriginal = Imgcodecs.imread("./src/imgs/temp/temp.png");
        Mat detectaBordas = new Mat();

        Imgproc.blur(imagemOriginal, detectaBordas, new Size(3, 3));

        Imgcodecs.imwrite("./src/imgs/temp/ruido3x3.png", detectaBordas);
    }

}

