package com.wecodeZA.backend.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getUsers() {
        return  userRepository.findAll();
    }

    public void addNewUser(User user) {

        String email = user.getEmail();
        if (email == null || !email.endsWith("@student.wethinkcode.co.za")) {
            throw new IllegalArgumentException("Email must end with @student.wethinkcode.co.za");
        }

        Optional<User> accountOptional = userRepository.findUserByEmail(user.getEmail());
        if (accountOptional.isPresent()){
            throw new IllegalStateException("Email already taken");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }


        if (user.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        if (!user.getPassword().matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }

        if (!user.getPassword().matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character");
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (user.getProfession() == null || user.getProfession().isEmpty()) {
            throw new IllegalArgumentException("Profession cannot be null or empty");
        }

        if (user.getBio() != null && user.getBio().length() > 500) {
            throw new IllegalArgumentException("Bio description is too long");
        }


        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));


        userRepository.save(user);
    }
    private void validatePassword(String password){
        if (password.length() < 8){
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }


        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new IllegalArgumentException("Password must contain at least one special character");
        }
    }


    public void deleteUser(Long id) {

        boolean exist = userRepository.existsById(id);
        if(!exist){
            throw new IllegalStateException("Account with ID: " + id + " does not exist");
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, String name, String lastname, String email, String username, String profession, String bio) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Account with ID: " + id + " does not exist"));

        if (name != null && !name.isEmpty() && !Objects.equals(user.getName(), name)) {
            user.setName(name.trim());
        }
        if (lastname != null && !lastname.isEmpty() && !Objects.equals(user.getLastname(), lastname)) {
            user.setLastname(lastname);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(user.getEmail(), email)) {
            Optional<User> accountOptional = userRepository.findUserByEmail(email);
            if (accountOptional.isPresent()) {
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
        if (profession != null && !profession.isEmpty() && !Objects.equals(user.getProfession(), profession)){
            user.setProfession(profession);
        }
        userRepository.save(user);
    }
    public boolean validateUser(String username, String rawPassword){
        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        else {
            return false;
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User with ID " + id + " not found"));
    }
}

