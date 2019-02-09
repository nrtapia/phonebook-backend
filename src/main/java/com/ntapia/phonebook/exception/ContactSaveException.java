package com.ntapia.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Represent a problem to persist the Contact data
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ContactSaveException extends RuntimeException{

    public ContactSaveException() {
        super();
    }
}
