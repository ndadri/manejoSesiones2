package filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

//Implementamos una anotacion.Esta anotacion
//Me sirve para poder utilizar la conexion en cualquier parte
//De mi aplicacion
@WebServlet("/*")
public class ConexionFilter implements Filter {
    /**
     * Una clase filter en java es un objeto que realiza tareas
     * de filtradoenlas solucitudes cliente servidor
     * respuesta a un recurso: los filtros se pueden ejecutar
     * en servidores compatibles con Jakarte EE
     * Los filtros interceptan solicitudes y respuestas de manera
     * dinamica para transformar o utilizar la informacion
     * que contienen. El filtrado se realiza mediante el metodo doFilter
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        /*
        * request: peticion que hace el cliente
        * response_ respuesta del servidor
        * filterchain- Es una clase de filtro que representa el flujo
        * de procesamiento, este metddo llama al metodo chain.doGilter(request, response)
        * dentro de un filtro pasa la solicitud, el siguiente paso la clase
        * filtra o te devuelve el recurso destino que puede ser un servlet
        * o jsp
        * */
        //Obtenemos la conexion
        try(Connection conn = util.ConexionBdd.getConnection()){
            //Verificamos que la conexion realizada o se cambia a utocommit
            //Configuracion automatica a la base de datos y cada instruccion
            //Sql
            if (conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }try{
                //Agregamos la conexion como un atributo wn la solucitud
                //Esto nos permite que otros componentes como servlet o Daos
                servletRequest.setAttribute("conn", conn);
                //Pasa la solicitud y la respuesta al siguiente filrto o recurso destino
                //Pasa la solicitud y la respuesra al siguiente filtro o al recurso destino
                filterChain.doFilter(servletRequest, servletResponse);
                /*
                *Si el procesamiento se realizao correctamente sin lanzar excepciones se confirma
                * la solicitud y se aplica todo los cambios a la base de datos
                */
                conn.commit();
                /*
                * Si ocurre algun error durante el procesamineto (dentro del dofilter), se
                * captura la excepcion
                * */
            }catch (SQLException e){

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
