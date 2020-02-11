package br.ufrn.ePET.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

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
import br.ufrn.ePET.repository.EventoRepository;
import br.ufrn.ePET.repository.PagamentoRepository;
import br.ufrn.ePET.repository.PessoaRepository;

@Service
public class PagamentoService {

	private final PagamentoRepository pagamentoRepository;
	private final String EMAIL = "abraaovld@gmail.com";
	private final String TOKEN = "9068F1E6809245E08070D78DB8EAE14F";
	private final EventoRepository eventoRepository;
	private final PessoaRepository pessoaRepository;
	
	@Autowired
	public PagamentoService(PagamentoRepository pagamentoRepository, EventoRepository eventoRepository, PessoaRepository pessoaRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.eventoRepository = eventoRepository;
		this.pessoaRepository = pessoaRepository;
	}
	
	private Credential getCredentials() {
		return Credential.sellerCredential(EMAIL, TOKEN);
	}
	
	public String criarPagamento(Long id_evento, Long id_pessoa){
		Evento evento = new Evento();
		if(eventoRepository.findById(id_evento).isPresent())
			evento = eventoRepository.findById(id_evento).get();
		else throw new ResourceNotFoundException("Evento com id "+ id_evento + " não encontrado");
		
		Pessoa pessoa = new Pessoa();
		if(pessoaRepository.findById(id_pessoa).isPresent())
			pessoa = pessoaRepository.findById(id_pessoa).get();
		else throw new ResourceNotFoundException("Pessoa com id "+ id_pessoa + " não encontrada");
		
		PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
				getCredentials(), PagSeguroEnv.SANDBOX);
		
		
		RegisteredCheckout registeredCheckout = pagSeguro.checkouts().register(
				new CheckoutRegistrationBuilder()
				.withCurrency(Currency.BRL)
				.withExtraAmount(BigDecimal.ONE)
				.withReference("PETCC-"+id_pessoa+"-"+id_evento)
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
		p.setPessoa(pessoa);
		p.setEvento(evento);
		p.setReferencia_pagseguro("PETCC-"+id_pessoa+"-"+id_evento);
		pagamentoRepository.save(p);
		return registeredCheckout.getRedirectURL();
		
	}
	
	public void redirectPagseguro(String transaction_id) {
		PagSeguro pagSeguro = PagSeguro.instance(new SimpleLoggerFactory(), new JSEHttpClient(),
				getCredentials(), PagSeguroEnv.SANDBOX);
		TransactionDetail transaction = pagSeguro.transactions().search().byCode(transaction_id);
		//System.out.println(transaction.getReference());
		List<Pagamento> p = pagamentoRepository.findByReferencia(transaction.getReference());
		p.get(0).setId_transacao_pagseguro(transaction_id);
		pagamentoRepository.save(p.get(0));
	}
	
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
	}
}
