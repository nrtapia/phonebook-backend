package com.ntapia.phonebook.service;

import com.ntapia.phonebook.dao.ContactDAO;
import com.ntapia.phonebook.exception.ContactAlreadyExistException;
import com.ntapia.phonebook.exception.ContactInvalidDataException;
import com.ntapia.phonebook.model.Contact;
import com.ntapia.phonebook.service.impl.ContactServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit Test Case for ContactService Implementation
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @Mock
    private ContactDAO contactDAO;

    @InjectMocks
    private ContactServiceImpl contactService;

    private Contact mockContact;

    @Before
    public void init() {
        mockContact = Contact.builder().firstName("Tony").lastName("Stark").phone("1234436").build();
    }

    private void verifyNeverCallFindByAndSave() {
        verify(contactDAO, never()).findByFirstNameAndLastName(anyString(), anyString());
        verify(contactDAO, never()).save(any(Contact.class));
    }

    @Test(expected = ContactInvalidDataException.class)
    public void testContactInvalidDataNull() {
        contactService.save(null);
        verifyNeverCallFindByAndSave();
    }

    @Test(expected = ContactInvalidDataException.class)
    public void testContactInvalidDataFirstNameNull() {
        mockContact.setFirstName(null);
        contactService.save(mockContact);
        verifyNeverCallFindByAndSave();
    }

    @Test(expected = ContactInvalidDataException.class)
    public void testContactInvalidDataLastNameNull() {
        mockContact.setFirstName(null);
        contactService.save(mockContact);
        verifyNeverCallFindByAndSave();
    }

    @Test(expected = ContactInvalidDataException.class)
    public void testContactInvalidDataPhoneNull() {
        mockContact.setPhone("  ");
        contactService.save(mockContact);
        verifyNeverCallFindByAndSave();
    }

    @Test(expected = ContactAlreadyExistException.class)
    public void testContactAlreadyExist() {
        final String firstName = mockContact.getFirstName();
        final String lastName = mockContact.getLastName();
        Optional<Contact> contact = Optional.of(
                Contact.builder().id(1L).firstName(firstName).lastName(lastName).build());

        when(contactDAO.findByFirstNameAndLastName(firstName, lastName)).thenReturn(contact);

        contactService.save(mockContact);
        verify(contactDAO, times(1)).findByFirstNameAndLastName(firstName, lastName);
        verify(contactDAO, never()).save(any(Contact.class));
    }

    @Test
    public void testSaveAllData() {
        when(contactDAO.save(mockContact)).thenReturn(mockContact);
        when(contactDAO.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());

        contactService.save(mockContact);
        verify(contactDAO, times(1))
                .findByFirstNameAndLastName(mockContact.getFirstName(), mockContact.getLastName());
        verify(contactDAO, times(1)).save(mockContact);
    }
}
