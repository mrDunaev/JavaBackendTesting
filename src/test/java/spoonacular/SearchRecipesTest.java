package spoonacular;

import static io.restassured.RestAssured.given;

import extensions.SpoonApiTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@SpoonApiTest
public class SearchRecipesTest {
    String  type = "breakfast";
    String  includeIngredients = "banana,strawberry";
    Integer totalNumberOfRecipes = 5222;
    String  excludeIngredients = "sugar,salt";


    @Test
    public void bananaBreakfastTest() {
        given()
                .queryParam("type", type)
                .queryParam("includeIngredients", includeIngredients)
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .body("results[0].title", Matchers.equalTo("Berry Banana Breakfast Smoothie"));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void recipesNumberTest(int num) {
        given()
                .queryParam("number", num)
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .body("number", Matchers.equalTo(num));
    }

    @Test
    public void totalNumberOfRecipesTest() {
        given()
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .body("totalResults", Matchers.equalTo(totalNumberOfRecipes));
    }

    @ParameterizedTest
    @CsvSource({
            "dessert, 20",
            "drink, 59"
    })
    public void healthyRecipesTest(String recipesType, int recipesNumber) {
        given()
                .queryParam("type", recipesType)
                .queryParam("excludeIngredients", excludeIngredients)
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .body("totalResults", Matchers.equalTo(recipesNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"pasta","burger"})
    public void wordSearchTest(String query) {
        given()
                .queryParam("query", query)
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .body("results[0].title", Matchers.containsStringIgnoringCase(query));
    }
}
