package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import utils.*;

import static javax.imageio.ImageIO.*;

public class SampleController {

    @FXML ImageView imageView1;
    @FXML ImageView imageView2;
    @FXML ImageView imageViewResultado;

    @FXML Label lblR;
    @FXML Label lblG;
    @FXML Label lblB;

    @FXML TextField txtPercR;
    @FXML TextField txtPercG;
    @FXML TextField txtPercB;

    @FXML TextField txtPosX;
    @FXML TextField txtPosY;

    // Adição e Subtração
    @FXML TextField txtPorcImg1;
    @FXML TextField txtPorcImg2;

    @FXML TitledPane painelCor;

    // radio buttons
    @FXML RadioButton radioX;
    @FXML RadioButton radioCruz;
    @FXML RadioButton radio3x3;

    // vars auxiliares
    private File file;

    private Image img1;
    private Image img2;
    private Image imgResultado;

    // LIMIARIARIZAÇÃO
    @FXML Slider slider;

    // onMouseEvent
    String array[][] = new String[2][3];

    // moldura
    private int x1, y1, x2, y2;
    @FXML CheckBox molduraAtiva;
    private String evtTarget;

    // inverter/espelhar/refletir
    @FXML Slider sliderRotate;
    @FXML Slider sliderRefletir;
    @FXML RadioButton eixoX;
    @FXML RadioButton eixoY;

    //	quadrantes
    @FXML Slider sliderQuad1;
    @FXML Slider sliderQuad2;

    // prova1 - Moldura interna na imagem
    @FXML TextField txtMolduraR;
    @FXML TextField txtMolduraG;
    @FXML TextField txtMolduraB;
    @FXML TextField txtMolduraPixels;

    // classificadores do opencv disponíveis
    // cole o arquivo .xml na pasta ./src/classificadores
    @FXML ComboBox classificadores;
    public void initialize() {
        classificadores.getItems().removeAll(classificadores.getItems());
        classificadores.getItems().addAll(
                "detect_faces.xml",
                "detect_full_body.xml",
                "frontalcatface.xml",
                "eye.xml",
                "eye_tree_eyeglasses.xml",
                "haarcascade_frontalcatface_extended.xml",
                "haarcascade_frontalface_alt.xml",
                "haarcascade_frontalface_alt_tree.xml",
                "haarcascade_frontalface_alt2.xml",
                "haarcascade_frontalface_default.xml",
                "haarcascade_lefteye_2splits.xml",
                "haarcascade_licence_plate_rus_16stages.xml",
                "haarcascade_lowerbody.xml",
                "haarcascade_profileface.xml",
                "haarcascade_righteye_2splits.xml",
                "haarcascade_russian_plate_number.xml",
                "haarcascade_smile.xml",
                "haarcascade_upperbody.xml",
                "hogcascade_pedestrians.xml"
        );
        classificadores.getSelectionModel().select("detect_faces.xml");
    }

    // CARREGA BIBLIOTECAS DO OPENCV
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    @FXML
    public void dividirQuadrantes() {
        imgResultado = PDIClass.dividirQuadrantes(img1, sliderQuad1.getValue(), sliderQuad2.getValue());
        atualizaImageResultado();
    }

    @FXML
    public void equalizaHistogramaDiagonal() {
        imgResultado = PDIClass.equalizaHistogramaDiagonal(img1);
        atualizaImageResultado();
    }

    @FXML
    public void equalizaHistograma() {
        imgResultado = HistogramUtils.equalizaHistograma(img1);
        atualizaImageResultado();
    }

    @FXML
    public void segmentacaoPorHistograma() {
        imgResultado = HistogramUtils.segmentacaoPorHistograma(img1);
        atualizaImageResultado();
    }

    @FXML
    public void segmentacao() {
        imgResultado = HistogramUtils.segmentacao(img1);
        atualizaImageResultado();
    }

