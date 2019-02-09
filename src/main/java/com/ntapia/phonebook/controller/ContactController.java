package com.ntapia.phonebook.controller;

import com.ntapia.phonebook.exception.ContactInvalidDataException;
import com.ntapia.phonebook.model.Contact;
import com.ntapia.phonebook.service.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to expose Contact REST endpoints
 *
 * GET     /contacts?term=toSearch
 * POST    /contacts
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public Contact post(@RequestBody ContactDTO contactDTO) {
        if(contactDTO == null){
            throw new ContactInvalidDataException();
        }

        return contactService.save(
                Contact.builder()
                        .firstName(contactDTO.getFirstName())
                        .lastName(contactDTO.getLastName())
                        .build()
                );
    }

    @GetMapping
    public List<ContactDTO> get(@PathVariable String term) {
        return contactService.search(term).stream()
                .map(contact -> ContactDTO.builder()
                        .id(contact.getId())
                        .firstName(contact.getFirstName())
                        .lastName(contact.getLastName())
                        .build())
                .collect(Collectors.toList());
    }
}
