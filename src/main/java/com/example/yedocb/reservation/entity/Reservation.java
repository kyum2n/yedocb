package com.example.yedocb.reservation.entity;

import java.time.LocalTime;
import java.util.Date;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class Reservation {
    private int rId;
    private String uId;
    private String tName;
    private Date consultDate;
    private LocalTime consultTime;
    private String status;
}