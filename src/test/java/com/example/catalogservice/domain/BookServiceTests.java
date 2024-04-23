package com.example.catalogservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void whenBookToCreateAlreadyExists_thenThrowException() {
        String isbn = "1234";
        Book book = Book.of(isbn, "Title", "Author", "Publisher", 9.99);
        when(bookRepository.existsByIsbn(isbn)).thenReturn(true);
        assertThatThrownBy(() -> bookService.addBookToCatalog(book))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessageContaining(BookService.getIsbnAlreadyExistsMessage(isbn));
    }

    @Test
    public void whenBookToReadAlreadyExists_thenThrowException() {
        String isbn = "1234";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.viewBookDetails(isbn))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining(BookService.getIsbnNotFoundMessage(isbn));
    }
}
