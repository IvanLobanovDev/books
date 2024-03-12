package telran.java51.book.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//RuntimeException используется, чтобы сделать unchecked, чтобы не требовал трай/кетчи
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2469978048019647545L;

}
