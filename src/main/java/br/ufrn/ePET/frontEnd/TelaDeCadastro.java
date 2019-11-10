package br.ufrn.ePET.frontEnd;

import java.io.IOException;

import org.json.JSONObject;

import br.ufrn.ePET.MainApplet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelaDeCadastro {

	private MainApplet mainApplet;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private Button btnCadastrar;
    
    @FXML
    private Button btnVoltar;

    @FXML
    private Text errorEmail;

    @FXML
    private Text errorSenha;

    @FXML
    private Text errorNome;

    @FXML
    private Text errorCPF;
    
    private OkHttpClient httpClient;

    @FXML
    void cadastrar(ActionEvent event) throws Exception{
    	// form parameters
        JSONObject json = new JSONObject();
        json.put("nome", txtNome.getText());
        json.put("cpf", txtCPF.getText());
        json.put("email", txtEmail.getText());
        json.put("senha", txtSenha.getText());
        
        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
        

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/usuarios-cadastrar/")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	//System.out.println(response.body().string());
            	JSONObject errors = new JSONObject(response.body().string());
            	String[] errorsCampos = errors.getString("campo").split(",");
            	String[] errorsCamposMensagens = errors.getString("campoMenssagem").split(",");
            	//System.out.println(errorsCampos.get(0));
            	int index = 0;
        		errorEmail.setVisible(false);
        		errorCPF.setVisible(false);
        		errorNome.setVisible(false);
        		errorSenha.setVisible(false);
            	
            	for(String campo : errorsCampos) {
            		if(campo.equals( "cpf")) {
            			errorCPF.setText(errorsCamposMensagens[index]);
            			errorCPF.setVisible(true);
            		}
            		if(campo.equals("nome")) {
            			errorNome.setText(errorsCamposMensagens[index]);
            			errorNome.setVisible(true);
            		}
            		if(campo.equals("email")) {
            			errorEmail.setVisible(true);
            			errorEmail.setText(errorsCamposMensagens[index]);
            		}
            		if(campo.equals("senha")) {
            			errorSenha.setText(errorsCamposMensagens[index]);
            			errorSenha.setVisible(true);
            		}
            		++index;
            	}
        	}
        	else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Sucesso");
        		alert.setHeaderText("Parabens, usuário cadastrado com sucesso");
            	mainApplet.initEscolha();
        	}
        }

            // Get response body
            //System.out.println(response.body().string());
    }
    
    public void setMainApp(MainApplet mainApplet, OkHttpClient http) {
		this.mainApplet = mainApplet;
		this.httpClient = http;
	}
    
    @FXML
    void voltar(ActionEvent event) throws IOException{
    	mainApplet.initEscolha();
    }

}