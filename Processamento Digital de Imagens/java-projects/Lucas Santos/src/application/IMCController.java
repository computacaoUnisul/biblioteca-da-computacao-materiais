package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IMCController {
	@FXML TextField txtPeso;
	@FXML TextField txtAltura;
	@FXML TextField txtIMC;
	
	@FXML
	public void IMC(){
		double peso = Double.parseDouble(txtPeso.getText());
		double altura = Double.parseDouble(txtAltura.getText());
		double imc = peso / (altura*altura);
		txtIMC.setText(imc +"");
	}
	
	@FXML
	public void limpaTela() {
		txtPeso.setText("");
		txtAltura.setText("");
		txtIMC.setText("");
		txtPeso.requestFocus();
	}
}
