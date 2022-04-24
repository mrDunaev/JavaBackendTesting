package petstore;

import petstore.dto.AddPetRequest;
import petstore.dto.Category;
import petstore.dto.TagsItem;
import petstore.extensions.PetStoreApiTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

@PetStoreApiTest
public class AddPetToStoreTest {
    private final static String ADD_PET = "/pet";
    private final static String GET_PET = "/pet/{petId}";

    private final static String CATEGORY_NAME_KEY = "category.name";
    private final static String PET_NAME_KEY = "name";
    private final static String FIRST_TAG_NAME_KEY = "tags.name[0]";
    private final static String ID_KEY = "id";

    private final static int SUCCESS_STATUS_CODE = 200;
    private final static int NOT_FOUND_STATUS_CODE = 404;

    private int id;

    @ParameterizedTest
    @CsvSource({
            "Shelby, 1, dog, multicolored",
            "Ryjik, 2, cat, red"
    })
    void addPetToStoreTest(String nameValue, int categoryId, String categoryValue, String colourValue) {
        int colourTagId = 1;
        Random random = new Random();
        Category category = new Category(categoryId, categoryValue);
        List<TagsItem> tags = new ArrayList<>();
        tags.add(new TagsItem(colourTagId, colourValue));

        id = given()
                .body(AddPetRequest.builder()
                        .id(random.nextInt(1000000))
                        .category(category)
                        .name(nameValue)
                        .tags(tags)
                        .build())
                .post(ADD_PET)
                .then()
                .statusCode(SUCCESS_STATUS_CODE)
                .body(CATEGORY_NAME_KEY, Matchers.equalTo(categoryValue))
                .body(PET_NAME_KEY, Matchers.equalTo(nameValue))
                .body(FIRST_TAG_NAME_KEY, Matchers.equalTo(colourValue))
                .extract()
                .jsonPath()
                .getInt(ID_KEY);
    }

    @AfterEach
    void tearDown() {
        given()
                .delete(GET_PET, id)
                .then()
                .statusCode(SUCCESS_STATUS_CODE);

        given()
                .get(GET_PET, id)
                .then()
                .statusCode(NOT_FOUND_STATUS_CODE);
    }
}
