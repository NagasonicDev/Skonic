package au.nagasonic.skonic.elements.util;

import au.nagasonic.skonic.Skonic;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger for Skonic plugin with debug mode support and formatted messages.
 *
 * @author  Faiizer
 *
 * @since   1.2.5
 */
@SuppressWarnings("unused")
public class SkonicLogger {

    /** The main plugin instance. */
    private final Skonic plugin;

    /** The Bukkit logger instance. */
    private final Logger logger;

    public SkonicLogger(Skonic plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }


    // INFO
    /**
     * Logs an informational message.
     *
     * @param message   the message to log
     *
     * @since           1.2.5
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Logs an informational message with an exception.
     *
     * @param message   the message to log
     * @param cause the exception to log with its stack trace
     *
     * @since           1.2.5
     */
    public void info(String message, Throwable cause) {
        logger.log(Level.INFO, message, cause);
    }

    /**
     * Logs a formatted informational message with arguments.
     *
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void info(String format, Object... args) {
        logger.info(String.format(format, args));
    }

    /**
     * Logs a formatted informational message with arguments and an exception.
     *
     * @param cause the exception to log with its stack trace
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void info(Throwable cause, String format, Object... args) {
        logger.log(Level.INFO, String.format(format, args), cause);
    }


    // WARNING
    /**
     * Logs a warning message.
     *
     * @param message   the message to log
     *
     * @since           1.2.5
     */
    public void warn(String message) {
        logger.warning(message);
    }

    /**
     * Logs a warning message with an exception.
     *
     * @param message   the message to log
     * @param cause the exception to log with its stack trace
     *
     * @since           1.2.5
     */
    public void warn(String message, Throwable cause) {
        logger.log(Level.WARNING, message, cause);
    }

    /**
     * Logs a formatted warning message with arguments.
     *
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void warn(String format, Object... args) {
        logger.warning(String.format(format, args));
    }

    /**
     * Logs a formatted warning message with arguments and an exception.
     *
     * @param cause the exception to log with its stack trace
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void warn(Throwable cause, String format, Object... args) {
        logger.log(Level.WARNING, String.format(format, args), cause);
    }


    // SEVERE
    /**
     * Logs a severe error message.
     *
     * @param message   the message to log
     *
     * @since           1.2.5
     */
    public void severe(String message) {
        logger.severe(message);
    }

    /**
     * Logs a severe error message with an exception.
     *
     * @param message   the message to log
     * @param cause the exception to log with its stack trace
     *
     * @since           1.2.5
     */
    public void severe(String message, Throwable cause) {
        logger.log(Level.SEVERE, message, cause);
    }

    /**
     * Logs a formatted severe error message with arguments.
     *
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void severe(String format, Object... args) {
        logger.warning(String.format(format, args));
    }

    /**
     * Logs a formatted severe error message with arguments and an exception.
     *
     * @param cause the exception to log with its stack trace
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void severe(Throwable cause, String format, Object... args) {
        logger.log(Level.SEVERE, String.format(format, args), cause);
    }


    // DEBUG
    /**
     * Logs a debug message (only if debug mode is enabled in config).
     *
     * @param message   the message to log
     *
     * @since           1.2.5
     */
    public void debug(String message) {
        if (plugin.getPluginConfig().isDebugEnabled()) {
            logger.log(Level.INFO, "[DEBUG] " + message);
        }
    }

    /**
     * Logs a debug message with an exception (only if debug mode is enabled in config).
     *
     * @param message   the message to log
     * @param cause the exception to log with its stack trace
     *
     * @since           1.2.5
     */
    public void debug(String message, Throwable cause) {
        if (plugin.getPluginConfig().isDebugEnabled()) {
            logger.log(Level.INFO, "[DEBUG] " + message, cause);
        }
    }

    /**
     * Logs a debug message with arguments (only if debug mode is enabled in config).
     *
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void debug(String format, Object... args) {
        if (plugin.getPluginConfig().isDebugEnabled()) {
            logger.log(Level.INFO, "[DEBUG] " + String.format(format, args));
        }
    }

    /**
     * Logs a debug message with arguments and an exception (only if debug mode is enabled in config).
     *
     * @param cause the exception to log with its stack trace
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void debug(Throwable cause, String format, Object... args) {
        logger.log(Level.SEVERE, String.format(format, args), cause);
        if (plugin.getPluginConfig().isDebugEnabled()) {
            logger.log(Level.INFO, "[DEBUG] " + String.format(format, args), cause);
        }
    }


    // MANUAL LOGS
    /**
     * Logs a message at a specified logging level.
     *
     * @param level     the severity level
     * @param message   the message to log
     *
     * @since           1.2.5
     */
    public void log(Level level, String message) {
        logger.log(level, message);
    }

    /**
     * Logs a message with an exception at a specified logging level.
     *
     * @param level     the severity level
     * @param message   the message to log
     * @param cause the exception to log
     *
     * @since           1.2.5
     */
    public void log(Level level, String message, Throwable cause) {
        logger.log(level, message, cause);
    }

    /**
     * Logs a formatted message with arguments.
     *
     * @param level     the severity level
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void log(Level level, String format, Object... args) {
        logger.log(level, String.format(format, args));
    }

    /**
     * Logs a formatted message with arguments and an exception.
     *
     * @param level     the severity level
     * @param cause the exception to log with its stack trace
     * @param format    the message format (using {@link String#format})
     * @param args      the arguments for the format
     *
     * @since           1.2.5
     */
    public void log(Level level, Throwable cause, String format, Object... args) {
        logger.log(level, String.format(format, args), cause);
    }

}
