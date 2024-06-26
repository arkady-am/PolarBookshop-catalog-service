package com.example.catalogservice;

import com.example.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetRequestToBooks_thenBooksReturned() {

        var isbn = "1234567890";
        var bookToCreate = Book.of(isbn, "Title", "Author", "Publisher", 9.99);

        Book expectedBook = webTestClient.post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> assertThat(actualBook).isNotNull())
                .returnResult().getResponseBody();

        webTestClient.get()
                .uri("/books/" + isbn)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenPostRequestToBooks_thenBookCreated() {

        var isbn = "1234567891";
        var bookToCreate = Book.of(isbn, "Title", "Author", "Publisher", 9.99);

        webTestClient.post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> {
                    assertThat(book).isNotNull();
                    assertThat(book.isbn()).isEqualTo(isbn);
                });
    }

    @Test
    void whenPutRequestToBooks_thenBookUpdated() {

        var isbn = "1234567892";
        var bookToCreate = Book.of(isbn, "Title", "Author", "Publisher", 9.99);
        var expectedBook = Book.of(isbn, "Updated Title", "Updated Author", "Publisher", 1.23);

        webTestClient.post()
                .uri("/books")
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(book -> {
                    assertThat(book).isNotNull();
                    assertThat(book.isbn()).isEqualTo(isbn);
                });

        webTestClient.put()
                .uri("/books/" + isbn)
                .bodyValue(expectedBook)
                .exchange()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.title()).isEqualTo(expectedBook.title());
                    assertThat(actualBook.author()).isEqualTo(expectedBook.author());
                    assertThat(actualBook.price()).isEqualTo(expectedBook.price());
                });
    }
}
