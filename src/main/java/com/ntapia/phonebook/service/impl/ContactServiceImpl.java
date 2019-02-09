package com.ntapia.phonebook.service.impl;

import com.ntapia.phonebook.dao.ContactDAO;
import com.ntapia.phonebook.exception.ContactInvalidDataException;
import com.ntapia.phonebook.exception.ContactSaveException;
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
        LOG.info("Persist contact info: {0}", contact);

        if (contact == null || StringUtils.isBlank(contact.getFirstName()) || StringUtils.isBlank(contact.getPhone())) {
            throw new ContactInvalidDataException();
        }

        try {
            return this.contactDAO.save(contact);
        } catch (Exception e) {
            LOG.error("Error to persist contact", e);
            throw new ContactSaveException();
        }
    }

    @Override
    public List<Contact> search(String term) {
        LOG.info("Search contacts term: [{0}]", term);

        if (StringUtils.isBlank(term)) {
            throw new ContactSearchInvalidDataException();
        }

        return this.contactDAO.findAll();
    }
}
