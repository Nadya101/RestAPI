package tests;

import com.google.gson.Gson;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres_objects.Registration;
import reqres_objects.ResourceList;
import reqres_objects.User;
import reqres_objects.UserList;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;

public class ReqresTest {

    @Test
    public void postCreateUserPostRequestTest() {
        User user = User.builder() //create object for sending by post request
                .name("morpheus")
                .job("leader")
                .build();
        given()
                .log().all()
                .header("Content-Type", "application/json; charset=utf-8") /// when PUT or POST req
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));

    }


    @Test
    public void listUsersGetRequestTest() {

        String body = given()
        .when()
                .get("https://reqres.in/api/users?page=2")
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        UserList userList = new Gson().fromJson(body, UserList.class);
        System.out.println(userList);
        Assert.assertEquals(userList.getData().get(0).getFirstName(), "Michael");
        Assert.assertEquals(userList.getTotal().toString(), "12");
        Assert.assertEquals(userList.getData().get(1).getEmail(), "lindsay.ferguson@reqres.in");
    }


    @Test
    public void singleUserGetRequestTest() {
        given()
        .when()
                .get("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.first_name", equalTo("Janet"))
                .body("data.id", equalTo(2));
    }

    @Test
    public void singleUserNotFoundTest() {
        given()
        .when()
                .get("https://reqres.in/api/users/23")
        .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void listResourceGetRequestTest() {
        String body = given()
        .when()
                .get("https://reqres.in/api/unknown")
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        ResourceList resourceList = new Gson().fromJson(body, ResourceList.class);
        Assert.assertEquals(resourceList.getTotalPages(), 2);
        Assert.assertEquals(resourceList.getData().get(0).getColor(), "#98B2D1");
        Assert.assertEquals(resourceList.getData().get(2).getPantoneValue(), "19-1664");
        Assert.assertEquals(resourceList.getData().get(3).getId(), 4);
    }

    @Test
    public void singleResourceGetRequestTest() {
        given()
        .when()
                .get("https://reqres.in/api/unknown/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.name", equalTo("fuchsia rose"))
                .body("data.year", equalTo(2001))
                .body("support.url", equalTo("https://reqres.in/#support-heading"))
                .body("support.text", equalTo("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void singleResourceNotFoundGetRequestTest() {
        given()
        .when()
                .get("https://reqres.in/api/unknown/23")
        .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void updateUserPutRequestTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .log().all()
                .body(user)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }

    @Test
    public void updateUserPatchRequestTest() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        given()
                .log().all()
                .body(user)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("zion resident"));
    }


    @Test
    public void deleteUserDeleteRequestTest() {
        given()
        .when()
                .log().all()
                .delete("https://reqres.in/api/users/2")
        .then()
                .statusCode(204);
    }

    @Test
    public void registerSuccessfulPostRequestTest() {
        Registration registrationUser = Registration.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .log().all()
                .body(registrationUser)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void registerUnsuccessfulPostRequestTest() {
        Registration registrationUser = Registration.builder()
                .email("sydney@fife")
                .build();
        given()
                .log().all()
                .body(registrationUser)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void loginSuccessfulPostRequestTest() {
        Registration loginUser = Registration.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        given()
                .body(loginUser)
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all()
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().all()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void loginUnsuccessfulPostRequestTest() {
        Registration loginUser = Registration.builder()
                .email("peter@klaven")
                .build();
        given()
                .body(loginUser)
                .header("Content-Type", "application/json; charset=utf-8")
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void delayedResponseGetRequestTest() {
        String body = given()
        .when()
                .get("https://reqres.in/api/users?delay=3")
        .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        UserList userList = new Gson().fromJson(body, UserList.class);
        Assert.assertEquals(userList.getTotal().toString(), "12");
        Assert.assertEquals(userList.getData().get(0).getEmail(), "george.bluth@reqres.in");
        Assert.assertEquals(userList.getData().get(1).getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
        Assert.assertEquals(userList.getData().get(2).getId(), 3);
        Assert.assertEquals(userList.getSupport().getText(), "To keep ReqRes free, contributions towards server costs are appreciated!");
    }
}