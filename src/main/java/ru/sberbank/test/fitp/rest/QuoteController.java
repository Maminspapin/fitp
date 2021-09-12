package ru.sberbank.test.fitp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.test.fitp.model.dto.ElvlDTO;
import ru.sberbank.test.fitp.model.dto.QuoteDTO;
import ru.sberbank.test.fitp.model.entity.Elvl;
import ru.sberbank.test.fitp.model.entity.Quote;
import ru.sberbank.test.fitp.service.QuoteService;
import ru.sberbank.test.fitp.util.ElvlMapper;
import ru.sberbank.test.fitp.util.QuoteMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/quotes/")
public class QuoteController {

        private final QuoteService quoteService;
        private final QuoteMapper quoteMapper;
        private final ElvlMapper elvlMapper;

    public QuoteController(@Autowired QuoteService quoteService,
                           @Autowired QuoteMapper quoteMapper,
                           @Autowired ElvlMapper elvlMapper) {
        this.quoteService = quoteService;
        this.quoteMapper = quoteMapper;
        this.elvlMapper = elvlMapper;
    }

    @PostMapping("/new")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void save(@Valid @RequestBody QuoteDTO quoteDTO) {

        Quote quote = quoteMapper.toEntity(quoteDTO);
        quoteService.save(quote);
    }

    @GetMapping("elvl/all")
    public List<ElvlDTO> getAll() {

        List<Elvl> elvlList = quoteService.getAll();
        return elvlList.stream()
                .map(elvlMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("elvl/{isin}")
    public ElvlDTO getByISIN(@PathVariable String isin) {

        Elvl elvl = quoteService.getByISIN(isin);
        return elvlMapper.toDTO(elvl);
    }

}
