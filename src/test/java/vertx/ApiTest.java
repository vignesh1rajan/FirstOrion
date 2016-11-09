package vertx;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;


/**
 * Created by vrajan on 11/8/2016.
 */
public class ApiTest {

    final String baseUri = "http://localhost:8080/";
    String basePath = "/products";

    @BeforeClass
    public void setUpTest(){
        RestAssured.baseURI = baseUri;
        RestAssured.basePath = basePath;

    }

    @Test
    public void getAll(){
        given().log().all()
               .when().get("/")
               .then().log().body();


    }


}
