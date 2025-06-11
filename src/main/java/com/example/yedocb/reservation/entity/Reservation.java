package com.example.yedocb.reservation.entity;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Reservation {

	@JsonProperty("rId")
    private int rId;
    
    @JsonProperty("uId")
    private String uId;
    
    @JsonProperty("tName")
    private String tName;
    
    @JsonProperty("consultDate")
    private Date consultDate;
    
    @JsonProperty("consultTime")
    private LocalTime consultTime;
    
    @JsonProperty("status")
    private String status;
}