package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

	@Query("SELECT c FROM Contract c WHERE c.status = 'IN_PROGRESS'")
    List<Contract> findAllContractsInProgress();

}
