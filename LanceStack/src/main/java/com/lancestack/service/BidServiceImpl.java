package com.lancestack.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lancestack.repository.BidRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BidServiceImpl implements BidService {
	
	@Autowired
	BidRepository bidRepo;
	
	@Autowired
	ModelMapper modepMapper;
}
