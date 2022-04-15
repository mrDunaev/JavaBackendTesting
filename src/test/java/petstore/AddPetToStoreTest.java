package petstore;

import extensions.PetStoreApiTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static io.restassured.RestAssured.given;

@PetStoreApiTest
public class AddPetToStoreTest {
    private int id;

    @ParameterizedTest
    @CsvSource({
            "Shelby, dog, multicolored",
            "Ryjik, cat, red"
    })
    void addPetToStoreTest(String name, String category, String colour) {
        Random random = new Random();
        id = given()
                .body("{\n" +
                        "  \"id\": " + random.nextInt(1000000) + ",\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"" + category + "\"\n" +
                        "  },\n" +
                        "  \"name\": \"" + name + "\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"" + colour + "\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .post("/pet")
                .then()
                .statusCode(200)
                .body("category.name", Matchers.equalTo(category))
                .body("name", Matchers.equalTo(name))
                .body("tags.name[0]", Matchers.equalTo(colour))
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @AfterEach
    void tearDown() {
        given()
                .delete("/pet/{petId}", id)
                .then()
                .statusCode(200);

        given()
                .get("/pet/{petId}", id)
                .then()
                .statusCode(404);
    }
}
