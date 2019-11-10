package br.ufrn.ePET;

import java.io.IOException;

import br.ufrn.ePET.frontEnd.TelaAdmPrincipal;
import br.ufrn.ePET.frontEnd.TelaDeCadastro;
import br.ufrn.ePET.frontEnd.TelaDeLogin;
import br.ufrn.ePET.frontEnd.TelaDisciplinaCadastrar;
import br.ufrn.ePET.frontEnd.TelaEventosCadastrar;
import br.ufrn.ePET.frontEnd.TelaNoticiaCadastrar;
import br.ufrn.ePET.frontEnd.TelaUsuarioPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainApplet extends Application {

	private Stage primeiro;
	private AnchorPane telaEscolha;
	private String usuario;
	private String senha;
	private Integer idPessoa; 
	private Integer idPetiano; 
	
	public void setUsuarioSenha(String user, String passwd, Integer idPessoa) {
		this.usuario = user;
		this.senha = passwd;
		this.idPessoa = idPessoa;
	}
	public String getSenha() {
		return this.senha;
	}
	public String getUsuario() {
		return this.usuario;
	}
	public Integer getIdPessoa() {
		return this.idPessoa;
	}
	public void setIdPetian(Integer idPetiano) {
		this.idPetiano = idPetiano;
	}
	public Integer getIdPetiano() {
		return this.idPetiano;
	}
	public Stage getPrimeiro() {
		return this.primeiro;
	}
	public AnchorPane getTelaEscolha() {
		return this.telaEscolha;
	}

    private final OkHttpClient httpClient = new OkHttpClient();
	
	@Override
	public void start(Stage primaryStage) {
		primeiro = primaryStage;
		primeiro.setTitle("ePET");
		initEscolha();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void initEscolha() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaDeLogin.fxml"));
			telaEscolha = (AnchorPane) loader.load();

			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
			TelaDeLogin telaDeLoginController = loader.getController();
			telaDeLoginController.setMainApp(this, httpClient);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void cadastrar() throws	IOException{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaDeCadastro.fxml"));
			telaEscolha = (AnchorPane) loader.load();

			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
			TelaDeCadastro telaDeCadastroController = loader.getController();
			telaDeCadastroController.setMainApp(this, httpClient);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void loginUsuario() throws	Exception{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaUsuarioPrincipal.fxml"));
			telaEscolha = (AnchorPane) loader.load();
			
			TelaUsuarioPrincipal telaUsuarioPrincipalController = loader.getController();
			telaUsuarioPrincipalController.setMainApp(this, httpClient);
			telaUsuarioPrincipalController.popularNoticias();
			telaUsuarioPrincipalController.popularEventosAbertos();
			telaUsuarioPrincipalController.popularParticipantes();
			telaUsuarioPrincipalController.popularOrganizadores();
			telaUsuarioPrincipalController.popularDisciplinas();
			telaUsuarioPrincipalController.popularTutoriasMinistradas();

			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void loginAdm() throws	Exception{
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaAdmPrincipal.fxml"));
			telaEscolha = (AnchorPane) loader.load();
			
			TelaAdmPrincipal telaAdmPrincipal = loader.getController();
			telaAdmPrincipal.setMainApp(this, httpClient);
			telaAdmPrincipal.popularNoticias();
			telaAdmPrincipal.popularEventosAbertos();
			telaAdmPrincipal.popularParticipantes();
			telaAdmPrincipal.popularOrganizadores();
			telaAdmPrincipal.popularDisciplinas();
			telaAdmPrincipal.popularTutoriasMinistradas();

			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public void sair() throws Exception{
        Request request = new Request.Builder()
                .url("http://localhost:8080/logout/")
                .build();
        httpClient.newCall(request).execute();
    	this.initEscolha();
	}
	public void cadastrarNoticia() throws Exception{
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaNoticiaCadastrar.fxml"));
			telaEscolha = (AnchorPane) loader.load();
			
			TelaNoticiaCadastrar telaNoticiaCadastrar = loader.getController();
			telaNoticiaCadastrar.setMainApp(this, httpClient);
	
			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void cadastrarEvento() throws Exception{
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaEventosCadastrar.fxml"));
			telaEscolha = (AnchorPane) loader.load();
			
			TelaEventosCadastrar telaEventosCadastrar = loader.getController();
			telaEventosCadastrar.setMainApp(this, httpClient);
	
			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void cadastrarDisciplina() throws Exception{
		try {	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApplet.class.getResource("views/TelaDisciplina.fxml"));
			telaEscolha = (AnchorPane) loader.load();
			
			TelaDisciplinaCadastrar telaDisciplinaCadastrar = loader.getController();
			telaDisciplinaCadastrar.setMainApp(this, httpClient);
	
			Scene scene = new Scene(telaEscolha);
			primeiro.setScene(scene);
			primeiro.show();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
