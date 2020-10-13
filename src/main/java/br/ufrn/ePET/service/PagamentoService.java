package br.ufrn.ePET.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uol.pagseguro.api.PagSeguro;
import br.com.uol.pagseguro.api.PagSeguroEnv;
import br.com.uol.pagseguro.api.checkout.CheckoutRegistrationBuilder;
import br.com.uol.pagseguro.api.checkout.RegisteredCheckout;
import br.com.uol.pagseguro.api.common.domain.builder.ConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentItemBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodBuilder;
import br.com.uol.pagseguro.api.common.domain.builder.PaymentMethodConfigBuilder;
import br.com.uol.pagseguro.api.common.domain.enums.ConfigKey;
import br.com.uol.pagseguro.api.common.domain.enums.Currency;
import br.com.uol.pagseguro.api.common.domain.enums.PaymentMethodGroup;
import br.com.uol.pagseguro.api.credential.Credential;
import br.com.uol.pagseguro.api.http.JSEHttpClient;
import br.com.uol.pagseguro.api.transaction.search.TransactionDetail;
import br.com.uol.pagseguro.api.utils.logging.SimpleLoggerFactory;
import br.ufrn.ePET.error.ResourceNotFoundException;
import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Pagamento;
import br.ufrn.ePET.models.Pessoa;
import br.ufrn.ePET.repository.PagamentoRepository;

@Service
public class PagamentoService {

	private final PagamentoRepository pagamentoRepository;
	private final String EMAIL = "danielhenriquefg@gmail.com";
	private final String TOKEN = "FBFC86DB79E645CDBDB8FD4AA05E6F72";
	private final ParticipanteRepository participanteRepository;
	
	@Autowired
	public PagamentoService(PagamentoRepository pagamentoRepository, ParticipanteRepository participanteRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.participanteRepository = participanteRepository;
	}
	
	private Credential getCredentials() {
		return Credential.sellerCredential(EMAIL, TOKEN);
	}
	
	public String criarPagamento(Long id_participante){
		Pagamento existsPagamento = pagamentoRepository.findByParticipante(id_participante);
		PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
				getCredentials(), PagSeguroEnv.SANDBOX);
		if(existsPagamento != null) {
			TransactionDetail transactionDetail = pagSeguro.transactions().search().byCode(existsPagamento.getId_transacao_pagseguro());
			return transactionDetail.getPaymentLink();
		}
		Participante participante = new Participante();
		if(participanteRepository.findById(id_participante).isPresent())
			participante = participanteRepository.findById(id_participante).get();
		else throw new ResourceNotFoundException("Participante com id "+ id_participante + " não encontrada");
		
		Pessoa pessoa = participante.getPessoa();
		Evento evento = participante.getEvento();
		if (evento.getFim_rolagem().compareTo(LocalDate.now()) < 0) {
			throw new ResourceNotFoundException("Período de inscrição finalizado!");
		}

		if(evento.getValor() > 0.0){
			RegisteredCheckout registeredCheckout = pagSeguro.checkouts().register(
					new CheckoutRegistrationBuilder()
							.withCurrency(Currency.BRL)
							//.withExtraAmount(BigDecimal.ONE)
							.withReference("PETCC-"+participante.getPessoa().getIdPessoa()+"-"+participante.getEvento().getIdEvento())
							.addItem(new PaymentItemBuilder()
									.withId(evento.getIdEvento() + "")
									.withDescription(evento.getDescricao())
									.withAmount(new BigDecimal(evento.getValor()))
									.withQuantity(1)
							)
							.addPaymentMethodConfig(new PaymentMethodConfigBuilder()
									.withPaymentMethod(new PaymentMethodBuilder()
											.withGroup(PaymentMethodGroup.CREDIT_CARD)
									)
									.withConfig(new ConfigBuilder()
											.withKey(ConfigKey.MAX_INSTALLMENTS_LIMIT)
											.withValue(new BigDecimal(1))
									)

							)

			);

			Pagamento p = new Pagamento();
			p.setParticipante(participante);
			p.setReferencia_pagseguro("PETCC-"+pessoa.getIdPessoa()+"-"+evento.getIdEvento());
			pagamentoRepository.save(p);
			return registeredCheckout.getRedirectURL();
		} else {
			throw new RuntimeException("Evento gratuito!");
		}
		
	}
	
	public void redirectPagseguro(String transaction_id) {
		PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
				getCredentials(), PagSeguroEnv.SANDBOX);
		TransactionDetail transactionDetail = pagSeguro.transactions().search().byCode(transaction_id);
		//System.out.println(transaction.getReference());
		//List<Pagamento> p = pagamentoRepository.findByReferencia(transaction.getReference());
		//p.get(0).setId_transacao_pagseguro(transaction_id);
		//pagamentoRepository.save(p.get(0));
		Pagamento p  = pagamentoRepository.findByReferencia(transactionDetail.getReference());
		if(p != null){
			p.setData_criacao(transactionDetail.getDate());
			p.setData_atualizacao(transactionDetail.getLastEvent());
			p.setId_transacao_pagseguro(transactionDetail.getCode());
			p.setStatus(transactionDetail.getStatus().getStatus().toString());
			pagamentoRepository.save(p);
		} else {
			throw new ResourceNotFoundException("Nenhuma solicitação de pagamento encontrada!");
		}

	}
	/*
	public Pagamento verificarStatusPagamento(String reference) throws IOException{
		PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
				getCredentials(), PagSeguroEnv.SANDBOX);
		
		Pagamento p;
		if(pagamentoRepository.findByReferencia(reference).isEmpty()) {
			throw new ResourceNotFoundException("Não foi encontrado nenhum pagamento com a referência " + reference);
		} else {
			p = pagamentoRepository.findByReferencia(reference).get(0);
		}
		TransactionDetail t = pagSeguro.transactions().search().byCode(p.getId_transacao_pagseguro());
		p.setData(Instant.ofEpochMilli(t.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
		p.setStatus(t.getStatus().getStatus().name());
		pagamentoRepository.save(p);
		return p;
	}*/

	public void verifiarStatusPagamento(String nCode, String nType){
		PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
				getCredentials(), PagSeguroEnv.SANDBOX);
		//System.out.println("Rodou ate aqui");
		TransactionDetail transactionDetail = pagSeguro.transactions().search().byNotificationCode(nCode);
		//System.out.println(transactionDetail.getReference());
		Pagamento p  = pagamentoRepository.findByReferencia(transactionDetail.getReference());
		if(p != null){
			p.setData_criacao(transactionDetail.getDate());
			p.setData_atualizacao(transactionDetail.getLastEvent());
			p.setId_transacao_pagseguro(transactionDetail.getCode());
			p.setStatus(transactionDetail.getStatus().getStatus().toString());
			pagamentoRepository.save(p);
			if(transactionDetail.getStatus().getStatusId() == 3 || transactionDetail.getStatus().getStatusId() == 4){
				Participante participante = p.getParticipante();
				if(participante != null){
					participante.setConfirmado(true);
					participanteRepository.save(participante);
				} else {
					throw new ResourceNotFoundException("Não encontramos o participante");
				}
			} else if(transactionDetail.getStatus().getStatusId() >= 5) {
				Participante participante = p.getParticipante();
				if(participante != null){
					participante.setConfirmado(false);
					participanteRepository.save(participante);
				} else {
					throw new ResourceNotFoundException("Não encontramos o participante");
				}
			}
		} else {
			throw new ResourceNotFoundException("Nenhuma solicitação de pagamento encontrada!");
		}

	}
}
