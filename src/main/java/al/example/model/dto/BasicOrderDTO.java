package al.example.model.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class BasicOrderDTO {

	private Long id;
	private Date submittedDate;
	private Date deadlineDate;
	private String code;
	private String statusName;
	private String username;

}
