package net.revenda.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class FabricanteRepositoryTest {

    @Autowired
    private FabricanteRepository repositorio;

    @Test
    public void findAll(){
        // fixture
        repositorio.saveAll(Arrays.asList(
            new Fabricante(null, "GM"), 
            new Fabricante(null, "Ford"), 
            new Fabricante(null, "Fiat")
        ));
        // call
        Iterable<Fabricante> registros = repositorio.findAll();
        long qtd = StreamSupport.stream(registros.spliterator(), false).count();
        // assert
        assertEquals(3, qtd);
    }
}
