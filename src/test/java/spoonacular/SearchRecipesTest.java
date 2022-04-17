package spoonacular;

import static io.restassured.RestAssured.given;

import extensions.SpoonApiTest;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@SpoonApiTest
public class SearchRecipesTest extends BaseSpoonTest {
    private static ResponseSpecification successfulResponse;

    private final static String TYPE_KEY = "type";
    private final static String INCLUDE_INGREDIENTS_KEY = "includeIngredients";
    private final static String NUMBER_KEY = "number";
    private final static String EXCLUDE_INGREDIENTS_KEY = "excludeIngredients";
    private final static String QUERY_KEY = "query";
    private final static String TOTAL_RESULTS_KEY = "totalResults";
    private final static String RESULTS_TITLE_KEY = "results[0].title";

    private final static Integer TOTAL_NUMBER_OF_RECIPES = 5222;

    @BeforeAll
    static void beforeAll() {
        successfulResponse = new ResponseSpecBuilder()
                .expectStatusCode(SUCCESS_STATUS_CODE)
                .build();
    }

    @ParameterizedTest
    @CsvSource({
            "breakfast, 'banana,strawberry', 'Berry Banana Breakfast Smoothie'"
    })
    public void bananaBreakfastTest(String recipeType, String includeIngredients, String recipeName) {
        given()
                .queryParam(TYPE_KEY, recipeType)
                .queryParam(INCLUDE_INGREDIENTS_KEY, includeIngredients)
                .get(SEARCH_RECIPES)
                .then()
                .spec(successfulResponse)
                .body(RESULTS_TITLE_KEY, Matchers.equalTo(recipeName));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    public void recipesNumberTest(int number) {
        given()
                .queryParam(NUMBER_KEY, number)
                .get(SEARCH_RECIPES)
                .then()
                .spec(successfulResponse)
                .body(NUMBER_KEY, Matchers.equalTo(number));
    }

    @Test
    public void totalNumberOfRecipesTest() {
        given()
                .get(SEARCH_RECIPES)
                .then()
                .spec(successfulResponse)
                .body(TOTAL_RESULTS_KEY, Matchers.equalTo(TOTAL_NUMBER_OF_RECIPES));
    }

    @ParameterizedTest
    @CsvSource({
            "dessert, 'sugar,salt', 20",
            "drink, 'sugar,salt', 59"
    })
    public void healthyRecipesTest(String recipesType, String excludeIngredients, int recipesNumber) {
        given()
                .queryParam(TYPE_KEY, recipesType)
                .queryParam(EXCLUDE_INGREDIENTS_KEY, excludeIngredients)
                .get(SEARCH_RECIPES)
                .then()
                .spec(successfulResponse)
                .body(TOTAL_RESULTS_KEY, Matchers.equalTo(recipesNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {"pasta","burger"})
    public void wordSearchTest(String query) {
        given()
                .queryParam(QUERY_KEY, query)
                .get(SEARCH_RECIPES)
                .then()
                .spec(successfulResponse)
                .body(RESULTS_TITLE_KEY, Matchers.containsStringIgnoringCase(query));
    }
}
