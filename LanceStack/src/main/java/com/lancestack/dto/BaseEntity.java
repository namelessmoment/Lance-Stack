package com.lancestack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
//	@CreationTimestamp //to auto generate creation date
//	@Column(name="creation_date")
//	@JsonProperty(access = Access.READ_ONLY)
//	private LocalDate creationDate;
//	@UpdateTimestamp //to auto generate updation datetime(TS)
//	@JsonProperty(access = Access.READ_ONLY)
//	@Column(name="updation_ts")
//	private LocalDateTime updationTimeStamp;
}
