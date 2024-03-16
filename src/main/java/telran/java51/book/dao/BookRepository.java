package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Author;
import telran.java51.book.model.Book;

public interface BookRepository {

	Stream<Book> findBooksByAuthorsName(String author);
	
	Stream<Book> findBooksByPublishersName(String publisher);

	boolean existsById(String isbn);
	
	Book save(Book book);
	
	Optional<Book> findById(String isbn);

	void delete(Book book);

}
