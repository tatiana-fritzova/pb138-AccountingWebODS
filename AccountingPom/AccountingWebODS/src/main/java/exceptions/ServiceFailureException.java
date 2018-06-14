package exceptions;

public class ServiceFailureException extends Exception {

    /**
     * Creates a new instance of <code>ServiceFailureException</code> without
     * detail message.
     */
    public ServiceFailureException() {
    }

    /**
     * Constructs an instance of <code>ServiceFailureException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceFailureException(String msg) {
        super(msg);
    }
}
