import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {

    @Test
    public void getRequestTest() {
        given()
                .baseUri("https://postman-echo.com")
                .queryParam("foo", "bar")
                .when()
                .get("/get")
                .then()
                .log().all()
                .statusCode(200)
                .body("args.foo", equalTo("bar"));
    }

    @Test
    public void postRequestTest() {
        String requestBody = "{\"name\": \"Elizaveta\", \"job\": \"QA Automation\"}";

        given()
                .baseUri("https://postman-echo.com")
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("json.name", equalTo("Elizaveta"))
                .body("json.job", equalTo("QA Automation"));
    }
}


