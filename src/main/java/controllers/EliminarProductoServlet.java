package controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceJdbcImplement;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

/*
 * fecha: 19/11/2025
 * descripcion: Controlador para eliminar un producto.
 * Recibe un ID y llama al servicio para realizar la baja (cambiar estado a inactivo).
 */
@WebServlet("/eliminar")
public class EliminarProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Obtener conexión y preparar el servicio
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        // 2. Obtener y validar el ID desde la URL
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        // 3. Si el ID es válido, ejecutamos la eliminación
        if (id > 0) {
            // Verificamos que el producto exista antes de intentar borrarlo
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                service.eliminar(id); // Esto ejecuta el UPDATE condicion = 0
            }
        }

        // 4. Redirigimos de vuelta a la lista para ver los cambios
        resp.sendRedirect(req.getContextPath() + "/productos");
    }
}
