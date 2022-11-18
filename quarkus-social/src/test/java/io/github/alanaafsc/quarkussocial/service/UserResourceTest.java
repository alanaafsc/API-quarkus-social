package io.github.alanaafsc.quarkussocial.service;

import io.github.alanaafsc.quarkussocial.controller.UserController;
import io.github.alanaafsc.quarkussocial.dto.CreateUserRequest;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static io.github.alanaafsc.quarkussocial.exception.ConstraintViolationExceptionMapper.UNPROCESSABLE_ENTITY_STATUS;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(UserController.class)
@QuarkusTestResource(QuarkusSocialTestLifeCycleManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserResourceTest {

    @Test
    @DisplayName("Should create an user successfully")
    @Order(1)
    public void createUserTest(){
        var user = new CreateUserRequest();
        user.setName("Fulano");
        user.setAge(30);
        var response =
                given().
                        contentType(ContentType.JSON).
                        body(user).
                when()
                        .post()
                .then()
                        .extract().response();
        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));
    }

    @Test
    @Order(2)
    @DisplayName("Should return error when json is not valid")
    public void createUserValidationErrorTest(){
        var user = new CreateUserRequest();
        user.setAge(null);
        user.setName(null);
        var response =
                given()
                        .contentType(ContentType.JSON)
                        .body(user)
                .when()
                        .post()
                .then()
                        .extract().response();
        assertEquals(UNPROCESSABLE_ENTITY_STATUS, response.statusCode());

        List<Map<String, String>> errors = response.jsonPath().getList("violacoes");
        assertNotNull(errors.get(0).get("mensagem"));
        assertNotNull(errors.get(1).get("mensagem"));
    }

    @Test
    @DisplayName("Should list all users")
    @Order(3)
    public void listAllUsersTest(){
        given()
            .contentType(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(200);
    }
}