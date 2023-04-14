package com.rest.webservices.restfulwebservices.user;

import com.rest.webservices.restfulwebservices.jpa.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    private UserDAOService service;
    private PostRepository postRepository;

    @Autowired
    public UserResource() {

        this.service = service;

    }

    @GetMapping(path="/users")
    public List<User> getAllUsers(){
        return service.findAll();
        }

    @GetMapping (path="/users/{id}")
    public EntityModel<User> findUserById(@PathVariable int id){
        User findOne = service.getById(id);
        if(findOne == null) {
            throw new UserNotFoundException(":id" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(findOne);
        WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        entityModel.add(linkBuilder.withRel("all-users"));
        return entityModel;

    }
    @PostMapping(path="/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user){

        User savedUser = service.saveUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                       path("/{id}").buildAndExpand(savedUser.
                        getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(path="/users/{id}")
    public void deleteUserById(@PathVariable int id){
        service.deleteById(id);
    }








}
