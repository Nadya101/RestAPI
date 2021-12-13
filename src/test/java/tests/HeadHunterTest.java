package tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.VacanciesList;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeadHunterTest {

    @Test
    public void vacancyTest() {
        String body = given()
            .when()
                .get("https://api.hh.ru/vacancies?text=QA")
            .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        VacanciesList vacanciesList= new Gson().fromJson(body, VacanciesList.class);
        int salaryToFromAPI = vacanciesList.getItems().get(2).getSalary().getTo();
        System.out.println(" Salary 'to' is "+salaryToFromAPI);
       //demo assert: Assert.assertEquals(vacancyPage.getSalaryTo(), salaryToFromAPI);
        System.out.println("Include expose " + vacanciesList);
        VacanciesList vacanciesListWithoutExpose= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                .fromJson(body, VacanciesList.class);
        System.out.println("Exclude expose "+ vacanciesListWithoutExpose);

    }
}
