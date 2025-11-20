package repositorio;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    //Implemento un método para listar los productos
    List<T> listar() throws SQLException;
    //implementamos un método para buscar un producto por id
    T porId(Long id) throws SQLException;
    //implementamos un método para guardar la información en la BBDD
    void guardar(T t) throws SQLException;
    //implementamos un método para eliminar
    void eliminar(Long id)throws SQLException;
    //implementar un método para desactivar el producto
    //implementar un método para activar el producto
}
