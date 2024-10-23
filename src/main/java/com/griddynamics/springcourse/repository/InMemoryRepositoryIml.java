package com.griddynamics.springcourse.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Keeps phoneBook data in memory in ordered in accordance to addition.
 */
@Repository
public class InMemoryRepositoryIml implements InMemoryRepository {

    private Map<String, Set<String>> data;

    /**
     * no args constructor
     */
    public InMemoryRepositoryIml() {
        // LinkedHashMap is chosen because usually iteration order matters
        this(new LinkedHashMap<>());
    }

    /**
     * this constructor allows to inject initial data to the repository
     *
     * @param data
     */
    public InMemoryRepositoryIml(Map<String, Set<String>> data) {
        this.data = new LinkedHashMap<>(data);
    }

    @Override
    public Map<String, Set<String>> findAll() {
        return new LinkedHashMap<>(this.data);
    }

    @Override
    public Set<String> findAllPhonesByName(String name) {
        return this.data.getOrDefault(name, Collections.emptySet());
    }

    @Override
    public String findNameByPhone(String phone) {
        for (Map.Entry<String, Set<String>> entry : data.entrySet()) {
            if (entry.getValue().contains(phone)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void addPhone(String name, String phone) {
        this.data.computeIfAbsent(name, phones -> new HashSet<>()).add(phone);
    }

    @Override
    public void removeName(String name) {
        if (!this.data.containsKey(name)) {
            throw new IllegalArgumentException("Name not found in phone book: " + name);
        }
        this.data.remove(name);
    }

}
