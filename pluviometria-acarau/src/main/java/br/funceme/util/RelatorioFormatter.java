package br.funceme.util;

import br.funceme.model.RegistroChuva;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class RelatorioFormatter {

    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
    private static final String SEP = "=".repeat(60);
    private static final String DIV = "-".repeat(60);

    public static void imprimirTotalPorMes(int ano, TreeMap<Integer, Double> totais) {
        cabecalho("Total de Precipitação por Mês — " + ano);
        System.out.printf("  %-15s  %10s%n", "Mês", "Total (mm)");
        System.out.println("  " + DIV);
        for (Map.Entry<Integer, Double> e : totais.entrySet())
            System.out.printf("  %-15s  %10.1f mm%n", nomeMes(e.getKey()), e.getValue());
    }

    public static void imprimirDiaMaiorMenorPrecipitacao(int ano,
                                                         Optional<RegistroChuva> maior, Optional<RegistroChuva> menor) {
        cabecalho("Dia de Maior e Menor Precipitação — " + ano);
        maior.ifPresentOrElse(
                r -> System.out.printf("  Maior: %s  ->  %.1f mm%n", r.getData(), r.getValor()),
                () -> System.out.println("  Maior: (sem dados)"));
        menor.ifPresentOrElse(
                r -> System.out.printf("  Menor: %s  ->  %.1f mm  (excluindo dias sem chuva)%n",
                        r.getData(), r.getValor()),
                () -> System.out.println("  Menor: (sem dias com chuva)"));
    }

    public static void imprimirMesMaiorMenorPrecipitacao(int ano,
                                                         Optional<Integer> maior, Optional<Integer> menor,
                                                         TreeMap<Integer, Double> totais) {
        cabecalho("Mês de Maior e Menor Precipitação — " + ano);
        maior.ifPresentOrElse(
                m -> System.out.printf("  Maior: %s  ->  %.1f mm%n", nomeMes(m), totais.get(m)),
                () -> System.out.println("  Maior: (sem dados)"));
        menor.ifPresentOrElse(
                m -> System.out.printf("  Menor: %s  ->  %.1f mm%n", nomeMes(m), totais.get(m)),
                () -> System.out.println("  Menor: (sem dados)"));
    }

    public static void imprimirMediaAnual(int ano, double media) {
        cabecalho("Média Diária de Precipitação — " + ano);
        System.out.printf("  Media anual: %.2f mm/dia%n", media);
    }

    public static void imprimirMediaPorMes(int ano, TreeMap<Integer, Double> medias) {
        cabecalho("Média Diária de Precipitação por Mês — " + ano);
        System.out.printf("  %-15s  %12s%n", "Mês", "Média (mm/dia)");
        System.out.println("  " + DIV);
        for (Map.Entry<Integer, Double> e : medias.entrySet())
            System.out.printf("  %-15s  %12.2f mm/dia%n", nomeMes(e.getKey()), e.getValue());
    }

    public static void imprimirTopDias(int ano, int quantidade, List<RegistroChuva> topDias) {
        cabecalho("Top " + quantidade + " Dias de Maior Precipitação — " + ano);
        System.out.printf("  %-5s  %-12s  %10s%n", "Rank", "Data", "Precipitação");
        System.out.println("  " + DIV);
        for (int i = 0; i < topDias.size(); i++) {
            RegistroChuva r = topDias.get(i);
            System.out.printf("  %-5d  %-12s  %10.1f mm%n", i + 1, r.getData(), r.getValor());
        }
    }

    public static void imprimirTotalAnual(int ano, double total) {
        System.out.printf("%n  Total acumulado em %d: %.1f mm%n", ano, total);
    }

    private static void cabecalho(String titulo) {
        System.out.println("\n" + SEP);
        System.out.printf("  %s%n", titulo.toUpperCase());
        System.out.println(SEP);
    }

    private static String nomeMes(int numeroMes) {
        return Month.of(numeroMes).getDisplayName(TextStyle.FULL, LOCALE_PT_BR);
    }
}