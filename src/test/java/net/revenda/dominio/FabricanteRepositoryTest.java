package net.revenda.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
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
