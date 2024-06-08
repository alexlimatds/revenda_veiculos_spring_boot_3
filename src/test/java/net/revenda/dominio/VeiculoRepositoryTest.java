package net.revenda.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository repositorioVeiculo;
    @Autowired
    private FabricanteRepository repositorioFabricante;
    @Autowired
    private TipoVeiculoRepository repositorioTipoVeiculo;
    @Autowired
    private ModeloRepository repositorioModelo;

    @Test
    public void findVeiculoByFields_semFiltros(){
        // fixture
        String placa = null;
        Integer idFabricante = null;
        Integer idModelo = null;
        
        // call and assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            repositorioVeiculo.findVeiculoByFields(placa, idFabricante, idModelo);
        });
        assertEquals(IllegalArgumentException.class, exception.getCause().getClass()); 
    }

    @Test
    public void findVeiculoByFields_Fabricante(){
        // fixture
        Fabricante gm = new Fabricante(null, "GM");
        Fabricante ford = new Fabricante(null, "Ford");
        repositorioFabricante.saveAll(Arrays.asList(gm, ford));

        TipoVeiculo hatch = new TipoVeiculo(null, "Hatch"); 
        repositorioTipoVeiculo.saveAll(Arrays.asList(hatch));
        
        Modelo onix = new Modelo(null, "Ônix", gm, hatch);
        repositorioModelo.saveAll(Arrays.asList(onix));

        Veiculo nob3507 = new Veiculo(null, "NOB3507");
        nob3507.setAnoFabricacao(2011);
        nob3507.setModelo(onix);
        repositorioVeiculo.save(nob3507);
        
        // call
        List<Veiculo> veiculos_1 = repositorioVeiculo.findVeiculoByFields(null, gm.getId(), null);
        List<Veiculo> veiculos_0 = repositorioVeiculo.findVeiculoByFields(null, ford.getId(), null);
        
        // assert
        assertEquals(1, veiculos_1.size());
        assertEquals(0, veiculos_0.size());
    }

    @Test
    public void findVeiculoByFields_Modelo(){
        // fixture
        Fabricante gm = new Fabricante(null, "GM");
        repositorioFabricante.saveAll(Arrays.asList(gm));

        TipoVeiculo hatch = new TipoVeiculo(null, "Hatch"); 
        repositorioTipoVeiculo.saveAll(Arrays.asList(hatch));
        
        Modelo onix = new Modelo(null, "Ônix", gm, hatch);
        Modelo corsa = new Modelo(null, "Corsa", gm, hatch);
        repositorioModelo.saveAll(Arrays.asList(onix, corsa));

        Veiculo nob3507 = new Veiculo(null, "NOB3507");
        nob3507.setAnoFabricacao(2011);
        nob3507.setModelo(onix);
        repositorioVeiculo.save(nob3507);
        
        // call
        List<Veiculo> veiculos_1 = repositorioVeiculo.findVeiculoByFields(null, null, onix.getId());
        List<Veiculo> veiculos_0 = repositorioVeiculo.findVeiculoByFields(null, null, corsa.getId());
        
        // assert
        assertEquals(1, veiculos_1.size());
        assertEquals(0, veiculos_0.size());
    }

    @Test
    public void findVeiculoByFields_Placa(){
        // fixture
        Fabricante gm = new Fabricante(null, "GM");
        repositorioFabricante.saveAll(Arrays.asList(gm));

        TipoVeiculo hatch = new TipoVeiculo(null, "Hatch"); 
        repositorioTipoVeiculo.saveAll(Arrays.asList(hatch));
        
        Modelo onix = new Modelo(null, "Ônix", gm, hatch);
        repositorioModelo.saveAll(Arrays.asList(onix));

        String placa = "NOB3507";
        Veiculo nob3507 = new Veiculo(null, placa);
        nob3507.setAnoFabricacao(2011);
        nob3507.setModelo(onix);
        repositorioVeiculo.save(nob3507);
        
        // call
        List<Veiculo> veiculos_1a = repositorioVeiculo.findVeiculoByFields(placa, null, null);
        List<Veiculo> veiculos_1b = repositorioVeiculo.findVeiculoByFields("NOB", null, null);
        List<Veiculo> veiculos_0 = repositorioVeiculo.findVeiculoByFields("NNN", null, null);
        
        // assert
        assertEquals(1, veiculos_1a.size());
        assertEquals(1, veiculos_1b.size());
        assertEquals(0, veiculos_0.size());
    }

    @Test
    public void findVeiculoByFields_FabricanteModelo(){
        // fixture
        Fabricante gm = new Fabricante(null, "GM");
        repositorioFabricante.saveAll(Arrays.asList(gm));

        TipoVeiculo hatch = new TipoVeiculo(null, "Hatch"); 
        repositorioTipoVeiculo.saveAll(Arrays.asList(hatch));
        
        Modelo onix = new Modelo(null, "Ônix", gm, hatch);
        Modelo corsa = new Modelo(null, "Corsa", gm, hatch);
        repositorioModelo.saveAll(Arrays.asList(onix, corsa));

        Veiculo nob3507 = new Veiculo(null, "NOB3507");
        nob3507.setAnoFabricacao(2011);
        nob3507.setModelo(onix);
        repositorioVeiculo.save(nob3507);
        
        // call
        List<Veiculo> veiculos_1 = repositorioVeiculo.findVeiculoByFields(null, gm.getId(), onix.getId());
        List<Veiculo> veiculos_0 = repositorioVeiculo.findVeiculoByFields(null, gm.getId(), corsa.getId());
        
        // assert
        assertEquals(1, veiculos_1.size());
        assertEquals(0, veiculos_0.size());
    }
}
