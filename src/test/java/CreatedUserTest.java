

import dto.CreateUserRequest;
import dto.CreateUserResponse;
import dto.InvalidErrorResponse;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


public class CreatedUserTest extends BaseTest {


    @Test public void getUser (){
        given().baseUri("https://studio-api.softr.io/v1/api")
                .when().log().all()
                .post("/users")
                .then().log().all();
    }
    @Test
    public void successfulCreatedUser (){
        CreateUserRequest request = new CreateUserRequest ("John Start", "Testq1@gmail.com", "12345Q", "null");
        CreateUserResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", headerValidApiKey)
                .header("Softr-Domain", headerValidDomain)
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


         given()
                .baseUri(BASE_URI)
                .header("Soft-Api-Key", headerValidApiKey)
                .header("Soft-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("/v1/api/users/"+ request.getEmail())
                .then().log().all()
                .statusCode(200);

    }
    @Test
    public void createdUserWithoutEmail (){
        CreateUserRequest request = new CreateUserRequest ("John Start", " ", "12345Q", "true");
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
                .header("Softr-Api-Key", headerValidApiKey)
                .header("Softr-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(400)
                .extract().body().as(InvalidErrorResponse.class);

    }



    @Test
    public void createUserWithoutPassword (){
        String generateEmail = generateUserEmail();
        CreateUserRequest request = CreateUserRequest.builder()
                .full_name("John Start")
                .email(generateEmail)
                .generate_magic_link("false").build();
        CreateUserResponse response = given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", headerValidApiKey)
                .header("Softr-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all()
                .statusCode(201)
                .extract().body().as(CreateUserResponse.class);

        given()
                .baseUri(BASE_URI)
                .header("Soft-Api-Key", headerValidApiKey)
                .header("Soft-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("/v1/api/users/"+ request.getEmail())
                .then().log().all()
                .statusCode(200);
    }
}
