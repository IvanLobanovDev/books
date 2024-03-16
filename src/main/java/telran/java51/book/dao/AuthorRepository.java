package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Author;
import telran.java51.book.model.Publisher;

public interface AuthorRepository {
	
	Optional<Author> findById(String authorName);
	
	Author save(Author author);

//	void delete(Author author);

	void deleteById(String author);

}
