package com.ntapia.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Contact already exist
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Contact already register!")
public class ContactAlreadyExistException extends RuntimeException {

    public ContactAlreadyExistException() {
        super();
    }
}
