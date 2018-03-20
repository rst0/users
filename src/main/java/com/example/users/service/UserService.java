package com.example.users.service;

import com.example.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private CrudRepository<User, Long> repository;

    @Transactional
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    @Transactional
    public User getById(long id) {
        return repository.findOne(id);
    }

    public User save(User user) {
        return repository.save(user);
    }
}
