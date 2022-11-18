package io.github.alanaafsc.quarkussocial.service;

import io.github.alanaafsc.quarkussocial.exception.FollowsException;
import io.github.alanaafsc.quarkussocial.exception.NoFollowerException;
import io.github.alanaafsc.quarkussocial.exception.NotFoundUserException;
import io.github.alanaafsc.quarkussocial.model.Post;
import io.github.alanaafsc.quarkussocial.model.User;
import io.github.alanaafsc.quarkussocial.repository.FollowerRepository;
import io.github.alanaafsc.quarkussocial.repository.PostRepository;
import io.github.alanaafsc.quarkussocial.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.dto.CreatePostRequest;
import io.github.alanaafsc.quarkussocial.dto.PostResponse;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PostService {

    private static final String MESSAGE_FORGOT_FOLLOWER_HEADER = "You forgot the header followerId";
    private static final String MESSAGE_FOLLOWER_NOT_FOUND = "Follower Not Found";
    private static final String MESSAGE_FOLLOWS = "You can't see these posts";

    @Inject
    UserRepository userRepository;
    @Inject
    PostRepository repository;
    @Inject
    FollowerRepository followerRepository;

    @Transactional
    public void save(Long userId, CreatePostRequest request){
        User user = userRepository.findById(userId);
        if(user == null){
            throw new NotFoundUserException();
        }
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);
        repository.persist(post);
    }

    public List<PostResponse> listAllPosts(Long userId, Long followerId){
        User user = userRepository.findById(userId);
        if(user == null){
            throw new NotFoundUserException();
        }

        if(followerId == null){
            throw new NoFollowerException(MESSAGE_FORGOT_FOLLOWER_HEADER);
          //  return Response.status(Response.Status.BAD_REQUEST).
  //                  entity("You forgot the header followerId").build();
        }

        User follower = userRepository.findById(followerId);
        if(follower == null){
            throw new NoFollowerException(MESSAGE_FOLLOWER_NOT_FOUND);
            //return Response.status(Response.Status.BAD_REQUEST).
           //         entity("inexistent followerId").build();
        }

        boolean follows = followerRepository.follows(follower, user);
        if(!follows){
            throw new FollowsException(MESSAGE_FOLLOWS);
          //  return Response.status(Response.Status.FORBIDDEN).
             //       entity("You can't see these posts").build();
        }

        var query = repository.
                find("user", Sort.by("dateTime", Sort.Direction.Descending), user);
        var list = query.list();

        var postResponseList = list.stream().map(post -> PostResponse.fromEntity(post)).
                collect(Collectors.toList());

        return postResponseList;
    }
}
