package services;

import jakarta.servlet.http.HttpServletRequest;//clases de las API de los servlets
import jakarta.servlet.http.HttpSession;

import java.util.Optional;//Optional se usa para expresar la posible ausencia de valor de forma segura (evita null directo)

public class LoginServiceSessionImplement implements LoginService {
    @Override
    //Implementación del metodo de la interfaz.
    // Recibe la petición HTTP para extraer la sesión/atributos
    public Optional<String> getUsername(HttpServletRequest request) {
        //request.getSession() devuelve la sesión actual o crea una nueva si no existe
        HttpSession session = request.getSession();
        //recupera el atribbuto llamado username almacenado en la sesion y lo castea a String
        String username = (String) session.getAttribute("username");

        if (username != null) {// si el username tiene un valor
            return Optional.of(username); //retornamos un Optional que lo contiene (con un valor dentro)
        }
        return Optional.empty();//Si no había nada, devuelve un Optional vacío (sin null)
    }
}
