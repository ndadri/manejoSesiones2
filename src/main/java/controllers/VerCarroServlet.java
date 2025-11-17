package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ver-carro")
public class VerCarroServlet extends HttpServlet {
    /**
     * Metodo que se ejecuta cuando el usuario realiza una petición GET a /ver-carro.
     * reenvia la solicitud hacia la vista "carro.jsp",
     * donde se mostrará el contenido del carrito almacenado en sesión.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/carro.jsp").forward(req, resp);
    }
}
