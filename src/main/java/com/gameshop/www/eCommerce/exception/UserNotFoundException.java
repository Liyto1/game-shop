package com.gameshop.www.eCommerce.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
