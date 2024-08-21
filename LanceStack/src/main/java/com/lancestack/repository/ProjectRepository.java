package com.lancestack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Project;
import com.lancestack.entities.ProjectType;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
	@Query("Select p from Project p where p.budget between :startRange and :endRange")
	List<Project> findProjectsByBudgetRange(double startRange, double endRange);
	
//	@Query("Select p from Project p where p.projType = :projectType")
//	List<Project> findProjectsByProjectType(ProjectType projectType);
	
	@Query("Select p from Project p where p.status = 'COMPLETED'")
	List<Project> findProjectsWhereStatusCompleted();
	
	@Query("Select p from Project p where p.status = 'IN_PROGRESS'")
	List<Project> findProjectsWhereStatusInprogress();
	
	List<Project> findByProjType(ProjectType projType);
	
//	@Query(value = "SELECT p.* FROM projects p "
//            + "JOIN bids b ON (link unavailable) = b.project_id "
//            + "WHERE b.freelancer_id = :freelancerId AND p.status = 'IN_PROGRESS'",
//            nativeQuery = true)
//    List<Project> findAllocatedProjectsInProgress(@Param("freelancerId") Long freelancerId);
}
