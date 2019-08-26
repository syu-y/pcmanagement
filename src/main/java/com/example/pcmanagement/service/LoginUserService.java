package com.example.pcmanagement.service;

import javax.transaction.Transactional;

import com.example.pcmanagement.domain.model.LoginUser;
import com.example.pcmanagement.domain.model.User;
import com.example.pcmanagement.domain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class LoginUserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findById(userId).get();
		System.out.println(user.getUserId() + "/" + user.getUserName() + "/" + user.getPassword() + "/" + user.getPermission());
		if (user == null) {
			throw new UsernameNotFoundException("not found");
		}
		return new LoginUser(user);
	}
}
