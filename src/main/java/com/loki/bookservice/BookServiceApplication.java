package com.loki.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication

public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner createSomeBooks(BookRepository bookRepository) {
		return strings -> { Arrays.asList(new Book[]{new Book("Immortals of meluha","Amish tripathi"),new Book("Lost symbol","Dan brown"),new Book("Pet semetary","Stephen king")})
				.stream().forEach(book -> bookRepository.save(book));
		};
	}
}

@RefreshScope
@RestController
//@RequestMapping("/")
class BooController {

	@Value("${message}")
	private String msg;

	@RequestMapping("/message")
	public String getMessage() {

		return "This is "+msg;

	}

}

@RepositoryRestResource
interface BookRepository extends JpaRepository<Book,Long> {

	@RestResource(path = "by-name")
	Collection<Book> findByTitle(@Param("bt") String bt);

}

@Entity
class Book{
	@Id
	@GeneratedValue
	private long id;

	private String title;

	private String author;

	public Book() {
	}

	public Book(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				'}';
	}
}