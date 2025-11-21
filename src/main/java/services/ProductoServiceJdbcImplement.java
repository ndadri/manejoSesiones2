package services;

// Importaciones de clases de modelo y repositorio, esenciales para el funcionamiento del servicio.
import models.Categoria;
import models.Producto;
import repositorio.CategoriaRepositoryJdbcImplement;
import repositorio.ProductoRepositoryJdbcImplement;
import repositorio.Repository;

// Importaciones estándar de Java para manejo de bases de datos, colecciones y excepciones.
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Implementación concreta del servicio de productos utilizando JDBC (Java Database Connectivity).
 * Esta clase actúa como la capa de lógica de negocio, intermediando entre el controlador (Servlet)
 * y la capa de persistencia (Repositorio).
 */
public class ProductoServiceJdbcImplement implements ProductoService {

    // --- Declaración de Repositorios (Inyección Manual de Dependencias) ---

    /**
     * Repositorio genérico para la entidad Producto.
     * Se usa la interfaz Repository<Producto> para mantener el desacoplamiento.
     */
    private Repository<Producto> repositoryJdbc;

    /**
     * Repositorio para la entidad Categoria, necesario para listar categorías.
     */
    private Repository<Categoria> repositoryCategoriaJdbc;

    // --- Constructor ---

    /**
     * Constructor del servicio. Recibe la conexión activa a la base de datos,
     * inyectando la dependencia necesaria para inicializar los repositorios.
     * @param connection La conexión JDBC activa.
     */
    public ProductoServiceJdbcImplement(Connection connection) {
        // Inicialización de las implementaciones concretas de los repositorios.
        this.repositoryJdbc = new ProductoRepositoryJdbcImplement(connection);
        this.repositoryCategoriaJdbc = new CategoriaRepositoryJdbcImplement(connection);
    }

    // --- Implementación de Métodos de la Interfaz ProductoService ---

    @Override
    /**
     * Obtiene una lista de todos los productos.
     * Delega la ejecución de la consulta SELECT al repositorio.
     * Maneja las excepciones SQL, relanzándolas como excepciones de servicio (ServiceJdbcException).
     */
    public List<Producto> listar() {
        try{
            return repositoryJdbc.listar();
        }catch(SQLException throwables){
            throw new ServiceJbdcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    /**
     * Busca un producto por su identificador único (ID).
     * El resultado se envuelve en un Optional<Producto> para manejar la posible ausencia del registro.
     */
    public Optional<Producto> porId(Long id) {
        try{
            return Optional.ofNullable(repositoryJdbc.porId(id));
        }catch (SQLException throwables){
            throw new ServiceJbdcException(throwables.getMessage(), throwables.getCause());

        }
    }

    @Override
    /**
     * Persiste un objeto Producto (realiza un INSERT o UPDATE).
     * La lógica para determinar si es creación o actualización reside en la capa de persistencia (Repositorio).
     */
    public void guardar(Producto producto) {
        try {
            repositoryJdbc.guardar(producto);
        } catch (SQLException throwables) {
            throw new ServiceJbdcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    /**
     * Realiza la baja lógica (desactivación) de un producto, estableciendo su condición a 0.
     */
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        }catch (SQLException throwables) {
            throw new ServiceJbdcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    /**
     * Obtiene la lista completa de categorías disponibles, utilizando el repositorio de Categoría.
     */
    public List<Categoria> listarCategorias() {
        try{
            return repositoryCategoriaJdbc.listar();
        }catch (SQLException throwables) {
            throw new ServiceJbdcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    /**
     * Busca una categoría específica por su ID.
     */
    public Optional<Categoria> porIdCategoria(Long id) {
        try{
            return Optional.ofNullable(repositoryCategoriaJdbc.porId(id));
        }catch (SQLException throwables) {
            throw new ServiceJbdcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    /**
     * Activa un producto previamente desactivado, estableciendo su condición a 1.
     * Complementa el método 'eliminar' para la gestión de estados.
     */
    public void activar(Long id) {
        try {
            repositoryJdbc.activar(id);
        } catch (SQLException e) {
            throw new ServiceJbdcException(e.getMessage(), e.getCause());
        }
    }

}
