package repositorio;

import models.Categoria;
import models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJdbcImplement implements Repository<Producto>{
    //obtener la conexion a la bdd
    private Connection conn;

    //obtengo mi conexion mediante el constructor
    public ProductoRepositoryJdbcImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("select p.* , c.nombreCategoria as categoria FROM producto as p " +
                     " INNER JOIN categoria as c ON (p.idCategoria=c.id) order by p.id ASC")) {
            while(rs.next()) {
                Producto p = getProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }
    //implementamos un metodo para buscar un registro por un ID

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try(PreparedStatement stmt = conn.prepareStatement("select p.*, c.nombreCategoria as categoria from producto as p " +
                " inner join categoria as c ON (p.idCategoria = c.id)where p.id=?")) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    producto = getProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;

        // Definimos la SQL dependiendo de si es Editar o Crear
        if (producto.getId() != null && producto.getId() > 0) {
            // UPDATE: Nótese que arreglé la falta de coma y el orden
            sql = "UPDATE producto SET nombreProducto=?, idCategoria=?, cantidad=?, precio=?, descripcion=?, fecha_elaboracion=?, fecha_caducidad=? WHERE id=?";
        } else {
            // INSERT: Quitamos 'codigo' porque no viene del formulario
            sql = "INSERT INTO producto (nombreProducto, idCategoria, cantidad, precio, descripcion, fecha_elaboracion, fecha_caducidad, condicion) " +
                    " VALUES (?,?,?,?,?,?,?,1)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Usamos una variable indice para no perdernos con los números
            int i = 1;

            // Seteamos los valores comunes en el MISMO ORDEN que aparecen en la SQL de arriba
            stmt.setString(i++, producto.getNombreProducto());  // 1
            stmt.setLong(i++, producto.getCategoria().getId()); // 2
            stmt.setInt(i++, producto.getStock());           // 3
            stmt.setDouble(i++, producto.getPrecio());          // 4
            stmt.setString(i++, producto.getDescripcion());     // 5
            stmt.setDate(i++, Date.valueOf(producto.getFechaElaboracion())); // 6
            stmt.setDate(i++, Date.valueOf(producto.getFechaCaducidad()));   // 7

            // Si es UPDATE, necesitamos setear el ID al final (para el WHERE id=?)
            if (producto.getId() != null && producto.getId() > 0) {
                stmt.setLong(i++, producto.getId()); // 8
            }

            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        /*String sql= "delete from producto where id=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }*/
        //metodo desactivar
        String sql ="UPDATE producto SET condicion =0 WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    @Override
    public void activar(Long id) throws SQLException {
        String sql = "UPDATE producto SET condicion = 1 WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    private static Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombreProducto(rs.getString("nombreProducto"));
        p.setStock(rs.getInt("cantidad"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setFechaElaboracion(rs.getDate("fecha_elaboracion").toLocalDate());
        p.setFechaCaducidad(rs.getDate("fecha_caducidad").toLocalDate());
        p.setCondicion(rs.getInt("condicion"));
        //creamos un nuevo objeto de tipo categoria
        Categoria c = new Categoria();
        c.setId(rs.getLong("idCategoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
