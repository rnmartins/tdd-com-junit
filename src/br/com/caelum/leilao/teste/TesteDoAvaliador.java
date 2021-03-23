package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteDoAvaliador {
    private Avaliador leiloeiro;
    private Usuario renan;
    private Usuario nadia;
    private Usuario maria;

    @BeforeEach
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.renan = new Usuario("Renan");
        this.nadia = new Usuario("Nadia");
        this.maria = new Usuario("Maria");
    }

    @Test
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        try {
            Leilao leilao = new CriadorDeLeilao().para("PlayStation 5").constroi();
            leiloeiro.avalia(leilao);
            Assertions.fail();
        } catch(RuntimeException exception) {

        }
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
//        Parte I: cenário
        Leilao leilao = new CriadorDeLeilao().para("XBox 360")
                .lance(renan, 250.0)
                .lance(nadia, 300.0)
                .lance(maria, 400.0)
                .constroi();

//        Parte II: ação
        leiloeiro.avalia(leilao);

//        Parte III: validação
        double maiorEsperado = 400.0;
        double menorEsperado = 250.0;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        Leilao leilao = new Leilao("Computador");

        leilao.propoe(new Lance(renan, 1000.0));

        leiloeiro.avalia(leilao);

        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        Leilao leilao = new CriadorDeLeilao().para("Fone de ouvido")
                    .lance(renan, 100.0)
                    .lance(nadia, 200.0)
                    .lance(renan, 300.0)
                    .lance(nadia, 400.0)
                    .constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
        assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
    }
}
