import org.bosonit.formacion.Calculadora;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraTest {

    @Test
    void suma() {
        Calculadora calculadora = new Calculadora();
        int resultado = calculadora.suma(32, 94);
        assertEquals(126,resultado); //Valor que debería dar, Variable a comparar

    }

    @Test
    void resta() {
    }

    @Test
    void division() {
    }

    @Test
    void multiplicacion() {
    }
}