package com.gameshop.www.eCommerce.exception;

public class AddressNotFoundException extends  RuntimeException{
    public AddressNotFoundException(String message){
        super(message);
    }
}
