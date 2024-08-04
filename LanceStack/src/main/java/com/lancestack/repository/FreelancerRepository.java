package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Freelancer;
import java.util.List;


@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
	Freelancer findByEmail(String email);

}
