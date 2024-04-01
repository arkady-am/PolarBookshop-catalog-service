package com.example.catalogservice.web;

import com.example.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {
    @Autowired
    JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        Book book = new Book("1234", "Title", "Author", 9.99);
        assertThat(json.write(book)).extractingJsonPathStringValue("@.isbn").isEqualTo("1234");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.title").isEqualTo("Title");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.author").isEqualTo("Author");
        assertThat(json.write(book)).extractingJsonPathNumberValue("@.price").isEqualTo(9.99);
    }

    @Test
    void testDeserialize() throws Exception {
        String content = "{\"isbn\":\"1234\",\"title\":\"Title\",\"author\":\"Author\",\"price\":9.99}";
        Book book = new Book("1234", "Title", "Author", 9.99);
        assertThat(json.parse(content)).isEqualTo(book);
        assertThat(json.parseObject(content).isbn()).isEqualTo("1234");
        assertThat(json.parseObject(content).title()).isEqualTo("Title");
        assertThat(json.parseObject(content).author()).isEqualTo("Author");
        assertThat(json.parseObject(content).price()).isEqualTo(9.99);
    }
}
