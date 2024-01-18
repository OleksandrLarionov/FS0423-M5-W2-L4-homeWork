package larionov.API.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice // <-- Controller specifico per le eccezioni
// Questa classe dovrà quindi catturare le eccezioni provenienti dai nostri endpoints
// Implementerò un metodo per ogni eccezione da gestire
public class ExceptionsHandler {
	@ExceptionHandler(BadRequestException.class)
	// Indico che questo metodo gestirà le eccezioni di tipo BadRequestException
	@ResponseStatus(HttpStatus.BAD_REQUEST) // Dovrà rispondere con un 400
	public ErrorsPayload handleBadRequest(BadRequestException ex) {
		return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(NotFoundException.class)
	// Indico che questo metodo gestirà le eccezioni di tipo NotFoundException
	@ResponseStatus(HttpStatus.NOT_FOUND) // Dovrà rispondere con un 404
	public ErrorsPayload handleNotFound(NotFoundException ex) {
		return new ErrorsPayload(ex.getMessage(), LocalDateTime.now());
	}

	@ExceptionHandler(Exception.class)
	// Tutti gli altri tipi di eccezioni (come errori vari nel codice) verranno gestiti da questo metodo
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Dovrà rispondere con un 500
	public ErrorsPayload handleGenericError(Exception ex) {
		ex.printStackTrace();
		return new ErrorsPayload("Problema lato server! Giuro che risolveremo presto!", LocalDateTime.now());
	}
}
