package main.java.mapsproject.model;

import java.time.LocalDate;

public class Parcela {
    private LocalDate dataPagamento;
    private double valorPago;
    private double valorJurosPago;
    private double valorSaldoPago;

    public Parcela(LocalDate dataPagamento, double valorPago,
                   double valorJurosPago, double valorSaldoPago) {
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.valorJurosPago = valorJurosPago;
        this.valorSaldoPago = valorSaldoPago;
    }


    public LocalDate getDataPagamento() { return dataPagamento; }
    public double getValorPago() { return valorPago; }
    public double getValorJurosPago() { return valorJurosPago; }
    public double getValorSaldoPago() { return valorSaldoPago; }
}
