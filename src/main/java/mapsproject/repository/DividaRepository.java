package main.java.mapsproject.repository;

import main.java.mapsproject.model.Divida;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DividaRepository {
    private Map<String, Divida> dividas = new HashMap<>();
    private AtomicInteger contador = new AtomicInteger(1);


    public Divida incluirDivida(double principal, double taxaJuros, LocalDate dataInicio) {
        String id = "DIV-" + contador.getAndIncrement();
        Divida divida = new Divida(id, principal, dataInicio, taxaJuros);
        dividas.put(id, divida);
        return divida;
    }


    public List<Divida> consultarDividasNaData(LocalDate data) {
        return dividas.values().stream()
                .filter(divida -> {
                    divida.calcularJuros(data);
                    return true;
                })
                .collect(Collectors.toList());
    }


    public Divida obterDividaPorId(String id) {
        Divida divida = dividas.get(id);
        if (divida == null) {
            throw new NoSuchElementException("Dívida não encontrada");
        }
        return divida;
    }
}

