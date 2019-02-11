package com.ntapia.phonebook.service.impl;

import com.ntapia.phonebook.dao.ContactDAO;
import com.ntapia.phonebook.exception.ContactAlreadyExistException;
import com.ntapia.phonebook.exception.ContactInvalidDataException;
import com.ntapia.phonebook.exception.ContactSaveException;
import com.ntapia.phonebook.exception.ContactSearchException;
import com.ntapia.phonebook.exception.ContactSearchInvalidDataException;
import com.ntapia.phonebook.model.Contact;
import com.ntapia.phonebook.service.ContactService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

/**
 * Contact service default implementation
 */
@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final ContactDAO contactDAO;

    @Autowired
    public ContactServiceImpl(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public Contact save(Contact contact) {
        LOG.info("Persist contact info: {}", contact);

        if (contact == null || StringUtils.isBlank(contact.getFirstName()) || StringUtils.isBlank(contact.getLastName())
                || StringUtils.isBlank(contact.getPhone())) {
            throw new ContactInvalidDataException();
        }

        contactDAO.findByFirstNameAndLastName(contact.getFirstName(), contact.getLastName())
                .ifPresent(persisted -> { throw new ContactAlreadyExistException(); });

        contact.setTextData(buildSearchData(contact));

        try {
            return this.contactDAO.save(contact);
        } catch (Exception e) {
            LOG.error("Error to persist contact", e);
            throw new ContactSaveException();
        }
    }

    private String buildSearchData(Contact contact) {
        return contact.getFirstName().toUpperCase() + contact.getLastName().toUpperCase() + contact.getPhone();
    }

    @Override
    public List<Contact> search(String term) {
        LOG.info("Search contacts term: [{}]", term);

        if (StringUtils.isBlank(term)) {
            throw new ContactSearchInvalidDataException();
        }

        try {
            return this.contactDAO.findTop100ByTextDataContainingOrderByFirstName(term.toUpperCase());
        } catch (Exception e) {
            LOG.error("Error to search contacts", e);
            throw new ContactSearchException();
        }
    }
}
