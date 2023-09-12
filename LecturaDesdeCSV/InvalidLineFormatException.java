package LecturaDesdeCSV;

public class InvalidLineFormatException extends Exception {
	String error;
	public InvalidLineFormatException() {
        this.error="Error 1";
    }
     
     
    public InvalidLineFormatException(String introduceMensaje) {
        this.error=introduceMensaje;   
    }
     
    public String getMessage() {
        return error;
    }
}
