package br.edu.utfpr.td.cotsi.transacoes.producer.service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import br.edu.utfpr.td.cotsi.transacoes.producer.model.Transacao;

public class LeitorArquivo {

    public List<Transacao> lerArquivo() {
        List<Transacao> transacoes = new ArrayList<>();
        try {
            Reader leitorArquivo = new FileReader("transacoes.csv");
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader("codigo", "cedente", "pagador", "valor", "vencimento")
                    .setSkipHeaderRecord(true).build();
            CSVParser parser = format.parse(leitorArquivo);

            for (CSVRecord record : parser.getRecords()) {
                String codigo = record.get("codigo");
                String cedente = record.get("cedente");
                String pagador = record.get("pagador");
                Double valor = Double.valueOf(record.get("valor"));
                String vencimento = record.get("vencimento");

                Transacao t = new Transacao(codigo, cedente, pagador, valor, vencimento);
                transacoes.add(t);
            }

        } catch (IOException e) {
            System.out.println("Erro ao abrir arquivo CSV: " + e.getMessage());
        }
        return transacoes;
    }
}
