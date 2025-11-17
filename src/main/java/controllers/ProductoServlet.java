package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.LoginService;
import services.LoginServiceSessionImplement;
import services.ProductoService;
import services.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/** * @WebServlet define las rutas o urls con las que este servlet responderá.
 * * En este caso: "/productos.html" y "/productos". */

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    /** * Metodo doGet: se ejecuta cuando el cliente realiza una petición GET (por ejemplo,
     * * al ingresar la URL en el navegador o hacer clic en un enlace). */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se crea una instancia del servicio de productos
        // que maneja la obtención de la lista de productos disponibles.
        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        // Se crea el servicio de autenticación de usuarios
        // para verificar si existe una sesión activa.
        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Listado de Productos</title>");
            out.println("</head>");
            out.println("<body class='bg-light'>");

            out.println("<div class='container mt-5'>");
            out.println("<div class='text-center mb-4'>");
            out.println("<h1 class='display-5 fw-bold text-primary'>Listado de Productos</h1>");
            // Si hay un usuario autenticado, se muestra su nombre y un saludo
            if (usernameOptional.isPresent()) {
                out.println("<h5 class='text-success mt-3'>Hola, <strong>" + usernameOptional.get() + "</strong> ¡Bienvenido!</h5>");
            }
            out.println("</div>");

            // Tabla con estilos Bootstrap
            out.println("<div class='card shadow-lg border-0'>");
            out.println("<div class='card-body'>");
            out.println("<div class='table-responsive'>");
            out.println("<table class='table table-striped table-hover align-middle'>");
            out.println("<thead class='table-success'>");
            out.println("<tr>");
            out.println("<th scope='col'>ID</th>");
            out.println("<th scope='col'>Nombre</th>");
            out.println("<th scope='col'>Tipo</th>");
            // Si el usuario está logueado, se muestra también la columna de precios y la de accion
            if (usernameOptional.isPresent()) {
                out.println("<th scope='col'>Precio</th>");
                out.println("<th scope='col'>Opciones</th>");
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // Recorremos la lista de productos obtenidos del servicio
            // y generamos una fila HTML por cada producto
            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                // Solo los usuarios con sesión activa pueden ver los precios y la accion agregar al carrito
                if (usernameOptional.isPresent()) {
                    out.println("<td>$" + p.getPrecio() + "</td>");
                    out.println("<td>");

                    //el error venía del signo de pregunta extra dentro del href
                    out.println("<a href='" + req.getContextPath() + "/agregar-carro?id=" + p.getIdProducto() + "' class='btn btn-sm btn-outline-primary'>");
                    out.println("<i class='bi bi-cart-plus'></i> Agregar al carro");
                    out.println("</a>");
                    out.println("</td>");
                }
                out.println("</tr>");
            });

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}

