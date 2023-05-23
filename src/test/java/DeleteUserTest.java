import dto.CreateUserRequest;
import dto.InvalidDeleteUserResponse;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeleteUserTest extends BaseTest {





    @Test
    public void deleteCreatedUser (){
        String generatedEmail = generateUserEmail();
        CreateUserRequest request = new CreateUserRequest ("John Start", generatedEmail, "12345Q", "null");
         given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", headerValidApiKey)
                .header("Softr-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all();


         given()
                .baseUri(BASE_URI)
                .header("Soft-Api-Key", headerValidApiKey)
                .header("Soft-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete(deleteEndPoint + request.getEmail())
                .then().log().all()
                .statusCode(200);

    }

    @Test
    public void secondTimeDeleteUser (){
        String generatedEmail = generateUserEmail();
        CreateUserRequest request = new CreateUserRequest ("John Start", generatedEmail, "12345Q", "null");

        given()
                .baseUri(BASE_URI)
                .body(request)
                .header("Softr-Api-Key", headerValidApiKey)
                .header("Softr-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .post("/v1/api/users")
                .then().log().all();


        given()
                .baseUri(BASE_URI)
                .header("Soft-Api-Key", headerValidApiKey)
                .header("Soft-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete(deleteEndPoint + request.getEmail())
                .then().log().all()
                .statusCode(200);

         InvalidDeleteUserResponse response = given()
                .baseUri(BASE_URI)
                .header("Soft-Api-Key", headerValidApiKey)
                .header("Soft-Domain", headerValidDomain)
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete(deleteEndPoint + request.getEmail())
                .then().log().all()
                .statusCode(404)
                 .extract().body().as(InvalidDeleteUserResponse.class);
      assertEquals("Not found", response.getCode());
      assertEquals("User with email:" + request.getEmail()+ "not found", response.getMessage());

    }


}
