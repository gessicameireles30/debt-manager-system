package main.java.mapsproject.test;

import main.java.mapsproject.service.GerenciadorDividas;
import main.java.mapsproject.model.Divida;
import java.time.LocalDate;

public class GerenciadorDividasTest {
    private GerenciadorDividas gerenciador;

    public GerenciadorDividasTest() {
        gerenciador = new GerenciadorDividas();
    }

    public void testAdicionarDivida() {
        Divida divida = gerenciador.adicionarDivida(1000, 5, LocalDate.now());
        assert divida != null : "Erro: A dívida não foi adicionada.";
        System.out.println("O teste adicionar divida passou!");

    }

    public void testConsultarDivida() {
        Divida divida = gerenciador.adicionarDivida(1000, 5, LocalDate.now());
        Divida dividaConsultada = gerenciador.consultarDividasNaData(LocalDate.now()).get(0);
        assert dividaConsultada.getId().equals(divida.getId()) : "Erro: A dívida consultada não corresponde à dívida adicionada.";
        System.out.println("O teste consultar divida passou!");

    }

    public void testPagarParcela() {
        Divida divida = gerenciador.adicionarDivida(1000, 5, LocalDate.now());
        gerenciador.pagarParcela(divida.getId(), 200, LocalDate.now());
        assert divida.getSaldo() < 1000 : "Erro: O saldo da dívida não foi atualizado após o pagamento.";
        System.out.println("O teste pagar parcela passou!");

    }

    public void testPagarParcelaComQuitacao() {
        Divida divida = gerenciador.adicionarDivida(1000, 5, LocalDate.now());
        gerenciador.pagarParcela(divida.getId(), 1, LocalDate.now());
        LocalDate dataFutura = LocalDate.now().plusDays(1);
        gerenciador.pagarParcela(divida.getId(), 800, dataFutura);
        assert divida.getSaldo() == 0 : "Erro: A dívida não foi quitada corretamente.";
        System.out.println("O teste pagar parcela com quitação passou!");
    }

    public static void main(String[] args) {
        GerenciadorDividasTest test = new GerenciadorDividasTest();
        
        test.testAdicionarDivida();
        test.testConsultarDivida();
        test.testPagarParcela();
        test.testPagarParcelaComQuitacao();
        
        System.out.println("Todos os testes passaram!");
    }
} 