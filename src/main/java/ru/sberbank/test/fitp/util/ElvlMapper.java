package ru.sberbank.test.fitp.util;

import org.mapstruct.Mapper;
import ru.sberbank.test.fitp.model.dto.ElvlDTO;
import ru.sberbank.test.fitp.model.entity.Elvl;

@Mapper(componentModel = "spring")
public interface ElvlMapper {

    Elvl toEntity(ElvlDTO elvlDTO);
    ElvlDTO toDTO(Elvl elvl);
}
