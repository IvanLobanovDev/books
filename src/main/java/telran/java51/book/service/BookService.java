package telran.java51.book.service;

import java.util.Set;

import telran.java51.book.dto.AuthorDto;
import telran.java51.book.dto.BookDto;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto removeBook(String isbn);
	
	BookDto updateBookTitle(String isbn, String title);
	
	Set<BookDto> findBooksByAuthor(String author);
	
	Set<BookDto> findBooksByPublisher(String publisher);
	
	Set<AuthorDto> findBookAuthors(String isbn);
	
	Set<String> findPublishersByAuthor(String author);
	
	AuthorDto removeAuthor(String author);

}
