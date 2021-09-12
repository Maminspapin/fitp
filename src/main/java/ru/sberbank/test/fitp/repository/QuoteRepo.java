package ru.sberbank.test.fitp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.test.fitp.model.entity.Quote;

public interface QuoteRepo extends JpaRepository<Quote, Integer> {
}
