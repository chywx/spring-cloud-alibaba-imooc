package cn.chendahai.center.content.security;

public class SecurityException extends RuntimeException{

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
