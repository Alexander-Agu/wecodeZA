package com.wecodeZA.backend.User;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/wecode")
public class UserController {

    public UserController(UserService useService) {
        this.useService = useService;
    }

    private final UserService useService;

    @GetMapping("/users")
    public List<User> getUsers(){
        return useService.getUsers();


    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user){
        useService.addNewUser(user);
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        useService.deleteUser(id);
    }

    @PutMapping(path = "/users/{id}")
    public void updateUser(
            @PathVariable("id") Long id,
            @RequestBody(required = false) String name,
            @RequestBody(required = false) String lastname,
            @RequestBody(required = false) String email,
            @RequestBody(required = false) String username){
        useService.updateUser(id, name, lastname, email, username);
    }




}
