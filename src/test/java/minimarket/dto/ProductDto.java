package minimarket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("categoryTitle")
    private String categoryTitle;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;
}