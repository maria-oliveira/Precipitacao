package br.funceme.service;

import br.funceme.model.RegistroChuva;
import br.funceme.repository.PluviometriaRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PluviometriaService {

    private final PluviometriaRepository repository;

    public PluviometriaService(PluviometriaRepository repository) {
        this.repository = repository;
    }

    public TreeMap<Integer, Double> totalPorMes(int ano) throws IOException {
        List<RegistroChuva> registros = repository.carregarPorAno(ano);
        TreeMap<Integer, Double> totais = new TreeMap<>();

        for (int mes = 1; mes <= 12; mes++) totais.put(mes, 0.0);

        for (RegistroChuva r : registros) {
            int mes = r.getData().getMonthValue();
            totais.put(mes, totais.get(mes) + r.getValor());
        }
        return totais;
    }

    public Optional<RegistroChuva> diaMaiorPrecipitacao(int ano) throws IOException {
        return repository.carregarPorAno(ano).stream()
                .max(Comparator.comparingDouble(RegistroChuva::getValor));
    }

    public Optional<RegistroChuva> diaMenorPrecipitacao(int ano) throws IOException {
        return repository.carregarPorAno(ano).stream()
                .filter(r -> r.getValor() > 0)
                .min(Comparator.comparingDouble(RegistroChuva::getValor));
    }

    public Optional<Integer> mesMaiorPrecipitacao(int ano) throws IOException {
        return totalPorMes(ano).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public Optional<Integer> mesMenorPrecipitacao(int ano) throws IOException {
        return totalPorMes(ano).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public double mediaDiariaPorAno(int ano) throws IOException {
        List<RegistroChuva> registros = repository.carregarPorAno(ano);
        if (registros.isEmpty()) return 0.0;
        double soma = registros.stream().mapToDouble(RegistroChuva::getValor).sum();
        return soma / registros.size();
    }

    public TreeMap<Integer, Double> mediaDiariaPorMes(int ano) throws IOException {
        TreeMap<Integer, Double> medias = new TreeMap<>();
        for (int mes = 1; mes <= 12; mes++) {
            List<RegistroChuva> registrosMes = repository.carregarPorAnoMes(ano, mes);
            if (registrosMes.isEmpty()) {
                medias.put(mes, 0.0);
            } else {
                double soma = registrosMes.stream().mapToDouble(RegistroChuva::getValor).sum();
                medias.put(mes, soma / registrosMes.size());
            }
        }
        return medias;
    }

    public List<RegistroChuva> topDiasMaiorPrecipitacao(int ano, int quantidade) throws IOException {
        List<RegistroChuva> ordenados = new ArrayList<>(repository.carregarPorAno(ano));
        ordenados.sort(Comparator.comparingDouble(RegistroChuva::getValor).reversed());
        return ordenados.subList(0, Math.min(quantidade, ordenados.size()));
    }

    public double totalAnual(int ano) throws IOException {
        return repository.carregarPorAno(ano).stream()
                .mapToDouble(RegistroChuva::getValor).sum();
    }
}