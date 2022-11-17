package io.github.alanaafsc.quarkussocial.controller;

import io.github.alanaafsc.quarkussocial.dto.CreatePostRequest;
import io.github.alanaafsc.quarkussocial.dto.PostResponse;
import io.github.alanaafsc.quarkussocial.model.Post;
import io.github.alanaafsc.quarkussocial.model.User;
import io.github.alanaafsc.quarkussocial.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.repository.PostRepository;
import io.github.alanaafsc.quarkussocial.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.service.PostService;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

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
