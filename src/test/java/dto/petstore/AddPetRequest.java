package dto.petstore;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPetRequest{
	private int id;
	private Category category;
	private String name;
	private List<String> photoUrls;
	private List<TagsItem> tags;
	private String status;
}