package br.funceme.model;

import java.time.LocalDate;

public class RegistroChuva {

    private final long id;
    private final double valor;
    private final LocalDate data;
    private final int posto;

    public RegistroChuva(long id, double valor, LocalDate data, int posto) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.posto = posto;
    }

    public long getId() { return id; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public int getPosto() { return posto; }

    @Override
    public String toString() {
        return String.format("RegistroChuva{id=%d, data=%s, valor=%.1f mm, posto=%d}",
                id, data, valor, posto);
    }
}