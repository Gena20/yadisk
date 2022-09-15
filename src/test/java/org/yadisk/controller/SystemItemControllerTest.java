package org.yadisk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.yadisk.dto.SystemItemImport;
import org.yadisk.dto.SystemItemImportRequest;
import org.yadisk.entity.SystemItemType;
import org.yadisk.service.SystemItemService;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SystemItemControllerTest {
    public static final String rootId = UUID.randomUUID().toString();

    @MockBean
    private SystemItemService service;

    @Autowired
    private MockMvc mvc = standaloneSetup(new SystemItemController(service))
            .alwaysExpect(content().contentType(MediaType.APPLICATION_JSON))
            .build();

    public static String[] validData() throws JsonProcessingException {
        var objectMapper = (new ObjectMapper()).registerModule(new JavaTimeModule());
        var rootItem = new SystemItemImport(rootId, null, SystemItemType.FOLDER, null, null);
        var item = new SystemItemImport(UUID.randomUUID().toString(), rootId, SystemItemType.FILE, "/some/path", 10L);

        var request = new SystemItemImportRequest(Arrays.asList(rootItem, item), Instant.now());

        return new String[]{objectMapper.writeValueAsString(request)};
    }

    @Order(1)
    @ParameterizedTest
    @MethodSource("validData")
    public void givenValidData_whenImport_thenUpdateItems(String payload) throws Exception {
        mvc.perform(post("/imports").contentType(MediaType.APPLICATION_JSON).content(payload))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void givenExistingItem_whenNodes_thenReturnNodes() throws Exception {
        mvc.perform(get("/nodes/%s".formatted(rootId)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(10));
    }

    @Order(3)
    @Test
    public void givenExistingItem_whenDelete_thenDeleteIt() throws Exception {
        mvc.perform(delete("/delete/%s".formatted(rootId)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    public void givenNonExistingItem_whenNodes_thenReturnNotFoundError() throws Exception {
        mvc.perform(get("/nodes/%s".formatted(rootId)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
