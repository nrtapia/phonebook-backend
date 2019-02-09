package com.ntapia.phonebook.dao;

import com.ntapia.phonebook.model.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to access Contact data.
 */
public interface ContactDAO extends JpaRepository<Contact, Long> {

}
