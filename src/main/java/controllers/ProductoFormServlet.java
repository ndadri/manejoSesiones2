package controllers;

// Importaciones estándar de Servlets (Jakarta EE)
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// Importaciones de Modelos y Servicio
import models.Categoria;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceJdbcImplement;

// Importaciones de Java para manejo de I/O, SQL, fechas y Optional
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

/**
 * ProductoFormServlet es un controlador encargado de dos tareas principales:
 * 1. Mostrar el formulario HTML (GET).
 * 2. Procesar y guardar los datos enviados desde ese formulario (POST).
 */
// La anotación WebServlet define la ruta (URL) que mapea a este controlador.
// Esta ruta específica (/productos/form) se usa para crear o editar un producto.
@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    /**
     * Maneja las peticiones GET. Se usa para cargar la página del formulario.
     * Si se recibe un 'id', carga los datos del producto para edición; si no, prepara un formulario vacío para creación.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtengo la conexión activa a la base de datos (generalmente inyectada por un filtro).
        Connection conn = (Connection) req.getAttribute("conn");
        // Inicializo la capa de servicio, que maneja la lógica de negocio y persistencia.
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        // 1. Lógica para determinar si es EDICIÓN o CREACIÓN.
        Long id;
        try {
            // Intento obtener el ID desde los parámetros de la URL (ej: /productos/form?id=5)
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            // Si el ID no existe o no es numérico, asumo que es una CREACIÓN.
            id = 0L;
        }

        // Inicializo un objeto Producto, y su Categoría, para evitar NullPointerExceptions en el JSP.
        Producto producto = new Producto();
        producto.setCategoria(new Categoria());

        // 2. Si el ID es válido (> 0), intento cargar el producto desde la base de datos.
        if (id > 0) {
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {
                // Si existe, cargo el objeto completo para rellenar el formulario (EDICIÓN).
                producto = o.get();
            }
        }

        // 3. Preparo los datos necesarios para la vista (JSP).
        // La lista de categorías para el desplegable <select>.
        req.setAttribute("categorias", service.listarCategorias());
        // El objeto producto a editar (si id > 0) o vacío (si es nuevo).
        req.setAttribute("producto", producto);

        // 4. Redirecciono internamente (FORWARD) al JSP para mostrar el formulario HTML.
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    /**
     * Maneja las peticiones POST. Se usa para procesar los datos enviados por el formulario HTML.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtengo la conexión y el servicio, igual que en doGet.
        Connection conn = (Connection) req.getAttribute("conn");
        ProductoService service = new ProductoServiceJdbcImplement(conn);

        // 1. Recolección y Parseo de Parámetros del Formulario.
        String nombre = req.getParameter("nombre");
        // Los números deben ser parseados, incluyendo manejo básico de errores NumberFormatException.
        Double precio;
        try { precio = Double.valueOf(req.getParameter("precio")); } catch (NumberFormatException e) { precio = 0.0; }
        Integer stock;
        try { stock = Integer.valueOf(req.getParameter("stock")); } catch (NumberFormatException e) { stock = 0; }
        String descripcion = req.getParameter("descripcion");
        Long idCategoria = Long.valueOf(req.getParameter("categoria"));

        // Las fechas se parsean usando LocalDate (compatible con el formato yyyy-MM-dd de HTML Input type="date").
        LocalDate fechaElab = LocalDate.parse(req.getParameter("fecha_elaboracion"));
        LocalDate fechaCad = LocalDate.parse(req.getParameter("fecha_caducidad"));

        // Obtengo el ID oculto para saber si es una EDICIÓN (id > 0) o CREACIÓN (id = 0).
        long id;
        try { id = Long.parseLong(req.getParameter("id")); } catch (NumberFormatException e) { id = 0L; }

        // 2. Creación y Mapeo del Objeto Producto.
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombreProducto(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setDescripcion(descripcion);
        producto.setFechaElaboracion(fechaElab);
        producto.setFechaCaducidad(fechaCad);
        producto.setCondicion(1); // El producto creado o editado siempre se marca como Activo (1).

        // Mapeo de la Categoría seleccionada. Solo necesitamos el ID para guardar.
        Categoria c = new Categoria();
        c.setId(idCategoria);
        producto.setCategoria(c);

        // 3. Guardado en la Base de Datos.
        // El servicio se encarga de llamar al Repositorio para ejecutar el INSERT o UPDATE.
        service.guardar(producto);

        // 4. Redirección.
        // Después de guardar, redirijo al usuario al listado principal de productos para que vea el resultado.
        // Uso sendRedirect para forzar una nueva petición GET y evitar problemas de doble submit.
        resp.sendRedirect(req.getContextPath() + "/productos");
    }
}