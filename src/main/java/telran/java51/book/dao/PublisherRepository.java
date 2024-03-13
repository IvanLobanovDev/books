package telran.java51.book.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
	
//	@Query("select p from Book b join b.authors a join b.publisher p where lower(a.name) like lower(?1)")
//	Stream<Publisher> findPublisherByAuthor(String author);
	
	@Query("select distinct p.publisherName from Book b join b.authors a join b.publisher p where lower(a.name)=lower(?1)")
	Set<String> findPublishersByAuthor(String authorName);
	//так же можно получить уникальные значения, указав в запросе distinct
}
