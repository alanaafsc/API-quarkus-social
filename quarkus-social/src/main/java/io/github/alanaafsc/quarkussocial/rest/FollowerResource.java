package io.github.alanaafsc.quarkussocial.rest;

import io.github.alanaafsc.quarkussocial.domain.model.Follower;
import io.github.alanaafsc.quarkussocial.domain.model.User;
import io.github.alanaafsc.quarkussocial.domain.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.domain.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.rest.dto.FollowerRequest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private FollowerRepository repository;
    private UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @PUT
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest request){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var follower = userRepository.findById(request.getFollowerId());
        var entity = new Follower();
        entity.setUser(user);
        entity.setFollower(follower);

        repository.persist(entity);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}