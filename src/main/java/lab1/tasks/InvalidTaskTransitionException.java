package lab1.tasks;

public class InvalidTaskTransitionException extends RuntimeException {
    public InvalidTaskTransitionException() {
        super();
    }

    public InvalidTaskTransitionException(String message) {
        super(message);
    }

    public InvalidTaskTransitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTaskTransitionException(Throwable cause) {
        super(cause);
    }
}
