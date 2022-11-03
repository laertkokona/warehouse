package al.example.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseWrapper<T> {
	
	private Boolean status;
	private T content;
	private String message;

}
