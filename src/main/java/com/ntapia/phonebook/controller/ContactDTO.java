package com.ntapia.phonebook.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for Contact Data
 */
@Builder
@Getter
@AllArgsConstructor
final class ContactDTO implements Serializable {

    private static final long serialVersionUID = 20190209L;

    private final Long id;

    private final String firstName;

    private final String lastName;

    private final String phone;
}
