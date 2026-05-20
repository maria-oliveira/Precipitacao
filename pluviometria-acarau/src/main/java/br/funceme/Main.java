package br.funceme;

import br.funceme.model.RegistroChuva;
import br.funceme.service.PluviometriaService;
import br.funceme.util.RelatorioFormatter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@SpringBootApplication
public class Main implements CommandLineRunner {

    private final PluviometriaService service;

    // Spring injeta automaticamente via construtor
    public Main(PluviometriaService service) {
        this.service = service;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int ano = 2025;
        int topDias = 10;

        System.out.println("RELATORIO DE PLUVIOMETRIA — ACARAU / CE — 2025");

        TreeMap<Integer, Double> totaisMensais = service.totalPorMes(ano);
        RelatorioFormatter.imprimirTotalPorMes(ano, totaisMensais);

        Optional<RegistroChuva> diaMaior = service.diaMaiorPrecipitacao(ano);
        Optional<RegistroChuva> diaMenor = service.diaMenorPrecipitacao(ano);
        RelatorioFormatter.imprimirDiaMaiorMenorPrecipitacao(ano, diaMaior, diaMenor);

        Optional<Integer> mesMaior = service.mesMaiorPrecipitacao(ano);
        Optional<Integer> mesMenor = service.mesMenorPrecipitacao(ano);
        RelatorioFormatter.imprimirMesMaiorMenorPrecipitacao(ano, mesMaior, mesMenor, totaisMensais);

        double mediaAnual = service.mediaDiariaPorAno(ano);
        RelatorioFormatter.imprimirMediaAnual(ano, mediaAnual);

        TreeMap<Integer, Double> mediasMensais = service.mediaDiariaPorMes(ano);
        RelatorioFormatter.imprimirMediaPorMes(ano, mediasMensais);

        List<RegistroChuva> top = service.topDiasMaiorPrecipitacao(ano, topDias);
        RelatorioFormatter.imprimirTopDias(ano, topDias, top);

        double totalAnual = service.totalAnual(ano);
        RelatorioFormatter.imprimirTotalAnual(ano, totalAnual);
    }
}