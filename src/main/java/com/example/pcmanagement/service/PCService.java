package com.example.pcmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.example.pcmanagement.domain.model.PC;
import com.example.pcmanagement.domain.repository.PCRepository;

@Transactional
@Service
public class PCService{
    @Autowired
	private PCRepository pcRepository;

	public PC getPC(String pcId) {
		return pcRepository.findById(pcId).get();
	}
	public boolean checkPC(String pcId) {
		return pcRepository.existsById(pcId);
	}
	public List<PC> getPCs(){
		return pcRepository.findAll();
	}
	public void addPC(PC pc) {
		pcRepository.save(pc);
	}
}
