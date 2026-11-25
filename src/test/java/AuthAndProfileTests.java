import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class AuthAndProfileTests {

    private static final String BASE_URI = "https://dummyjson.com";

    @Test
    public void authAndUserProfileFlowTest() {

        String loginBody = """
                {
                    "username": "emilys",
                    "password": "emilyspass"
                }
                """;

        String accessToken = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("username", equalTo("emilys"))
                .body("accessToken", notNullValue())
                .extract()
                .path("accessToken"); // Вытаскиваем реальный JWT-токен

        System.out.println("Access token received: " + accessToken.substring(0, 20) + "...");


        given()
                .baseUri(BASE_URI)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/auth/me")
                .then()
                .statusCode(200)
                .body("username", equalTo("emilys"))
                .body("firstName", equalTo("Emily"))
                .body("email", equalTo("emily.johnson@x.dummyjson.com"));


        given()
                .baseUri(BASE_URI)
                .when()
                .get("/auth/me")
                .then()
                .statusCode(401);
    }
}
