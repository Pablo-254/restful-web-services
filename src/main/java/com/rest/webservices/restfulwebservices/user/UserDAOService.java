package com.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDAOService {
    static private int userCount = 0;

    private static List<User> users = new ArrayList<>();
    static{
        users.add(new User(++userCount,"Patrice O'neal", LocalDate.now().minusYears(30)));
        users.add(new User(++userCount,"Vaniall O'neal", LocalDate.now().minusYears(25)));
        users.add(new User(++userCount,"Jim O'neal", LocalDate.now().minusYears(89)));


    }
    public List<User> findAll(){
        return users;

    }
    public User saveUser(User user){
        user.setId(++userCount);
        users.add(user);
        return  user;
    }
    public User getById(int id){

        Predicate<? super User> predicate = user -> user.getId()==id;
        return users.stream().filter(predicate).findFirst().orElse(null);

        }

    public void deleteById(int id) {
        Predicate<? super User> predicate = user -> user.getId()==id;
        users.removeIf(predicate);
    }


}

