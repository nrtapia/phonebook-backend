package com.ntapia.phonebook.service;

import com.ntapia.phonebook.dao.ContactDAO;
import com.ntapia.phonebook.model.Contact;
import com.ntapia.phonebook.service.impl.ContactServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Contact Service integration test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
public class ContactServiceIT {

    @Autowired
    private ContactDAO contactDAO;

    private ContactServiceImpl contactService;

    @Before
    public void init() {
        contactService = new ContactServiceImpl(contactDAO);
    }

    @Test
    public void testFindTop100ByTextDataContainingOrderByFirstName() {
        IntStream.range(0, 50)
                .mapToObj(i ->  Contact.builder().firstName("First " + i).lastName("Last " + i).phone(String.valueOf(i)).build())
                .forEach(contactService::save);

        List<Contact> result = contactService.search("First");
        assertFalse("Empty result", result.isEmpty());
        assertEquals("Result size unexpected", 50, result.size());

        result = contactService.search("10");
        assertFalse("Empty result", result.isEmpty());
        assertEquals("Result size unexpected", 1, result.size());

        result = contactService.search("Last 30");
        assertFalse("Empty result", result.isEmpty());
        assertEquals("Result size unexpected", 1, result.size());

        result = contactService.search("1");
        assertFalse("Empty result", result.isEmpty());
        assertEquals("Result size unexpected", 14, result.size());
    }
}
