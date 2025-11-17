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
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet({"/login", "/login.html"}) //este servlet responde a estas url

public class LoginServlet extends HttpServlet {
    //credenciales validas para acceder
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    /**
     * metodo do get que se ejecuta cuando el usuario entra al login desde el navegador
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //se crea un instancia del servicio que revisa la sesion (logginservicesessionimpl)
        LoginService auth = new LoginServiceSessionImplement();
        //si hay un usuario guardado en sesion devuelve un optional conn su nombre
        //si no, devuelve vacio
        Optional<String> usernameOptional = auth.getUsername(req);

        //si el usuario ya inició sesion respondemos con
        if (usernameOptional.isPresent()) {

            HttpSession session = req.getSession();
            //recuperar el contador de la sesion
            Integer contador = (Integer) session.getAttribute("contador");

            //si no existe, se inicializa en 1
            if (contador == null) {
                contador = 1;
            } else {
                //si ya existe, lo incrementamos
                contador++;
            }
            //guardamos el nuevo valor
            session.setAttribute("contador", contador);

            resp.setContentType("text/html;charset=utf-8");
            try (PrintWriter out = resp.getWriter() ) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=utf-8>");
                //usernameOptional.get saca el nombre dentro del optional
                out.println("<title>Hola "+ usernameOptional.get() +"</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/styles.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Hola "+ usernameOptional.get() +" has iniciado sesión con éxito!</h1>");
                //salida del contador
                out.println("<p>Has iniciado sesión <span class='admin'>" + contador + "</span> veces.</p>");

                //req.getContextPath() devuelve el nombre base de la aplicacion
                //para que los enlaces sean correctos sin importar el contexto
                //el segundo enlace /logout apunta al servlet de cierre se sesion
                out.println("<div class='links'>");
                out.println("<p><a href = '" + req.getContextPath() + "/index.html'>Volver</a></p>");
                out.println("<p><a href = '" + req.getContextPath() + "/logout'>Cerrar Sesión</a></p>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }else {
            //si el optional esta vacio, nadie inicio sesion aun, por lo que redirige al jsp login donde esta el formulario
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    /**
     * metodo dopost que se usa cuando el usuario envia el formulario
     * method=post
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //obtenemos los valores de los campos del formulario html
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //validamos las credenciales
        // comparando los datos ingresados con las credenciales definidas al inicio
        if(USERNAME.equals(username) && PASSWORD.equals(password)) {
            //si son correctos se crea la sesion
            HttpSession session = req.getSession();
            //guarda el atributo username dentro de la sesion
            session.setAttribute("username", username);
            //redirige al login.html lo que vuelve a ejecutar el doget
            //que va a mostrar la pagina de bienvenida
            resp.sendRedirect(req.getContextPath() + "/login.html");
        }else {
            //si son incorrectos envia un 401 (no autorizado)
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no esta autorizado para ingresar a esta pagina");
        }
    }
}
