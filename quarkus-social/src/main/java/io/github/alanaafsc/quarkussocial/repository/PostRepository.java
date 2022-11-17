package io.github.alanaafsc.quarkussocial.repository;

import io.github.alanaafsc.quarkussocial.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {

}
