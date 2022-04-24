package minimarket.endpoints;

import retrofit2.Call;
import retrofit2.http.*;
import minimarket.dto.ProductDto;

public interface ProductsService {

    @POST("products")
    Call<ProductDto> createProduct(@Body ProductDto productDto);

    @PUT("products")
    Call<ProductDto> changeProduct(@Body ProductDto productDto);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") int id);

}
