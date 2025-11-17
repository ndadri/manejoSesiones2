package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.DetalleCarro;
import models.ItemCarro;
import models.Producto;
import services.ProductoService;
import services.ProductoServiceImplement;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //obtenemos el id del producto
        Long id = Long.parseLong(req.getParameter("id"));

        //busca el producto por id
        ProductoService service = new ProductoServiceImplement();
        Optional<Producto> producto = service.porId(id);

        //si el producto existe crea un nuevo item carro con cantidad  1
        if (producto.isPresent()) {
            ItemCarro item = new ItemCarro( 1, producto.get());

            /** obtiene o crea el carrito en la sesion del usuario:
             si el usuario aun no tiene carrito se crea uno nuevo
             y se guarda en la sesion, si ya tiene uno se reutiliza*/
            HttpSession session = req.getSession();
            DetalleCarro detalleCarro;
            if (session.getAttribute("carro") == null) {
                detalleCarro= new DetalleCarro();
                session.setAttribute("carro", detalleCarro);
            }else{
                detalleCarro = (DetalleCarro) session.getAttribute("carro");
            }
            //Agrega el producto al carrito
            detalleCarro.addItemCarro(item);
        }
        //Redirige a la vista del carrito
        resp.sendRedirect(req.getContextPath() + "/ver-carro");
    }
}
