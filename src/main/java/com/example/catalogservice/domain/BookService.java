package com.example.catalogservice.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository repository;

    BookService(BookRepository repository) {
        this.repository = repository;
    }

    public static String getIsbnAlreadyExistsMessage(String isbn) {
        return "Book with ISBN '" + isbn + "' already exists";
    }

    public static String getIsbnNotFoundMessage(String isbn) {
        return "Book with ISBN '" + isbn + "' not found";
    }

    public Iterable<Book> viewBookList() {
        return repository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return repository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(getIsbnNotFoundMessage(isbn)));
    }

    public Book addBookToCatalog(Book book) {
        if (repository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(getIsbnAlreadyExistsMessage(book.isbn()));
        }
        repository.save(book);
        return book;
    }

    public Book editBookDetails(String isbn, Book book) {
        return repository.findByIsbn(isbn)
                .map(existingBook -> {
                    var bookToUpdate = new Book(
                            existingBook.id(),
                            existingBook.isbn(),
                            book.title(),
                            book.author(),
                            book.price(),
                            existingBook.createdDate(),
                            existingBook.lastModifiedDate(),
                            existingBook.version()
                    );
                    return repository.save(bookToUpdate);
                }).orElseGet(() -> repository.save(book));
    }

    public void deleteByIsbn(String isbn) {
        repository.findByIsbn(isbn).ifPresent(repository::delete);
    }
}
