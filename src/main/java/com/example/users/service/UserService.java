package com.example.users.service;

import com.example.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	private CrudRepository<User, Long> repository;

	public Iterable<User> retrieveAll() {
		return repository.findAll();
	}

	public User retrieveById(long id) {
		return repository.findOne(id);
	}

	public User save(User user) {
		return repository.save(user);
	}

	public User update(User user) {
		return repository.save(user);
	}

	public void delete(User user) {
		repository.delete(user);
	}
}
