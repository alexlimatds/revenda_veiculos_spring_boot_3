package net.revenda;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.revenda.dominio.Fabricante;
import net.revenda.dominio.FabricanteRepository;
import net.revenda.dominio.Modelo;
import net.revenda.dominio.ModeloRepository;
import net.revenda.dominio.TipoVeiculo;
import net.revenda.dominio.TipoVeiculoRepository;
import net.revenda.dominio.Usuario;
import net.revenda.dominio.UsuarioRepository;
import net.revenda.dominio.Veiculo;
import net.revenda.dominio.VeiculoRepository;

@Component
@Profile("dev") // Este bean estará disponível somente quando o perfil dev estiver em uso
public class DataInit {
    
    @Autowired
    private FabricanteRepository fabricanteRep;
    @Autowired
    private TipoVeiculoRepository tipoVeiculoRep;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @EventListener // indica que o método deve ser executado logo em seguida à inicialização do ApplicationContext
    public void onApplicationEvent(ContextRefreshedEvent event){
        Fabricante gm = new Fabricante(null, "GM");
        Fabricante ford = new Fabricante(null, "Ford");
        Fabricante fiat = new Fabricante(null, "Fiat");
        fabricanteRep.saveAll(Arrays.asList(gm, ford, fiat));

        TipoVeiculo seda = new TipoVeiculo(null, "Sedã");
        TipoVeiculo hatch = new TipoVeiculo(null, "Hatch"); 
        TipoVeiculo utilitario = new TipoVeiculo(null, "Utilitário");
        TipoVeiculo buggy = new TipoVeiculo(null, "Buggy");
        tipoVeiculoRep.saveAll(Arrays.asList(seda, hatch, utilitario, buggy));
        
        Modelo palio = new Modelo(null, "Pálio", fiat, hatch);
        Modelo siena = new Modelo(null, "Siena", fiat, seda);
        Modelo onix = new Modelo(null, "Ônix", gm, hatch);
        modeloRepository.saveAll(Arrays.asList(palio, siena, onix));

        Veiculo mxr1611 = new Veiculo(null, "MXR1611");
        mxr1611.setAnoFabricacao(2005);
        mxr1611.setModelo(palio);
        veiculoRepository.save(mxr1611);
        Veiculo nob3507 = new Veiculo(null, "NOB3507");
        nob3507.setAnoFabricacao(2011);
        nob3507.setModelo(onix);
        veiculoRepository.save(nob3507);

        Usuario gerente = new Usuario(
            null, "Valdir Silva", "11122233344", "84988880000", "valdir", 
            "senha", true, true
        );
        usuarioRepository.save(gerente);
        Usuario vendedor = new Usuario(
            null, "Ana Silva", "55566677788", "84999991122", "ana", 
            "senha", true, false
        );
        usuarioRepository.save(vendedor);

        System.out.println("DEV: registros inseridos no banco de dados");
    }
}
