package services;

import models.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    /**
     * Metodo que devuelve una lista de productos disponibles.
     *
     * @return Lista de objetos {Producto}.
     *
     * Descripción:
     * Permite obtener todos los productos registrados en el sistema o
     * fuente de datos correspondiente. Su implementación dependerá de la
     * clase que la utilice, como {ProductosServicesImplement}.
     */
    List<Producto> listar();
    Optional<Producto> porId(Long id);
}

