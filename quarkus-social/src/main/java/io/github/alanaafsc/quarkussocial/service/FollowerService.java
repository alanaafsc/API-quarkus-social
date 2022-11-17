package io.github.alanaafsc.quarkussocial.service;

import io.github.alanaafsc.quarkussocial.dto.FollowerRequest;
import io.github.alanaafsc.quarkussocial.dto.FollowerResponse;
import io.github.alanaafsc.quarkussocial.dto.FollowersPerUseResponse;
import io.github.alanaafsc.quarkussocial.model.Follower;
import io.github.alanaafsc.quarkussocial.model.User;
import io.github.alanaafsc.quarkussocial.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.repository.UserRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;


public class FollowerService {

    @Inject
    private FollowerRepository repository;

    @Inject
    private UserRepository userRepository;


    @Transactional
    public Response followUser(Long userId, FollowerRequest request){

        if(userId.equals(request.getFollowerId())){
            return Response.status(Response.Status.CONFLICT).entity("You can't follow yourself!").build();
        }

        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = repository.follows(follower, user);

        if(!follows){
            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);

            repository.persist(entity);
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    public Response listFollowers(Long userId){

        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var list = repository.findByUser(userId);
        FollowersPerUseResponse responseObject = new FollowersPerUseResponse();
        responseObject.setFollowersCount(list.size());
        var followersList = list.stream().map(FollowerResponse::new).
                collect(Collectors.toList());
        responseObject.setContent(followersList);
        return Response.ok(responseObject).build();
    }

    @Transactional
    public Response unfollowUser(Long userId, Long followerId){
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        repository.deleteByFollowerAndUser(followerId, userId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
