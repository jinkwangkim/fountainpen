package com.kwang23.fountainpen.keyword.adapter.in;

import com.kwang23.fountainpen.keyword.application.port.in.KeyWordCommandService;
import com.kwang23.fountainpen.keyword.application.port.out.KeyWordSearchRedisPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(KeyWordSearchApiController.class)
class KeyWordSearchApiControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    KeyWordCommandService keyWordCommandService;
    @MockBean
    KeyWordSearchRedisPort keyWordSearchRedisPort;
    @BeforeEach
    void setUp() {
    }

    @Test
    void getSearchWordList() throws Exception {
        KeyWordSearchDto keyWordSearchDto = new KeyWordSearchDto("keyWord", 10, LocalDate.now());
        when(keyWordCommandService.getSearchWordList(10)).thenReturn(List.of(keyWordSearchDto));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/search/word")
                        .queryParam("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].keyWord", is(keyWordSearchDto.getKeyWord())))
                .andExpect(jsonPath("$[0].frequency").value(equalTo(keyWordSearchDto.getFrequency()), Long.class));
    }

    @Test
    void getSearchWordList_invalid_size() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/search/word")
                        .queryParam("size", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}