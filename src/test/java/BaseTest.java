import java.util.Random;

public class BaseTest {

    public static String  BASE_URI = "https://studio-api.softr.io";
    public static String headerValidApiKey =  "khIbAyJIU5CIuh1oDuBRx1s49";
    public static String headerValidDomain = "jere237.softr.app";
    public static String deleteEndPoint = "/v1/api/users/";

    public String generateUserEmail (){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100000);
        String randomEmailUser = "User"+ randomInt +"@gmail.com";
        return randomEmailUser;}
}
