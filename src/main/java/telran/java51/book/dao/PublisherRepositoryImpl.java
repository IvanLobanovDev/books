package telran.java51.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import telran.java51.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {

//	позволяет создавать не 1 EntityManager, а для каждого контекста персистентности
	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Publisher> findPublishersByAuthorsName(String authorName) {
		Stream<Publisher> publishers = em
//				.createQuery("SELECT distinct b.publisher from Book b where lower(b.authors.name) = lower(?1)")
				.createQuery("SELECT DISTINCT p FROM Publisher p JOIN p.books b JOIN b.authors a WHERE LOWER(a.name) = LOWER(?1)")
				.setParameter(1, authorName).getResultStream();
		return publishers;
	}

	@Override
	public Optional<Publisher> findById(String publisher) {
		publisher = publisher.toLowerCase();
		return Optional
				.ofNullable(em.find(Publisher.class, publisher.substring(0, 1).toUpperCase() + publisher.substring(1)));
	}

	@Override
//	@Transactional
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
//		em.merge(publisher);
		return publisher;
	}

}
