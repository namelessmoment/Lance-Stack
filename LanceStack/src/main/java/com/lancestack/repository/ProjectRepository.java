package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	@Query("Select p from Project p where p.budget between :startRange and :endRange")
	List<Project> findProjectsByBudgetRange(double startRange, double endRange);
}
