package ru.sberbank.test.fitp.model.dto;

import lombok.Data;
import ru.sberbank.test.fitp.annotation.BidAskValidation;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@BidAskValidation
public class QuoteDTO {

    @Size(min = 12, max = 12)
    private String isin;

    @Min(value = 0)
    private double bid;

    @Min(value = 0)
    private double ask;
}
