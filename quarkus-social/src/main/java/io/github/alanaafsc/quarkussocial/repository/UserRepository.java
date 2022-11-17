package io.github.alanaafsc.quarkussocial.repository;

import io.github.alanaafsc.quarkussocial.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
