package telran.java51.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
	
	@Query("select p from Book b join b.authors a join b.publisher p where lower(a.name) like lower(?1)")
	Stream<Publisher> findPublisherByAuthor(String author);

}
