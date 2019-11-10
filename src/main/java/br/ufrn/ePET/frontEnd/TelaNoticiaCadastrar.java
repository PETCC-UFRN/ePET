package br.ufrn.ePET.frontEnd;

import org.json.JSONObject;

import br.ufrn.ePET.MainApplet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelaNoticiaCadastrar {

	private MainApplet mainApplet;

    private OkHttpClient httpClient;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtCorpo;

    @FXML
    private TextField txtDataInicio;

    @FXML
    private TextField txtDataFim;

    @FXML
    private Text errorTitulo;

    @FXML
    private Text errorCorpo;

    @FXML
    private Text errorDataInicio;

    @FXML
    private Text errorDataFim;

    @FXML
    private Button btnCadastrar;

    @FXML
    void cadastrarEvento(ActionEvent event) throws Exception {

        JSONObject json = new JSONObject();
        json.put("titulo", txtTitulo.getText());
        json.put("corpo", txtCorpo.getText());
        json.put("inicio_exibicao", txtDataInicio.getText());
        json.put("limite_exibicao", txtDataFim.getText());

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
        

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/noticia-cadastro/" + mainApplet.getIdPetiano())
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
            	JSONObject errors = new JSONObject(response.body().string());
        		errorTitulo.setVisible(false);
        		errorCorpo.setVisible(false);
        		errorDataInicio.setVisible(false);
        		errorDataFim.setVisible(false);
        	}
        	else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Sucesso");
        		alert.setHeaderText("Parabens, Noticia cadastrada com sucesso");
            	mainApplet.loginAdm();
        	}
        }
    }
    
    public void setMainApp(MainApplet mainApplet, OkHttpClient http) {
		this.mainApplet = mainApplet;
		this.httpClient = http;
	}

}