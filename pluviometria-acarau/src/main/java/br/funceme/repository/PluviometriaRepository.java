package br.funceme.repository;

import br.funceme.model.RegistroChuva;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PluviometriaRepository {

    private static final String SEPARADOR = ";";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Value("${pluviometria.arquivo:pluviometria_acarau_2025.csv}")
    private String caminhoArquivo;

    public List<RegistroChuva> carregarTodos() throws IOException {
        List<RegistroChuva> registros = new ArrayList<>();

        InputStream is = getClass().getClassLoader().getResourceAsStream(caminhoArquivo);
        if (is == null) {
            throw new IOException("Arquivo não encontrado: " + caminhoArquivo);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) { primeiraLinha = false; continue; }
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                RegistroChuva r = parseLinha(linha);
                if (r != null) registros.add(r);
            }
        }
        return registros;
    }

    public List<RegistroChuva> carregarPorAno(int ano) throws IOException {
        List<RegistroChuva> filtrados = new ArrayList<>();
        for (RegistroChuva r : carregarTodos())
            if (r.getData().getYear() == ano) filtrados.add(r);
        return filtrados;
    }

    public List<RegistroChuva> carregarPorAnoMes(int ano, int mes) throws IOException {
        List<RegistroChuva> filtrados = new ArrayList<>();
        for (RegistroChuva r : carregarPorAno(ano))
            if (r.getData().getMonthValue() == mes) filtrados.add(r);
        return filtrados;
    }

    private RegistroChuva parseLinha(String linha) {
        String[] partes = linha.split(SEPARADOR);
        if (partes.length < 4) return null;
        try {
            long id        = Long.parseLong(partes[0].trim());
            double valor   = Double.parseDouble(partes[1].trim());
            LocalDate data = LocalDate.parse(partes[2].trim(), FORMATO_DATA);
            int posto      = Integer.parseInt(partes[3].trim());
            return new RegistroChuva(id, valor, data, posto);
        } catch (Exception e) {
            System.err.println("Erro ao parsear linha: " + linha);
            return null;
        }
    }
}