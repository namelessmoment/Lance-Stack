package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

}
