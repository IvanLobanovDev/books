package telran.java51.book.model;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode(of ="publisherName")
@Entity
public class Publisher implements Serializable{
	
	private static final long serialVersionUID = -1880684893083322276L;
	
	@Id
	String publisherName;
//	на стороне родительской сущности обязательно указывается название поля с которым оно должно быть связано
	@OneToMany(mappedBy = "publisher")
	Set<Book> books;
	
	public Publisher(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public String toString() {
		return publisherName;
	}

}
