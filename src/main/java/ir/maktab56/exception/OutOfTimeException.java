package ir.maktab56.exception;

import ir.maktab56.exception.enumeration.ErrorCode;

import java.io.Serial;

public class OutOfTimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7718828512143226455L;

    private final ErrorCode code;

    public OutOfTimeException(ErrorCode code) {
        super();
        this.code = code;
    }

    public OutOfTimeException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public OutOfTimeException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public OutOfTimeException(Throwable cause, ErrorCode code) {
        super(cause);
        this.code = code;
    }

    public ErrorCode getCode() {
        return this.code;
    }
}
