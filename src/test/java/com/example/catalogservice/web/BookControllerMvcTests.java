package com.example.catalogservice.web;

import com.example.catalogservice.domain.BookNotFoundException;
import com.example.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void whenGetBookNotFound_thenReturns404() throws Exception {

        given(bookService.viewBookDetails("12345"))
                .willThrow(new BookNotFoundException("Book not found"));

        mockMvc.perform(get("/books/12345"))
                .andExpect(status().isNotFound());
    }
}
