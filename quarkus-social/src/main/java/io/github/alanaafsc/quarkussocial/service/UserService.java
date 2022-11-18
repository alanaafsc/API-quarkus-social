package io.github.alanaafsc.quarkussocial.service;

import io.github.alanaafsc.quarkussocial.exception.NotFoundUserException;
import io.github.alanaafsc.quarkussocial.model.User;
import io.github.alanaafsc.quarkussocial.repository.UserRepository;
import io.github.alanaafsc.quarkussocial.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Inject
    Validator validator;


    @Transactional
    public User saveUser(CreateUserRequest userRequest){

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
       /* if(!violations.isEmpty()){
            return ResponseError.createFromValidation(violations).
                    withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        } */
        //setar excecao para erro de validacao aqui =========================================================

        User user = new User();
        user.setAge(userRequest.getAge());
        user.setName(userRequest.getName());

        repository.persist(user);

        return user;

      //  return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }


    public List<User> findAllUsers(){
        PanacheQuery<User> query = repository.findAll();
        return query.list();
    }


    @Transactional
    public void delete(Long id){
        User user = repository.findById(id);

        /*if(user != null){
            repository.delete(user);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build(); */
        if(user == null){
            throw new NotFoundUserException();
        }

        repository.delete(user);
    }

    @Transactional
    public void update(Long id, CreateUserRequest userData){
        User user = repository.findById(id);
        if (user == null) {
            throw new NotFoundUserException();
        }
            user.setName(userData.getName());
            user.setAge(userData.getAge());
       //     return Response.noContent().build();

      //  return Response.status(Response.Status.NOT_FOUND).build();
    }
}
