package ru.sberbank.test.fitp.service;

import org.springframework.stereotype.Component;
import ru.sberbank.test.fitp.model.entity.Quote;

@Component
public class ElvlCalculator {

    public double calculate(Quote quote, double elvlCurrent) {

        double bid = quote.getBid();
        double ask = quote.getAsk();

        if (bid != 0 && bid > elvlCurrent) {
            return quote.getBid();
        }

        if (bid == 0 || ask < elvlCurrent) {
            return quote.getAsk();
        }

        return elvlCurrent;
    }

}
