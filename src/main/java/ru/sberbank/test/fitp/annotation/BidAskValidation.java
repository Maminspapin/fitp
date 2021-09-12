package ru.sberbank.test.fitp.annotation;

import ru.sberbank.test.fitp.annotation.validator.BidAskValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BidAskValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BidAskValidation {
    String message() default "Bid value should be less then Ask in a quote";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}