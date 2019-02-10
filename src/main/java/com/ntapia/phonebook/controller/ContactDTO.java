package com.ntapia.phonebook.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for Contact Data
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
class ContactDTO implements Serializable {

    private static final long serialVersionUID = 20190209L;

    private Long id;

    private String firstName;

    private String lastName;

    private String phone;
}
