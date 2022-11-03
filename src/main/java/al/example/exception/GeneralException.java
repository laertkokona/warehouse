package al.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class GeneralException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5237459523064592126L;
	private String message;
    private Object content;

}
