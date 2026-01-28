package br.edu.utfpr.td.cotsi.transacoes.producer;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.edu.utfpr.td.cotsi.transacoes.producer.config.RabbitMQConfig;
import br.edu.utfpr.td.cotsi.transacoes.producer.model.Transacao;
import br.edu.utfpr.td.cotsi.transacoes.producer.service.LeitorArquivo;

@SpringBootApplication
public class TransacoesProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransacoesProducerApplication.class, args);
    }

    @Bean
    public CommandLineRunner enviar(RabbitTemplate rabbitTemplate) {
        return args -> {
            LeitorArquivo leitor = new LeitorArquivo();
            List<Transacao> transacoes = leitor.lerArquivo();

            if (transacoes.isEmpty()) {
                System.out.println("Nenhuma transação encontrada no arquivo.");
                return;
            }

            for (Transacao t : transacoes) {
                // envia para fila principal
                rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_TRANSACOES, t);
                System.out.println("Enviado: " + t);
            }

            System.out.println("Envio concluído. Total enviado: " + transacoes.size());
        };
    }
}
