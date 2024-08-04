package com.lancestack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lancestack.entities.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

}
