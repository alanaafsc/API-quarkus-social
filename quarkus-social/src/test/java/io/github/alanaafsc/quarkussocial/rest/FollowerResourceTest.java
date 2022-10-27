package io.github.alanaafsc.quarkussocial.rest;

import io.github.alanaafsc.quarkussocial.domain.model.Follower;
import io.github.alanaafsc.quarkussocial.domain.model.User;
import io.github.alanaafsc.quarkussocial.domain.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.domain.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.rest.dto.FollowerRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
class FollowerResourceTest {

    @Inject
    UserRepository userRepository;

    Long userId;
    Long followerId;

    @BeforeEach
    @Transactional
    void setUp() {
        //usuário padrão dos testes
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();

        //usuário seguidor
        var follower = new User();
        follower.setAge(35);
        follower.setName("follower");
        userRepository.persist(follower);
        followerId = follower.getId();
    }

    @Test
    @DisplayName("should return 409 when followerId is equal to userId")
    public void sameUserAsFollowerTest(){
        var body = new FollowerRequest();
        body.setFollowerId(userId);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .pathParam("userId", userId)
        .when()
            .put()
        .then()
            .statusCode(Response.Status.CONFLICT.getStatusCode())
            .body(Matchers.is("You can't follow yourself!"));
    }

    @Test
    @DisplayName("should return 404 when userId is not found")
    public void userNotFoundTest(){
        var body = new FollowerRequest();
        body.setFollowerId(userId);
        var nonExistentUserId = 999;

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .pathParam("userId", nonExistentUserId)
        .when()
            .put()
        .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("should follow a user")
    public void followUserTest(){
        var body = new FollowerRequest();
        body.setFollowerId(followerId);

        given()
            .contentType(ContentType.JSON)
            .body(body)
            .pathParam("userId", userId)
        .when()
            .put()
        .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }
}