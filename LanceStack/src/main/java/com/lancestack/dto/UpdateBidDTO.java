package com.lancestack.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateBidDTO extends BaseEntity {
	private double bidAmount;
    private int daysWillTake;
    private String bidDescription;
}
