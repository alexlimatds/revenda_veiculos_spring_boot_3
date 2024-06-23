package net.revenda;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import net.revenda.dominio.CPF;

public class CpfFormatterTest {

    @Test
    void parseOkApenasDigitos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        fmt.parse("11122233300", null);
    }

    @Test
    void parseOkComPontos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        fmt.parse("111.222.333.00", null);
    }

    @Test
    void parseOkComTracos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        fmt.parse("111_222_333-00", null);
    }

    @Test
    void parseOkComPontosETracos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        fmt.parse("-111.222.333-00_", null);
    }

    @Test
    void parseFalhaComPontosETracos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        assertThrows(ParseException.class, () -> {
            fmt.parse("-11.222.333-00_", null);
        });
    }

    @Test
    void parseOkComEspacos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        fmt.parse("111 222 333 00  ", null);
    }

    @Test
    void parseFalhaComEspacos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        assertThrows(ParseException.class, () -> {
            fmt.parse("11 22233300", null);
        });
    }

    @Test
    void parseFalhaApenasDigitos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        assertThrows(ParseException.class, () -> {
            fmt.parse("1112223330", null);
        });
    }

    @Test
    void parseFalhaCaracteresInvalidos() throws ParseException{
        CpfFormatter fmt = new CpfFormatter();
        assertThrows(ParseException.class, () -> {
            fmt.parse("111.222.333-00A", null);
        });
    }

    @Test
    void print() throws ParseException{
        CPF cpf = new CPF("11122233300");
        CpfFormatter fmt = new CpfFormatter();

        String saida = fmt.print(cpf, null);

        assertEquals("111.222.333-00", saida);
    }
}
