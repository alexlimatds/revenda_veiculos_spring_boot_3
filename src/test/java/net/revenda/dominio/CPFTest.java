package net.revenda.dominio;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CPFTest {

    @Test
    void ok11Digitos(){
        new CPF("11122233300");
    }

    @Test
    void falha13Digitos(){
        assertThrows(IllegalArgumentException.class, () -> {
            new CPF("111222333009");
        });
    }

    @Test
    void falha9Digitos(){
        assertThrows(IllegalArgumentException.class, () -> {
            new CPF("111222333");
        });
    }

    @Test
    void falha14Caracteres(){
        assertThrows(IllegalArgumentException.class, () -> {
            new CPF("111.222.333-00");
        });
    }

    @Test
    void falha11Caracteres(){
        assertThrows(IllegalArgumentException.class, () -> {
            new CPF("111.222.33-");
        });
    }
}
