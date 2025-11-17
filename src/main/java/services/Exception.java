package services;


/*
* Esta clases sirve para obtener la causa y el error del mensaje
* Muestra un mensaje si la BDD esta conectada o no
* */
public class Exception extends RuntimeException {
    public Exception(String mensaje){
        super(mensaje);
    }
    public Exception(String mensaje, Throwable cause){
        super(cause);
    }

}
