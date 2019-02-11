package com.ntapia.phonebook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntapia.phonebook.exception.ContactAlreadyExistException;
import com.ntapia.phonebook.exception.ContactInvalidDataException;
import com.ntapia.phonebook.exception.ContactSearchInvalidDataException;
import com.ntapia.phonebook.model.Contact;
import com.ntapia.phonebook.service.ContactService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * REST Controller Test
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    private static final String PATH_SAVE = "/contacts";
    private static final String PATH_SEARCH = PATH_SAVE + "?term={0}";

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();
    private static final String EMPTY_STRING = "";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    private Contact mockContact1;
    private Contact mockContact2;

    @Before
    public void init() {
        mockContact1 = Contact.builder().firstName("Tony").lastName("Stark").phone("3387879").build();
        mockContact1.setId(1L);
        mockContact2 = Contact.builder().firstName("Steve").lastName("Rogers").phone("3309898").build();
        mockContact2.setId(2L);
    }

    @Test
    public void testSearchGet() throws Exception {
        final String term = "33";
        when(contactService.search(term)).thenReturn(Arrays.asList(mockContact1, mockContact2));

        mockMvc.perform(get(PATH_SEARCH, term))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(mockContact1.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(mockContact1.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(mockContact1.getLastName())))
                .andExpect(jsonPath("$[0].phone", is(mockContact1.getPhone())))
                .andExpect(jsonPath("$[1].id", is(mockContact2.getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(mockContact2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(mockContact2.getLastName())))
                .andExpect(jsonPath("$[1].phone", is(mockContact2.getPhone())));

        verify(contactService, times(1)).search(term);
    }

    @Test
    public void testSearchGetEmptyResult() throws Exception {
        final String term = "33";
        when(contactService.search(term)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(PATH_SEARCH, term))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(contactService, times(1)).search(term);
    }

    @Test
    public void testSearchGetEmptyTerm() throws Exception {
        when(contactService.search(EMPTY_STRING)).thenThrow(new ContactSearchInvalidDataException());

        mockMvc.perform(get(PATH_SEARCH, EMPTY_STRING))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("Term to search is required!")));

        verify(contactService, times(1)).search(EMPTY_STRING);
    }

    @Test
    public void testSavePost() throws Exception {
        when(contactService.save(any())).thenReturn(mockContact1);

        ContactDTO contactDTO = ContactDTO.builder()
                .firstName(mockContact1.getFirstName())
                .lastName(mockContact1.getLastName())
                .phone(mockContact1.getPhone())
                .build();

        mockMvc.perform(post(PATH_SAVE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contactDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockContact1.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(mockContact1.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(mockContact1.getLastName()))
                .andExpect(jsonPath("$.phone").value(mockContact1.getPhone()));

        verify(contactService, times(1)).save(any());
    }

    @Test
    public void testSavePostInvalidData() throws Exception {
        when(contactService.save(any())).thenThrow(new ContactInvalidDataException());

        ContactDTO contactDTO = ContactDTO.builder()
                .lastName(mockContact1.getLastName())
                .phone(mockContact1.getPhone())
                .build();

        mockMvc.perform(post(PATH_SAVE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contactDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason(containsString("First name, Last name and Phone are required!")));

        verify(contactService, times(1)).save(any());
    }

    @Test
    public void testSavePostAlreadyExist() throws Exception {
        when(contactService.save(any())).thenThrow(new ContactAlreadyExistException());

        ContactDTO contactDTO = ContactDTO.builder()
                .lastName(mockContact1.getLastName())
                .phone(mockContact1.getPhone())
                .build();

        mockMvc.perform(post(PATH_SAVE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(contactDTO)))
                .andExpect(status().isConflict())
                .andExpect(status().reason(containsString("Contact already register!")));

        verify(contactService, times(1)).save(any());
    }

    private static String asJsonString(final Object obj) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
