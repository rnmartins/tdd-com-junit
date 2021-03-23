package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeilaoTete {
    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        assertEquals(0, leilao.getLances().size());

        leilao.propoe(new Lance(new Usuario("Renan"), 2000.0));
        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void deveReceberVariosLances() {
        Leilao leilao = new Leilao("Macbook Pro 15");
        leilao.propoe(new Lance(new Usuario("Iara"), 2000.0));
        leilao.propoe(new Lance(new Usuario("Íris"), 3000.0));

        assertEquals(2, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
        assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
        Leilao leilao = new Leilao("Camisa autografada do Ronaldinho Gaúcho");
        Usuario renan = new Usuario("Renan");

        leilao.propoe(new Lance(renan, 2000.0));
        leilao.propoe(new Lance(renan, 3000.0));
        
        assertEquals(1, leilao.getLances().size());
        assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
    }

    @Test
    public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
        Leilao leilao = new Leilao("Camisa autografada do Ronaldinho Gaúcho");
        Usuario renan = new Usuario("Renan");
        Usuario iara = new Usuario("Iara");

        leilao.propoe(new Lance(renan, 2000.0));
        leilao.propoe(new Lance(iara, 3000.0));

        leilao.propoe(new Lance(renan, 4000.0));
        leilao.propoe(new Lance(iara, 5000.0));

        leilao.propoe(new Lance(renan, 6000.0));
        leilao.propoe(new Lance(iara, 7000.0));

        leilao.propoe(new Lance(renan, 8000.0));
        leilao.propoe(new Lance(iara, 9000.0));

        leilao.propoe(new Lance(renan, 10000.0));
        leilao.propoe(new Lance(iara, 11000.0));

//        Este lance deve ser ignorado
        leilao.propoe(new Lance(renan, 10000.0));
        
        assertEquals(10, leilao.getLances().size());
        assertEquals(11000, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);
    }
}
