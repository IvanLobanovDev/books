package telran.java51.book.service;

import java.util.Set;
import java.util.stream.Collectors;

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
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		// Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
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
	@Transactional(readOnly = true)
	public Set<BookDto> findBooksByAuthor(String author) {
		return bookRepository.findBooksByAuthorsName(author).map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toSet());
//		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<BookDto> findBooksByPublisher(String publisher) {
		return bookRepository.findBooksByPublisherPublisherNameIgnoreCase(publisher)
				.map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toSet());
	}

	@Override
	@Transactional(readOnly = true)
	public Set<AuthorDto> findBookAuthors(String isbn) {
//		return authorRepository.findBookAuthors(isbn).map(a -> modelMapper.map(a, AuthorDto.class))
//				.collect(Collectors.toSet());
		Book book = bookRepository.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toSet());
	}

	@Override
	public Set<String> findPublishersByAuthor(String authorName) {
		return publisherRepository.findPublishersByAuthor(authorName);
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String author) {
		Author authorVictim = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
//		bookRepository.findBooksByAuthorsName(author).forEach(b -> b.getAuthors().clear());
		bookRepository.deleteByAuthorsName(author);
		authorRepository.delete(authorVictim);
		return modelMapper.map(authorVictim, AuthorDto.class);
	}

}

//Допилить все.
//Remove author будет заданием со звездочкой
//find publisher by author будет заданием со звездочкой
