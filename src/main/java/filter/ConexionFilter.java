package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import services.Exception;
import util.ConexionBdd;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/* Implementamos una anotacion. esta anotacion
me sirve para poder utilizar la conexion en cualquier parte de mi
aplicacion*/
@WebFilter("/*")
public class ConexionFilter implements Filter {
        /*
        * una clase filter en java es un objeto que realiza tareas de filtrado en
        * las solicitudes cliente servidor
        * va a tener una respuesta a un recurso: los filtros se pueden ejecutar
        * en servidores compatibles con JakartaEE
        * los filtros interceptan solicitudes y respuestas de manera dinamica
        * para trasnformar o utilizar la informacion que contienen. el filtrado
        * se realiza mediante el metodo "doFilter"
        * */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    /*
    * request: peticion que hace el cliente
    * response: respuesta del servidor
    * filterChain: es una clase de filtro que representa el flujo de procesamiento,
    * este metodo llama al metodo "chain.doFilter(request, )", dentro de un filtro
    * pasa la solicitud, el siguiente poso la clase filtra o te devuelve el recurso
    * destino que puede ser un servlet o un JSP
    * */

        //obtenemos la conexion
        try(Connection conn = ConexionBdd.getConnection()){
            //verificamos que la conexion realizada o se cambie a autocommit
            //(configuracion automatica a la base de datos y cada instruccion SQL)
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try{
                //agregamos la conexion como atributo en la solicitud
                //esto nos permite que otros componentes como servlets o DAOS
                //puedan acceder a la conexion
                request.setAttribute("conn", conn);
                /*
                * pasa la solicitud y la respuesta al siguiente filtro o al
                * recurso destino
                * */
                filterChain.doFilter(request, response);
                conn.commit();
            } catch (SQLException | Exception e){
                conn.rollback();
                //enviamos cun codigo de error HTTP 500 al cliente indicando
                //un problema interno al servidor
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
            //thrpwable es el control de problemas
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
