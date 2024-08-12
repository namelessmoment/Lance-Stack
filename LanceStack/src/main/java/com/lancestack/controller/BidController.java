package com.lancestack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancestack.dto.ApiResponse;
import com.lancestack.dto.Bid.BidDTO;
import com.lancestack.dto.Bid.UpdateBidDTO;
import com.lancestack.dto.Freelancer.FreelancerBidDTO;
import com.lancestack.dto.Freelancer.FreelancerDTO;
import com.lancestack.entities.Bid;
import com.lancestack.service.BidService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("bids")
public class BidController {
	
	@Autowired
	BidService bidService;
	
	@Operation(description = "Posting a Bid.")
	@PostMapping("/postBid")
    public ResponseEntity<?> postBid(@RequestBody BidDTO bidDTO) {
//        BidDTO savedBid = bidService.postBid(bidDTO);
//        return ResponseEntity.ok(savedBid);
		try {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(bidService.postBid(bidDTO));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
    }

	@Operation(description = "For get the bids by Project ID.")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BidDTO>> getAllBidsByProject(@PathVariable Long projectId) {
        List<BidDTO> bids = bidService.getAllBidsByProject(projectId);
        return ResponseEntity.ok(bids);
	}
        
	
	@Operation(description = "Update Bid by bid ID.")
	@PutMapping("/updateBid/{bidId}")
	public ResponseEntity<?> updateBid(@PathVariable Long bidId , @RequestBody UpdateBidDTO updatedBid){
		try {
		return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(bidService.updateBid(bidId, updatedBid));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
		}
    
	@Operation(description = "Delete the bid by bid ID.")
	@DeleteMapping("/deleteBid/{bidId}")
	public ResponseEntity<?> deletBid(@PathVariable Long bidId){
		try {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(bidService.deleteBid(bidId));
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@Operation(description = "Get all bids associated with Single Freelancer ID.")
	@GetMapping("/getByFreelancerId/{freelancerId}")
	public ResponseEntity<List<FreelancerBidDTO>> getAllBidsByFreelancerId(@PathVariable Long freelancerId){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(bidService.getAllBidsByFreelancerId(freelancerId));
	}
	
	@Operation(description =  "Get freelancer by Bid Id")
	@GetMapping("/getFreelancerByBidId/{bidId}")
	public ResponseEntity<FreelancerDTO> getFreelancerByBidId(@PathVariable Long bidId){
		return ResponseEntity.status(HttpStatus.OK).body(bidService.getFreelanerByBidId(bidId));
	}
}
