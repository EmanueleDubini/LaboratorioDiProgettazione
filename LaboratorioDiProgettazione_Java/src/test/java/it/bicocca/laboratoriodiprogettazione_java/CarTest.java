package it.bicocca.laboratoriodiprogettazione_java;

import it.bicocca.laboratoriodiprogettazione_java.entity.Car;
import it.bicocca.laboratoriodiprogettazione_java.entity.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {
    @Test
    public void testCostruttore() {
        // Verifica che il costruttore crei correttamente un'istanza di Client
        Car car1 = new Car("FZ038UE", "VU8493823hd83hs", "Fiat", "Panda", "1", "Problema elettrico", "Preso in Carico");
        Car car2 = new Car("FZ038UE","VU8493823hd83hs", "Fiat", "Panda", "1", "1", "Problema elettrico", "Preso in Carico");

        assertEquals("FZ038UE", car1.getTarga());
        assertEquals("VU8493823hd83hs", car1.getTelaio());
        assertEquals("Fiat", car1.getMarcaVettura());
        assertEquals("Panda", car1.getModello());
        assertEquals("1", car1.getIdDipendente());
        assertEquals("Problema elettrico", car1.getDiagnosi());
        assertEquals("Preso in Carico", car1.getStatoRiparazione());

        assertEquals("FZ038UE", car2.getTarga());
        assertEquals("VU8493823hd83hs", car2.getTelaio());
        assertEquals("Fiat", car2.getMarcaVettura());
        assertEquals("Panda", car2.getModello());
        assertEquals("1", car2.getIdDipendente());
        assertEquals("1", car2.getIdCliente());
        assertEquals("Problema elettrico", car2.getDiagnosi());
        assertEquals("Preso in Carico", car2.getStatoRiparazione());

    }

    @Test
    public void testGetterSetter() {
        // Verifica che i metodi getter e setter funzionino correttamente
        Car car = new Car("FZ038UE", "VU8493823hd83hs", "Fiat", "Panda", "1", "Problema elettrico", "Preso in Carico");

        car.setTarga("ZH839JA");
        car.setTelaio("VU647823hd83abj");
        car.setMarcaVettura("Lancia");
        car.setModello("Ypsilon");
        car.setIdDipendente("2");
        car.setIdCliente("2");
        car.setDiagnosi("Problema meccanico");
        car.setStatoRiparazione("In riparazione");

        assertEquals("ZH839JA", car.getTarga());
        assertEquals("VU647823hd83abj", car.getTelaio());
        assertEquals("Lancia", car.getMarcaVettura());
        assertEquals("Ypsilon", car.getModello());
        assertEquals("2", car.getIdDipendente());
        assertEquals("2", car.getIdCliente());
        assertEquals("Problema meccanico", car.getDiagnosi());
        assertEquals("In riparazione", car.getStatoRiparazione());
    }
}
