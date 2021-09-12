package ru.sberbank.test.fitp.model.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ElvlDTO {

    @Size(min = 12, max = 12)
    private String isin;
    private double elvl;
}
