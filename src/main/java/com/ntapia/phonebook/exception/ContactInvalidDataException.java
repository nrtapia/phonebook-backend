package com.ntapia.phonebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Invalid Contact data
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "First name and phone are required!")
public class ContactInvalidDataException extends RuntimeException {

    public ContactInvalidDataException() {
        super();
    }
}
