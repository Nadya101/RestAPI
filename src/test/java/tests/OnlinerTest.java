package tests;

import com.google.gson.Gson;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OnlinerTest {

    @Test
    public void getCurrencyRateTest() {
//https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb
        given()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb")
                .then()
                .statusCode(200);
    }

    @Test
    public void getUAHRateTest() {
//https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb
        given()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=UAH&type=nbrb")
                .then()
                .statusCode(200);
    }


    @Test
    public void getEURRateTest() {
//https://www.onliner.by/sdapi/kurs/api/bestrate?currency=RUB&type=nbrb
        given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=EUR&type=nbrb")
                .then()
                .log().all()
                .statusCode(200)
                .body("amount", equalTo("2,8431"));//from db can take value

    }



}