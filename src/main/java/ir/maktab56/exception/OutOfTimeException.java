package ir.maktab56.exception;

import java.io.Serial;

public class OutOfTimeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7718828512143226455L;

    private final String code;

    public OutOfTimeException(String code) {
        super();
        this.code = code;
    }

    public OutOfTimeException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public OutOfTimeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public OutOfTimeException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
