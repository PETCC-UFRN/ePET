package br.ufrn.ePET.frontEnd;

import java.io.IOException;

import org.json.JSONObject;

import br.ufrn.ePET.MainApplet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TelaDeLogin {

	private MainApplet mainApplet;

    @FXML
    private Button btnAdm;

    @FXML
    private Button btnUsuario;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnCad;

    @FXML
    private Text txtError;

    @FXML
    private Text txtSemAut;

    private OkHttpClient httpClient;

    @FXML
    void openAdm(ActionEvent event) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/pessoas-usuario/")
                .addHeader("Authorization", Credentials.basic(txtUsuario.getText(), txtSenha.getText()))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	txtError.setVisible(true);
            	txtSemAut.setVisible(false);
        	}
        	else {
        		JSONObject conteudo = new JSONObject(response.body().string());
        		//System.out.println(conteudo.getJSONObject("tipo_usuario").getString("nome"));
        		if(! conteudo.getJSONObject("tipo_usuario").getString("nome").equals("petiano") &&
				   ! conteudo.getJSONObject("tipo_usuario").getString("nome").equals("tutor"))
        		{
                	txtError.setVisible(false);
                	txtSemAut.setVisible(true);
        		}
        		else {
	        		mainApplet.setUsuarioSenha(txtUsuario.getText(), txtSenha.getText(), (Integer)conteudo.get("idPessoa"));
        	        request = new Request.Builder()
        	                .url("http://localhost:8080/api/petianos-pessoa/"+mainApplet.getIdPessoa())
        	                .addHeader("Authorization", Credentials.basic(txtUsuario.getText(), txtSenha.getText()))
        	                .build();
        	        try (Response response2 = httpClient.newCall(request).execute()) {
        	            if (!response2.isSuccessful()) {//throw new IOException(response.body().toString());
        	            	System.out.println(response2);
        	            }
        	            else {
	                		conteudo = new JSONObject(response2.body().string());
	                		mainApplet.setIdPetian(conteudo.getInt("idPetiano"));
        	            }	
        	        }
        	        catch (Exception e) {
						System.out.println("error");
					}
        	        System.out.println(mainApplet.getIdPetiano());
	            	mainApplet.loginAdm();
        		}
        	}
        }
    }

    @FXML
    void openUsuario(ActionEvent event) throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:8080/api/pessoas-usuario/")
                .addHeader("Authorization", Credentials.basic(txtUsuario.getText(), txtSenha.getText()))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	txtError.setVisible(true);
            	txtSemAut.setVisible(false);
            	//System.out.println(errorsCampos.get(0));
        	}
        	else {
        		JSONObject conteudo = new JSONObject(response.body().string());
        		
        		mainApplet.setUsuarioSenha(txtUsuario.getText(), txtSenha.getText(), (Integer)conteudo.get("idPessoa"));
            	mainApplet.loginUsuario();
        	}
        }
    }

    @FXML
    void cadastrarUsuario(ActionEvent event) throws IOException {
    	mainApplet.cadastrar();
    }
    
    public void setMainApp(MainApplet mainApplet, OkHttpClient http) {
		this.mainApplet = mainApplet;
		this.httpClient = http;
	}

}
