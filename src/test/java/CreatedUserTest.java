

import dto.CreateUserRequest;
import dto.CreateUserResponse;
import dto.InvalidErrorResponse;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


public class CreatedUserTest {
    public static String  BASE_URI = "https://studio-api.softr.io";

    @Test public void getUser (){
        given().baseUri("https://studio-api.softr.io/v1/api")
                .when().log().all()
                .post("/users")
                .then().log().all();
    }
    @Test
    public void successfulCreatedUser (){
        CreateUserRequest request = new CreateUserRequest ("John Start", "Start@gmail.com", "12345Q", "null");
        CreateUserResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", "khIbAyJIU5CIuh1oDuBRx1s49")
                .header("Softr-Domain", "jere237.softr.app")
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201)
                .extract().body().as(CreateUserResponse.class);
                assertNotNull(response.getEmail());
                assertNotNull(response.getFull_name());
                assertNotNull(response.getCreated());
                assertNotNull(response.getUpdated());
                assertEquals(request.getFull_name(), response.getFull_name());
                assertEquals(request.getEmail(), response.getEmail());

    }
    @Test
    public void createdUserWithoutEmail (){
        CreateUserRequest request = new CreateUserRequest ("John Start", " ", "12345Q", "null");
        InvalidErrorResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", "khIbAyJIU5CIuh1oDuBRx1s49")
                .header("Softr-Domain", "jere237.softr.app")
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(400)
                .extract().body().as(InvalidErrorResponse.class);
                assertEquals("Something went wrong, please try again.", response.getMessage());
                assertEquals("Bad Request", response.getCode());
    }

    @Test
    public void createdUserWithoutFullName (){
        CreateUserRequest request = new CreateUserRequest (" ", "test123@gmail.com", "12345Q", "null");
        InvalidErrorResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", "khIbAyJIU5CIuh1oDuBRx1s49")
                .header("Softr-Domain", "jere237.softr.app")
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201)
                .extract().body().as(InvalidErrorResponse.class);

    }
    @Test
    public void createdUserWithoutPassword (){
        CreateUserRequest request = new CreateUserRequest ("John Start", "test123@gmail.com", "", "null");
        InvalidErrorResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", "khIbAyJIU5CIuh1oDuBRx1s49")
                .header("Softr-Domain", "jere237.softr.app")
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(400)
                .extract().body().as(InvalidErrorResponse.class);
        assertEquals("Something went wrong, please try again.", response.getMessage());
        assertEquals("Bad Request", response.getCode());

    }
}
