package com.griddynamics.springcourse;

import com.griddynamics.springcourse.config.ApplicationConfig;
import com.griddynamics.springcourse.service.PhoneBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class PhoneBookTest {

    @Autowired
    private PhoneBook phoneBook;

    @Test
    @DirtiesContext
    public void get_person_phone_numbers() {
        final Set<String> expected = new HashSet<>(asList("+79601232233"));
        assertEquals("phone numbers do not match",
                expected,
                phoneBook.findAll().get("Alex"));
    }

    @Test
    @DirtiesContext
    public void addPhoneNumberToExistingName() {
        String name = "Alex";
        String newPhone = "+1234567890";
        phoneBook.addPhoneToName(name, newPhone);

        Set<String> expectedPhones = new HashSet<>(asList("+79601232233", "+1234567890"));
        assertEquals("Phone numbers do not match after adding a new phone",
                expectedPhones, phoneBook.findByName(name));
    }

    @Test
    public void addPhoneNumberToNewName() {
        String name = "Charlie";
        String phone = "+1122334455";
        phoneBook.addPhoneToName(name, phone);

        Set<String> expectedPhones = new HashSet<>(List.of(phone));
        assertEquals("Phone numbers do not match for the new name",
                expectedPhones, phoneBook.findByName(name));
    }

    @Test
    @DirtiesContext
    public void deleteExistingName() {
        String name = "Alex";
        phoneBook.deleteByName(name);

        // Verify that the name has been removed
        assertEquals("Expected the name to be removed",
                0, phoneBook.findByName(name).size());
    }

    @Test
    public void deleteNonExistingNameThrowsException() {
        String nonExistentName = "NonExistent";

        // Verify that an exception is thrown when trying to delete a non-existent name
        assertThrows(IllegalArgumentException.class, () -> {
            phoneBook.deleteByName(nonExistentName);
        });
    }

    @Test
    @DirtiesContext
    public void findByNameReturnsCorrectPhoneNumbers() {
        String name = "Alex";
        Set<String> expectedPhones = new HashSet<>(List.of("+79601232233"));
        assertEquals("The phone numbers for Alex do not match",
                expectedPhones, phoneBook.findByName(name));
    }

    @Test
    public void findByNameReturnsEmptySetForNonExistentName() {
        String nonExistentName = "NonExistent";
        Set<String> expectedPhones = Collections.emptySet();
        assertEquals("Expected an empty set for a non-existent name",
                expectedPhones, phoneBook.findByName(nonExistentName));
    }

}
