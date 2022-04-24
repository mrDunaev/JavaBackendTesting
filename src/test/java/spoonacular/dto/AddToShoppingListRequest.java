package spoonacular.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToShoppingListRequest {
    private String item;
    private String aisle;
    private boolean parse;
}
