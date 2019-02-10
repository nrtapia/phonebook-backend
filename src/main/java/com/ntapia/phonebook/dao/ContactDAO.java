package com.ntapia.phonebook.dao;

import com.ntapia.phonebook.model.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to access Contact data.
 */
public interface ContactDAO extends JpaRepository<Contact, Long> {

    Optional<Contact> findByFirstNameAndLastName(String firstName, String lastName);

    List<Contact> findTop100ByTextDataContainingOrderByFirstName(String term);
}
