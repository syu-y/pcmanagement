package com.example.pcmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.domain.repository.UserRepository;

@Service
public class UserService{
    @Autowired
	private UserRepository userRepository;

	public User getUser(String userId) {
		return userRepository.findById(userId).get();
	}

	public boolean checkUser(String userId) {
		return userRepository.existsById(userId);
	}

	public List<User> getUsers(){
		return userRepository.findAll();
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}


}
