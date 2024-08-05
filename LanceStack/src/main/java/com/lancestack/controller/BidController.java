package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.BidDTO;
import com.lancestack.entities.Bid;
import com.lancestack.service.BidService;

@RestController
@RequestMapping("bids")
public class BidController {
	
	@Autowired
	BidService bidService;
	
	@PostMapping("/postBid")
    public ResponseEntity<BidDTO> postBid(@RequestBody BidDTO bidDTO) {
        BidDTO savedBid = bidService.postBid(bidDTO);
        return ResponseEntity.ok(savedBid);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Bid>> getAllBidsByProject(@PathVariable Long projectId) {
        List<Bid> bids = bidService.getAllBidsByProject(projectId);
        return ResponseEntity.ok(bids);
    }
    
}
