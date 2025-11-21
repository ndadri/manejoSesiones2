package services;

import models.Categoria;
import models.Producto;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz ProductoServices.
 * Descripción:
 * Define el contrato que deben implementar las clases encargadas de manejar
 * las operaciones relacionadas con los objetos de tipo {@link Producto}.
 *
 * Esta interfaz permite desacoplar la lógica de negocio de la implementación
 * concreta, facilitando la reutilización y mantenimiento del código.
 */
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
    void guardar(Producto producto);
    void eliminar(Long id);
    //implementamos un metodo para listar una categoria y traer la categoria por if
    List<Categoria> listarCategorias();
    Optional<Categoria> porIdCategoria(Long id);
    void activar(Long id);
}

