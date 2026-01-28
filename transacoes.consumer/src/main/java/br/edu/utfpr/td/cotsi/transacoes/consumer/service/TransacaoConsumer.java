package br.edu.utfpr.td.cotsi.transacoes.consumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.cotsi.transacoes.consumer.config.RabbitMQConfig;
import br.edu.utfpr.td.cotsi.transacoes.consumer.model.Transacao;

@Component
public class TransacaoConsumer {

    private final RabbitTemplate rabbitTemplate;

    public TransacaoConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter());
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_TRANSACOES)
    public void receber(Transacao transacao) throws InterruptedException {
        System.out.println("Recebida: " + transacao);

        Thread.sleep(1000);

        System.out.println("Processada: " + transacao.getCodigo());

        if (transacao.getValor() != null && transacao.getValor() >= 40000.0) {
            System.out.println("Valor >= 40000 -> Enviando notificação para " + RabbitMQConfig.EXCHANGE_NOTIFICACOES);
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NOTIFICACOES, "", transacao);
        }

        System.out.println("----------------------------------");
    }
}
