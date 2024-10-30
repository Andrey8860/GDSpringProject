package com.griddynamics.springcourse.service;

import com.griddynamics.springcourse.entity.PhoneBookEntry;
import com.griddynamics.springcourse.repository.InMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * PhoneBook service implementation
 */
@Service
public class PhoneBook {

    //@Autowired
    private InMemoryRepository repository;

    public PhoneBook() {
        // be careful this.repository will not be initialised if injection on setter is chosen
    }

    /**
     * injection is supported on constructor level.
     *
     * @param repository
     */
    @Autowired
    public PhoneBook(@Qualifier("repository") InMemoryRepository repository) {
        this.repository = repository;
    }

    /**
     * injection is supported on setter level
     *
     * @param repository
     */
    public void setRepository(InMemoryRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all pairs of type {name: [phone1, phone2]}
     */
    public Map<String, Set<String>> findAll() {
        return repository.findAll();
    }

    public Set<String> findByName(String name) {
        return repository.findAllPhonesByName(name) != null
                ? repository.findAllPhonesByName(name)
                : Collections.emptySet();
    }

    public void addPhoneToName(String name, String phoneNumber) {
        repository.addPhone(name, phoneNumber);
    }

    public void createRecord(PhoneBookEntry entry) {
        if (entry.getName() == null || entry.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Name and phone number must not be null");
        }
        repository.addPhone(entry.getName(), entry.getPhoneNumber());
    }

    public void deleteByName(String name) {
        repository.removeName(name);
    }
}
