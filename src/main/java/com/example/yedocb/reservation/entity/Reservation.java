package com.example.yedocb.reservation.entity;

import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Reservation {
    private int rId;
    
    @JsonProperty("uId")
    private String uId;
    
    @JsonProperty("tName")
    private String tName;
    
    private Date consultDate;
    
    private LocalTime consultTime;
    
    private String status;
}