    @FXML
    public void reducaoRuido() {

        WritableImage wi = new WritableImage((int)img1.getWidth(), (int)img1.getHeight());
        PixelWriter pw = wi.getPixelWriter();

        // vizinhança em cruz
        if(radio3x3.isSelected() == true) {

            for(int largura = 1; largura < (int)img1.getWidth()-1; largura++) {
                for(int altura = 1; altura < (int)img1.getHeight()-1; altura++) {

                    ArrayList<Double> medianas = ReducaoRuido.reducao3x3(img1, largura, altura);

                    Color corNova = new Color(medianas.get(0),
                            medianas.get(1),
                            medianas.get(2),
                            1);

                    pw.setColor(largura, altura, corNova);

                }
            }

            imageViewResultado.setImage(wi);
            imageViewResultado.setFitWidth(wi.getWidth());

        } else if (radioX.isSelected() == true) {

            for(int largura = 1; largura < (int)img1.getWidth()-1; largura++) {
                for(int altura = 1; altura < (int)img1.getHeight()-1; altura++) {

                    ArrayList<Double> medianas = ReducaoRuido.reducaoEmX(img1, largura, altura);

                    Color corNova = new Color(medianas.get(0),
                            medianas.get(1),
                            medianas.get(2),
                            1);

                    pw.setColor(largura, altura, corNova);

                }
            }

            imageViewResultado.setImage(wi);
            imageViewResultado.setFitWidth(wi.getWidth());

        } else if (radioCruz.isSelected() == true) {

            for(int largura = 1; largura < (int)img1.getWidth()-1; largura++) {
                for(int altura = 1; altura < (int)img1.getHeight()-1; altura++) {

                    ArrayList<Double> medianas = ReducaoRuido.reducaoEmCruz(img1, largura, altura);

                    Color corNova = new Color(medianas.get(0),
                            medianas.get(1),
                            medianas.get(2),
                            1);

                    pw.setColor(largura, altura, corNova);

                }
            }

            imageViewResultado.setImage(wi);
            imageViewResultado.setFitWidth(wi.getWidth());

        } else {
            exibeMsg("Selecione um tipo de redução", "Atenção", "É necessário selecionar uma técnica de aplicação da redução de ruído.", AlertType.WARNING);
        }

    }

