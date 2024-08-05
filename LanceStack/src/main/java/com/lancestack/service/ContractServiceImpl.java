package com.lancestack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.repository.ContractRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractRepository contractRepo;
}
