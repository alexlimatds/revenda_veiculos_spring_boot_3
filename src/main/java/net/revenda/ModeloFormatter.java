package net.revenda;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import net.revenda.dominio.Modelo;
import net.revenda.dominio.ModeloRepository;

public class ModeloFormatter implements Formatter<Modelo>{

    @Autowired
    private ModeloRepository repositorio;

    @Override
    public String print(Modelo modelo, Locale locale) {
        return modelo.getId().toString();
    }

    @Override
    public Modelo parse(String text, Locale locale) throws ParseException {
        Integer id = Integer.parseInt(text);
        Optional<Modelo> obj = repositorio.findById(id);
        if(obj.isPresent())
            return obj.get();
        else
            throw new ParseException("ID inválido", 0);
    }
    
}