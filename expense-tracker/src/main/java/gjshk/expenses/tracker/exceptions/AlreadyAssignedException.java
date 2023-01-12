package gjshk.expenses.tracker.exceptions;

public class AlreadyAssignedException extends RuntimeException {
    public AlreadyAssignedException(String message) {
        super(message);
    }

    public AlreadyAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
