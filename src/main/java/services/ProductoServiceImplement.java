package services;

import models.Categoria;
import models.Producto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**.
 * Esta clase simula la obtención de datos desde una fuente estática,
 * como si provinieran de una base de datos o servicio externo.
 * Es útil para pruebas, demostraciones o cuando aún no se integra
 * una capa de persistencia real.
 */
public class ProductoServiceImplement implements ProductoService {

    @Override
    public List<Producto> listar() {
        return List.of();
    }
    @Override
    public Optional<Producto> porId(Long id) {
        return listar().stream().filter(p -> p.getId().equals((id))).findAny();
    }

    @Override
    public void guardar(Producto producto) {

    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public List<Categoria> listarCategorias() {
        return List.of();
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        return Optional.empty();
    }

    @Override
    public void activar(Long id) {

    }
}
