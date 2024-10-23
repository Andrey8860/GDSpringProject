package com.griddynamics.springcourse.repository;

import java.util.Map;
import java.util.Set;

/**
 * Repository interface
 */
public interface InMemoryRepository {
    /**
     * @return all repository records "{name: [phone1, phone2]}"
     */
    Map<String, Set<String>> findAll();

    /**
     * @param name
     * @return all phone numbers associated with a name
     */
    Set<String> findAllPhonesByName(String name);

    /**
     * @param phone
     * @return name associated with a phone
     */
    String findNameByPhone(String phone);

    /**
     * add phone number for a name or create new record
     *
     * @param name
     * @param phone
     */
    void addPhone(String name, String phone);

    void removeName(String name) throws IllegalArgumentException;
}
