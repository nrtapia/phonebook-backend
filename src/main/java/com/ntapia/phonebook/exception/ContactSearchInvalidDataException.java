package com.ntapia.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Contact search term
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Term to search is required!")
public class ContactSearchInvalidDataException extends RuntimeException {

    public ContactSearchInvalidDataException() {
        super();
    }
}
