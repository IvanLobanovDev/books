package telran.java51.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Book;

public interface BookRepository extends JpaRepository<Book, String> {

	@Query("select b from Book b join b.authors a where lower(a.name) like lower(?1)")
	Stream<Book> findBooksByAuthor(String author);
	
	Stream<Book> findBooksByPublisherPublisherNameIgnoreCase(String publisher);

}
