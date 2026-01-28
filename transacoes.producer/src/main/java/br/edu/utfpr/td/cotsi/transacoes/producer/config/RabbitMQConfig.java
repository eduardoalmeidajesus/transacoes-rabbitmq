package br.edu.utfpr.td.cotsi.transacoes.producer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FILA_TRANSACOES = "transacoes.financeiras";
    public static final String EXCHANGE_NOTIFICACOES = "notificacoes.fanout";
    public static final String FILA_POLICIA = "policia.federal";
    public static final String FILA_RECEITA = "receita.federal";

    @Bean
    public Queue filaTransacoes() {
        return new Queue(FILA_TRANSACOES, true);
    }

    @Bean
    public FanoutExchange exchangeNotificacoes() {
        return new FanoutExchange(EXCHANGE_NOTIFICACOES, true, false);
    }

    @Bean
    public Queue filaPolicia() {
        return new Queue(FILA_POLICIA, true);
    }

    @Bean
    public Queue filaReceita() {
        return new Queue(FILA_RECEITA, true);
    }

    @Bean
    public Binding bindPolicia(FanoutExchange exchangeNotificacoes, Queue filaPolicia) {
        return BindingBuilder.bind(filaPolicia).to(exchangeNotificacoes);
    }

    @Bean
    public Binding bindReceita(FanoutExchange exchangeNotificacoes, Queue filaReceita) {
        return BindingBuilder.bind(filaReceita).to(exchangeNotificacoes);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
