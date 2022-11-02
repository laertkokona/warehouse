package al.example.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Pagination {
	
	private Integer pageSize = 10;
	private Integer pageNumber = 0;
	private String sortByProperty = "id";
	private Boolean sortByAsc = true;

}
