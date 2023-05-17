import dto.DeleteUserResponse;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeleteUserTest {
    public static String  BASE_URI = "https://studio-api.softr.io";
    @Test
    public void deleteCreatedUser (){
        DeleteUserResponse response = given()
                .baseUri(BASE_URI)
                .header("Soft-Api-Key", "khIbAyJIU5CIuh1oDuBRx1s49")
                .header("Soft-Domain", "jere237.softr.app")
                .when().log().all()
                .contentType(ContentType.JSON)
                .delete("/v1/api/users/hkksdasedejh@gmail.com")
                .then().log().all()
                .statusCode(200)
                .extract().body().as(DeleteUserResponse.class);
    }
}
