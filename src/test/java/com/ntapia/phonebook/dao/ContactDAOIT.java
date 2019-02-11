package com.ntapia.phonebook.dao;

import com.ntapia.phonebook.model.Contact;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Data integration test
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application.properties")
public class ContactDAOIT {

    @Autowired
    private ContactDAO contactDAO;

    private Contact mockContact1;
    private Contact mockContact2;


    @Before
    public void init() {
        mockContact1 = Contact.builder().firstName("Tony").lastName("Stark").phone("1234436").build();
        mockContact2 = Contact.builder().firstName("Steve").lastName("Rogers").phone("98757").build();
    }

    @Test
    public void testSave() {
        Contact contact = contactDAO.save(mockContact1);
        assertNotNull("Save operation fail", contact);
        assertNotNull("Id not found", contact.getId());

        compareData(mockContact1, contact);
    }

    private void compareData(Contact mock, Contact contactData) {
        assertEquals(mock.getFirstName(), contactData.getFirstName());
        assertEquals(mock.getLastName(), contactData.getLastName());
        assertEquals(mock.getPhone(), contactData.getPhone());
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        contactDAO.save(mockContact1);
        contactDAO.save(mockContact2);

        Optional<Contact> contact = contactDAO.findByFirstNameAndLastName(mockContact1.getFirstName(),
                mockContact1.getLastName());
        assertTrue("Contact not found", contact.isPresent());
        compareData(mockContact1, contact.get());

        contact = contactDAO.findByFirstNameAndLastName(mockContact2.getFirstName(), mockContact2.getLastName());
        assertTrue("Contact not found", contact.isPresent());
        compareData(mockContact2, contact.get());

        contact = contactDAO.findByFirstNameAndLastName(mockContact1.getFirstName(), mockContact2.getLastName());
        assertFalse("Contact found", contact.isPresent());

        contact = contactDAO.findByFirstNameAndLastName("FirstName", "LastName");
        assertFalse("Contact found", contact.isPresent());
    }
}
