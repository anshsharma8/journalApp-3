package net.Anshul.journalApp.service;

import net.Anshul.journalApp.entity.JournalEntry;
import net.Anshul.journalApp.entity.User;
import net.Anshul.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {



    @Autowired
    private UserRepository userRepository;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }


    public void saveNewUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAll() {

        return  userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return  userRepository.findById(id);
    }

    public void deleteById(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }


}
