package ru.sberbank.test.fitp.util;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.sberbank.test.fitp.model.dto.QuoteDTO;
import ru.sberbank.test.fitp.model.entity.Quote;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    Quote toEntity(QuoteDTO quoteDTO);
    QuoteDTO toDTO(Quote quote);

    @AfterMapping
    default void setTime(@MappingTarget Quote quote) {
        quote.setTimeReceived(LocalDateTime.now());
    }
}
