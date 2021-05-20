package ru.toroschin.spring.market.error_handling;

public class UserEnableException extends RuntimeException{
    public UserEnableException(String message) {
        super(message);
    }
}
