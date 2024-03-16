package telran.java51.book.dao;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Publisher;

public interface PublisherRepository {
	
	Stream<Publisher> findPublishersByAuthorsName(String authorName);

	Optional<Publisher> findById(String publisher);

	Publisher save(Publisher publisher);
}
