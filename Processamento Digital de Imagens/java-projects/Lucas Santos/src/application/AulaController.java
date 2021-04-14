package application;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.PDI;

public class AulaController {
	
	//GERAL
	@FXML Label lblRed;
	@FXML Label lblGreen;
	@FXML Label lblBlue;
	@FXML Label coordx;
	@FXML Label coordy;

	@FXML ImageView imageView1;
	@FXML ImageView imageView2;
	@FXML ImageView imageView3;

	//ESCALA DE CINZA
	@FXML TextField txtPRed;
	@FXML TextField txtPGreen;
	@FXML TextField txtPBlue;

	//LIMIARIZACAO
	@FXML Slider slider;
	
	private File f;
	private Image img1;
	private Image img2;
	private Image img3;
	
	//ADICAO / SUBTRACAO
	@FXML TextField txtPercertIMG1;
	@FXML TextField txtPercertIMG2;
	
	//MOLDURA
	private int x1, y1, x2, y2;
	@FXML CheckBox habilitaMoldura; 
	
	//RUIDO
	@FXML RadioButton radioC;
	@FXML RadioButton radioX;
	@FXML RadioButton radio3x3;
	
	
	//PROVA01
	@FXML ColorPicker c1;
	@FXML TextField p;

	@FXML
	public void escalaDeCinzaMedia(){
		img3 = PDI.escalaDeCinza(img1, 0, 0, 0);
		openImg3();
	}
	
	@FXML
	public void escalaDeCinzaPonderada(){
		double red = Double.parseDouble(txtPRed.getText());
		double green = Double.parseDouble(txtPGreen.getText());
		double blue = Double.parseDouble(txtPBlue.getText());
		double soma = (red + green + blue);
		if ( soma <= 0 || soma > 100) {
			exibeMsg("Erro!", "Porcentagens inválidas", "A soma dos valores tem que ser igual a 100!", AlertType.ERROR);
		}
		img3 = PDI.escalaDeCinza(img1, red, green, blue);
		openImg3();
	}
	
	@FXML
	public void limiarizacao(){
		double valor = slider.getValue();
		valor = valor / 255;
		img1 = PDI.escalaDeCinza(img1, 0, 0, 0);
		img3 = PDI.limiarizacao(img1, valor);
		openImg3();
	}
	
	@FXML
	public void negativo(){
		img1 = PDI.escalaDeCinza(img1, 0, 0, 0);
		img3 = PDI.negativo(img1);
		openImg3();
	}
	
	@FXML
	public void openImg1(){
		f = selecionaImagem();
		if(f != null){
			img1 = new Image(f.toURI().toString());
			imageView1.setImage(img1);
			imageView1.setFitWidth(img1.getWidth());
			imageView1.setFitHeight(img1.getHeight());
		}
	}
	
	@FXML
	public void openImg2(){
		f = selecionaImagem();
		if(f != null){
			img2 = new Image(f.toURI().toString());
			imageView2.setImage(img2);
			imageView2.setFitWidth(img2.getWidth());
			imageView2.setFitHeight(img2.getHeight());
		}
	}
	
	public void openImg3(){
		imageView3.setImage(img3);
		imageView3.setFitWidth(img3.getWidth());
		imageView3.setFitHeight(img3.getHeight());
	}
	
	@FXML
	public void limpaLabels(){
		lblGreen.setText("G");
		lblRed.setText("R");
		lblBlue.setText("B");
	}
	
