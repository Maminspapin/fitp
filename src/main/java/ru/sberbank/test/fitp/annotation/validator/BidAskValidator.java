package ru.sberbank.test.fitp.annotation.validator;

import ru.sberbank.test.fitp.annotation.BidAskValidation;
import ru.sberbank.test.fitp.model.dto.QuoteDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BidAskValidator implements ConstraintValidator<BidAskValidation, QuoteDTO> {


    @Override
    public void initialize(BidAskValidation constraintAnnotation) {

    }

    @Override
    public boolean isValid(QuoteDTO quoteDTO, ConstraintValidatorContext context) {
        if (quoteDTO.getBid() > quoteDTO.getAsk()) {
            return false;
        }
        return true;
    }
}
