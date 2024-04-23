package com.example.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Book(
        @Id
        Long id,
        @NotBlank(message = Book.MESSAGE_ISBN_MANDATORY)
        @Pattern(regexp = Book.PATTERN_ISBN, message = Book.MESSAGE_ISBN_VALID)
        String isbn,
        @NotBlank(message = Book.MESSAGE_TITLE_MANDATORY)
        String title,
        @NotBlank(message = Book.MESSAGE_AUTHOR_MANDATORY)
        String author,
        @NotNull(message = Book.MESSAGE_PRICE_MANDATORY)
        @Positive(message = Book.MESSAGE_PRICE_POSITIVE)
        Double price,
        @Version
        int version
) {
    public static final String PATTERN_ISBN = "^(97([89]))?\\d{9}(\\d|X)$";
    public static final String MESSAGE_ISBN_MANDATORY = "ISBN is mandatory";
    public static final String MESSAGE_ISBN_VALID = "ISBN must be valid";
    public static final String MESSAGE_TITLE_MANDATORY = "Title is mandatory";
    public static final String MESSAGE_AUTHOR_MANDATORY = "Author is mandatory";
    public static final String MESSAGE_PRICE_MANDATORY = "Price is mandatory";
    public static final String MESSAGE_PRICE_POSITIVE = "Price must be greater than zero";

    public static Book of(
            String isbn,
            String title,
            String author,
            Double price
    ) {
        return new Book(null, isbn, title, author, price, 0);
    }
}
