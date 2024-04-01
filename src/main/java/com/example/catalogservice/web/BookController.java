package com.example.catalogservice.web;

import com.example.catalogservice.domain.Book;
import com.example.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {
    private final BookService service;

    BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Book> index() {
        return service.viewBookList();
    }

    @GetMapping("/books/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public Book read(@PathVariable String isbn) {
        return service.viewBookDetails(isbn);
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book store(@Valid @RequestBody Book book) {
        return service.addBookToCatalog(book);
    }

    @DeleteMapping("/books/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIsbn(@PathVariable String isbn) {
        service.deleteByIsbn(isbn);
    }

    @PutMapping("/books/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateByIsbn(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return service.editBookDetails(isbn, book);
    }
}
