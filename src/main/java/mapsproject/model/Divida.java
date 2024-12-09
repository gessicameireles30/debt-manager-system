package main.java.mapsproject.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Divida {
    private String id;
    private double principal;
    private LocalDate dataInicio;
    private double taxaJurosAnual;
    private double saldo;
    private double valorJuros;
    private List<Parcela> parcelas;
    private LocalDate dataPagamento;


    public Divida(String id, double principal, LocalDate dataInicio, double taxaJurosAnual) {
        validarPrincipal(principal);

        this.id = id;
        this.principal = principal;
        this.dataInicio = dataInicio;
        this.taxaJurosAnual = taxaJurosAnual;
        this.saldo = principal;
        this.valorJuros = 0.0;
        this.parcelas = new ArrayList<>();
    }


    private void validarPrincipal(double principal) {
        if (principal < 0) {
            throw new IllegalArgumentException("Principal não pode ser negativo");
        }


        long centavos = Math.round(principal * 100);
        if (centavos != principal * 100) {
            throw new IllegalArgumentException("Principal não pode ter frações de centavos");
        }
    }


    public void calcularJuros(LocalDate dataCalculo) {
        if (dataCalculo.isBefore(dataInicio)) {
            return;
        }

        long diasUteis = calcularDiasUteis(dataInicio, dataCalculo);
        double fatorJuros = Math.pow((1 + taxaJurosAnual / 100), diasUteis / 252.0);

        double novoSaldo = truncar(saldo * fatorJuros);
        valorJuros = truncar(novoSaldo - saldo);
    }


    private long calcularDiasUteis(LocalDate inicio, LocalDate fim) {
        return inicio.datesUntil(fim.plusDays(1))
                .filter(data -> data.getDayOfWeek() != DayOfWeek.SATURDAY
                        && data.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();
    }


    private double truncar(double valor) {
        return Math.floor(valor * 100) / 100;
    }


    public void pagarParcela(double valorPago, LocalDate dataPagamento) {
        validarPagamento(valorPago, dataPagamento);

        calcularJuros(dataPagamento);

        double valorJurosPago = Math.min(valorJuros, valorPago);
        double valorSaldoPago = Math.min(saldo, valorPago - valorJurosPago);


        Parcela parcela = new Parcela(dataPagamento, valorPago, valorJurosPago, valorSaldoPago);
        parcelas.add(parcela);


        valorJuros -= valorJurosPago;
        saldo -= valorSaldoPago;


        if (saldo == 0 && valorJuros == 0) {
            this.dataPagamento = dataPagamento;
        }
    }


    private void validarPagamento(double valorPago, LocalDate dataPagamento) {
        if (valorPago < 0) {
            throw new IllegalArgumentException("Valor pago não pode ser negativo");
        }

        long centavos = Math.round(valorPago * 100);
        if (centavos != valorPago * 100) {
            throw new IllegalArgumentException("Valor pago não pode ter frações de centavos");
        }

        if (dataPagamento.isBefore(dataInicio)) {
            throw new IllegalArgumentException("Data de pagamento deve ser posterior à data de início");
        }


        boolean pagamentoDuplicado = parcelas.stream()
                .anyMatch(p -> p.getDataPagamento().equals(dataPagamento));
        if (pagamentoDuplicado) {
            throw new IllegalArgumentException("Não podem haver dois pagamentos no mesmo dia");
        }


        double totalDevido = saldo + valorJuros;
        if (valorPago > totalDevido) {
            throw new IllegalArgumentException("Valor pago não pode ser maior que o valor devido");
        }
    }

    public String getId() { return id; }
    public double getPrincipal() { return principal; }
    public LocalDate getDataInicio() { return dataInicio; }
    public double getSaldo() { return saldo; }
    public double getValorJuros() { return valorJuros; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public double getTotalJurosPagos() {
        return parcelas.stream()
                .mapToDouble(Parcela::getValorJurosPago)
                .sum();
    }
}
