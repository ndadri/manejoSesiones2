package services;

public class ServiceJbdcException extends RuntimeException{
    /*
     * implementamos un constructor que invoca al constructor padres
     * y recibe un parámetro de tipo String llamando mensaje
     *
     */
    public ServiceJbdcException(String message){
        //Llamamos al contructor padre para mostrar el mensaje
        super(message);
    }
    /*
     * implementamos un constructor que recibe dos parámetros
     * de tipo String message y tambien la causa del error
     *
     */
    public ServiceJbdcException(String message, Throwable cause){
        super(message, cause);
    }
}
