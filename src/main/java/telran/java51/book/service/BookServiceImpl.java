package telran.java51.book.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.usertype.UserCollectionType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.book.dao.AuthorRepository;
import telran.java51.book.dao.BookRepository;
import telran.java51.book.dao.PublisherRepository;
import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;
import telran.java51.book.dto.exceptions.EntityNotFoundException;
import telran.java51.book.model.Author;
import telran.java51.book.model.Book;
import telran.java51.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	final BookRepository bookRepository;
	final PublisherRepository publisherRepository;
	final AuthorRepository authorRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if (bookRepository.existsById(bookDto.getIsbn())) {
			return false;
		}
		// Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
//				orElseGet - если нет, вызови функцию
				.orElseGet(() -> publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElseGet(() -> authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());

		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookRepository.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookRepository.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto updateBookTitle(String isbn, String title) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
//	@Transactional(readOnly = true)
	public Set<BookDto> findBooksByAuthor(String authorName) {
//		1st solution
		Author author = authorRepository.findById(authorName).orElseThrow(EntityNotFoundException::new);
		return author.getBooks().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toSet());
//		2nd solution
//		return bookRepository.findBooksByAuthorsName(authorName).map(b -> modelMapper.map(b, BookDto.class))
//				.collect(Collectors.toSet());
	}

	@Override
//	@Transactional(readOnly = true)
	public Set<BookDto> findBooksByPublisher(String publisherName) {
//		1st solution
		Publisher publisher = publisherRepository.findById(publisherName).orElseThrow(EntityNotFoundException::new);
		return publisher.getBooks().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toSet());
//		2nd solution
//		return bookRepository.findBooksByPublishersName(publisherName).map(b -> modelMapper.map(b, BookDto.class))
//				.collect(Collectors.toSet());
	}

	@Override
	public Set<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toSet());
	}

	@Override
	public Set<String> findPublishersByAuthor(String authorName) {
		return publisherRepository.findPublishersByAuthorsName(authorName).map(Publisher::getPublisherName)
				.collect(Collectors.toSet());
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String author) {
		Author authorVictim = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
		authorRepository.deleteById(author);
		return modelMapper.map(authorVictim, AuthorDto.class);
	}

}
