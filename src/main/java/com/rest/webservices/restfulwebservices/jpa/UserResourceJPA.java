package com.rest.webservices.restfulwebservices.jpa;

import com.rest.webservices.restfulwebservices.user.Post;
import com.rest.webservices.restfulwebservices.user.PostNotFoundException;
import com.rest.webservices.restfulwebservices.user.User;
import com.rest.webservices.restfulwebservices.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserResourceJPA {

    private UserRepository repository;
    private PostRepository postRepository;

    @Autowired
    public UserResourceJPA(UserRepository repository,PostRepository postRepository) {
        this.repository=repository;

        this.postRepository = postRepository;
    }

    @GetMapping(path="/jpa/users")
    public List<User> getAllUsers(){
        return repository.findAll();
        }

    @GetMapping (path="/jpa/users/{id}")
    public EntityModel<User> findUserById(@PathVariable int id){
        Optional<User> findOne = repository.findById(id);
        if(findOne.isEmpty()) {
            throw new UserNotFoundException(":id" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(findOne.get());
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;

    }
    @PostMapping(path="/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                       path("/{id}").buildAndExpand(savedUser.
                        getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(path="/jpa/users/{id}")
    public void deleteUserById(@PathVariable int id){
        repository.deleteById(id);
    }
    @GetMapping(path="/jpa/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable int id){
        Optional<User> findOne = repository.findById(id);
        if(findOne.isEmpty()) {
            throw new UserNotFoundException(":id" + id);

        }
       return findOne.get().getPosts();

    }
    @PostMapping(path="/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPostsForUser(@PathVariable int id, @Valid @RequestBody Post posts){
        Optional<User> findOne = repository.findById(id);
        if(findOne.isEmpty()) {
            throw new UserNotFoundException(":id" + id);

        }
        posts.setUser(findOne.get());

        Post savedPost = postRepository.save(posts); ;
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(savedPost.
                        getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping(path="/jpa/users/{id}/posts/{post_id}")
    public String retrievePostDetailsForUser(@PathVariable int id,@PathVariable int post_id){
        Optional<Post> post = postRepository.findById(post_id);
        if(post.isPresent())
            throw new PostNotFoundException(":id" + post_id);


       else return post.get().getDescription();

    }










}
