package br.ufrn.ePET.service;

import br.ufrn.ePET.models.Evento;
import br.ufrn.ePET.models.Participante;
import br.ufrn.ePET.repository.PagamentoRepository;
import br.ufrn.ePET.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling
public class RolagemFilaService {

   private final EventoService eventoService;
   private final PagamentoRepository pagamentoRepository;
   private final PagamentoService pagamentoService;
   private final ParticipanteRepository participanteRepository;
   private final ParticipanteService participanteService;
   private JavaMailSender javaMailSender;

    @Autowired
    public RolagemFilaService(EventoService eventoService, PagamentoRepository pagamentoRepository,
                              PagamentoService pagamentoService, ParticipanteRepository participanteRepository,
                              ParticipanteService participanteService, JavaMailSender javaMailSender) {
        this.eventoService = eventoService;
        this.pagamentoRepository = pagamentoRepository;
        this.pagamentoService = pagamentoService;
        this.participanteRepository = participanteRepository;
        this.participanteService = participanteService;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 40 1 * * *")
    public void VerificarPagamentos(){
        List<Evento> lista = eventoService.buscarAtivos();
        for(Evento e : lista){
            List<Participante> lista_participantes = participanteRepository.findByTituloEvento(e.getTitulo());
            for(Participante p : lista_participantes){
                if(!p.isConfirmado() && !p.isEspera() && p.getData_maxima().compareTo(LocalDate.now()) < 0){
                    Participante participante = participanteService.remover(p.getIdParticipantes());
                    enviarEmail(participante);
                } else if (participanteRepository.countAtivos(e.getIdEvento()) < e.getQtdVagas()){
                    Participante p_ = participanteRepository.findByEspera(e.getIdEvento(), true);
                    p_.setData_maxima(LocalDate.now().plusDays(p_.getEvento().getDias_compensacao()));
                    p_.setEspera(false);
                    enviarEmail(p_);
                }
            }
        }
    }

    public void enviarEmail(Participante participante) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(participante.getPessoa().getUsuario().getEmail());
        smm.setText("Olá " + participante.getPessoa().getNome() + "!\n"
                + "Temos o prazer de informar que uma vaga foi aberta no evento:\n" + participante.getEvento().getTitulo() +"\n" +
                "Caso tenha ainda interesse em participar acesse o nosso site e caso o evento seja pago, efetue o pagamento do mesmo.");
        try {
            javaMailSender.send(smm);
        } catch (Exception e) {
            throw new RuntimeException("Houve algum erro no envio do seu email!\n" + e);
        }
    }



}
