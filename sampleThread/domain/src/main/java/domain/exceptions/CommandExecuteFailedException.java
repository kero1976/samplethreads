package domain.exceptions;

public final class CommandExecuteFailedException extends RuntimeException {

    public CommandExecuteFailedException(String message) {
        super(message);
    }

    public CommandExecuteFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}