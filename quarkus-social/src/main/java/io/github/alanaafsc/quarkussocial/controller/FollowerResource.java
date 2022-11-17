package io.github.alanaafsc.quarkussocial.controller;

import io.github.alanaafsc.quarkussocial.dto.FollowerRequest;
import io.github.alanaafsc.quarkussocial.dto.FollowersPerUseResponse;
import io.github.alanaafsc.quarkussocial.service.FollowerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    @Inject
    FollowerService followerService;

    @PUT
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest request){

        followerService.follow(userId, request);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId){

        FollowersPerUseResponse responseObject = followerService.listAll(userId);
        return Response.ok(responseObject).build();
    }

    @DELETE
    public Response unfollowUser(@PathParam("userId") Long userId, @QueryParam("followerId") Long followerId){
        followerService.unfollow(userId, followerId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
