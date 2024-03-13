package telran.java51.book.dao;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.java51.book.model.Author;

public interface AuthorRepository extends JpaRepository<Author, String> {
	
	
//	@Query("select a from Author a join a.book b where b.isbn = ?1")
//	@Query("select a from Book b join b.authors a where b.isbn = ?1")
//	Stream<Author> findBookAuthors(String isbn);

}
