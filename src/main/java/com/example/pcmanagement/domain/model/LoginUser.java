package com.example.pcmanagement.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.security.core.authority.AuthorityUtils;

@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUser extends org.springframework.security.core.userdetails.User {

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = 1L;
	private User user;

	public LoginUser(User user) {
		super(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getPermission()));
		this.user = user;
	}
}
