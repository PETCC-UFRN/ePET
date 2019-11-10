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

public class TelaEventosCadastrar {

	private MainApplet mainApplet;

    private OkHttpClient httpClient;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtLocal;

    @FXML
    private TextField txtDataIncricaoInicio;

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
    private TextField txtDataIncricaoFim;

    @FXML
    private TextField txtAnexos;

    @FXML
    private TextField txtVagas;

    @FXML
    private TextField txtCH;

    @FXML
    private TextField txtDias;

    @FXML
    private TextField txtValor;

    @FXML
    private Button btnVoltar;

    @FXML
    void cadastrarEvento(ActionEvent event)  throws Exception{

        JSONObject json = new JSONObject();
        json.put("titulo", txtTitulo.getText());
        json.put("descricao", txtDescricao.getText());
        json.put("local", txtLocal.getText());
        json.put("d_inscricao", txtDataIncricaoInicio.getText());
        json.put("d_inscricao_fim", txtDataIncricaoFim.getText());
        json.put("participante_anexos", Boolean.parseBoolean(txtDataIncricaoFim.getText()));
        json.put("qtdVagas", Integer.parseInt(txtVagas.getText()));
        json.put("qtdCargaHoraria", Integer.parseInt(txtCH.getText()));
        json.put("qtdDias", Integer.parseInt(txtDias.getText()));
        json.put("valor", Double.parseDouble(txtValor.getText()));

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://localhost:8080/api/eventos-cadastrar/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
        	}
        	else {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Sucesso");
        		alert.setHeaderText("Parabens, Evento cadastrado com sucesso");
            	mainApplet.loginAdm();
        	}
        }
        catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @FXML
    void voltar(ActionEvent event) throws Exception {
    	mainApplet.loginAdm();
    }
    
    public void setMainApp(MainApplet mainApplet, OkHttpClient http) {
		this.mainApplet = mainApplet;
		this.httpClient = http;
	}

}