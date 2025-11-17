package services;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface LoginService {
    /**metodo que se debe implementar
     *optional que puede conyener el username en string
     * o estar vacio si no hay sesion
     * getUsername nombre del metodo
     * httpservletrequest se encarga de recibir la peticion hhtp actual
     * que contiene la informacion de la sesion y del usuario
     */
    Optional<String> getUsername(HttpServletRequest request);
}
