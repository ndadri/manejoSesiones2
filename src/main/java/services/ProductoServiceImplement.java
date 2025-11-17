package services;

import models.Producto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImplement implements ProductoService {

    /**
     * Devuelve una lista de productos predefinidos.
     *
     * @return Lista de objetos {Producto} con datos estáticos.
     *
     * Descripción:
     * Este metodo crea una lista inmutable de productos usando la clase
     * {Arrays} y el metodo {asList()}. Cada producto contiene
     * un identificador, nombre, tipo y precio definidos manualmente.
     */
    @Override
    public List<Producto> listar() {
        return Arrays.asList(
                new Producto(1L, "laptop", "computacion", 455.90),
                new Producto(2L, "mouse", "computacion", 25.50),
                new Producto(3L, "cocina", "cocina", 599.60)
        );
    }

    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getIdProducto().equals((id))).findAny();
    }

}
