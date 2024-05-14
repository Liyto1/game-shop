package com.gameshop.www.eCommerce.exception;

import lombok.Getter;

@Getter
public class UserNotVerifiedException extends Exception {

    private final boolean newEmailSent;

    public UserNotVerifiedException(boolean newEmailSent) {
        this.newEmailSent = newEmailSent;
    }

}
