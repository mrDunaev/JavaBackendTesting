package minimarket.endpoints;

import minimarket.dto.CategoryDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {

    @GET("categories/{id}")
    Call<CategoryDto> getCategory(@Path("id") int id);
}
