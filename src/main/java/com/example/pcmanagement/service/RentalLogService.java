package com.example.pcmanagement.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.pcmanagement.domain.model.RentalLog;
import com.example.pcmanagement.domain.repository.RentalLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class RentalLogService{
    @Autowired
    RentalLogRepository rentalLogRepository;

    public List<RentalLog> findNotReturnLog() {
        return rentalLogRepository.findNotReturnLog();
    }
    public RentalLog getRentalLog(String rentalId) {
		return rentalLogRepository.findById(rentalId).get();
	}
	public List<RentalLog> getRentalLogs(){
		return rentalLogRepository.findAll();
	}
	public void addRentalLog(RentalLog rentalLog) {
		rentalLogRepository.save(rentalLog);
	}
}
