package ru.sberbank.test.fitp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import ru.sberbank.test.fitp.model.entity.Elvl;
import ru.sberbank.test.fitp.model.entity.Quote;
import ru.sberbank.test.fitp.repository.ElvlRepo;
import ru.sberbank.test.fitp.repository.QuoteRepo;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    private final QuoteRepo quoteRepo;
    private final ElvlRepo elvlRepo;
    private final ElvlCalculator elvlCalculator;

    public QuoteService(@Autowired QuoteRepo quoteRepo,
                        @Autowired ElvlRepo elvlRepo,
                        @Autowired ElvlCalculator elvlCalculator) {
        this.quoteRepo = quoteRepo;
        this.elvlRepo = elvlRepo;
        this.elvlCalculator = elvlCalculator;
    }

    public void save(Quote quote) {

        Optional<Elvl> fromDB = elvlRepo.findByIsin(quote.getIsin());
        Elvl elvl = fromDB.orElse(new Elvl(quote.getIsin(), 0.0));

        double elvlCurrent = elvl.getElvl();
        double elvlNew = elvlCalculator.calculate(quote, elvlCurrent);

        if (elvlCurrent != elvlNew) {
            elvl.setElvl(elvlNew);
            elvlRepo.save(elvl);
        }

        quoteRepo.save(quote);
    }

    public List<Elvl> getAll() {

        return elvlRepo.findAll();
    }

    public Elvl getByISIN(@PathVariable String isin) {

        Optional<Elvl> result = elvlRepo.findByIsin(isin);

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return result.get();
    }


}
