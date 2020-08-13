package com.example.demo;

import com.example.demo.dto.BookDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_create_book_is_ok() throws Exception {
        BookDto dto = new BookDto();
        dto.bookName = "Book-1";
        dto.description = "description-1";
        mockMvc.perform(post("/api/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void should_get_books_is_ok() throws Exception {
        mockMvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void should_get_books_by_parameter_is_ok() throws Exception {
        BookDto dto = new BookDto();
        dto.bookName = "Test1";
        dto.description = "description-test1";
        mockMvc.perform(post("/api/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(get("/api/books?bookName=Test1")).andDo(print()).andExpect(status().isOk());
        mockMvc.perform(get("/api/books?description=description-test1")).andDo(print()).andExpect(status().isOk());
    }
}
