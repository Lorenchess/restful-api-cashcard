package com.example.cashcard;

import com.example.cashcard.controller.CashCardController;
import com.example.cashcard.dto.CashCardDTO;
import com.example.cashcard.service.CashCardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CashCardController.class)
public class CashCardWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CashCardService service;

    private final ObjectMapper mapper = new ObjectMapper();



    @Test
    @WithMockUser(username = "lorenchess", password = "abc123", roles = "CARD-OWNER")
    void shouldThrowAnExceptionWhenPassingNullAsOwner() throws Exception {
        CashCardDTO cashCardDTO = new CashCardDTO(null, 10.00, null);
        String cashCardDTOJson = mapper.writeValueAsString(cashCardDTO);


        mockMvc.perform(post("/cashcards").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(cashCardDTOJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.owner").value("Owner value should be set."));

    }

    @Test
    @WithMockUser(username = "lorenchess", password = "abc123", roles = "CARD-OWNER")
    void shouldAllowUserToCreateACashCardWhenPassingCredentialsAndOwner() throws Exception {
        CashCardDTO cashCardDTO = new CashCardDTO(null, 10.00, "lorenchess");
        String cashCardDTOJson = mapper.writeValueAsString(cashCardDTO);

        //mocking service layer to return not null from the service
        given(service.saveCashCard(any(CashCardDTO.class), any(Principal.class))).willReturn(cashCardDTO);

        mockMvc.perform(post("/cashcards").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(cashCardDTOJson))
                .andDo(print())
                .andExpect(status().isCreated());

    }


}
