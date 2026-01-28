# Sistema de Processamento de Transações com RabbitMQ

Projeto desenvolvido em **Java** para a disciplina **APIs e Web Services**, com o objetivo de implementar uma **aplicação distribuída baseada em eventos**, utilizando o protocolo **AMQP** e o broker de mensagens **RabbitMQ**.

---

## 📚 Descrição do Projeto

Este projeto tem como finalidade o **processamento de transações financeiras por meio de mensageria**, simulando um ambiente distribuído com **produtor e múltiplos consumidores**.

O sistema é composto por:

- **Produtor de mensagens**
- **Consumidor principal de transações**
- **Consumidor da Polícia Federal**
- **Consumidor da Receita Federal**

O **Produtor** é responsável por realizar a **leitura de um arquivo CSV** contendo dados de transações financeiras. Cada registro do arquivo é convertido em um **evento** e enviado para a fila **`transacoes.financeiras`**.

O **Consumidor principal** processa cada transação recebida, exibindo seus dados na saída padrão e simulando um **tempo de processamento de 1 segundo por transação**.

Para todas as transações cujo **valor seja maior ou igual a R$ 40.000**, o sistema realiza uma **notificação automática** à **Polícia Federal** e à **Receita Federal**.

Essa notificação é feita por meio de um **Exchange do tipo fanout**, garantindo que a mesma mensagem seja enviada simultaneamente para as duas filas de notificação.

---

## 🔁 Arquitetura da Solução

- **Fila principal:** `transacoes.financeiras`
- **Exchange de notificação:** `notificacoes.fanout`
- **Fila da Polícia Federal:** `policia.federal`
- **Fila da Receita Federal:** `receita.federal`

Quando uma transação atende ao critério de valor (≥ R$ 40.000), o consumidor principal envia o evento para o exchange `notificacoes.fanout`, que distribui automaticamente a mensagem para ambas as filas de notificação.

---

## 🛠️ Tecnologias Utilizadas

- Java  
- Spring Boot  
- Spring AMQP  
- RabbitMQ  
- AMQP  
- Maven  
- Apache Commons CSV  

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

- Java 17 ou superior  
- Maven  
- RabbitMQ em execução  

---

### Passos para execução

1. Clonar o repositório:
    ```bash
    git clone https://github.com/eduardoalmeidajesus/transacoes-rabbitmq

2. Acessar a pasta do projeto:
    ```bash
    cd transacoes-rabbitmq

3. Executar os consumidores (em terminais separados):
    ```bash
    cd transacoes-consumer
    mvn spring-boot:run
    Executar as classes:
    TransacoesConsumerApplication
    TransacoesPoliciaApplication
    TransacoesReceitaApplication

4. Executar o produtor:
    ```bash
    cd transacoes-producer
    mvn spring-boot:run

---

📄 Arquivo CSV

O arquivo transacoes.csv contém os dados das transações financeiras que serão processadas pelo produtor.
Cada linha do arquivo representa uma transação que será enviada como evento para o RabbitMQ.

---

📌 Observações

Cada aplicação possui sua própria classe main, permitindo a execução independente do produtor e dos consumidores.

O RabbitMQ é responsável pela criação automática das filas e do exchange durante a execução da aplicação.
