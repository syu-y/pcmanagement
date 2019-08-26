package com.example.pcmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pcmanagement.domain.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
