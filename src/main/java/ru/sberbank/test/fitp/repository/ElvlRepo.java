package ru.sberbank.test.fitp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.test.fitp.model.entity.Elvl;

import java.util.Optional;

public interface ElvlRepo extends JpaRepository<Elvl, String> {

    Optional<Elvl> findByIsin(String isin);

}
