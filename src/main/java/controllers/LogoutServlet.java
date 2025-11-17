package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.LoginService;
import services.LoginServiceSessionImplement;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    /**
     * Metodo que se ejecuta cuando el usuario accede mediante una peticion get
     * ejemplo cuando se da clic en logout
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //se crea el servicio de autenticacion
        //se instancia la clase que implementa la interfaz loginservice encargada
        //de obtener el nombre del usuario que este actualmente en sesion
        LoginService auth = new LoginServiceSessionImplement();

        //comprobar si hay un usuario logeado
        //se llama al metodo getUsername que busca el atributo username dentro de la sesion
        //si existe optional tiene un valor
        //si no estara vacio
        Optional<String> username = auth.getUsername(req);

        //Si hay usuario se invalida sesion
        if (username.isPresent()) {
            //getsession obtiene la sesion actual
            HttpSession session = req.getSession();
            //luego invalidate elimina todos los datos almacenados, destruye la sesion
            session.invalidate();
        }
        //luego de eliminarla el servlet redirige al usuario al login
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
