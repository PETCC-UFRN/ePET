package br.ufrn.ePET.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.ufrn.ePET.error.CustomException;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Certificado;
import br.ufrn.ePET.models.CertificadoOrganizador;
import br.ufrn.ePET.models.Hash;
import br.ufrn.ePET.models.Organizadores;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.repository.CertificadoOrganizadoresRepository;
import br.ufrn.ePET.repository.CertificadoRepository;
import br.ufrn.ePET.repository.FrequenciaRepository;
import br.ufrn.ePET.repository.OrganizadoresRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class CertificadoService {
	
	private final CertificadoRepository certificadoRepository;
	
	private final CertificadoOrganizadoresRepository certificadoOrganizadorRepository;
	
	private final ParticipanteRepository participanteRepository;
	
	private final OrganizadoresRepository organizadoresRepository;
	
	private final FrequenciaRepository frequenciaRepository;
	
    private final Hash hashDeclaracao;

	@Autowired
	public CertificadoService(CertificadoRepository certificadoRepository, ParticipanteRepository participanteRepository, OrganizadoresRepository organizadoresRepository, CertificadoOrganizadoresRepository certificadoOrganizadorRepository, FrequenciaRepository frequenciaRepository) throws NoSuchAlgorithmException {
		this.certificadoRepository = certificadoRepository;		
		this.participanteRepository = participanteRepository;	
		this.organizadoresRepository = organizadoresRepository;
		this.certificadoOrganizadorRepository = certificadoOrganizadorRepository;
		this.frequenciaRepository = frequenciaRepository;
        this.hashDeclaracao = new Hash();
	}
	
	public String gerarCertificado(Long id_pessoa, Long id_evento) {
		Participante participante = participanteRepository.findByPessoaAndEvento(id_pessoa, id_evento);
		if (participante == null)
			throw new ResourceNotFoundException("Participante não encontrado!");
		int assiduidadeFreq = 0;
		try {
			assiduidadeFreq = frequenciaRepository.findAssiduidadeByParticipante(participante.getIdParticipantes(), id_evento);
		}
		catch (Exception e) {
			throw new ResourceNotFoundException("Usuário não concluiu a carga horária mínima para obter o certificado do evento!");
		}

		if( ((double) assiduidadeFreq / (double) participante.getEvento().getQtdCargaHoraria())*100 < participante.getEvento().getPercentual() )
			throw new ResourceNotFoundException("Usuário não concluiu a carga horária mínima para obter o certificado do evento!");
		
		Certificado certificado = certificadoRepository.findByParticipante(participante);
		if(certificado == null)
			certificado = new Certificado();
		certificado.setParticipante(participante);
        LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		certificado.setDataCriacao(today);
		String password = participante.getPessoa().getNome() + participante.getPessoa().getCpf() +
				participante.getPessoa().getUsuario().getEmail() + participante.getEvento().getTitulo()+
				participante.getEvento().getD_evento_inicio() + participante.getEvento().getD_evento_fim();
		certificado.setHash(hashDeclaracao.novaHash(password));
		String pathSaida = "./data/certificado/"+certificado.getParticipante().getPessoa().getNome()+".pdf";
		/*String periodo = "";
		if(participante.getEvento().getQtdDias() == 1)
		{
			periodo = ", no dia " + participante.getEvento().getD_evento_inicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}
		else {
			periodo = ", nos dias "+participante.getEvento().getD_evento_inicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
					" à " + participante.getEvento().getD_inscricao_fim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}*/
		String textoDeclaracao = participante.getEvento().getTextoDeclaracaoEvento();
		textoDeclaracao = textoDeclaracao.replaceAll("\\{nome_participante\\}", participante.getPessoa().getNome());
		textoDeclaracao = textoDeclaracao.replaceAll("\\{cpf\\}", participante.getPessoa().getCpf());
		textoDeclaracao = textoDeclaracao.replaceAll("\\{titulo_evento\\}", participante.getEvento().getTitulo());
		textoDeclaracao = textoDeclaracao.replaceAll("\\{carga_horária\\}", String.valueOf(participante.getEvento().getQtdCargaHoraria()));
		textoDeclaracao = textoDeclaracao.replaceAll("\\{data_inicio\\}", String.valueOf(participante.getEvento().getD_evento_inicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		textoDeclaracao = textoDeclaracao.replaceAll("\\{data_fim\\}", String.valueOf(participante.getEvento().getD_evento_fim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		textoDeclaracao = textoDeclaracao.replaceAll("\\{local\\}", String.valueOf(participante.getEvento().getLocal()));	
			
        /*textoDeclaracao = "Declaro, para os devidos fins, que " + participante.getPessoa().getNome() + ", CPF " +
                participante.getPessoa().getCpf() + ", participou do evento " + participante.getEvento().getTitulo() + 
                ", realizado no " + participante.getEvento().getLocal() + periodo + ", com uma carga-horária total de " + 
                participante.getEvento().getQtdCargaHoraria() +
                "h. Este evento foi promovido pelo Programa de " +
                "Educação Tutorial do Curso de Ciência da Computação da Universidade Federal " +
                "do Rio Grande do Norte (PET-CC/UFRN).";*/
        String textoData = "Natal/RN " + formattedDate + ".";

        try {
            PdfReader pdfReader = new PdfReader("./template/template.pdf");

            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream(pathSaida));

            //Image image = Image.getInstance("./assinatura/assin.png");

            //Inserindo assinatura
            PdfContentByte content = pdfStamper.getOverContent(1);
            //image.setAbsolutePosition(590f, 60f);
            //content.addImage(image);

            //Inserindo texto
            Rectangle rectText = new Rectangle(130, 350, 715, 180);
            rectText.setBorder(Rectangle.BOX);
            //rectText.setBorderWidth(1);
            //rectText.setBorderColor(BaseColor.RED);
            content.rectangle(rectText);

            ColumnText ctText = new ColumnText(content);
            ctText.setSimpleColumn(rectText);
            Font catFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph paragraphText = new Paragraph(textoDeclaracao, catFont);
            paragraphText.setAlignment(Element.ALIGN_JUSTIFIED);
            ctText.addElement(paragraphText);
            ctText.go();
            
            //Inserindo texto
            Rectangle rectLink = new Rectangle(83, 20, 515, 60);
            rectLink.setBorder(Rectangle.BOX);
            content.rectangle(rectLink);
            ColumnText ctLink= new ColumnText(content);
            ctLink.setSimpleColumn(rectLink);
            Font catLink = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Paragraph paragraphLink = new Paragraph("Para validar essa declaração acesse: https://petcc.dimap.ufrn.br/validacao", catLink);
            paragraphLink.setAlignment(Element.ALIGN_JUSTIFIED);
            ctLink.addElement(paragraphLink);
            ctLink.go();

            //Inserindo data
            Rectangle rectData = new Rectangle(390, 150, 715, 180);
            rectData.setBorder(Rectangle.BOX);
            //rectData.setBorderWidth(1);
            //rectData.setBorderColor(BaseColor.GREEN);
            content.rectangle(rectData);

            ColumnText ctData = new ColumnText(content);
            ctData.setSimpleColumn(rectData);
            Paragraph paragraphData = new Paragraph(textoData, catFont);
            paragraphData.setAlignment(Element.ALIGN_RIGHT);
            ctData.addElement(paragraphData);
            ctData.go();

            //Inserindo Hash
            //String password = "dados.getNomeParticipante() + dados.getCpfParticipante() + dados.getNomeEvento()" +
            //        "dados.getNomeAtividade() + dados.getDataAtividadeInicial()";
            //String textoHash = hashDeclaracao.novaHash(password);
            String textoHash = certificado.getHash();
            //System.out.println(textoHash);

            Rectangle rectHash = new Rectangle(83, 20, 515, 50);
            rectHash.setBorder(Rectangle.BOX);
            //rectHash.setBorderWidth(1);
            //rectHash.setBorderColor(BaseColor.MAGENTA);
            content.rectangle(rectHash);

            Font hashFont = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
            hashFont.setColor(BaseColor.GRAY);
            ColumnText ctHash = new ColumnText(content);
            ctHash.setSimpleColumn(rectHash);
            Paragraph paragraphHash = new Paragraph(textoHash, hashFont);
            paragraphHash.setAlignment(Element.ALIGN_LEFT);
            ctHash.addElement(paragraphHash);
            ctHash.go();

            pdfStamper.close();

            //Armazenando dados
            //armazenamento.armazenarDados(textoHash, dados);

        } catch (IOException e) {
            e.printStackTrace();
			throw new CustomException("Erro na criação do certificado, entre em contado com nosso suporte!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new CustomException("Erro na criação do certificado, entre em contado com nosso suporte!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
		certificadoRepository.save(certificado);
		
		return pathSaida; 
	}
	
	public String gerarCertificadoOrganizador(Long id_pessoa, Long id_evento) {
		Organizadores organizador = organizadoresRepository.findByPessoaAndEvento(id_pessoa, id_evento);
		if (organizador == null)
			throw new ResourceNotFoundException("Organizador não encontrado!");
		CertificadoOrganizador certificado = certificadoOrganizadorRepository.findByOrganizadores(organizador);
		if(certificado == null)
			certificado = new CertificadoOrganizador();
		certificado.setOrganizadores(organizador);
        LocalDate today = LocalDate.now();
		if(organizador.getEvento().getD_evento_fim().isAfter(today))
        		throw new ResourceNotFoundException("O certificado de organização só pode ser gerado após a conclusão do evento!");
        	
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		certificado.setDataCriacao(today);
		String password = organizador.getPessoa().getNome() + organizador.getPessoa().getCpf() +
				organizador.getPessoa().getUsuario().getEmail() + organizador.getEvento().getTitulo()+
				organizador.getEvento().getD_evento_inicio() + organizador.getEvento().getD_evento_fim();
		certificado.setHash(hashDeclaracao.novaHash(password));
		String pathSaida = "./data/certificado/"+certificado.getOrganizadores().getPessoa().getNome()+".pdf";
		
		String textoDeclaracao = organizador.getEvento().getTextoDeclaracaoEvento();
		textoDeclaracao = textoDeclaracao.replaceAll("\\{nome_participante\\}", organizador.getPessoa().getNome());
		textoDeclaracao = textoDeclaracao.replaceAll("\\{cpf\\}", organizador.getPessoa().getCpf());
		textoDeclaracao = textoDeclaracao.replaceAll("\\{titulo_evento\\}", organizador.getEvento().getTitulo());
		textoDeclaracao = textoDeclaracao.replaceAll("\\{carga_horária\\}", String.valueOf(organizador.getEvento().getQtdCargaHoraria()));
		textoDeclaracao = textoDeclaracao.replaceAll("\\{data_inicio\\}", String.valueOf(organizador.getEvento().getD_evento_inicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		textoDeclaracao = textoDeclaracao.replaceAll("\\{data_fim\\}", String.valueOf(organizador.getEvento().getD_evento_fim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
		textoDeclaracao = textoDeclaracao.replaceAll("\\{local\\}", String.valueOf(organizador.getEvento().getLocal()));	

        String textoData = "Natal/RN " + formattedDate + ".";

        try {
            PdfReader pdfReader = new PdfReader("./template/template.pdf");

            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream(pathSaida));

            //Image image = Image.getInstance("./assinatura/assin.png");

            //Inserindo assinatura
            PdfContentByte content = pdfStamper.getOverContent(1);
            //image.setAbsolutePosition(590f, 60f);
            //content.addImage(image);

            //Inserindo texto
            Rectangle rectText = new Rectangle(130, 350, 715, 180);
            rectText.setBorder(Rectangle.BOX);
            //rectText.setBorderWidth(1);
            //rectText.setBorderColor(BaseColor.RED);
            content.rectangle(rectText);

            ColumnText ctText = new ColumnText(content);
            ctText.setSimpleColumn(rectText);
            Font catFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph paragraphText = new Paragraph(textoDeclaracao, catFont);
            paragraphText.setAlignment(Element.ALIGN_JUSTIFIED);
            ctText.addElement(paragraphText);
            ctText.go();
            
            //Inserindo texto
            Rectangle rectLink = new Rectangle(83, 20, 515, 60);
            rectLink.setBorder(Rectangle.BOX);
            content.rectangle(rectLink);
            ColumnText ctLink= new ColumnText(content);
            ctLink.setSimpleColumn(rectLink);
            Font catLink = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Paragraph paragraphLink = new Paragraph("Para validar essa declaração acesse: https://epet.imd.ufrn.br/validacao", catLink);
            paragraphLink.setAlignment(Element.ALIGN_JUSTIFIED);
            ctLink.addElement(paragraphLink);
            ctLink.go();

            //Inserindo data
            Rectangle rectData = new Rectangle(390, 150, 715, 180);
            rectData.setBorder(Rectangle.BOX);
            //rectData.setBorderWidth(1);
            //rectData.setBorderColor(BaseColor.GREEN);
            content.rectangle(rectData);

            ColumnText ctData = new ColumnText(content);
            ctData.setSimpleColumn(rectData);
            Paragraph paragraphData = new Paragraph(textoData, catFont);
            paragraphData.setAlignment(Element.ALIGN_RIGHT);
            ctData.addElement(paragraphData);
            ctData.go();

            //Inserindo Hash
            //String password = "dados.getNomeParticipante() + dados.getCpfParticipante() + dados.getNomeEvento()" +
            //        "dados.getNomeAtividade() + dados.getDataAtividadeInicial()";
            //String textoHash = hashDeclaracao.novaHash(password);
            String textoHash = certificado.getHash();
            //System.out.println(textoHash);

            Rectangle rectHash = new Rectangle(83, 20, 515, 50);
            rectHash.setBorder(Rectangle.BOX);
            //rectHash.setBorderWidth(1);
            //rectHash.setBorderColor(BaseColor.MAGENTA);
            content.rectangle(rectHash);

            Font hashFont = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
            hashFont.setColor(BaseColor.GRAY);
            ColumnText ctHash = new ColumnText(content);
            ctHash.setSimpleColumn(rectHash);
            Paragraph paragraphHash = new Paragraph(textoHash, hashFont);
            paragraphHash.setAlignment(Element.ALIGN_LEFT);
            ctHash.addElement(paragraphHash);
            ctHash.go();

            pdfStamper.close();

            //Armazenando dados
            //armazenamento.armazenarDados(textoHash, dados);

        } catch (IOException e) {
            e.printStackTrace();
			throw new CustomException("Erro na criação do certificado, entre em contado com nosso suporte!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new CustomException("Erro na criação do certificado, entre em contado com nosso suporte!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
		certificadoOrganizadorRepository.save(certificado);
		
		return pathSaida; 
	}
	
	public Certificado verificarCertificado(String hash) {
		Certificado certificado = certificadoRepository.findByHash(hash);
		if (certificado == null) {
			throw new ResourceNotFoundException("Certificado inválido!");
		}
		return certificado;
	}	
	
	public void modificarTemplate(MultipartFile file) {
		Path fileNameAndPath = Paths.get("./template/", "template.pdf");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			throw new CustomException("Erro na criação do certificado, entre em contado com nosso suporte!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
