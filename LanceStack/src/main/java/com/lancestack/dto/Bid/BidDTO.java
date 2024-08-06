package com.lancestack.dto.Bid;

import com.lancestack.dto.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BidDTO extends BaseEntity{
    private double bidAmount;
    private int daysWillTake;
    private String bidDescription;
    private Long projectId;
    private Long freelancerId;
}

