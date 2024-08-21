package com.lancestack.dto.Freelancer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerBidDTO {
	private Long id;
	private double bidAmount;
    private int daysWillTake;
    private String bidDescription;
    private Long projectId;
    private Long freelancerId;
}
