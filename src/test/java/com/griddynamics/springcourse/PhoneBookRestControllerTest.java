package com.griddynamics.springcourse;

import com.griddynamics.springcourse.config.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ApplicationConfig.class})
public class PhoneBookRestControllerTest {

    private static final String BASE_URL = "/api/v1/contacts";
    private static final String JSON_CONTENT_TYPE = "application/json";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllContactsShouldReturn200AndJson() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE));
    }

    @Test
    public void getAllContactsShouldReturnCorrectStructureAndData() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.Alex[0]", is("+79601232233")))
                .andExpect(jsonPath("$.Billy", hasSize(3)));
    }

    @Test
    public void getContactByNameShouldReturn200AndExpectedPhones() throws Exception {
        mockMvc.perform(get(BASE_URL + "/Alex"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is("+79601232233")));
    }

    @Test
    public void getNonExistentContactShouldReturn200AndEmptyArray() throws Exception {
        mockMvc.perform(get(BASE_URL + "/randomjibberish"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void addPhoneToExistingContactShouldReturn200() throws Exception {
        mockMvc.perform(put(BASE_URL + "/Alex")
                        .param("phoneNumber", "+1234567890"))
                .andExpect(status().isOk());
    }

    @Test
    public void addPhoneToNonExistentContactShouldReturn200() throws Exception {
        mockMvc.perform(put(BASE_URL + "/NonExistent")
                        .param("phoneNumber", "+1234567890"))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewContactShouldReturn201() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(JSON_CONTENT_TYPE)
                        .content("{\"name\": \"Charlie\", \"phoneNumber\": \"+1122334455\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void createContactWithInvalidJsonShouldReturn400() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(JSON_CONTENT_TYPE)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteExistingContactShouldReturn200() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/Alex"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistentContactShouldReturn404() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/randomjibberish"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.error").value("Name not found in phone book: randomjibberish"));
    }
}
