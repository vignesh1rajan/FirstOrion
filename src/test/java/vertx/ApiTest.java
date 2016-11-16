package vertx;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import vertx.bo.Persons;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.given;


/**
 * Created by vrajan on 11/8/2016.
 */
public class ApiTest {

    final String baseUri = "http://localhost:8080/";
    String basePath = "/persons";
    ArrayList<String> nameList = new ArrayList<>(3);
    final Persons p = new Persons();

    @BeforeClass
    public void setUpTest(){
        RestAssured.baseURI = baseUri;
        RestAssured.basePath = basePath;
        nameList.add("Dr. Strange");
        nameList.add("Monty Python");
        nameList.add("Iron man");


        p.setName("Mr. James Bond");
        p.setAddress("55 Bond Lane");
        p.setStatus("unprocessed");
        p.setPersonId("jBond123");
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

    @Test(dependsOnMethods = "getAll" )//
    public void addPerson(){

         given()
                .log().all()
                 .body(p, ObjectMapperType.JACKSON_2)
         .when().
                put("/" +p.getPersonId() )

         .then().
                log().all().
                statusCode(200);
    }

    @Test(dependsOnMethods = "addPerson")
    public void verifyAddPerson(){
        given()
                .log().all()
        .when()
                .get("/" + p.getPersonId()).
        then()
                .log().all().
                statusCode(200);

    }



}
