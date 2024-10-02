package net.Anshul.journalApp.Controller;

import net.Anshul.journalApp.entity.JournalEntry;
import net.Anshul.journalApp.entity.User;
import net.Anshul.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @GetMapping
    public String healthCheck() {
        return "ok";
    }
}
