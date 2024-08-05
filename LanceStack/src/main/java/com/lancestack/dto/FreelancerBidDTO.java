package com.lancestack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerBidDTO {
	private double bidAmount;
    private int daysWillTake;
    private String bidDescription;
}
