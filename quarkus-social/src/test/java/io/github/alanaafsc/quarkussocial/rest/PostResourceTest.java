package io.github.alanaafsc.quarkussocial.rest;

import io.github.alanaafsc.quarkussocial.domain.model.User;
import io.github.alanaafsc.quarkussocial.domain.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {

    @Inject
    UserRepository userRepository;
    Long userId;

    @BeforeEach
    @Transactional
    public void setUP(){
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();
    }

    @Test
    @DisplayName("Should create a post for a user")
    public void createPostTest(){
        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        given()
            .contentType(ContentType.JSON)
            .body(postRequest)
            .pathParam("userId", userId)
        .when()
            .post()
        .then()
            .statusCode(201);
    }

    @Test
    @DisplayName("Should return 404 when trying to make a post for a non-existent user")
    public void postForANonExistentUserTest(){
        var postRequest = new CreatePostRequest();
        postRequest.setText("Some text");

        var nonExistentUserId = 999;
        given()
                .contentType(ContentType.JSON)
                .body(postRequest)
                .pathParam("userId", nonExistentUserId)
                .when()
                .post()
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("should return 404 when user doesn't exist")
    public void listPostUserNotFoundTest(){

    }

    @Test
    @DisplayName("should return 400 when followerId header is not present")
    public void listPostFollowerHeaderNotSendTest(){

    }

    @Test
    @DisplayName("should return 404 when follower doesn't exist")
    public void listPostFollowerNotFoundTest(){

    }

    @Test
    @DisplayName("should return 403 when follower isn't a follower")
    public void listPostNotAFollowerTest(){

    }

    @Test
    @DisplayName("should return posts")
    public void listPostsTest(){

    }
}