package br.edu.utfpr.td.cotsi.transacoes.consumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.cotsi.transacoes.consumer.config.RabbitMQConfig;
import br.edu.utfpr.td.cotsi.transacoes.consumer.model.Transacao;


@Component
public class PoliciaConsumer {

    @RabbitListener(queues = RabbitMQConfig.FILA_POLICIA)
    public void receberNotificacao(Transacao transacao) {
        System.out.println("=== NOTIFICAÇÃO POLÍCIA FEDERAL ===");
        System.out.println("Recebido evento de suspeita: " + transacao);
        System.out.println("===================================");
    }
}
