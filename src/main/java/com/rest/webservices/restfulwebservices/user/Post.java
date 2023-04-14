package com.rest.webservices.restfulwebservices.user;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private int post_id;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public int getId() {
        return post_id;
    }

    public void setId(int id) {
        this.post_id = post_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + post_id +
                ", description='" + description + '\'' +
                '}';
    }
}
