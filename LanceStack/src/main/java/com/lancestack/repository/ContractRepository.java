package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Contract;
import com.lancestack.entities.ContractStatus;
import com.lancestack.entities.Freelancer;
import com.lancestack.entities.Project;
import com.lancestack.entities.User;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

	@Query("SELECT c FROM Contract c WHERE c.status = 'IN_PROGRESS'")
    List<Contract> findAllContractsInProgress();
	
	List<Contract> findByStatus(ContractStatus status);
	
	List<Contract> findByFreelancerIdAndStatus(Long freelancerId, ContractStatus status);
	
	@Query(value = "SELECT c.* FROM contracts c " +
            "JOIN projects p ON c.project_id = p.id " +
            "WHERE c.status = 'IN_PROGRESS' AND p.user_id = :userId", nativeQuery = true)
	List<Contract> findAllContractsInProgressByUserId(Long userId);
	
	@Query(value = "SELECT c.* FROM contracts c " +
            "JOIN projects p ON c.project_id = p.id " +
            "WHERE c.status = 'COMPLETED' AND p.user_id = :userId", nativeQuery = true)
	List<Contract> findAllContractsCompletedByUserId(Long userId);

	@Query("SELECT c FROM Contract c WHERE c.freelancer = :freelancer AND c.status = 'IN_PROGRESS'")
	List<Contract> findInProgressContractsByFreelancer(@Param("freelancer") Freelancer freelancer);


	@Query("SELECT c FROM Contract c WHERE c.freelancer = :freelancer AND c.status = 'COMPLETED'")
	List<Contract> findCompletedContractsByFreelancer(@Param("freelancer") Freelancer freelancer);

	boolean existsByProject(Project project);

//	@Query("SELECT c FROM Contract c JOIN c.project p JOIN p.user u WHERE u = :user")
//    List<Contract> findContractsByUser(@Param("user") User user);
}
