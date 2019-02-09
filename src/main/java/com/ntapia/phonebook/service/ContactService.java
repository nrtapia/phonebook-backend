package com.ntapia.phonebook.service;

import com.ntapia.phonebook.model.Contact;

import java.util.List;

/**
 * Contract to provide Contact Services
 */
public interface ContactService {

    /**
     * Service to create new contact
     *
     * @param contact
     *         contact data
     * @return persisted contact
     */
    Contact save(Contact contact);

    /**
     * Service to search contacts
     *
     * @param term
     *         term to search
     * @return contacts list
     */
    List<Contact> search(String term);
}
