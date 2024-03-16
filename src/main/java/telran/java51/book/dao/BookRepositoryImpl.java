package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@Repository
public class BookRepositoryImpl implements BookRepository {

//	позволяет создавать не 1 EntityManager, а для каждого контекста персистентности
	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Book> findBooksByAuthorsName(String author) {
		Stream<Book> books = em.createQuery("SELECT a.books from Author a where lower(a.name) = lower(?1)")
				.setParameter(1, author).getResultStream();
		return books;
	}

	@Override
	public Stream<Book> findBooksByPublishersName(String publisher) {
		Stream<Book> books = em.createQuery("SELECT p.books from Publisher p where lower(p.publisherName) = lower(?1)")
				.setParameter(1, publisher).getResultStream();
		return books;
	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		isbn = isbn.toLowerCase();
		return Optional.ofNullable(em.find(Book.class, isbn.substring(0, 1).toUpperCase() + isbn.substring(1)));
	}

	@Override
	public void delete(Book book) {
		em.remove(book);
	}

}
