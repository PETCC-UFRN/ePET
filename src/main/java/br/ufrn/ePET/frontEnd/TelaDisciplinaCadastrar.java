package br.ufrn.ePET.frontEnd;

import org.json.JSONObject;

import br.ufrn.ePET.MainApplet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelaDisciplinaCadastrar {

	private MainApplet mainApplet;
    
    private OkHttpClient httpClient;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCodDisciplina;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button btnCadastrar;

    @FXML
    void cadastrarDisciplina(ActionEvent event) throws Exception{

    	// form parameters
        JSONObject json = new JSONObject();
        json.put("nome", txtNome.getText());
        json.put("codigo", txtCodDisciplina.getText());
        
        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
        

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/disciplinas/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
            	//JSONObject errors = new JSONObject(response.body().string());
        	}
        	else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Sucesso");
        		alert.setHeaderText("Parabens, Disciplina cadastrada com sucesso");
            	mainApplet.loginAdm();
        	}
        }
    }
    
    public void setMainApp(MainApplet mainApplet, OkHttpClient http) {
		this.mainApplet = mainApplet;
		this.httpClient = http;
	}
    
    @FXML
    void voltar(ActionEvent event) throws Exception{
    	mainApplet.loginAdm();
    }

}
