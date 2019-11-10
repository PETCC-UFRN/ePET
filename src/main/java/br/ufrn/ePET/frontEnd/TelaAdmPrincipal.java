package br.ufrn.ePET.frontEnd;

import java.awt.event.KeyEvent;
import java.beans.DesignMode;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional.TxType;

import org.hibernate.id.IncrementGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import br.ufrn.ePET.MainApplet;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TelaAdmPrincipal {

	private MainApplet mainApplet;

	@FXML
    private TabPane tabPane;

    @FXML
    private Tab tabNoticias;

    @FXML
    private TableView<Noticia> noticiasTable;

    @FXML
    private TableColumn<Noticia, String> noticiaTitulo;

    @FXML
    private TableColumn<Noticia, String> noticiaCorpo;

    @FXML
    private TableColumn<Noticia, String> noticiaCriador;

    @FXML
    private TableColumn<Noticia, Integer> noticiaId;

    @FXML
    private Button btnEditarNoticias;

    @FXML
    private Button btnCadastrarNoticias;
    
    @FXML
    private Tab tabEventos;

    @FXML
    private TableView<Evento> tableAbertos1;

    @FXML
    private TableColumn<Evento, String> abertosTitulo1;

    @FXML
    private TableColumn<Evento, String> abertosDescricao1;

    @FXML
    private TableColumn<Evento, String> abertosLocal1;

    @FXML
    private TableColumn<Evento, Integer> abertosVagas1;

    @FXML
    private TableColumn<Evento, Double> abertosCH1;

    @FXML
    private TableColumn<Evento, Double> abertosValor1;

    @FXML
    private TableColumn<Evento, String> abertosDataInicio1;

    @FXML
    private TableColumn<Evento, Boolean> abertosInscrever1;

    @FXML
    private TableColumn<Evento, Integer> abertosId1;

    @FXML
    private TableView<Evento> tableAbertos;

    @FXML
    private TableColumn<Evento, String> abertosTitulo;

    @FXML
    private TableColumn<Evento, String> abertosDescricao;

    @FXML
    private TableColumn<Evento, String> abertosLocal;

    @FXML
    private TableColumn<Evento, Integer> abertosVagas;

    @FXML
    private TableColumn<Evento, Double> abertosCH;

    @FXML
    private TableColumn<Evento, Double> abertosValor;

    @FXML
    private TableColumn<Evento, String> abertosDataInicio;

    @FXML
    private TableColumn<Evento, Boolean> abertosInscrever;

    @FXML
    private TableColumn<Evento, Integer> abertosId;

    @FXML
    private Button btnConfirmarInscri;

    @FXML
    private TableView<Evento> tableAbertos2;

    @FXML
    private TableColumn<Evento, String> abertosTitulo2;

    @FXML
    private TableColumn<Evento, String> abertosDescricao2;

    @FXML
    private TableColumn<Evento, String> abertosLocal2;

    @FXML
    private TableColumn<Evento, Integer> abertosVagas2;

    @FXML
    private TableColumn<Evento, Double> abertosCH2;

    @FXML
    private TableColumn<Evento, Double> abertosValor2;

    @FXML
    private TableColumn<Evento, String> abertosDataInicio2;

    @FXML
    private TableColumn<Evento, Boolean> abertosInscrever2;

    @FXML
    private TableColumn<Evento, Integer> abertosId2;

    @FXML
    private Button btnRetirar;

    @FXML
    private Button btnEditarEvento;

    @FXML
    private Button btnEventoCadastrar;

    @FXML
    private Tab tabDisciplinas;

    @FXML
    private TableView<Disciplina> tableDisciplinas;

    @FXML
    private TableColumn<Disciplina, String> tabNomeDisciplina;

    @FXML
    private TableColumn<Disciplina, String> tabCodigoDisciplina;

    @FXML
    private TableColumn<Disciplina, String> tabNomeTutor;

    @FXML
    private TableColumn<Disciplina, Boolean> tabSolicitar;

    @FXML
    private TableColumn<Disciplina, Integer> tabIdDisciplina;

    @FXML
    private Button btnSolicitarTutoria;

    @FXML
    private Button btnCadastrarDisciplina;

    @FXML
    private Tab tabTutorias;

    @FXML
    private TableView<TutoriaMinistrada> tableTutoriasMinistradas;

    @FXML
    private TableColumn<TutoriaMinistrada, String> tabNomeTutoriaMinistrada;

    @FXML
    private TableColumn<TutoriaMinistrada, String> tabCodigoTutoriaMinistrada;

    @FXML
    private TableColumn<TutoriaMinistrada, String> tabNomeTutorTutoriaMinistrada;

    @FXML
    private TableColumn<TutoriaMinistrada, String> tabEmailTutoriaMinistrada;

    @FXML
    private TableColumn<TutoriaMinistrada, Boolean> tabDesmarcarTutoriaMinistrada;

    @FXML
    private TableColumn<TutoriaMinistrada, Integer> tabIdTutoriaMinistrada;

    @FXML
    private Button btnDesmarcarTutoriaMinistrada;

    @FXML
    private Button btnSair;
    
    private OkHttpClient httpClient;

    @FXML
    void sair(ActionEvent event) throws Exception{
    	mainApplet.sair();
    }
    
    public void setMainApp(MainApplet mainApplet, OkHttpClient http) {
		this.mainApplet = mainApplet;
		this.httpClient = http;
	}
    
    public void popularNoticias() throws Exception{
    	Request request = new Request.Builder()
                .url("http://localhost:8080/api/noticia/")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .build();
    	try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
            	JSONObject errors = new JSONObject(response.body().string());
        	}
        	else {
        		JSONObject conteudo = new JSONObject(response.body().string());
        		//System.out.println(conteudo.toString());
        		JSONArray jsonArray = conteudo.getJSONArray("content");
				noticiaTitulo.setCellValueFactory(new PropertyValueFactory<>(("noticiaTitulo")));
				noticiaTitulo.setCellFactory(TextFieldTableCell.forTableColumn());
				noticiaCorpo.setCellValueFactory(new PropertyValueFactory<>(("noticiaCorpo")));
				noticiaCorpo.setCellFactory(TextFieldTableCell.forTableColumn());
				noticiaCriador.setCellValueFactory(new PropertyValueFactory<>(("noticiaCriador")));
				noticiaCriador.setCellFactory(TextFieldTableCell.forTableColumn());
				noticiaId.setCellValueFactory(new PropertyValueFactory<>(("noticiaId")));
				noticiaId.setCellFactory(TextFieldTableCell.forTableColumn( new IntegerStringConverter()));
				List<Noticia> listaDeNoticias = new ArrayList<>();

        		for (int i = 0; i < jsonArray.length(); ++i) {
					try {
						JSONObject noticia = new JSONObject(jsonArray.get(i).toString().replace("{", "{\n")
																						 .replace("}", "}\n"));
						listaDeNoticias.add(new Noticia(noticia.getString("titulo"), 
														noticia.getString("corpo"),
														noticia.getJSONObject("petiano")
															    .getJSONObject("pessoa")
															    .getString("nome"),
														noticia.getInt("idNoticia")));
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
        		System.out.println("asidofjaosdfjao");
        		noticiasTable.setItems(FXCollections.observableArrayList(listaDeNoticias));
        		System.out.println(noticiaTitulo.getCellData(0));
        	}
        }
    	catch (Exception e) {
			System.out.println(e.toString());
		}

    }

    public void popularEventosAbertos() throws Exception{
    	Request request = new Request.Builder()
                .url("http://localhost:8080/api/eventos-abertos/")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .build();
    	try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
        	}
        	else {
        		JSONArray conteudo = new JSONArray(response.body().string());
        		abertosCH.setCellValueFactory(new PropertyValueFactory<>(("abertosCH")));
        		abertosDataInicio.setCellValueFactory(new PropertyValueFactory<>(("abertosDataInicio")));
        		abertosDescricao.setCellValueFactory(new PropertyValueFactory<>(("abertosDescricao")));
        		abertosId.setCellValueFactory(new PropertyValueFactory<>(("abertosId")));
        		abertosInscrever.setCellValueFactory(new PropertyValueFactory<>(("abertosInscrever")));
        		abertosInscrever.setCellFactory(CheckBoxTableCell.forTableColumn(abertosInscrever));
        		
        		abertosLocal.setCellValueFactory(new PropertyValueFactory<>(("abertosLocal")));
        		abertosTitulo.setCellValueFactory(new PropertyValueFactory<>(("abertosTitulo")));
        		abertosVagas.setCellValueFactory(new PropertyValueFactory<>(("abertosVagas")));
        		abertosValor.setCellValueFactory(new PropertyValueFactory<>(("abertosValor")));
        		List<Evento> listaDeEventosAbertos = new ArrayList<>();
        		
        		for (int i = 0; i < conteudo.length(); i++) {
        			JSONObject json = new JSONObject(conteudo.get(i).toString());
        			listaDeEventosAbertos.add(new Evento(json.getInt("idEvento"),
        												 json.getString("titulo"),
        												 json.getString("descricao"),
        												 json.getString("local"),
        												 json.getInt("qtdVagas"),
        												 json.getDouble("qtdCargaHoraria"),
        												 json.getDouble("valor"),
        												 json.getString("d_inscricao"),
        												 false));
					
				}
        		tableAbertos.setItems(FXCollections.observableArrayList(listaDeEventosAbertos));
        	}
        }
    	catch (Exception e) {
			System.out.println(e.toString());
		}

    }

    public void popularParticipantes() throws Exception{
    	Request request = new Request.Builder()
                .url("http://localhost:8080/api/participantes-pessoa/"+mainApplet.getIdPessoa())
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .build();
    	try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
        	}
        	else {
        		JSONObject conteudo = new JSONObject(response.body().string());
        		JSONArray jsonArray = conteudo.getJSONArray("content");
        		//System.out.println(jsonArray);
        		
        		abertosCH1.setCellValueFactory(new PropertyValueFactory<>(("abertosCH")));
        		abertosDataInicio1.setCellValueFactory(new PropertyValueFactory<>(("abertosDataInicio")));
        		abertosDescricao1.setCellValueFactory(new PropertyValueFactory<>(("abertosDescricao")));
        		abertosId1.setCellValueFactory(new PropertyValueFactory<>(("abertosId")));
        		abertosInscrever1.setCellValueFactory(new PropertyValueFactory<>(("abertosInscrever")));
        		abertosInscrever1.setCellFactory(CheckBoxTableCell.forTableColumn(abertosInscrever1));

        		abertosLocal1.setCellValueFactory(new PropertyValueFactory<>(("abertosLocal")));
        		abertosTitulo1.setCellValueFactory(new PropertyValueFactory<>(("abertosTitulo")));
        		abertosVagas1.setCellValueFactory(new PropertyValueFactory<>(("abertosVagas")));
        		abertosValor1.setCellValueFactory(new PropertyValueFactory<>(("abertosValor")));
        		List<Evento> listaDeParticipante = new ArrayList<>();
        		
        		for (int i = 0; i < jsonArray.length(); i++) {
        			JSONObject json1 = new JSONObject(jsonArray.get(i).toString());
        			JSONObject json = json1.getJSONObject("evento");
        			listaDeParticipante.add(new Evento(json1.getInt("idParticipantes"),
							 json.getString("titulo"),
							 json.getString("descricao"),
							 json.getString("local"),
							 json.getInt("qtdVagas"),
							 json.getDouble("qtdCargaHoraria"),
							 json.getDouble("valor"),
							 json.getString("d_inscricao"),
							 false));
        			System.out.println(listaDeParticipante.get(i).getabertosLocal());
				}
        		tableAbertos1.setItems(FXCollections.observableArrayList(listaDeParticipante));
        	}
        }
    	catch (Exception e) {
			System.out.println(e.toString());
		}

    }
    
    public void popularOrganizadores() {
    	Request request = new Request.Builder()
                .url("http://localhost:8080/api/organizadores-pessoa/"+mainApplet.getIdPessoa())
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .build();
    	try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response);
            	System.out.println(response.body().string());
        	}
        	else {
        		JSONArray conteudo = new JSONArray(response.body().string());
        		
        		abertosCH2.setCellValueFactory(new PropertyValueFactory<>(("abertosCH")));
        		abertosCH2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        		abertosDataInicio2.setCellValueFactory(new PropertyValueFactory<>(("abertosDataInicio")));
        		abertosDataInicio2.setCellFactory(TextFieldTableCell.forTableColumn());
        		abertosDescricao2.setCellValueFactory(new PropertyValueFactory<>(("abertosDescricao")));
        		abertosDescricao2.setCellFactory(TextFieldTableCell.forTableColumn());
        		abertosId2.setCellValueFactory(new PropertyValueFactory<>(("abertosId")));
        		abertosId2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        		abertosInscrever2.setCellValueFactory(new PropertyValueFactory<>(("abertosInscrever")));
        		abertosInscrever2.setCellFactory(CheckBoxTableCell.forTableColumn(abertosInscrever));

        		abertosLocal2.setCellValueFactory(new PropertyValueFactory<>(("abertosLocal")));
        		abertosLocal2.setCellFactory(TextFieldTableCell.forTableColumn());
        		abertosTitulo2.setCellValueFactory(new PropertyValueFactory<>(("abertosTitulo")));
        		abertosTitulo2.setCellFactory(TextFieldTableCell.forTableColumn());
        		abertosVagas2.setCellValueFactory(new PropertyValueFactory<>(("abertosVagas")));
        		abertosVagas2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        		abertosValor2.setCellValueFactory(new PropertyValueFactory<>(("abertosValor")));
        		abertosValor2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        		List<Evento> listaDeOrganizadores= new ArrayList<>();
        		
        		for (int i = 0; i < conteudo.length(); i++) {
        			JSONObject json1 = new JSONObject(conteudo.get(i).toString());
        			JSONObject json = json1.getJSONObject("evento");
        			listaDeOrganizadores.add(new Evento(json.getInt("idEvento"),
							 json.getString("titulo"),
							 json.getString("descricao"),
							 json.getString("local"),
							 json.getInt("qtdVagas"),
							 json.getDouble("qtdCargaHoraria"),
							 json.getDouble("valor"),
							 json.getString("d_inscricao"),
							 false));
        		}
        		tableAbertos2.setItems(FXCollections.observableArrayList(listaDeOrganizadores));
        	}
        }
    	catch (Exception e) {
			System.out.println(e.toString());
		}
    }
    
    public void popularDisciplinas() {
    	Request request = new Request.Builder()
                .url("http://localhost:8080/api/tutorias")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .build();
    	try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
            	JSONObject errors = new JSONObject(response.body().string());
        	}
        	else {
        		JSONObject conteudo = new JSONObject(response.body().string());
        		JSONArray jsonArray = conteudo.getJSONArray("content");

				tabIdDisciplina.setCellValueFactory(new PropertyValueFactory<>(("tabIdDisciplina")));
				tabCodigoDisciplina.setCellValueFactory(new PropertyValueFactory<>(("tabCodigoDisciplina")));
				tabNomeDisciplina.setCellValueFactory(new PropertyValueFactory<>(("tabNomeDisciplina")));
				tabNomeTutor.setCellValueFactory(new PropertyValueFactory<>(("tabNomeTutor")));
				tabSolicitar.setCellValueFactory(new PropertyValueFactory<>(("tabSolicitar")));
				tabSolicitar.setCellFactory(CheckBoxTableCell.forTableColumn(tabSolicitar));
				List<Disciplina> listaDeDisciplinas = new ArrayList<>();

        		for (int i = 0; i < jsonArray.length(); ++i) {
					try {
						JSONObject disciplina = new JSONObject(jsonArray.get(i).toString().replace("{", "{\n")
																						 .replace("}", "}\n"));
						System.out.println(disciplina);
						listaDeDisciplinas.add(new Disciplina(disciplina.getInt("idTutoria"),
								disciplina.getJSONObject("disciplina").getString("nome"),
								disciplina.getJSONObject("petiano").getJSONObject("pessoa").getString("nome"),
								disciplina.getJSONObject("disciplina").getString("codigo"),
								false));
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
        		tableDisciplinas.setItems(FXCollections.observableArrayList(listaDeDisciplinas));
        	}
        }
    	catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    public void popularTutoriasMinistradas() {
    	Request request = new Request.Builder()
                .url("http://localhost:8080/api/tutorias-ministradas")
                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
                .build();
    	try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {//throw new IOException(response.body().toString());
            	System.out.println(response.body().string());
            	JSONObject errors = new JSONObject(response.body().string());
        	}
        	else {
        		JSONObject conteudo = new JSONObject(response.body().string());
        		System.out.println(conteudo.toString());
        		JSONArray jsonArray = conteudo.getJSONArray("content");
        		System.out.println(jsonArray.toString());
        		
        		tabIdTutoriaMinistrada.setCellValueFactory(new PropertyValueFactory<>(("tabIdTutoriaMinistrada")));
        		tabNomeTutoriaMinistrada.setCellValueFactory(new PropertyValueFactory<>(("tabNomeTutoriaMinistrada")));
        		tabCodigoTutoriaMinistrada.setCellValueFactory(new PropertyValueFactory<>(("tabCodigoTutoriaMinistrada")));
        		tabNomeTutorTutoriaMinistrada.setCellValueFactory(new PropertyValueFactory<>(("tabNomeTutorTutoriaMinistrada")));
        		tabEmailTutoriaMinistrada.setCellValueFactory(new PropertyValueFactory<>(("tabEmailTutoriaMinistrada")));
        		tabDesmarcarTutoriaMinistrada.setCellValueFactory(new PropertyValueFactory<>(("tabDesmarcarTutoriaMinistrada")));
        		tabDesmarcarTutoriaMinistrada.setCellFactory(CheckBoxTableCell.forTableColumn(tabDesmarcarTutoriaMinistrada));
				List<TutoriaMinistrada> listaTutoriasMinistradas = new ArrayList<>();
				
				for (int i = 0; i < jsonArray.length(); ++i) {
					try {
						JSONObject tutoria = new JSONObject(jsonArray.get(i).toString().replace("{", "{\n")
																						 .replace("}", "}\n"));
						System.out.println(tutoria);
						if (tutoria.getJSONObject("tutoria").
								getJSONObject("petiano").getJSONObject("pessoa").getInt("idPessoa") == mainApplet.getIdPessoa() )
						{
							listaTutoriasMinistradas.add(new TutoriaMinistrada(tutoria.getInt("idTutoria_ministrada"),
									tutoria.getJSONObject("tutoria").getJSONObject("disciplina").getString("nome"),
									tutoria.getJSONObject("pessoa").getString("nome"),
									tutoria.getJSONObject("tutoria").getJSONObject("disciplina").getString("codigo"),
									false, 
									String.valueOf(tutoria.getJSONObject("tutoria").getBoolean("ativo"))));
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
        		tableTutoriasMinistradas.setItems(FXCollections.observableArrayList(listaTutoriasMinistradas));
        	}
        }
    	catch (Exception e) {
			System.out.println(e.toString());
		}
    }

    public static class Noticia {
        private	SimpleStringProperty noticiaTitulo;
        private SimpleStringProperty noticiaCorpo;
        private SimpleStringProperty noticiaCriador;
        private SimpleIntegerProperty noticiaId;
        
		public Noticia(String noticiaTitulo, String noticiaCorpo,
				String noticiaCriador, Integer noticiaId) {
			super();
			this.noticiaTitulo = new SimpleStringProperty(noticiaTitulo);
			this.noticiaCorpo = new SimpleStringProperty(noticiaCorpo);
			this.noticiaCriador = new SimpleStringProperty(noticiaCriador);
			this.noticiaId = new SimpleIntegerProperty(noticiaId);
		}
		public String getnoticiaTitulo() {
			return this.noticiaTitulo.get();
		}
		public SimpleStringProperty noticiaTituloProperty() {
			return noticiaTitulo;
		}
		public void setNoticiaTitulo(SimpleStringProperty noticiaTitulo) {
			this.noticiaTitulo = noticiaTitulo;
		}
		public String getnoticiaCorpo() {
			return this.noticiaCorpo.get();
		}		
		public SimpleStringProperty noticiaCorpoProperty() {
			return noticiaCorpo;
		}
		public void setNoticiaCorpo(SimpleStringProperty noticiaCorpo) {
			this.noticiaCorpo = noticiaCorpo;
		}
		public String getnoticiaCriador() {
			return this.noticiaCriador.get();
		}		
		public SimpleStringProperty noticiaCriadorProperty() {
			return noticiaCriador;
		}
		public void setNoticiaCriador(SimpleStringProperty noticiaCriador) {
			this.noticiaCriador = noticiaCriador;
		}
		public Integer getnoticiaId() {
			return this.noticiaId.get();
		}		
		public SimpleIntegerProperty noticiaIdProperty() {
			return noticiaId;
		}
		public void setNoticiaId(SimpleIntegerProperty noticiaId) {
			this.noticiaId = noticiaId;
		}        
    }

    public static class Evento{
    	private SimpleIntegerProperty abertosId;
    	private SimpleStringProperty abertosTitulo;
    	private SimpleStringProperty abertosDescricao;
    	private SimpleStringProperty abertosLocal;
    	private SimpleIntegerProperty abertosVagas;
    	private SimpleDoubleProperty abertosCH;
    	private SimpleDoubleProperty abertosValor;
    	private SimpleStringProperty abertosDataInicio;
    	private SimpleBooleanProperty abertosInscrever;
		public Evento(Integer abertosId,String abertosTitulo,
				String abertosDescricao, String abertosLocal,
				Integer abertosVagas, Double abertosCH, Double abertosValor,
				String abertosDataInicio, Boolean abertosInscrever) {
			this.abertosId = new SimpleIntegerProperty(abertosId);
			this.abertosTitulo = new SimpleStringProperty(abertosTitulo);
			this.abertosDescricao = new SimpleStringProperty(abertosDescricao);
			this.abertosLocal = new SimpleStringProperty(abertosLocal);
			this.abertosVagas = new SimpleIntegerProperty(abertosVagas);
			this.abertosCH = new SimpleDoubleProperty(abertosCH);
			this.abertosValor = new SimpleDoubleProperty(abertosValor);
			this.abertosDataInicio = new SimpleStringProperty(abertosDataInicio);
			this.abertosInscrever = new SimpleBooleanProperty(abertosInscrever);
		}
		public Integer getabertosId() {
			return this.abertosId.get();
		}
		public SimpleIntegerProperty abertosIdProperty() {
			return abertosId;
		}
		public void setAbertosId(SimpleIntegerProperty abertosId) {
			this.abertosId = abertosId;
		}
		public String getabertosTitulo() {
			return this.abertosTitulo.get();
		}
		public SimpleStringProperty abertosTituloProperty() {
			return abertosTitulo;
		}
		public void setAbertosTitulo(SimpleStringProperty abertosTitulo) {
			this.abertosTitulo = abertosTitulo;
		}
		public String getabertosDescricao() {
			return this.abertosDescricao.get();
		}
		public SimpleStringProperty abertosDescricaoProperty() {
			return abertosDescricao;
		}
		public void setAbertosDescricao(SimpleStringProperty abertosDescricao) {
			this.abertosDescricao = abertosDescricao;
		}
		public String getabertosLocal() {
			return this.abertosLocal.get();
		}
		public SimpleStringProperty abertosLocalProperty() {
			return abertosLocal;
		}
		public void setAbertosLocal(SimpleStringProperty abertosLocal) {
			this.abertosLocal = abertosLocal;
		}
		public Integer getabertosVagas() {
			return this.abertosVagas.get();
		}
		public SimpleIntegerProperty abertosVagasProperty() {
			return abertosVagas;
		}
		public void setAbertosVagas(SimpleIntegerProperty abertosVagas) {
			this.abertosVagas = abertosVagas;
		}
		public Double getabertosCH() {
			return this.abertosCH.get();
		}
		public SimpleDoubleProperty abertosCHProperty() {
			return abertosCH;
		}
		public void setAbertosCH(SimpleDoubleProperty abertosCH) {
			this.abertosCH = abertosCH;
		}
		public Double getabertosValor() {
			return this.abertosValor.get();
		}
		public SimpleDoubleProperty abertosValorProperty() {
			return abertosValor;
		}
		public void setAbertosValor(SimpleDoubleProperty abertosValor) {
			this.abertosValor = abertosValor;
		}
		public String getabertosDataInicio() {
			return this.abertosDataInicio.get();
		}
		public SimpleStringProperty abertosDataInicioProperty() {
			return abertosDataInicio;
		}
		public void setAbertosDataInicio(SimpleStringProperty abertosDataInicio) {
			this.abertosDataInicio = abertosDataInicio;
		}
		public Boolean getabertosInscrever() {
			return this.abertosInscrever.get();
		}
		public SimpleBooleanProperty abertosInscreverProperty() {
			return abertosInscrever;
		}
		public void setAbertosInscrever(SimpleBooleanProperty abertosInscrever) {
			this.abertosInscrever = abertosInscrever;
		}
    }
    public static class Disciplina{
    	private SimpleIntegerProperty tabIdDisciplina;
    	private SimpleStringProperty tabNomeDisciplina;
    	private SimpleStringProperty tabNomeTutor;
    	private SimpleStringProperty tabCodigoDisciplina;
    	private SimpleBooleanProperty tabSolicitar;
		public Disciplina(Integer tabIdDisciplina, String tabNomeDisciplina,
				String tabNomeTutor, String tabCodigoDisciplina,
				Boolean tabSolicitar) {
			super();
			this.tabIdDisciplina = new SimpleIntegerProperty(tabIdDisciplina);
			this.tabNomeDisciplina = new SimpleStringProperty(tabNomeDisciplina);
			this.tabNomeTutor = new SimpleStringProperty(tabNomeTutor);
			this.tabCodigoDisciplina = new SimpleStringProperty(tabCodigoDisciplina);
			this.tabSolicitar = new SimpleBooleanProperty(tabSolicitar);
		}
		public Integer getTabIdDisciplina() {
			return this.tabIdDisciplina.get();
		}
		public SimpleIntegerProperty tabIdDisciplinaProperty() {
			return tabIdDisciplina;
		}
		public void setTabIdDisciplina(SimpleIntegerProperty tabIdDisciplina) {
			this.tabIdDisciplina = tabIdDisciplina;
		}
		public String getTabNomeDisciplina() {
			return this.tabNomeDisciplina.get();
		}
		public SimpleStringProperty tabNomeDisciplinaProperty() {
			return tabNomeDisciplina;
		}
		public void setTabNomeDisciplina(SimpleStringProperty tabNomeDisciplina) {
			this.tabNomeDisciplina = tabNomeDisciplina;
		}
		public String getTabNomeTutor() {
			return this.tabNomeTutor.get();
		}
		public SimpleStringProperty tabNomeTutorProperty() {
			return tabNomeTutor;
		}
		public void setTabNomeTutor(SimpleStringProperty tabNomeTutor) {
			this.tabNomeTutor = tabNomeTutor;
		}
		public String getTabCodigoDisciplina() {
			return this.tabCodigoDisciplina.get();
		}
		public SimpleStringProperty tabCodigoDisciplinaProperty() {
			return tabCodigoDisciplina;
		}
		public void setTabCodigoDisciplina(SimpleStringProperty tabCodigoDisciplina) {
			this.tabCodigoDisciplina = tabCodigoDisciplina;
		}
		public Boolean getTabSolicitar() {
			return this.tabSolicitar.get();
		}
		public SimpleBooleanProperty tabSolicitarProperty() {
			return tabSolicitar;
		}
		public void setTabSolicitar(SimpleBooleanProperty tabSolicitar) {
			this.tabSolicitar = tabSolicitar;
		}
    }

    public static class TutoriaMinistrada{
    	private SimpleIntegerProperty tabIdTutoriaMinistrada;
    	private SimpleStringProperty tabNomeTutoriaMinistrada;
    	private SimpleStringProperty tabNomeTutorTutoriaMinistrada;
    	private SimpleStringProperty tabCodigoTutoriaMinistrada;
    	private SimpleBooleanProperty tabDesmarcarTutoriaMinistrada;
    	private SimpleStringProperty tabEmailTutoriaMinistrada;
		public TutoriaMinistrada(Integer tabIdDisciplina, String tabNomeDisciplina,
				String tabNomeTutor, String tabCodigoDisciplina,
				Boolean tabSolicitar, String tabEmailTutoriaMinistrada) {
			super();
			this.tabIdTutoriaMinistrada = new SimpleIntegerProperty(tabIdDisciplina);
			this.tabNomeTutoriaMinistrada = new SimpleStringProperty(tabNomeDisciplina);
			this.tabNomeTutorTutoriaMinistrada = new SimpleStringProperty(tabNomeTutor);
			this.tabCodigoTutoriaMinistrada = new SimpleStringProperty(tabCodigoDisciplina);
			this.tabDesmarcarTutoriaMinistrada = new SimpleBooleanProperty(tabSolicitar);
			this.tabEmailTutoriaMinistrada = new SimpleStringProperty(tabEmailTutoriaMinistrada);
		}
		public Integer getTabIdTutoriaMinistrada() {
			return this.tabIdTutoriaMinistrada.get();
		}
		public SimpleIntegerProperty tabIdTutoriaMinistradaProperty() {
			return tabIdTutoriaMinistrada;
		}
		public void setTabIdTutoriaMinistrada(SimpleIntegerProperty tabIdTutoriaMinistrada) {
			this.tabIdTutoriaMinistrada = tabIdTutoriaMinistrada;
		}
		public String getTabNomeDisciplina() {
			return this.tabNomeTutoriaMinistrada.get();
		}
		public SimpleStringProperty tabNomeTutoriaMinistradaProperty() {
			return tabNomeTutoriaMinistrada;
		}
		public void setTabNomeTutoriaMinistrada(SimpleStringProperty tabNomeTutoriaMinistrada) {
			this.tabNomeTutoriaMinistrada = tabNomeTutoriaMinistrada;
		}
		public String getTabNomeTutorTutoriaMinistrada() {
			return this.tabNomeTutorTutoriaMinistrada.get();
		}
		public SimpleStringProperty tabNomeTutorTutoriaMinistradaProperty() {
			return tabNomeTutorTutoriaMinistrada;
		}
		public void setTabNomeTutorTutoriaMinistrada(SimpleStringProperty tabNomeTutorTutoriaMinistrada) {
			this.tabNomeTutorTutoriaMinistrada = tabNomeTutorTutoriaMinistrada;
		}
		public String getTabCodigoTutoriaMinistrada() {
			return this.tabCodigoTutoriaMinistrada.get();
		}
		public SimpleStringProperty tabCodigoDisciplinaProperty() {
			return tabCodigoTutoriaMinistrada;
		}
		public void setTabCodigoTutoriaMinistrada(SimpleStringProperty tabCodigoTutoriaMinistrada) {
			this.tabCodigoTutoriaMinistrada = tabCodigoTutoriaMinistrada;
		}
		public Boolean getTabDesmarcarTutoriaMinistrada() {
			return this.tabDesmarcarTutoriaMinistrada.get();
		}
		public SimpleBooleanProperty tabDesmarcarTutoriaMinistradaProperty() {
			return tabDesmarcarTutoriaMinistrada;
		}
		public void setTabDesmarcarTutoriaMinistrada(SimpleBooleanProperty tabSolicitar) {
			this.tabDesmarcarTutoriaMinistrada = tabSolicitar;
		}
		public String getTabEmailTutoriaMinistrada() {
			return this.tabEmailTutoriaMinistrada.get();
		}
		public SimpleStringProperty tabEmailTutoriaMinistradaProperty() {
			return tabEmailTutoriaMinistrada;
		}
		public void setTabEmailTutoriaMinistrada(SimpleStringProperty tabEmailTutoriaMinistrada) {
			this.tabEmailTutoriaMinistrada = tabEmailTutoriaMinistrada;
		}
    }
    
    public void inscreverEvento() throws Exception{
    	ObservableList<Evento> listaEventosAbertos= (tableAbertos.getItems());
    	for (int i = 0; i < listaEventosAbertos.size(); i++) {
			if(listaEventosAbertos.get(i).getabertosInscrever()) {
				//System.out.println("LOL");

		        JSONObject json = new JSONObject();
		        json.put("teste", "teste");
		        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
		        
		        Request request = new Request.Builder()
		                .url("http://localhost:8080/api/participantes-cadastrar/"+
		                			listaEventosAbertos.get(i).getabertosId()+"/"+
		                			mainApplet.getIdPessoa().toString()+"/")
		                .addHeader("Content-Type", "application/json")
		                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
		                .post(body)
		                .build();

		        try (Response response = httpClient.newCall(request).execute()) {

		            if (!response.isSuccessful()) {
		            	System.out.println(response);
		            	//throw new IOException(response.body().toString());
		            	System.out.println(response.body().toString());
		            }
		            else{
		        		Alert alert = new Alert(AlertType.INFORMATION);
		        		alert.setTitle("Sucesso");
		        		alert.setHeaderText("Parabens, usuário inscrito com sucesso no evento" + 
		        								listaEventosAbertos.get(i).getabertosTitulo());
		            	mainApplet.loginUsuario();
		            }
		        }
		        catch (Exception e) {
					System.out.println(e);
				}

			}
		}
    }
    public void retirarInscricao() throws Exception {
    	ObservableList<Evento> listaEventosAbertos= (tableAbertos1.getItems());
    	for (int i = 0; i < listaEventosAbertos.size(); i++) {
			if(listaEventosAbertos.get(i).getabertosInscrever()) {
				//System.out.println("LOL");

		        JSONObject json = new JSONObject();
		        json.put("teste", "teste");
		        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
		        
		        Request request = new Request.Builder()
		                .url("http://localhost:8080/api/participantes-remove/"+
		                			listaEventosAbertos.get(i).getabertosId()+"/")
		                .addHeader("Content-Type", "application/json")
		                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
		                .delete(body)
		                .build();

		        try (Response response = httpClient.newCall(request).execute()) {

		            if (!response.isSuccessful()) {
		            	System.out.println(response);
		            	//throw new IOException(response.body().toString());
		            	System.out.println(response.body().toString());
		            }
		            else{
		        		Alert alert = new Alert(AlertType.INFORMATION);
		        		alert.setTitle("Sucesso");
		        		alert.setHeaderText("Parabens, você retirou sua inscrição no evento" + 
		        								listaEventosAbertos.get(i).getabertosTitulo());
		            	mainApplet.loginUsuario();
		            }
		        }
		        catch (Exception e) {
					System.out.println(e);
				}

			}
		}
    }
    public void editarEvento() throws Exception{
    	ObservableList<Evento> listaEventosAbertos= (tableAbertos2.getItems());
    	for (int i = 0; i < listaEventosAbertos.size(); i++) {
			if(listaEventosAbertos.get(i).getabertosInscrever()) {
		        JSONObject json = new JSONObject();
		        System.out.println(listaEventosAbertos.get(i).getabertosTitulo());
		        json.put("idEvento", listaEventosAbertos.get(i).getabertosId());
		        json.put("titulo", listaEventosAbertos.get(i).getabertosTitulo());
		        json.put("descricao", listaEventosAbertos.get(i).getabertosDescricao());
		        json.put("local", listaEventosAbertos.get(i).getabertosLocal());
		        json.put("d_inscricao", listaEventosAbertos.get(i).getabertosDataInicio());
		        json.put("qtdVagas", listaEventosAbertos.get(i).getabertosVagas());
		        json.put("qtdCargaHoraria", listaEventosAbertos.get(i).getabertosCH());
		        json.put("valor", listaEventosAbertos.get(i).getabertosVagas());
		        
		        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
		        
		        Request request = new Request.Builder()
		                .url("http://localhost:8080/api/eventos-cadastrar")
		                .addHeader("Content-Type", "application/json")
		                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
		                .post(body)
		                .build();

		        try (Response response = httpClient.newCall(request).execute()) {

		            if (!response.isSuccessful()) {
		            	System.out.println(response);
		            	//throw new IOException(response.body().toString());
		            	System.out.println(response.body().toString());
		            }
		            else{
		            	System.out.println(response);
		        		Alert alert = new Alert(AlertType.INFORMATION);
		        		alert.setTitle("Sucesso");
		        		alert.setHeaderText("Parabens, você editou o evento " + 
		        								listaEventosAbertos.get(i).getabertosTitulo());
		            	mainApplet.loginUsuario();
		            }
		        }
		        catch (Exception e) {
					System.out.println(e);
				}

			}
		}
    }
    public void solicitarTutoria() throws Exception{
    	ObservableList<Disciplina> listaTutorias = (tableDisciplinas.getItems());
    	for (int i = 0; i < listaTutorias.size(); i++) {
			if(listaTutorias.get(i).getTabSolicitar()) {
		        JSONObject json = new JSONObject();
		        //System.out.println(listaTutorias.get(i).getabertosTitulo());
		        json.put("teste", "teste");
		        
		        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json; charset=utf-8"));
		        
		        Request request = new Request.Builder()
		                .url("http://localhost:8080/api/tutorias-ministradas-cadastro/"+
		                		mainApplet.getIdPessoa()+"/"+
		                		listaTutorias.get(i).getTabIdDisciplina())
		                .addHeader("Content-Type", "application/json")
		                .addHeader("Authorization", Credentials.basic(mainApplet.getUsuario(), mainApplet.getSenha()))
		                .post(body)
		                .build();

		        try (Response response = httpClient.newCall(request).execute()) {

		            if (!response.isSuccessful()) {
		            	System.out.println(response);
		            	//throw new IOException(response.body().toString());
		            	System.out.println(response.body().toString());
		            }
		            else{
		            	System.out.println(response);
		        		Alert alert = new Alert(AlertType.INFORMATION);
		        		alert.setTitle("Sucesso");
		        		alert.setHeaderText("Você solicitou uma tutoria na disciplina " + 
		        				listaTutorias.get(i).getTabNomeDisciplina());
		            	mainApplet.loginUsuario();
		            }
		        }
		        catch (Exception e) {
					System.out.println(e);
				}

			}
		}
    }
    public void editarNoticias() {
    	
    }
    public void cadastrarNoticias() throws Exception{
    	mainApplet.cadastrarNoticia();
    }
    public void cadastrarEvento() throws Exception{
    	mainApplet.cadastrarEvento();
    }
    public void cadastrarDisciplina() throws Exception{
    	mainApplet.cadastrarDisciplina();
    }
    public void desmarcarTutoriaMinistrada() {
    	
    }
}