package controllers;
/** * autor: Génesis
 * * fecha: 11/11/2025
 * * descripción: servlet encargado de mostrar un listado de productos
 * en formato HTML. * Si el usuario ha iniciado sesión,
 * se muestra un mensaje de bienvenida * y también se revelan datos adicionales
 * como el precio del producto. */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/** * @WebServlet define las rutas o urls con las que este servlet responderá.
 * * En este caso: "/productos.html" y "/productos". */

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    /** * Metodo doGet: se ejecuta cuando el cliente realiza una petición GET */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Connection conn = (Connection) req.getAttribute("conn");

        ProductoService service = new ProductoServiceJdbcImplement(conn);
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceSessionImplement();
        Optional<String> usernameOptional = auth.getUsername(req);

        /*resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Listado de Productos</title>");


            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css' rel='stylesheet'>");


            out.println("<style>");
            out.println("body { background: linear-gradient(135deg, #ffb3c6, #d8b4fe, #a3c9f9); min-height: 100vh; }");
            out.println("h1 { color: #ff69b4 !important; }");
            out.println("h5 { color: #c9184a !important; }");

            out.println(".card {");
            out.println("  background: #fff;");
            out.println("  border-radius: 16px;");
            out.println("  border: 2px solid #ffb3c6;");
            out.println("  box-shadow: 0 6px 14px rgba(255,105,180,0.25);");
            out.println("}");

            out.println(".table-success th {");
            out.println("  background-color: #ffb3c6 !important;");
            out.println("  color: #fff !important;");
            out.println("}");

            out.println(".btn-outline-primary {");
            out.println("  border-color: #ff69b4;");
            out.println("  color: #ff69b4;");
            out.println("}");
            out.println(".btn-outline-primary:hover {");
            out.println("  background-color: #ff69b4;");
            out.println("  color: white;");
            out.println("}");
            out.println("</style>");

            out.println("</head>");
            out.println("<body class='bg-light'>");

            out.println("<div class='container mt-5'>");
            out.println("<div class='text-center mb-4'>");
            out.println("<h1 class='display-5 fw-bold'>Listado de Productos</h1>");

            if (usernameOptional.isPresent()) {
                out.println("<h5 class='mt-3'>Hola, <strong>" + usernameOptional.get() + "</strong> ¡Bienvenido!</h5>");
            }
            out.println("</div>");

            out.println("<div class='card shadow-lg border-0'>");
            out.println("<div class='card-body'>");
            out.println("<div class='table-responsive'>");

            out.println("<table class='table table-striped table-hover align-middle'>");
            out.println("<thead class='table-success'>");
            out.println("<tr>");
            out.println("<th scope='col'>ID</th>");
            out.println("<th scope='col'>Nombre</th>");
            out.println("<th scope='col'>Stock</th>");
            out.println("<th scope='col'>Fecha produccion</th>");

            if (usernameOptional.isPresent()) {
                out.println("<th scope='col'>Precio</th>");
                out.println("<th scope='col'>Opciones</th>");
            }

            out.println("</tr>");

            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getNombreProducto() + "</td>");
                out.println("<td>" + p.getCantidad() + "</td>");
                out.println("<td>" + p.getFechaElaboracion() + "</td>");

                if (usernameOptional.isPresent()) {
                    out.println("<td>$" + p.getPrecio() + "</td>");
                    out.println("<td>");
                    out.println("<a href='" + req.getContextPath() + "/agregar-carro?id=" + p.getId() + "' class='btn btn-sm btn-outline-primary'>");
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
        }*/
        //seteamos los atributos de productos y username para pasar al jsp
        req.setAttribute("productos", productos);
        req.setAttribute("username", usernameOptional);
        //pasamos el servlet
        getServletContext().getRequestDispatcher("/producto.jsp").forward(req, resp);
    }
}


