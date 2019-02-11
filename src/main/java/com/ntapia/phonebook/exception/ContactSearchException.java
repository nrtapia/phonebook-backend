package com.ntapia.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represent a problem to search the Contacts data
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error to search contacts data!")
public class ContactSearchException extends RuntimeException{

    public ContactSearchException() {
        super();
    }
}
