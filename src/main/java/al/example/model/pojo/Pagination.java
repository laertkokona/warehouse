package al.example.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Pagination {
	
	private Integer pageSize = 1;
	private Integer pageNumber = 10;
	private String sortByProperty = "id";
	private Boolean sortByAsc = true;

}
