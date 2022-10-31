package al.example.exception;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import al.example.model.ResponseWrapper;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = { GeneralException.class })
	public void commence(HttpServletRequest request, HttpServletResponse response, GeneralException exception)
			throws IOException {

		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(),
				new ResponseWrapper<>(false, exception.getContent(), exception.getMessage()));
	}

}
