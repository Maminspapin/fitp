package ru.sberbank.test.fitp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.sberbank.test.fitp.model.dto.ElvlDTO;
import ru.sberbank.test.fitp.model.dto.QuoteDTO;
import ru.sberbank.test.fitp.model.entity.Elvl;
import ru.sberbank.test.fitp.service.QuoteService;
import ru.sberbank.test.fitp.util.ElvlMapper;
import ru.sberbank.test.fitp.util.QuoteMapper;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteControllerTest {

    @MockBean
    private QuoteService quoteService;

    @MockBean
    private QuoteMapper quoteMapper;

    @MockBean
    private ElvlMapper elvlMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("201: POST /api/quotes/new")
    void save_created() throws Exception {

        QuoteDTO dto = new QuoteDTO();
        dto.setIsin("ABC123456789");
        dto.setAsk(60.60);
        dto.setBid(50.50);

        mockMvc.perform(post("/api/quotes/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("400 (not valid isin length): POST /api/quotes/new")
    void save_bad_request_1() throws Exception {

        QuoteDTO dto = new QuoteDTO();
        dto.setIsin("ABC");
        dto.setAsk(60.60);
        dto.setBid(50.50);

        mockMvc.perform(post("/api/quotes/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("400 (ask less then bid): POST /api/quotes/new")
    void save_bad_request_2() throws Exception {

        QuoteDTO dto = new QuoteDTO();
        dto.setIsin("ABC123456789");
        dto.setAsk(50.50);
        dto.setBid(60.60);

        mockMvc.perform(post("/api/quotes/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("200: GET /api/quotes/elvl/all")
    public void getAll() throws Exception {

        Elvl elvlA = new Elvl();
        elvlA.setIsin("A12345678910");
        elvlA.setElvl(100.50);

        Elvl elvlB = new Elvl();
        elvlB.setIsin("B12345678910");
        elvlB.setElvl(200.50);

        ElvlDTO elvlDTOA = new ElvlDTO();
        elvlDTOA.setIsin(elvlA.getIsin());
        elvlDTOA.setElvl(elvlA.getElvl());

        ElvlDTO elvlDTOB = new ElvlDTO();
        elvlDTOB.setIsin(elvlB.getIsin());
        elvlDTOB.setElvl(elvlB.getElvl());

        Mockito.when(quoteService.getAll()).thenReturn(Arrays.asList(elvlA, elvlB));
        Mockito.when(elvlMapper.toDTO(elvlA)).thenReturn(elvlDTOA);
        Mockito.when(elvlMapper.toDTO(elvlB)).thenReturn(elvlDTOB);

        mockMvc.perform(get("/api/quotes/elvl/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].isin", is("A12345678910")))
                .andExpect(jsonPath("$[0].elvl", is(100.50)))
                .andExpect(jsonPath("$[1].isin", is("B12345678910")))
                .andExpect(jsonPath("$[1].elvl", is(200.50)));
    }

    @Test
    @DisplayName("200: GET /api/quotes/elvl/{isin}}")
    public void getByISIN_ok() throws Exception {

        Elvl elvlA = new Elvl();
        elvlA.setIsin("A12345678910");
        elvlA.setElvl(100.50);

        ElvlDTO elvlDTOA = new ElvlDTO();
        elvlDTOA.setIsin(elvlA.getIsin());
        elvlDTOA.setElvl(elvlA.getElvl());

        Mockito.when(quoteService.getByISIN("A12345678910")).thenReturn(elvlA);
        Mockito.when(elvlMapper.toDTO(elvlA)).thenReturn(elvlDTOA);

        mockMvc.perform(get("/api/quotes/elvl/A12345678910"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isin", is("A12345678910")))
                .andExpect(jsonPath("$.elvl", is(100.50)));
    }

    @Test
    @DisplayName("404: GET /api/quotes/elvl/{isin}}")
    public void getByISIN_not_found() throws Exception {

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(quoteService).getByISIN("A12345678910");

        mockMvc.perform(get("/api/quotes/elvl/A12345678910"))
                .andExpect(status().isNotFound());
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
