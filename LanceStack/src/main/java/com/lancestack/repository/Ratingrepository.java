package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Rating;

@Repository
public interface Ratingrepository extends JpaRepository<Rating, Long> {

}
