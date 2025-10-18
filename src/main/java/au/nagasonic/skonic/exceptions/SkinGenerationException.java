package au.nagasonic.skonic.exceptions;


/**
 * Exception raised when skin texture recovery or generation fails.
 *
 * @author  Faiizer
 *
 * @since   1.2.5
 */
public class SkinGenerationException extends Exception {

    /**
     * Constructs a new {@link SkinGenerationException} with the specified message.
     *
     * @param message   the message.
     *
     * @since           1.2.5
     */
    public SkinGenerationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@link SkinGenerationException} with the specified formatted message.
     *
     * @param format    the message format (using {@link String#format}).
     * @param args      the arguments for the format.
     *
     * @since           1.2.5
     */
    public SkinGenerationException(String format, Object... args) {
        super(String.format(format, args));
    }

    /**
     * Constructs a new {@link SkinGenerationException} with the specified message and exception.
     *
     * @param message   the message.
     * @param cause     the exception.
     *
     * @since           1.2.5
     */
    public SkinGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@link SkinGenerationException} with the specified formatted message and exception.
     *
     * @param cause     the exception.
     * @param format    the message format (using {@link String#format}).
     * @param args      the arguments for the format.
     *
     * @since           1.2.5
     */
    public SkinGenerationException(Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
    }
}