package telran.java51.book.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode(of ="name")
@Entity
public class Author implements Serializable{
	
	private static final long serialVersionUID = -8428841473039448125L;
	
	@Id
	String name;
	LocalDate birthDate;
//	на стороне родительской сущности обязательно указывается название поля с которым оно должно быть связано
//	cascade = CascadeType.REMOVE - настройка, которая позволит удаляя автора делать дополнительный запрос в БД, чтобы удалить книги
	@ManyToMany(mappedBy = "authors", cascade = CascadeType.REMOVE)
	Set<Book> books;
	
	public Author(String name, LocalDate birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}
	
}
