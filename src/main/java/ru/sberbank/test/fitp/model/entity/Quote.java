package ru.sberbank.test.fitp.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity(name = "quote")
public class Quote {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "isin")
    private String isin;

    @Column(name = "bid")
    private double bid;

    @Column(name = "ask")
    private double ask;

    @Column(name = "time_received")
    private LocalDateTime timeReceived;
}

