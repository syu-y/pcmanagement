package com.example.pcmanagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pcmanagement.domain.model.PC;

@Repository
public interface PCRepository extends JpaRepository<PC, String> {}
