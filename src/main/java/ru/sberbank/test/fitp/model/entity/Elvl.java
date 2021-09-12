package ru.sberbank.test.fitp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "elvl")
public class Elvl {

    @Id
    @Column(name = "isin")
    private String isin;

    @Column(name = "elvl")
    private double elvl;
}
