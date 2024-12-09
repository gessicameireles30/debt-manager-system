package main.java.mapsproject.service;

import main.java.mapsproject.model.Divida;
import main.java.mapsproject.repository.DividaRepository;

import java.time.LocalDate;
import java.util.List;

public class GerenciadorDividas {
    private DividaRepository repository;

    public GerenciadorDividas() {
        this.repository = new DividaRepository();
    }


    public Divida adicionarDivida(double principal, double taxaJuros, LocalDate dataInicio) {
        return repository.incluirDivida(principal, taxaJuros, dataInicio);
    }


    public void pagarParcela(String dividaId, double valorPago, LocalDate dataPagamento) {
        Divida divida = repository.obterDividaPorId(dividaId);
        divida.pagarParcela(valorPago, dataPagamento);
    }


    public List<Divida> consultarDividasNaData(LocalDate data) {
        return repository.consultarDividasNaData(data);
    }
}