package com.example.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookValidationTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenAllFieldsAreValid_thenValidationSucceeds() {
        Book book = new Book("9781234567897", "Title", "Author", 19.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenIsbnIsBlank_thenValidationFails() {
        Book book = new Book("", "Title", "Author", 19.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        List<String> violationMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        assertThat(violations.size()).isEqualTo(2);
        assertThat(violationMessages).contains(
                Book.MESSAGE_ISBN_MANDATORY,
                Book.MESSAGE_ISBN_VALID
        );
    }

    @Test
    public void whenIsbnIsInvalid_thenValidationFails() {
        Book book = new Book("invalid", "Title", "Author", 19.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(Book.MESSAGE_ISBN_VALID);
    }

    @Test
    public void whenAuthorIsBlank_thenValidationFails() {
        Book book = new Book("9781234567897", "Title", "", 19.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(Book.MESSAGE_AUTHOR_MANDATORY);
    }

    @Test
    public void whenTitleIsBlank_thenValidationFails() {
        Book book = new Book("9781234567897", "", "Author", 19.99);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(Book.MESSAGE_TITLE_MANDATORY);
    }

    @Test
    public void whenPriceIsNull_thenValidationFails() {
        Book book = new Book("9781234567897", "Title", "Author", null);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(Book.MESSAGE_PRICE_MANDATORY);
    }

    @Test
    public void whenPriceIsBelowZero_thenValidationFails() {
        Book book = new Book("9781234567897", "Title", "Author", -1.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(Book.MESSAGE_PRICE_POSITIVE);
    }


}