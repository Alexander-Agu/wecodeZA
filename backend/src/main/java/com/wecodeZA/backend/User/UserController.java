package com.wecodeZA.backend.User;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable("id")Long id){
        return useService.getUserById(id);
    }

    @PostMapping("users/signup")
    public ResponseEntity<String> signupUser(@RequestBody User user){
        useService.addNewUser(user);
        return ResponseEntity.ok("Sign up successful");
    }

    @PostMapping("users/login")
    public ResponseEntity<String> login(@RequestBody User user){
        boolean isValid = useService.validateUser(user.getUsername(), user.getPassword());

        if(isValid){
            return ResponseEntity.ok("Login successful");
        }
        else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id){
        useService.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String profession,
            @RequestParam(required = false) String bio){
        useService.updateUser(id, name, lastname, email, username, profession, bio);
        return ResponseEntity.ok("User details successfully updated");
    }




}
