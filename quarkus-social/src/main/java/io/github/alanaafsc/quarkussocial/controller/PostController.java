package io.github.alanaafsc.quarkussocial.controller;

import io.github.alanaafsc.quarkussocial.dto.CreatePostRequest;
import io.github.alanaafsc.quarkussocial.dto.PostResponse;
import io.github.alanaafsc.quarkussocial.service.PostService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostController {

    @Inject
    PostService postService;

    @POST
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){
        postService.save(userId, request);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId){
        List<PostResponse> postResponseList = postService.listAllPosts(userId, followerId);
        return Response.ok(postResponseList).build();
    }
}
