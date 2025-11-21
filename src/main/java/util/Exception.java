package util;

public class Exception extends RuntimeException {
    /**Implementamos un constructor donde recibe como parametro
     * de mensaje luego llamamos a la clase constructor de la clase padre para
     * que lance la excepcion
     */
    public Exception(String message) {
        super(message);
    }
    /*
    * Implementamos un constructor donde recibe dos parametros
    * como el mensaje y la causa de la excepcion
    * Luego se invoca al constructor de la clase padre donde vuelve
    * la causa de forma tecnica
    * */
    public Exception(String message, Throwable cause) {
        super(message, cause);
    }

}
