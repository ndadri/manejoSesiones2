package repositorio;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * Repository<T> es un contrato genérico para operaciones
 * básicas de persistencia (CRUD mínimo) sobre objetos de tipo T.
 * Define qué debe hacerse (listar, buscar por id, guardar, eliminar)
 * pero no cómo — eso lo hace una clase que implemente la interfaz.
 */
public interface Repository<T>{
    //Devuelve una List con todos los objetos ej. todos los productos
    List<T> listar() throws SQLException;

    //Busca y devuelve una entidad T por su identificador.
    T porId(Long id) throws SQLException;

    //Sirve para insertar o actualizar una entidad T.
    void guardar(T t) throws SQLException;

    //Borra la entidad con el id dado.
    void eliminar(Long id) throws SQLException;

    //activa la entidad con el id
    void activar(Long id) throws SQLException;
}