	private void verificaCor(Image img, int x, int y){
		try {
			Color cor = img.getPixelReader().getColor(x, y);
			lblRed.setText("" + (int) (cor.getRed()*255));
			lblRed.setTextFill(Color.RED);
			lblGreen.setText("" + (int) (cor.getGreen()*255));
			lblGreen.setTextFill(Color.GREEN);
			lblBlue.setText("" + (int) (cor.getBlue()*255));
			lblBlue.setTextFill(Color.BLUE);
			
			coordx.setText("" + x);
			coordy.setText("" + y);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void rasterIMG(MouseEvent evt){
		ImageView iw = (ImageView)evt.getTarget();
		if (iw.getImage() != null) {
			verificaCor(iw.getImage(), (int)evt.getX(), (int)evt.getY());
		}
	}
	
	private File selecionaImagem(){
		FileChooser filechoose = new FileChooser();
		filechoose.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.jpg", "*.JPG", 
																						"*.png", "*.PNG",
																						"*.gif", "*.GIF",
																						"*.bmp", "*.BMP"));
//		filechoose.setInitialDirectory(new File("/Users/lucas/Documents/Unisul/02 - Processamento Digital de Imagens/img/"));
		File imgSelec = filechoose.showOpenDialog(null);
		try {
			if (imgSelec != null) {
				return imgSelec;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@FXML
	public void salvar(){
		if(img3 != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagem", "*.png"));
//			fileChooser.setInitialDirectory(new File("/Users/lucas/Documents/Unisul/02 - Processamento Digital de Imagens/img/save"));
			File file = fileChooser.showSaveDialog(null);
			if (file != null) {
				BufferedImage bImg = SwingFXUtils.fromFXImage(img3, null);
				try {
					ImageIO.write(bImg, "PNG", file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				exibeMsg("Salvar imagem.", "Não é possível salvar a imagem", "Não há nenhuma imagem modificada.", AlertType.ERROR);
			}
		}
	}

	@FXML
	public void adicao(){
		img3 = PDI.adicao(img1, img2, txtPercertIMG1.getText(), txtPercertIMG2.getText());
		openImg3();
	}
	
	@FXML
	public void subtracao(){
		img3 = PDI.subtracao(img1, img2);
		openImg3();
	}

	@FXML
	public void ruido(){
		if (radioC.isSelected()) {
			img3 = PDI.ruido(img1, 3);
		}
		if (radioX.isSelected()) {
			img3 = PDI.ruido(img1, 2);
		}
		if (radio3x3.isSelected()) {
			img3 = PDI.ruido(img1, 1);
		}
		openImg3();
	}
	
	@FXML
	public void moldura(){
		img3 = PDI.moldura(img1, x1, x2, y1, y2);
		openImg3();
	}
	
	@FXML
	public void coordenada1(MouseEvent evt){
		ImageView iw = (ImageView)evt.getTarget();
		if (habilitaMoldura.isSelected() == true) {
			if (iw.getImage() != null ) {
				x1 = (int)evt.getX();
				y1 = (int)evt.getY();
			}
		}
	}
	
	@FXML
	public void coordenada2(MouseEvent evt){
		ImageView iw = (ImageView)evt.getTarget();
		if (habilitaMoldura.isSelected() == true) {
			if (iw.getImage() != null) {
				x2 = (int)evt.getX();
				y2 = (int)evt.getY();
				moldura();
			}
		}
	}
	
	private void exibeMsg(String title, String header, String msg, AlertType type){
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
	}
	
	@FXML
	public void rotate90(){
		img3 = PDI.rotate90(img1);
		openImg3();
	}
	
	@FXML
	public void rotate180(){
		img3 = PDI.rotate90(img1);
		img3 = PDI.rotate90(img3);
		openImg3();
	}
	
	public void rotate270(){
		img3 = PDI.rotate90(img1);
		img3 = PDI.rotate90(img3);
		img3 = PDI.rotate90(img3);
		openImg3();
	}
	
	public void rotatePadrao(){
		img3 = img1;
		openImg3();
	}
	
	@FXML
	public void openModalHistograma(ActionEvent event){
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ModalHistograma.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Histograma");
//			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.show();
			
			ModalHistogramaController controller = (ModalHistogramaController)loader.getController();
			  
			if(img1!=null)
				PDI.getGrafico(img1, controller.grafico1);
			if(img2!=null)
				PDI.getGrafico(img2, controller.grafico2);
			if(img3!=null)
				PDI.getGrafico(img3, controller.grafico3);
			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void equalizaHistograma(){
		img3 = PDI.equalizacaoHistograma(img1);
		openImg3();
	}

	@FXML
	public void questao1(){
		img3 = PDI.q1(img1, 0, 0, 0, 1, 4);
		openImg3();
	}

	@FXML
	public void prova01(){
		int n = Integer.parseInt(p.getText());
		img3 = PDI.prova01(img1, c1, n);
		openImg3();
	}

	@FXML
	public void prova02(){
		img3 = PDI.prova02(img1);
		openImg3();
	}

	@FXML
	public void prova03(){
		PDI.prova03(img1);
	}

	@FXML
	public void erosao(){
		PDI.erosao();
	}

	@FXML
	public void dilatacao(){
		PDI.dilatacao();
	}

	@FXML
	public void canny(){
		PDI.canny();
	}

	@FXML
	public void prewitt(){
		PDI.prewitt();
	}

	@FXML
	public void sobel(){
		PDI.sobel();
	}
	
	@FXML
	public void gaussian(){
		PDI.gaussian();
	}

}
