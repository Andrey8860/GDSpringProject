package com.griddynamics.springcourse.restcontroller;

import com.griddynamics.springcourse.entity.PhoneBookEntry;
import com.griddynamics.springcourse.service.PhoneBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/v1/contacts")
public class PhoneBookRestController {

    private PhoneBook phoneBook;

    @Autowired
    public PhoneBookRestController(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }

    @GetMapping
    public Map<String, Set<String>> findAll() {
        return phoneBook.findAll();
    }

    @GetMapping("/{name}")
    public Set<String> findByName(@PathVariable String name) {
        return phoneBook.findByName(name);
    }

    @PutMapping("/{name}")
    public void addPhoneToName(@PathVariable String name, @RequestParam String phoneNumber) {
        phoneBook.addPhoneToName(name, phoneNumber);
    }

    @PostMapping
    public void createRecord(@RequestBody PhoneBookEntry entry) {
        phoneBook.createRecord(entry);
    }

    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable String name) {
        phoneBook.deleteByName(name);
    }

}
