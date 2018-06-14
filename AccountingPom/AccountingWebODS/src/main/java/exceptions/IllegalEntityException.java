package exceptions;

public class IllegalEntityException extends Exception {

    /**
     * Creates a new instance of <code>IllegalEntityException</code> without
     * detail message.
     */
    public IllegalEntityException() {
    }

    /**
     * Constructs an instance of <code>IllegalEntityException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalEntityException(String msg) {
        super(msg);
    }
}