    // seleção de imagem
    @FXML
    private File selectImagem() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser
                .ExtensionFilter(
                "Imagens", "*.jpg", "*.JPG",
                "*.png", "*.PNG",
                "*.gif", "*.GIF",
                "*.bmp", "*.BMP"
        ));

        fileChooser.setInitialDirectory(new File("./src/imgs"));
        File imgSelect = fileChooser.showOpenDialog(null);

        try {
            if(imgSelect != null) {
                return imgSelect;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // abrir arquivo 1
    @FXML
    public void abreImagem1() {
        file = selectImagem();
        if (file != null) {
            img1 = new Image(file.toURI().toString());

            // seta imageView 1
            imageView1.setImage(img1);
            imageView1.setFitWidth(270);
        }
    }

    // abrir arquivo 2
    @FXML
    public void abreImagem2() {
        file = selectImagem();
        if (file != null) {
            img2 = new Image(file.toURI().toString());

            // seta imageView 1
            imageView2.setImage(img2);
            imageView2.setFitWidth(270);
        }
    }

    private void atualizaImageResultado() {
        imageViewResultado.setImage(imgResultado);
        imageViewResultado.setFitWidth(imgResultado.getWidth());
        //imageViewResultado.setFitHeight(imgResultado.getHeight());
    }

    @FXML
    public void salvar() {
        if(imgResultado != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.png"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showSaveDialog(null);

            if(file != null) {
                BufferedImage bImg = SwingFXUtils.fromFXImage(imgResultado, null);
                try {
                    write(bImg, "PNG", file);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else {
            exibeMsg("Salvar imagem.", "Não é possivel salvar imagem.","Não há nenhuma imagem modificada.", AlertType.ERROR);
        }
    }

    private void verificaCor(Image img, int x, int y){
        try {
            Color cor = img.getPixelReader().getColor(x, y);
            lblR.setText("R: "+(int) (cor.getRed()*255));
            lblG.setText("G: "+(int) (cor.getGreen()*255));
            lblB.setText("B: "+(int) (cor.getBlue()*255));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ao clicar em mostrar cor da posição selecionada
    public void mostraCorTela() {

        if(!txtPosX.getText().equals("") && !txtPosX.getText().equals("")) {

            Integer eixoX = Integer.parseInt(txtPosX.getText());
            Integer eixoY = Integer.parseInt(txtPosY.getText());

            exibeMsg("Cor atual", "Dados da cor selecionada", CorAtualUtils.RGBdaPosicao(img1, eixoX, eixoY), AlertType.CONFIRMATION);
        }
    }

    @FXML
    public void rasterImg(MouseEvent evt) {
        ImageView iw = (ImageView)evt.getTarget();
        if (iw.getImage() != null) {
            verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());
        }
    }

    @FXML
    public void getPosition(MouseEvent evt) {
        ImageView iw = (ImageView)evt.getTarget();
        if (iw.getImage() != null) {

            painelCor.setExpanded(true);

            txtPosX.setText((int)evt.getX()+"");
            txtPosY.setText((int)evt.getY()+"");
        }
    }

    @FXML
    public void limpaLabel() {
        lblR.setText("R:");
        lblG.setText("G:");
        lblB.setText("B:");
    }

    @FXML
    public void tonsDeCinza() {
        imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
        atualizaImageResultado();
    }

    @FXML
    public void tonsDeCinzaPonderada() {

        int pcR = Integer.parseInt(txtPercR.getText());
        int pcG = Integer.parseInt(txtPercG.getText());
        int pcB = Integer.parseInt(txtPercB.getText());

        if(pcR + pcG + pcB <= 100) {

            if(pcR + pcG + pcB < 100) {
                exibeMsg("Escala de Cinza", "Aviso", "Valor é menor que 100, foi igual a : " + (pcR+pcG+pcB), AlertType.WARNING);
            }

            imgResultado = PDIClass.tonsDeCinza(img1, pcR, pcG, pcB);
            atualizaImageResultado();

        }else {
            exibeMsg("Escala de Cinza", "Erro", "Somatório maior que 100", AlertType.ERROR);
        }
    }

    @FXML
    public void negativa() {
        imgResultado = PDIClass.tonsDeCinza(img1, 0, 0, 0);
        imgResultado = PDIClass.negativa(img1);
        atualizaImageResultado();
    }

    @FXML
    public void limiarizacao() {
        double valor = slider.getValue();
        valor = valor / 255;
        img1 = PDIClass.tonsDeCinza(img1,0,0,0);
        imgResultado = PDIClass.limiarizacao(img1,valor);
        atualizaImageResultado();
    }

    @FXML
    public void adicao() {

        String porcentagemImg1 = txtPorcImg1.getText();
        String porcentagemImg2 = txtPorcImg2.getText();;

        if(txtPorcImg1.getText().isEmpty()) { porcentagemImg1 = "50"; }
        if(txtPorcImg2.getText().isEmpty()) { porcentagemImg2 = "50"; }

        if((Double.parseDouble(porcentagemImg1) + Double.parseDouble(porcentagemImg2)) <= 100) {
            imgResultado = Adicao.adicao(img1, img2, porcentagemImg1, porcentagemImg2);
            atualizaImageResultado();
        } else {
            exibeMsg("Valores excedem 100%", "Erro", "Soma das duas porcentagem é maior que 100", AlertType.ERROR);
        }

    }

    @FXML
    public void subtracao() {
        imgResultado = Subtracao.subtracao(img1, img2);
        atualizaImageResultado();
    }

    @FXML
    public void rotate() {
        if(img1 != null) {
            imgResultado = img1;
            atualizaImageResultado();

            imageViewResultado.setRotate(sliderRotate.getValue());
        } else {
            exibeMsg("Nenhuma imagem", "Opsss", "Você precisa carregar uma imagem no local 1", AlertType.ERROR);
        }
    }

    @FXML
    public void refletir() {
        if(img1 != null) {

            imgResultado = img1;
            atualizaImageResultado();

            if(eixoX.isSelected()) {
                imageViewResultado.setRotationAxis(Rotate.X_AXIS);
                imageViewResultado.setRotate(sliderRefletir.getValue());
            }
            if(eixoY.isSelected()) {
                imageViewResultado.setRotationAxis(Rotate.Y_AXIS);
                imageViewResultado.setRotate(sliderRefletir.getValue());
            }

//			imageViewResultado.setRotate(sliderRefletir.getValue());

        } else {
            exibeMsg("Nenhuma imagem", "Opsss", "Você precisa carregar uma imagem no local 1", AlertType.ERROR);
        }
    }

    @FXML
    public void moldura(){

        if(evtTarget.equalsIgnoreCase("imageView1")) {
            imgResultado = Moldura.moldura(img1, x1, x2, y1, y2);
        }
        if(evtTarget.equalsIgnoreCase("imageView2")) {
            imgResultado = Moldura.moldura(img2, x1, x2, y1, y2);
        }

        atualizaImageResultado();
    }

    @FXML
    public void identificaSegmentos(){
        if(PDIClass.identificaSegmentos(img1)){
            exibeMsg("Não contínua", "FORMA ABERTA!", "Esta forma está ABERTA!", AlertType.ERROR);
        } else {
            exibeMsg("Contínua", "FORMA FECHADA!", "Esta forma está FECHADA!", AlertType.INFORMATION);
        }
    }

    @FXML
    public void mousePressed(MouseEvent evt){
        ImageView iw = (ImageView)evt.getTarget();

        // descobre em qual imagem estamos trabalhando
        if(evt.getTarget().equals(imageView1)) { evtTarget = "imageView1"; }
        if(evt.getTarget().equals(imageView2)) { evtTarget = "imageView2"; }


        if (molduraAtiva.isSelected() == true) {
            if (iw.getImage() != null ) {
                x1 = (int)evt.getX();
                y1 = (int)evt.getY();
            }
        }
    }

    @FXML
    public void mouseReleased(MouseEvent evt){
        ImageView iw = (ImageView)evt.getTarget();

        if (molduraAtiva.isSelected() == true) {
            if (iw.getImage() != null) {
                x2 = (int)evt.getX();
                y2 = (int)evt.getY();
                moldura();
            }
        }
    }

    @FXML
    public void prova1Moldura() {
        imgResultado = Prova1.moldura(img1, Double.parseDouble(txtMolduraR.getText()), Double.parseDouble(txtMolduraG.getText()), Double.parseDouble(txtMolduraB.getText()), Integer.parseInt(txtMolduraPixels.getText()));
        atualizaImageResultado();
    }

    @FXML
    public void prova1DivideInverteTonsCinza() {
        imgResultado = Prova1.divideInverteTonsCinza(img1);
        atualizaImageResultado();
    }

    @FXML
    public void prova1IdentificaFormas() {
        if(Prova1.identificaFormas(img1).equalsIgnoreCase("Nenhum dos dois!")){
            exibeMsg("", Prova1.identificaFormas(img1), Prova1.identificaFormas(img1), AlertType.ERROR);
        } else {
            exibeMsg("", Prova1.identificaFormas(img1), Prova1.identificaFormas(img1), AlertType.WARNING);
        }
    }

    @FXML
    public void OpenCVremocaoRuido3x3() throws IOException {
        if(criaCacheImagem(img1)){
            OpenCV.remocaoRuido3x3();

            File file = new File("./src/imgs/temp/ruido3x3.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void canny() throws IOException {
        if(criaCacheImagem(img1)){
            OpenCV.canny();

            File file = new File("./src/imgs/temp/canny.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void prewitt() throws IOException {
        if(criaCacheImagem(img1)){
            OpenCV.prewitt();

            File file = new File("./src/imgs/temp/prewitt.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);;

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void sobel() throws IOException {
        if(criaCacheImagem(img1)){
            OpenCV.sobel();

            File file = new File("./src/imgs/temp/sobel.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);;

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void laplace() throws  IOException {
        if(criaCacheImagem(img1)){
            OpenCV.laplace();

            File file = new File("./src/imgs/temp/laplace.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);;

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void erosao() throws IOException {
        if(criaCacheImagem(img1)){
            OpenCV.erosao();

            File file = new File("./src/imgs/temp/erosao.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);;

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void dilatacao() throws IOException {
        if(criaCacheImagem(img1)){
            OpenCV.dilatacao();

            File file = new File("./src/imgs/temp/dilatacao.png");
            BufferedImage bufferedImage = ImageIO.read(file);
            imgResultado = SwingFXUtils.toFXImage(bufferedImage, null);;

            atualizaImageResultado();
            limpaPastaCache();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void detectPatterns() throws IOException {
        if(criaCacheImagem(img1)){
            imgResultado = OpenCV.detectPatterns(new File("./src/imgs/temp/temp.png"), classificadores.getSelectionModel().getSelectedItem().toString());
            atualizaImageResultado();
            limpaCacheImagem();
        } else {
            System.out.println("Problemas no processo de cache");
        }
    }

    @FXML
    public void openCVTonsDeCinza(){
        if(criaCacheImagem(img1)) {
            file = OpenCV.tonsDeCinza(new File("./src/imgs/temp/temp.png"));
            imgResultado = new Image(file.toURI().toString());
            atualizaImageResultado();

            limpaPastaCache();
        } else {
            limpaPastaCache();
        }
    }

    private void exibeMsg(String titulo, String cabecalho, String msg, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void histogram(ActionEvent event) {

        try {
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Histogram.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle("Histograma");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

            // passa dados para geração do histograma
            Histogram controller = (Histogram)loader.getController();
            if (img1 != null)
                HistogramUtils.getGrafico(img1, controller.hist1);
            if (img2 != null)
                HistogramUtils.getGrafico(img2, controller.hist2);
            if (imgResultado != null)
                HistogramUtils.getGrafico(imgResultado, controller.hist3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // copia uma imagem para pasta temporária
    public Boolean criaCacheImagem(Image img) {

        // se pasta de cache não existir, cria
        if(!checkFolder("./src/imgs/temp/")){
            new File("./src/imgs/temp/").mkdirs();
        }

        try {
            File temp = new File("./src/imgs/temp/temp.png");
            BufferedImage bImg = SwingFXUtils.fromFXImage(img, null);

            if(write(bImg, "png", temp)){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            System.out.println("Algum erro ao criar cache da imagem");
        }
        return false;
    }

    public void limpaCacheImagem(){
        File temp = new File("./src/imgs/temp/temp.png");
        temp.delete();
    }

    public void limpaPastaCache(){
        File folder = new File("./src/imgs/temp");
        if (folder.isDirectory()) {
            File[] sun = folder.listFiles();
            for (File toDelete : sun) {
                toDelete.delete();
            }
        }
    }

    public Boolean checkFolder(String pasta) {
        File folder = new File(pasta);
        if(folder.exists()){
            return true;
        } else {
            return false;
        }
    }

}
