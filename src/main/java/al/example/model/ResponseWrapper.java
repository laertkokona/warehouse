package al.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseWrapper<T> {
	
	private Boolean status;
	private List<T> content;
	private String message;

}
