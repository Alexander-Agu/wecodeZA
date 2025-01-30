package com.wecodeZA.backend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private  final UserRepository userRepository;

    public List<User> getUsers() {
        return  userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> accountOptional = userRepository.findUserByEmail(user.getEmail());
        if (accountOptional.isPresent()){
            throw new IllegalStateException(("Email already taken"));
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {

        boolean exist = userRepository.existsById(id);
        if(!exist){
            throw new IllegalStateException("Account with ID: " + id + " does not exist");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, String name, String email, String username) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Account with ID: " + id + " does not exist"));

        if (name != null && !name.isEmpty() && !Objects.equals(user.getName(), name)){
            user.setName(name);
        }


        if (email != null && !email.isEmpty() && !Objects.equals(user.getEmail(), email)){
            Optional<User> accountOptional = userRepository.findUserByEmail(email);
            if (accountOptional.isPresent()){
                throw new IllegalStateException("Email already taken");
            }
            user.setEmail(email);
        }

        if (username != null && !username.isEmpty() && !Objects.equals(user.getUsername(), username)) {
            Optional<User> userOptional = userRepository.findUserByUsername(username);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("Username already taken");
            }
            user.setUsername(username);
        }

        userRepository.save(user);




    }
}

