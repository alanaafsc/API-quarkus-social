package io.github.alanaafsc.quarkussocial.rest;

import io.github.alanaafsc.quarkussocial.domain.model.Follower;
import io.github.alanaafsc.quarkussocial.domain.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.domain.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
