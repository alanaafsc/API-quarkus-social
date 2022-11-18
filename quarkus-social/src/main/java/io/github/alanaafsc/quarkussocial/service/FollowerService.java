package io.github.alanaafsc.quarkussocial.service;

import io.github.alanaafsc.quarkussocial.dto.FollowerRequest;
import io.github.alanaafsc.quarkussocial.dto.FollowerResponse;
import io.github.alanaafsc.quarkussocial.dto.FollowersPerUseResponse;
import io.github.alanaafsc.quarkussocial.exception.NoFollowerException;
import io.github.alanaafsc.quarkussocial.exception.NotFoundUserException;
import io.github.alanaafsc.quarkussocial.exception.UserEqualsFollowerException;
import io.github.alanaafsc.quarkussocial.model.Follower;
import io.github.alanaafsc.quarkussocial.model.User;
import io.github.alanaafsc.quarkussocial.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerService {

    public static final String MESSAGE_FOLLOWER_NOT_FOUND = "Follower Not Found";
    public static final String MESSAGE_USER_NOT_FOUND = "User Not Found";

    @Inject
    FollowerRepository repository;

    @Inject
    UserRepository userRepository;
    //deu erro estando "public" n sei pq, entao tirei; mesma coisa nos outros services

    @Transactional
    public void follow(Long userId, FollowerRequest request){

        if(userId.equals(request.getFollowerId())){
            throw new UserEqualsFollowerException("You can't follow yourself!");
           // return Response.status(Response.Status.CONFLICT).entity("You can't follow yourself!").build();
        }

        User user = userRepository.findById(userId);
        if(user == null){
            throw new NotFoundUserException(MESSAGE_USER_NOT_FOUND);
        }
        var follower = userRepository.findById(request.getFollowerId());
        if(follower == null) {
            throw new NoFollowerException(MESSAGE_FOLLOWER_NOT_FOUND);
        }
        boolean follows = repository.follows(follower, user);

        if(!follows){
            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);

            repository.persist(entity);
        }
       // return Response.status(Response.Status.NO_CONTENT).build();
    }

    public FollowersPerUseResponse listAll(Long userId){

        User user = userRepository.findById(userId);
        if(user == null){
            throw new NotFoundUserException(MESSAGE_USER_NOT_FOUND);
        }

        var list = repository.findByUser(userId);

        FollowersPerUseResponse responseObject = new FollowersPerUseResponse();
        responseObject.setFollowersCount(list.size());
        var followersList = list.stream().map(FollowerResponse::new).
                collect(Collectors.toList());
        responseObject.setContent(followersList);
        return responseObject;
    }

    @Transactional
    public void unfollow(Long userId, Long followerId){
        User user = userRepository.findById(userId);
        if(user == null){
           throw new NotFoundUserException(MESSAGE_USER_NOT_FOUND);
        }
        repository.deleteByFollowerAndUser(followerId, userId);
      //  return Response.status(Response.Status.NO_CONTENT).build();
    }
}
