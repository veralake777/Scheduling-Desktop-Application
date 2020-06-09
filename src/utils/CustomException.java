package utils;

public class CustomException extends Exception {
    /*
    The serialVersionUID is a universal version identifier for a Serializable class. Deserialization uses this number
    to ensure that a loaded class corresponds exactly to a serialized object.
    If no match is found, then an InvalidClassException is thrown.
     */
    private static final long serialVersionUID = 1L;

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
