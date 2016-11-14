package vertx;

import com.jayway.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.given;


/**
 * Created by vrajan on 11/8/2016.
 */
public class ApiTest {

    final String baseUri = "http://localhost:8080/";
    String basePath = "/persons";
    ArrayList<String> nameList = new ArrayList<>(3);
    @BeforeClass
    public void setUpTest(){
        RestAssured.baseURI = baseUri;
        RestAssured.basePath = basePath;
        nameList.add("Dr. Strange");
        nameList.add("Monty Python");
        nameList.add("Iron man");
    }

    @Test
    public void getAll(){

        ArrayList<String > jsonArray =
            given().
                    log().all().
            when().
                    get("/").
            then().
                    log().all().
                    statusCode(200).
            extract().
                    path("name");

        jsonArray.forEach(s -> {
            try {
                Assert.assertTrue(nameList.contains(s));
            }catch (AssertionError ex){
                System.out.println( "Name not found " + s);
                throw ex;
            }
        });
    }
/*
    @Test(dependsOnMethods = "getAll" )
    public void addPerson(){
        String newPersondId = "newPerson123";

         given().
                log().all()
         .when().
                put("/" +newPersondId ).

         then().
                log().all().
                statusCode(200);
    }
    */


}
