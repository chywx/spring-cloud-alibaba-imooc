package cn.chendahai.center.user.security;

public class SecurityException extends RuntimeException{

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
