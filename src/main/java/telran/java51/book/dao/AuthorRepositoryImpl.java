package telran.java51.book.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.dto.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Publisher;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

//	позволяет создавать не 1 EntityManager, а для каждого контекста персистентности
	@PersistenceContext
	EntityManager em;
	
	@Override
	public Optional<Author> findById(String authorName) {
		authorName = authorName.toLowerCase();
		return Optional.ofNullable(em.find(Author.class, authorName.substring(0, 1).toUpperCase() + authorName.substring(1)));
	}

	@Override
//	@Transactional
	public Author save(Author author) {
		em.persist(author);
//		em.merge(author);
		return author;
	}

	@Override
	public void deleteById(String author) {
		em.remove(findById(author).orElseThrow(EntityNotFoundException::new));
	}

}
